package com.minigod.zero.trade.afe.resp.ipo;

import lombok.Data;

import java.util.Date;

/**
 * @ClassName: com.minigod.zero.trade.afe.resp.ipo.IpoOrderDetailResp
 * @Description:
 * @Author: linggr
 * @CreateDate: 2024/4/23 10:22
 * @Version: 1.0
 */
@Data
public class IpoOrderDetailResp {
	private String MARKET;
	private String ORDER_NO;
	private String INSTRUMENT_NO;
	private String QUANTITY;
	private String CHANNEL;
	private String ORDER_AMOUNT;
	private String REJ_USER;
	private String STATUS;
	private String INPUT_DATETIME;
	private String TOTAL_PAYMENT;
	private String NAME;
	private String HANDLING_FEE;
	private String INPUT_BY;
	private String INTEREST;
	private String DEPOSIT_AMT;
}
