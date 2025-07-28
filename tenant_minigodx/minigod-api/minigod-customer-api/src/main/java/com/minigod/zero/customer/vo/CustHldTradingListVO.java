package com.minigod.zero.customer.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName: com.minigod.zero.customer.vo.CustHldTradingListVO
 * @Description:
 * @Author: linggr
 * @CreateDate: 2024/5/9 16:05
 * @Version: 1.0
 */
@Data
public class CustHldTradingListVO extends TradingPageVO{

	@ApiModelProperty(value="type类型,1:买;2:卖;3:交换买;4:交换卖;")
	private Integer type;

	/**
	 * 状态 ,状态 100 已保存；200 已提交；230 已发送（ta）；270 已确认；300 已结算；500 已撤销；720 过期作废
	 */
	@ApiModelProperty(value="status状态 ,100:PENDING;200:SUBMITTED;211:AUTHORIZED;221:POOLED;230:PLACED;270:CONFIRMED;300:SETTLED;400:REJECTED;500:CANCELED;600:FAILED;700:SUCCESS;")
	private Integer status;
}
