package com.minigod.zero.bpmn.module.account.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 后台审核开户修改信息 返回对象
 *
 * @author eric
 * @since 2024-08-05 14:08:01
 */
@Data
@ApiModel(value = "AccountOpenInfoModifyApplyApproveDTO", description = "后台审核开户修改信息")
public class AccountOpenInfoModifyApplyApproveDTO {
	/**
	 * 申请记录id
	 */
	@ApiModelProperty(value = "申请记录id")
	private String applyId;

	/**
	 * 审核状态
	 */
	@ApiModelProperty(value = "审核状态")
	private Integer approveStatus;

	/**
	 * 审核意见
	 */
	@ApiModelProperty(value = "审核意见")
	private String approveComment;
}
