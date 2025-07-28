/**
 * @Title: FundServiceImpl.java
 * @Description: 查询基金资产信息
 * @author yanghu.luo
 * @date 2022年8月11日
 */

package com.minigod.zero.trade.hs.service;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: FundServiceImpl
 * @Description: 查询基金资产信息
 * @author yanghu.luo
 * @date 2022年8月11日
*/
@Slf4j
@Service
public class FundServiceImpl implements FundService {
//	@Resource
//	public DefaultConfig defaultConfig;
//    @Autowired
//    private SecuritiesUserInfoService securitiesUserInfoService;

	@Override
	public Map<String,JSONObject> getFundAvailableAsset(Long userId,String fundAccount) {
		Map<String,JSONObject> fundAvailableAsset = new HashMap<String,JSONObject>();
		if(userId == null){
			return fundAvailableAsset;
		}
// TODO
//    	SecuritiesUserInfoReqVo search = new SecuritiesUserInfoReqVo();
//    	search.setUserId(userId);
//    	search.setFundAccount(fundAccount);
//    	SecuritiesUserInfoRespVo userInfo = securitiesUserInfoService.selectSecuritiesUserInfo(search);
//    	if(null != userInfo && StringUtils.isNotBlank(userInfo.getYfFundAccount())) {
//    		String url = defaultConfig.getVal("yfund.service.host") + defaultConfig.getVal("yfund.api.asset") + "?type=1&yfsubacctid=" + userInfo.getYfFundAccount() + "&uin=" + userId;
//    		log.info("基金资产url:" + url);
//    		String fundResult = HttpClientUtils.get(url, Charset.forName("UTF-8"));
//			log.info("基金资产result:" + fundResult);
//    		if(StringUtils.isNotBlank(fundResult)){
//    			JSONObject rootObject = JSONObject.parseObject(fundResult);
//    			String data = rootObject.getString("data");
//    			if(StringUtils.isNotBlank(data)){
//    				JSONObject dataObject = JSONObject.parseObject(data);
//    				String fundAssets = dataObject.getString("fundAsset");
//    				if(StringUtils.isNotBlank(fundAssets)){
//    					JSONArray jsonArray = JSONArray.parseArray(fundAssets);
//    					for(int i = 0;i < jsonArray.size();i++) {
//    						String fundAsset = jsonArray.get(i).toString();
//    						if(StringUtils.isNotBlank(fundAsset)){
//    							JSONObject fundAssetObject = JSONObject.parseObject(jsonArray.get(i).toString());
//    							fundAvailableAsset.put(fundAssetObject.getString("currency"), fundAssetObject);
//    						}
//    					}
//    				}
//    			}
//    		}
//    	}
		return fundAvailableAsset;
	}

}
