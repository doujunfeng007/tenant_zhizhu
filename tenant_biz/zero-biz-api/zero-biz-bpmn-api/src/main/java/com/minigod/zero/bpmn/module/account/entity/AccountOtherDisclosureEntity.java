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
@TableName("open_account_other_disclosure")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "AccountOtherDisclosure对象", description = "")
public class AccountOtherDisclosureEntity extends TenantEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 预约流水号
	 */
	@ApiModelProperty(value = "预约流水号")
	private String applicationId;
	/**
	 * 编号
	 */
	@ApiModelProperty(value = "编号")
	private Integer disclosureCode;
	/**
	 * 标示符[0-否 1-是]
	 */
	@ApiModelProperty(value = "标示符[0-否 1-是]")
	private Integer disclosureIsTrue;
	/**
	 * 披露字段1,多组信息逗号隔开
	 */
	@ApiModelProperty(value = "披露字段1,多组信息逗号隔开")
	private String disclosure1;
	/**
	 * 披露字段2,多组信息逗号隔开
	 */
	@ApiModelProperty(value = "披露字段2,多组信息逗号隔开")
	private String disclosure2;
	/**
	 * 披露字段3,多组信息逗号隔开
	 */
	@ApiModelProperty(value = "披露字段3,多组信息逗号隔开")
	private String disclosure3;
	/**
	 * 披露字段4,多组信息逗号隔开
	 */
	@ApiModelProperty(value = "披露字段4,多组信息逗号隔开")
	private String disclosure4;

}
