package com.minigod.zero.cust.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.minigod.zero.bpm.entity.BpmSecuritiesInfoEntity;
import com.minigod.zero.cust.vo.CustContactVO;

/**
 * 证券客户资料表 Mapper 接口
 *
 * @author 掌上智珠
 * @since 2023-05-18
 */
public interface SecuritiesInfoMapper extends BaseMapper<BpmSecuritiesInfoEntity> {

	/**
	 * 查询客户账号联系方式
	 *
	 * @param custId
	 * @return
	 */
	CustContactVO getCustContactInfo(Long custId);

}
