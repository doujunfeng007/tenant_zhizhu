package com.minigod.zero.trade.hs.resp;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;

/**
 * 10102 查询最大可买
 *
 */
@Data
public class EF01110102VO implements Serializable {
    private static final long serialVersionUID = 1L;

	/**
	 * 可用数量
	 */
	@JSONField(name="enable_amount")
    private String enableAmount;
}
