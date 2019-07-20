package cn.zhang.jie.core.social;

import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.security.SocialAuthenticationProvider;
import org.springframework.social.security.SocialUserDetailsService;

public class MySocialAuthenticationProvider extends SocialAuthenticationProvider {

	public MySocialAuthenticationProvider(UsersConnectionRepository usersConnectionRepository,
			SocialUserDetailsService userDetailsService) {
		super(usersConnectionRepository, userDetailsService);
	}
}
