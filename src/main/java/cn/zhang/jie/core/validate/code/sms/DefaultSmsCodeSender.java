package cn.zhang.jie.core.validate.code.sms;

//提供一个系统的默认实现，也可以由用户覆盖，提供自己的实现
public class DefaultSmsCodeSender implements SmsCodeSender {
	@Override
	public void send(String mobile, String code) {
		System.out.println("向手机 " + mobile + " 发送短信验证码 " + code);
	}
}
