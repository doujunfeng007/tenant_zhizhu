package com.minigod.zero.bpmn.module.deposit.service.impl;

import com.minigod.zero.bpmn.module.deposit.entity.SecDepositTypeEntity;
import com.minigod.zero.bpmn.module.deposit.vo.SecDepositTypeVO;
import com.minigod.zero.bpmn.module.deposit.mapper.SecDepositTypeMapper;
import com.minigod.zero.bpmn.module.deposit.service.ISecDepositTypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 入金方式管理配置表 服务实现类
 *
 * @author 掌上智珠
 * @since 2024-03-01
 */
@Service
public class SecDepositTypeServiceImpl extends ServiceImpl<SecDepositTypeMapper, SecDepositTypeEntity> implements ISecDepositTypeService {

	@Override
	public IPage<SecDepositTypeVO> selectSecDepositTypePage(IPage<SecDepositTypeVO> page, SecDepositTypeVO secDepositTypeVO) {
		return page.setRecords(baseMapper.selectSecDepositTypePage(page, secDepositTypeVO));
	}


}
