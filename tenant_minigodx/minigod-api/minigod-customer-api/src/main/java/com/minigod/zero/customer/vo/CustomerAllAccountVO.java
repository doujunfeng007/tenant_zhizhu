package com.minigod.zero.customer.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author chen
 * @ClassName CustomerAllAccountVO.java
 * @Description TODO
 * @createTime 2024年12月13日 10:53:00
 */
@Data
public class CustomerAllAccountVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<CustomerTradeAccountVO> tradeAccountList;

	private List<CustomerTradeSubAccountVO> subAccountList;
}
