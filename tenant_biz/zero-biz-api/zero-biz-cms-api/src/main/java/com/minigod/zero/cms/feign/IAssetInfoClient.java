package com.minigod.zero.cms.feign;

import com.minigod.zero.cms.entity.AssetInfoEntity;
import com.minigod.zero.core.launch.constant.AppConstant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 股票码表 Feign接口类
 *
 * @author 掌上智珠
 * @since 2022-11-17
 */

@FeignClient(
	value = AppConstant.SERVICE_BIZ_CMS_NAME
)
public interface IAssetInfoClient {

	String API_PREFIX = AppConstant.FEIGN_API_PREFIX + "/assetInfo";
	String TEST = API_PREFIX + "/test";
	String FIND_ASSET_INFO = API_PREFIX + "/findAssetInfo";

	/**
	 * 通过股票ID查询 股票信息
	 *
	 * @param assetId
	 * @return
	 */
	@GetMapping(FIND_ASSET_INFO)
    AssetInfoEntity findAssetInfo(@RequestParam String assetId);
}
