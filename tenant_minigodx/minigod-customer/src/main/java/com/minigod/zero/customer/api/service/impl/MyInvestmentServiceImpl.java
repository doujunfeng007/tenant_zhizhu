package com.minigod.zero.customer.api.service.impl;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.minigod.common.cache.CacheNames;
import com.minigod.common.constant.AccountMessageConstant;
import com.minigod.zero.core.i18n.utils.I18nUtil;
import com.minigod.zero.core.redis.cache.ZeroRedis;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.api.ResultCode;
import com.minigod.zero.core.tool.utils.CollectionUtil;
import com.minigod.zero.core.tool.utils.DateUtil;
import com.minigod.zero.customer.enums.CurrencyType;
import com.minigod.zero.customer.enums.FundTradingEnum;
import com.minigod.zero.customer.api.service.MyInvestmentService;
import com.minigod.zero.customer.dto.CustHldTradingListDO;
import com.minigod.zero.customer.dto.CustomerFundTradingAppListDTO;
import com.minigod.zero.customer.dto.CustomerHldTradingAppListDTO;
import com.minigod.zero.customer.entity.*;
import com.minigod.zero.customer.enums.TradeAccountType;
import com.minigod.zero.customer.factory.ExchangeRateFactory;
import com.minigod.zero.customer.mapper.*;
import com.minigod.zero.customer.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/5/6 21:19
 * @description：
 */
@Slf4j
@Service
public class MyInvestmentServiceImpl implements MyInvestmentService {

	@Autowired
	private FundLatestPriceMapper fundLatestPriceMapper;

	@Autowired
	private CustomerFundCapitalAccountMapper customerFundCapitalAccountMapper;

	@Autowired
	private CustomerFundTradingAccountMapper customerFundTradingAccountMapper;

	@Autowired
	private CustomerFundHoldingRecordsMapper customerFundHoldingRecordsMapper;

	@Autowired
	private CustomerFinancingAccountMapper customerFinancingAccountMapper;

	@Autowired
	private FinancingAccountAmountMapper financingAccountAmountMapper;

	@Autowired
	private ExchangeRateFactory exchangeRateFactory;
	@Autowired
	private CustomerHldHoldingRecordsMapper customerHldHoldingRecordsMapper;

	@Autowired
	private CustomerHldCapitalAccountMapper customerHldCapitalAccountMapper;

	@Autowired
	private CustomerFundTradingRecordsMapper customerFundTradingRecordsMapper;

	@Autowired
	private CustomerHldTradingRecordsMapper customerHldTradingRecordsMapper;
	@Autowired
	private CustomerBondTradingRecordsMapper customerBondTradingRecordsMapper;

	@Autowired
	private CustomerTotalAssetsHistoryMapper customerTotalAssetsHistoryMapper;

