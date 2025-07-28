package com.minigod.zero.data.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 订单按产品类型统计结果VO
 *
 * @author eric
 * @date 2024-11-04
 */
@Data
@ApiModel(value = "订单按产品类型统计结果")
public class OrderTotalCountVO {
	@ApiModelProperty(value = "产品类型(0:基金 64:活利得 65:债券易)")
	private Integer busitype;

	@ApiModelProperty(value = "买入订单数量(type=1或3)")
	private Integer buyCount;

	@ApiModelProperty(value = "卖出订单数量(type=2或4)")
	private Integer sellCount;

	@ApiModelProperty(value = "撤单数量(status=500或510)")
	private Integer cancelCount;

	@ApiModelProperty(value = "成交数量(status=300)")
	private Integer dealCount;

	@ApiModelProperty(value = "待成交(买入挂单)数量-status IN (230, 310)")
	private Integer pendingBuyCount;

	@ApiModelProperty(value = "待成交(卖出挂单)数量-status IN (230, 310)")
	private Integer pendingSellCount;

	@ApiModelProperty(value = "产品类型名称")
	private String busitypeName;

	public String getBusitypeName() {
		if (busitype == null) return "未知";
		switch (busitype) {
			case 0:
				return "基金";
			case 64:
				return "活利得";
			case 65:
				return "债券易";
			default:
				return "未知";
		}
	}
}
