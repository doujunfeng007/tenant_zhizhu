
package com.minigod.zero.log.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.log.model.LogUsualDTO;
import com.minigod.zero.log.model.LogUsualVO;
import com.minigod.zero.log.service.ILogUsualService;
import lombok.AllArgsConstructor;
import com.minigod.zero.core.mp.support.Query;
import com.minigod.zero.core.tenant.annotation.NonDS;
import com.minigod.zero.core.tool.api.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * 通用日志控制器
 *
 * @author Chill
 */
@NonDS
@RestController
@AllArgsConstructor
@RequestMapping("/api-usual-log")
public class LogUsualController {

	private final ILogUsualService logUsualService;

	/**
	 * 查询单条
	 */
	@GetMapping("/detail")
	public R<LogUsualVO> detail(Long id) {
		return R.data(logUsualService.getLogDetail(id));
	}

	/**
	 * 查询多条(分页)
	 */
	@GetMapping("/list")
	public R<IPage<LogUsualVO>> list(LogUsualDTO logUsual, Query query) {
		IPage<LogUsualVO> pages = logUsualService.selectLogPage(query, logUsual);
		return R.data(pages);
	}

	/**
	 * 导出
	 *
	 * @param response
	 * @param logUsual
	 */
	@GetMapping("/export")
	public void export(HttpServletResponse response, LogUsualDTO logUsual) {
		logUsualService.exportLog(response, logUsual);
	}

}