	@Override
	public R getAccountIncome(Integer accountType, String currency) {
		if (accountType == null) {
			return R.fail("查询失败，查询类型不能为空");
		}
		Long userId = AuthUtil.getTenantUser().getUserId();
		AccountIncomeVO accountIncome = new AccountIncomeVO();
		//基金
		if (accountType.equals(TradeAccountType.FUND.getCode())) {
			CustomerFundTradingAccountEntity fundTradingAccount = customerFundTradingAccountMapper.selectByCustId(userId);
			if (fundTradingAccount == null) {
				return R.fail("基金交易账户不存在");
			}
			String tradingAccount = fundTradingAccount.getTradeAccount();
			List<CustomerFundCapitalAccountEntity> fundCapitalAccountList = customerFundCapitalAccountMapper.selectSubAccountByCustId(userId);
			List<String> subAccountList = fundCapitalAccountList.stream().map(CustomerFundCapitalAccountEntity::getFundAccount).collect(Collectors.toList());
			//获取所有持仓
			List<CustomerFundHoldingRecordsEntity> fundHoldingRecords = customerFundHoldingRecordsMapper.selectBySubAccountIds(subAccountList);
			List<BigDecimal> dailyEarnings = new ArrayList<>();
			List<BigDecimal> holdingEarnings = new ArrayList<>();
			List<BigDecimal> totalEarnings = new ArrayList<>();
			List<BigDecimal> totalAmounts = new ArrayList<>();
			if (!CollectionUtil.isEmpty(fundHoldingRecords)) {
				Date date = new Date();
				String today = DateUtil.format(date, DateUtil.PATTERN_DATE);
				String yesterday = DateUtil.format(DateUtil.plusDays(date, -1), DateUtil.PATTERN_DATE);
				fundHoldingRecords.stream().forEach(fundHoldingRecord -> {
					//持仓币种
					String holdingCurrency = fundHoldingRecord.getCurrency();
					//累计收益已盈利部分
					totalEarnings.add(exchangeRateFactory.exchange(currency, holdingCurrency, fundHoldingRecord.getRealizedGainLoss()));
					//份额为0的过滤掉，不计算日收益，持仓收益
					BigDecimal availableQuantity = fundHoldingRecord.getTotalQuantity();
					if (availableQuantity.compareTo(BigDecimal.ZERO) <= 0) {
						return;
					}
					String theDay = today;
					String theDayBefore = yesterday;
					FundLatestPriceEntity theDayPrice = fundLatestPriceMapper.selectByFundCodeAndDay(fundHoldingRecord.getFundCode(), theDay, holdingCurrency);
					//如果当天价格还没有更新，往前推一天，避免没有价格收益为0
					if (theDayPrice == null) {
						log.info("账号{}基金账户收益未取到当日价格，往前推一天取价格计算收益", tradingAccount);
						theDay = DateUtil.format(DateUtil.plusDays(date, -1), DateUtil.PATTERN_DATE);
						theDayBefore = DateUtil.format(DateUtil.plusDays(date, -2), DateUtil.PATTERN_DATE);
						theDayPrice = fundLatestPriceMapper.selectByFundCodeAndDay(fundHoldingRecord.getFundCode(), theDay, holdingCurrency);
					}
					FundLatestPriceEntity theDayBeforePrice = fundLatestPriceMapper.selectByFundCodeAndDay(fundHoldingRecord.getFundCode(), theDayBefore, holdingCurrency);
					//市值 最新市价 x 可用份额
					BigDecimal totalAmount = theDayPrice.getPrice().multiply(availableQuantity);
					totalAmounts.add(exchangeRateFactory.exchange(currency, holdingCurrency, totalAmount));
					//计算日收益 （最新市价 - 前一天市价） X 可用份额
					BigDecimal dailyEarning = theDayPrice.getPrice().subtract(theDayBeforePrice.getPrice()).multiply(availableQuantity);
					dailyEarnings.add(exchangeRateFactory.exchange(currency, holdingCurrency, dailyEarning));
					//持仓收益  （最新市价 - 平均成本价）X 可用份额
					BigDecimal holdingEarning = theDayPrice.getPrice().subtract(fundHoldingRecord.getAverageCost()).multiply(availableQuantity);
					holdingEarnings.add(exchangeRateFactory.exchange(currency, holdingCurrency, holdingEarning));
					//累计收益持仓收益部分
					totalEarnings.add(exchangeRateFactory.exchange(currency, holdingCurrency, holdingEarning));
				});
			}
			accountIncome.setDailyEarnings(dailyEarnings.stream().reduce(BigDecimal.ZERO, BigDecimal::add));
			accountIncome.setHoldingEarnings(holdingEarnings.stream().reduce(BigDecimal.ZERO, BigDecimal::add));
			accountIncome.setTotalEarnings(totalEarnings.stream().reduce(BigDecimal.ZERO, BigDecimal::add));
			accountIncome.setTotalAmount(totalAmounts.stream().reduce(BigDecimal.ZERO, BigDecimal::add));
		}
		//股票
		if (accountType.equals(TradeAccountType.STOCK.getCode())) {

		}
		return R.data(accountIncome);
	}

