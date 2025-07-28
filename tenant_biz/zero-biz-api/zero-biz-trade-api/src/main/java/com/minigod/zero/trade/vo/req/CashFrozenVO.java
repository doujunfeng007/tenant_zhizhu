package com.minigod.zero.trade.vo.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 资金冻结
 */
@Data
@ApiModel(value = "资金冻结", description = "资金冻结")
public class CashFrozenVO implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "资金账号")
	private String capitalAccount;

	@ApiModelProperty(value = "币种 0-人民币 1-美元 2-港币")
	private String moneyType;

	@ApiModelProperty(value = "发生金额")
	private String occurBalance;

	@ApiModelProperty(value = "限制有效日期")
	private String validDate;

	@ApiModelProperty(value = "冻结原因（对应字典项编号：1111 ）")
	private String frozenReason;

	@ApiModelProperty(value = "备注")
	private String remark;

	@ApiModelProperty(value = "中文备注")
	private String localeRemark;

	@ApiModelProperty(value = "强制透支标志 0-不允许强制透支 1-允许强制透支")
	private String overdraftForcedFlag;

	@ApiModelProperty(value = "反向流水标志（0-生成反向流水 1- 不生成反向流程）")
	private String revertFlag;
}
