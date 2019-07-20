package cn.zhang.jie.core.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

//用来封装所有系统配置
@ConfigurationProperties(prefix = "imooc.security")
public class SercurityProperties {

	private BrowserProperties browser = new BrowserProperties();
	private ValidateCodeProperties code = new ValidateCodeProperties();
	private SocialProperties social = new SocialProperties();

	public BrowserProperties getBrowser() {
		return browser;
	}
	public void setBrowser(BrowserProperties browser) {
		this.browser = browser;
	}
	public ValidateCodeProperties getCode() {
		return code;
	}
	public void setCode(ValidateCodeProperties code) {
		this.code = code;
	}
	public SocialProperties getSocial() {
		return social;
	}
	public void setSocial(SocialProperties social) {
		this.social = social;
	}
}
