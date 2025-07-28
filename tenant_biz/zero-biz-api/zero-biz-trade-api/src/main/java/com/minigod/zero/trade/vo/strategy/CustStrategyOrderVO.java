package com.minigod.zero.trade.vo.strategy;

import com.minigod.zero.trade.entity.CustStrategyOrderEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 条件单信息表 视图实体类
 *
 * @author 云锋有鱼
 * @since 2023-05-18
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CustStrategyOrderVO extends CustStrategyOrderEntity {
	private static final long serialVersionUID = 1L;

	/**
	 * 证券类别，下单时判断权限使用，不存表里
	 */
	@ApiModelProperty(value = "证券类别，下单时判断权限使用，不存表里")
	private String stockType;

	private String orderNo;
}
