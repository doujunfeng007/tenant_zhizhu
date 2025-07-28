package com.minigod.zero.manage.feign;

import com.minigod.zero.core.launch.constant.AppConstant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 经纪席位数据 Feign接口类
 *
 * @author 掌上智珠
 * @since 2022-01-09
 */

@FeignClient(
	value = AppConstant.SERVICE_BIZ_OMS_NAME
)
public interface IHkBrokerInfoClient {

	String API_PREFIX = AppConstant.FEIGN_API_PREFIX + "/hkBrokerInfo";
	String DO_UPDATE_WORK = API_PREFIX + "/doUpdateWork";
	//String FIND_ALL_BROKER = API_PREFIX + "/find_all_broker";

	/**
	 * 更新经纪席位数据
	 */
	@GetMapping(DO_UPDATE_WORK)
	void doUpdateWork(@RequestParam("url") String url);

}
