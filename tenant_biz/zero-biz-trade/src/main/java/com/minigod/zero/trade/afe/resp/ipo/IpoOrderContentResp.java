package com.minigod.zero.trade.afe.resp.ipo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import org.apache.poi.ss.formula.functions.T;

import java.util.Date;
import java.util.List;

/**
 * @ClassName: com.minigod.zero.trade.afe.resp.ipo.IpoOrderContentResp
 * @Description:
 * @Author: linggr
 * @CreateDate: 2024/4/23 11:08
 * @Version: 1.0
 */
@Data
public class IpoOrderContentResp {
	@JSONField(name = "MARKET")
	private String market;

	@JSONField(name = "INSTRUMENT_NO")
	private String instrumentNo;

	@JSONField(name = "INSTRUMENT_NAME")
	private String instrumentName;

	@JSONField(name = "APPLICATION_DATE")
	private String applicationDate;

	@JSONField(name = "ALLOTMENT_DATE")
	private String allotmentDate;

	@JSONField(name = "REFUND_DATE")
	private String refundDate;

	@JSONField(name = "TRADING_DATE")
	private String tradingDate;

	@JSONField(name = "NUMBER_OF_INTEREST_DAYS")
	private String numberOfInterestDays;

	@JSONField(name = "SUBSCRIPTION_END_DATE_HKEX")
	private String subscriptionEndDateHkex;

	@JSONField(name = "SUBSCRIPTION_PRICE")
	private String subscriptionPrice;

	@JSONField(name = "LOT_SIZE")
	private String lotSize;

	@JSONField(name = "APPLY_END_DATE_FULLPAID")
	private String applyEndDateFullpaid;

	@JSONField(name = "HANDLE_FEE_FULLPAID")
	private String handleFeeFullpaid;
	/**
	 * 如果=T，意味着允许保证金eIPO，否则，保证金eIPO不允许，
	 * 如果这个字段=T，以下APPLY_END_DATE_FINANCING、HANDLE_FEE_FINANCING、
	 * MARGIN_DATE_MIN、MARGIN_RATE_MAX、MARGIN_AMT_MIN和MARGIN_AMT_MAX字段是有用的。否则，请简单地忽略这些字段
	 */
	@JSONField(name = "FINANICAL_FLAG")
	private String finanicalFlag;

	@JSONField(name = "APPLY_END_DATE_FINANCING")
	private String applyEndDateFinancing;

	@JSONField(name = "HANDLE_FEE_FINANCING")
	private String handleFeeFinancing;


	/**
	 * 如果=为0，则表示没有最小值
	 */
	@JSONField(name = "MARGIN_RATE_MIN")
	private String marginRateMin;

	/**
	 * 如果=为0，则表示没有最小值
	 */

	@JSONField(name = "MARGIN_RATE_MAX")
	private String marginRateMax;

	@JSONField(name = "MARGIN_AMT_MIN")
	private String marginAmtMin;

	@JSONField(name = "MARGIN_AMT_MAX")
	private String marginAmtMax;

	@JSONField(name = "RESULT")
	private IpoResultOrderListResp result;

	@JSONField(name = "STATUS")
	private String status;

	@JSONField(name = "Msg_ID")
	private String msgId;
}
