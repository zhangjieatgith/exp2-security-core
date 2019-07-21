package cn.zhang.jie.core.validate.code;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.time.LocalDateTime;

public class ValidateCode implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String code;
	private LocalDateTime expireTime;
	
	
	//设置一个过期时间
	public ValidateCode(String code,int expireInt) {
		this.code = code;
		this.expireTime = LocalDateTime.now().plusSeconds(expireInt);
	}
	
	public ValidateCode(String code,LocalDateTime expireTime) {
		this.code = code;
		this.expireTime = expireTime;
	}
	
	public boolean isExpired() {
		return LocalDateTime.now().isAfter(expireTime);
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
