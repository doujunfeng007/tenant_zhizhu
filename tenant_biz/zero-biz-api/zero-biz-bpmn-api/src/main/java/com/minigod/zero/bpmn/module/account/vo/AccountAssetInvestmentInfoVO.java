package com.minigod.zero.bpmn.module.account.vo;

import com.minigod.zero.bpmn.module.account.dto.AccountAssetInvestmentInfoModifyDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
  * 开户资料资产投资对象
  *
  * @param
  * @return
  */
@Data
@EqualsAndHashCode(callSuper = true)
public class AccountAssetInvestmentInfoVO extends AccountAssetInvestmentInfoModifyDTO {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "使用/交易频率名称")
	private String tradeFrequencyName;

}
