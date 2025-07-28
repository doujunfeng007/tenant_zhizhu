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
@TableName("open_account_property_type")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "AccountPropertyType对象", description = "")
public class AccountPropertyTypeEntity extends TenantEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 预约流水号
	 */
	@ApiModelProperty(value = "预约流水号")
	private String applicationId;
	/**
	 * 财产种类
	 */
	@ApiModelProperty(value = "财产种类")
	private Integer propertyType;
	/**
	 *
	 */
	@ApiModelProperty(value = "")
	private Integer propertyAmount;

}
