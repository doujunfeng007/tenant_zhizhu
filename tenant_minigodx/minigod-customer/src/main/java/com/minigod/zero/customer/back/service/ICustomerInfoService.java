/*
 *  Copyright (c) 2018-2028, MiniGod All rights reserved.
 */
package com.minigod.zero.customer.back.service;

import com.alibaba.fastjson.JSONObject;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.customer.dto.CustRiskInfoDTO;
import com.minigod.zero.customer.dto.OpenAccountLogQueryDTO;
import com.minigod.zero.customer.dto.StatementListDTO;
import com.minigod.zero.customer.dto.SubAccountDTO;
import com.minigod.zero.customer.entity.CustomerInfoEntity;
import com.minigod.zero.customer.vo.*;
import com.minigod.zero.core.mp.base.BaseService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.jsonwebtoken.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.List;

/**
 * 客户信息表 服务类
 *
 * @author ken
 * @since 2024-04-11
 */
public interface ICustomerInfoService extends BaseService<CustomerInfoEntity> {
	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param customerInfo
	 * @return
	 */
	IPage<CustomerInfoVO> selectCustomerInfoPage(IPage<CustomerInfoVO> page, CustomerInfoVO customerInfo);

	/**
	 * 分页查询客户等级和账户开通情况
	 *
	 * @param
	 * @return
	 */
	IPage<CustRiskInfoVO> custRiskPage(IPage<CustRiskInfoVO> page, CustRiskInfoDTO custRiskInfoDTO);

	/**
	 * 客户列表
	 *
	 * @param page
	 * @param queryParams
	 * @return
	 */
	IPage<CustomerOpenAccountVO> openAccountList(IPage<CustomerOpenAccountVO> page, OpenAccountLogQueryDTO queryParams);

	/**
	 * 开户详情
	 *
	 * @param custId
	 * @return
	 */
	BackCustomerDetailVO detail(Long custId);

	/**
	 * 现金账户信息
	 *
	 * @param custId
	 * @return
	 */
	List<CashAccountVO> cashAccountInfo(Long custId);

	/**
	 * 现金账户统一币种换算汇总
	 *
	 * @param custId
	 * @param currency
	 * @return
	 */
	BigDecimal cashAccountBalance(Long custId, String currency);

	/**
	 * 银行卡列表
	 *
	 * @param page
	 * @param custId
	 * @return
	 */
	IPage<CustomerBankCardVO> bankCardInfo(IPage<CustomerBankCardVO> page, Long custId);

	/**
	 * 资料流水
	 *
	 * @param page
	 * @param custId
	 * @return
	 */
	IPage<CapitalFlowVO> capitalFlow(IPage<CapitalFlowVO> page, Long custId);

	R<IPage<CustomerPositionsDetailVO>> customerPositionList(Long custId, Integer pageNumber, Integer pageSize, Integer productType);

	R<IPage<CustomerOrderVO>> orderList(Long custId, Integer pageNumber, Integer pageSize, Integer productType, Integer status);

	R<IPage<CustomerHistoryOrderVO>> customerHistoryOrder(Long custId, Integer pageNumber, Integer pageSize, Integer productType);

	R<IPage<DividendDistributionRecords>> distributionRecords(Long custId, Integer pageNumber, Integer pageSize, Integer productType);

	R positionMarketValue(Long custId, Integer productType, String currency);

	R selectConfirmation(String orderId);

	void downloadConfirmation(HttpServletResponse response, HttpServletRequest request, String path) throws IOException, java.io.IOException;

	R customerStatement(IPage<CustomerOpenAccountVO> page, StatementListDTO statementListDTO);

	List<CustomerOpenAccountVO> getCustomerStatementList(IPage<CustomerOpenAccountVO> page, StatementListDTO statementListDTO);

	BigDecimal getAccumulatedRevenue(JSONObject data, String currency);

	R freezeCustomers(Long custId, Integer status);

	R queryFreezeCustomers(IPage<CustomerWhiteListVO> page, String keyword);

	R customerWithdrawalInfo(String accountId);

	R getCustomerRole(Long custId);

	R settingCustomerRole(Long custId, List<Integer> roles);

	R deleteCustomerRole(Long custId, List<Integer> roles);

	R generateSubAccount(SubAccountDTO dto);

	R subAccountList(String accountId,String subAccount,Integer roleId);
}
