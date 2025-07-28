package com.minigod.zero.trade.afe.resp.ipo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.Date;

/**
 * @ClassName: com.minigod.zero.trade.afe.req.ipo.OrderBookQueryReq
 * @Description:
 * @Author: linggr
 * @CreateDate: 2024/4/19 16:37
 * @Version: 1.0
 */
@Data
public class IpoOrderBookQueryResp extends IpoRespVO {
	@JSONField(name = "MARKET")
	private String market;

	@JSONField(name = "ORDER_NO")
	private String orderNo;

	@JSONField(name = "INSTRUMENT_NO")
	private String instrumentNo;

	@JSONField(name = "QUANTITY")
	private String quantity;

	@JSONField(name = "CHANNEL")
	private String channel;

	@JSONField(name = "ORDER_AMOUNT")
	private String orderAmount;

	@JSONField(name = "REJ_USER")
	private String rejUser;

	@JSONField(name = "STATUS")
	private String status;

	@JSONField(name = "INPUT_DATETIME")
	private Date inputDatetime;

	@JSONField(name = "TOTAL_PAYMENT")
	private String totalPayment;

}
