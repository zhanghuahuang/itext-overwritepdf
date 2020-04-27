package pdf;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;

/**
 * 替换PDF文件某个区域内的文本
 * @user : huang zhanghua
 * @date : 2020-04-23
 */
public class PdfReplacer {

	private final static int FONTSIZE = 9;
	private final static String[] PERSON_ARR = new String[]{"承认者","检印者","作成者"};
	private Map<String, ReplaceRegion> replaceRegionMap = new HashMap<String, ReplaceRegion>();
	private Map<String, Object> replaceTextMap =new HashMap<String, Object>();
	private ByteArrayOutputStream output = null;
	private PdfReader reader             = null;;
	private PdfStamper stamper           = null;;
	private PdfContentByte canvas        = null;;

	public PdfReplacer(byte[] pdfBytes) throws DocumentException, IOException{
		init(pdfBytes);
	}

	public PdfReplacer(String fileName) throws IOException, DocumentException{
		FileInputStream in = null;
		try{
			in =new FileInputStream(fileName);
			byte[] pdfBytes = new byte[in.available()];
			in.read(pdfBytes);
			init(pdfBytes);
		}finally{
			in.close();
		}
	}

	private void init(byte[] pdfBytes) throws DocumentException, IOException{
		reader = new PdfReader(pdfBytes);
		output = new ByteArrayOutputStream();
		stamper = new PdfStamper(reader, output);
	}

	public static void main(String[] args) throws IOException, DocumentException {

		PdfReplacer textReplacer = new PdfReplacer("E:/pdfout/MOTOR行动路线图.pdf");
		textReplacer.replaceText();
		textReplacer.createNewPdf("E:/pdfout/out.pdf");
	}

	/**
	 *
	 */
	public void replaceText(){

		for(String person : PERSON_ARR){
			this.replaceTextMap.put(person, "刘永超");
		}
	}

	/**
	 * 替换文本
	 * @throws IOException
	 * @throws DocumentException
	 * @user : huang zhanghua
	 * @date : 2020-04-23
	 */
	private void startProcess() throws DocumentException, IOException{
		try{
			// 获取填充区域
			getRegion();

			// 循环每一页填充
			for (int page = 1; page <= reader.getNumberOfPages(); page ++){
				canvas = stamper.getOverContent(page);

				canvas.saveState();
				Set<Entry<String, ReplaceRegion>> entrys = replaceRegionMap.entrySet();

				for (Entry<String, ReplaceRegion> entry : entrys) {
					ReplaceRegion value = entry.getValue();
					canvas.setColorFill(BaseColor.WHITE);
					canvas.rectangle(value.getX(),value.getY(),value.getW(),value.getH());
				}
				canvas.fill();
				canvas.restoreState();
				//开始写入文本
				canvas.beginText();
				for (Entry<String, ReplaceRegion> entry : entrys) {
					ReplaceRegion value = entry.getValue();
					BaseFont bf = null;
					//设置字体
					String path = "pdf/STXinwei.ttf";
					try{
						bf  = BaseFont.createFont(path, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
					}catch(Exception e){
						bf  = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",BaseFont.NOT_EMBEDDED);
					}
					canvas.setFontAndSize(new Font(bf, FONTSIZE).getBaseFont(), FONTSIZE);
					canvas.setColorFill(BaseColor.BLUE);
					canvas.setTextMatrix(replaceTextMap.get(value.getAliasName()).toString().length() == 3 ? value.getX() : value.getX() - 5,value.getY());
					canvas.showText((String) replaceTextMap.get(value.getAliasName()));
				}
				canvas.endText();
			}
		}finally{
			if(stamper != null){
				stamper.close();
			}
		}
	}

	/**
	 * 指定具体的替换位置
	 * @throws IOException
	 * @user : huang zhanghua
	 * @date : 2020-04-23
	 */
	private void getRegion() throws IOException {

		PdfReaderContentParser parser   = new PdfReaderContentParser(reader);
		PositionRenderListener listener = new PositionRenderListener(PERSON_ARR);
		parser.processContent(1, listener);
		Map<String, ReplaceRegion> parseResult = listener.getResult();

		Set<Entry<String, ReplaceRegion>> parseEntrys = parseResult.entrySet();
		for (Entry<String, ReplaceRegion> entry : parseEntrys) {
			if(entry.getValue() != null){
				this.replaceRegionMap.put(entry.getKey(), entry.getValue());
			}
		}
	}

	/**
	 * 生成新的PDF文件
	 * @user : huang zhanghua
	 * @date : 2020-04-23
	 * @param fileName
	 * @throws DocumentException
	 * @throws IOException
	 */
	public void createNewPdf(String fileName) throws DocumentException, IOException{
		FileOutputStream fileOutputStream = null;
		try{
			startProcess();
			fileOutputStream = new FileOutputStream(fileName);
			fileOutputStream.write(output.toByteArray());
			fileOutputStream.flush();
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			if(fileOutputStream != null){
				fileOutputStream.close();
			}
			if(reader != null){
				reader.close();
			}
			if(output != null){
				output.close();
			}

			output=null;
			replaceRegionMap=null;
			replaceTextMap=null;;
		}
	}
}