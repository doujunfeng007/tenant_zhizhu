package com.minigod.zero.bpmn.module.stocktransfer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.minigod.zero.bpmn.module.stocktransfer.entity.SecSharesInfoEntity;
import com.minigod.zero.bpmn.module.stocktransfer.mapper.SecSharesInfoMapper;
import com.minigod.zero.bpmn.module.stocktransfer.service.ISecSharesInfoService;
import com.minigod.zero.core.mp.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName SecSharesInfoServiceImpl.java
 * @author chen
 * @Description TODO
 * @createTime 2024年03月06日 17:01:00
 */
@Service
public class SecSharesInfoServiceImpl extends BaseServiceImpl<SecSharesInfoMapper, SecSharesInfoEntity> implements ISecSharesInfoService {


	@Override
	public List<SecSharesInfoEntity> selectListByTransferredStockId(Long id) {

		LambdaQueryWrapper<SecSharesInfoEntity> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(SecSharesInfoEntity::getStockId, id);
		return baseMapper.selectList(queryWrapper);
	}
}
