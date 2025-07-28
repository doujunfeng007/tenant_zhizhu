/*
 *  Copyright (c) 2018-2028, MiniGod All rights reserved.
 */
package com.minigod.zero.customer.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.customer.entity.CustomerFinancingAccountEntity;
import com.minigod.zero.customer.vo.CustomerAccountBalanceVO;
import com.minigod.zero.customer.vo.CustomerFinancingAccountVO;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * 客户理财账户表 Mapper 接口
 *
 * @author ken
 * @since 2024-04-11
 */
@DS("customer")
public interface CustomerFinancingAccountMapper extends BaseMapper<CustomerFinancingAccountEntity> {
	int deleteByPrimaryKey(Long id);

	int insert(CustomerFinancingAccountEntity record);

	int insertSelective(CustomerFinancingAccountEntity record);

	CustomerFinancingAccountEntity selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(CustomerFinancingAccountEntity record);

	int updateByPrimaryKey(CustomerFinancingAccountEntity record);
	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param financingAccount
	 * @return
	 */
	List<CustomerFinancingAccountVO> selectCustomerFinancingAccountPage(IPage page, CustomerFinancingAccountVO financingAccount);

	/**
	 * 根据用户id查询理财账户
	 * @param custId
	 * @return
	 */
	@InterceptorIgnore(tenantLine = "true")
	CustomerFinancingAccountEntity selectByCustId(Long custId);

	/**
	 * 将基金平台查询过来的金额更新到本地理财账户
	 * @param customerAccount
	 */
	void updateByAccountId(CustomerAccountBalanceVO customerAccount);

	/**
	 * 冻结金额（可用转冻结）
	 * @param id
	 * @param amount
	 */
	void freezeAvailableAmount(@Param("id") Integer id, @Param("amount")BigDecimal amount);

	/**
	 * 扣减冻结金额
	 * @param id
	 * @param amount
	 */
	void reduceFreezeAmount(@Param("id") Integer id, @Param("amount")BigDecimal amount);

	/**
	 * 冻结转可用
	 * @param id
	 * @param amount
	 */
	void freezeToAvailableAmount(@Param("id") Integer id, @Param("amount")BigDecimal amount);

	/**
	 * 理财账号查询
	 * @param accountId
	 * @return
	 */
	@InterceptorIgnore(tenantLine = "true")
	CustomerFinancingAccountEntity selectByAccountId(String accountId);

	/**
	 * 获取所有理财账号
	 * @return
	 */
	List<CustomerFinancingAccountEntity> selectAllAccount();


	CustomerFinancingAccountEntity selectByAccountIdAndTenantId(@Param("accountId")String accountId,@Param("tenantId")String tenantId);
}
