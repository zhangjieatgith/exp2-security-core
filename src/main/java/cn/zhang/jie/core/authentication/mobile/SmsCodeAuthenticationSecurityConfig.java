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

@Configuration
public class SmsCodeAuthenticationSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

	@Autowired
	private AuthenticationSuccessHandler imoocAuthenticationSuccessHandler;
	@Autowired
	private AuthenticationFailureHandler imoocAuthenticationFailureHandler;
	@Autowired
	private UserDetailsService userDetailsService;
	
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
		
		//将自定义的provider加入到 AuthenticationManager 中
		http.authenticationProvider(smsCodeAuthenticationProvider)
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
