
package com.minigod.zero.log.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.minigod.zero.core.log.model.LogError;
import com.minigod.zero.core.mp.support.Query;
import com.minigod.zero.log.model.LogErrorDTO;
import com.minigod.zero.log.model.LogErrorVO;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 服务类
 *
 * @author Chill
 */
public interface ILogErrorService extends IService<LogError> {
	/**
	 * 分页查询Error-Log
	 *
	 * @param query
	 * @param errorDTO
	 * @return
	 */
	IPage<LogErrorVO> selectLogPage(Query query, LogErrorDTO errorDTO);

	/**
	 * 查询Error-Log列表
	 *
	 * @param errorDTO
	 * @return
	 */
	List<LogErrorVO> selectLogList(LogErrorDTO errorDTO);

	/**
	 * 查询Error-Log详情
	 *
	 * @param id
	 * @return
	 */
	LogErrorVO getLogDetail(Long id);

	/**
	 * 导出Error-Log
	 *
	 * @param response
	 * @param errorDTO
	 */
	void exportLog(HttpServletResponse response, LogErrorDTO errorDTO);
}
