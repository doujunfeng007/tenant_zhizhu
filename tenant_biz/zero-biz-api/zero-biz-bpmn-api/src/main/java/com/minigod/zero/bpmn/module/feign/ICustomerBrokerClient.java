package com.minigod.zero.bpmn.module.feign;

import com.minigod.zero.core.tool.api.R;
import org.apache.ibatis.annotations.Param;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

/**
 * @ClassName: com.minigod.zero.bpmn.module.feign.ICustomerBrokerClient
 * @Description:
 * @Author: linggr
 * @CreateDate: 2024/9/24 18:54
 * @Version: 1.0
 */
@FeignClient(value = "minigod-customer")
public interface ICustomerBrokerClient {

	/**
	 * 奖励入金接口
	 * 客户奖励金  合伙奖励金
	 * @return
	 */
	@PostMapping("/broker/fund/award_gold_deposit")
	R awardGoldDeposit(@RequestParam("startTime") Date startTime, @RequestParam("endTime")Date endTime) ;

	/**
	 * 累计资产奖励接口
	 * @param startTime
	 * @return
	 */
	@PostMapping("/broker/fund/award_property_deposit")
	R awardPropertyDeposit(@RequestParam("startTime") Date startTime);


}
