package com.minigod.zero.manage.controller;

import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.tool.api.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 李长春
 * @email cloud@bdie.com.cn
 * @WeChat 138264386257
 * @date 2022/11/9
 */
@Slf4j
@RestController
@RequestMapping(AppConstant.BACK_API_PREFIX)
public class RedisTestController {

	@GetMapping("info")

	public R<String> testInfo(String name) {
		log.info("缓存无数据，重新获取");
		return R.data("Hello, My Name is: " + name);
	}

}
