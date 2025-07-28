/*
 *  Copyright (c) 2018-2028, MiniGod All rights reserved.
 */
package com.minigod.zero.customer.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.minigod.zero.customer.entity.CustomerFundTradingAccountEntity;
import com.minigod.zero.customer.vo.CustomerFundTradingAccountVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.customer.vo.FundAccountBalanceVO;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * 基金交易账户信息表 Mapper 接口
 *
 * @author ken
 * @since 2024-04-11
 */
@DS("customer")
public interface CustomerFundTradingAccountMapper extends BaseMapper<CustomerFundTradingAccountEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param fundTransactionAccount
	 * @return
	 */
	List<CustomerFundTradingAccountVO> selectCustomerFundTransactionAccountPage(IPage page, CustomerFundTradingAccountVO fundTransactionAccount);

	/**
	 * 用户id查询基金交易账户
	 * @param custId
	 * @return
	 */
	CustomerFundTradingAccountEntity selectByCustId(Long custId);

	/**
	 * 基金平台查询余额更新到本地平台
	 */
	void updateById(FundAccountBalanceVO fundAccountBalanceVO);
	/**
	 * 冻结金额（可用转冻结）
	 * @param id
	 * @param amount
	 */
	void freezeAvailableAmount(@Param("id") Long id, @Param("amount") BigDecimal amount);
	/**
	 * 扣减冻结金额
	 * @param id
	 * @param amount
	 */
	void reduceFreezeAmount(@Param("id") Long id, @Param("amount")BigDecimal amount);

	/**
	 * 冻结转可用
	 * @param id
	 * @param amount
	 */
	void freezeToAvailableAmount(@Param("id") Long id, @Param("amount")BigDecimal amount);

	/**
	 * 获取账号
	 * @param custId
	 * @return
	 */
	CustomerFundTradingAccountEntity selectCurrentAccountByCustId(Long custId);

	/**
	 * 修改风险等级
	 * @param id
	 */
	void updateRiskLevelById(@Param("id") Long id,@Param("riskLevel")Integer riskLevel);

}
