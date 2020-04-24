/**
 * PowerPoint2007FileUtil.java
 * Copyright &reg; 2017 窦海宁
 * All right reserved
 */

package test;

import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.xslf.usermodel.XMLSlideShow;

/**
 * <p>PowerPoint2007版文件工具类
 *
 * <p>通用的PowerPoint2007版文件工具类，可用于从PowerPoint文档中抽取文本信息
 *
 * @author  窦海宁, chong0660@sina.com
 * @since   AiyuCommonCore-1.0
 * @version AiyuCommonCore-1.0
 */
public abstract class PowerPoint2007FileUtil extends BasePowerPointFileUtil {

    /**
     * <p>从PowerPoint文档中提取文本信息
     *
     * @param  powerPointFile PowerPoint文件
     * @param  shapeSeparator Shape分隔符
     * @param  slideSeparator Slide分隔符
     *
     * @return 提取后的文本信息
     *
     * @modify 窦海宁, 2017-01-18
     */
    protected static String extractTextFromPowerPointFile(File powerPointFile , String shapeSeparator , String slideSeparator) {

        StringBuffer returnValue = new StringBuffer();
        if (powerPointFile != null && slideSeparator != null && shapeSeparator != null) {

            if (powerPointFile.isFile()) {

                try {

                    XMLSlideShow slideShow     = new XMLSlideShow(new FileInputStream(powerPointFile));
                    Iterator     slideIterator = readSlideShow(slideShow).iterator();
                    //遍历Slide
                    while (slideIterator.hasNext()) {

                        Iterator shapeIterator = ((List) slideIterator.next()).iterator();
                        //遍历Shape
                        while (shapeIterator.hasNext()) {

                            Object shapeValue = shapeIterator.next();
                            if (shapeValue != null) {

                                returnValue.append((String) shapeValue);
                                if (shapeIterator.hasNext()) {

                                    returnValue.append(shapeSeparator);
                                }
                            }
                        }
                        if (slideIterator.hasNext()) {

                            returnValue.append(slideSeparator);
                        }
                    }
                } catch (Exception ex) {

                    ex.printStackTrace();
                }
            }
        }
        return returnValue.toString();
    }

}