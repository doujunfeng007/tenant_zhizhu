package com.minigod.zero.trade.afe.req.ipo;

import com.alibaba.fastjson.annotation.JSONField;
import com.minigod.zero.trade.afe.req.CommonRequest;
import lombok.Data;

/**
 * @ClassName: com.minigod.zero.trade.afe.req.ipo.IpoOrderDetailQueryReq
 * @Description:
 * @Author: linggr
 * @CreateDate: 2024/4/23 10:09
 * @Version: 1.0
 */
@Data
public class IpoOrderDetailQueryReq extends CommonRequest {
	/**
	 * LINK_IPO_FLG 搜索股票，留空以搜索全部
	 */
	@JSONField(name ="INSTRUMENT_NO")
	private  String instrumentNo;
	/**
	 * 月份时间  整数
	 */
	@JSONField(name ="MONTH_PERIOD")
	private  String monthPeriod;

	/**
	 * 供未来开发使用
	 */
	@JSONField(name ="LINK_IPO_FLG")
	private  String linkIpoFlg;

	/**
	 * 客户ID
	 */
	@JSONField(name ="CLIENT_ID")
	private String clientId;

}
