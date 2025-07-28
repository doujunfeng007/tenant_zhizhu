package com.minigod.zero.customer.back.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.minigod.zero.biz.common.exception.BusinessException;
import com.minigod.zero.core.redis.cache.ZeroRedis;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.utils.CollectionUtil;
import com.minigod.zero.core.tool.utils.DateUtil;
import com.minigod.zero.core.tool.utils.StringUtil;
import com.minigod.zero.customer.back.client.BpmnProxyClient;
import com.minigod.zero.customer.back.service.ReportService;
import com.minigod.zero.customer.entity.CustomerBasicInfoEntity;
import com.minigod.zero.customer.entity.CustomerCashAssetsHistory;
import com.minigod.zero.customer.entity.CustomerFinancingAccountEntity;
import com.minigod.zero.customer.entity.FinancingAccountAmountFlows;
import com.minigod.zero.customer.enums.CurrencyType;
import com.minigod.zero.customer.factory.ExchangeRateFactory;
import com.minigod.zero.customer.mapper.CustomerBasicInfoMapper;
import com.minigod.zero.customer.mapper.CustomerCashAssetsHistoryMapper;
import com.minigod.zero.customer.mapper.CustomerFinancingAccountMapper;
import com.minigod.zero.customer.mapper.FinancingAccountAmountFlowsMapper;
import com.minigod.zero.customer.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/7/30 17:24
 * @description：
 */
@Service
public class ReportServiceImpl implements ReportService {

	@Autowired
	private ZeroRedis zeroRedis;

	@Autowired
	private BpmnProxyClient accountOpenClient;

	@Autowired
	private ExchangeRateFactory exchangeRateFactory;


	@Autowired
	private CustomerFinancingAccountMapper customerFinancingAccountMapper;

	@Autowired
	private CustomerBasicInfoMapper customerBasicInfoMapper;

	@Autowired
	private FinancingAccountAmountFlowsMapper financingAccountAmountFlowsMapper;

	@Autowired
	private CustomerCashAssetsHistoryMapper customerCashAssetsHistoryMapper;

	@Override
	public R queryPage(Long pageIndex, Long pageSize, String startTime, String endTime, String clientId, Integer currency, Integer depositStatus, Integer withdrawalStatus, String applicationIds, Integer type) {
		R<ReportVO> result =
			accountOpenClient.queryPage(pageIndex,pageSize,startTime,endTime,clientId,currency,depositStatus,withdrawalStatus,applicationIds,type);
		if (!result.isSuccess()){
			throw new BusinessException("查询异常："+result.getMsg());
		}
		ReportVO report = result.getData();
		Page page = report.getPage();
		List<DepositAndWithdrawalFundsReportVO> list = page.getRecords();
		if (!CollectionUtil.isEmpty(list)){
			for (DepositAndWithdrawalFundsReportVO fundsReport: list) {
				CustomerFinancingAccountEntity financingAccount = customerFinancingAccountMapper.selectByAccountId(fundsReport.getClientId());
				if (financingAccount != null){
					CustomerBasicInfoEntity basicInfo = customerBasicInfoMapper.selectByCustId(financingAccount.getCustId());
					if (basicInfo != null) {
						String userName = basicInfo.getClientName();
						if (StringUtil.isBlank(userName)) {
							userName = basicInfo.getGivenNameSpell() + " " + basicInfo.getFamilyNameSpell();
						}
						fundsReport.setClientName(userName);
					}
				}
			}
		}
		BigDecimal usdToHkd = exchangeRateFactory.exchange(CurrencyType.HKD.getCode(), CurrencyType.USD.getCode(),report.getDepositUSD());
		BigDecimal cnyToHkd = exchangeRateFactory.exchange(CurrencyType.HKD.getCode(), CurrencyType.CNY.getCode(),report.getDepositCNY());
		report.setTotalDepositHKD(report.getDepositHKD().add(usdToHkd).add(cnyToHkd).setScale(2, RoundingMode.HALF_UP));

		usdToHkd = exchangeRateFactory.exchange(CurrencyType.HKD.getCode(), CurrencyType.USD.getCode(),report.getWithdrawalUSD());
		cnyToHkd = exchangeRateFactory.exchange(CurrencyType.HKD.getCode(), CurrencyType.CNY.getCode(),report.getWithdrawalCNY());
		report.setTotalWithdrawalHKD(report.getWithdrawalHKD().add(usdToHkd).add(cnyToHkd).setScale(2, RoundingMode.HALF_UP));
		return R.data(report);
	}

