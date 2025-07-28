package com.minigod.zero.system.feign;

import com.minigod.zero.core.tool.api.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/7/1 16:29
 * @description：
 */
@FeignClient(
	value = "minigod-broker",
	fallback = BrokerClientFallBack.class
)
public interface BrokerClient {
	String SELECT_CHANNEL_ID_BY_DEPT_ID_URL = "/broker_channel/dept";
	String ADD_BROKER_CHANNEL_BY_DEPT = "/broker_channel/addBrokerChannelByDept";

	String UPDATE_BROKER_USER_INFO_BY_BROKER_ID = "/broker_user_info/updateBrokerUser";

	@GetMapping(SELECT_CHANNEL_ID_BY_DEPT_ID_URL)
	R<List<Long>> selectChannelIdByDeptId(@RequestParam("deptId") String deptId);

	@GetMapping(ADD_BROKER_CHANNEL_BY_DEPT)
	R<Boolean> saveBrokerChannelByDept(@RequestParam("deptId") String deptId,@RequestParam("deptName") String deptName);

	@GetMapping(UPDATE_BROKER_USER_INFO_BY_BROKER_ID)
	R<Boolean> updateBrokerUserByBrokerId(@RequestParam("brokerId") Long brokerId, @RequestParam("deptId") String deptId);
}
