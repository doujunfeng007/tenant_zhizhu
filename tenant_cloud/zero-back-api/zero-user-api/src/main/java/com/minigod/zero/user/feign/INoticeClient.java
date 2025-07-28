package com.minigod.zero.user.feign;

import com.minigod.zero.user.entity.Notice;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.mp.support.ZeroPage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Notice Feign接口类
 *
 * @author Chill
 */
@FeignClient(
	value = AppConstant.APPLICATION_USER_NAME
)
public interface INoticeClient {

	String TOP = AppConstant.FEIGN_API_PREFIX  + "/top";

	/**
	 * 获取notice列表
	 *
	 * @param current
	 * @param size
	 * @return
	 */
	@GetMapping(TOP)
	ZeroPage<Notice> top(@RequestParam("current") Integer current, @RequestParam("size") Integer size);

}
