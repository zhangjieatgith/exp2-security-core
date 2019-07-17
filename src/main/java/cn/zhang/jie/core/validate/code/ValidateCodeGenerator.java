package cn.zhang.jie.core.validate.code;

import org.springframework.web.context.request.ServletWebRequest;

//声明验证码生成的接口，可以让应用开发者重写图形验证码的生成逻辑
public interface ValidateCodeGenerator {

	public ImageCode generate(ServletWebRequest request);
}
