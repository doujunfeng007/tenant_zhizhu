package com.minigod.zero.biz.common.mkt.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 条件单
 *
 * @author 掌上智珠
 * @since 2023-05-18
 */
@Data
public class StrategyOrderVO {
    /**
     * 资产ID
     */
    private String assetId;
    /**
     * 触发价格
     */
    private String strategyPrice;
    /**
     * 1.上涨到触发价格触发,2.下跌到触发价格触发,3.触碰到价格后触发
     */
    private String strategyAction;
    /**
     * 有效期 1-当日有效，2-撤单前有效
     */
    private String deadlineDate;
    /**
     * 委托编号
     */
    private String entrustNo;
	/**
	 * 过期时间
	 */
	private Long expSeconds;
	/**
	 * 订单状态：C0.待触发,C1.已取消(撤单),C2.已失效,C3.已触发,C4.已失败
	 */
	@ApiModelProperty(value = "订单状态：C0.待触发,C1.已取消(撤单),C2.已失效,C3.已触发,C4.已失败")
	private String entrustStatus;
	/**
	 * 创建时间
	 */
	private String createTime;
	/**
	 * 是否允许盘前盘后触发：0否，1是
	 */
	@ApiModelProperty(value = "是否允许盘前盘后触发：0否，1是")
	private String discType;

	/**
	 *
	 * 租户ID
	 */
	private String tenantId;

	private String orderNo;

}
