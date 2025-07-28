package com.minigod.zero.data.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 出入金申请统计
 *
 * @author eric
 * @since 2024-10-30 20:13:05
 */
@Data
@ApiModel(value = "出入金申请统计")
public class DepositAndWithdrawalApplyCountVO implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "入金申请审批数（包括EDDA的）")
	private Long depositApplyCount;

	@ApiModelProperty(value = "出金申请审批数")
	private Long withdrawalApplyCount;
}
