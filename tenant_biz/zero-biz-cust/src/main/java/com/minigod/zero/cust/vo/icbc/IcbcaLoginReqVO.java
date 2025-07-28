package com.minigod.zero.cust.vo.icbc;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author:yanghu.luo
 * @create: 2023-03-07 15:18
 * @Description: 登录请求
 */
@Data
public class IcbcaLoginReqVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 登录账号 工银 loginName
	 */
	@ApiModelProperty(value = "登录账号 工银 loginName")
	private String loginName;

	/**
	 * 密码
	 */
	@ApiModelProperty(value = "密码")
	private String password;

	/**
	 * 工银安全键盘
	 */
	@ApiModelProperty(value = "工银安全键盘")
	private String rule;

	/**
	 * 工银安全键盘
	 */
	@ApiModelProperty(value = "工银安全键盘")
	private String changeRule;

	/**
	 * 设备类型 I-ios,A-其他所有
	 */
	@ApiModelProperty(value = "设备类型 I-ios,A-其他所有")
	private String deviceType;

	/**
	 * 设备号，设备UUID
	 */
	@ApiModelProperty(value = "设备号，设备UUID")
	private String deviceIdentity;

	/**
	 * 系统版本号
	 */
	@ApiModelProperty(value = "系统版本号")
	private String osver;

	/**
	 * 验证码key,后台用于保存验证码值的key，获取验证码图片的时候传给前端
	 */
	@ApiModelProperty(value = "验证码key,后台用于保存验证码值的key，获取验证码图片的时候传给前端")
	private String captchaKey;

	/**
	 * 用户输入的验证码
	 */
	@ApiModelProperty(value = "用户输入的验证码")
	private String captchaInput;

	/**
	 * 登录类型
	 * 0:数字密码登录
	 * 1:指纹登录
	 * 2:人脸登录
	 * 9: softtoken登录（加密数据中的login_token为对应的softt_oken_sn）
	 */
	@ApiModelProperty(value = "登录类型 0:数字密码登录，1:指纹登录，2:人脸登录，9: softtoken登录")
	private Integer btype;
}
