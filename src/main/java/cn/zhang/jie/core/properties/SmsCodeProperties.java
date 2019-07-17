package cn.zhang.jie.core.properties;

public class SmsCodeProperties {

	private int length = 6;
	private int expireIn = 60;
	//配置需要拦截的url
	private String url = "";
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public int getExpireIn() {
		return expireIn;
	}
	public void setExpireIn(int expireIn) {
		this.expireIn = expireIn;
	}
}
