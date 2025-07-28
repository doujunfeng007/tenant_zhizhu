package com.minigod.zero.bpmn.module.edda.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @ClassName: com.minigod.zero.customer.vo.FundDepositEddaVO
 * @Description:
 * @Author: linggr
 * @CreateDate: 2024/5/9 18:03
 * @Version: 1.0
 */
@Data
public class FundDepositEddaBO {
	/**
	 * edda授权id
	 */
	public Long eddaInfoId;
	/**
	 * 币种代码,币种 1港币 2美元 3人民币
	 */
	private Integer moneyType;
	/**
	 * 存入金额
	 */
	private BigDecimal depositAmount;
}
