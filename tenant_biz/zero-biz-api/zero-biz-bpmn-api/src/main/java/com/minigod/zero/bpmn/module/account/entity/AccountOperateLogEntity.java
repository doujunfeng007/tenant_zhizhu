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
@TableName("open_account_operate_log")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "AccountOperateLog对象", description = "")
public class AccountOperateLogEntity extends TenantEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 流程节点
	 */
	@ApiModelProperty(value = "流程节点")
	private String currentNode;
	/**
	 * 操作内容
	 */
	@ApiModelProperty(value = "操作内容")
	private String updateContent;
	/**
	 * 操作前
	 */
	@ApiModelProperty(value = "操作前")
	private String operatePre;
	/**
	 * 操作后
	 */
	@ApiModelProperty(value = "操作后")
	private String operateAfter;
	/**
	 * 流水号
	 */
	@ApiModelProperty(value = "流水号")
	private String applicationId;

}
