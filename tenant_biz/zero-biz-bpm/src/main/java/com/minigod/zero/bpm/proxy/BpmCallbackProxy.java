package com.minigod.zero.bpm.proxy;

import cn.hutool.json.JSONUtil;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.minigod.zero.bpm.service.ICashDepositFundsService;
import com.minigod.zero.bpm.service.ICashEddaInfoService;
import com.minigod.zero.bpm.service.ICashExtractingMoneyService;
import com.minigod.zero.bpm.utils.BpmRespCodeUtils;
import com.minigod.zero.bpm.vo.request.*;
import com.minigod.zero.bpm.entity.CashExtractingMoneyEntity;
import com.minigod.zero.bpm.vo.response.ResponseVO;
import com.minigod.zero.biz.common.vo.CommonReqVO;
import com.minigod.zero.bpm.service.openAccount.IChangeAccountService;
import com.minigod.zero.bpm.service.openAccount.IOpenAccountService;
import com.minigod.zero.bpm.vo.request.*;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.tool.api.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * BPM后台回调接口
 *
 * @author Zhe.Xiao
 */
@RestController
@RequestMapping(AppConstant.PROXY_API_PREFIX + "/bpm_callback")
@Api(value = "BPM后台回调接口", tags = "BPM后台回调接口")
@Slf4j
public class BpmCallbackProxy {

	@Resource
	private ICashEddaInfoService cashEddaInfoService;
	@Resource
	private ICashDepositFundsService cashDepositFundsService;
	@Resource
	private ICashExtractingMoneyService cashExtractingMoneyService;
	@Resource
	private IOpenAccountService openAccountService;
	@Resource
	private IChangeAccountService changeAccountService;

	@PostMapping("/update_edda_status")
	@ResponseBody
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "客户eDDA状态回调", notes = "客户eDDA状态回调")
	public ResponseVO updateEddaStatus(@RequestBody CommonReqVO<EddaInfoCallProtocol> request) {
		log.info(AppConstant.PROXY_API_PREFIX + "/bpm_callback/update_edda_status : " + JSONUtil.toJsonStr(request));
		return cashEddaInfoService.updateEddaStatus(request.getParams());
	}

	@PostMapping("/update_edda_funds_status")
	@ResponseBody
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "客户快捷入金状态回调", notes = "客户快捷入金状态回调")
	public ResponseVO updateEddaFundsStatus(@RequestBody CommonReqVO<ClientDepositFundsCallBackProtocol> request) {
		log.info(AppConstant.PROXY_API_PREFIX + "/bpm_callback/update_edda_funds_status : " + JSONUtil.toJsonStr(request));
		return cashDepositFundsService.updateEddaFundsStatus(request.getParams());
	}

	@PostMapping("/update_edda_funds_ApplicationId")
	@ResponseBody
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "快捷自动入金申请流水号更新回调", notes = "快捷自动入金申请流水号更新回调")
	public ResponseVO updateEddaFundsApplicationId(@RequestBody CommonReqVO<ClientEddaFundsCallBackProtocol> request) {
		log.info(AppConstant.PROXY_API_PREFIX + "/bpm_callback/update_edda_funds_ApplicationId : " + JSONUtil.toJsonStr(request));
		return cashDepositFundsService.updateEddaFundsApplicationId(request.getParams());
	}

	@PostMapping("/update_fund_withdraw_status")
	@ResponseBody
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "客户提取资金回调", notes = "客户提取资金回调")
	public ResponseVO updateFundWithdrawStatus(@RequestBody CommonReqVO<ClientFundWithdrawCallbackProtocol> request) {
		log.info(AppConstant.PROXY_API_PREFIX + "/bpm_callback/update_fund_withdraw_status : " + JSONUtil.toJsonStr(request));
		return cashExtractingMoneyService.updateFundWithdrawStatus(request.getParams());
	}

	@PostMapping("/update_deposit_funds_status")
	@ResponseBody
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "客户存入资金回调", notes = "客户存入资金回调")
	public ResponseVO updateDepositFundsStatus(@RequestBody CommonReqVO<ClientDepositFundsCallBackProtocol> request) {
		log.info(AppConstant.PROXY_API_PREFIX + "/bpm_callback/update_deposit_funds_status : " + JSONUtil.toJsonStr(request));
		return cashDepositFundsService.updateDepositFundsStatus(request.getParams());
	}

	@PostMapping("/update_open_user_info")
	@ResponseBody
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "BPM回调开户状态接口", notes = "BPM回调开户状态接口")
	public R updateOpenUserInfo(@RequestBody OpenAccResultCallbackProto proto) {
		log.info(AppConstant.PROXY_API_PREFIX + "/bpm_callback/update_open_user_info : " + JSONUtil.toJsonStr(proto));
		return openAccountService.updateOpenUserInfo(proto);
	}

	@PostMapping("/offline_open_account")
	@ResponseBody
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "BPM线下开户回调", notes = "BPM线下开户回调")
	public R offlineOpenAccount(@RequestBody OpenAccResultCallbackProto proto) {
		log.info(AppConstant.PROXY_API_PREFIX + "/bpm_callback/offline_open_account : " + JSONUtil.toJsonStr(proto));
		return openAccountService.offlineOpenAccount(proto);
	}

	@PostMapping("/change_account_check_status")
	@ResponseBody
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "线上修改资料bpm回调修改审核状态", notes = "线上修改资料bpm回调修改审核状态")
	public R changeAccountCheckStatus(@RequestBody SecUserInfoChangeCallbackProtocol proto) {
		log.info(AppConstant.PROXY_API_PREFIX + "/bpm_callback/change_account_check_status : " + JSONUtil.toJsonStr(proto));
		return changeAccountService.changeAccountCheckStatus(proto);
	}

	@PostMapping("/change_account_image")
	@ResponseBody
	@ApiOperationSupport(order = 9)
	@ApiOperation(value = "BPM回调中台更新客户图片凭证", notes = "BPM回调中台更新客户图片凭证")
	public R changeAccountImage(@RequestBody OpenAccountImgUpdateVo proto) {
		//TODO 看代码BPM未调用，暂不使用此接口
		log.info(AppConstant.PROXY_API_PREFIX + "/bpm_callback/change_account_image : " + JSONUtil.toJsonStr(proto));
		return changeAccountService.changeAccountImage(proto);
	}

	@PostMapping("/bs_withdraw_sync")
	@ResponseBody
	@ApiOperationSupport(order = 10)
	@ApiOperation(value = "银证出金同步APP出金记录", notes = "银证出金同步APP出金记录")
	public ResponseVO bsWithdrawSync(@RequestBody CashExtractingMoneyEntity cashExtractingMoneyEntity) {
		log.info(AppConstant.PROXY_API_PREFIX + "/bpm_callback/bs_withdraw_sync : " + JSONUtil.toJsonStr(cashExtractingMoneyEntity));
		cashExtractingMoneyService.save(cashExtractingMoneyEntity);
		return BpmRespCodeUtils.getSuccessMsg(new ResponseVO());
	}
}
