
package com.minigod.zero.log.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.log.model.LogErrorDTO;
import com.minigod.zero.log.model.LogErrorVO;
import lombok.AllArgsConstructor;
import com.minigod.zero.log.service.ILogErrorService;
import com.minigod.zero.core.mp.support.Query;
import com.minigod.zero.core.tenant.annotation.NonDS;
import com.minigod.zero.core.tool.api.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 异常日志控制器
 *
 * @author Chill
 */
@NonDS
@RestController
@AllArgsConstructor
@RequestMapping("/api-error-log")
public class LogErrorController {

	private final ILogErrorService errorLogService;

	/**
	 * 查询单条
	 */
	@GetMapping("/detail")
	public R<LogErrorVO> detail(Long id) {
		return R.data(errorLogService.getLogDetail(id));
	}

	/**
	 * 查询多条(分页)
	 */
	@GetMapping("/list")
	public R<IPage<LogErrorVO>> list(LogErrorDTO logError, Query query) {
		IPage<LogErrorVO> pages = errorLogService.selectLogPage(query, logError);
		return R.data(pages);
	}

	/**
	 * 按时间区间获取日志列表
	 */
	@GetMapping("/list-time")
	public R<List<LogErrorVO>> listAll(@RequestParam("startTime") String startTime, @RequestParam("endTime") String endTime) {
		LogErrorDTO apiDTO = new LogErrorDTO();
		apiDTO.setStartTime(startTime);
		apiDTO.setEndTime(endTime);
		List<LogErrorVO> list = errorLogService.selectLogList(apiDTO);
		return R.data(list);
	}

	/**
	 * 导出
	 *
	 * @param response
	 * @param logError
	 */
	@GetMapping("/export")
	public void export(HttpServletResponse response, LogErrorDTO logError) {
		errorLogService.exportLog(response, logError);
	}
}
