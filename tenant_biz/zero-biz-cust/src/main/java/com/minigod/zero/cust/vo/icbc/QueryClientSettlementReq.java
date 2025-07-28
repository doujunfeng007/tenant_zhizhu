package com.minigod.zero.cust.vo.icbc;

import lombok.Data;

import java.io.Serializable;

/**
 * @author:yanghu.luo
 * @create: 2023-03-08 14:44
 * @Description: 获取未绑定结算账户
 */
@Data
public class QueryClientSettlementReq implements Serializable {
	private static final long serialVersionUID = 1L;
	private String ci_no;
}
