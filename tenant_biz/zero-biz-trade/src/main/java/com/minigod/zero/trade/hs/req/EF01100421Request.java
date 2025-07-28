package com.minigod.zero.trade.hs.req;

import com.minigod.zero.trade.hs.vo.GrmRequestVO;
import lombok.Data;

import java.io.Serializable;

/**
 * 421 查询历史委托
 * @author sunline
 *
 */
@Data
public class EF01100421Request extends GrmRequestVO implements Serializable {
	private static final long serialVersionUID = 53171133020057924L;
	//
	private String opStation;
	private String functionId;
	private String fundAccount;
	private String password;
	private String exchangeType;
	private String startDate;
	private String endDate;
	private String filterType = "1";//结果集过滤，参考HsConstants.EntrustFilterType
	private String requestNum;
	//增加状态查询
	private String entrustStatus;
	private String assetId;
}
