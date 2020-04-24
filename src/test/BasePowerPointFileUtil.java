/**
 * BasePowerPointFileUtil.java
 * Copyright &reg; 2017 窦海宁
 * All right reserved
 */

package test;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.sl.usermodel.AutoShape;
import org.apache.poi.sl.usermodel.Shape;
import org.apache.poi.sl.usermodel.Slide;
import org.apache.poi.sl.usermodel.SlideShow;

/**
 * <p>PowerPoint文件工具基类
 *
 * <p>通用的PowerPoint文件工具基类，可用于从PowerPoint文档中抽取文本信息
 *
 * @author  窦海宁, chong0660@sina.com
 * @since   AiyuCommonCore-1.0
 * @version AiyuCommonCore-1.0
 */
public abstract class BasePowerPointFileUtil {

    /**
     * <p>读取PowerPoint文件中的幻灯片对象
     *
     * @param  slideShow SlideShow对象
     *
     * @return 读取出的工作薄列表
     *
     * @modify 窦海宁, 2017-01-18
     */
    protected static List readSlideShow(SlideShow slideShow) {

        List slideList = null;
        if (slideShow != null) {

            slideList = new ArrayList();
            List slides = slideShow.getSlides();
            for (int i = 0 ; i < slides.size() ; i++) {

                slideList.add(BasePowerPointFileUtil.readSlide((Slide) slides.get(i)));
            }
        }
        return slideList;
    }

    /**
     * <p>读取指定的Slide中的数据
     *
     * @param  slide Slide对象
     *
     * @return 读取出的Slide数据列表
     *
     * @modify 窦海宁, 2017-01-18
     */
    protected static List readSlide(Slide slide) {

        List shapeList = null;
        if (slide != null) {

            shapeList = new ArrayList();
            List shapes = slide.getShapes();
            for (int i = 0 ; i < shapes.size() ; i++) {

                shapeList.add(BasePowerPointFileUtil.readShape((Shape) shapes.get(i)));
            }
        }
        return shapeList;
    }

    /**
     * <p>读取指定的图形的数据
     *
     * @param  shape Slide中的图形对象
     *
     * @return 读取出的图形数据
     *
     * @modify 窦海宁, 2017-01-18
     */
    protected static Object readShape(Shape shape) {

        String returnValue = null;
        if (shape != null) {

            if (shape instanceof AutoShape) {
                try {

                    returnValue = ((AutoShape) shape).getText();
                } catch (Exception ex) {

                    ex.printStackTrace();
                }
            }
        }
        return returnValue;
    }

}