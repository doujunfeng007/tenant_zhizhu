package com.minigod.zero.trade.feign;

import com.minigod.zero.trade.entity.IpoPredictConfigEntity;
import com.minigod.zero.core.launch.constant.AppConstant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author:yanghu.luo
 * @create: 2023-04-26 10:14
 * @Description: IPO相关接口
 */
@FeignClient(
	value = AppConstant.SERVICE_BIZ_TRADE_NAME
)
public interface IIpoClient {
	String API_PREFIX = AppConstant.FEIGN_API_PREFIX + "/ipo";
	String GET_IPO_CONFIG = API_PREFIX + "/getIpoConfig";

	/**
	 *  交易账号登录校验
	 */
	@PostMapping(GET_IPO_CONFIG)
	List<IpoPredictConfigEntity> getIpoConfig(@RequestBody IpoPredictConfigEntity ipoPredictConfigGet);
}
