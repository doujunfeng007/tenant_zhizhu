package com.minigod.zero.cust.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.minigod.zero.bpm.entity.BpmAccountInfoEntity;
import com.minigod.zero.cust.apivo.CustAccountVO;
import com.minigod.zero.cust.apivo.CustTradePushInfoVO;
import com.minigod.zero.cust.cache.AcctInfo;

import java.util.List;

/**
 * 交易账户信息表 Mapper 接口
 *
 * @author 掌上智珠
 * @since 2023-03-27
 */
public interface CustAccountInfoMapper extends BaseMapper<BpmAccountInfoEntity> {


	CustAccountVO getCurrentAccount(Long custId);

	/**
	 * 根据资金账号获取账号信息
	 * @param capitalAccount
	 * @return
	 */
	CustTradePushInfoVO getCustTradePushInfo(String capitalAccount);

	/**
	 * 根据授权人邮箱查询
	 * @param custId or email
	 * @return
	 */
	List<Long> getCustIdList(String custId);

	/**
	 * 获取账户列表
	 * @param custId
	 * @return
	 */
	List<AcctInfo> getAcctList(Long custId);

}
