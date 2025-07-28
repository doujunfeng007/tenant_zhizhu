package com.minigod.zero.trade.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.minigod.zero.trade.entity.CustOperationLogEntity;
import com.minigod.zero.trade.vo.CustOperationLogVO;

/**
 * 客户操作日志 服务类
 *
 * @author 掌上智珠
 * @since 2023-07-06
 */
public interface ICustOperationLogService extends IService<CustOperationLogEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param custOperationLog
	 * @return
	 */
	IPage<CustOperationLogVO> selectCustOperationLogPage(IPage<CustOperationLogVO> page, CustOperationLogVO custOperationLog);


}
