package com.minigod.zero.bpmn.module.feign;

import com.minigod.zero.bpmn.module.feign.dto.OfflineOpenAccountDTO;
import com.minigod.zero.core.tool.api.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

/**
 * 线下开户资料回传给账户中心
 *
 * @author eric
 * @since 2024-06-13 15:37:15
 */
@FeignClient(value = "minigod-customer")
public interface IOfflineAccountOpenInfoClient {
	String OFFLINE_OPEN_ACCOUNT = "/register";
	/**
	 * 开户提交之前注册
	 *
	 * @param offlineOpenAccountDTO
	 * @return
	 */
	@PostMapping(OFFLINE_OPEN_ACCOUNT)
	R<Map<String, Object>> customerRegister(@RequestBody OfflineOpenAccountDTO offlineOpenAccountDTO);
}
