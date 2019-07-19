package cn.zhang.jie.core.authentication.mobile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public class SmsCodeAuthenticationProvider implements AuthenticationProvider{

	@Autowired
	private UserDetailsService userDetailsService; 
	
	//自定义身份认证的逻辑
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		SmsCodeAuthenticationToken authenticationToken = (SmsCodeAuthenticationToken) authentication;
		//这里的 principal 就是 mobile
		UserDetails user = userDetailsService.loadUserByUsername((String)authenticationToken.getPrincipal());
		if(user == null) {
			throw new InternalAuthenticationServiceException("无法获取用户信息");
		} 
		//这里将是否已认证设为true，表示认证通过
		SmsCodeAuthenticationToken authenticationResult = new SmsCodeAuthenticationToken(user, user.getAuthorities());
		//由于之前将请求信息设置到了token中，这里使用拷贝，将之前的请求信息设置到新的、已认证的SmsCodeAuthenticationToken中
		authenticationResult.setDetails(authenticationToken.getDetails());
		return authenticationResult;
	}

	//AuthencationManager 进行认证判断的时候，是从Provider中挑选一个进行判断
	@Override
	public boolean supports(Class<?> authentication) {
		//判断是否是同一种类型
		return SmsCodeAuthenticationToken.class.isAssignableFrom(authentication);
	}

	
	public UserDetailsService getUserDetailsService() {
		return userDetailsService;
	}
	public void setUserDetailsService(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}
}
