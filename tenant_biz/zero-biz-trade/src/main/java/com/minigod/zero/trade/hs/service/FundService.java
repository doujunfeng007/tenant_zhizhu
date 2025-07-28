/**
 * @Title: FundService.java
 * @Description: 查询基金资产信息
 * @author yanghu.luo
 * @date 2022年8月11日
 */

package com.minigod.zero.trade.hs.service;

import com.alibaba.fastjson.JSONObject;

import java.util.Map;

/**
 * @ClassName: FundService
 * @Description: 查询基金资产信息
 * @author yanghu.luo
 * @date 2022年8月11日
*/

public interface FundService {

	/**
	 * 获取基金不同币种下的可取金额
	 * @MethodName: getFundAvailableAsset
	 * @Description: 获取基金不同币种下的可取金额
	 * @author yanghu.luo
	 * @param userId
	 * @param fundAccount
	 * @return Map<String,JSONObject>
	 * @date 2022年8月12日
	 */
	Map<String,JSONObject> getFundAvailableAsset(Long userId,String fundAccount);
}

