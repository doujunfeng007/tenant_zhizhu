package com.minigod.zero.bpmn.module.stock.domain.bo;

import lombok.Data;

/**
 * @author chen
 * @ClassName StockQueryInfoBO.java
 * @Description TODO
 * @createTime 2024年12月17日 19:36:00
 */
@Data
public class StockQueryInfoBO implements java.io.Serializable{
	private static final long serialVersionUID = 1L;

	private String phoneArea;
	private String phoneNumber;
}
