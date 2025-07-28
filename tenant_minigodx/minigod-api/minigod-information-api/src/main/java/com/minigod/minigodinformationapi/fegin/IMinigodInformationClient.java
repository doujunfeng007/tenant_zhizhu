package com.minigod.minigodinformationapi.fegin;

import com.minigod.zero.core.tool.api.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/10/28 10:32
 * @description：定时任务处理
 */
@FeignClient(value = "minigod-information")
public interface IMinigodInformationClient {
	String SY_INFORMATION_DATA_URL = "/information/sy_information_data";

	/**
	 * 定时任务处理  同步信息披露数据
	 * @return
	 */
	@GetMapping(SY_INFORMATION_DATA_URL)
	R  syInformationData(@RequestParam("startTime") Date startTime, @RequestParam("endTime") Date endTime);

}
