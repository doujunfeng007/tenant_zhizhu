package com.minigod.zero.data.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @ClassName: com.minigod.zero.customer.vo.CustAssetsQuarterVO
 * @Description: 资金季度奖励
 * @Author: linggr
 * @CreateDate: 2024/9/27 15:58
 * @Version: 1.0
 */
@Data
public class CustAssetsQuarterVO {
	private BigDecimal quarter;
	/**
	 * 客户id
	 */
	private Long custId;


	public CustAssetsQuarterVO(BigDecimal quarter, Long custId) {
		this.quarter = quarter;
		this.custId = custId;
	}
}
