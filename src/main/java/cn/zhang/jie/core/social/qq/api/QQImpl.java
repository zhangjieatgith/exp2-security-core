package cn.zhang.jie.core.social.qq.api;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Objects;

import org.apache.commons.lang.StringUtils;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ch.qos.logback.classic.Logger;

public class QQImpl extends AbstractOAuth2ApiBinding implements QQ {
	
	//获取openId地址
	private static final String URL_GET_OPENID = "https://graph.qq.com/oauth2.0/me?access_token=%s";
	//获取用户信息地址
	private static final String URL_GET_USER_INFO = "https://graph.qq.com/user/get_user_info?oauth_consumer_key=%s&openid=%s";
	
	private String appId;
	private String openId;
	
	private ObjectMapper objectMapper = new ObjectMapper();
	
	public QQImpl(String accessToken,String appId) {
		//将accessToken在发送请求的时候，使用参数的形式携带，默认使用请求头的方式携带
		super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER);
		this.appId = appId;
		String url = String.format(URL_GET_OPENID, accessToken);
		String result = getRestTemplate().getForObject(url, String.class);
		System.out.println(result);
		this.openId = StringUtils.substringBetween(result, "\"openid\":\"", "\"}");
	}
	
	@Override
	public QQUserInfo getUserInfo() {
		String url = String.format(URL_GET_USER_INFO, appId ,openId);
		String result = getRestTemplate().getForObject(url, String.class);
		System.out.println(result);
		QQUserInfo qqUserInfo = null;
		try {
//			qqUserInfo = objectMapper.readValue(result, QQUserInfo.class);
			qqUserInfo = parseIntoQQUserInfo(result);
			qqUserInfo.setOpenId(openId);
			return qqUserInfo;
		} catch (Exception e) {
			throw new RuntimeException("获取用户信息失败", e);
		}
	}
	
	public QQUserInfo parseIntoQQUserInfo(String source) {
		JSONObject jsonObject = JSONObject.parseObject(source);
		QQUserInfo qqUserInfo = new QQUserInfo();
		qqUserInfo.setRet(jsonObject.getString("ret"));
		qqUserInfo.setMsg(jsonObject.getString("msg"));
		qqUserInfo.setNickname(jsonObject.getString("nickname"));
		qqUserInfo.setFigureurl_qq_1(jsonObject.getString("figureurl_qq_1"));
		return qqUserInfo;
	}
}
