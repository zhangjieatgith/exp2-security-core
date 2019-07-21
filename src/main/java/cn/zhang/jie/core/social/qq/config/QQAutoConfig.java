package cn.zhang.jie.core.social.qq.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.social.SocialAutoConfigurerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.web.servlet.View;

import cn.zhang.jie.core.properties.QQproperties;
import cn.zhang.jie.core.properties.SercurityProperties;
import cn.zhang.jie.core.social.ImoocConnectView;
import cn.zhang.jie.core.social.qq.connect.QQConnectionFactory;

@Configuration
//这里表示当配置文件中配置了 imooc.security.social.qq.app-id 时，下面的配置文件才生效
@ConditionalOnProperty(prefix = "imooc.security.social.qq" ,name = "appId")
public class QQAutoConfig extends SocialAutoConfigurerAdapter {

	@Autowired
	private SercurityProperties securityProperties;
	
	@Bean
	@Override
	protected ConnectionFactory<?> createConnectionFactory() {
		QQproperties qQproperties = securityProperties.getSocial().getQq();
		return new QQConnectionFactory(qQproperties.getProviderId(), qQproperties.getAppId(), qQproperties.getAppSecret());
	}
	
	
	//针对不同的社交账号，定义不同的view
	@Bean("connect/callbackConnected")
	//可以通过定义个bean，name是qqConnectedView来覆盖当前的bean
	@ConditionalOnMissingBean(name = "callbackConnected")
	public View qqConnectedView() {
		return new ImoocConnectView();
	}
}
