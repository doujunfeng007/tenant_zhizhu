
package com.minigod.zero.log.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.core.log.model.LogError;
import com.minigod.zero.log.model.LogErrorDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Mapper 接口
 *
 * @author Chill
 */
public interface LogErrorMapper extends BaseMapper<LogError> {
	/**
	 * 分页查询Error-Log
	 *
	 * @param page
	 * @param errorDTO
	 * @return
	 */
	List<LogError> selectLogPage(IPage page, @Param("log") LogErrorDTO errorDTO);

	/**
	 * 查询Error-Log
	 *
	 * @param errorDTO
	 * @return
	 */
	List<LogError> selectLogList(@Param("log") LogErrorDTO errorDTO);

	/**
	 * 查询Error-Log总数
	 * @param errorDTO
	 * @return
	 */
	Long selectLogCount(@Param("log") LogErrorDTO errorDTO);
}