	@Override
	public R getCustomerFundHolding(Integer pageIndex,Integer pageSize) {
		Long userId = AuthUtil.getTenantUser().getUserId();
		List<CustomerFundHoldingVO> fundHoldingList = new ArrayList<>();
		List<CustomerFundCapitalAccountEntity> fundCapitalAccountList = customerFundCapitalAccountMapper.selectSubAccountByCustId(userId);
		if (CollectionUtil.isEmpty(fundCapitalAccountList)) {
			return R.data(new PageInfo());
		}
		List<String> subAccountList = fundCapitalAccountList.stream().map(CustomerFundCapitalAccountEntity::getFundAccount).collect(Collectors.toList());
		//获取所有持仓
		PageHelper.startPage(pageIndex, pageSize);
		List<CustomerFundHoldingRecordsEntity> fundHoldingRecords = customerFundHoldingRecordsMapper.selectBySubAccountIds(subAccountList);
		PageInfo pageInfo = new PageInfo(fundHoldingList);
		if (CollectionUtil.isEmpty(fundHoldingRecords)) {
			return R.data(pageInfo);
		}
		fundHoldingRecords.stream().forEach(fundHolding -> {
			BigDecimal availableQuantity = fundHolding.getTotalQuantity();
			//过滤掉份额为0的
			if (availableQuantity.compareTo(BigDecimal.ZERO) <= 0) {
				return;
			}
			String fundCode = fundHolding.getFundCode();
			CustomerFundHoldingVO customerFundHolding = new CustomerFundHoldingVO();
			customerFundHolding.setFundCode(fundCode);
			customerFundHolding.setFundName(fundHolding.getFundName());
			//获取市价
			Date date = new Date();
			String today = DateUtil.format(date, DateUtil.PATTERN_DATE);
			FundLatestPriceEntity theDayPrice = fundLatestPriceMapper.selectByFundCodeAndDay(fundCode, today, fundHolding.getCurrency());
			//如果当天价格还没有更新，往前推一天，避免没有价格收益为0
			if (theDayPrice == null) {
				today = DateUtil.format(DateUtil.plusDays(date, -1), DateUtil.PATTERN_DATE);
				theDayPrice = fundLatestPriceMapper.selectByFundCodeAndDay(fundCode, today, fundHolding.getCurrency());
			}
			BigDecimal todayMarketValue = theDayPrice.getPrice().multiply(availableQuantity);
			customerFundHolding.setMarketValue(todayMarketValue);
			customerFundHolding.setPrice(theDayPrice.getPrice());
			customerFundHolding.setHoldingNumber(availableQuantity);
			customerFundHolding.setCurrency(fundHolding.getCurrency());
			fundHoldingList.add(customerFundHolding);
		});
		return R.data(pageInfo);
	}


	@Override
	public R hldHolding(Integer pageIndex, Integer pageSize) {
		Long userId = AuthUtil.getTenantUser().getUserId();
		List<CustomerHldCapitalAccount> hldCapitalAccountList = customerHldCapitalAccountMapper.selectSubAccountByCustId(userId);
		if (CollectionUtil.isEmpty(hldCapitalAccountList)) {
			return R.data(new PageInfo());
		}
		List<String> subAccountList = hldCapitalAccountList.stream().map(CustomerHldCapitalAccount::getSubAccount).collect(Collectors.toList());
		if (!CollectionUtil.isEmpty(subAccountList)) {
			return R.data(new PageInfo());
		}
		PageHelper.startPage(pageIndex, pageSize);
		List<CustomerHldHoldingRecords> hldHoldingRecords = customerHldHoldingRecordsMapper.selectBySubAccountIds(subAccountList);
		if (CollectionUtil.isEmpty(hldHoldingRecords)) {
			return R.data(new PageInfo());
		}
		CustomerHldHoldingVO hldHolding = new CustomerHldHoldingVO();
		return null;
	}

	@Override
	public R totalAssets(String currency) {
		Long userId = AuthUtil.getTenantUser().getUserId();
		CustomerAccountBalanceVO accountBalance = this.calculateTotalCashAssets(userId,currency);
		if (accountBalance == null){
			return R.fail(ResultCode.FAILURE,"账户异常");
		}
		//账户现金
		BigDecimal totalCash = accountBalance.getAvailableAmount().add(accountBalance.getFreezeAmount());
		//基金持仓
		BigDecimal fundMarketValue = calculateFundMarketValue(userId, currency);
		//股票

		//活力得
		return R.data(totalCash.add(fundMarketValue));
	}


