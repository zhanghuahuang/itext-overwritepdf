package pdf;

import java.io.FileOutputStream;
import java.io.IOException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * 第1章：PDF和iText简介
 *	1.1第一个iText示例：Hello World
 */
public class PdfTest {
	 /** 生成的PDF文件的路径。 */
    public static final String RESULT = "E:/pdfout/hello.pdf";

    /**
     *   创建一个PDF文件：hello.pdf
     * @param    args    no arguments needed
     */
    public static void main(String[] args) throws DocumentException, IOException {
    	new PdfTest().createPdf(RESULT);
    }

    /**
     *   创建PDF文档.
     * @param filename 新PDF文档的路径
     * @throws    DocumentException
     * @throws    IOException
     */
    public void createPdf(String filename) throws DocumentException, IOException {
        // 第一步 创建文档实例
    	Rectangle pagesize = new Rectangle(100f, 100f);
    	Document document = new Document(pagesize, 36f, 72f, 36f, 36f);
        // 第二步 获取PdfWriter实例
        PdfWriter.getInstance(document, new FileOutputStream(filename));
        // 第三步 打开文档
        document.open();
        // 第四步 添加段落内容
        document.add(new Paragraph("Hello World!"));
        // 第五部 操作完成后必须执行文档关闭操作。
        document.close();
    }
}