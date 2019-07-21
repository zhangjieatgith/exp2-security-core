package cn.zhang.jie.core.social;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.security.SpringSocialConfigurer;

import cn.zhang.jie.core.properties.SercurityProperties;

@Configuration
@EnableSocial
public class SocialConfig extends SocialConfigurerAdapter {

	@Autowired
	private DataSource dataSource;
	@Autowired
	private SercurityProperties sercurityProperties;
	@Autowired(required = false)
	private ConnectionSignUp connectionSignUp; 
	
	@Primary
	@Bean
	@Override
	public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
		JdbcUsersConnectionRepository repository = new JdbcUsersConnectionRepository(dataSource, connectionFactoryLocator, Encryptors.noOpText());
		//可以设置表名的前缀
		repository.setTablePrefix("imooc_");
		if(connectionSignUp != null) {
			//如果QQ登录的用户不是业务系统的用户，那么就为它创建一个用户
			repository.setConnectionSignUp(connectionSignUp);
		}
		//建表脚本在 JdbcUsersConnectionRepository 所在包下
		//表中的三个核心字段：userId(业务系统ID)、providerId(服务提供商ID)、providerUserId(服务提供商中的用户ID，比如openId)
		return repository;
	}
	
	@Bean
	public SpringSocialConfigurer imoocSocialSecurityConfig() {
		String filterProcessUrl = sercurityProperties.getSocial().getFilterProcessUrl();
		//默认情况下，socialAuthenticationFilter 会拦截所有以 /auth 开头的请求。这里使用自定义的配置
		ImoocSpringSocialConfigurer configurer = new ImoocSpringSocialConfigurer(filterProcessUrl);
		//在用户不存在的时候，跳转到指定的地址
		configurer.signupUrl(sercurityProperties.getBrowser().getSignUpUrl());
		return configurer;
	}
	
	//注册过程中如何拿到SpringSocial信息（头像、姓名等），如何将UserId传给SpringSocial
	@Bean
	public ProviderSignInUtils providerSignInUtils(ConnectionFactoryLocator connectionFactoryLocator) {
		return new ProviderSignInUtils(connectionFactoryLocator, getUsersConnectionRepository(connectionFactoryLocator));
	} 
}
