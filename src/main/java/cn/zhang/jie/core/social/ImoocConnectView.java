package cn.zhang.jie.core.social;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractView;


//这里不能直接使用Component，因为不同的社交账号的名称是不同的
public class ImoocConnectView extends AbstractView {

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().write("<h3>绑定成功</h3>");
	}
}
