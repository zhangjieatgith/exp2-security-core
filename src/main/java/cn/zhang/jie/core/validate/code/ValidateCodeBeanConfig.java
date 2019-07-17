package cn.zhang.jie.core.validate.code;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cn.zhang.jie.core.properties.SercurityProperties;
import cn.zhang.jie.core.validate.code.sms.DefaultSmsCodeSender;
import cn.zhang.jie.core.validate.code.sms.SmsCodeSender;

@Configuration
public class ValidateCodeBeanConfig {

	@Autowired
	private SercurityProperties sercurityProperties; 
	
	//将验证码的实现做成可配置的（将接口的实现做成可配置的），这里的效果和声明 @Component一样 
	@Bean
	@ConditionalOnMissingBean(name = "imageCodeGenerator")
	public ValidateCodeGenerator imageCodeGenerator() {
		ImageCodeGenerator codeGenerator = new ImageCodeGenerator();
		codeGenerator.setSercurityProperties(sercurityProperties);
		return codeGenerator;
	}
	
	
	//将验证码的实现做成可配置的（将接口的实现做成可配置的），这里的效果和声明 @Component一样
	//第二种写法，如果在系统中找到了 SmsCodeSender 的实现，就不会用这里的bean了
	@Bean
	@ConditionalOnMissingBean(SmsCodeSender.class)
	public SmsCodeSender smsCodeSender() {
		return new DefaultSmsCodeSender();
	}
}
