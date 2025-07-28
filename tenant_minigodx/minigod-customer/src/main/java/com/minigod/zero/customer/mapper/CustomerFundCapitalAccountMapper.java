/*
 *  Copyright (c) 2018-2028, MiniGod All rights reserved.
 */
package com.minigod.zero.customer.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.minigod.zero.customer.entity.CustomerBondCapitalAccount;
import com.minigod.zero.customer.entity.CustomerFundCapitalAccountEntity;
import com.minigod.zero.customer.vo.CustomerFundCapitalAccountVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.customer.vo.FundSubAccountBalanceVO;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * 基金账户信息表 Mapper 接口
 *
 * @author ken
 * @since 2024-04-11
 */
@DS("customer")
public interface CustomerFundCapitalAccountMapper extends BaseMapper<CustomerFundCapitalAccountEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param fundCapitalAccount
	 * @return
	 */
	List<CustomerFundCapitalAccountVO> selectCustomerFundCapitalAccountPage(IPage page, CustomerFundCapitalAccountVO fundCapitalAccount);

	/**
	 * 根据用户id查询基金子账号
	 * @param custId
	 * @return
	 */
	List<CustomerFundCapitalAccountEntity> selectSubAccountByCustId(Long custId);

	/**
	 * 将基金平台查询过来的金额信息更新到平台
	 * @param accountList
	 */
	void updateBySubAccount(@Param("accountList") List<FundSubAccountBalanceVO> accountList);

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

	Long selectCustAccountBySubId(String subAccount);

	List<CustomerFundCapitalAccountEntity> selectByAccountId(String accountId);
}
