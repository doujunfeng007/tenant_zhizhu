package com.minigod.zero.customer.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName: com.minigod.zero.customer.vo.FundDepositEddaVO
 * @Description:
 * @Author: linggr
 * @CreateDate: 2024/5/9 18:03
 * @Version: 1.0
 */
@Data
public class FundDepositEddaVO {
	@ApiModelProperty("银行卡id")
	public int backId;

	@ApiModelProperty(value = "币种")
	private String currency;

	@ApiModelProperty(value = "存入金额")
	private String depositAccount;



}
