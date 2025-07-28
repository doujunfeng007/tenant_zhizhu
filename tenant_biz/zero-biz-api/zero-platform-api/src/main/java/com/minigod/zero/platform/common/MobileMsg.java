package com.minigod.zero.platform.common;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Zhe.Xiao
 */
@Data
public class MobileMsg implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long msgId; //发送记录ID
	private String mobile;//手机号码
	private String message;//短信内容
	private Integer channel;//0-创蓝 1-阿里云
	private String tempCode;//阿里云短信模板代码
	private String tempParam;//阿里云短信模板参数
	//是否是验证码
	private Boolean verificationCode = false;
	//验证码有效期 分钟
	private Long expire = 5L;

}
