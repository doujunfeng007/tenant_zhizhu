package com.minigod.zero.bpmn.module.deposit.openapi;

import com.minigod.zero.bpmn.module.constant.ErrorMessageConstant;
import com.minigod.zero.bpmn.module.deposit.bo.*;
import com.minigod.zero.bpmn.module.deposit.service.IClientFundService;
import com.minigod.zero.bpmn.module.deposit.vo.*;
import com.minigod.zero.biz.common.vo.CommonReqVO;
import com.minigod.zero.bpmn.module.feign.vo.CashDepositWay;
import com.minigod.zero.bpmn.module.feign.vo.DepositBankVO;
import com.minigod.zero.bpmn.module.feign.vo.ReceivingBankVO;
import com.minigod.zero.core.boot.ctrl.ZeroController;
import com.minigod.zero.core.i18n.utils.I18nUtil;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.log.annotation.ApiLog;
import com.minigod.zero.core.tool.api.R;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author dell
 */
@Slf4j
@Validated
@RestController
@AllArgsConstructor
@RequestMapping(AppConstant.OPEN_API_PREFIX + "/open_api")
public class ClientDepositController extends ZeroController {
	private final IClientFundService clientFundService;

	@ApiLog("查询资金记录")
	@RequestMapping("/get_records")
	public R<HistoryRecordVO> findHistoryRecord(@RequestBody CommonReqVO<HistoryRecordBo> bo) {
		HistoryRecordVO findDepositRecord = clientFundService.findHistoryRecord(bo.getParams());
		return R.data(findDepositRecord);
	}

	@ApiLog("获取银行卡信息")
	@RequestMapping("/deposit/record_deposited_banks")
	public R<List<BankCardInfoVO>> accountDepositBankInfo(@RequestBody CommonReqVO<AccountDepositBankBo> bo) {
		return R.data(clientFundService.findAccountDepositBankInfo(bo.getParams()));
	}

	@ApiLog("获取入金银行配置信息")
	@RequestMapping("/deposit/config_out_banks")
	public R<List<DepositBankVO>> depositBankInfo(@RequestBody CommonReqVO<DepositBankInfoBo> request) {
		return R.data(clientFundService.findDepositBank(request.getParams()));
	}

	@ApiLog("获取入金方式配置信息")
	@RequestMapping("/deposit/config_out_ways")
	public R<List<CashDepositWay>> depositTypeSettingInfo(@RequestBody final CommonReqVO<DepositBankInfoBo> request) {
		return clientFundService.getDepositWays(request.getParams().getMoneyType(), request.getParams().getBankType());
	}

	/**
	 * fps入金获取收款账户
	 *
	 * @param request
	 * @return
	 */
	@ApiLog("获取fps收款账户")
	@RequestMapping("/fps_deposit/receiving_account")
	public R<ReceivingBankVO> fpsReceivingAccount(@RequestBody final CommonReqVO<DepositBankInfoBo> request) {
		return R.data(clientFundService.fpsReceivingAccount(request.getParams()));
	}

	/**
	 * 网银入金 获取收款账户
	 *
	 * @param request
	 * @return
	 */
	@ApiLog("获取网银收款账户")
	@RequestMapping("/obt_deposit/receiving_account")
	public R<ReceivingBankVO> obtReceivingAccount(@RequestBody final CommonReqVO<DepositBankInfoBo> request) {
		return R.data(clientFundService.receivingAccount(request.getParams()));
	}

	@ApiLog("获取入金方式配置信息")
	@RequestMapping("/deposit_type_setting")
	public R<List<SecDepositTypeVO>> getDepositTypeSetting(Integer bankType, String key) {
		DepositBankInfoBo bo = new DepositBankInfoBo();
		bo.setBankType(bankType);
		bo.setKey(key);
		return R.data(clientFundService.findDepositTypeSetting(bo));
	}

	@RequestMapping(value = "/deposit/save_img")
	@ApiLog("保存入金凭证")
	public R<SecImgRespVo> depositFundImg(@RequestBody CommonReqVO<SecImgBo> request) {
		return R.data(clientFundService.saveSecAccImg(request.getParams()), I18nUtil.getMessage(ErrorMessageConstant.SUCCESS_SAVE_NOTICE));
	}

	@RequestMapping(value = "/deposit/get_img")
	@ApiLog("获取入金凭证列表")
	public R<List<SecImgRespVo>> getDepositFundImg(@RequestBody CommonReqVO<SecImgBo> request) {
		return R.data(clientFundService.findSecAccImg(request.getParams()));
	}

	@RequestMapping("/deposit/submit")
	@ApiLog("存入资金申请")
	public R saveIntoMoney(@RequestBody @Valid CommonReqVO<DepositVO> request) {
		return clientFundService.submitDepositFund(request.getParams());
	}

	@ApiLog("获取入金银行配置信息")
	@RequestMapping("/deposit/bankInfo/payeeBankDetailId")
	public R<List<DepositBankVO>> getDepositBankByPayeeBankDetailId(Long payeeBankDetailId,
																	String supportType,
																	String currency) {
		return R.data(clientFundService.getDepositBankByPayeeBankDetailId(payeeBankDetailId, supportType, currency));
	}

	@ApiLog("获取开户银行卡")
	@GetMapping("/customer_bank_card")
	public R getCustomerBankCard(String bankId, String bankCode) {
		return R.data(clientFundService.getCustomerBankCard(bankId, bankCode));
	}

	@ApiLog("获取入金方式配置信息")
	@RequestMapping("/deposit/ways")
	public R<List<CashDepositWay>> depositWays(@RequestBody final CommonReqVO<DepositBankInfoBo> request) {
		return clientFundService.getDepositWays(request.getParams().getMoneyType(), request.getParams().getBankType());
	}

	@ApiLog("手工入金")
	@PostMapping("/manual/deposit/submit")
	public R manualDeposit(@RequestBody ManualDepositVO manualDepositVO) {
		return clientFundService.manualDeposit(manualDepositVO);
	}
}
