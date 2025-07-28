package com.minigod.zero.bpm.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.minigod.zero.bpm.constant.CashConstant;
import com.minigod.zero.bpm.vo.request.CashEddaInfoReqVo;
import com.minigod.zero.bpm.vo.request.EddaInfoApplyProtocol;
import com.minigod.zero.biz.common.utils.HttpClientUtils;
import com.minigod.zero.bpm.entity.CashEddaInfoEntity;
import com.minigod.zero.bpm.vo.response.ResponseVO;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.api.ResultCode;
import com.minigod.zero.core.tool.support.Kv;
import com.minigod.zero.cust.enums.CustStaticType;
import com.minigod.zero.platform.dto.SendEmailDTO;
import com.minigod.zero.platform.feign.IPlatformMsgClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 汇丰edda公共类
 *
 * @author Zhe.Xiao
 */
@Service
@Slf4j
public class HsbcEddaCommonService {

	@Resource
	private ICashEddaInfoService cashEddaInfoService;
	@Resource
	private IPlatformMsgClient platformMsgClient;

	@Value("${bpm.api.url:http://10.9.68.165:7777/bpm}")
	private String cubpBaseUrl;
	@Value("${hsbc.edda.error.accept}")
	private String eddaErrorAccept;
	private String CUBP_HSBC_BANK_EDDA_INFO = "";
	private String CUBP_HSBC_BANK_EDDA_OTP_CONFIRM = "";
	private String CUBP_HSBC_BANK_EDDA_OTP_REGENERATION = "";

	@PostConstruct
	protected void initRestUrl() {
		CUBP_HSBC_BANK_EDDA_INFO = cubpBaseUrl + "/proxy/hsbcBank/sendEddaAuthorisations";
		CUBP_HSBC_BANK_EDDA_OTP_CONFIRM = cubpBaseUrl + "/proxy/hsbcBank/sendOtpConfirmation";
		CUBP_HSBC_BANK_EDDA_OTP_REGENERATION = cubpBaseUrl + "/proxy/hsbcBank/sendOtpRegeneration";
	}

