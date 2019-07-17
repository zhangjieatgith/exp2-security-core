package cn.zhang.jie.core.validate.code;

import org.springframework.web.context.request.ServletWebRequest;

//将相同步骤、不同处理的逻辑抽取成模板模式，这里是封装验证码验证逻辑，短信和图形验证码都是“创建 -> 存储 -> 发送”，只是一些步骤的实现不同
public interface ValidateCodeProcessor {

	//验证码放入Session时的前缀
	String SESSION_KEY_PREFIX = "SESSION_KEY_FOR_CODE_";
	
	//创建校验码
	void create(ServletWebRequest request) throws Exception;
}
