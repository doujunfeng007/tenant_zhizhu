package com.minigod.zero.bpm.openapi;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.minigod.zero.bpm.service.IBpmOtherBankInfoService;
import com.minigod.zero.bpm.vo.request.CashDepositBankQueryReq;
import com.minigod.zero.bpm.entity.BpmOtherBankInfoEntity;
import com.minigod.zero.bpm.vo.BankInfoVO;
import com.minigod.zero.bpm.vo.request.OtherBankInfoQueryReq;
import com.minigod.zero.cms.entity.oms.CashDepositBankCurrencyGuideEntity;
import com.minigod.zero.cms.entity.oms.CashDepositBankEntity;
import com.minigod.zero.cms.entity.oms.CashDepositBankGuideEntity;
import com.minigod.zero.cms.feign.oms.ICashDepositBankClient;
import com.minigod.zero.cms.feign.oms.ICashDepositBankCurrencyGuideClient;
import com.minigod.zero.cms.feign.oms.ICashDepositBankGuideClient;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.tool.api.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 银行卡相关 控制器
 *
 * @author zejie.weng
 * @since 2023-05-26
 */
@RestController
@AllArgsConstructor
@RequestMapping(AppConstant.OPEN_API_PREFIX + "/bank_api")
@Api(value = "银行卡相关接口", tags = "银行卡相关接口")
@Slf4j
public class BpmBankController {

	private final IBpmOtherBankInfoService bpmOtherBankInfoService;

	private final ICashDepositBankClient cashDepositBankClient;

	private final ICashDepositBankGuideClient cashDepositBankGuideClient;

	private final ICashDepositBankCurrencyGuideClient cashDepositBankCurrencyGuideClient;


	/**
	 * 其他银行列表 列表
	 */
	@GetMapping("/findDepositBankList")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "获取其他银行列表", notes = "传入cashDepositBankQueryReq")
	public R<List<BankInfoVO>> findDepositBankList(CashDepositBankQueryReq cashDepositBankQueryReq) {
		List<BankInfoVO> bankList = bpmOtherBankInfoService.findDepositBankList(cashDepositBankQueryReq);
		return R.data(bankList);
	}

	/**
	 * 根据swiftCode or bankName查询bankId
	 */
	@GetMapping(value = "/otherBankInfoBySwiftOrName")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "根据swiftCode or bankName查询bankId", notes = "传入otherBankInfoQueryReq")
	public R<String[]> otherBankInfo(OtherBankInfoQueryReq otherBankInfoQueryReq) {
		String[] resultList = bpmOtherBankInfoService.otherBankInfo(otherBankInfoQueryReq);
		return R.data(resultList);
	}

	/**
	 * 获取其他银行卡列表信息
	 */
	@GetMapping(value = "/otherBankInfoList")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "获取其他银行卡信息", notes = "传入otherBankInfoQueryReq")
	public R<List<BpmOtherBankInfoEntity>> otherBankInfoList(OtherBankInfoQueryReq otherBankInfoQueryReq) {
		List<BpmOtherBankInfoEntity> otherBankInfoEntityList = bpmOtherBankInfoService.otherBankInfoList(otherBankInfoQueryReq);
		return R.data(otherBankInfoEntityList);
	}

	/**
	 * 获取用户银行卡信息
	 */
	/*@PostMapping(value = "/bank_info")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "获取用户银行卡信息", notes = "查询登录用户银行卡信息")
	public R<AcctOpenBankInfoExtVO> bankInfo() {
		AcctOpenBankInfoExtVO otherBankInfoEntityList = cashDepositBankService.bankInfo();
		return R.data(otherBankInfoEntityList);
	}*/

	/**
	 * 入金银行信息维护表 详情
	 */
	@GetMapping("/cashDepositBankDetail")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "入金银行信息维护表-详情", notes = "传入cashDepositBank")
	public R<CashDepositBankEntity> cashDepositBankDetail(CashDepositBankEntity cashDepositBank) {
		CashDepositBankEntity detail = cashDepositBankClient.cashDepositBankDetail(cashDepositBank);
		return R.data(detail);
	}

	/**
	 * 获取入金银行转账方式说明 详情
	 */
	@GetMapping("/cashDepositBankGuideDetail")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "获取入金银行转账方式说明 详情", notes = "传入cashDepositBankGuide")
	public R<CashDepositBankGuideEntity> detail(CashDepositBankGuideEntity cashDepositBankGuide) {
		CashDepositBankGuideEntity detail = cashDepositBankGuideClient.cashDepositBankGuideDetail(cashDepositBankGuide);
		return R.data(detail);
	}

	/**
	 * 获取入金银行入金指引 详情
	 */
	@GetMapping("/cashDepositBankCurrencyGuideDetail")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "获取入金银行入金指引 详情", notes = "传入cashDepositBankCurrencyGuide")
	public R<CashDepositBankCurrencyGuideEntity> cashDepositBankCurrencyGuideDetail(CashDepositBankCurrencyGuideEntity cashDepositBankCurrencyGuide) {
		CashDepositBankCurrencyGuideEntity detail = cashDepositBankCurrencyGuideClient.cashDepositBankCurrencyGuideDetail(cashDepositBankCurrencyGuide);
		return R.data(detail);
	}
}
