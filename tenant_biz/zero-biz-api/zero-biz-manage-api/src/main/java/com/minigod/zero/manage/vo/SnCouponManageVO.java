package com.minigod.zero.manage.vo;

import com.minigod.zero.manage.entity.SnCouponManageEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 批量兑换码列表
 *
 * @author eric
 * @since 2024-12-27 10:57:01
 */
@Data
@ApiModel(description = "优惠券管理VO")
public class SnCouponManageVO extends SnCouponManageEntity implements Serializable {
	private static final long serialVersionUID = -2260388125919493487L;
	@ApiModelProperty(value = "是否使用(0:未使用 1:已使用 2:已过期 3:已作废)")
	private Integer useStatus;
}
