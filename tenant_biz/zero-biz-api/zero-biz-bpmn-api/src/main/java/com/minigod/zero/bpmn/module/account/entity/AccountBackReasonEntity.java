package com.minigod.zero.bpmn.module.account.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.minigod.zero.core.mp.base.BaseEntity;
import com.minigod.zero.core.tenant.mp.TenantEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import java.time.LocalDateTime;


/**
 *  实体类
 *
 * @author Chill
 */
@Data
@TableName("open_account_back_reason")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "AccountBackReason对象", description = "")
public class AccountBackReasonEntity extends TenantEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 前端路由
	 */
	@ApiModelProperty(value = "前端路由")
	private String code;
	/**
	 * 退回原因
	 */
	@ApiModelProperty(value = "退回原因")
	private String content;
	/**
	 * 流水号
	 */
	@ApiModelProperty(value = "流水号")
	private String applicationId;
	/**
	 * 模块标题
	 */
	@ApiModelProperty(value = "模块标题")
	private String title;

}
