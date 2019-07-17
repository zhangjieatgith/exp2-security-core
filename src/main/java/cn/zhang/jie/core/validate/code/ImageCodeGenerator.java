package cn.zhang.jie.core.validate.code;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import cn.zhang.jie.core.properties.SercurityProperties;

public class ImageCodeGenerator implements ValidateCodeGenerator{

	private SercurityProperties sercurityProperties;
	
	@Override
	public ImageCode generate(ServletWebRequest request) {
		// 图片的宽度,如果请求中没有，那么就从应用级的配置中获取
	    int width = ServletRequestUtils.getIntParameter(request.getRequest(), "width", sercurityProperties.getCode().getImage().getWidth());
	    // 图片的高度。
	    int height = ServletRequestUtils.getIntParameter(request.getRequest(), "height", sercurityProperties.getCode().getImage().getHeight());;
	    // 验证码字符个数
	    int codeCount = sercurityProperties.getCode().getImage().getLength();
	    // 验证码干扰线数
	    int lineCount = 20;
	    BufferedImage bufferedImage;
	    char[] codeSequence = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
	            'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
	            'X', 'Y', 'Z', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
	    String code = null;
        //字符所在x坐标
        int x = 0;
        //字体高度
        int fontHeight = 0;
        //字符所在y坐标
        int codeY = 0;
        int red = 0;
        int green = 0;
        int blue = 0;
        x = width / (codeCount + 2);
        fontHeight = height - 2;
        codeY = height - 4;
        bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
        Graphics2D graphics2D = bufferedImage.createGraphics();
        Random random = new Random();
        graphics2D.setColor(Color.WHITE);
        graphics2D.fillRect(0, 0, width,height);
        Font font = new Font("Fixedays",Font.PLAIN,fontHeight);
        graphics2D.setFont(font);
 
        for (int i = 0; i < lineCount; i++) {
            //x轴第一个点的位置
            int x1 = random.nextInt(width);
            //y轴第一个点的位置
            int y1 = random.nextInt(height);
            //x轴第二个点的位置
            int x2 = x1 + random.nextInt(width >> 2);
            //y轴第二个点的位置
            int y2 = y1 + random.nextInt(height >> 2);
 
            red = random.nextInt(255);
            green = random.nextInt(255);
            blue = random.nextInt(255);
 
            graphics2D.setColor(new Color(red, green, blue));
            graphics2D.drawLine(x1, y1, x2, y2);
        }
 
        StringBuffer randomCode = new StringBuffer(codeCount);
        for (int i = 0; i < codeCount; i++) {
            String strRand = String.valueOf(codeSequence[random.nextInt(codeSequence.length)]);
            red = random.nextInt(255);
            green = random.nextInt(255);
            blue = random.nextInt(255);
            graphics2D.setColor(new Color(red, green, blue));
            graphics2D.drawString(strRand, (i +1) * x, codeY);
            randomCode.append(strRand);
        }
        code = randomCode.toString();
		return new ImageCode(bufferedImage, code, sercurityProperties.getCode().getImage().getExpireIn());
	}

	public SercurityProperties getSercurityProperties() {
		return sercurityProperties;
	}
	public void setSercurityProperties(SercurityProperties sercurityProperties) {
		this.sercurityProperties = sercurityProperties;
	}
}
