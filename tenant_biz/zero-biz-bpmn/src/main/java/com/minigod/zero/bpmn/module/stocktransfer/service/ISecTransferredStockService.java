package com.minigod.zero.bpmn.module.stocktransfer.service;

import com.minigod.zero.bpmn.module.stocktransfer.entity.SecTransferredStockEntity;
import com.minigod.zero.bpmn.module.stocktransfer.vo.SecTransferredStockVO;
import com.minigod.zero.core.mp.base.BaseService;

/**
 * @ClassName SecTransferredStockService.java
 * @author chen
 * @Description TODO
 * @createTime 2024年03月06日 16:29:00
 */
public interface ISecTransferredStockService extends BaseService<SecTransferredStockEntity> {


	boolean updateAndStock(SecTransferredStockVO transferredStockVO);
}