	/**
	 * 汇丰银行卡EDDA授权 （汇丰edda针对汇丰银行卡授权需要发送短信验证码，所以单独做成同步的）
	 *
	 * @param
	 * @return
	 */
	public R hsbcBankEddaAuthorisations(CashEddaInfoEntity eddaInfo) {
		try {
			EddaInfoApplyProtocol protocol = new EddaInfoApplyProtocol();
			protocol.setBankPhone(eddaInfo.getBankPhone());
			protocol.setClientId(eddaInfo.getTradeAccount());
			protocol.setFundAccount(eddaInfo.getFundAccount());
			protocol.setBankName(eddaInfo.getBankName());
			protocol.setBankCode(eddaInfo.getBankCode());
			protocol.setBankId(eddaInfo.getBankId());
			protocol.setDepositAccount(eddaInfo.getDepositAccount());
			protocol.setDepositAccountName(eddaInfo.getDepositAccountName());
			protocol.setDepositAccountType(eddaInfo.getDepositAccountType());
			protocol.setBankIdKind(eddaInfo.getBankIdKind());
			protocol.setBankIdNo(eddaInfo.getBankIdNo());
			protocol.setBenefitAccount(eddaInfo.getBenefitAccount());
			protocol.setBenefitBankCore(eddaInfo.getBenefitBankCore());
			protocol.setBenefitNo(eddaInfo.getBenefitNo());
			//info 银行 1大陆 2香港 3其他
			//protocol 银行 1大陆 0香港 2其他
			if (eddaInfo.getBankType() == 2) {
				protocol.setBankType(0);
			} else if (eddaInfo.getBankType() == 1) {
				protocol.setBankType(1);
			} else if (eddaInfo.getBankType() == 3) {
				protocol.setBankType(2);
			}

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("params", protocol);
			log.info(eddaInfo.getCustId() + " hsbcBankAuthorisations send params：" + JSON.toJSONString(map));
			String result = HttpClientUtils.postJson(CUBP_HSBC_BANK_EDDA_INFO, JSONObject.toJSONString(map), true);
			log.info(eddaInfo.getCustId() + " hsbcBankAuthorisations send return：" + result);
			if (StringUtils.isBlank(result)) {
				log.info(eddaInfo.getCustId() + "（汇丰银行卡）汇丰Edda授权数据上送失败，下游返回NULL");
				return R.fail(ResultCode.INTERNAL_ERROR);
			}
			ResponseVO resultObj = JSONObject.parseObject(result, ResponseVO.class);
			if (resultObj.getCode() == 0) {
				String applicationId = JSONObject.parseObject(resultObj.getResult().toString()).getString("applicationId");
				String mandateId = JSONObject.parseObject(resultObj.getResult().toString()).getString("mandateIdentification");
				String otpId = JSONObject.parseObject(resultObj.getResult().toString()).getString("otpIdentificationNumber");
				String mandateStatus = JSONObject.parseObject(resultObj.getResult().toString()).getString("mandateStatus");
				String phoneMask = JSONObject.parseObject(resultObj.getResult().toString()).getString("phoneMask");
				eddaInfo.setApplicationId(applicationId);
				eddaInfo.setMandateId(mandateId);
				eddaInfo.setOtpId(otpId);
				eddaInfo.setMandateStatus(mandateStatus);
				eddaInfo.setUpdateTime(new Date());
				eddaInfo.setPhoneMask(phoneMask);
				cashEddaInfoService.updateEddaInfo(eddaInfo);
				log.info(eddaInfo.getCustId() + "（汇丰银行卡）汇丰Edda授权数据上送成功");
				return R.data(Kv.create().set("keyId", eddaInfo.getId()).set("phoneMask", phoneMask));
			} else {
				if (resultObj.getResult() != null && !JSONObject.parseObject(resultObj.getResult().toString()).isEmpty()) {
					eddaInfo.setApplicationId(JSONObject.parseObject(resultObj.getResult().toString()).getString("applicationId"));
					eddaInfo.setEddaState(JSONObject.parseObject(resultObj.getResult().toString()).getInteger("eddaState"));
					eddaInfo.setEddaFailReason(JSONObject.parseObject(resultObj.getResult().toString()).getString("eddaFailReason"));
					eddaInfo.setBankQuota(JSONObject.parseObject(resultObj.getResult().toString()).getBigDecimal("bankQuota"));
				}
				eddaInfo.setErrCnt(eddaInfo.getErrCnt() + 1);
				eddaInfo.setUpdateTime(new Date());
				log.info(eddaInfo.getCustId() + "（汇丰银行卡）汇丰Edda授权数据上送失败");
				cashEddaInfoService.updateEddaInfo(eddaInfo);
				saveErrorInfo(eddaInfo, "（汇丰银行卡）汇丰Edda授权数据发送失败：" + result, "（汇丰银行卡）汇丰Edda授权数据发送失败");
				return R.fail(ResultCode.INTERNAL_ERROR.getCode(), resultObj.getMessage());
			}
		} catch (Exception e) {
			log.error("汇丰Edda授权数据上送异常", e);
			eddaInfo.setErrCnt(eddaInfo.getErrCnt() + 1);
			cashEddaInfoService.updateEddaInfo(eddaInfo);
			saveErrorInfo(eddaInfo, "（汇丰银行卡）汇丰Edda授权数据上送异常：" + e, "（汇丰银行卡）汇丰Edda授权数据上送异常");
			return R.fail(ResultCode.INTERNAL_ERROR);
		}
	}

