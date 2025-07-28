package com.minigod.zero.customer.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @ClassName: com.minigod.zero.customer.vo.CustFlowCurrencyVO
 * @Description:
 * @Author: linggr
 * @CreateDate: 2024/8/22 15:15
 * @Version: 1.0
 */
@Data
public class CustFlowCurrencyVO {
	@ApiModelProperty(value = "用户Id")
	private Long custId;

	/**
	 * 总金额
	 */
	private BigDecimal totalAmount;

	/**
	 * 币种
	 */
	private String currency;
}
