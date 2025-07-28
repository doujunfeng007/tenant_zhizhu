
package com.minigod.zero.log.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.core.log.model.LogApi;
import com.minigod.zero.log.model.LogApiDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Mapper 接口
 *
 * @author Chill
 */
public interface LogApiMapper extends BaseMapper<LogApi> {
	/**
	 * 分页查询API-Log
	 *
	 * @param page
	 * @param logDTO
	 * @return
	 */
	List<LogApi> selectLogPage(IPage page, @Param("log") LogApiDTO logDTO);

	/**
	 * 查询API-Log
	 *
	 * @param logDTO
	 * @return
	 */
	List<LogApi> selectLogList(@Param("log") LogApiDTO logDTO);

	/**
	 * 查询API-Log总数
	 * @param logDTO
	 * @return
	 */
	Long selectLogCount(@Param("log") LogApiDTO logDTO);
}
