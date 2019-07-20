package cn.zhang.jie.core.social.qq.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.social.SocialAutoConfigurerAdapter;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.connect.ConnectionFactory;

import cn.zhang.jie.core.properties.QQproperties;
import cn.zhang.jie.core.properties.SercurityProperties;
import cn.zhang.jie.core.social.qq.connect.QQConnectionFactory;

@Configuration
//这里表示当配置文件中配置了 imooc.security.social.qq.app-id 时，下面的配置文件才生效
@ConditionalOnProperty(prefix = "imooc.security.social.qq" ,name = "appId")
public class QQAutoConfig extends SocialAutoConfigurerAdapter {

	@Autowired
	private SercurityProperties securityProperties;
	
	@Override
	protected ConnectionFactory<?> createConnectionFactory() {
		QQproperties qQproperties = securityProperties.getSocial().getQq();
		return new QQConnectionFactory(qQproperties.getProviderId(), qQproperties.getAppId(), qQproperties.getAppSecret());
	}
}
