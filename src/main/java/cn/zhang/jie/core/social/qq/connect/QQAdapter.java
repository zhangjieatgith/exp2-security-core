package cn.zhang.jie.core.social.qq.connect;

import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;

import cn.zhang.jie.core.social.qq.api.QQ;
import cn.zhang.jie.core.social.qq.api.QQUserInfo;

//这里的泛型指的是 API 的类型，这里是QQ
public class QQAdapter implements ApiAdapter<QQ>{
	
	@Override
	//测试API是否可用，正常是发送一个请求，这里显示的置为 true
	public boolean test(QQ api) {
		return true;
	}
	
	@Override
	//将 API 适配为 connection，在这里就是将 api 转为 values
	public void setConnectionValues(QQ api, ConnectionValues values) {
		QQUserInfo qqUserInfo = api.getUserInfo();
		values.setDisplayName(qqUserInfo.getNickname());
		values.setImageUrl(qqUserInfo.getFigureurl_qq_1());
		values.setProfileUrl(null);
		values.setProviderUserId(qqUserInfo.getOpenId());
	}

	@Override
	public UserProfile fetchUserProfile(QQ api) {
		return null;
	}

	@Override
	public void updateStatus(QQ api, String message) {
		//只有某些类似微博的应用才具备
	}
}
