package cn.zhang.jie.core.validate.code.sms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import cn.zhang.jie.core.validate.code.AbstractValidateCodeProcessor;

@Component("smsCodeProcessor")
public class SmsCodeProcessor extends AbstractValidateCodeProcessor<SmsCode> {

	@Autowired
	private SmsCodeSender smsCodeSender;
	
	@Override
	protected void send(ServletWebRequest request, SmsCode code) {
		String mobile;
		try {
			mobile = ServletRequestUtils.getRequiredStringParameter(request.getRequest(), "mobile");
			smsCodeSender.send(mobile, code.getCode());
		} catch (ServletRequestBindingException e) {
			e.printStackTrace();
		}
	}
}
