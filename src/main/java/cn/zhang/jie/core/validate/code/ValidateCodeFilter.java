package cn.zhang.jie.core.validate.code;

import java.io.IOException;
import java.nio.file.PathMatcher;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import cn.zhang.jie.core.properties.SercurityProperties;

public class ValidateCodeFilter extends OncePerRequestFilter implements InitializingBean{

	private AuthenticationFailureHandler authenticationFailureHandler;
	
	private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();
	//存放配置的拦截URL
	private Set<String> urls = new HashSet<>();

	private SercurityProperties sercurityProperties;
	
	private AntPathMatcher pathMatcher = new AntPathMatcher(); 
	
	@Override
	public void afterPropertiesSet() throws ServletException {
		super.afterPropertiesSet();
		String [] configUrls = StringUtils.splitByWholeSeparatorPreserveAllTokens(sercurityProperties.getCode().getImage().getUrl(), ",");
		for(String url : configUrls) {
			//这里是配置的url
			urls.add(url);
		}
		urls.add("/authentication/form");	//登录请求一定要做验证码验证
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		boolean action = false;
		for(String url : urls) {
			if(pathMatcher.match(url, request.getRequestURI())) {
				action = true;
			}
		}
		if(action) {
			try {
				validate(new ServletWebRequest(request));
			} catch (ValidateCodeException e) {
				authenticationFailureHandler.onAuthenticationFailure(request, response, e);
				return;
			}
		}
		filterChain.doFilter(request, response);
	}

	private void validate(ServletWebRequest request) throws ServletRequestBindingException {
		ValidateCode codeInSession = (ValidateCode) sessionStrategy.getAttribute(request, ValidateCodeProcessor.SESSION_KEY_PREFIX + "IMAGE");
		String codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(), "imageCode");
		if(StringUtils.isBlank(codeInRequest)) {
			throw new ValidateCodeException("验证码的值不能为空");
		}
		if(codeInSession == null) {
			throw new ValidateCodeException("验证码不存在");
		}
		if(codeInSession.isExpired()) {
			sessionStrategy.removeAttribute(request, ValidateCodeProcessor.SESSION_KEY_PREFIX + "IMAGE");
			throw new ValidateCodeException("验证码已过期");
		}
		if(!StringUtils.equalsIgnoreCase(codeInSession.getCode(), codeInRequest)) {
			throw new ValidateCodeException("验证码不匹配");
		}
		sessionStrategy.removeAttribute(request, ValidateCodeProcessor.SESSION_KEY_PREFIX + "IMAGE");
	}

	public AuthenticationFailureHandler getAuthenticationFailureHandler() {
		return authenticationFailureHandler;
	}
	public void setAuthenticationFailureHandler(AuthenticationFailureHandler authenticationFailureHandler) {
		this.authenticationFailureHandler = authenticationFailureHandler;
	}
	public SessionStrategy getSessionStrategy() {
		return sessionStrategy;
	}
	public void setSessionStrategy(SessionStrategy sessionStrategy) {
		this.sessionStrategy = sessionStrategy;
	}
	public Set<String> getUrls() {
		return urls;
	}
	public void setUrls(Set<String> urls) {
		this.urls = urls;
	}
	public SercurityProperties getSercurityProperties() {
		return sercurityProperties;
	}
	public void setSercurityProperties(SercurityProperties sercurityProperties) {
		this.sercurityProperties = sercurityProperties;
	}
}
