package cn.zhang.jie.core.social;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractView;

import com.fasterxml.jackson.databind.ObjectMapper;

//用来返回社交账号的绑定信息，比如 是否绑定QQ、是否绑定微信等
//组件名字是固定的
@Component("connect/status")
public class ImoocConnectionStatusView extends AbstractView {

	@Autowired
	//默认情况下是渲染到指定页面，这里希望返回的是一个json数据
	private ObjectMapper objectMapper;
	
	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {	
		//拿到所有的connection
		Map<String,List<Connection<?>>> connections =  (Map<String, List<Connection<?>>>) model.get("connectionMap");
		//对于前台，只需要拿到一个包含布尔值的map即可,即当前账号是否绑定了某个社交账号
		Map<String,Boolean> result = new HashMap<>();
		for(String key : connections.keySet()) {
			result.put(key, CollectionUtils.isNotEmpty(connections.get(key)));
		}
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().write(objectMapper.writeValueAsString(result));
	}
}
