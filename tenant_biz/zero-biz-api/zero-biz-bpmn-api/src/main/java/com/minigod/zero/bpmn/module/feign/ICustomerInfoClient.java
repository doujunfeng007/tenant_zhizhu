package com.minigod.zero.bpmn.module.feign;

import com.minigod.zero.bpmn.module.account.dto.OpenAccountDTO;
import com.minigod.zero.bpmn.module.account.vo.FundAccountVO;
import com.minigod.zero.bpmn.module.feign.dto.*;
import com.minigod.zero.bpmn.module.feign.vo.*;
import com.minigod.zero.core.tool.api.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author：eric
 * @Date：2024/4/26 11:00
 * @description：专业投资者PI认者通过后同步客户级别
 */
@FeignClient(value = "minigod-customer")
public interface ICustomerInfoClient {
    /**
     * 更新客户级别
     *
     * @param piLevel
     * @param custId
     * @return
     */
    @PostMapping("/customer/update_pi_level")
    R updatePiLevel(@RequestParam("piLevel") Integer piLevel, @RequestParam("custId") Long custId);

	@PostMapping("/customer/open_account")
	R openAccount(@RequestBody CustomerOpenAccountDTO customerOpenAccount);

	@PostMapping("/customer/open_account_new")
	R openAccountNew(@RequestBody CustomerOpenAccountDTO customerOpenAccount);

	@PostMapping("/customer/fund/gold_deposit")
	R goldDeposit(@RequestBody AmountDTO amountVO);

	@PostMapping("/customer/fund/freeze_amount")
	R freezeAmount(@RequestBody AmountDTO amountVO);

	@PostMapping("/customer/fund/thawing_amount")
	R thawingAmount(@RequestBody AmountDTO amountVO);

	@PostMapping("/customer/fund/scratch_button")
	R scratchButton(@RequestBody AmountDTO amountVO);

	@PostMapping("/fund/openAccount")
	R<FundAccountVO> fundOpenAccount(@RequestBody OpenAccountDTO fundOpenAccount);

	@PostMapping("/customer/archive_user_assets")
	R archiveUserAssets();

	/**
	 * 账户余额信息
	 * @param accountId
	 * @param currency
	 * @return
	 */
	@GetMapping("/customer/account_amount")
	R<FinancingAccountAmount> accountAmount(@RequestParam("accountId")String accountId,@RequestParam("currency")String currency);

	@PostMapping("/statement/reports")
	R reports(StatementPageDTO statementPageDTO);

	@GetMapping("/customer/select_account_id")
	R selectAccountIdByCustId(@RequestParam("custId") Long custId);

	@GetMapping("/detail")
	R<CustomerDetailVO> selectCustomerDetail(@RequestParam("custId")Long custId);
	@GetMapping("/customer/detailByAccountId")
	R<CustomerAccountDetailVO> selectCustomerDetailByAccountId(@RequestParam("accountId")String accountId);

	/**
	 * 导出日结单
	 * @param custStatementDTO
	 * @return
	 */
	@PostMapping("/customer/statement/exportStatementDaily")
	R exportStatementDaily(@RequestBody CustStatementDTO custStatementDTO);

	/**
	 * 导出月结单
	 * @param custStatementDTO
	 * @return
	 */
	@PostMapping("/customer/statement/exportStatementMonth")
	R exportStatementMonth(@RequestBody  CustStatementDTO custStatementDTO);


	@GetMapping("/account_info")
	R<CustomerAccountVO> customerAccountInfo(@RequestParam("custId") Long custId);

	/**
	 * 修改开户信息
	 */
	@PostMapping("/customer/open_account_info")
	R updateOpenAccountInfo(@RequestBody CustomerOpenAccountInfoDTO customerOpenAccount);


	/**
	 * 开债券户
	 * @param fundOpenAccount
	 * @return
	 */
	@PostMapping("/customer/open_otc_account")
	R openOtcAccount(@RequestBody OpenAccountDTO fundOpenAccount);


	/**
	 * 通过理财账号获取用户的所有账户信息
	 */
	@GetMapping("/customer/all_account_info")
	R<CustomerAccountVO> customerAllAccountInfo(@RequestParam("accountId") Long accountId);

	/**
	 * 通过理财账号获取用户的所有账户信息
	 */
	@GetMapping("/customer/allAccountByAccountId")
	R<CustomerAllAccountVO> customerAllAccountByAccountId(@RequestParam("accountId") Long accountId);

	/**
	 * 通过手机号查询用户的开户信息
	 */
	@GetMapping("/customer/custOpenAccountInfoByPhone")
	R<CustOpenAccountInfoVO> selectCustOpenAccountInfoByPhone(@RequestParam("phoneArea")String phoneArea, @RequestParam("phoneNumber")String phoneNumber);
}
