package cn.zhang.jie.core.validate.code;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import cn.zhang.jie.core.properties.SercurityProperties;
import cn.zhang.jie.core.validate.code.sms.SmsCode;

@Component("smsCodeGenerator")
public class SmsCodeGenerator implements ValidateCodeGenerator{

	@Autowired
	private SercurityProperties sercurityProperties;
	
	@Override
	public ValidateCode generate(ServletWebRequest request) {
		String code = RandomStringUtils.randomNumeric(sercurityProperties.getCode().getSms().getLength());
		return new SmsCode(code, sercurityProperties.getCode().getSms().getExpireIn());
	}

	public SercurityProperties getSercurityProperties() {
		return sercurityProperties;
	}
	public void setSercurityProperties(SercurityProperties sercurityProperties) {
		this.sercurityProperties = sercurityProperties;
	}
}
