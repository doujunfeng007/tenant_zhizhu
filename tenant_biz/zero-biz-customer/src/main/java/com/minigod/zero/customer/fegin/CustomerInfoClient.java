package com.minigod.zero.customer.fegin;

import cn.hutool.core.bean.BeanUtil;
import com.minigod.zero.biz.common.constant.AccountMessageConstant;
import com.minigod.zero.biz.common.constant.TenantConstant;
import com.minigod.zero.core.i18n.utils.I18nUtil;
import com.minigod.zero.core.launch.constant.TokenConstant;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.jackson.JsonUtil;
import com.minigod.zero.customer.api.service.AppCustomerInfoService;
import com.minigod.zero.customer.api.service.ICustDeviceService;
import com.minigod.zero.customer.api.service.OpenAccountService;
import com.minigod.zero.customer.api.service.PayService;
import com.minigod.zero.customer.back.service.*;
import com.minigod.zero.customer.common.CustomerClientConstants;
import com.minigod.zero.customer.dto.*;
import com.minigod.zero.customer.entity.*;
import com.minigod.zero.customer.mapper.CustomerBasicInfoMapper;
import com.minigod.zero.customer.mapper.CustomerFinancingAccountMapper;
import com.minigod.zero.customer.mapper.CustomerInfoMapper;
import com.minigod.zero.customer.mapper.CustomerRealnameVerifyMapper;
import com.minigod.zero.customer.vo.*;
import com.minigod.zero.customer.vo.stock.CustomerStockInfo;
import com.minigod.zero.trade.feign.ICounterFundClient;
import com.minigod.zero.trade.vo.sjmb.req.FundDepositReq;
import com.minigod.zero.trade.vo.sjmb.req.FundFreezeReq;
import com.minigod.zero.trade.vo.sjmb.req.FundUnFreezeReq;
import com.minigod.zero.trade.vo.sjmb.req.FundUnFreezeWithdrawReq;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class CustomerInfoClient implements ICustomerInfoClient {
	@Autowired
	private AppCustomerInfoService customerInfoService;

	@Autowired
	private PayService payService;

	@Autowired
	private OpenAccountService openAccountService;

	@Autowired
	private ICustDeviceService custDeviceService;

	@Autowired
	private CustomerFileService customerFileService;

	@Autowired
	private CostPackageService costPackageService;

	@Autowired
	private CustomerStatementAccountService customerStatementAccountService;

	@Autowired
	private Environment environment;

	@Autowired
	private ICounterFundClient counterFundClient;

	@Autowired
	private AppCustomerInfoService appCustomerInfoService;

	@Autowired
	private ICustomerTradeAccountService customerTradeAccountService;

	@Autowired
	private ICustomerTradeSubAccountService customerSubAccountService;

	@Autowired
	private CustomerInfoMapper customerInfoMapper;

	@Autowired
	private CustomerBasicInfoMapper customerBasicInfoMapper;

	@Autowired
	private CustomerRealnameVerifyMapper customerRealnameVerifyMapper;

	@Autowired
	private CustomerFinancingAccountMapper customerFinancingAccountMapper;




	@Override
	@GetMapping(CustomerClientConstants.LOAD_CUSTOMER_BY_USERNAME)
	public R<CustomerInfoVO> loadCustomerByUsername(String userName, String areaCode, String tenantId, String param) {
		return R.data(customerInfoService.loadCustomerByUsername(userName,areaCode,tenantId,param));
	}

	@Override
	public R<CustomerInfoVO> loadCustomerByAccount(String userName, String tenantId) {
		return R.data(customerInfoService.loadCustomerByAccount(userName,tenantId));
	}

	@Override
	public R<CustomerInfoVO> loadCustomerBySubAccount(String accountId, String subAccount) {
		return R.data(customerInfoService.loadCustomerBySubAccount(accountId,subAccount));
	}

	@Override
	@GetMapping(CustomerClientConstants.CUSTOMER_DEFAULT_REGISTER)
	public R<CustomerInfoVO> defaultRegister(String phone, String areaCode,String tenantId) {
		return R.data(customerInfoService.defaultRegister(phone, areaCode, tenantId));
	}

	@Override
	@PostMapping(CustomerClientConstants.UPDATE_PI_LEVEL)
	public R updatePiLevel(Integer piLevel,Long custId){
		return customerInfoService.updateCustomerPiLevel(piLevel,custId);
	}

	@Override
	@PostMapping(CustomerClientConstants.CUSTOMER_OPEN_ACCOUNT)
	public R openAccount(@RequestBody CustomerOpenAccountDTO customerOpenAccount) {
		return openAccountService.openAccount(customerOpenAccount);
	}

	@Override
	public R openAccountNew(CustomerOpenAccountDTO customerOpenAccount) {
		log.info("客户开户参数：{}", JsonUtil.toJson(customerOpenAccount));
		String businessType = environment.getProperty("tenant.openAccount.type");
		List<String> businessTypeList = Arrays.asList(businessType.split(","));
		/**
		 * 账户系统开户
		 */
		R result = openAccountService.customerOpenAccount(customerOpenAccount);

		if (result.isSuccess()) {

			Map<String, String> data = (Map<String, String>)result.getData();
			/**
			 * 基金开户
			 */
			if (businessTypeList.contains(TenantConstant.FUND_TYPE)) {
				result = openAccountService.customerFundOpenAccount(customerOpenAccount);
			}
			/**
			 * 证券开户
			 */
			if (businessTypeList.contains(TenantConstant.STOCK_TYPE)) {
				result = openAccountService.customerStockOpenAccount(customerOpenAccount);

			}
			if(result.isSuccess()){
				/**
				 * 推送消息
				 */
				result.setData(data);
			}

		}
		log.info("客户开户返回结果：{}", JsonUtil.toJson(result));
		return result;
	}

	@Override
	public R goldDeposit(@RequestBody AmountDTO amountDTO) {
		String businessTypeList = environment.getProperty("tenant.business.type");
		String businessType = StringUtils.isNotBlank(amountDTO.getBusinessType())?amountDTO.getBusinessType():businessTypeList.split(",")[0];
		R result = R.success();
		if (TenantConstant.FUND_TYPE.equals(businessType)) {
			result =payService.goldDeposit(amountDTO);
			log.info("基金入金请求返回参数,{}",JsonUtil.toJson(result));
		} else if (TenantConstant.STOCK_TYPE.equals(businessType)) {
			FundDepositReq fundDepositReq =new FundDepositReq();
			/**
			 * 查询资金账号
			 */
			CustomerStockInfo customerStockInfo =appCustomerInfoService.selectStockAccount(amountDTO.getAccountId());
			fundDepositReq.setTradeAccount(customerStockInfo.getTradeAccount());
			fundDepositReq.setFundAccount(customerStockInfo.getCapitalAccounts().get(0).getCapitalAccount());
			fundDepositReq.setCurrency(amountDTO.getCurrency());
			fundDepositReq.setAmount(amountDTO.getAmount().toString());
			result =counterFundClient.deposit(fundDepositReq);
			log.info("证券入金请求返回参数,{}",JsonUtil.toJson(result));
		}
		return result;
	}

	@Override
	public R freezeAmount(AmountDTO amountDTO) {
		String businessTypeList = environment.getProperty("tenant.business.type");
		String businessType = StringUtils.isNotBlank(amountDTO.getBusinessType())?amountDTO.getBusinessType():businessTypeList.split(",")[0];

		R result = R.success();
		if (TenantConstant.FUND_TYPE.equals(businessType)) {
			result =payService.freezeAmount(amountDTO);
			log.info("基金冻结请求返回参数,{}",JsonUtil.toJson(result));
		} else if (TenantConstant.STOCK_TYPE.equals(businessType)) {
			FundFreezeReq fundFreezeReq =new FundFreezeReq();
			/**
			 * 查询资金账号
			 */
			CustomerStockInfo customerStockInfo =appCustomerInfoService.selectStockAccount(amountDTO.getAccountId());
			fundFreezeReq.setTradeAccount(customerStockInfo.getTradeAccount());
			fundFreezeReq.setFundAccount(customerStockInfo.getCapitalAccounts().get(0).getCapitalAccount());
			fundFreezeReq.setCurrency(amountDTO.getCurrency());
			fundFreezeReq.setAmount(amountDTO.getAmount().toString());
			fundFreezeReq.setOrderId(amountDTO.getBusinessId());
			result =counterFundClient.freeze(fundFreezeReq);
			log.info("证券冻结请求返回参数,{}",JsonUtil.toJson(result));
		}
		return result;


	}

	@Override
	public R thawingAmount(AmountDTO amountDTO) {

		Integer type = amountDTO.getType();
		String businessTypeList = environment.getProperty("tenant.business.type");
		String businessType = StringUtils.isNotBlank(amountDTO.getBusinessType())?amountDTO.getBusinessType():businessTypeList.split(",")[0];

		R result = R.success();
		if (TenantConstant.FUND_TYPE.equals(businessType)) {
			result =payService.thawingAmount(amountDTO);
			log.info("基金出金请求返回参数,{}",JsonUtil.toJson(result));
		} else if (TenantConstant.STOCK_TYPE.equals(businessType)) {

			if (type == 0){
				// 解冻并出金
				FundUnFreezeWithdrawReq fundUnFreezeWithdrawReq =new FundUnFreezeWithdrawReq();
				/**
				 * 查询资金账号
				 */
				CustomerStockInfo customerStockInfo =appCustomerInfoService.selectStockAccount(amountDTO.getAccountId());
				fundUnFreezeWithdrawReq.setTradeAccount(customerStockInfo.getTradeAccount());
				fundUnFreezeWithdrawReq.setFundAccount(customerStockInfo.getCapitalAccounts().get(0).getCapitalAccount());
				fundUnFreezeWithdrawReq.setCurrency(amountDTO.getCurrency());
				fundUnFreezeWithdrawReq.setAmount(amountDTO.getAmount().toString());
				fundUnFreezeWithdrawReq.setBusinessNumber(amountDTO.getBusinessId());
				result =counterFundClient.unFreezeWithdraw(fundUnFreezeWithdrawReq);
				log.info("证券出金请求返回参数,{}",JsonUtil.toJson(result));
			}
			else if (type == 1){
				// 解冻
				FundUnFreezeReq fundUnFreezeReq =new FundUnFreezeReq();
				/**
				 * 查询资金账号
				 */
				CustomerStockInfo customerStockInfo =appCustomerInfoService.selectStockAccount(amountDTO.getAccountId());
				fundUnFreezeReq.setTradeAccount(customerStockInfo.getTradeAccount());
				fundUnFreezeReq.setFundAccount(customerStockInfo.getCapitalAccounts().get(0).getCapitalAccount());
				fundUnFreezeReq.setCurrency(amountDTO.getCurrency());
				fundUnFreezeReq.setAmount(amountDTO.getAmount().toString());
				fundUnFreezeReq.setLockedCashId(amountDTO.getBusinessId());
				result =counterFundClient.unFreeze(fundUnFreezeReq);
				log.info("证券解冻请求返回参数,{}",JsonUtil.toJson(result));
			}else{
				return R.fail("参数错误");
			}

		}
		return result;

	}

	@Override
	public R scratchButton(AmountDTO amountDTO) {
		return payService.scratchButton(amountDTO);
	}

	@Override
	@PostMapping(CustomerClientConstants.ARCHIVE_USER_ASSETS)
	public R archiveUserAssets() {
		return customerInfoService.archiveUserAssets();
	}

	@Override
	@PostMapping(CustomerClientConstants.ORGANIZATION_OPEN_ACCOUNT)
	public R organizationOpenAccount(OrganizationOpenAccountVO organizationOpenAccountVO) {
		return openAccountService.organizationOpenAccount(organizationOpenAccountVO);
	}

	@Override
	@GetMapping(CustomerClientConstants.CUSTOMER_DEVICE_LIST)
	public R customerDeviceList(List<Long> userIds) {
		return R.data(custDeviceService.getDeviceList(userIds));
	}

	@Override
	@GetMapping(CustomerClientConstants.ALL_DEVICE_USER_ID_LIST)
	public R getAllUserDeviceIdList() {
		return R.data(custDeviceService.getAllUserDeviceIdList());
	}

	@Override
	@GetMapping(CustomerClientConstants.CUSTOMER_EMAIL_LIST)
	public R<String> customerEmailList(List<Long> userIds) {
		return R.data(custDeviceService.customerEmailList(userIds));
	}


	@Override
	@GetMapping(CustomerClientConstants.CUSTOMER_DEVICE_LIST_DETAIL)
	public R<CustomerDeviceInfoEntity> customerDeviceDetail(Long custId,String deviceCode) {
		return R.data(custDeviceService.getByDeviceCodeAndCustId(custId,deviceCode));
	}

	@Override
	public R<FinancingAccountAmount> accountAmount(String accountId, String currency) {
		return customerInfoService.accountAmount(accountId, currency);
	}

	@Override
	public R selectAccountIdByCustId(Long custId) {
		CustomerFinancingAccountEntity financingAccount = customerInfoService.selectAccountByCustId(custId);
		if (financingAccount == null){
			return R.fail(I18nUtil.getMessage(AccountMessageConstant.ACCOUNT_OPEN_FINANCING_ACCOUNT_NOT_EXIST_NOTICE));
		}
		return R.data(financingAccount.getAccountId());
	}

	@PostMapping(CustomerClientConstants.STATEMENT_LIST)
	public R reports(@RequestBody StatementPageDTO statementPageDTO) {
		return customerFileService.reports(statementPageDTO);
	}

	@Override
	public R exportStatementDaily(CustStatementDTO custStatementDTO) {
		customerStatementAccountService.exportHldBondDailyAccountList2(custStatementDTO);
		return R.success();
	}

	@Override
	public R exportStatementMonth(CustStatementDTO custStatementDTO) {
		customerStatementAccountService.exportHldBondMonthAccountList2(custStatementDTO);
		return R.success();
	}

	@Override
	public R updateCustomerDerivative(Long custId) {
		return customerInfoService.updateCustomerDerivative(custId);
	}

	@Override
	public R updateOpenAccountInfo(CustomerOpenAccountInfoDTO customerOpenAccount) {
		return customerInfoService.updateOpenAccountInfo(customerOpenAccount);
	}

	@Override
	public R<CustomerAccountDetailVO> selectCustomerDetailByAccountId(String accountId) {
		return customerInfoService.selectCustomerDetailByAccountId(accountId);

	}

	@Override
	public R<CustomerAccountDetailVO> selectCustomerDetailByCustId(Long custId) {
		return customerInfoService.selectCustomerDetailByCustId(custId);

	}

	@Override
	public R<CostPackage> selectByPackageNumber(String packageNumber) {
		return costPackageService.selectByPackageNumber(packageNumber);
	}

	@Override
	public R<List<CustomerAssetDetailVO>> selectCustomerDetailByTime(Date startTime, Date endTime) {
		return customerInfoService.selectCustomerDetailByTime(startTime,endTime);
	}

	@Override
	public R<CustomerAllAccountVO> selectAllAccountInfo(String accountId) {
		CustomerAllAccountVO customerAllAccountVO = new CustomerAllAccountVO();
		List<CustomerTradeAccountEntity> tradeAccountList =customerTradeAccountService.selectTradeByAccount(accountId);
		List<CustomerTradeSubAccountEntity> subAccountList =customerSubAccountService.selectByAccountId(accountId);
		List<CustomerTradeAccountVO> tradeAccountListVO = BeanUtil.copyToList(tradeAccountList,CustomerTradeAccountVO.class);
        List<CustomerTradeSubAccountVO> subAccountListVO = BeanUtil.copyToList(subAccountList,CustomerTradeSubAccountVO.class);
		customerAllAccountVO.setTradeAccountList(tradeAccountListVO);
		customerAllAccountVO.setSubAccountList(subAccountListVO);
		return R.data(customerAllAccountVO);
	}

	@Override
	public R<CustOpenAccountInfoVO> selectCustOpenAccountInfo(String custId) {
		CustOpenAccountInfoVO custOpenAccountInfoVO = new CustOpenAccountInfoVO();
		CustomerInfoEntity customerInfo =customerInfoMapper.selectByCustId(Long.valueOf(custId));
		custOpenAccountInfoVO.setCustomerInfo(customerInfo);
		CustomerBasicInfoEntity customerBasicInfo =customerBasicInfoMapper.selectByCustId(Long.valueOf(custId));
		custOpenAccountInfoVO.setCustomerBasicInfo(customerBasicInfo);
		CustomerRealnameVerifyEntity customerRealnameVerify =customerRealnameVerifyMapper.selectByCustId(Long.valueOf(custId));
		custOpenAccountInfoVO.setCucustomerRealnameVerify(customerRealnameVerify);


		CustomerFinancingAccountEntity financingAccount = customerFinancingAccountMapper.selectByCustId(Long.valueOf(custId));
		if(null != financingAccount){
			List<CustomerTradeAccountEntity> customerTradeAccountList =customerTradeAccountService.selectTradeByAccount(financingAccount.getAccountId());
			custOpenAccountInfoVO.setCustomerTradeAccountList(customerTradeAccountList);
		}

		return R.data(custOpenAccountInfoVO);
	}

	@Override
	public R<CustOpenAccountInfoVO> selectCustOpenAccountInfoByPhone(String phoneArea, String phoneNumber) {
		CustomerInfoEntity customerInfo =customerInfoMapper.selectByPhoneAndTenantId(phoneNumber, phoneArea, TokenConstant.DEFAULT_TENANT_ID);
	    if(null==customerInfo){
			R.fail(I18nUtil.getMessage(AccountMessageConstant.ACCOUNT_OPEN_SELECT_CUSTOMER_INFO_FAIL_NOT_EXIST_NOTICE));
		}
		return selectCustOpenAccountInfo(customerInfo.getId().toString());
	}
}
