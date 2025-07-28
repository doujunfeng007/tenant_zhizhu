package com.minigod.zero.trade.hs.req;

import com.minigod.zero.trade.hs.vo.GrmRequestVO;
import lombok.Data;

import java.io.Serializable;

/**
 * @author:yanghu.luo
 * @create: 2023-05-16 14:57
 * @Description: 获取孖展比率
 */
@Data
public class EF01110186Request extends GrmRequestVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 证券代码
	 */
	private String stockCode;
}
