package com.minigod.zero.bpmn.module.withdraw.openapi;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.minigod.zero.biz.common.utils.RestTemplateUtil;
import com.minigod.zero.biz.common.vo.CommonReqVO;
import com.minigod.zero.bpmn.module.common.group.AddGroup;
import com.minigod.zero.bpmn.module.common.group.QueryGroup;
import com.minigod.zero.bpmn.module.constant.CashConstant;
import com.minigod.zero.bpmn.module.constant.WithdrawalsFundMessageConstant;
import com.minigod.zero.bpmn.module.deposit.bo.AccountDepositBankBo;
import com.minigod.zero.bpmn.module.deposit.bo.BankCardReviewBo;
import com.minigod.zero.bpmn.module.deposit.dto.BankCardImageDTO;
import com.minigod.zero.bpmn.module.deposit.entity.BankCardInfo;
import com.minigod.zero.bpmn.module.deposit.service.BankCardApplicationService;
import com.minigod.zero.bpmn.module.deposit.service.BankCardImageService;
import com.minigod.zero.bpmn.module.deposit.service.BankCardInfoService;
import com.minigod.zero.bpmn.module.deposit.service.BankCardReviewInfoService;
import com.minigod.zero.bpmn.module.deposit.vo.BankCardApplicationVO;
import com.minigod.zero.bpmn.module.deposit.vo.BankCardInfoVO;
import com.minigod.zero.bpmn.module.feign.ICashBankClient;
import com.minigod.zero.bpmn.module.feign.ICustomerInfoClient;
import com.minigod.zero.bpmn.module.feign.vo.CustomerDetailVO;
import com.minigod.zero.bpmn.module.withdraw.bo.BankFeeConfigBo;
import com.minigod.zero.bpmn.module.withdraw.bo.ClientFundWithdrawApplicationBo;
import com.minigod.zero.bpmn.module.withdraw.bo.ClientFundWithdrawInfoBo;
import com.minigod.zero.bpmn.module.withdraw.bo.ClientTeltransferInfoBo;
import com.minigod.zero.bpmn.module.withdraw.entity.BankFeeConfig;
import com.minigod.zero.bpmn.module.withdraw.enums.SystemCommonEnum;
import com.minigod.zero.bpmn.module.withdraw.request.*;
import com.minigod.zero.bpmn.module.withdraw.service.IBankFeeConfigService;
import com.minigod.zero.bpmn.module.withdraw.service.IClientFundWithdrawApplicationService;
import com.minigod.zero.bpmn.module.withdraw.util.ConstKey;
import com.minigod.zero.bpmn.module.withdraw.vo.ClientFundWithdrawApplicationVo;
import com.minigod.zero.bpmn.utils.FileUtils;
import com.minigod.zero.core.boot.ctrl.ZeroController;
import com.minigod.zero.core.i18n.utils.I18nUtil;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.log.annotation.ApiLog;
import com.minigod.zero.core.mp.support.Condition;
import com.minigod.zero.core.mp.support.Query;
import com.minigod.zero.core.redis.lock.RedisLock;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.support.Kv;
import com.minigod.zero.core.tool.utils.StringUtil;
import com.minigod.zero.dbs.util.DecimalUtils;
import com.minigod.zero.system.feign.IDictClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @ClassName: ClientWithdrawController
 * @Description:
 * @Author chenyu
 * @Date 2024/3/23
 * @Version 1.0
 */
@RestController
@RequestMapping(AppConstant.OPEN_API_PREFIX + "/open_api")
@Validated
@Slf4j
@AllArgsConstructor
@Api(value = "出金接口", tags = "出金接口")
public class ClientWithdrawController extends ZeroController {

	private final IDictClient dictClient;
	private final ICashBankClient cashBankClient;
	private final ICustomerInfoClient customerInfoClient;
	private final BankCardInfoService bankCardInfoService;
	private final IBankFeeConfigService bankFeeConfigService;
	private final BankCardReviewInfoService bankCardReviewInfoService;
	private final BankCardApplicationService bankCardApplicationService;
	private final BankCardImageService bankCardImageService;
	private final IClientFundWithdrawApplicationService fundWithdrawApplicationService;
	@Resource
	private RestTemplateUtil restTemplateUtil;

