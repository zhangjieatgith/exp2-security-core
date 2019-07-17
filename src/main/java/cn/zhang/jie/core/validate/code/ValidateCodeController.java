package cn.zhang.jie.core.validate.code;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import cn.zhang.jie.core.properties.SercurityProperties;

@RestController
public class ValidateCodeController {
	
	public static final String SESSION_KEY = "SESSION_KEY_IMAGE_CODE";
	//Spring用来操作Session的工具类
	private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

	@Autowired
	private ValidateCodeGenerator imageCodeGenerator; 
	
	@GetMapping("/image/code")
	public void createCode(HttpServletRequest request,HttpServletResponse response) throws IOException {
		//1.生成图片
		ImageCode imageCode = imageCodeGenerator.generate(new ServletWebRequest(request));
		//2.将随机数保存到session中
		sessionStrategy.setAttribute(new ServletWebRequest(request), SESSION_KEY, imageCode);
		//3.将生成的图片写到响应中
		ImageIO.write(imageCode.getImage(), "JPEG", response.getOutputStream());
	}
}
