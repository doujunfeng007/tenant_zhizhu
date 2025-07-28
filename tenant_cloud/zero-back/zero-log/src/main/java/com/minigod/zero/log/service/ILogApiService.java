
package com.minigod.zero.log.service;

import com.minigod.zero.core.mp.support.Query;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.minigod.zero.core.log.model.LogApi;
import com.minigod.zero.log.model.LogApiDTO;
import com.minigod.zero.log.model.LogApiVO;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 服务类
 *
 * @author Chill
 */
public interface ILogApiService extends IService<LogApi> {
	/**
	 * 分页查询API-Log
	 *
	 * @param query
	 * @param apiDTO
	 * @return
	 */
	IPage<LogApiVO> selectLogPage(Query query, LogApiDTO apiDTO);

	/**
	 * 查询API-Log列表
	 *
	 * @param apiDTO
	 * @return
	 */
	List<LogApiVO> selectLogList(LogApiDTO apiDTO);

	/**
	 * 查询API-Log详情
	 *
	 * @param id
	 * @return
	 */
	LogApiVO getLogDetail(Long id);

	/**
	 * 导出API-Log
	 *
	 * @param response
	 * @param apiDTO
	 */
	void exportLog(HttpServletResponse response, LogApiDTO apiDTO);

}