	@ResponseBody
	@ApiLog("提交出金申请")
	@ApiOperation("提交出金申请")
	@RequestMapping("/applyWithdraw")
	@RedisLock(value = "lock:fund:apply_withdraw")
	public R<String> applyWithdraw(@Validated({AddGroup.class}) @RequestBody CommonReqVO<ApplyWithdrawlRequest> requestVo) {
		ApplyWithdrawlRequest request = requestVo.getParams();
		if ((request.getRecvBankCode() == null && request.getBankCode() == null) || request.getBankId() == null) {
			return R.fail(I18nUtil.getMessage(WithdrawalsFundMessageConstant.WITHDRAWALS_FUND_SUBMIT_BANK_CODE_ID_EMPTY_NOTICE));
		}
		// 暂不使用ApplyWithdrawlRequest参数中的withdrawalsId
		// 提交出金申请逻辑增加根据bankCode和bankId查询用户绑定的银行卡对应的出金银行主键ID。
		R<Long> withdrawBankIdResult = cashBankClient.withdrawBankId(request.getRecvBankCode() == null ? request.getBankCode() : request.getRecvBankCode(), request.getBankId());
		if (!withdrawBankIdResult.isSuccess()) {
			return R.fail(I18nUtil.getMessage(WithdrawalsFundMessageConstant.WITHDRAWALS_FUND_QUERY_WITHDRAWAL_BANK_FAILED_NOTICE));
		}
		Long withdrawBankId = withdrawBankIdResult.getData();
		if (withdrawBankId == null) {
			return R.fail(I18nUtil.getMessage(WithdrawalsFundMessageConstant.WITHDRAWALS_FUND_QUERY_WITHDRAWAL_BANK_CONFIG_FAILED_NOTICE));
		}
		ClientFundWithdrawInfoBo fundWithdrawInfoBo = new ClientFundWithdrawInfoBo();
		fundWithdrawInfoBo.setWithdrawalsId(withdrawBankId);
		R<CustomerDetailVO> result = customerInfoClient.selectCustomerDetail(AuthUtil.getTenantCustId());
		if (!result.isSuccess()) {
			log.error("客户custId = {} 提交出金申请失败！错误信息：{}", AuthUtil.getTenantCustId(), result.getMsg());
			return R.fail(I18nUtil.getMessage(WithdrawalsFundMessageConstant.WITHDRAWALS_FUND_SUBMIT_FAILED_NOTICE));
		}
		CustomerDetailVO customerDetail = result.getData();
		if (StringUtils.isEmpty(customerDetail.getAccountId())) {
			return R.fail(I18nUtil.getMessage(WithdrawalsFundMessageConstant.WITHDRAWALS_FUND_QUERY_FUND_ACCOUNT_FAILED_NOTICE));
		}
		//客户信息
		Integer gender = customerDetail.getGender();
		fundWithdrawInfoBo.setUserId(customerDetail.getCustId());
		fundWithdrawInfoBo.setClientId(customerDetail.getAccountId());
		fundWithdrawInfoBo.setFundAccount(customerDetail.getAccountId());
		fundWithdrawInfoBo.setPhoneNumber(customerDetail.getPhoneNum());
		fundWithdrawInfoBo.setClientName(customerDetail.getCustomerName());
		fundWithdrawInfoBo.setPhoneAreaCode(customerDetail.getOpenAreaCode());
		fundWithdrawInfoBo.setSex(gender == null ? null : String.valueOf(gender));
		fundWithdrawInfoBo.setClientNameSpell(customerDetail.getCustomerEnName());
		//收款信息
		BankCardInfo bankCardInfo = bankCardInfoService.checkClientBankNo(fundWithdrawInfoBo.getClientId(), request.getRecvBankAcct());
		Integer iTransferType = request.getTransferType();
		fundWithdrawInfoBo.setRecvBankCode(bankCardInfo.getBankCode());
		fundWithdrawInfoBo.setRecvBankAcct(bankCardInfo.getBankNo());
		fundWithdrawInfoBo.setRecvBankName(bankCardInfo.getBankName());
		fundWithdrawInfoBo.setRecvSwiftCode(bankCardInfo.getSwiftCode());

		//如果是其他银行，则取手动输入的银行id
		fundWithdrawInfoBo.setRecvBankId(bankCardInfo.getBankId());
		if (bankCardInfo.getBankName().contains("其他银行")) {
			fundWithdrawInfoBo.setRecvBankId(requestVo.getParams().getBankId());
		}

		fundWithdrawInfoBo.setRecvBankAcctName(bankCardInfo.getBankAccount());
		fundWithdrawInfoBo.setRecvSwiftCode(bankCardInfo.getSwiftCode());
		fundWithdrawInfoBo.setRecvBankType(bankCardInfo.getBankType());
		//取款信息
		fundWithdrawInfoBo.setPayWay(iTransferType);
		fundWithdrawInfoBo.setCcy(request.getCcy());
		fundWithdrawInfoBo.setEntrustTime(new Date());
		fundWithdrawInfoBo.setTransferType(iTransferType);
		fundWithdrawInfoBo.setDeductWay(request.getDeductWay());
		fundWithdrawInfoBo.setBankAcctType(request.getBankAcctType());
		fundWithdrawInfoBo.setChargeFee(DecimalUtils.toDecimal(request.getChargeFee()));
		fundWithdrawInfoBo.setWithdrawAmount(DecimalUtils.toDecimal(request.getAmount()));
		fundWithdrawInfoBo.setApplySource(SystemCommonEnum.ApplySource.ONLINE_TRADE.getType());
		ClientTeltransferInfoBo ttv = new ClientTeltransferInfoBo();
		if (request.getTeltransferInfoId() == null || ConstKey.TRANSFER_TYPE_TT.equals(iTransferType)) {
			ttv.setAddress(request.getAddress());
			ttv.setBankCode(request.getRecvBankCode());
			ttv.setBankName(request.getRecvBankName());
			ttv.setIsVisible(request.getVisibleFlag());
			ttv.setSwiftCode(request.getSwiftCode());
			ttv.setFundAccount(request.getFundAccount());
			ttv.setProvinceName(request.getProvinceName());
			ttv.setCityName(request.getCityName());
			ttv.setBankAcct(request.getRecvBankAcct());
			ttv.setNationality(request.getNationality());
			ttv.setCountry(request.getCountry());
			if (SystemCommonEnum.NationalityTypeEnum.CHINA.equals(request.getNationality())) {
				ttv.setCityId(request.getCityId() != null ? Long.valueOf(request.getCityId()) : null);
				ttv.setProvinceId(request.getProvinceId() != null ? Long.valueOf(request.getProvinceId()) : null);
			}
		} else {
			ttv.setId(Long.valueOf(request.getTeltransferInfoId()));
		}
		fundWithdrawInfoBo.setTelegram(ttv);
		// 执行申请
		String applicationId = fundWithdrawApplicationService.commitApply(fundWithdrawInfoBo);
		fundWithdrawApplicationService.startWithdrawFlow(applicationId,
			fundWithdrawInfoBo.getTransferType(), fundWithdrawInfoBo.getApplySource());

		return R.data(applicationId);

	}

