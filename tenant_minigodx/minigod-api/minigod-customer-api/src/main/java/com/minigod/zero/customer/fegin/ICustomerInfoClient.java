package com.minigod.zero.customer.fegin;

import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.customer.common.CustomerClientConstants;
import com.minigod.zero.customer.dto.*;
import com.minigod.zero.customer.entity.CostPackage;
import com.minigod.zero.customer.entity.CustomerDeviceInfoEntity;
import com.minigod.zero.customer.entity.FinancingAccountAmount;
import com.minigod.zero.customer.vo.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/4/26 11:00
 * @description：
 */
@FeignClient(value = "minigod-customer")
public interface ICustomerInfoClient {
	@GetMapping(CustomerClientConstants.LOAD_CUSTOMER_BY_USERNAME)
	R<CustomerInfoVO> loadCustomerByUsername(@RequestParam("userName") String userName,
											 @RequestParam("areaCode") String areaCode,
											 @RequestParam("tenantId") String tenantId,
											 @RequestParam("param") String param);

	@GetMapping(CustomerClientConstants.LOAD_CUSTOMER_BY_ACCOUNT)
	R<CustomerInfoVO> loadCustomerByAccount(@RequestParam("userName") String userName,
											@RequestParam("tenantId") String tenantId);


	@GetMapping(CustomerClientConstants.LOG_CUSTOMER_BY_SUB_ACCOUNT)
	R<CustomerInfoVO> loadCustomerBySubAccount(@RequestParam("accountId") String accountId,
											   @RequestParam("subAccount") String subAccount);

	@GetMapping(CustomerClientConstants.CUSTOMER_DEFAULT_REGISTER)
	R<CustomerInfoVO> defaultRegister(@RequestParam("phone") String phone,
									  @RequestParam("areaCode")String areaCode,
									  @RequestParam("tenantId")String tenantId);

	@PostMapping(CustomerClientConstants.UPDATE_PI_LEVEL)
	R updatePiLevel(@RequestParam("piLevel")Integer piLevel, @Param("custId")Long custId);

	/**
	 * 后续会弃用
	 * @param customerOpenAccount
	 * @return
	 */
	@PostMapping(CustomerClientConstants.CUSTOMER_OPEN_ACCOUNT)
	R openAccount(@RequestBody CustomerOpenAccountDTO customerOpenAccount);

	@PostMapping(CustomerClientConstants.CUSTOMER_OPEN_ACCOUNT_NEW)
	R openAccountNew(@RequestBody CustomerOpenAccountDTO customerOpenAccount);

	@PostMapping(CustomerClientConstants.GOLD_DEPOSIT)
	R goldDeposit(@RequestBody AmountDTO amountDTO);

	@PostMapping(CustomerClientConstants.FREEZE_AMOUNT)
	R<FinancingAccountAmount> freezeAmount(@RequestBody AmountDTO amountDTO);

	@PostMapping(CustomerClientConstants.THAWING_AMOUNT)
	R thawingAmount(@RequestBody AmountDTO amountDTO);

	@PostMapping(CustomerClientConstants.SCRATCH_BUTTON)
	R scratchButton(@RequestBody AmountDTO amountDTO);

	@PostMapping(CustomerClientConstants.ARCHIVE_USER_ASSETS)
	R archiveUserAssets();

	@PostMapping(CustomerClientConstants.ORGANIZATION_OPEN_ACCOUNT)
	R organizationOpenAccount(@RequestBody OrganizationOpenAccountVO organizationOpenAccountVO);

	@GetMapping(CustomerClientConstants.CUSTOMER_DEVICE_LIST)
	R<List<CustomerDeviceInfoEntity>> customerDeviceList(@RequestParam("userIds") List<Long> userIds);

	@GetMapping(CustomerClientConstants.ALL_DEVICE_USER_ID_LIST)
	R getAllUserDeviceIdList();

	@GetMapping(CustomerClientConstants.CUSTOMER_EMAIL_LIST)
	R<String> customerEmailList(@RequestParam("userIds") List<Long> userIds);

	@GetMapping(CustomerClientConstants.CUSTOMER_DEVICE_LIST_DETAIL)
	R<CustomerDeviceInfoEntity> customerDeviceDetail(@RequestParam("custId")Long custId,@RequestParam("deviceCode") String  deviceCode);

	@GetMapping(CustomerClientConstants.ACCOUNT_AMOUNT)
	R<FinancingAccountAmount> accountAmount(@RequestParam("accountId")String accountId,@RequestParam("currency")String currency);

	@PostMapping(CustomerClientConstants.STATEMENT_LIST)
	R reports(@RequestBody StatementPageDTO statementPageDTO);

	@GetMapping(CustomerClientConstants.SELECT_ACCOUNT_ID)
	R selectAccountIdByCustId(@RequestParam("custId") Long custId);

	@PostMapping(CustomerClientConstants.EXPORT_STATEMENT_DAILY)
	R exportStatementDaily(@RequestBody CustStatementDTO custStatementDTO);

	@PostMapping(CustomerClientConstants.EXPORT_STATEMENT_MONTH)
	R exportStatementMonth(@RequestBody CustStatementDTO custStatementDTO);

	@PutMapping(CustomerClientConstants.UPDATE_CUSTOMER_DERIVATIVE)
	R updateCustomerDerivative(@RequestParam("custId") Long custId);

	@PostMapping(CustomerClientConstants.UPDATE_CUSTOMER_OPEN_ACCOUNT_INFO)
	R updateOpenAccountInfo(@RequestBody CustomerOpenAccountInfoDTO customerOpenAccount);

	@GetMapping(CustomerClientConstants.LOAD_CUSTOMER_BY_ACCOUNT_ID)
	R<CustomerAccountDetailVO> selectCustomerDetailByAccountId(@RequestParam("accountId")String accountId);

	@GetMapping(CustomerClientConstants.LOAD_CUSTOMER_BY_CUST_ID)
	R<CustomerAccountDetailVO> selectCustomerDetailByCustId(@RequestParam("custId")Long custId);

	@GetMapping(CustomerClientConstants.SELECT_PACKAGE_BY_NUMBER)
	R<CostPackage> selectByPackageNumber(@RequestParam("packageNumber")String packageNumber);

	@GetMapping(CustomerClientConstants.LOAD_CUSTOMER_BY_TIME)
	R<List<CustomerAssetDetailVO>> selectCustomerDetailByTime(@RequestParam("startTime")@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date startTime, @RequestParam("endTime")@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")  Date endTime);

	@GetMapping(CustomerClientConstants.QUERY_ALL_ACCOUNT_INFO)
	R<CustomerAllAccountVO> selectAllAccountInfo(@RequestParam("accountId")String accountId);


	@GetMapping(CustomerClientConstants.QUERY_CUST_OPEN_ACCOUNT_INFO)
	R<CustOpenAccountInfoVO> selectCustOpenAccountInfo(@RequestParam("custId")String custId);

	@GetMapping(CustomerClientConstants.QUERY_CUST_OPEN_ACCOUNT_INFO_BY_PHONE)
	R<CustOpenAccountInfoVO> selectCustOpenAccountInfoByPhone(@RequestParam("phoneArea")String phoneArea,@RequestParam("phoneNumber")String phoneNumber);
}
