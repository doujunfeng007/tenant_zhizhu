package com.minigod.zero.trade.hs.resp;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;

/**
 * 10101 查询最大可卖
 *
 */
@Data
public class EF01110101VO implements Serializable {
    private static final long serialVersionUID = 1L;

	/**
	 * 可用数量
	 */
	@JSONField(name="enable_amount")
    private String enableAmount;
}
