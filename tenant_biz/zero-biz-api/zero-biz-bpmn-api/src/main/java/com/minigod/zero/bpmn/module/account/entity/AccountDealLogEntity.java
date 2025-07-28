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
@TableName("open_account_deal_log")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "AccountDealLog对象", description = "")
public class AccountDealLogEntity extends TenantEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 流水号
	 */
	@ApiModelProperty(value = "流水号")
	private String applicationId;
	/**
	 * 系统用户ID
	 */
	@ApiModelProperty(value = "系统用户ID")
	private Long userId;
	/**
	 * 节点
	 */
	@ApiModelProperty(value = "节点")
	private String node;
	/**
	 * 处理时间
	 */
	@ApiModelProperty(value = "处理时间")
	private Date dealTime;
	/**
	 * 处理结果
	 */
	@ApiModelProperty(value = "处理结果")
	private String result;
	/**
	 * 原因
	 */
	@ApiModelProperty(value = "原因")
	private String reason;

}
