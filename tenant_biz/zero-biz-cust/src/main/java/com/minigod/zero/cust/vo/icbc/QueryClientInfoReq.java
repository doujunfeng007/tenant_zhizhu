package com.minigod.zero.cust.vo.icbc;

import lombok.Data;

import java.io.Serializable;

/**
 * @author:yanghu.luo
 * @create: 2023-03-08 16:29
 * @Description: 查询客户信息
 */
@Data
public class QueryClientInfoReq implements Serializable {
	private static final long serialVersionUID = 1L;
	private String ci_no;
}
