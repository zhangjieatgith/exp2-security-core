package cn.zhang.jie.core.validate.code;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

public class ImageCode {

	private BufferedImage image;
	private String code;
	private LocalDateTime expireTime;
	
	
	//设置一个过期时间
	public ImageCode(BufferedImage bufferedImage,String code,int expireInt) {
		this.image = bufferedImage;
		this.code = code;
		this.expireTime = LocalDateTime.now().plusSeconds(expireInt);
	}
	
	public ImageCode(BufferedImage bufferedImage,String code,LocalDateTime expireTime) {
		this.image = bufferedImage;
		this.code = code;
		this.expireTime = expireTime;
	}
	
	public boolean isExpired() {
		return LocalDateTime.now().isAfter(expireTime);
	}
	
	public BufferedImage getImage() {
		return image;
	}
	public void setImage(BufferedImage image) {
		this.image = image;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public LocalDateTime getExpireTime() {
		return expireTime;
	}
	public void setExpireTime(LocalDateTime expireTime) {
		this.expireTime = expireTime;
	}
}