	@Override
	public R accountAssetSummaryReport(Long pageIndex, Long pageSize, String startTime, String endTime, String accountId, String currency,String ids) {
		Page page = new Page<>(pageIndex,pageSize);
		List<CustomerCashAssetsVO> list = customerCashAssetsHistoryMapper.accountAssetSummaryReport(page,startTime,endTime,accountId,currency,getIdsParam(ids));
		if (!CollectionUtil.isEmpty(list)){
			list.stream().forEach(cash->{
				String statisticalTime = cash.getStatisticalTime();
				String cashCurrency = cash.getCurrency();
				cash.setId(cash.getId()+"-"+cash.getCurrency());
				cash.setLastDayBalance(getAssets(cash.getAccountId(),statisticalTime,cashCurrency));
				cash.setTodayBalance(cash.getTodayBalance().setScale(2,RoundingMode.HALF_UP));
				//交收
				BigDecimal[] amounts = getDepositAmountAndWithdrawalAmount(cash.getAccountId(),cash.getCurrency(),statisticalTime);
				cash.setSettlement(amounts[0].subtract(amounts[1]));
			});
		}
		page.setRecords(list);
		return R.data(page);
	}

	@Override
	public R financialDetailsReport(Long pageIndex, Long pageSize, String startTime, String endTime, String accountId, String currency,String ids) {
		Page page = new Page<>(pageIndex,pageSize);
		List<CustomerCashAssetsVO> list = customerCashAssetsHistoryMapper.accountAssetSummaryReport(page,startTime,endTime,accountId,currency,getIdsParam(ids));
		CustomerFinancialDetailsReport report = new CustomerFinancialDetailsReport();

		List<CustomerFinancialDetailsRecord> recordList = new ArrayList<>();
		if (!CollectionUtil.isEmpty(list)){
			list.stream().forEach(cashAssets->{
				recordList.add(buildDetailRecord(cashAssets));
			});
		}
		report.setPage(page);
		page.setRecords(recordList);
		//按查询条件汇总
		list = customerCashAssetsHistoryMapper.accountAssetSummaryReport(null,startTime,endTime,accountId,currency,getIdsParam(ids));
		List<CustomerFinancialDetailsRecord> totalRecordList = new ArrayList<>();
		if (!CollectionUtil.isEmpty(list)){
			list.stream().forEach(cashAssets->{
				totalRecordList.add(buildDetailRecord(cashAssets));
			});
		}
		Map<String,List<CustomerFinancialDetailsRecord>> recordGroup =
			totalRecordList.stream().collect(Collectors.groupingBy(CustomerFinancialDetailsRecord::getCurrency));
		report.setHkd(this.summary(report,recordGroup.get(CurrencyType.HKD.getCode())));
		report.setUsd(this.summary(report,recordGroup.get(CurrencyType.USD.getCode())));
		report.setCny(this.summary(report,recordGroup.get(CurrencyType.CNY.getCode())));
		report.setTotal(summaryTotal(report));
		return R.data(report);
	}

	private List<String> getIdsParam(String ids){
		if (StringUtil.isBlank(ids)){
			return null;
		}
		String[] idArray = ids.split(",");
		List<String> idList = new ArrayList<>();
		for (String id : idArray){
			if (StringUtil.isNotBlank(id)){
				idList.add(id);
			}
		}
		return idList;
	}

	/**
	 * 构建分页数据
	 * @param cashAssets
	 * @return
	 */
	private CustomerFinancialDetailsRecord buildDetailRecord(CustomerCashAssetsVO cashAssets){
		String account = cashAssets.getAccountId();
		String cashCurrency = cashAssets.getCurrency();
		String statisticalTime = cashAssets.getStatisticalTime();

		CustomerFinancialDetailsRecord record = new CustomerFinancialDetailsRecord();
		record.setId(cashAssets.getId()+"-"+cashAssets.getCurrency());
		record.setAccountId(account);
		record.setCurrency(cashCurrency);
		record.setStatisticalTime(statisticalTime);
		record.setCustomerName(cashAssets.getCustomerName());
		record.setLastDayBalance(getAssets(account,statisticalTime,cashCurrency));
		BigDecimal[] amounts = getDepositAmountAndWithdrawalAmount(account,cashCurrency,statisticalTime);
		record.setDepositBalance(amounts[0]);
		record.setWithdrawalBalance(amounts[1]);
		record.setSettlement(amounts[0].subtract(amounts[1]));
		return record;
	}


