package com.minigod.zero.bpm.openapi;

import cn.hutool.json.JSONUtil;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.minigod.zero.bpm.service.*;
import com.minigod.zero.bpm.vo.request.*;
import com.minigod.zero.cms.vo.*;
import com.minigod.zero.biz.common.vo.CommonReqVO;
import com.minigod.zero.bpm.dto.acct.req.CashPolicyReqDto;
import com.minigod.zero.cms.entity.oms.CashWithdrawalsBankEntity;
import com.minigod.zero.cms.feign.oms.ICashDepositBankClient;
import com.minigod.zero.cms.feign.oms.ICashPayeeBankClient;
import com.minigod.zero.cms.feign.oms.ICashPayeeBankDetailClient;
import com.minigod.zero.cms.feign.oms.ICashWithdrawalsBankClient;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.api.ResultCode;
import com.minigod.zero.core.tool.support.Kv;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 出入金 控制器
 *
 * @author 掌上智珠
 * @since 2023-05-16
 */
@RestController
@AllArgsConstructor
@RequestMapping(AppConstant.OPEN_API_PREFIX + "/cash_api")
@Api(value = "出入金相关接口", tags = "出入金相关接口")
@Slf4j
public class BpmCashController {

	private final ICashDepositFundsService cashDepositFundsService;
	private final ICashExtractingMoneyService cashExtractingMoneyService;
	private final ICashEddaInfoService cashEddaInfoService;
	private final HsbcEddaCommonService hsbcEddaCommonService;
	private final ICashCommonService cashCommonService;
	private final ICashDepositAccountBankService cashDepositAccountBankService;
	private final ICashPayeeBankClient cashPayeeBankClient;
	private final ICashPayeeBankDetailClient cashPayeeBankDetailClient;
	private final ICashDepositBankClient cashDepositBankClient;
	private final ICashWithdrawalsBankClient cashWithdrawalsBankClient;


