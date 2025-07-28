package com.minigod.zero.platform.service;

import com.minigod.zero.platform.dto.GraphValidateCodeDto;

import java.awt.image.BufferedImage;

/**
 * @author 掌上智珠
 * @since 2023/7/10 17:38
 */
public interface IPlatformGraphValidateCodeService {

	/**
	 * 将BufferedImage转换为base64
	 * @param image 图对象
	 * @return base64编码的字符串
	 */
	String convertImageToBase64(BufferedImage image);


	/**
	 * 生成基础图形验证码
	 * @param width 图形宽度
	 * @param height 图形高度
	 * @param code 验证码字符串
	 * @return 图对象
	 */
	BufferedImage generateCaptchaImage(Integer width,Integer height,String code);


	/**
	 * 生成图像验证码完整方法
	 * @param width 图形宽度
	 * @param height 图形高度
	 * @param codeLength 图行验证码位数
	 * @return 图像验证码返回对象
	 */
	GraphValidateCodeDto fetchGraphValidateCode(Integer width,Integer height,Integer codeLength);

	/**
	 * 检验图形验证码
	 * @param code 输入的图形验证码
	 * @param captchaKey 获取验证码返回的key
	 * @return
	 */
	boolean checkGraphValidateCode(String code,String captchaKey);
}
