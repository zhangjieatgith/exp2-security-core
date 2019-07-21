package cn.zhang.jie.core.validate.code;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

public abstract class AbstractValidateCodeProcessor<T extends ValidateCode> implements ValidateCodeProcessor{

	
	private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy(); 
	
	//收集系统中所有 ValidateCodeGenerator 接口的实现。遇到map类型的@autowired，Spring会查找所有该类型的(或者其实现)bean，然后以bean的名字为键，bean为值，保存到该map中
	@Autowired
	private Map<String,ValidateCodeGenerator> validateCodeGenerators;
	
	@Override
	public void create(ServletWebRequest request) throws Exception {
		T validateCode = generate(request);
		save(request, validateCode);
		send(request, validateCode);
	}
	
	public void save(ServletWebRequest request,T code) {
		//这里是为了不将图片保存到session中，而是仅仅将图形校验码保存到session中
		ValidateCode validateCode = new ValidateCode(code.getCode(), code.getExpireTime());
		sessionStrategy.setAttribute(request, SESSION_KEY_PREFIX + getGeneratorType(request).toUpperCase(), validateCode);
	}
	
	@SuppressWarnings("unchecked")
	public void validate(ServletWebRequest request) throws ValidateCodeException{
		ValidateCodeType validateCodeType = getValidateCodeType(request);//获取validate类型
		String sessionKey = getSessionKey(request);
		
		T code = (T)sessionStrategy.getAttribute(request, sessionKey);
		
		String codeParam;
		try {
			codeParam=ServletRequestUtils.getStringParameter(request.getRequest(), validateCodeType.getParamNameOnValidate());
		} catch (ServletRequestBindingException e) {
			throw new ValidateCodeException("获取验证码的值失败");
		}
		if(StringUtils.isBlank(codeParam)){
			throw new ValidateCodeException("验证码不能为空");
		}
		if(code==null){
			throw new ValidateCodeException("验证码不存在");
		}
		if(code.isExpired()){
			sessionStrategy.removeAttribute(request, sessionKey);
			throw new ValidateCodeException("验证码已失效");
		}
		if(!StringUtils.equals(codeParam, code.getCode())){
			throw new ValidateCodeException("验证码不匹配");
		}
		sessionStrategy.removeAttribute(request, sessionKey);
		
	}
	
	private ValidateCodeType getValidateCodeType(ServletWebRequest servletWebRequest){
		String before = StringUtils.substringBefore(getClass().getSimpleName(), "CodeProcessor");
		return ValidateCodeType.valueOf(before.toUpperCase());
	}
	
	public String getGeneratorType(ServletWebRequest request){
		return StringUtils.substringAfter(request.getRequest().getRequestURI(), "/code/");
	}
	
	public String getSessionKey(ServletWebRequest request){
		return SESSION_KEY_PREFIX + getValidateCodeType(request).toString().toUpperCase();
	}
	
	//生成校验码
	@SuppressWarnings("unchecked")
	private T generate(ServletWebRequest request) {
		String type = getProcessorType(request);
		ValidateCodeGenerator validateCodeGenerator = validateCodeGenerators.get(type + "CodeGenerator");
		return (T) validateCodeGenerator.generate(request);
	}
	
	//根据请求的url获取校验码的类型
	private String getProcessorType(ServletWebRequest request) {
		return StringUtils.substringAfter(request.getRequest().getRequestURI(), "/code/");
	}

	//发送校验码，由子类实现
	protected abstract void send(ServletWebRequest request,T validateCode);
}
