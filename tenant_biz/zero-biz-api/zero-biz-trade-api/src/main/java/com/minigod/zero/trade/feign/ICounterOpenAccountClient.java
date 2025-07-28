package com.minigod.zero.trade.feign;

import com.minigod.zero.trade.vo.sjmb.req.ModifyAccountReq;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.trade.vo.sjmb.req.OpenAccountReq;
import com.minigod.zero.trade.vo.sjmb.resp.OpenAccountVO;

import feign.Request;

/**
 * @author:yanghu.luo
 * @create: 2023-04-26 10:14
 * @Description: 恒生登陆相关接口
 */
@FeignClient(
	value = AppConstant.SERVICE_BIZ_TRADE_NAME,
	configuration = ICounterOpenAccountClient.FeignConfig.class
)
public interface ICounterOpenAccountClient {
	String API_PREFIX = AppConstant.FEIGN_API_PREFIX + "/account";
	String OPEN_ACCOUNT = API_PREFIX + "/open_account";
	String UPDATE_ACCOUNT = API_PREFIX + "/update_account";




	/**
	 *  开户
	 */
	@PostMapping(value =OPEN_ACCOUNT)
	R<OpenAccountVO> openAccount(@RequestBody OpenAccountReq openAccountReq);

	/**
	 * 账户资料修改
	 */
	@PostMapping(value = UPDATE_ACCOUNT)
	R updateAccount(@RequestBody ModifyAccountReq modifyAccountReq);

	@Configuration
	class FeignConfig {
		@Bean
		public Request.Options requestOptions() {
			return new Request.Options(60000, 60000); // 设置连接和读取超时时间
		}
	}


}
