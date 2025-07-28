
package com.minigod.zero.log.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.core.log.model.LogUsual;
import com.minigod.zero.log.model.LogUsualDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Mapper 接口
 *
 * @author Chill
 */
public interface LogUsualMapper extends BaseMapper<LogUsual> {
	/**
	 * 分页查询Usual-Log
	 *
	 * @param page
	 * @param usualDTO
	 * @return
	 */
	List<LogUsual> selectLogPage(IPage page, @Param("log") LogUsualDTO usualDTO);

	/**
	 * 查询Usual-Log
	 *
	 * @param usualDTO
	 * @return
	 */
	List<LogUsual> selectLogList(@Param("log") LogUsualDTO usualDTO);

	/**
	 * 查询Usual-Log总数
	 * @param usualDTO
	 * @return
	 */
	Long selectLogCount(@Param("log") LogUsualDTO usualDTO);
}
