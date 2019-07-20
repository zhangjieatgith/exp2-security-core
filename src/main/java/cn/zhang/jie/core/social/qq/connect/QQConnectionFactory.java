package cn.zhang.jie.core.social.qq.connect;

import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import org.springframework.social.oauth2.OAuth2ServiceProvider;

import cn.zhang.jie.core.social.qq.api.QQ;

//这里的泛型指的是API的类型
public class QQConnectionFactory extends OAuth2ConnectionFactory<QQ> {

	public QQConnectionFactory(String providerId, String appId, String appSecret) {
		super(providerId, new QQServiceProvider(appId, appSecret), new QQAdapter());
	}
}