	/**
	 * 基金总资产
	 *
	 * @param userId
	 * @param currency
	 * @return
	 */
	private BigDecimal calculateFundMarketValue(Long userId, String currency) {
		List<CustomerFundCapitalAccountEntity> fundCapitalAccountList = customerFundCapitalAccountMapper.selectSubAccountByCustId(userId);
		if (CollectionUtil.isEmpty(fundCapitalAccountList)) {
			return BigDecimal.ZERO;
		}
		List<String> subAccountList = fundCapitalAccountList.stream().map(CustomerFundCapitalAccountEntity::getFundAccount).collect(Collectors.toList());
		List<CustomerFundHoldingRecordsEntity> fundHoldingRecords = customerFundHoldingRecordsMapper.selectBySubAccountIds(subAccountList);
		if (CollectionUtil.isEmpty(fundHoldingRecords)) {
			return BigDecimal.ZERO;
		}
		List<BigDecimal> marketValues = new ArrayList<>();
		fundHoldingRecords.stream().forEach(fundHoldingRecord -> {
			//持仓币种
			String holdingCurrency = fundHoldingRecord.getCurrency();
			//份额为0的过滤掉，不计算日收益，持仓收益
			BigDecimal availableQuantity = fundHoldingRecord.getTotalQuantity();
			if (availableQuantity.compareTo(BigDecimal.ZERO) <= 0) {
				return;
			}
			String theDay = DateUtil.format(new Date(), DateUtil.PATTERN_DATE);
			;
			FundLatestPriceEntity theDayPrice = fundLatestPriceMapper.selectByFundCodeAndDay(fundHoldingRecord.getFundCode(), theDay, holdingCurrency);
			//如果当天价格还没有更新，往前推一天，避免没有价格收益为0
			if (theDayPrice == null) {
				theDay = DateUtil.format(DateUtil.plusDays(new Date(), -1), DateUtil.PATTERN_DATE);
				theDayPrice = fundLatestPriceMapper.selectByFundCodeAndDay(fundHoldingRecord.getFundCode(), theDay, holdingCurrency);
			}
			//市值 最新市价 x 可用份额
			BigDecimal totalAmount = theDayPrice.getPrice().multiply(availableQuantity);
			marketValues.add(exchangeRateFactory.exchange(currency, holdingCurrency, totalAmount));
		});
		return marketValues.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	/**
	 * 现金资产
	 *
	 * @param custId
	 * @param currency
	 * @return
	 */
	private CustomerAccountBalanceVO calculateTotalCashAssets(Long custId, String currency) {
		CustomerAccountBalanceVO accountBalanceVO = new CustomerAccountBalanceVO();
		CustomerFinancingAccountEntity financingAccount = customerFinancingAccountMapper.selectByCustId(custId);
		if (financingAccount == null) {
			return null;
		}
		accountBalanceVO.setAccountId(financingAccount.getAccountId());
		accountBalanceVO.setCustId(financingAccount.getCustId());
		accountBalanceVO.setTenantId(financingAccount.getTenantId());
		List<FinancingAccountAmount> accountAmountList =
			financingAccountAmountMapper.selectByAccountId(financingAccount.getAccountId(), currency);

		List<BigDecimal> availableAmountList = new ArrayList<>();
		List<BigDecimal> freezeAmountList = new ArrayList<>();
		List<BigDecimal> transitedAmountList = new ArrayList<>();

		if (CollectionUtil.isEmpty(accountAmountList)) {
			return accountBalanceVO;
		}
		accountAmountList.stream().forEach(accountAmount -> {
			String sourceCurrency = accountAmount.getCurrency();
			availableAmountList.add(exchangeRateFactory.exchange(currency, sourceCurrency, accountAmount.getAvailableAmount()));
			freezeAmountList.add(exchangeRateFactory.exchange(currency, sourceCurrency, accountAmount.getFreezeAmount()));
			transitedAmountList.add(exchangeRateFactory.exchange(currency, sourceCurrency, accountAmount.getTransitedAmount()));
		});
		accountBalanceVO.setCurrency(currency);
		//可用
		BigDecimal availableAmount = availableAmountList.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
		accountBalanceVO.setAvailableAmount(availableAmount);
		//冻结
		BigDecimal freezeAmount = freezeAmountList.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
		accountBalanceVO.setFreezeAmount(freezeAmount);
		//在途
		BigDecimal transitedAmount = transitedAmountList.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
		accountBalanceVO.setTransitedAmount(transitedAmount);
		return accountBalanceVO;
	}


	/**
	 * 基金交易记录
	 * <p>
	 * 已提交订单 ->  submitDate   - 200
	 * 申购成功   ->  confirmedDate -300
	 * 申购失败   ->  rejectedDate  -400,600
	 * 赎回成功   ->  confirmedDate -300
	 * 赎回失败   ->  rejectedDate  -400,600
	 * 取消申购   ->  canceledDate  -500
	 * 赎回到账   ->  settledDate   -300
	 * status状态码:  100待付款  200已提交  211以授权 221已发送  300已清算
	 * 400拒绝   500已取消  600失败  700成功
	 *
	 * @param type 交易类别
	 * @return
	 */
	@Override
	public R fundTradingList(String type, Integer pageIndex, Integer pageSize) {
		PageHelper.startPage(pageIndex, pageSize);
		Long userId = AuthUtil.getTenantUser().getUserId();
		Date time = cn.hutool.core.date.DateUtil.lastMonth().toSqlDate();
		List<CustomerFundTradingAppListDTO> list = customerFundTradingRecordsMapper.selectTradingAppList(userId, type, time, pageIndex, pageSize);
		for (CustomerFundTradingAppListDTO dto : list) {
			if (dto.getType() == FundTradingEnum.TradingType.buy.getCode()) {
				//购买
				if (dto.getStatus() == FundTradingEnum.TradingStatusType.SETTLED.getCode()) {
					dto.setTime(dto.getConfirmedDate());
				}
			}
			if (dto.getType() == FundTradingEnum.TradingType.sell.getCode()) {
				//赎回
				if (dto.getStatus() == FundTradingEnum.TradingStatusType.SETTLED.getCode()) {
					if (dto.getSettledDate() != null) {
						dto.setTime(dto.getSettledDate());
					} else {
						dto.setTime(dto.getConfirmedDate());
					}
				}
			}
			if (dto.getStatus() == FundTradingEnum.TradingStatusType.SUBMITTED.getCode()) {
				dto.setTime(dto.getSubmitDate());
			}
			if (dto.getStatus() == FundTradingEnum.TradingStatusType.CANCELED.getCode()) {
				dto.setTime(dto.getCanceledDate());
			}

			if (dto.getStatus() == FundTradingEnum.TradingStatusType.FAILED.getCode() || dto.getStatus() == FundTradingEnum.TradingStatusType.REJECTED.getCode()) {
				dto.setTime(dto.getRejectedDate());
			}
		}


		PageInfo<CustomerFundTradingAppListDTO> pageInfo = new PageInfo<>(list);

		return R.data(pageInfo);
	}

	/**
	 * 活利得交易记录
	 *
	 * @param custHldTradingListVO
	 * @return
	 */

	@Override
	public R hldTradingList(CustHldTradingListVO custHldTradingListVO) {
		PageHelper.startPage(custHldTradingListVO.getPageIndex(), custHldTradingListVO.getPageSize());
		Long userId = AuthUtil.getTenantUser().getUserId();
		Date time = cn.hutool.core.date.DateUtil.lastMonth().toSqlDate();
		CustHldTradingListDO custHldTradingListDO = new CustHldTradingListDO();
		BeanUtils.copyProperties(custHldTradingListVO, custHldTradingListDO);
		custHldTradingListDO.setUserId(userId);
		custHldTradingListDO.setTime(time);
		List<CustomerHldTradingAppListDTO> list = customerHldTradingRecordsMapper.selectTradingAppList(custHldTradingListDO);
		for (CustomerHldTradingAppListDTO dto : list) {
			if (dto.getType() == FundTradingEnum.TradingType.buy.getCode()) {
				//购买
				if (dto.getStatus() == FundTradingEnum.TradingStatusType.SETTLED.getCode()) {
					dto.setTime(dto.getConfirmedDate());
				}
			}
			if (dto.getType() == FundTradingEnum.TradingType.sell.getCode()) {
				//赎回
				if (dto.getStatus() == FundTradingEnum.TradingStatusType.SETTLED.getCode()) {
					if (dto.getSettledDate() != null) {
						dto.setTime(dto.getSettledDate());
					} else {
						dto.setTime(dto.getConfirmedDate());
					}
				}
			}
			if (dto.getStatus() == FundTradingEnum.TradingStatusType.SUBMITTED.getCode()) {
				dto.setTime(dto.getSubmitDate());
			}
			if (dto.getStatus() == FundTradingEnum.TradingStatusType.CANCELED.getCode()) {
				dto.setTime(dto.getCanceledDate());
			}

			if (dto.getStatus() == FundTradingEnum.TradingStatusType.FAILED.getCode() || dto.getStatus() == FundTradingEnum.TradingStatusType.REJECTED.getCode()) {
				dto.setTime(dto.getRejectedDate());
			}
		}


		PageInfo<CustomerHldTradingAppListDTO> pageInfo = new PageInfo<>(list);

		return R.data(pageInfo);


	}

	@Override
	public R bondTradingList(CustBondTradingListVO custBondTradingListVO) {
		PageHelper.startPage(custBondTradingListVO.getPageIndex(), custBondTradingListVO.getPageSize());
		Long userId = AuthUtil.getTenantUser().getUserId();
		Date time = cn.hutool.core.date.DateUtil.lastMonth().toSqlDate();
		CustHldTradingListDO custHldTradingListDO = new CustHldTradingListDO();
		BeanUtils.copyProperties(custBondTradingListVO, custHldTradingListDO);
		custHldTradingListDO.setUserId(userId);
		custHldTradingListDO.setTime(time);
		List<CustomerHldTradingAppListDTO> list = customerBondTradingRecordsMapper.selectTradingAppList(custHldTradingListDO);
		for (CustomerHldTradingAppListDTO dto : list) {
			if (dto.getType() == FundTradingEnum.TradingType.buy.getCode()) {
				//购买
				if (dto.getStatus() == FundTradingEnum.TradingStatusType.SETTLED.getCode()) {
					dto.setTime(dto.getConfirmedDate());
				}
			}
			if (dto.getType() == FundTradingEnum.TradingType.sell.getCode()) {
				//赎回
				if (dto.getStatus() == FundTradingEnum.TradingStatusType.SETTLED.getCode()) {
					if (dto.getSettledDate() != null) {
						dto.setTime(dto.getSettledDate());
					} else {
						dto.setTime(dto.getConfirmedDate());
					}
				}
			}
			if (dto.getStatus() == FundTradingEnum.TradingStatusType.SUBMITTED.getCode()) {
				dto.setTime(dto.getSubmitDate());
			}
			if (dto.getStatus() == FundTradingEnum.TradingStatusType.CANCELED.getCode()) {
				dto.setTime(dto.getCanceledDate());
			}

			if (dto.getStatus() == FundTradingEnum.TradingStatusType.FAILED.getCode() || dto.getStatus() == FundTradingEnum.TradingStatusType.REJECTED.getCode()) {
				dto.setTime(dto.getRejectedDate());
			}
		}


		PageInfo<CustomerHldTradingAppListDTO> pageInfo = new PageInfo<>(list);

		return R.data(pageInfo);
	}

	@Override
	public R assertsHistory(String currency) {
		if (StringUtils.isEmpty(currency)) {
			return R.fail(ResultCode.PARAM_MISS);
		}
		String accountId = AuthUtil.getTenantUser().getAccount();
		List<CustomerTotalAssetsHistory> totalAssetsHistoryList = customerTotalAssetsHistoryMapper.selectByAccountId(accountId);
		List<AssertsHistoryVO> assertsHistoryVOList = new ArrayList<>();
		int size = totalAssetsHistoryList.size();
		String date = DateUtil.formatDate(new Date());
		if (size > 0 && size < 7) {
			date = totalAssetsHistoryList.get(size - 1).getStatisticalTime();
		}
		for (int i = 0; i < 7; i++) {
			AssertsHistoryVO assertsHistoryVO = new AssertsHistoryVO();
			if (i < size) {
				CustomerTotalAssetsHistory assertsHistory = totalAssetsHistoryList.get(i);
				assertsHistoryVO.setDate(assertsHistory.getStatisticalTime());
				if (currency.equals(CurrencyType.HKD.getCode())) {
					assertsHistoryVO.setAmount(assertsHistory.getHkdAssets());
				}
				if (currency.equals(CurrencyType.CNY.getCode())) {
					assertsHistoryVO.setAmount(assertsHistory.getCnyAssets());
				}
				if (currency.equals(CurrencyType.USD.getCode())) {
					assertsHistoryVO.setAmount(assertsHistory.getUsdAssets());
				}
			} else {
				String d = DateUtil.formatDate(DateUtil.plusDays(DateUtil.parseDate(date), -i));
				assertsHistoryVO.setDate(d);
				assertsHistoryVO.setAmount(BigDecimal.ZERO);
			}
			assertsHistoryVOList.add(assertsHistoryVO);
		}
		assertsHistoryVOList.sort(Comparator.comparing(AssertsHistoryVO::getDate));
		return R.data(assertsHistoryVOList);
	}

	@Override
	public R positionsAsserts(String currency) {
		if (StringUtils.isEmpty(currency)) {
			return R.fail(ResultCode.PARAM_MISS);
		}
		String accountId = AuthUtil.getTenantUser().getAccount();
		List<CustomerTotalAssetsHistory> totalAssetsHistoryList = customerTotalAssetsHistoryMapper.selectByAccountId(accountId);
		List<AssertsHistoryVO> assertsHistoryVOList = new ArrayList<>();
		int size = totalAssetsHistoryList.size();
		String date = DateUtil.formatDate(new Date());
		if (size > 0 && size < 7) {
			date = totalAssetsHistoryList.get(size - 1).getStatisticalTime();
		}
		for (int i = 0; i < 7; i++) {
			AssertsHistoryVO assertsHistoryVO = new AssertsHistoryVO();
			if (i < size) {
				CustomerTotalAssetsHistory assertsHistory = totalAssetsHistoryList.get(i);
				assertsHistoryVO.setDate(assertsHistory.getStatisticalTime());
				if (currency.equals(CurrencyType.HKD.getCode())) {
					assertsHistoryVO.setAmount(assertsHistory.getHkdPositionAssets().add(assertsHistory.getHkdIncomeAssets()));
				}
				if (currency.equals(CurrencyType.CNY.getCode())) {
					assertsHistoryVO.setAmount(assertsHistory.getCnyPositionAssets().add(assertsHistory.getCnyIncomeAssets()));
				}
				if (currency.equals(CurrencyType.USD.getCode())) {
					assertsHistoryVO.setAmount(assertsHistory.getUsdPositionAssets().add(assertsHistory.getUsdIncomeAssets()));
				}
			} else {
				String d = DateUtil.formatDate(DateUtil.plusDays(DateUtil.parseDate(date), -i));
				assertsHistoryVO.setDate(d);
				assertsHistoryVO.setAmount(BigDecimal.ZERO);
			}
			assertsHistoryVOList.add(assertsHistoryVO);
		}
		assertsHistoryVOList.sort(Comparator.comparing(AssertsHistoryVO::getDate));
		return R.data(assertsHistoryVOList);
	}

	@Resource
	private ZeroRedis miniGodRedis;

	@Resource
	private CustomerDoubleVerifyMapper customerDoubleVerifyMapper;

	@Override
	public R checkTradePassword(String exAccountId, String token) {
		if (StringUtils.isEmpty(exAccountId) || StringUtils.isEmpty(token)) {
			return R.fail(ResultCode.PARAM_MISS);
		}
		CustomerFinancingAccountEntity financingAccount = customerFinancingAccountMapper.selectByAccountId(exAccountId);
		if (financingAccount == null) {
			return R.fail(I18nUtil.getMessage(AccountMessageConstant.ACCOUNT_OPEN_FINANCING_ACCOUNT_NOT_EXIST_NOTICE));
		}
		Long custId = financingAccount.getCustId();
		String tokenKey = CacheNames.TRADE_LOGIN_TOKEN + custId;
		if (!miniGodRedis.exists(tokenKey)) {
			return R.fail(I18nUtil.getMessage(AccountMessageConstant.ACCOUNT_OPEN_TRADE_TOKEN_EXPIRED_NOTICE));
		}
		String currentToken = miniGodRedis.get(tokenKey);
		if (!currentToken.equals(token)) {
			return R.fail(I18nUtil.getMessage(AccountMessageConstant.ACCOUNT_OPEN_ACCOUNT_LOGIN_OTHER_DEVICE_NOTICE));
		}
		return R.success();
	}
}
