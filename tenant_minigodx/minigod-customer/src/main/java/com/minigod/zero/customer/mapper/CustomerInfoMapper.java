/*
 *  Copyright (c) 2018-2028, MiniGod All rights reserved.
 */
package com.minigod.zero.customer.mapper;

import cn.hutool.core.date.DateTime;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.customer.dto.*;
import com.minigod.zero.customer.entity.CustomerCashAssetsHistory;
import com.minigod.zero.customer.entity.CustomerInfoEntity;
import com.minigod.zero.customer.entity.CustomerTotalAssetsHistory;
import com.minigod.zero.customer.vo.*;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 客户信息表 Mapper 接口
 *
 * @author ken
 * @since 2024-04-11
 */
@DS("customer")
public interface CustomerInfoMapper extends BaseMapper<CustomerInfoEntity> {
	int deleteByPrimaryKey(Long id);

	int insert(CustomerInfoEntity record);

	int insertSelective(CustomerInfoEntity record);

	CustomerInfoEntity selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(CustomerInfoEntity record);

	int updateByPrimaryKey(CustomerInfoEntity record);

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param customerInfo
	 * @return
	 */
	List<CustomerInfoVO> selectCustomerInfoPage(IPage page, CustomerInfoVO customerInfo);

	/**
	 * 手机号获取用户信息
	 *
	 * @param userName
	 * @return
	 */
	@InterceptorIgnore(tenantLine = "true")
	CustomerInfoEntity loadCustomerByUsername(@Param("userName") String userName,
											  @Param("areaCode") String areaCode,
											  @Param("tenantId") String tenantId);

	/**
	 * 昵称获取用户信息
	 *
	 * @param nickName
	 * @return
	 */
	CustomerInfoEntity selectByNickName(String nickName);

	/**
	 * 用户id查询
	 *
	 * @return
	 */
	@InterceptorIgnore(tenantLine = "true")
	CustomerInfoEntity selectByCustId(Long custId);

	/**
	 * 用户ids集合查询
	 *
	 * @return
	 */
	List<CustomerInfoEntity> selectByCustIds(@Param("custIds") List<Long> custIds);

	/**
	 * 查询客户等级和账户开通情况
	 *
	 * @param
	 * @return
	 */
	List<CustRiskInfoVO> custRiskList(IPage<CustRiskInfoVO> page, @Param("custRiskInfoDTO") CustRiskInfoDTO custRiskInfoDTO);

	List<HldStatementMonthVO> hldStatement(@Param("custStatementDTO") CustStatementDTO custStatementDTO);


	//活力得 日账户列表
	List<String> selHldAccountList(@Param("date") Date date);

	List<String> selHldAccountMonthList(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

	//债券易 日账户列表
	List<String> selBondAccountList(@Param("custStatementDTO") CustStatementDTO custStatementDTO);

	List<String> selBondAccountMonthList(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

	//债券易活力得  汇总日账户列表
	List<String> selAccountByDateList(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

	List<String> selAccountList();

	List<String> selAccountMonthList(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

	//债券易活力得  汇总日cust列表
	List<String> selCustByDateList(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

	List<String> selCustList();

	//债券易活力得  汇总月cust列表
	List<String> selCustMonthList(@Param("startDate") Date startDate, @Param("endDate") Date endDate);


	//活利得月结单持仓总览
	List<HldHoldingStatementMonthVO> hldHoldingStatementList(@Param("custStatementDTO") CustStatementDTO custStatementDTO);

	//活利得日结单-活利得交易明细-活利得买入确认单
	List<HldTradingBuyStatementDailyVO> hldTradingStatementList(@Param("custStatementDTO") CustStatementDTO custStatementDTO);

	//活利得日结单-活利得交易明细-活利得卖出确认单
	List<HldTradingSaleStatementDailyVO> hldTradingSaleStatementList(@Param("custStatementDTO") CustStatementDTO custStatementDTO);

	//活利得日结单-2-2.活利得持仓总览
	List<HldHoldingHistoryStatementDailyVO> hldHoldingHistoryStatementList(@Param("custStatementDTO") CustStatementDTO custStatementDTO);

	CustStatementVO selCustHldStatement(String id);

	CustStatementVO selCustBondStatement(String id);

	List<CustomerOpenAccountVO> customerList(IPage<CustomerOpenAccountVO> page, @Param("keyword") String keyword,
											 @Param("riskLevel") Integer riskLevel,
											 @Param("piLevel") Integer piLevel,
											 @Param("startTime") String startTime,
											 @Param("endTime") String endTime);

	List<CustomerOpenAccountVO> visitorList(IPage<CustomerOpenAccountVO> page, @Param("keyword") String keyword,
											@Param("startTime") String startTime,
											@Param("endTime") String endTime);

	List<CustomerOpenAccountVO> userList(IPage<CustomerOpenAccountVO> page, @Param("keyword") String keyword,
										 @Param("startTime") String startTime,
										 @Param("endTime") String endTime);

	//投资组合总览: 现金变动结余  昨日结余
	CustomerTotalAssetsHistory selCashChangeBalanceList(@Param("date") String date, @Param("custId") String custId);

	CustomerCashAssetsHistory selCashBalanceList(@Param("date") String date, @Param("custId") String custId);


	CustomerInfoEntity selectByPhoneAndTenantId(@Param("phone") String phone,
												@Param("areaCode") String areaCode,
												@Param("tenantId") String tenantId);


	List<CustomerWhiteListVO> queryFreezeCustomers(IPage<CustomerWhiteListVO> page, @Param("keyword") String keyword);


	int updateRiskLevel(Long custId, Integer riskLevel, Date expiryDate);

	List<HldHoldingHistoryStatementDailyVO> hldHoldingHistoryList(@Param("custStatementDTO") CustStatementDTO custStatementDTO);

	List<String> selHldOrderList(@Param("startTime") DateTime startTime, @Param("endTime") DateTime endTime);

	List<String> selBondOrderList(@Param("startTime") DateTime startTime, @Param("endTime") DateTime endTime);

	BigDecimal hldHoldingHistoryByFundCode(@Param("productId") String productId, @Param("custStatementDTO") CustStatementDTO custStatementDTO);

	BigDecimal getAverageCost(@Param("custStatementDTO") CustStatementDTO custStatementDTO, @Param("productId") String productId);

	List<CustomerOpenAccountVO> customerStatementList(IPage<CustomerOpenAccountVO> page, @Param("statementListDTO") StatementListDTO statementListDTO);

	List<String> selCashByCustList(@Param("startTime") DateTime startTime, @Param("endTime") DateTime endTime);

	List<BrokerOrderInfoDTO> selHldOrderInfoList(@Param("orderInfoDTO") OrderInfoDTO orderInfoDTO);

	List<BrokerOrderInfoDTO> selBondOrderInfoList(@Param("orderInfoDTO") OrderInfoDTO orderInfoDTO);

	List<CustomerOpenAccountVO> allCustomerList(IPage<CustomerOpenAccountVO> page, String keyword, String startTime, String endTime);


	@InterceptorIgnore(tenantLine = "true")
	int updateTenantCustId(Long custId, Long tenantCustId);

	CustomerInfoEntity updateCustomerInfo(CustomerInfoEntity customerInfo);
}
