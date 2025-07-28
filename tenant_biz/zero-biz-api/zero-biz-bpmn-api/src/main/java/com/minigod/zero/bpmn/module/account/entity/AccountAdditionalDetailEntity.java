package com.minigod.zero.bpmn.module.account.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.minigod.zero.core.tenant.mp.TenantEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


/**
 *  开户补充资料信息备注表
 *
 * @author Chill
 */
@Data
@TableName("open_account_additional_detail")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "AccountAdditionalDetail对象", description = "")
public class AccountAdditionalDetailEntity extends TenantEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 预约流水号
	 */
	@ApiModelProperty(value = "预约流水号")
	private String applicationId;
	/**
	 * 备注与文件的关联ID（UUID）
	 */
	@ApiModelProperty(value = "备注与文件的关联ID（UUID）")
	private String additionalId;
	/**
	 * 备注
	 */
	@ApiModelProperty(value = "备注")
	private String remark;

}
