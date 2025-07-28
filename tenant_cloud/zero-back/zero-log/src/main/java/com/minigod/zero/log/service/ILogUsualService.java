
package com.minigod.zero.log.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.minigod.zero.core.log.model.LogUsual;
import com.minigod.zero.core.mp.support.Query;
import com.minigod.zero.log.model.LogUsualDTO;
import com.minigod.zero.log.model.LogUsualVO;

import javax.servlet.http.HttpServletResponse;

/**
 * 服务类
 *
 * @author Chill
 */
public interface ILogUsualService extends IService<LogUsual> {
	/**
	 * 分页查询Usual-Log
	 *
	 * @param query
	 * @param usualDTO
	 * @return
	 */
	IPage<LogUsualVO> selectLogPage(Query query, LogUsualDTO usualDTO);

	/**
	 * 查询Usual-Log详情
	 *
	 * @param id
	 * @return
	 */
	LogUsualVO getLogDetail(Long id);

	/**
	 * 导出Usual-Log
	 *
	 * @param response
	 * @param usualDTO
	 */
	void exportLog(HttpServletResponse response, LogUsualDTO usualDTO);
}
