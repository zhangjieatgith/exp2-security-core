package cn.zhang.jie.core.authentication.mobile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.Assert;

//拦截登录请求，并组装自定义的token
public class SmsCodeAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

	//表单中的参数名
	public static final String IMOOC_FORM_MOBILE_KEY = "mobile";

	private String mobileParamter = IMOOC_FORM_MOBILE_KEY;
	//当前处理器是否只处理post请求
	private boolean postOnly = true;

	public SmsCodeAuthenticationFilter() {
		//这里的拦截，处理的是哪个请求
		super(new AntPathRequestMatcher("/authentication/mobile", "POST"));
	}

	public Authentication attemptAuthentication(HttpServletRequest request,
			HttpServletResponse response) throws AuthenticationException {
		if (postOnly && !request.getMethod().equals("POST")) {
			throw new AuthenticationServiceException(
					"Authentication method not supported: " + request.getMethod());
		}

		String mobile = obtainMobile(request);

		if (mobile == null) {
			mobile = "";
		}

		mobile = mobile.trim();
		//组装自定义的 token
		SmsCodeAuthenticationToken authRequest = new SmsCodeAuthenticationToken(mobile);
		//将请求信息设置到了token中
		setDetails(request, authRequest);
		//拿着 token 去调用 AuthenticationManager
		return this.getAuthenticationManager().authenticate(authRequest);
	}

	protected String obtainMobile(HttpServletRequest request) {
		return request.getParameter(mobileParamter);
	}


	protected void setDetails(HttpServletRequest request,
			SmsCodeAuthenticationToken authRequest) {
		authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
	}

 	public void setMobileParameter(String usernameParameter) {
		Assert.hasText(usernameParameter, "Username parameter must not be empty or null");
		this.mobileParamter = usernameParameter;
	}

	public void setPostOnly(boolean postOnly) {
		this.postOnly = postOnly;
	}

	public final String getMobileParameter() {
		return mobileParamter;
	}
}
