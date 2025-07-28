package com.minigod.zero.cust.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 李长春
 * @email cloud@bdie.com.cn
 * @WeChat 138264386257
 * @date 2023/3/27
 */
@Data
@ApiModel(value = "交易解锁返参对象", description = "交易解锁返参对象")
public class TradeUnlockRes {

	@ApiModelProperty(value = "是否需要做2FA")
	private boolean need2fa;

	@ApiModelProperty(value = "验证码Key（获取验证码接口返回）（需要做2FA时会返回）")
	private String captchaKey;

	@ApiModelProperty(value = "2FA手机号（脱敏）")
	private String tradePhone;

	@ApiModelProperty(value = "解锁状态")
	private Boolean unlockStatus;

}