	@ApiLog("出金申请列表")
	@ApiOperation("出金申请列表")
	@RequestMapping("/withdrawList")
	@ResponseBody
	public R<IPage<ClientFundWithdrawApplicationVo>> withdrawList
		(@RequestBody CommonReqVO<QueryWithdrawRequest> requestVo) {
		QueryWithdrawRequest request = requestVo.getParams();
		Query query = new Query();

		query.setSize(request.getSize());
		ClientFundWithdrawApplicationBo bo = new ClientFundWithdrawApplicationBo();
		if (StringUtils.isNotEmpty(request.getBeginDate())) {
			bo.setBeginCreateTime(request.getBeginDate());
		}
		if (StringUtils.isNotEmpty(request.getEndDate())) {
			bo.setEndCreateTime(request.getEndDate());
		}
		if (ObjectUtils.isNotEmpty(request.getStatus())) {
			bo.setStatus(request.getStatus());
		}
		if (ObjectUtils.isNotEmpty(request.getCurrency()) && !request.getCurrency().equals(0)) {
			String desc = CashConstant.PayCurrencyTypeDescEnum.getDesc(request.getCurrency().toString());
			bo.getInfo().setCcy(desc);
		}

		bo.getInfo().setUserId(AuthUtil.getTenantCustId());

		// 执行申请
		IPage<ClientFundWithdrawApplicationVo> page = fundWithdrawApplicationService.queryDetailInfoList(bo, Condition.getPage(query));
		return R.data(page);
	}


