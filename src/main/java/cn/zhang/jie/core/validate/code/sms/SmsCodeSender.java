package cn.zhang.jie.core.validate.code.sms;

//短信服务的提供商
public interface SmsCodeSender {
	//手机号 + 短信验证码
	void send(String mobile,String code);
}
