package com.minigod.zero.biz.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MktInfoUtil {

	//@Resource
	//private ZeroRedis zeroRedis;

	/**
	 * 判断当前是否是暗盘
	 * @param assetId
	 * @return
	 */
	/*public boolean isConfidentialMark(String assetId) {
		if (assetId.endsWith(EMarket.HK.toString())) {
			StkInfo stkInfo = zeroRedis.protoGet(assetId, StkInfo.class);
			if (stkInfo == null) return false;
			String listingDateStr = stkInfo.getListingDate();
			if (StringUtil.isBlank(listingDateStr)) return false;
			Date listingDate = DateUtil.parseDate(listingDateStr);
			if (assetId.endsWith(EMarket.HK.toString())) {
				StkTrdCale stkTrdCale = getStkTrdCaleByAssetId(assetId, null);
				if (stkTrdCale == null) {
					return false;
				}
				Date confidentialDate =  stkTrdCale.getNextTrd();
				if (listingDate.compareTo(confidentialDate) == 0) {
					return true;
				}
			}
			return false;
		}
		return false;
	}*/

	/**
	 * 获取交易日
	 * @param assetId
	 * @return
	 */
	/*public StkTrdCale getStkTrdCaleByAssetId(String assetId, Date listedDate) {
		if (assetId.endsWith(EMarket.HK.toString())) {
			if (listedDate == null) listedDate = new Date();
			String curDateStr = DateUtil.formatDate(listedDate);
			String key = curDateStr + "_" + EMarket.HK;
			return zeroRedis.protoGet(key, StkTrdCale.class);
		}
		return null;
	}*/
}
