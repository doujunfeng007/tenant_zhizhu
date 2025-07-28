package com.minigod.zero.bpmn.module.stocktransfer.service;

import com.minigod.zero.bpmn.module.stocktransfer.entity.SecSharesInfoEntity;
import com.minigod.zero.core.mp.base.BaseService;

import java.util.List;

/**
 * @ClassName SecSharesInfoService.java
 * @author chen
 * @Description TODO
 * @createTime 2024年03月06日 17:01:00
 */
public interface ISecSharesInfoService extends BaseService<SecSharesInfoEntity> {


	/**
	 * 通过转入转出股票id 查询股票列表
	 * @param id
	 * @return
	 */
	List<SecSharesInfoEntity> selectListByTransferredStockId(Long id);
}
