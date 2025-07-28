package com.minigod.zero.bpmn.module.account.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.minigod.zero.core.tenant.mp.TenantEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *  客户开户资料修改申请记录表
 *
 * @author eric
 * @since 2024-08-02 19:01:01
 */
@Data
@TableName("customer_open_info_modify_apply")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "AccountOpenInfoModifyApply对象", description = "客户开户资料修改申请记录表")
public class AccountOpenInfoModifyApplyEntity extends TenantEntity {
	private static final long serialVersionUID = 1L;
	/**
	 * 预约流水号
	 */
	@ApiModelProperty(value = "预约流水号")
	private String applicationId;

	/**
	 * 用户ID
	 */
	@ApiModelProperty(value = "用户ID")
	private Long userId;

	/**
	 * 用户名称
	 */
	@ApiModelProperty(value = "用户名称")
	private String userName;

	/**
	 * 申请记录标题
	 */
	@ApiModelProperty(value = "申请记录标题")
	private String applyTitle;

	/**
	 * 修改数据标识
	 */
	@ApiModelProperty(value = "修改数据标识")
	private String dataSection;

	/**
	 * 开户资料更改状态[0-待审核 1-审核通过 2-审核不通过]
	 */
	@ApiModelProperty(value = "审核状态[0-待审核 1-审核通过 2-审核不通过]")
	private Integer approveStatus;

	/**
	 * 审核人ID
	 */
	@ApiModelProperty(value = "审核人ID")
	private Long approveUserId;

	/**
	 * 审核人名称
	 */
	@ApiModelProperty(value = "审核人名称")
	private String approveUserName;

	/**
	 * 审核意见
	 */
	@ApiModelProperty(value = "审核意见")
	private String approveComment;

	/**
	 * 更新状态[0-待更新 1-更新成功 2-更新失败]
	 */
	@ApiModelProperty(value = "更新状态[0-待更新 1-更新成功 2-更新失败]")
	private Integer updateStatus;

	/**
	 * 更新操作结果
	 */
	@ApiModelProperty(value = "更新操作结果")
	private String updateResult;

	/**
	 * 同步状态[0-待同步 1-同步成功 2-同步失败]
	 */
	@ApiModelProperty(value = "同步状态[0-待同步 1-同步成功 2-同步失败]")
	private Integer syncStatus;

	/**
	 * 同步操作结果
	 */
	@ApiModelProperty(value = "同步操作结果")
	private String syncResult;
}
