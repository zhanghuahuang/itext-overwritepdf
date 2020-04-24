package pdf;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
public class Repalce2 {

	private final static int FONTSIZE        = 9;
	private final static String[] PERSON_ARR = new String[]{"承认者","检印者","作成者"};
	private Map<String, ReplaceRegion> replaceRegionMap = new HashMap<String, ReplaceRegion>();
	private Map<String, Object> replaceTextMap          = new HashMap<String, Object>();

	public static void main(String[] args) throws IOException, DocumentException {

		Repalce2 repalce2 = new Repalce2();
		FileInputStream in = new FileInputStream("E:/pdfout/MOTOR行动路线图.pdf");
		repalce2.createNewPdf(in, new File("E:/pdfout/out.pdf"));
	}

	/**
	 * 生成新的PDF文件
	 * @param file
	 * @throws DocumentException
	 * @throws IOException
	 */
	public void createNewPdf(FileInputStream in, File file) throws DocumentException, IOException{

		byte[] pdfBytes = new byte[in.available()];
		in.read(pdfBytes);

		ByteArrayOutputStream output = new ByteArrayOutputStream();
		PdfReader reader             = new PdfReader(pdfBytes);
		PdfStamper stamper           = new PdfStamper(reader, output);
		PdfContentByte canvas        = null;;
		FileOutputStream fileOutputStream = null;

		for(String person : PERSON_ARR){
			this.replaceTextMap.put(person, person);
		}

		try{
			startProcess(reader, stamper, canvas);

			fileOutputStream = new FileOutputStream(file);
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
			if(in != null){
				in.close();
			}


			this.replaceRegionMap = null;
			this.replaceTextMap   = null;;
		}
	}

	/**
	 * 替换文本
	 * @throws IOException
	 * @throws DocumentException
	 */
	private void startProcess(PdfReader reader, PdfStamper stamper, PdfContentByte canvas) throws DocumentException, IOException{
		try{
			// 获取填充区域
			getRegion(reader);

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
					//设置字体
					BaseFont bf  = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.EMBEDDED);
					canvas.setFontAndSize(new Font(bf, FONTSIZE).getBaseFont(), FONTSIZE);
					canvas.setColorFill(BaseColor.BLUE);
					canvas.setTextMatrix(value.getX(),value.getY());
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
	 */
	private void getRegion(PdfReader reader) throws IOException {

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
}