	/**
	 * 汇丰eDDA短信验证码校验
	 *
	 * @param vo
	 * @return
	 */
	public R otpConfirmation(CashEddaInfoReqVo vo) {
		CashEddaInfoEntity eddaInfo = cashEddaInfoService.getBaseMapper().selectById(vo.getKeyId());
		if (eddaInfo == null) {
			return R.fail(CustStaticType.CodeType.EDDA_INFO_NOT_FOUND.getCode(), CustStaticType.CodeType.EDDA_INFO_NOT_FOUND.getMessage());
		}
		if (!CashConstant.PDOU.equals(eddaInfo.getMandateStatus())) {
			return R.fail(CustStaticType.CodeType.EDDA_INFO_NOT_FOUND_PENDING.getCode(), CustStaticType.CodeType.EDDA_INFO_NOT_FOUND_PENDING.getMessage());
		}

		//未重发，已校验三次
		boolean isFirstExceedConfirmMax = eddaInfo.getSmsRetryCount() == 0 && eddaInfo.getSmsConformCount() >= CashConstant.MAX_OTP_CONFIRM_NUM;
		//已重发，已校验三次
		boolean isSecondExceedConfirmMax = eddaInfo.getSmsRetryCount() == 1 && eddaInfo.getSmsConformCount() >= CashConstant.MAX_OTP_CONFIRM_NUM;

		if (isFirstExceedConfirmMax) {
			return R.fail(CustStaticType.CodeType.EDDA_SMS_FIRST_EXCEED_MAX_CONFIRM_ERR.getCode(), CustStaticType.CodeType.EDDA_SMS_FIRST_EXCEED_MAX_CONFIRM_ERR.getMessage());
		} else if (isSecondExceedConfirmMax) {
			return R.fail(CustStaticType.CodeType.EDDA_SMS_SECOND_EXCEED_MAX_CONFIRM_ERR.getCode(), CustStaticType.CodeType.EDDA_SMS_SECOND_EXCEED_MAX_CONFIRM_ERR.getMessage());
		}

		try {
			EddaInfoApplyProtocol protocol = new EddaInfoApplyProtocol();
			protocol.setMandateIdentification(eddaInfo.getMandateId());
			protocol.setApplicationId(eddaInfo.getApplicationId());
			protocol.setOtpIdentificationNumber(eddaInfo.getOtpId());
			protocol.setOtpPassword(vo.getOtpPassword());
			Map<String, Object> map = new HashMap<>();
			map.put("params", protocol);
			log.info(eddaInfo.getCustId() + " otpConfirmation send params：" + JSON.toJSONString(map));
			String result = HttpClientUtils.postJson(CUBP_HSBC_BANK_EDDA_OTP_CONFIRM, JSONObject.toJSONString(map));
			log.info(eddaInfo.getCustId() + " otpConfirmation send return：" + result);
			if (StringUtils.isBlank(result)) {
				log.info(eddaInfo.getCustId() + " 汇丰Edda短信校验失败,下游返回NULL");
				return R.fail(ResultCode.INTERNAL_ERROR);
			}
			ResponseVO resultObj = JSONObject.parseObject(result, ResponseVO.class);
			if (resultObj.getCode() == 0) {
				eddaInfo.setSmsConformCount(eddaInfo.getSmsConformCount() + 1);
				cashEddaInfoService.updateEddaInfo(eddaInfo);
				eddaInfo.setEddaState(1);
				cashEddaInfoService.updateEddaInfo(eddaInfo);
				return R.success("校验成功");
			}
			if (resultObj.getCode() == CashConstant.HSBC_EDDA_RESP_MPP04003) {
				eddaInfo.setSmsConformCount(eddaInfo.getSmsConformCount() + 1);
				cashEddaInfoService.updateEddaInfo(eddaInfo);
				log.info(eddaInfo.getCustId() + " 汇丰Edda短信输入错误,当前校验次数:" + eddaInfo.getSmsConformCount());
				//已重发过短信并且第三次校验失败时，应该把提交的授权信息更新删除，让用户重新提交授权信息
				boolean doDelete = eddaInfo.getSmsRetryCount() == 1 && eddaInfo.getSmsConformCount() >= CashConstant.MAX_OTP_CONFIRM_NUM;
				if (doDelete) {
					eddaInfo.setDataState(2);
					eddaInfo.setDeleteTime(new Date());
					cashEddaInfoService.updateEddaInfo(eddaInfo);
				}
				Integer remainConfirmNum = CashConstant.MAX_OTP_CONFIRM_NUM - eddaInfo.getSmsConformCount();
				return R.fail(CustStaticType.CodeType.EDDA_OTP_CONFIRM_ERR.getCode(), CustStaticType.CodeType.EDDA_OTP_CONFIRM_ERR.getMessage() + "（剩余次数：" + remainConfirmNum + "次）");
			}
			if (resultObj.getCode() == CashConstant.HSBC_EDDA_RESP_MPP04001) {
				eddaInfo.setSmsConformCount(eddaInfo.getSmsConformCount() + 1);
				cashEddaInfoService.updateEddaInfo(eddaInfo);
				log.info(eddaInfo.getCustId() + " 汇丰Edda短信输入错误次数达到上限,当前校验次数:" + eddaInfo.getSmsConformCount());
				boolean doDelete = eddaInfo.getSmsRetryCount() == 1 && eddaInfo.getSmsConformCount() >= CashConstant.MAX_OTP_CONFIRM_NUM;
				if (doDelete) {
					eddaInfo.setDataState(2);
					eddaInfo.setDeleteTime(new Date());
					cashEddaInfoService.updateEddaInfo(eddaInfo);
					return R.fail(CustStaticType.CodeType.EDDA_SMS_SECOND_EXCEED_MAX_CONFIRM_ERR.getCode(), CustStaticType.CodeType.EDDA_SMS_SECOND_EXCEED_MAX_CONFIRM_ERR.getMessage());
				} else {
					return R.fail(CustStaticType.CodeType.EDDA_SMS_FIRST_EXCEED_MAX_CONFIRM_ERR.getCode(), CustStaticType.CodeType.EDDA_SMS_FIRST_EXCEED_MAX_CONFIRM_ERR.getMessage());
				}
			}
			if (resultObj.getCode() == CashConstant.HSBC_EDDA_RESP_MPP04004) {
				boolean doDelete = eddaInfo.getSmsRetryCount() == 1;
				if (doDelete) {
					eddaInfo.setDataState(2);
					eddaInfo.setDeleteTime(new Date());
					cashEddaInfoService.updateEddaInfo(eddaInfo);
					return R.fail(CustStaticType.CodeType.EDDA_OTP_SECOND_EXPIRED_ERR.getCode(), CustStaticType.CodeType.EDDA_OTP_SECOND_EXPIRED_ERR.getMessage());
				} else {
					return R.fail(CustStaticType.CodeType.EDDA_OTP_FIRST_EXPIRED_ERR.getCode(), CustStaticType.CodeType.EDDA_OTP_FIRST_EXPIRED_ERR.getMessage());
				}
			}
			log.info(eddaInfo.getCustId() + "汇丰Edda短信校验失败,请求异常");
			return R.fail(ResultCode.INTERNAL_ERROR.getCode(), resultObj.getMessage());
		} catch (Exception e) {
			log.error("汇丰Edda短信校验异常", e);
			return R.fail(ResultCode.INTERNAL_ERROR);
		}
	}

