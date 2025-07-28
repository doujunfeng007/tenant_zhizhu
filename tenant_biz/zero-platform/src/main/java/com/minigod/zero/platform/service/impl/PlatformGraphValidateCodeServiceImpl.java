package com.minigod.zero.platform.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.minigod.zero.platform.service.IPlatformGraphValidateCodeService;
import com.minigod.zero.core.redis.cache.ZeroRedis;
import com.minigod.zero.core.tool.utils.StringUtil;
import com.minigod.zero.platform.dto.GraphValidateCodeDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * @author caizongtai
 * @since 2023/7/10 17:45
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class PlatformGraphValidateCodeServiceImpl implements IPlatformGraphValidateCodeService {

	private final ZeroRedis zeroRedis;

	private final String GRAPH_VAl_CAPTCHA="GRAPH_VAl_CAPTCHA:";

	@Value("${platform.graphCode.isGraphValidate:true}")
	private Boolean isGraphValidate;

	@Override
	public String convertImageToBase64(BufferedImage image) {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(image, "png", baos);
			baos.flush();
			byte[] imageBytes = baos.toByteArray();
			baos.close();
			return Base64.getEncoder().encodeToString(imageBytes);
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}
	}

	@Override
	public  BufferedImage generateCaptchaImage(Integer width, Integer height, String code) {


		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D graphics = image.createGraphics();

		// 设置背景颜色
		graphics.setColor(Color.WHITE);
		graphics.fillRect(0, 0, width, height);

		// 设置字体样式
		Font font = new Font("Arial", Font.BOLD, 40);
		graphics.setFont(font);

		// 绘制验证码文本
		FontMetrics fontMetrics = graphics.getFontMetrics();
		int codeWidth = fontMetrics.stringWidth(code);
		int codeHeight = fontMetrics.getHeight();
		int x = (width - codeWidth) / 2;
		int y = (height - codeHeight) / 2 + fontMetrics.getAscent();
		graphics.setColor(Color.BLACK);
		graphics.drawString(code, x, y);

		// 绘制干扰线
		graphics.setColor(Color.GRAY);
		Random random = new Random();
		for (int i = 0; i < 5; i++) {
			int startX = random.nextInt(width);
			int startY = random.nextInt(height);
			int endX = random.nextInt(width);
			int endY = random.nextInt(height);
			graphics.drawLine(startX, startY, endX, endY);
		}

		// 绘制干扰点
		int pointCount = width * height / 50;
		for (int i = 0; i < pointCount; i++) {
			int px = random.nextInt(width);
			int py = random.nextInt(height);
			image.setRGB(px, py, Color.BLACK.getRGB());
		}

		graphics.dispose();

		return image;
	}

	@Override
	public GraphValidateCodeDto fetchGraphValidateCode(Integer width, Integer height, Integer codeLength) {
		//根据长度生成随机码
		String validateCode = RandomUtil.randomNumbers(codeLength);

		//生成基本图像
		BufferedImage bufferedImage = this.generateCaptchaImage(width, height, validateCode);

		//转换为base64编码
		String resBase64 = this.convertImageToBase64(bufferedImage);

		//封装数据
		if (StringUtil.isNotBlank(resBase64)){
			//生成captchaKey
			String captchaKey = UUID.randomUUID().toString().replaceAll("-", "");

			GraphValidateCodeDto graphValidateCodeDto = new GraphValidateCodeDto();
			graphValidateCodeDto.setCaptchaKey(captchaKey);
			graphValidateCodeDto.setBaseCode(resBase64);
			//将前缀和key作为redis中的key,code作为value
			zeroRedis.setEx(GRAPH_VAl_CAPTCHA + validateCode +captchaKey, validateCode, 300L);// 缓存到Redis
			return graphValidateCodeDto;
		}else {
			return null;
		}

	}

	@Override
	public boolean checkGraphValidateCode(String code, String captchaKey) {

		//如果不需要检验,直接返回true
		if (!isGraphValidate) return true;

		//判空
		if (StringUtil.isBlank(code)||StringUtil.isBlank(captchaKey)){
			return false;
		}

		//去除空格
		code=code.trim();
		captchaKey=captchaKey.trim();

		//code和captchaKey只可能是英文和数字,不存在其他的可能,防止恶意请求
		Pattern pattern = Pattern.compile("^[a-zA-Z0-9]+$");
		if (!pattern.matcher(code).matches()||!pattern.matcher(captchaKey).matches()){
			return false;
		}

		//获取redis中的code
		String redisStockKey=GRAPH_VAl_CAPTCHA +code+captchaKey;
		String redisStockCode=zeroRedis.get(redisStockKey);

		//检验时效已经过了
		if (StringUtil.isBlank(redisStockCode)){
			return false;
		}

		if (code.equals(redisStockCode)){
			zeroRedis.del(redisStockKey);
			return true;
		}else {
			return false;
		}
	}
}
