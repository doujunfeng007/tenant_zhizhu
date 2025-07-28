package com.minigod.zero.trade.vo.sjmb.resp;

import lombok.Data;

import java.io.Serializable;

/**
 * @author chen
 * @ClassName FundFreezeVO.java
 * @Description TODO
 * @createTime 2024年03月19日 16:45:00
 */

@Data
public class FundFreezeVO implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 冻结业务编号
	 */
	private String businessNumber;
}