	@ApiLog("查询银行卡")
	@ApiOperation("查询银行卡")
	@RequestMapping("/queryBankCardInfo")
	@ResponseBody
	public R<List<BankCardInfoVO>> getBankCardInfoList
		(@Validated({QueryGroup.class}) @RequestBody CommonReqVO<AccountDepositBankBo> requestVo) {
		List<BankCardInfoVO> bankCardInfoList = bankCardInfoService.selectBankInfoList(requestVo.getParams());
		return R.data(bankCardInfoList);
	}

	@ApiLog("查询银行信息")
	@ApiOperation("查询银行信息")
	@RequestMapping("/queryBankSwiftCode")
	@ResponseBody
	public R<BankSwiftCodeRequest> queryBankSwiftCode (@RequestBody CommonReqVO<BankSwiftCodeRequest> requestVo) {
		BankSwiftCodeRequest bankSwiftCode = requestVo.getParams();
		return cashBankClient.bankSwiftCode(bankSwiftCode.getBankCode());
	}

	@ApiLog("查询电汇转账收款银行")
	@ApiOperation("查询电汇转账收款银行")
	@RequestMapping("/queryTeltransferBankList")
	@ResponseBody
	public R queryTeltransferBankList(@RequestBody CommonReqVO<TeltransferRequest> requestVo) {
		TeltransferRequest request = requestVo.getParams();
		return cashBankClient.withdrawBankList(request.getBankType());
	}

	@ApiLog("查询登记收款银行")
	@ApiOperation("查询登记收款银行")
	@RequestMapping("/queryAuditBankCardInfoList")
	@ResponseBody
	public R<IPage<BankCardApplicationVO>> queryAuditBankCardInfoList
		(@RequestBody CommonReqVO<Query> requestVo) {
		IPage<BankCardApplicationVO> pages = bankCardApplicationService.selectBankCardApplicationPage(Condition.getPage(requestVo.getParams()));
		return R.data(pages);
	}

	@ApiLog("查询现有收款银行列表")
	@ApiOperation("查询现有收款银行列表")
	@RequestMapping("/queryBankCardInfoList")
	@ResponseBody
	public R<IPage<BankCardInfo>> queryBankCardInfoList(@RequestBody CommonReqVO<Query> requestVo) {
		IPage<BankCardInfo> pages = bankCardInfoService.selectBankCardInfoPage(Condition.getPage(requestVo.getParams()));
		return R.data(pages);
	}


	@ApiLog("查询收款银行信息")
	@ApiOperation("查询收款银行信息")
	@RequestMapping("/queryBankCardInfoById")
	@ResponseBody
	public R<BankCardInfo> queryBankCardInfoById(@RequestBody CommonReqVO<Long> requestVo) {
		BankCardInfo info = bankCardInfoService.selectBankCardInfoById(requestVo.getParams());
		return R.data(info);
	}

	@ApiLog("绑定/解绑/修改收款银行")
	@ApiOperation("绑定/解绑/修改收款银行")
	@RequestMapping("/submitBankCardReview")
	@ResponseBody
	public R<String> submitBankCardReview(@Validated @RequestBody CommonReqVO<BankCardReviewBo> requestVo) {
		BankCardReviewBo bo = requestVo.getParams();
		R<CustomerDetailVO> result = customerInfoClient.selectCustomerDetail(AuthUtil.getTenantCustId());
		if (!result.isSuccess()) {
			log.error("客户custId = {} 提交出金申请失败！错误信息：{}", AuthUtil.getTenantCustId(), result.getMsg());
			return R.fail(I18nUtil.getMessage(WithdrawalsFundMessageConstant.WITHDRAWALS_FUND_SUBMIT_BANK_BIND_UNBIND_MODIFY_FAILED_NOTICE));
		}
		CustomerDetailVO customerDetail = result.getData();
		if (StringUtils.isEmpty(customerDetail.getAccountId())) {
			return R.fail(I18nUtil.getMessage(WithdrawalsFundMessageConstant.WITHDRAWALS_FUND_QUERY_FUND_ACCOUNT_FAILED_NOTICE));
		}
		bo.setUserId(customerDetail.getCustId());
		bo.setClientId(customerDetail.getAccountId());
		bo.setSource("app");
		String applicationId = bankCardReviewInfoService.submitBankCardReview(bo);
		return R.data(applicationId);
	}

