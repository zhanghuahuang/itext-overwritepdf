package pdf;

import java.util.HashMap;
import java.util.Map;

import com.itextpdf.awt.geom.Rectangle2D.Float;
import com.itextpdf.text.pdf.parser.ImageRenderInfo;
import com.itextpdf.text.pdf.parser.RenderListener;
import com.itextpdf.text.pdf.parser.TextRenderInfo;

/**
 * pdf渲染监听,当找到渲染的文本时，得到文本的坐标x,y,w,h
 * @user : huang zhanghua
 * @date : 2020-04-23
 */
public class PositionRenderListener implements RenderListener{

	private String[] findText = null;
	private Map<String, ReplaceRegion> result = new HashMap<String, ReplaceRegion>();
	private final static float defaultH  = 0;
	private final static float fixHeight = 15;
	private final static float hOffset   = 2;

	public PositionRenderListener(String[] findText) {
		this.findText  = findText;
	}

	@Override
	public void renderText(TextRenderInfo textInfo) {
		String text = textInfo.getText();
		for (String keyWord : findText) {
			if (null != text && text.equals(keyWord)){

				Float bound = textInfo.getBaseline().getBoundingRectange();
				ReplaceRegion region = new ReplaceRegion(keyWord);
				region.setH(defaultH);
				region.setW(bound.width);
				region.setX(bound.x - hOffset);
				region.setY(bound.y - fixHeight);
				result.put(keyWord, region);
			}
		}
	}

	/**
	 * 获取填充的区域
	 * @return
	 */
	public Map<String, ReplaceRegion> getResult() {
		return this.result;
	}

	@Override
	public void beginTextBlock() {
	}

	@Override
	public void endTextBlock() {
	}

	@Override
	public void renderImage(ImageRenderInfo arg0) {
	}
}