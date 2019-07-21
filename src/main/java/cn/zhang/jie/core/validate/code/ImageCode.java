package cn.zhang.jie.core.validate.code;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

public class ImageCode extends ValidateCode {

	private static final long serialVersionUID = 1L;
	
	private BufferedImage image;
	
	//设置一个过期时间
	public ImageCode(BufferedImage bufferedImage,String code,int expireInt) {
		super(code, expireInt);
		this.image = bufferedImage;
	}
	
	public ImageCode(BufferedImage bufferedImage,String code,LocalDateTime expireTime) {
		super(code, expireTime);
		this.image = bufferedImage;
	}
	
	public BufferedImage getImage() {
		return image;
	}
	public void setImage(BufferedImage image) {
		this.image = image;
	}
}
