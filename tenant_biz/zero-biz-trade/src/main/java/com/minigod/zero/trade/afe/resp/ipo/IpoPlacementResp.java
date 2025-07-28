package com.minigod.zero.trade.afe.resp.ipo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @ClassName: com.minigod.zero.trade.afe.resp.ipo.IpoPlacementResp
 * @Description:
 * @Author: linggr
 * @CreateDate: 2024/4/23 13:16
 * @Version: 1.0
 */
@Data
public class IpoPlacementResp extends IpoRespVO{
	@JSONField(name = "ORDER_NO")
	private String orderNo;

	/**
	 * 申购金额
	 */
	@JSONField(name = "AMOUNT")
	private String amount;

	/**
	 * 手续费
	 */
	@JSONField(name = "HANDLING_FEE")
	private String handlingFee;

	@JSONField(name = "STATUS")
	private String status;//OK

	@JSONField(name = "Msg_ID")
	private String msgId;
}
