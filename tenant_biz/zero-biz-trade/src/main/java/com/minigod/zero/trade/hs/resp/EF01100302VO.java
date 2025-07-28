package com.minigod.zero.trade.hs.resp;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by sunline
 * Date:4/9/16
 * Time:10:12 PM
 */
@Data
public class EF01100302VO  implements Serializable {
    private static final long serialVersionUID = 1L;

	/**
	 * 委托编号
	 */
	@JSONField(name="entrust_no")
    private String entrustNo;

	/**
	 * 市场交易日期
	 */
	@JSONField(name="init_date")
    private String initDate;
}