	/**
	 * 汇总所有
	 * @param report
	 * @return
	 */
	private CustomerFinancialDetailsReport.Summary summaryTotal(CustomerFinancialDetailsReport report){
		CustomerFinancialDetailsReport.Summary hkd = report.getHkd();
		CustomerFinancialDetailsReport.Summary usd = report.getUsd();
		CustomerFinancialDetailsReport.Summary cny = report.getCny();

		BigDecimal settlementUsd = exchangeRateFactory.exchange(CurrencyType.HKD.getCode(), CurrencyType.USD.getCode(),usd.getSettlement());
		BigDecimal lastDayBalanceUsd = exchangeRateFactory.exchange(CurrencyType.HKD.getCode(), CurrencyType.USD.getCode(),usd.getLastDayBalance());
		BigDecimal depositBalanceUsd = exchangeRateFactory.exchange(CurrencyType.HKD.getCode(), CurrencyType.USD.getCode(),usd.getDepositBalance());
		BigDecimal withdrawalBalanceUsd = exchangeRateFactory.exchange(CurrencyType.HKD.getCode(), CurrencyType.USD.getCode(),usd.getWithdrawalBalance());

		BigDecimal settlementCny = exchangeRateFactory.exchange(CurrencyType.HKD.getCode(), CurrencyType.CNY.getCode(),cny.getSettlement());
		BigDecimal lastDayBalanceCny = exchangeRateFactory.exchange(CurrencyType.HKD.getCode(), CurrencyType.CNY.getCode(),cny.getLastDayBalance());
		BigDecimal depositBalanceCny = exchangeRateFactory.exchange(CurrencyType.HKD.getCode(), CurrencyType.CNY.getCode(),cny.getDepositBalance());
		BigDecimal withdrawalBalanceCny = exchangeRateFactory.exchange(CurrencyType.HKD.getCode(), CurrencyType.CNY.getCode(),cny.getWithdrawalBalance());

		BigDecimal settlement = hkd.getSettlement().add(settlementUsd).add(settlementCny).setScale(2,RoundingMode.HALF_UP);
		BigDecimal lastDayBalance = hkd.getLastDayBalance().add(lastDayBalanceUsd).add(lastDayBalanceCny).setScale(2,RoundingMode.HALF_UP);
		BigDecimal depositBalance = hkd.getDepositBalance().add(depositBalanceCny).add(depositBalanceUsd).setScale(2,RoundingMode.HALF_UP);
		BigDecimal withdrawalBalance = hkd.getWithdrawalBalance().add(withdrawalBalanceCny).add(withdrawalBalanceUsd).setScale(2,RoundingMode.HALF_UP);

		return report.setSummary(lastDayBalance,depositBalance,withdrawalBalance,settlement);
	}

	/**
	 * 根据币种汇总
	 * @param report
	 * @param recordList
	 * @return
	 */
	private CustomerFinancialDetailsReport.Summary summary(CustomerFinancialDetailsReport report,List<CustomerFinancialDetailsRecord> recordList){
		BigDecimal settlement = BigDecimal.ZERO;
		BigDecimal lastDayBalance = BigDecimal.ZERO;
		BigDecimal depositBalance = BigDecimal.ZERO;
		BigDecimal withdrawalBalance = BigDecimal.ZERO;

		if (CollectionUtil.isEmpty(recordList)){
			return report.setSummary(lastDayBalance,depositBalance,withdrawalBalance,settlement);
		}
		for (CustomerFinancialDetailsRecord record :recordList){
			settlement = settlement.add(record.getSettlement()).setScale(2,RoundingMode.HALF_UP);
			lastDayBalance = lastDayBalance.add(record.getLastDayBalance()).setScale(2,RoundingMode.HALF_UP);
			depositBalance = depositBalance.add(record.getDepositBalance()).setScale(2,RoundingMode.HALF_UP);
			withdrawalBalance = withdrawalBalance.add(record.getWithdrawalBalance()).setScale(2,RoundingMode.HALF_UP);
		}
		return report.setSummary(lastDayBalance,depositBalance,withdrawalBalance,settlement);
	}

