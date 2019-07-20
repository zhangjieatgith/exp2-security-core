package cn.zhang.jie.core.authentication.mobile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.security.SocialUserDetailsService;

import cn.zhang.jie.core.social.MySocialAuthenticationProvider;
import cn.zhang.jie.core.social.SocialConfig;

@Configuration
public class SmsCodeAuthenticationSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

	@Autowired
	private AuthenticationSuccessHandler imoocAuthenticationSuccessHandler;
	@Autowired
	private AuthenticationFailureHandler imoocAuthenticationFailureHandler;
	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private SocialConfig socialConfig;
	@Autowired
	private ConnectionFactoryLocator connectionFactoryLocator;
	@Autowired
	private SocialUserDetailsService socialUserDetailsService;
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		//配置filter
		SmsCodeAuthenticationFilter smsCodeAuthenticationFilter = new SmsCodeAuthenticationFilter();
		smsCodeAuthenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
		smsCodeAuthenticationFilter.setAuthenticationSuccessHandler(imoocAuthenticationSuccessHandler);
		smsCodeAuthenticationFilter.setAuthenticationFailureHandler(imoocAuthenticationFailureHandler);
		
		//配置 provider
		SmsCodeAuthenticationProvider smsCodeAuthenticationProvider = new SmsCodeAuthenticationProvider();
		smsCodeAuthenticationProvider.setUserDetailsService(userDetailsService);
		
		
		//用于QQ登录时候的 AuthenticationProvider，本应提供单独的一个配置类，暂时写在此处
		MySocialAuthenticationProvider mySocialAuthenticationProvider = 
			new MySocialAuthenticationProvider(socialConfig.getUsersConnectionRepository(connectionFactoryLocator), socialUserDetailsService);
		
		
		//将自定义的provider加入到 AuthenticationManager 中
		http.authenticationProvider(smsCodeAuthenticationProvider)
			.authenticationProvider(mySocialAuthenticationProvider)
			//将自定义的过滤器加到 UsernamePasswordAuthenticationFilter 之后
			.addFilterAfter(smsCodeAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
	}
	
	public AuthenticationSuccessHandler getImoocAuthenticationSuccessHandler() {
		return imoocAuthenticationSuccessHandler;
	}
	public void setImoocAuthenticationSuccessHandler(AuthenticationSuccessHandler imoocAuthenticationSuccessHandler) {
		this.imoocAuthenticationSuccessHandler = imoocAuthenticationSuccessHandler;
	}
	public AuthenticationFailureHandler getImoocAuthenticationFailureHandler() {
		return imoocAuthenticationFailureHandler;
	}
	public void setImoocAuthenticationFailureHandler(AuthenticationFailureHandler imoocAuthenticationFailureHandler) {
		this.imoocAuthenticationFailureHandler = imoocAuthenticationFailureHandler;
	}
}
