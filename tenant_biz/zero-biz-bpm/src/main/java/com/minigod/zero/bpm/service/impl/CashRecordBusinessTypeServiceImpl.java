package com.minigod.zero.bpm.service.impl;

import com.minigod.zero.bpm.entity.CashRecordBusinessTypeEntity;
import com.minigod.zero.bpm.mapper.CashRecordBusinessTypeMapper;
import com.minigod.zero.bpm.service.ICashRecordBusinessTypeService;
import com.minigod.zero.bpm.vo.CashRecordBusinessTypeVO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 业务查询归属 服务实现类
 *
 * @author 掌上智珠
 * @since 2023-05-18
 */
@Service
public class CashRecordBusinessTypeServiceImpl extends ServiceImpl<CashRecordBusinessTypeMapper, CashRecordBusinessTypeEntity> implements ICashRecordBusinessTypeService {

	@Override
	public IPage<CashRecordBusinessTypeVO> selectCashRecordBusinessTypePage(IPage<CashRecordBusinessTypeVO> page, CashRecordBusinessTypeVO cashRecordBusinessType) {
		return page.setRecords(baseMapper.selectCashRecordBusinessTypePage(page, cashRecordBusinessType));
	}


}
