package com.minigod.zero.cust.vo.icbc;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author:yanghu.luo
 * @create: 2023-03-08 09:39
 * @Description: 未绑定结算账户查询
 */
@Data
public class IcbcaUnboundSettleAcctReqVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 客户ID
	 */
	@ApiModelProperty(value = "客户ID")
	private Long custId;

	/**
	 * 投资账户
	 */
	@ApiModelProperty(value = "投资账户")
	private String investAcct;
}
