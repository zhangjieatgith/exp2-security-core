package cn.zhang.jie.core.validate.code.sms;

import java.time.LocalDateTime;

import cn.zhang.jie.core.validate.code.ValidateCode;

public class SmsCode extends ValidateCode {

	public SmsCode(String code, LocalDateTime expireTime) {
		super(code, expireTime);
	}
	
	public SmsCode(String code, int expireIn) {
		super(code, expireIn);
	}
}
