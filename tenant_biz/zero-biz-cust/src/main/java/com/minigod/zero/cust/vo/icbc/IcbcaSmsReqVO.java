package com.minigod.zero.cust.vo.icbc;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author:yanghu.luo
 * @create: 2023-03-07 17:32
 * @Description: 发送短信请求
 */
@Data
public class IcbcaSmsReqVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 登录账号 工银 loginName
	 */
	@ApiModelProperty(value = "登录账号 工银 loginName")
	private String loginName;

	/**
	 * 语言
	 */
	@ApiModelProperty(value = "语言")
	private String lang;

	/**
	 * 短信类型 1:数字密码登录,2:指纹识别开启,3:人脸识别开启,4:指纹识别登录,5:人脸识别登录
	 */
	@ApiModelProperty(value = "短信类型 1:数字密码登录,2:指纹识别开启,3:人脸识别开启,4:指纹识别登录,5:人脸识别登录")
	private Integer smstype;

}
