package com.minigod.zero.customer.api.controller;

import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.customer.api.service.DataxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: com.minigod.zero.customer.api.controller.JobDataController
 * @Description:
 * @Author: linggr
 * @CreateDate: 2024/5/6 20:24
 * @Version: 1.0
 */
@RestController
@RequestMapping("/job")
public class JobDataController {
	@Autowired
	private DataxService dataxService;

	/**
	 * datax运行情况
	 * @return
	 */
	@GetMapping("/datax/detail")
	public R DataxDetail(){
		return dataxService.DataxDetail();

	}
}
