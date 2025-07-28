package com.minigod.zero.cust.vo.icbc;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author:yanghu.luo
 * @create: 2023-03-08 14:36
 * @Description: 绑定账户
 */
@Data
public class Settleaccts implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 账户编号
	 */
	@ApiModelProperty(value = "账户编号")
	private String settleacctno;

	/**
	 * 账户币种类型 1: 人民币 2: 港币 3: 美元
	 */
	@ApiModelProperty(value = "账户币种类型 1: 人民币 2: 港币 3: 美元")
	private String type;
}
