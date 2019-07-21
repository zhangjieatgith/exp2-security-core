package cn.zhang.jie.core.properties;

//封装和浏览器相关的配置
public class BrowserProperties {

	//指定默认人，如果用户没有配置登录页，就是用默认的标准登录页
	private String loginPage = "/imooc-signIn.html";
	
	private LoginType loginType = LoginType.JSON;
	
	//配置“记住我”的时效性
	private int rememberMeSeconds = 3600;
	//配置QQ登录时的默认注册页
	private String signUpUrl = "imooc-signUp.html";

	//执行退出的url
	private String signOutUrl;
	
	public String getLoginPage() {
		return loginPage;
	}
	public void setLoginPage(String loginPage) {
		this.loginPage = loginPage;
	}
	public LoginType getLoginType() {
		return loginType;
	}
	public void setLoginType(LoginType loginType) {
		this.loginType = loginType;
	}
	public int getRememberMeSeconds() {
		return rememberMeSeconds;
	}
	public void setRememberMeSeconds(int rememberMeSeconds) {
		this.rememberMeSeconds = rememberMeSeconds;
	}
	public String getSignUpUrl() {
		return signUpUrl;
	}
	public void setSignUpUrl(String signUpUrl) {
		this.signUpUrl = signUpUrl;
	}
	public String getSignOutUrl() {
		return signOutUrl;
	}
	public void setSignOutUrl(String signOutUrl) {
		this.signOutUrl = signOutUrl;
	}
}
