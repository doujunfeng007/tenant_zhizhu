package com.minigod.zero.biz.common.vo.mkt;

import com.minigod.zero.biz.common.mkt.cache.ApplyCommonVO;
import lombok.Data;

/**
 * 新股日历 待上市列表信息
 */

@Data
public class ApplyWaitListingVO extends ApplyCommonVO {

	/** 招股价上限 */
	private String priceCeiling;
	/** 招股价下限 */
	private String priceFloor;
	/** 招股截止日 */
	private String endDate;
	/** 上市日期 */
	private String listingDate;
	/** 证券类别 */
	private Integer secType;
	/** 细分类别 */
	private Integer secsType;
	/** 暗盘日期 */
	private String cfDate;
	/** 是否今天暗盘 */
	private Boolean todayIsCf = false;
	/** 现价 */
	private String confidentialPrice;
	/** 涨跌额 */
	private String confidentialChange;
	/** 涨跌幅 */
	private String confidentialChangePct;
	/** 每手股数 */
	private String shares;
	/** 最低入场金额 */
	private String entranceMin;
	/** 认购倍数 */
	private String subscribed;
	/** 一手中签率 */
	private String codesRate;
	/** 认购状态 -1.无 0.去认购 1.可撤回 2.取消排队 3.已认购 */
	private Integer applyStatus;
	/**  结果公布日*/
	private String resultDate;
}
