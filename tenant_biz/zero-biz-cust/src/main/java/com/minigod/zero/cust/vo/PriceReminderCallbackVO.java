package com.minigod.zero.cust.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
public class PriceReminderCallbackVO implements Serializable {
	private static final long serialVersionUID = 1L;
	// 标题
	private Integer templateCode;
	private String tenantId;
	private Date sendTime;
	private List<Long> lstToUserId;//客户Id(列表)
	private String setValue;
	private Map<String, Object> paramMap;
}