	private void saveErrorInfo(CashEddaInfoEntity info, String errorMsg, String body) {
		if (info.getErrCnt() <= 1) {
			String title = "客户号：" + info.getCustId() + "Edda授权数据上送任务异常";
			String accept = eddaErrorAccept;
			errorMsg = errorMsg + "【" + body + "】";
			SendEmailDTO sendEmailDTO = new SendEmailDTO();
			sendEmailDTO.setAccepts(Arrays.asList(accept));
			sendEmailDTO.setTitle(title);
			sendEmailDTO.setContent(errorMsg);
			platformMsgClient.sendEmail(sendEmailDTO);
		}
	}

	public R otpRegeneration(CashEddaInfoReqVo vo) {
		CashEddaInfoEntity eddaInfo = cashEddaInfoService.getBaseMapper().selectById(vo.getKeyId());
		if (eddaInfo == null) {
			return R.fail(CustStaticType.CodeType.EDDA_INFO_NOT_FOUND.getCode(), CustStaticType.CodeType.EDDA_INFO_NOT_FOUND.getMessage());
		}
		if (!CashConstant.PDOU.equals(eddaInfo.getMandateStatus())) {
			return R.fail(CustStaticType.CodeType.EDDA_INFO_NOT_FOUND_PENDING.getCode(), CustStaticType.CodeType.EDDA_INFO_NOT_FOUND_PENDING.getMessage());
		}
		if (eddaInfo.getSmsRetryCount() >= CashConstant.MAX_OTP_RESEND_NUM) {
			return R.fail(CustStaticType.CodeType.EDDA_SMS_EXCEED_MAX_RESEND_ERR.getCode(), CustStaticType.CodeType.EDDA_SMS_EXCEED_MAX_RESEND_ERR.getMessage());
		}

		try {
			EddaInfoApplyProtocol protocol = new EddaInfoApplyProtocol();
			protocol.setApplicationId(eddaInfo.getApplicationId());
			protocol.setMandateIdentification(eddaInfo.getMandateId());
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("params", protocol);
			log.info(eddaInfo.getCustId() + " otpRegeneration send params：" + JSON.toJSONString(map));
			String result = HttpClientUtils.postJson(CUBP_HSBC_BANK_EDDA_OTP_REGENERATION, JSONObject.toJSONString(map));
			log.info(eddaInfo.getCustId() + " otpRegeneration send return：" + result);
			if (StringUtils.isBlank(result)) {
				log.info(eddaInfo.getCustId() + "汇丰Edda短信验证码重新生成失败,下游返回NULL");
				return R.fail(ResultCode.INTERNAL_ERROR);
			}
			ResponseVO resultObj = JSONObject.parseObject(result, ResponseVO.class);
			if (resultObj.getCode() == 0) {
				eddaInfo.setSmsRetryCount(eddaInfo.getSmsRetryCount() + 1);
				eddaInfo.setSmsConformCount(0);
				String otpIdentificationNumber = JSONObject.parseObject(resultObj.getResult().toString()).getString("OtpIdentificationNumber");
				eddaInfo.setOtpId(otpIdentificationNumber);
				cashEddaInfoService.updateEddaInfo(eddaInfo);
				return R.success();
			}
			if (resultObj.getCode() == CashConstant.HSBC_EDDA_RESP_MPP04002) {
				log.info(eddaInfo.getCustId() + "汇丰Edda短信验证码重新生成达到上限,当前重新生成次数:" + eddaInfo.getSmsRetryCount());
				return R.fail(CustStaticType.CodeType.EDDA_SMS_EXCEED_MAX_RESEND_ERR.getCode(), CustStaticType.CodeType.EDDA_SMS_EXCEED_MAX_RESEND_ERR.getMessage());
			}
			if (resultObj.getCode() == CashConstant.HSBC_EDDA_RESP_MPP04005) {
				log.info(eddaInfo.getCustId() + "汇丰Edda短信验证码未能发出,当前重新生成次数:" + eddaInfo.getSmsRetryCount());
				return R.fail(CustStaticType.CodeType.EDDA_OTP_CAN_NOT_BE_SEND.getCode(), CustStaticType.CodeType.EDDA_OTP_CAN_NOT_BE_SEND.getMessage());
			}
			log.info(eddaInfo.getCustId() + "汇丰Edda短信验证码重新生成失败,当前重新生成次数:" + eddaInfo.getSmsRetryCount());
			return R.fail(ResultCode.INTERNAL_ERROR.getCode(), resultObj.getMessage());
		} catch (Exception e) {
			log.error("汇丰Edda短信重新生成异常", e);
			return R.fail(ResultCode.INTERNAL_ERROR);
		}
	}

}
