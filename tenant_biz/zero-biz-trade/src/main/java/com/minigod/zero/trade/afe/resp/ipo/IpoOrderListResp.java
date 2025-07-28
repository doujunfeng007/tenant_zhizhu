package com.minigod.zero.trade.afe.resp.ipo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @ClassName: com.minigod.zero.trade.afe.resp.ipo.IpoOrderListResp
 * @Description:
 * @Author: linggr
 * @CreateDate: 2024/4/23 11:04
 * @Version: 1.0
 */
@Data
public class IpoOrderListResp {
	@JSONField(name = "QTY")
	private String qty;
	@JSONField(name = "PRICE")
	private String price;
	@JSONField(name = "STATUS")
	private String status;
	@JSONField(name = "Msg_ID")
	private String msgId;
}
