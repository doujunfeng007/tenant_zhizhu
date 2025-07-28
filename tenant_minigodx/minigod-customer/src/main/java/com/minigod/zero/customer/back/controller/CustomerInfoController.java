/*
 *  Copyright (c) 2018-2028, MiniGod All rights reserved.
 */
package com.minigod.zero.customer.back.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.core.boot.ctrl.ZeroController;
import com.minigod.zero.core.mp.support.Condition;
import com.minigod.zero.core.mp.support.Query;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.customer.api.service.AppCustomerInfoService;
import com.minigod.zero.customer.back.service.ICustomerInfoService;
import com.minigod.zero.customer.dto.CustomerRoleDTO;
import com.minigod.zero.customer.dto.CustomerWhiteDTO;
import com.minigod.zero.customer.dto.OpenAccountLogQueryDTO;
import com.minigod.zero.customer.dto.StatementListDTO;
import com.minigod.zero.customer.dto.*;
import com.minigod.zero.customer.vo.*;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;

import com.minigod.zero.core.mp.support.Condition;
import com.minigod.zero.core.mp.support.Query;
import com.minigod.zero.core.tool.api.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 客户信息表 控制器
 *
 * @author ken
 * @since 2024-04-11
 */
@RestController
@AllArgsConstructor
@RequestMapping("/customerInfo")
@Api(value = "客户信息表", tags = "客户信息表接口")
public class CustomerInfoController extends ZeroController {

	private final ICustomerInfoService customerInfoService;

	/**
	 * 客户信息表 详情
	 */
	@GetMapping("/detail")
	public R<BackCustomerDetailVO> detail(Long custId) {
		return R.data(customerInfoService.detail(custId));
	}

	@GetMapping("/cash_account")
	public R cashAccountInfo(Long custId) {
		return R.data(customerInfoService.cashAccountInfo(custId));
	}

	@GetMapping("/cash_account/balance")
	public R cashAccountBalance(Long custId, String currency) {
		return R.data(customerInfoService.cashAccountBalance(custId, currency));
	}

	@GetMapping("/bank_card_info")
	public R bankCardInfo(Query query, Long custId) {
		return R.data(customerInfoService.bankCardInfo(Condition.getPage(query), custId));
	}

	@GetMapping("/capital_flow")
	public R capitalFlow(Query query, Long custId) {
		return R.data(customerInfoService.capitalFlow(Condition.getPage(query), custId));
	}

	/**
	 * 开户日志
	 *
	 * @param query
	 * @param queryParams
	 * @return
	 */
	@GetMapping("/open_account_log")
	public R<IPage<CustomerOpenAccountVO>> openAccountList(Query query,
														   OpenAccountLogQueryDTO queryParams) {
		return R.data(customerInfoService.openAccountList(Condition.getPage(query), queryParams));
	}


	@GetMapping("/customer_position_list")
	public R<IPage<CustomerPositionsDetailVO>> customerPositionList(Long custId, Integer current, Integer size, Integer productType) {
		return customerInfoService.customerPositionList(custId, current, size, productType);
	}

	@GetMapping("/order_list")
	public R<IPage<CustomerOrderVO>> orderList(Long custId, Integer current, Integer size, Integer productType, Integer status) {
		return customerInfoService.orderList(custId, current, size, productType, status);
	}

	@GetMapping("/customer_history_order")
	public R<IPage<CustomerHistoryOrderVO>> customerHistoryOrder(Long custId, Integer current, Integer size, Integer productType) {
		return customerInfoService.customerHistoryOrder(custId, current, size, productType);
	}


	@GetMapping("/distribution_records")
	public R<IPage<DividendDistributionRecords>> distributionRecords(Long custId, Integer current, Integer size, Integer productType) {
		return customerInfoService.distributionRecords(custId, current, size, productType);
	}

	@GetMapping("/position_market_value")
	public R positionMarketValue(Long custId, Integer productType, String currency) {
		return customerInfoService.positionMarketValue(custId, productType, currency);
	}

	@GetMapping("/select_confirmation")
	public R selectConfirmation(String orderId) {
		return customerInfoService.selectConfirmation(orderId);
	}

	@GetMapping("/download_confirmation")
	public void downloadConfirmation(HttpServletResponse response, HttpServletRequest request, String path) throws IOException {
		customerInfoService.downloadConfirmation(response, request, path);
	}

	/**
	 * 日月结单管理列表查询
	 *
	 * @param query
	 * @return
	 */
	@GetMapping("/customer_statement")
	public R customerStatement(Query query, StatementListDTO statementListDTO) {
		return customerInfoService.customerStatement(Condition.getPage(query), statementListDTO);
	}

	@PostMapping("/freeze")
	public R freezeCustomers(@RequestBody CustomerWhiteDTO customer) {
		return customerInfoService.freezeCustomers(customer.getCustId(), customer.getStatus());
	}

	@GetMapping("/freeze")
	public R queryFreezeCustomers(Query query, String keyword) {
		return customerInfoService.queryFreezeCustomers(Condition.getPage(query), keyword);
	}

	@GetMapping("/customer_account_info")
	public R customerWithdrawalInfo(String accountId) {
		return customerInfoService.customerWithdrawalInfo(accountId);
	}

	/**
	 * 查询客户角色
	 *
	 * @param custId
	 * @return
	 */
	@GetMapping("/customer_role")
	public R getCustomerRole(Long custId) {
		return customerInfoService.getCustomerRole(custId);
	}

	/**
	 * 设置客户角色
	 *
	 * @param customerRole
	 * @return
	 */
	@PostMapping("/customer_role")
	public R settingCustomerRole(@RequestBody CustomerRoleDTO customerRole) {
		return customerInfoService.settingCustomerRole(customerRole.getCustId(), customerRole.getRoleIds());
	}

	/**
	 * 删除客户角色
	 * @param customerRole
	 * @return
	 */
	@DeleteMapping("/customer_role")
	public R deleteCustomerRole(@RequestBody CustomerRoleDTO customerRole) {
		return customerInfoService.deleteCustomerRole(customerRole.getCustId(), customerRole.getRoleIds());
	}

	/**
	 * 子账号开通
	 * @param dto
	 * @return
	 */
	@PostMapping("/generate_sub_account")
	public R generateSubAccount(@RequestBody SubAccountDTO dto){
		return customerInfoService.generateSubAccount(dto);
	}

	/**
	 * 子账号列表
	 * @param accountId
	 * @param subAccount
	 * @param roleId
	 * @return
	 */
	@GetMapping("/sub_account")
	public R subAccountList(String accountId,String subAccount,Integer roleId){
		return customerInfoService.subAccountList(accountId,subAccount,roleId);
	}
}
