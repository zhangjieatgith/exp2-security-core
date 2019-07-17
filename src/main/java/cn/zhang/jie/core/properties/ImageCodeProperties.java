package cn.zhang.jie.core.properties;

public class ImageCodeProperties extends SmsCodeProperties{

	public ImageCodeProperties () {
		//设置自己的默认长度，而不是使用继承过来的属性
		setLength(4);
	}
	
	private int width = 67;
	private int height = 23;
	
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
}
