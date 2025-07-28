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
@TableName("customer_account_open_agreement")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "AccountOpenAgreement对象", description = "")
public class AccountOpenAgreementEntity extends TenantEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 开户流水号
	 */
	@ApiModelProperty(value = "开户流水号")
	private String applicationId;
	/**
	 * 协议ID
	 */
	@ApiModelProperty(value = "协议ID")
	private Long agreementId;

}
