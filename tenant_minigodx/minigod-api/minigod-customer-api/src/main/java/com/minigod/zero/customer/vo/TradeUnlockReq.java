package com.minigod.zero.customer.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 李长春
 * @email cloud@bdie.com.cn
 * @WeChat 138264386257
 * @date 2023/3/24
 */
@Data
@ApiModel(value = "交易解锁入参对象", description = "交易解锁入参对象")
public class TradeUnlockReq {

	/**
	 * 解锁方式：0-交易密码 1-流动编码（令牌）
	 */
	@ApiModelProperty(value = "解锁方式：0-交易密码 1-流动编码（令牌）", required = true)
	private Integer unlockType;

	/**
	 * 交易密码（RSA）
	 */
	@ApiModelProperty(value = "交易密码（RSA）")
	private String password;

	/**
	 * 设备类型
	 */
	@ApiModelProperty(value = "设备类型", required = true)
	private Integer deviceType;

	/**
	 * 系统版本
	 */
	@ApiModelProperty(value = "系统版本", required = true)
	private String osVersion;

	/**
	 * 密码登录安全码
	 */
	@ApiModelProperty(value = "密码登录安全码")
	private String rule;

	/**
	 * 流动编码登录参数
	 */
	@ApiModelProperty(value = "流动编码登录参数")
	private String changeRule;

	/**
	 * 交易账号 可为空
	 */
	@ApiModelProperty(value = "交易账号")
	private String tradeAccount;


	private String tradeToken;

	private Integer expire = 15;
}