	/**
	 * 统计金额
	 * @return
	 */
	private BigDecimal[] getDepositAmountAndWithdrawalAmount(String accountId,String currency,String date){
		String key = "report_account_business_flow:" + accountId +":"+ date + ":" +currency;
		List<FinancingAccountAmountFlows> flowsList = null;
		if (zeroRedis.exists(key)){
			flowsList = zeroRedis.get(key);
		}else{
			flowsList = financingAccountAmountFlowsMapper.selectThatTimeFlowsByCurrency(accountId,currency,date);
		}
		if (CollectionUtil.isEmpty(flowsList)){
			return new BigDecimal[]{BigDecimal.ZERO,BigDecimal.ZERO};
		}
		zeroRedis.setEx(key,flowsList,getEXTime());
		List<BigDecimal> depositFlow = new ArrayList<>();
		List<BigDecimal> withdrawalFlow = new ArrayList<>();
		flowsList
			.stream()
			.forEach(flow->{
				if (flow.getAfterAmount().subtract(flow.getBeforeAmount()).compareTo(BigDecimal.ZERO) > 0){
					depositFlow.add(flow.getAmount());
				}else{
					withdrawalFlow.add(flow.getAmount());
				}
			});
		BigDecimal depositAmount = depositFlow.stream().reduce(BigDecimal.ZERO,BigDecimal::add).setScale(2,RoundingMode.HALF_UP);
		BigDecimal withdrawalAmount = withdrawalFlow.stream().reduce(BigDecimal.ZERO,BigDecimal::add).setScale(2,RoundingMode.HALF_UP);
		return new BigDecimal[]{depositAmount,withdrawalAmount};
	}

	/**
	 * 晚上11点前过期，因为11.30会跑当天新的数据
	 * @return
	 */
	private Long getEXTime(){
		Date now = new Date();
		String endTime = DateUtil.format(now,"yyyy-MM-dd 23:00:00");
		Date end = DateUtil.parseDate(endTime);
		Long time = end.getTime() - now.getTime();
		//如果过了11点，设置1分钟缓存
		if (time <= 0){
			return 1 * 60L;
		}
		return time / 1000;
	}

	/**
	 * 获取对应币种资产
	 * @param accountId
	 * @param date
	 * @param currency
	 * @return
	 */
	private BigDecimal getAssets(String accountId,String date,String currency){
		String lastDay = DateUtil.plusDays(DateUtil.parseDate(date),-1,"yyyy-MM-dd");
		String key = "report_cash_assets_history:" + accountId+":"+date+":"+currency;
		List<CustomerCashAssetsHistory> cashAssetsHistoryList = null;
		if (zeroRedis.exists(key)){
			cashAssetsHistoryList = zeroRedis.get(key);
		}else{
			cashAssetsHistoryList = customerCashAssetsHistoryMapper.selectByAccountId(accountId);
		}
		if (CollectionUtil.isEmpty(cashAssetsHistoryList)){
			return BigDecimal.ZERO;
		}
		zeroRedis.setEx(key,cashAssetsHistoryList,getEXTime());
		Optional<CustomerCashAssetsHistory> optional = cashAssetsHistoryList.stream().filter(asset->{
			return asset.getStatisticalTime().equals(lastDay);
		}).findFirst();
		if (optional.isEmpty()){
			return BigDecimal.ZERO;
		}
		CustomerCashAssetsHistory  cashAssetsHistory = optional.get();
		if (CurrencyType.HKD.getCode().equals(currency)){
			return cashAssetsHistory.getHkdAssets().setScale(2,RoundingMode.HALF_UP);
		}
		if (CurrencyType.USD.getCode().equals(currency)){
			return cashAssetsHistory.getUsdAssets().setScale(2,RoundingMode.HALF_UP);
		}
		if (CurrencyType.CNY.getCode().equals(currency)){
			return cashAssetsHistory.getCnyAssets().setScale(2,RoundingMode.HALF_UP);
		}
		return BigDecimal.ZERO;
	}

}