	@PostMapping("/save_into_money")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "存入资金", notes = "存入资金")
	public R saveIntoMoney(@RequestBody CommonReqVO<CashDepositFundsReqVo> request) {
		log.info("/cash_api/save_into_money : " + JSONUtil.toJsonStr(request));
		Long custId = AuthUtil.getCustId();
		return cashDepositFundsService.saveIntoMoney(custId, request.getParams());
	}

	@PostMapping("/edda_bank_save")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "eDDA提交保存接口", notes = "eDDA提交保存接口")
	public R saveEddaBank(@RequestBody CommonReqVO<CashEddaInfoReqVo> request) {
		//TODO 自测
		log.info("/cash_api/edda_bank_save : " + JSONUtil.toJsonStr(request));
		Long custId = AuthUtil.getCustId();
		if (request == null || request.getParams() == null) {
			return R.fail(ResultCode.PARAMETER_ERROR);
		}
		if (StringUtils.isBlank(request.getParams().getClientId())) {
			return R.fail(ResultCode.PARAMETER_ERROR.getCode(), "缺少参数clientId");
		}
		if (StringUtils.isBlank(request.getParams().getFundAccount())) {
			return R.fail(ResultCode.PARAMETER_ERROR.getCode(), "缺少参数fundAccount");
		}
		if (StringUtils.isBlank(request.getParams().getDepositAccount())) {
			return R.fail(ResultCode.PARAMETER_ERROR.getCode(), "缺少参数depositAccount");
		}
		if (StringUtils.isBlank(request.getParams().getDepositAccountName())) {
			return R.fail(ResultCode.PARAMETER_ERROR.getCode(), "缺少参数depositAccountName");
		}
		if (StringUtils.isBlank(request.getParams().getBankId())) {
			return R.fail(ResultCode.PARAMETER_ERROR.getCode(), "缺少参数bankId");
		}
		return cashEddaInfoService.saveEddaBank(custId, request.getParams());
	}

	@PostMapping("/edda_bank_retry")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "失败的eDDA授权记录重试", notes = "失败的eDDA授权记录重试")
	public R eddaBankRetry(@RequestBody CommonReqVO<CashEddaInfoReqVo> request) {
		//TODO 自测
		log.info("/cash_api/edda_bank_retry : " + JSONUtil.toJsonStr(request));
		Long custId = AuthUtil.getCustId();
		return cashEddaInfoService.eddaBankRetry(custId, request.getParams());
	}

	@PostMapping("/edda_bank_update")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "eDDA前端用户修改", notes = "eDDA前端用户修改")
	public R updateEddaBankInfo(@RequestBody CommonReqVO<CashEddaInfoReqVo> request) {
		//TODO 自测
		log.info("/cash_api/edda_bank_update : " + JSONUtil.toJsonStr(request));
		Long custId = AuthUtil.getCustId();
		return cashEddaInfoService.updateEddaBankInfo(custId, request.getParams());
	}

	@PostMapping("/edda_bank_delete")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "eDDA前端用户删除", notes = "eDDA前端用户删除")
	public R deleteEddaBank(@RequestBody CommonReqVO<CashEddaInfoReqVo> request) {
		//TODO 自测
		log.info("/cash_api/edda_bank_delete : " + JSONUtil.toJsonStr(request));
		Long custId = AuthUtil.getCustId();
		return cashEddaInfoService.deleteEddaBank(custId, request.getParams());
	}

	@PostMapping("/get_edda_bank_info")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "eDDA绑定列表查询", notes = "eDDA绑定列表查询")
	public R getEddaBankInfo(@RequestBody CommonReqVO<CashEddaInfoReqVo> request) {
		//TODO 自测
		log.info("/cash_api/get_edda_bank_info : " + JSONUtil.toJsonStr(request));
		Long custId = AuthUtil.getCustId();
		return cashEddaInfoService.getEddaBankInfo(custId, request.getParams());
	}

	@PostMapping("/otp_confirmation")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "汇丰eDDA短信验证码校验", notes = "汇丰eDDA短信验证码校验")
	public R otpConfirmation(@RequestBody CommonReqVO<CashEddaInfoReqVo> request) {
		//TODO 自测
		log.info("/cash_api/otp_confirmation : " + JSONUtil.toJsonStr(request));
		return hsbcEddaCommonService.otpConfirmation(request.getParams());
	}

	@PostMapping("/otp_regeneration")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "汇丰eDDA短信验证码重新生成", notes = "汇丰eDDA短信验证码重新生成")
	public R otpRegeneration(@RequestBody CommonReqVO<CashEddaInfoReqVo> request) {
		//TODO 自测
		log.info("/cash_api/otp_regeneration : " + JSONUtil.toJsonStr(request));
		return hsbcEddaCommonService.otpRegeneration(request.getParams());
	}

	@PostMapping("/extract_fund")
	@ApiOperationSupport(order = 9)
	@ApiOperation(value = "提取资金", notes = "提取资金")
	public R extractFund(@RequestBody CommonReqVO<ExtractMoneyReqVo> request) {
		log.info("/cash_api/extract_fund : " + JSONUtil.toJsonStr(request));
		if (request.getParams().getExtMethod() == null) {
			return R.fail(ResultCode.PARAMETER_ERROR);
		}
		if (request.getParams().getCurrency() == null) {
			return R.fail("请选择币种");
		}
		if (StringUtils.isBlank(request.getParams().getBankAccount())) {
			return R.fail("请填写银行账号");
		}
		if (StringUtils.isBlank(request.getParams().getSwiftCode())) {
			return R.fail("param swiftCode miss");
		}
		if (StringUtils.isBlank(request.getParams().getExtAccount())) {
			return R.fail("param extAccount miss");
		}
		if (request.getParams().getBusType() == null) {
			return R.fail("param busType miss");
		}
		if (request.getParams().getExtMethod() == null) {
			return R.fail("param extMethod miss");
		}
		Long custId = AuthUtil.getCustId();
		return cashExtractingMoneyService.extractFund(custId, request.getParams());
	}

	@PostMapping("/get_withdraw_img")
	@ApiOperationSupport(order = 10)
	@ApiOperation(value = "获取出金凭证", notes = "获取出金凭证")
	public R getWithdrawImg(@RequestBody CommonReqVO<CashImageReqVo> request) {
		log.info("/cash_api/get_withdraw_img : " + JSONUtil.toJsonStr(request));
		Long custId = AuthUtil.getCustId();
		return cashExtractingMoneyService.getWithdrawImg(custId, request.getParams().getImgIds());
	}

	@PostMapping("/find_extractable_money")
	@ApiOperationSupport(order = 11)
	@ApiOperation(value = "获取用户资金信息", notes = "获取用户资金信息")
	public R getExtractableMoney(@RequestBody CommonReqVO<AccountFundReqVo> request) {
		log.info("/cash_api/find_extractable_money : " + JSONUtil.toJsonStr(request));
		return cashExtractingMoneyService.getExtractableMoney(request.getParams());
	}

	@PostMapping("/bank_info")
	@ApiOperationSupport(order = 12)
	@ApiOperation(value = "获取用户银行卡信息", notes = "获取用户银行卡信息")
	public R bankInfo() {
		return cashCommonService.getBankInfo(AuthUtil.getCustId());
	}

	@PostMapping("/validata_purview")
	@ApiOperationSupport(order = 13)
	@ApiOperation(value = "获取用户出入金权限", notes = "获取用户出入金权限")
	public R validataPurview() {
		return cashCommonService.validataPurview(AuthUtil.getCustId());
	}

	@PostMapping("/cancel_withdrawal")
	@ApiOperationSupport(order = 14)
	@ApiOperation(value = "取消提取资金", notes = "取消提取资金")
	public R cancelWithdrawal(@RequestBody CommonReqVO<ExtractMoneyReqVo> request) {
		log.info("/cash_api/cancel_withdrawal : " + JSONUtil.toJsonStr(request));
		return cashExtractingMoneyService.cancelClientWithdraw(request.getParams());
	}

	@PostMapping("/deposit/bank_setting_info")
	@ApiOperationSupport(order = 15)
	@ApiOperation(value = "获取入金银行配置信息", notes = "获取入金银行配置信息")
	public R depositBankSettingInfo(@RequestBody CommonReqVO<DepositBankInfoReqVo> request) {
		log.info("/cash_api/deposit/bank_setting_info : " + JSONUtil.toJsonStr(request));
		List<DepositBankRespVo> depositBankSetting = cashDepositBankClient.findDepositBankSetting(request.getParams().getBankType());
		return R.data(depositBankSetting);
	}

	@PostMapping("/withdrawals/bank_setting_info")
	@ApiOperationSupport(order = 16)
	@ApiOperation(value = "获取出金银行配置信息", notes = "获取出金银行配置信息")
	public R withdrawalsBankSettingInfo(@RequestBody CommonReqVO<DepositBankInfoReqVo> request) {
		log.info("/cash_api/withdrawals/bank_setting_info : " + JSONUtil.toJsonStr(request));
		List<SecWithdrawalsBankVo> withdrawalsBankSetting = cashWithdrawalsBankClient.findWithdrawalsBankSetting(request.getParams().getBankType());
		return R.data(withdrawalsBankSetting);
	}

	@PostMapping("/withdrawals/bank_find")
	@ApiOperationSupport(order = 17)
	@ApiOperation(value = "获取香港出金银行配置信息", notes = "获取香港出金银行配置信息")
	public R withdrawalsBankFind(@RequestBody CommonReqVO<DepositBankInfoReqVo> request) {
		log.info("/cash_api/withdrawals/bank_find : " + JSONUtil.toJsonStr(request));
		if (request != null && request.getParams() != null && request.getParams().getBankType() == 2) {
			CashWithdrawalsBankEntity cashWithdrawalsBankEntity = cashWithdrawalsBankClient.withdrawalsBankFind(request.getParams().getBankType());
			return R.data(cashWithdrawalsBankEntity);
		} else {
			return R.fail(ResultCode.PARAMETER_ERROR);
		}
	}

	@PostMapping("/deposit/type_setting_info")
	@ApiOperationSupport(order = 18)
	@ApiOperation(value = "获取入金方式配置信息", notes = "获取入金方式配置信息")
	public R depositTypeSettingInfo(@RequestBody CommonReqVO<DepositBankInfoReqVo> request) {
		log.info("/cash_api/deposit/type_setting_info : " + JSONUtil.toJsonStr(request));
		List<SecDepositTypeVo> depositTypeSetting = cashDepositBankClient.findDepositTypeSetting(request.getParams().getBankType(), request.getParams().getKey());
		return R.data(depositTypeSetting);
	}

	@PostMapping("/withdraw_bank")
	@ApiOperationSupport(order = 19)
	@ApiOperation(value = "获取用户最近一笔出金成功的银行卡信息", notes = "获取用户最近一笔出金成功的银行卡信息")
	public R withdrawBank(@RequestBody CommonReqVO<ExtractMoneyReqVo> request) {
		log.info("/cash_api/withdraw_bank : " + JSONUtil.toJsonStr(request));
		Long custId = AuthUtil.getCustId();
		return cashExtractingMoneyService.withdrawSuccessBank(custId, request.getParams().getExtMethod());
	}

	@PostMapping("/deposit_bank_cancel")
	@ApiOperationSupport(order = 20)
	@ApiOperation(value = "客户取消汇款银行卡绑定", notes = "客户取消汇款银行卡绑定")
	public R depositBankCancel(@RequestBody CommonReqVO<DepositAccountBankCancelRepVo> request) {
		log.info("/cash_api/deposit_bank_cancel : " + JSONUtil.toJsonStr(request));
		return cashDepositAccountBankService.depositAccountBankCancel(AuthUtil.getCustId(), request.getParams());
	}

	@PostMapping("/find_deposit_bank_by_area")
	@ApiOperationSupport(order = 21)
	@ApiOperation(value = "根据区域获取按汇款方式转账的银行列表", notes = "根据区域获取按汇款方式转账的银行列表")
	public R findDepositBankByArea(@RequestBody CommonReqVO<DepositBankListVo> request) {
		log.info("/cash_api/find_deposit_bank_by_area : " + JSONUtil.toJsonStr(request));
		Long custId = AuthUtil.getCustId();
		Kv depositBankByArea = cashDepositBankClient.findDepositBankByArea(custId, request.getParams().getBankType());
		return R.data(depositBankByArea);
	}

	@RequestMapping("/find_payee_bank")
	@ApiOperation(value = "获取付款账户列表", notes = "获取付款账户列表")
	public R findPayeeBankList(@RequestBody CommonReqVO<PayeeBankInfoReq> request) {
		if (request == null || request.getParams() == null) {
			return R.fail("无有效参数，请检查后重试");
		}
		PayeeBankInfoReq params = request.getParams();
		if (params.getBankType() == null) {
			if (params.getDepositId() == null || params.getSupportType() == null || params.getCurrency() == null) {
				return R.fail("必要参数缺失，请检查后重试");
			}
		}
		List<CashPayeeBankListVo> payeeBankList = cashPayeeBankClient.findPayeeBankList(params);
		if (CollectionUtils.isEmpty(payeeBankList)) {
			return R.fail("未获取到付款账户，请切换其他银行");
		}
		return R.data(payeeBankList);
	}

	@PostMapping("/find_support_info_by_deposit")
	@ApiOperationSupport(order = 22)
	@ApiOperation(value = "根据银行获取转账类型信息", notes = "根据银行获取转账类型信息")
	public R findSupportInfoByDeposit(@RequestBody CommonReqVO<PayeeBankInfoReq> request) {
		log.info("/cash_api/find_support_info_by_deposit : " + JSONUtil.toJsonStr(request));
		Map<String, Object> map = cashPayeeBankDetailClient.findSupportInfoByDeposit(request.getParams().getDepositId());
		return R.data(map);
	}

	@PostMapping("/deposit_bank")
	@ApiOperationSupport(order = 23)
	@ApiOperation(value = "获取用户入金成功银行卡信息", notes = "获取用户入金成功银行卡信息")
	public R depositBankInfo(@RequestBody CommonReqVO<SecDepositBankVo> request) {
		log.info("/cash_api/deposit_bank : " + JSONUtil.toJsonStr(request));
		return cashDepositAccountBankService.depositBankInfo(request.getParams());
	}

	@PostMapping("/withdraw_bank_list")
	@ApiOperationSupport(order = 24)
	@ApiOperation(value = "获取用户最近出金成功银行卡信息列表", notes = "获取用户最近出金成功银行卡信息列表")
	public R withdrawBankList() {
		Long custId = AuthUtil.getCustId();
		return cashExtractingMoneyService.withdrawSuccessBankList(custId);
	}

	@PostMapping("/bs_bind_status")
	@ApiOperationSupport(order = 25)
	@ApiOperation(value = "获取用户银证绑定状态", notes = "获取用户银证绑定状态")
	public R bsBindStatus() {
		Long custId = AuthUtil.getCustId();
		return cashCommonService.bsBindStatus(custId);
	}

	@PostMapping("/find_deposit_record")
	@ApiOperationSupport(order = 26)
	@ApiOperation(value = "资金存入记录查询", notes = "资金存入记录查询")
	public R findDepositRecord(@RequestBody CommonReqVO<HistoryRecordReqVo> req) {
		log.info("资金存入记录查询securities/find_deposit_record:" + JSONUtil.toJsonStr(req));
		if (req == null || req.getParams() == null) {
			return R.fail(ResultCode.PARAM_MISS);
		}
		Long custId = AuthUtil.getCustId();
		if (custId == null) {
			return R.fail(ResultCode.SESSION_INVALID);
		}
		if (req.getParams().getType() == null || req.getParams().getType() > 2) {
			return R.fail(ResultCode.PARAMETER_ERROR);
		}
		return cashDepositFundsService.findDepositRecord(custId, req.getParams());
	}

	@PostMapping("/save_img")
	@ApiOperationSupport(order = 27)
	@ApiOperation(value = "存取资金图片上传", notes = "存取资金图片上传")
	public R<Kv> saveImg(@RequestBody CommonReqVO<CashImageReqVo> request) {
		return cashCommonService.saveImg(request.getParams());
	}

	@PostMapping("/get_img")
	@ApiOperationSupport(order = 28)
	@ApiOperation(value = "获取图片资源", notes = "获取图片资源")
	public R findAccImg(@RequestBody CommonReqVO<CashImageReqVo> request) {
		log.info("/cash_api/get_img : " + JSONUtil.toJsonStr(request));
		Long custId = AuthUtil.getCustId();
		return cashCommonService.findAccImg(custId, request.getParams().getImgIds());
	}

	@PostMapping("/save_money_check_account")
	@ApiOperationSupport(order = 29)
	@ApiOperation(value = "存入资金账号入金记录数量", notes = "存入资金账号入金记录数量")
	public R saveMoneyCheckAccount(@RequestBody CommonReqVO<SecDepositFundsVo> request) {
		log.info("/cash_api/save_money_check_account : " + JSONUtil.toJsonStr(request));
		Long custId = AuthUtil.getCustId();
		return cashDepositFundsService.saveMoneyCheckAccount(request.getParams());
	}

	@PostMapping("/add_policy_payment")
	@ApiOperationSupport(order = 30)
	@ApiOperation(value = "万通出金申请", notes = "万通出金申请")
	public R addPolicyPayment(@RequestBody CommonReqVO<CashPolicyReqDto> request) {
		if(null == request || null == request.getParams() ) return R.fail(ResultCode.PARAM_VALID_ERROR);
		return cashDepositFundsService.addPolicyPayment(request.getParams());
	}
}
