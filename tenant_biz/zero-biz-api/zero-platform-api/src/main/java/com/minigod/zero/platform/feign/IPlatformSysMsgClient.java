package com.minigod.zero.platform.feign;

import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.platform.entity.PlatformSysMsgEntity;
import com.minigod.zero.platform.vo.PlatformSysMsgVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(
	value = AppConstant.APPLICATION_PLATFORM_NAME
)
public interface IPlatformSysMsgClient {

	String FIND_INFORM_SYS_MSG = AppConstant.FEIGN_API_PREFIX + "/findInformSysMsg";
	String PUSH_UNSEND_SYS_MSG = AppConstant.FEIGN_API_PREFIX + "/pushUnsendSysMsg";
	String SAVE_SYS_MSG = AppConstant.FEIGN_API_PREFIX + "/saveSysMsg";

	/**
	 * 查询系统消息
	 *
	 * @param userId
	 * @param count
	 * @param lngVersion
	 * @return
	 */
	@GetMapping(FIND_INFORM_SYS_MSG)
	List<PlatformSysMsgEntity> findInformSysMsg(@RequestParam Long lngVersion, @RequestParam Integer count, @RequestParam Long userId);

	/**
	 * 定时发送系统消息
	 * @return
	 */
	@GetMapping(PUSH_UNSEND_SYS_MSG)
	R pushUnsendSysMsg();

	/**
	 * 保存
	 *
	 * @param entity
	 * @return
	 */
	@PostMapping(SAVE_SYS_MSG)
	Boolean saveSysMsg(@RequestBody PlatformSysMsgVO entity);
}
