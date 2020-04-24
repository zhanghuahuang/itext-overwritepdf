package pdf;

/**
 * 需要填充的区域
 * @user : huang zhanghua
 * @date : 2020-04-23
 */
public class ReplaceRegion {

	private String aliasName;
	private Float x;
	private Float y;
	private Float w;
	private Float h;

	public ReplaceRegion(String aliasName){
		this.aliasName = aliasName;
	}

	/**
	 * 替换区域的别名
	 * @user : huang zhanghua
	 * @date : 2020-04-23
	 * @return
	 */
	public String getAliasName() {
		return aliasName;
	}
	public void setAliasName(String aliasName) {
		this.aliasName = aliasName;
	}
	public Float getX() {
		return x;
	}
	public void setX(Float x) {
		this.x = x;
	}
	public Float getY() {
		return y;
	}
	public void setY(Float y) {
		this.y = y;
	}
	public Float getW() {
		return w;
	}
	public void setW(Float w) {
		this.w = w;
	}
	public Float getH() {
		return h;
	}
	public void setH(Float h) {
		this.h = h;
	}
}