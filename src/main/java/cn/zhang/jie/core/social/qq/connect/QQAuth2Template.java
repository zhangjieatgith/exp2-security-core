package cn.zhang.jie.core.social.qq.connect;

import java.nio.charset.Charset;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class QQAuth2Template extends OAuth2Template {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	public QQAuth2Template(String clientId, String clientSecret, String authorizeUrl, String accessTokenUrl) {
		super(clientId, clientSecret, authorizeUrl, accessTokenUrl);
		setUseParametersForClientAuthentication(true);
	}
	
	@Override
	//将请求的标准按照QQ的格式做了自定义解析
	//默认情况下，是按照map的数据结构解析的，但实际上QQ的接口返回的是一个字符串，以&拼接，例如: a=xxx&b=xxx&c=xxx，因此需要特殊处理
	protected AccessGrant postForAccessGrant(String accessTokenUrl, MultiValueMap<String, String> parameters) {
		//由于QQ的返回是一个String，不是Map
		String response = getRestTemplate().postForObject(accessTokenUrl, parameters, String.class);
		logger.info("获取accessToken 的响应 : " + response);
		String [] items = StringUtils.splitByWholeSeparatorPreserveAllTokens(response, "&");
		String accessToken = StringUtils.substringAfterLast(items[0], "=");
		Long expiresIn = new Long(StringUtils.substringAfterLast(items[1], "="));
		String refreshToken = StringUtils.substringAfterLast(items[2], "=");
		return new AccessGrant(accessToken, null, refreshToken, expiresIn);
	}
	
	@Override
	//默认OAuth2Template在处理请求的时候，不支持 text/html 的格式，因此这里做成自定义的
	protected RestTemplate createRestTemplate() {
		RestTemplate restTemplate = super.createRestTemplate();
		//这种converter支持 text/html
		restTemplate.getMessageConverters().add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
		return restTemplate;
	}
}