	@ApiLog("获取取款手续费")
	@ApiOperation("获取取款手续费")
	@RequestMapping("/queryWithdrwaFee")
	@ResponseBody
	public R<List<BankFeeConfig>> queryWithdrwaFee(@RequestBody CommonReqVO<WithdrawFeeRequest> requestVo) {
		WithdrawFeeRequest bo = requestVo.getParams();
		BankFeeConfigBo bankFeeConfigBo = new BankFeeConfigBo();
		bankFeeConfigBo.setCcy(bo.getCcy());
		bankFeeConfigBo.setTransferType(bo.getTransferType());
		bankFeeConfigBo.setActive(SystemCommonEnum.YesNo.YES.getIndex());
		R<String> r = dictClient.getValue("WITHDRAW_FEE", AuthUtil.getTenantId());
		bankFeeConfigBo.setBankCode(bo.getBankCode());
		if (r.isSuccess()) {
			if (StringUtil.isNotBlank(r.getData()) && r.getData().trim().equals("1")) {
				bankFeeConfigBo.setBankCode(bo.getBankCode());
			}
		}
		List<BankFeeConfig> list = bankFeeConfigService.queryList(bankFeeConfigBo);
		return R.data(list);
	}

	@ApiLog("撤销申请")
	@ApiOperation("撤销申请")
	@RequestMapping("/cancel")
	@ResponseBody
	public R cancelWithdraw(@RequestBody CommonReqVO<String> requestVo) {
		log.info("撤销申请 " + requestVo.getParams());
		R cancel = fundWithdrawApplicationService.cancel(requestVo.getParams());
		if (cancel.isSuccess()) {
			String approvalOption = "用户手动撤销";
			fundWithdrawApplicationService.cancelFlow(requestVo.getParams(), approvalOption);
			return R.success(I18nUtil.getMessage(WithdrawalsFundMessageConstant.WITHDRAWALS_FUND_REVOCATION_SUCCESSFUL_NOTICE));
		}
		return cancel;
	}

	@ApiLog("银行卡信息审核凭证上传")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "银行卡信息审核凭证上传(file)", notes = "银行卡信息审核凭证上传(file)")
	@PostMapping(value = "/upload-bank-card-file", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
	public R<Kv> uploadImage(@ApiParam(value = "凭证类型[0-银行卡 1-银行证明]", required = true)
							 @RequestParam("type") Integer type,
							 @ApiParam(value = "文件", required = true)
							 @RequestPart("file") MultipartFile file) {
		if (!FileUtils.checkImageFormats(file.getOriginalFilename())) {
			return R.fail(I18nUtil.getMessage(WithdrawalsFundMessageConstant.WITHDRAWALS_FUND_UPLOAD_IMAGE_TYPE_ERROR_NOTICE));
		}
		if (!FileUtils.checkFileSize(file, FileUtils.MAX_FILE_SIZE, FileUtils.MIN_FILE_SIZE)) {
			return R.fail(String.format(I18nUtil.getMessage(WithdrawalsFundMessageConstant.WITHDRAWALS_FUND_UPLOAD_IMAGE_SIZE_ERROR_NOTICE), FileUtils.MAX_FILE_SIZE / 1024 / 1024, FileUtils.MIN_FILE_SIZE));
		}
		return bankCardImageService.uploadImage(type, file);
	}

	@ApiLog("银行卡信息审核凭证上传")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "银行卡信息审核凭证上传(Base64图片)", notes = "银行卡信息审核凭证上传(Base64图片)")
	@PostMapping("/upload-bank-card-base64")
	public R<Kv> uploadImg(@RequestBody CommonReqVO<BankCardImageDTO> bankCardImageDTOCommonReqVO) {
		if (!FileUtils.isImageBase64(bankCardImageDTOCommonReqVO.getParams().getImgBase64())) {
			return R.fail(I18nUtil.getMessage(WithdrawalsFundMessageConstant.WITHDRAWALS_FUND_UPLOAD_IMAGE_TYPE_ERROR_NOTICE));
		}
		if (!FileUtils.getBase64ImageSize(bankCardImageDTOCommonReqVO.getParams().getImgBase64(), FileUtils.MAX_FILE_SIZE, FileUtils.MIN_FILE_SIZE)) {
			return R.fail(String.format(I18nUtil.getMessage(WithdrawalsFundMessageConstant.WITHDRAWALS_FUND_UPLOAD_IMAGE_SIZE_ERROR_NOTICE), FileUtils.MAX_FILE_SIZE / 1024 / 1024, FileUtils.MIN_FILE_SIZE));
		}
		return bankCardImageService.uploadImage(bankCardImageDTOCommonReqVO.getParams());
	}
}
