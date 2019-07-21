package cn.zhang.jie.core.social;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

//@Controller
public class ImoocConnectView2 {

//	@RequestMapping("/connect/callback")
	public void renderMergedOutputModel(HttpServletRequest request,HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().write("<h3>QQ绑定成功</h3>");
	}
}
