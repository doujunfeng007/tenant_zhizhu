package com.minigod.zero.trade.vo.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 资金解冻
 */
@Data
@ApiModel(value = "资金解冻", description = "资金解冻")
public class CashFrozenCancelVO implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "资金冻结日期")
	private String jourDate;

	@ApiModelProperty(value = "资金冻结反向流水号")
	private String jourSerialNo;

	@ApiModelProperty(value = "解冻金额")
	private String cancelBalance;

	@ApiModelProperty(value = "发生金额，审核界面显示")
	private String occurBalance;

	@ApiModelProperty(value = "备注")
	private String remark;

	@ApiModelProperty(value = "中文备注")
	private String localeRemark;
}
