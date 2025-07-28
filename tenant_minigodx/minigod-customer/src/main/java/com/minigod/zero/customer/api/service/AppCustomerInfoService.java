package com.minigod.zero.customer.api.service;

import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.customer.dto.CustomerInfoDTO;
import com.minigod.zero.customer.dto.CustomerOpenAccountInfoDTO;
import com.minigod.zero.customer.entity.CustomerFinancingAccountEntity;
import com.minigod.zero.customer.entity.CustomerInfoEntity;
import com.minigod.zero.customer.entity.CustomerTradeAccountEntity;
import com.minigod.zero.customer.vo.CustomerAccountDetailVO;
import com.minigod.zero.customer.vo.CustomerAssetDetailVO;
import com.minigod.zero.customer.vo.CustomerInfoVO;
import com.minigod.zero.customer.vo.stock.CustomerStockInfo;
import com.minigod.zero.platform.dto.OpenAccountDTO;
import com.minigod.zero.platform.vo.FundAccountVO;

import java.util.Date;
import java.util.List;

/**
 * @Author: guangjie.liao
 * @Date: 2024/4/14 14:46
 * @Description:
 */
public interface AppCustomerInfoService {
	/**
	 * 客户注册
	 * @param customerInfoDTO
	 * @return
	 */
	R customerRegister(CustomerInfoDTO customerInfoDTO);

	/**
	 * 基金开户
	 * @param fundOpenAccount
	 * @return
	 */
	R<FundAccountVO> fundOpenAccount(OpenAccountDTO fundOpenAccount);

	/**
	 * 活利得开户
	 * @param fundOpenAccount
	 * @return
	 */
	CustomerTradeAccountEntity hldOpenAccount(OpenAccountDTO fundOpenAccount);


	FundAccountVO openOtcAccount(OpenAccountDTO openAccount);

	/**
	 * 债券开户
	 * @param fundOpenAccount
	 * @return
	 */
	CustomerTradeAccountEntity bondOpenAccount(OpenAccountDTO fundOpenAccount);
	/**
	 * 查询理财账户信息
	 * @return
	 */
	R accountTotalBalance(String currency,String accountId);

	/**
	 *
	 * @param currency 币种
	 * @param accountId
	 * @return
	 */
	R selectAccountBalance(String currency,String accountId);

	/**
	 * 查询用户详情
	 * @param custId
	 * @return
	 */
	R selectCustomerDetail(Long custId);

	/**
	 * 更新用户pi等级
	 * @param piLevel
	 * @return
	 */
	R updateCustomerPiLevel(Integer piLevel,Long custId);

	/**
	 * 更新用户衍生品调查结果
	 * @param custId
	 * @return
	 */
	R updateCustomerDerivative(Long custId);

	/**
	 * 手机号获取客户信息
	 * @param userName
	 * @return
	 */
	CustomerInfoVO loadCustomerByUsername(String userName,String areaCode,String tenantId, String param);

	/**
	 * 理财账号获取用户信息
	 * @param userName
	 * @param tenantId
	 * @return
	 */
	CustomerInfoVO loadCustomerByAccount(String userName,String tenantId);

	/**
	 *
	 * @param accountId
	 * @param subAccount
	 * @return
	 */
	CustomerInfoVO loadCustomerBySubAccount(String accountId, String subAccount);

	/**
	 * 静默注册
	 * @param phone
	 * @param areaCode
	 * @return
	 */
	CustomerInfoVO defaultRegister(String phone,String areaCode,String tenantId);

	/**
	 * 修改活利得风险等级
	 * @param riskLevel
	 * @return
	 */
	R updateHldAccountRiskLevel(Integer riskLevel);

	/**
	 * 归档客户总资产
	 * @return
	 */
	R archiveUserAssets();

	/**
	 * 查询理财账户币种金额
	 * @param accountId
	 * @param currency
	 * @return
	 */
	R accountAmount(String accountId,String currency);

	/**
	 * 根据用户id查询理财账号
	 * @param custId
	 * @return
	 */
	CustomerFinancingAccountEntity selectAccountByCustId(Long custId);

	/**
	 * 修改开户资料
	 * @param customerOpenAccount
	 * @return
	 */
	R updateOpenAccountInfo(CustomerOpenAccountInfoDTO customerOpenAccount);


	R<CustomerAccountDetailVO> selectCustomerDetailByAccountId(String accountId);

	/**
	 *
	 * @param custId
	 * @return
	 */
	R<CustomerAccountDetailVO> selectCustomerDetailByCustId(Long custId);

	R<List<CustomerAssetDetailVO>> selectCustomerDetailByTime(Date startTime, Date endTime);


	/**
	 * 租户注册
	 * @param customerInfo
	 * @return
	 */
	R tenantCustomerRegister(CustomerInfoDTO customerInfo);


	/**
	 * 根据id获取用户详细
	 * @param custId
	 * @return
	 */
	R selectProxyCustomerDetail(Long custId);

	/**
	 * 验证交易账号密码
	 * @param tradeAccount
	 * @param password
	 * @return
	 */
	R acctCheckPwd(String tradeAccount, String password);

	/**
	 * 根据大账户查询股票开户信息
	 * @param accountId
	 * @return
	 */
	CustomerStockInfo selectStockAccount(String accountId);

	/**
	 * 更新客户信息
	 * @param customerInfoDTO
	 * @return
	 */
	CustomerInfoEntity updateCustomerInfo(CustomerInfoDTO customerInfoDTO);
}
