package com.minigod.zero.trade.hs.resp;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;

@Data
public class EF01110104VO implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 委托编号
	 */
	@JSONField(name="entrust_no")
    private String entrustNo;

	/**
	 * 委托时间
	 */
	@JSONField(name="init_date")
    private String entrustTime;
}
