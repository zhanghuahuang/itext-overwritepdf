package test;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import org.apache.poi.xslf.extractor.XSLFPowerPointExtractor;
import org.apache.poi.xslf.usermodel.XMLSlideShow;


public class test {
	public static void main(String[] args){

		String fileNm = "PowerComm安装流程.pptx";
		String relativepath = "/测试/测试/PowerComm安装流程.pptx/";
		int b = relativepath.lastIndexOf("a");
		String aa = relativepath.replace(fileNm,"").replaceAll("/","\\\\");

		writeppt();
	}

    public static String getProperties(String filePath, String keyWord){
        Properties prop = new Properties();
        String value = null;
        try {
            // 通过输入缓冲流进行读取配置文件
            InputStream InputStream = new BufferedInputStream(new FileInputStream(new File(filePath)));
            // 加载输入流
            prop.load(InputStream);
            // 根据关键字获取value值
            value = prop.getProperty(keyWord);
            System.out.println(value);
            value = prop.getProperty("port");
            System.out.println(value);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }
    public static String writeppt(){
    	String value = null;
    	String filePath = "C:\\Users\\mid1565\\Desktop\\iotest\\PowerComm安装流程.pptx";
    	String path = "C:\\Users\\mid1565\\Desktop\\iotest\\1.pptx";
    	try {
    		// 通过输入缓冲流进行读取配置文件
    		InputStream inputStream = new BufferedInputStream(new FileInputStream(new File(filePath)));
    		System.out.println(inputStream.available());
    		// 加载输入流
    		File targetFile = new File(path);
			OutputStream outStream = new FileOutputStream(targetFile, true);

            byte[] buffer = new byte[1024 * 4];
            int i = 0;
            while ((i = inputStream.read(buffer)) != -1) {
            	System.out.println(i);
            	outStream.write(buffer, 0, i);
            }

			inputStream.close();
			outStream.close();

			//

    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	return value;
    }
    public static String ppt(){
    	String value = null;
    	String filePath = "C:\\Users\\mid1565\\Desktop\\iotest\\01【YESZ】画面设计_检查课.xlsx";
    	String path = "C:\\Users\\mid1565\\Desktop\\iotest\\1.xlsx";
    	try {
    		// 通过输入缓冲流进行读取配置文件
    		InputStream inputStream = new BufferedInputStream(new FileInputStream(new File(filePath)));
    		System.out.println(inputStream.available());
    		// 加载输入流
    		File targetFile = new File(path);
    		OutputStream outStream = new FileOutputStream(targetFile, true);

    		byte[] buffer = new byte[inputStream.available()];
    		inputStream.read(buffer);
    		outStream.write(buffer);

    		inputStream.close();
    		outStream.close();

    		//

    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	return value;
    }


}
