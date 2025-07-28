package com.minigod.zero.cms.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @ClassName: com.minigod.zero.oms.vo.BankListVO
 * @Description: 出参
 * @Author: linggr
 * @CreateDate: 2024/5/16 16:24
 * @Version: 1.0
 */
@Data
public class DepositBankListVO {

	@ApiModelProperty(value = "详情子表id")
	private Long id;

	@ApiModelProperty(value = "转账类型 1fps 2网银 3支票 4快捷入金 5银证转账 6EDDA")
	private String supportType;

	@ApiModelProperty(value = "币种 1港币 2美元 3人民币")
	private Integer currency;

	@ApiModelProperty(value = "银行名称")
	private String bankName;

	@ApiModelProperty(value = "收款银行账户")
	private String bankAccount;

	@ApiModelProperty(value = "收款人名称")
	private String payeeName;

	@ApiModelProperty(value = "图标")
	private String icon;

	@ApiModelProperty(value = "国际编号")
	private String bankId;

	@ApiModelProperty(value = "银行编号")
	private String bankCode;

	@ApiModelProperty(value = "单笔限额")
	private BigDecimal maxAmt;

}
