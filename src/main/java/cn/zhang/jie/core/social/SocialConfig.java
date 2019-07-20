package cn.zhang.jie.core.social;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.security.SpringSocialConfigurer;

import cn.zhang.jie.core.properties.SercurityProperties;

@Configuration
@EnableSocial
public class SocialConfig extends SocialConfigurerAdapter {

	@Autowired
	private DataSource dataSource;
	@Autowired
	private SercurityProperties sercurityProperties; 
	
	@Override
	public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
		JdbcUsersConnectionRepository repository = new JdbcUsersConnectionRepository(dataSource, connectionFactoryLocator, Encryptors.noOpText());
		//可以设置表名的前缀
		repository.setTablePrefix("imooc_");
		//建表脚本在 JdbcUsersConnectionRepository 所在包下
		//表中的三个核心字段：userId(业务系统ID)、providerId(服务提供商ID)、providerUserId(服务提供商中的用户ID，比如openId)
		return repository;
	}
	
	@Bean
	public SpringSocialConfigurer imoocSocialSecurityConfig() {
		String filterProcessUrl = sercurityProperties.getSocial().getFilterProcessUrl();
		//默认情况下，socialAuthenticationFilter 会拦截所有以 /auth 开头的请求。这里使用自定义的配置
		ImoocSpringSocialConfigurer configurer = new ImoocSpringSocialConfigurer(filterProcessUrl);
		return configurer;
	}
}
