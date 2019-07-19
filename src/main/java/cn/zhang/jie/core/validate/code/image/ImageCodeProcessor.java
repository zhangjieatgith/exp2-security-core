package cn.zhang.jie.core.validate.code.image;

import java.io.IOException;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import cn.zhang.jie.core.validate.code.AbstractValidateCodeProcessor;
import cn.zhang.jie.core.validate.code.ImageCode;

@Component("imageCodeProcessor")
public class ImageCodeProcessor extends AbstractValidateCodeProcessor<ImageCode> {

	@Override
	protected void send(ServletWebRequest request, ImageCode code) {
		try {
			ImageIO.write(code.getImage(), "JPEG", request.getResponse().getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage(), e);
		}
	}
}
