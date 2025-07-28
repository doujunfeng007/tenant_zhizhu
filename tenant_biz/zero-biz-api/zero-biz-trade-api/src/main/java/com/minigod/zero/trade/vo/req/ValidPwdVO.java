package com.minigod.zero.trade.vo.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author:yanghu.luo
 * @create: 2023-05-19 14:09
 * @Description: 验证交易密码
 */
@Data
public class ValidPwdVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 交易账号
	 */
	@ApiModelProperty(value = "交易账号")
	private String tradeAccount;

	/**
	 * 密码
	 */
	@ApiModelProperty(value = "密码")
	private String password;


}
