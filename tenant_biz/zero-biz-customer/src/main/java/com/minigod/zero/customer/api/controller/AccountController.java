package com.minigod.zero.customer.api.controller;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.minigod.zero.biz.common.constant.CommonConstant;
import com.minigod.zero.core.i18n.utils.I18nUtil;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.utils.StringUtil;
import com.minigod.zero.customer.api.service.AppCustomerInfoService;
import com.minigod.zero.customer.api.service.ICustAccountInfoService;
import com.minigod.zero.customer.api.service.ICustInfoService;
import com.minigod.zero.customer.dto.ReqUpdateCustDTO;
import com.minigod.zero.customer.dto.ReqUpdatePwdDTO;
import com.minigod.zero.customer.vo.ClientAccountVO;
import com.minigod.zero.customer.vo.CustAccountInfoVO;
import com.minigod.zero.customer.vo.CustCapitalInfoVO;
import com.minigod.zero.platform.dto.OpenAccountDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/5/8 9:44
 * @description：账户相关接口
 */
@RestController
public class AccountController {

	@Autowired
	private AppCustomerInfoService customerInfoService;

	@Resource
	private ICustInfoService custInfoService;

	@Resource
	private ICustAccountInfoService custAccountInfoService;

	/**
	 * 基金开户
	 * @return
	 */
	@PostMapping("/fund/openAccount")
	public R fundOpenAccount(@RequestBody OpenAccountDTO fundOpenAccount){
		return customerInfoService.fundOpenAccount(fundOpenAccount);
	}

	/**
	 * 活利得开户
	 * @param fundOpenAccount
	 * @return
	 */
	@PostMapping("/hld/openAccount")
	public R hldOpenAccount(@RequestBody OpenAccountDTO fundOpenAccount){
		return R.data(customerInfoService.hldOpenAccount(fundOpenAccount));
	}

	/**
	 * 债券开户
	 * @param fundOpenAccount
	 * @return
	 */
	@PostMapping("/bond/openAccount")
	public R bondOpenAccount(@RequestBody OpenAccountDTO fundOpenAccount){
		return R.data(customerInfoService.bondOpenAccount(fundOpenAccount));
	}


	/**
	 * 开债券户
	 * @param fundOpenAccount
	 * @return
	 */
	@PostMapping("/customer/open_otc_account")
	public R  openOtcAccount(@RequestBody OpenAccountDTO fundOpenAccount){
		return R.data(customerInfoService.openOtcAccount(fundOpenAccount));
	}


	/**
	 * 查询客户账户金额信息 换算成统一币种
	 * @return
	 */
	@GetMapping("/account_balance")
	public R accountTotalBalance(String currency,String accountId){
		return customerInfoService.accountTotalBalance(currency,accountId);
	}

	/**
	 * 查询客户账户金额信息 区分币种
	 * @return
	 */
	@GetMapping("/account_balance/detail")
	public R selectAccountBalance(String currency,String accountId){
		return customerInfoService.selectAccountBalance(currency,accountId);
	}

	/**
	 * 账号信息查询
	 */
	@PostMapping("/acctInfo")
	public R<ClientAccountVO> accountInfo() {
		return custInfoService.accountInfo();
	}

	/**
	 * 获取客户交易账号列表
	 */
	@GetMapping("/get_cust_account_list")
	public R<List<CustAccountInfoVO>> getAccountList() {
		return R.data(custAccountInfoService.getAccountList());
	}

	/**
	 * 获取客户资金账号列表
	 */
	@GetMapping("/get_cust_capital_list")
	public R<List<CustCapitalInfoVO>> getCapitalList(@RequestParam String moneyType) {
		return R.data(custAccountInfoService.getCapitalList(moneyType));
	}

	/**
	 * 切换交易账号
	 * @param req
	 * @return
	 */
	@PostMapping("/switch_trade_acct")
	public R switchTradeAcct(@RequestBody ReqUpdateCustDTO req) {
		if (req == null || StringUtils.isBlank(req.getTradeAccount())) {
			return R.fail(I18nUtil.getMessage(CommonConstant.TRADING_ACCOUNT_CANNOT_BE_EMPTY));
		}
		return custAccountInfoService.switchTradeAcct(req.getTradeAccount());
	}

	/**
	 * 切换资金账号
	 * @param req
	 * @return
	 */
	@PostMapping("/switch_capital_acct")
	public R switchCapitalAcct(@RequestBody ReqUpdateCustDTO req) {
		if (req == null || StringUtils.isBlank(req.getTradeAccount()) || StringUtils.isBlank(req.getCapitalAccount())) {
			return R.fail(I18nUtil.getMessage(CommonConstant.TRADING_ACCOUNT_CANNOT_BE_EMPTY));
		}
		return custAccountInfoService.switchCapitalAcct(req);
	}

	/**
	 * 修改交易密码
	 * @return
	 */
	@PostMapping("/update_trade_pwd")
	public R updateTradPwd(@RequestBody ReqUpdatePwdDTO dto){
		return custInfoService.updateTradPwd(dto);
	}

	/**
	 * 校验交易密码
	 * @return
	 */
	@PostMapping("/check_trade_pwd")
	public R checkTradPwd(@RequestBody ReqUpdatePwdDTO dto){
		return custInfoService.checkTradPwd(dto);
	}


	@PostMapping("/check_trade_pwd_for_inside")
	public R checkTradPwdForInside(@RequestBody ReqUpdatePwdDTO dto){
		return custInfoService.checkTradPwd(dto.getNewPassword(),dto.getCustId());
	}
	/**
	 * 重置交易密码
	 * @param vo
	 * @return
	 */
	@PostMapping("/reset_trade_pwd")
	public R resetTradePwd(@RequestBody ReqUpdatePwdDTO vo) {
		if (vo == null || StringUtil.isBlank(vo.getNewPassword()) ||StringUtil.isBlank(vo.getCaptchaCode())
			|| StringUtil.isBlank(vo.getCaptchaKey())) {
			return R.fail(I18nUtil.getMessage(CommonConstant.MISSING_PASSWORD_AND_VERIFICATION_CODE));
		}
		if (StringUtil.isBlank(vo.getEmail())) {
			return R.fail(I18nUtil.getMessage(CommonConstant.PHONE_NUMBER_CANNOT_BE_EMPTY));
		}
		return custInfoService.resetTradePwd(vo);
	}

	/**
	 * 更新活利得风险等级
	 * @param riskLevel
	 * @return
	 */
	@PostMapping("/update_hld_account_risk_level")
	public R updateHldAccountRiskLevel(@RequestParam("riskLevel") Integer riskLevel){
		return customerInfoService.updateHldAccountRiskLevel(riskLevel);
	}
}
