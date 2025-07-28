
package com.minigod.zero.log.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.log.model.LogApiDTO;
import com.minigod.zero.log.model.LogApiVO;
import lombok.AllArgsConstructor;
import com.minigod.zero.log.service.ILogApiService;
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
 * API 日志
 *
 * @author Chill
 */
@NonDS
@RestController
@AllArgsConstructor
@RequestMapping("/api-log")
public class LogApiController {

	private final ILogApiService logService;

	/**
	 * 查询单条
	 */
	@GetMapping("/detail")
	public R<LogApiVO> detail(Long id) {
		return R.data(logService.getLogDetail(id));
	}

	/**
	 * 查询多条(分页)
	 */
	@GetMapping("/list")
	public R<IPage<LogApiVO>> list(LogApiDTO logDTO, Query query) {
		IPage<LogApiVO> pages = logService.selectLogPage(query, logDTO);
		return R.data(pages);
	}

	/**
	 * 获取日志列表
	 */
	@GetMapping("/list-all")
	public R<List<LogApiVO>> listAll(LogApiDTO apiDTO) {
		List<LogApiVO> list = logService.selectLogList(apiDTO);
		return R.data(list);
	}

	/**
	 * 按时间区间获取日志列表
	 */
	@GetMapping("/list-time")
	public R<List<LogApiVO>> listAll(@RequestParam("startTime") String startTime, @RequestParam("endTime") String endTime) {
		LogApiDTO apiDTO = new LogApiDTO();
		apiDTO.setStartTime(startTime);
		apiDTO.setEndTime(endTime);
		List<LogApiVO> list = logService.selectLogList(apiDTO);
		return R.data(list);
	}

	/**
	 * 导出
	 *
	 * @param response
	 * @param apiDTO
	 */
	@GetMapping("/export")
	public void export(HttpServletResponse response, LogApiDTO apiDTO) {
		logService.exportLog(response, apiDTO);
	}

}
