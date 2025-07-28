package com.minigod.zero.manage.service;

import com.minigod.zero.manage.entity.ReportLogEntity;
import com.minigod.zero.manage.vo.ReportLogVO;
import com.minigod.zero.core.mp.base.BaseService;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * APP日志下载 服务类
 *
 * @author 掌上智珠
 * @since 2023-03-23
 */
public interface IReportLogService extends BaseService<ReportLogEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param reportLog
	 * @return
	 */
	IPage<ReportLogVO> selectReportLogPage(IPage<ReportLogVO> page, ReportLogVO reportLog);


}
