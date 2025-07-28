package com.minigod.zero.cust.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.minigod.zero.bpm.entity.BpmSecuritiesInfoEntity;
import com.minigod.zero.cust.vo.CustContactVO;

/**
 * 证券客户资料表 服务类
 *
 * @author 掌上智珠
 * @since 2023-05-18
 */
public interface ISecuritiesInfoService extends IService<BpmSecuritiesInfoEntity> {

	/**
	 * 查询开户联系资料
	 * @param custId
	 * @return
	 */
	CustContactVO getCustContactInfo(Long custId,String tradeAccount);

}
