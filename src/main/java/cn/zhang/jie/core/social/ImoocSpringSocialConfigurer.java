package cn.zhang.jie.core.social;

import org.springframework.social.security.SocialAuthenticationFilter;
import org.springframework.social.security.SpringSocialConfigurer;

public class ImoocSpringSocialConfigurer extends SpringSocialConfigurer {

	private String filterProcessUrl;
	
	public ImoocSpringSocialConfigurer(String filterProcessUrl) {
		this.filterProcessUrl = filterProcessUrl;
	}
	
	//重写该方法主要是为了覆盖默认的拦截地址 /auth
	@Override
	//这里的object是实际需要放到过滤器上的filter
	protected <T> T postProcess(T object) {
		SocialAuthenticationFilter filter = (SocialAuthenticationFilter) super.postProcess(object); 
		filter.setFilterProcessesUrl(filterProcessUrl);
		return (T) filter; 
	}
}
