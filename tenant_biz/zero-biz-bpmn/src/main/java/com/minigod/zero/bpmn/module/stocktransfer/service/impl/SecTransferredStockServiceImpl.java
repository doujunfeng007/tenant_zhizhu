package com.minigod.zero.bpmn.module.stocktransfer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.minigod.zero.bpmn.module.stocktransfer.entity.SecSharesInfoEntity;
import com.minigod.zero.bpmn.module.stocktransfer.entity.SecTransferredStockEntity;
import com.minigod.zero.bpmn.module.stocktransfer.mapper.SecTransferredStockMapper;
import com.minigod.zero.bpmn.module.stocktransfer.service.ISecSharesInfoService;
import com.minigod.zero.bpmn.module.stocktransfer.service.ISecTransferredStockService;
import com.minigod.zero.bpmn.module.stocktransfer.vo.SecTransferredStockVO;
import com.minigod.zero.core.mp.base.BaseServiceImpl;
import com.minigod.zero.core.tool.utils.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * @ClassName SecTransferredStockServiceImpl.java
 * @author chen
 * @Description TODO
 * @createTime 2024年03月06日 16:32:00
 */
@Service
public class SecTransferredStockServiceImpl extends BaseServiceImpl<SecTransferredStockMapper, SecTransferredStockEntity> implements ISecTransferredStockService {


	@Autowired
	private ISecSharesInfoService secSharesInfoService;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean updateAndStock(SecTransferredStockVO transferredStockVO) {
		SecTransferredStockEntity entity = Objects.requireNonNull(BeanUtil.copy(transferredStockVO, SecTransferredStockEntity.class));
		int count=baseMapper.updateById(entity);
		if(null !=transferredStockVO.getSharesData()){
			List<SecSharesInfoEntity> sharesData =transferredStockVO.getSharesData();
			LambdaQueryWrapper<SecSharesInfoEntity> wrapper = new LambdaQueryWrapper<>();
			wrapper.eq(SecSharesInfoEntity::getStockId, transferredStockVO.getId());
			secSharesInfoService.getBaseMapper().delete(wrapper);
			for (SecSharesInfoEntity sharesDatum : sharesData) {
				secSharesInfoService.save(sharesDatum);
			}
		}
		return count >0;
	}
}
