package com.minigod.zero.cust.vo.icbc;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author:yanghu.luo
 * @create: 2023-03-08 15:17
 * @Description: 结算账户绑定
 */
@Data
public class IcbcaSettleAcctReqVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 用户ID
	 */
	@ApiModelProperty(value = "用户ID")
	private Long custId;

	/**
	 * 投资账户
	 */
	@ApiModelProperty(value = "投资账户")
	private String investAcctNo;

	/**
	 * 投资账户类型，同broker25接口中的accttype(0: 现金 M: 融资)
	 */
	@ApiModelProperty(value = "投资账户类型，同broker25接口中的accttype(0: 现金 M: 融资)")
	private String investAcctType;

	/**
	 * 结算账户
	 */
	@ApiModelProperty(value = "结算账户")
	private String settleAcctNo;

	/**
	 * 结算账户类型, (1: 人民币 2: 港币 3: 美元)
	 */
	@ApiModelProperty(value = "结算账户类型, (1: 人民币 2: 港币 3: 美元)")
	private Integer settleAcctType;
}
