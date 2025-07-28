package com.minigod.zero.manage.mapper;

import com.minigod.zero.manage.entity.ReportLogEntity;
import com.minigod.zero.manage.vo.ReportLogVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * APP日志下载 Mapper 接口
 *
 * @author 掌上智珠
 * @since 2023-03-23
 */
public interface ReportLogMapper extends BaseMapper<ReportLogEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param reportLog
	 * @return
	 */
	List<ReportLogVO> selectReportLogPage(IPage page, @Param("reportLog") ReportLogVO reportLog);


}
