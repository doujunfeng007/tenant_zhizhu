package com.minigod.zero.trade.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minigod.zero.trade.entity.CustOperationLogEntity;
import com.minigod.zero.trade.mapper.CustOperationLogMapper;
import com.minigod.zero.trade.service.ICustOperationLogService;
import com.minigod.zero.trade.vo.CustOperationLogVO;
import org.springframework.stereotype.Service;

/**
 * 客户操作日志 服务实现类
 *
 * @author 掌上智珠
 * @since 2023-07-06
 */
@Service
public class CustOperationLogServiceImpl extends ServiceImpl<CustOperationLogMapper, CustOperationLogEntity> implements ICustOperationLogService {

	@Override
	public IPage<CustOperationLogVO> selectCustOperationLogPage(IPage<CustOperationLogVO> page, CustOperationLogVO custOperationLog) {
		return page.setRecords(baseMapper.selectCustOperationLogPage(page, custOperationLog));
	}


}
