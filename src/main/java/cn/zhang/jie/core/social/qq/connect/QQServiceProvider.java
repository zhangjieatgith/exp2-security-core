package cn.zhang.jie.core.social.qq.connect;

import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;

import cn.zhang.jie.core.social.qq.api.QQ;
import cn.zhang.jie.core.social.qq.api.QQImpl;

//这里的泛型指的是 API 的接口类型
public class QQServiceProvider extends AbstractOAuth2ServiceProvider<QQ> {

	private String appId;
	
	//将用户导向认证服务器的url
	private static final String URL_AUTHORIZE = "https://graph.qq.com/oauth2.0/authorize";
	//去认证服务器申请令牌的url
	private static final String URL_ACCESS_TOKEN = "https://graph.qq.com/oauth2.0/token";
	
	public QQServiceProvider(String appId,String appSecret) {
		//appId,appSecret,将用户导向认证服务器的url，去认证服务器申请令牌的url
		super(new QQAuth2Template(appId, appSecret, URL_AUTHORIZE, URL_ACCESS_TOKEN));
		this.appId = appId;
	}

	@Override
	public QQ getApi(String accessToken) {
		return new QQImpl(accessToken, appId);
	}
}
