package cn.zhang.jie.core.validate.code;

import org.springframework.security.core.AuthenticationException;

//针对验证码的异常，AuthenticationException是在整个过滤器链/认证过程中出现的异常的基类
public class ValidateCodeException extends AuthenticationException {

	public ValidateCodeException(String msg) {
		super(msg);
	}

	private static final long serialVersionUID = 1L;

}
