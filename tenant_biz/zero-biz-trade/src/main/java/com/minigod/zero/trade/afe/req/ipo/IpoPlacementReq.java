package com.minigod.zero.trade.afe.req.ipo;

import com.alibaba.fastjson.annotation.JSONField;
import com.minigod.zero.trade.afe.req.CommonRequest;
import lombok.Data;

/**
 * @ClassName: com.minigod.zero.trade.afe.req.ipo.IpoPlacementReq
 * @Description:
 * @Author: linggr
 * @CreateDate: 2024/4/23 11:35
 * @Version: 1.0
 */
@Data
public class IpoPlacementReq extends CommonRequest {
	@JSONField(name = "MARKET")
	private String market;
	@JSONField(name = "INSTRUMENT_NO")
	private String instrumentNo;
	@JSONField(name = "QUANTITY")
	private String quantity;

	@JSONField(name ="CLIENT_ID")
	private String clientId;

	/**
	 * “F”=全额支付，“T”=保证金
	 */
	@JSONField(name = "FINANCING_FLAG")
	private String financingFlag;

	/**
	 * （“R”=按费率，“A”=按金额）
	 */
	@JSONField(name = "FINANCING_TYPE")
	private String financingType;

	/**
	 * 如果FINANCING_TYPE=“R”，这个值表示百分比(90%，否则表示融资金额
	 */
	@JSONField(name = "MARGIN_RATE_AMOUNT")
	private String marginRateAmount;

}
