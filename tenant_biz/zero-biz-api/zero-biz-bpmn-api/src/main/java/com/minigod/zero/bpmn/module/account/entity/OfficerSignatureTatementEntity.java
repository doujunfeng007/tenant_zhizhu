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
@TableName("account_officer_signature_tatement")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "OfficerSignatureTatement对象", description = "")
public class OfficerSignatureTatementEntity extends TenantEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 预约流水号
	 */
	@ApiModelProperty(value = "预约流水号")
	private String applicationId;
	/**
	 * 认识客户年期[1-少于一年 2-一至五年 3-多于五年]
	 */
	@ApiModelProperty(value = "认识客户年期[1-少于一年 2-一至五年 3-多于五年]")
	private Integer meetCustomerYears;
	/**
	 * 介绍模式[1-客户自荐 2-有其他客户引荐 99-其他]
	 */
	@ApiModelProperty(value = "介绍模式[1-客户自荐 2-有其他客户引荐 99-其他]")
	private Integer introduceModel;
	/**
	 * 推荐人账户
	 */
	@ApiModelProperty(value = "推荐人账户")
	private String refereesAccount;
	/**
	 * 其他介绍模式具体描述
	 */
	@ApiModelProperty(value = "其他介绍模式具体描述")
	private String introduceModelOther;

}
