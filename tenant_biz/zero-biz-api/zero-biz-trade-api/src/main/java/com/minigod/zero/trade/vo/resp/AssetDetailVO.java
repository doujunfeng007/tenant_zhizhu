package com.minigod.zero.trade.vo.resp;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author:yanghu.luo
 * @create: 2023-05-15 19:02
 * @Description: 各市场资金明细
 */
@Data
public class AssetDetailVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 分市场资产
	 */
	private List<EF01110003VO> assetDetails;
	/**
	 * 保证金比例
	 */
	private EF01110180VO bondRatio;
}
