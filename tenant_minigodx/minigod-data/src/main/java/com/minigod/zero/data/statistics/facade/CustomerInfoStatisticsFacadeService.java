package com.minigod.zero.data.statistics.facade;

import java.util.concurrent.CompletableFuture;

import com.minigod.zero.data.statistics.service.CustomerBasicInfoStatisticsService;
import com.minigod.zero.data.statistics.service.CustomerFinancingAccountService;
import com.minigod.zero.data.statistics.service.CustomerInfoStatisticsService;
import com.minigod.zero.data.statistics.service.CustomerRealNameVerifyStatisticsService;
import com.minigod.zero.data.vo.CustomerCountVO;
import com.minigod.zero.data.vo.CustomerTotalCountVO;
import org.springframework.stereotype.Service;

/**
 * 客户信息数据统计服务
 *
 * @author eric
 * @date 2024-10-26 17:30:18
 */
@Service
public class CustomerInfoStatisticsFacadeService {
	private final CustomerInfoStatisticsService customerInfoService;
	private final CustomerRealNameVerifyStatisticsService customerRealNameVerifyService;
	private final CustomerBasicInfoStatisticsService customerBasicInfoService;
	private final CustomerFinancingAccountService customerFinancingAccountService;

	public CustomerInfoStatisticsFacadeService(CustomerInfoStatisticsService customerInfoService,
											   CustomerRealNameVerifyStatisticsService customerRealNameVerifyService,
											   CustomerBasicInfoStatisticsService customerBasicInfoService,
											   CustomerFinancingAccountService customerFinancingAccountService) {
		this.customerInfoService = customerInfoService;
		this.customerRealNameVerifyService = customerRealNameVerifyService;
		this.customerBasicInfoService = customerBasicInfoService;
		this.customerFinancingAccountService = customerFinancingAccountService;
	}

	/**
	 * 统计用户数及占比情况
	 *
	 * @return
	 */
	public CustomerCountVO countCustomers() {
		CustomerCountVO customerCountVO = new CustomerCountVO();

		CompletableFuture<Void> piCountFuture = CompletableFuture.runAsync(() ->
			customerCountVO.setPiCount(customerInfoService.statisticsByPiLevel()));

		CompletableFuture<Void> genderCountFuture = CompletableFuture.runAsync(() ->
			customerCountVO.setGenderCount(customerRealNameVerifyService.statisticsByGender()));

		CompletableFuture<Void> ageCountFuture = CompletableFuture.runAsync(() ->
			customerCountVO.setAgeCount(customerRealNameVerifyService.statisticsByAge()));

		CompletableFuture<Void> idKindCountFuture = CompletableFuture.runAsync(() ->
			customerCountVO.setIdKindCount(customerBasicInfoService.statisticsByIdKind()));

		CompletableFuture.allOf(piCountFuture, genderCountFuture, ageCountFuture, idKindCountFuture).join();

		return customerCountVO;
	}

	/**
	 * 统计用户总数概览
	 *
	 * @return
	 */
	public CustomerTotalCountVO getCustomerTotalCount() {
		CustomerTotalCountVO customerTotalCountVO = customerFinancingAccountService.getCustomerTotalCount();
		customerTotalCountVO.setPiCount(customerInfoService.statisticsPiUserCount());
		return customerTotalCountVO;
	}
}

