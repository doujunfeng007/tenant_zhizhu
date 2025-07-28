package com.minigod.zero.trade.hs.req;

import com.minigod.zero.trade.hs.vo.GrmRequestVO;
import lombok.Data;

import java.io.Serializable;

/**
 * 344 查询OQ上下限价格
 * @author sunline
 *
 */
@Data
public class EF01100344Request extends GrmRequestVO implements Serializable {

	private static final long serialVersionUID = 1L;
	private String exchangeType;
	private String entrustPrice;
	private String spreadCode = "1";// 价差代码
}
