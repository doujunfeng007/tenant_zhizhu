package com.minigod.zero.bpmn.module.dbs.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONObject;
import cn.jiguang.common.utils.StringUtils;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.minigod.zero.bpmn.module.account.mapper.AccountOpenInfoMapper;
import com.minigod.zero.bpmn.module.account.vo.AccountOpenInfoVO;
import com.minigod.zero.bpmn.module.constant.CashConstant;
import com.minigod.zero.bpmn.module.dbs.DbsEddaFundBusinessService;
import com.minigod.zero.bpmn.module.deposit.entity.SecDepositFundsEntity;
import com.minigod.zero.bpmn.module.deposit.enums.DepositStatusEnum;
import com.minigod.zero.bpmn.module.deposit.mapper.FundDepositApplicationMapper;
import com.minigod.zero.bpmn.module.deposit.service.BankCardInfoService;
import com.minigod.zero.bpmn.module.deposit.service.ISecDepositFundsService;
import com.minigod.zero.bpmn.module.edda.entity.ClientEddaFundApplicationEntity;
import com.minigod.zero.bpmn.module.edda.entity.ClientEddaInfoApplicationEntity;
import com.minigod.zero.bpmn.module.edda.entity.DbsEddaReqLogEntity;
import com.minigod.zero.bpmn.module.edda.service.ClientEddaFundApplicationService;
import com.minigod.zero.bpmn.module.edda.service.ClientEddaInfoApplicationService;
import com.minigod.zero.bpmn.module.edda.service.DbsEddaReqLogService;
import com.minigod.zero.bpmn.module.feign.ICustomerInfoClient;
import com.minigod.zero.bpmn.module.feign.dto.AmountDTO;
import com.minigod.zero.bpmn.module.feign.enums.ThawingType;
import com.minigod.zero.bpmn.module.feign.vo.CustomerAccountDetailVO;
import com.minigod.zero.bpmn.module.withdraw.enums.BpmCommonEnum;
import com.minigod.zero.bpmn.module.withdraw.enums.SystemCommonEnum;
import com.minigod.zero.bpmn.utils.ApplicationIdUtil;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.utils.ObjectUtil;
import com.minigod.zero.core.tool.utils.StringUtil;
import com.minigod.zero.dbs.service.DbsApiBusinessService;
import com.minigod.zero.dbs.vo.FpsTransactionRequestVO;
import com.minigod.zero.platform.constants.CommonTemplateCode;
import com.minigod.zero.platform.enums.InformEnum;
import com.minigod.zero.platform.enums.MsgStaticType;
import com.minigod.zero.platform.enums.PushTemplate;
import com.minigod.zero.platform.enums.PushTypeEnum;
import com.minigod.zero.platform.utils.PushUtil;
import com.minigod.zero.system.feign.IDictBizClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ClassName: com.minigod.zero.bpmn.module.dbs.impl.DbsEddaFundBusinessServiceImpl
 * @Description:
 * @Author: linggr
 * @CreateDate: 2024/5/13 19:11
 * @Version: 1.0
 */
@Slf4j
@Service
@AllArgsConstructor
public class DbsEddaFundBusinessServiceImpl implements DbsEddaFundBusinessService {
	private final DbsApiBusinessService dbsApiBusinessService;

	@Autowired
	private DbsEddaReqLogService dbsEddaReqLogService;
	@Autowired
	private ISecDepositFundsService iSecDepositFundsService;
	@Autowired
	private ClientEddaFundApplicationService clientEddaFundApplicationService;
	@Autowired
	private ClientEddaInfoApplicationService clientEddaInfoApplicationService;

	@Autowired
	private FundDepositApplicationMapper fundDepositApplicationMapper;
	@Autowired
	private AccountOpenInfoMapper accountOpenInfoMapper;
	private final ICustomerInfoClient iCustomerInfoClient;

	@Autowired
	private BankCardInfoService bankCardInfoService;
	@Autowired
	private IDictBizClient dictBizClient;

	/**
	 * edda  入金申请
	 * @param eddaFund
	 * @param createBy
	 */
	@Override
	public void sendEDDAFund(ClientEddaFundApplicationEntity eddaFund, String createBy) {
		String ddaRef = "EDDAFUNDGPC" + eddaFund.getApplicationId();

		DbsEddaReqLogEntity eddaReqLog = new DbsEddaReqLogEntity();
		eddaReqLog.setApplicationid(eddaFund.getApplicationId());
		eddaReqLog.setBusinesstype("fund-FpsGpcFund");
		eddaReqLog.setCreatetime(new Date());
		eddaReqLog.setUpdatetime(new Date());
		eddaReqLog.setReqtime(new Date());
		dbsEddaReqLogService.save(eddaReqLog);
		String msgId = "DDAGPC" + ApplicationIdUtil.generateDbsMsgId("000000");

		ClientEddaInfoApplicationEntity info = clientEddaInfoApplicationService.getById(eddaFund.getEddaInfoId());

		ClientEddaFundApplicationEntity updFund = new ClientEddaFundApplicationEntity();
		updFund.setId(eddaFund.getId());
		updFund.setMsgId(msgId);
		updFund.setDdaRef(info.getDdaRef());
		updFund.setReqTime(new Date());
		updFund.setBankState(SystemCommonEnum.EDDABankStateTypeEnum.AUTHORIZING.getValue());
		//updFund.setOldEddaState(1);
		updFund.setUpdateTime(new Date());
		clientEddaFundApplicationService.updateById(updFund);

		LambdaUpdateWrapper<SecDepositFundsEntity> updateWrapper = new LambdaUpdateWrapper<>();
		updateWrapper.set(SecDepositFundsEntity::getState, DepositStatusEnum.SecDepositFundsStatus.ACCEPT.getCode());
		updateWrapper.set(SecDepositFundsEntity::getModifyTime, new Date());
		updateWrapper.eq(SecDepositFundsEntity::getEddaApplicationId, eddaFund.getApplicationId());
		iSecDepositFundsService.update(updateWrapper);


		FpsTransactionRequestVO requestVO = new FpsTransactionRequestVO();
		requestVO.setMsgId(msgId);
		requestVO.setCustomerReference(eddaFund.getApplicationId() + RandomUtil.randomInt(1, 100));

		requestVO.setTxnAmount(eddaFund.getDepositBalance().doubleValue());
		requestVO.setDdaRef(info.getDdaRef());
		requestVO.setMandateId(info.getMandateId());
		requestVO.setTxnCcy(CashConstant.PayCurrencyTypeDescEnum.getDesc(eddaFund.getMoneyType().toString()));

		requestVO.setBenefitAccountNo(eddaFund.getBenefitNo());
		requestVO.setBenefitAccountName(eddaFund.getBenefitAccount());
		requestVO.setDepositBankId(eddaFund.getDepositBankId());
		requestVO.setDepositAccountNo(eddaFund.getDepositNo());
		requestVO.setDepositAccountName(eddaFund.getDepositAccount());

		//requestVO.setBusinessBankCode();

		requestVO.setApplicationId(eddaFund.getApplicationId());
		//requestVO.setCusRef();
		String tenantId = ObjectUtil.isNotEmpty(AuthUtil.getTenantId())? AuthUtil.getTenantId() : "000000";
		requestVO.setTenantId(tenantId);

		DbsEddaReqLogEntity reqEddaReqLog = new DbsEddaReqLogEntity();
		reqEddaReqLog.setId(eddaReqLog.getId());
		reqEddaReqLog.setUpdatetime(new Date());
		try {
			log.info("msgId = "+msgId + "  DBS eDDA sendFpsGpcFund 请求报文：" + JSON.toJSONString(requestVO));
			R<String> stringR = dbsApiBusinessService.sendFpsGpcFund(requestVO);
			log.info("msgId = "+msgId + "  DBS eDDA sendFpsGpcFund 响应报文：" + stringR.getData());

			if (!stringR.isSuccess()) {
				log.info(eddaFund.getApplicationId() + "请求DBS eDDA转账异常 " + stringR.getMsg());
				reqEddaReqLog.setReqstatus("0");
			}else if (!(stringR.getCode() == R.success().getCode())) {
				log.info(eddaFund.getApplicationId() + "请求DBS eDDA转账失败  " + stringR.getMsg());
				reqEddaReqLog.setReqstatus("0");
			}else {
				String responseMes = stringR.getData();
				if (StringUtil.isEmpty(responseMes)) {
					log.info(eddaFund.getApplicationId() + "请求DBS eDDA转账，返回报文为空");
					reqEddaReqLog.setReqstatus("0");
				}else {
					JSONObject jsonObj=new JSONObject(stringR.getData());
					msgId = jsonObj.getJSONObject("header").getStr("msgId");
					Date date = jsonObj.getJSONObject("header").getDate("timeStamp");
					//保存响应报文
					DbsEddaReqLogEntity reqEddaInfoReqLog = new DbsEddaReqLogEntity();
					reqEddaInfoReqLog.setId(eddaReqLog.getId());
					reqEddaInfoReqLog.setMsgid(msgId);
					reqEddaInfoReqLog.setDdaref(requestVO.getDdaRef());
					reqEddaInfoReqLog.setResmessage(responseMes);
					reqEddaInfoReqLog.setReqmessage(JSON.toJSONString(requestVO));
					reqEddaInfoReqLog.setRestime(date);
					reqEddaInfoReqLog.setUpdatetime(new Date());
					dbsEddaReqLogService.updateById(reqEddaInfoReqLog);

					//处理响应报文
					this.businessResEddaFund(eddaFund, eddaReqLog, responseMes, createBy);
					return;
				}
			}

		} catch (Exception e) {
			log.error("msgId = "+eddaFund.getApplicationId() + eddaReqLog.getId() + "  DBS eDDA Enquiry响应报文处理异常：" + e.toString());
			reqEddaReqLog.setReqstatus("0");
		}
		dbsEddaReqLogService.updateById(reqEddaReqLog);


	}

	private void businessResEddaFund(ClientEddaFundApplicationEntity eddaFund, DbsEddaReqLogEntity resfpsReqLog, String decResponse, String createBy){
		try {
			//解析响应报文
			JSONObject jsonObj=new JSONObject(decResponse);
			//交易请求结果响应报文
			String txnResponse = jsonObj.getStr("txnResponse");
			String txnStatus = new JSONObject(txnResponse).getStr("txnStatus");
			String txnRefId = new JSONObject(txnResponse).getStr("txnRefId");//银行流水号
			//更新log表状态
			DbsEddaReqLogEntity eddaLog =new DbsEddaReqLogEntity();
			eddaLog.setId(resfpsReqLog.getId());
			eddaLog.setReqstatus("1");
			eddaLog.setEnqstatus(txnStatus);
			eddaLog.setUpdatetime(new Date());
			dbsEddaReqLogService.updateById(eddaLog);

			//如果交易拒绝保存拒绝信息 失败
			if ("RJCT".equals(txnStatus)) {
				String txnRejectCode = new JSONObject(txnResponse).getStr("txnRejectCode");
				String txnStatusDescription = new JSONObject(txnResponse).getStr("txnStatusDescription");
				//如果是 I103 && DUPL-ACTC-Success 交易已经成功 返回的查询结果 处理为成功
				if("I103".equals(txnRejectCode)){
					if(txnStatusDescription.contains("ACTC")) {
						String settAmt = new JSONObject(txnResponse).getStr("txnSettlementAmt");
						String settDt = new JSONObject(txnResponse).getStr("txnSettlementDt");
						this.updateEddaFundSuccess(eddaFund, txnRefId, settAmt, settDt, createBy);

						return;
					}else if(txnStatusDescription.contains("PDNG")){
						return;
					}
				}
				//请求完成更新数据
				ClientEddaFundApplicationEntity updateEddaFund = new ClientEddaFundApplicationEntity();
				updateEddaFund.setId(eddaFund.getId());
				updateEddaFund.setBankState(SystemCommonEnum.EDDABankStateTypeEnum.FAIL.getValue());
				updateEddaFund.setTxnRefId(txnRefId);
				updateEddaFund.setRejCorde(txnRejectCode);
				updateEddaFund.setRejDescription(txnStatusDescription);
				updateEddaFund.setUpdateTime(new Date());
				updateEddaFund.setUpdateUser(createBy);
				clientEddaFundApplicationService.updateById(updateEddaFund);

				LambdaUpdateWrapper<SecDepositFundsEntity> updateWrapper = new LambdaUpdateWrapper<>();
				updateWrapper.set(SecDepositFundsEntity::getModifyTime, new Date());
				updateWrapper.eq(SecDepositFundsEntity::getEddaApplicationId, eddaFund.getApplicationId());
				updateWrapper.set(SecDepositFundsEntity::getState, DepositStatusEnum.SecDepositFundsStatus.RETURN.getCode());
				updateWrapper.set(SecDepositFundsEntity::getBackReason, txnStatusDescription);
				updateWrapper.set(SecDepositFundsEntity::getTxnRefId, txnRefId);
				iSecDepositFundsService.update(updateWrapper);

				fundDepositApplicationMapper.updateApplicationStatusByApplicationId(eddaFund.getApplicationId(), BpmCommonEnum.FundDepositStatus.DEPOSIT_FAIL.getStatus());

				try {
					List<String> params = new ArrayList<>();
					R<CustomerAccountDetailVO> accountDetail = iCustomerInfoClient.selectCustomerDetailByAccountId(eddaFund.getClientId());
					if (accountDetail.isSuccess()) {
						CustomerAccountDetailVO accountOpenInfoVO = accountDetail.getData();
						params.add(eddaFund.getApplicationId());
						params.add(dictBizClient.getValue("company", "CO_Phone_HK").getData());

						PushUtil.builder()
							.msgGroup("P")
							.custId(accountOpenInfoVO.getCustId())
							.params(params)
							.group(MsgStaticType.DisplayGroup.SERVICE_MSG)
							.pushType(PushTypeEnum.STRONG_MSG.getTypeValue())
							.templateCode(PushTemplate.QUICK_DEPOSIT_FAIL.getCode())
							.sendWay(InformEnum.SendWayTimeEnum.FORTHWITH.getTypeCode())
							.pushAsync();
					}
				} catch (Exception e) {
					log.error("推送消息失败",e);
				}


			} else if ("ACTC".equals(txnStatus)) {
				//成功
				String settAmt = new JSONObject(txnResponse).getStr("txnSettlementAmt");
				String settDt = new JSONObject(txnResponse).getStr("txnSettlementDt");
				//请求完成更新数据
				this.updateEddaFundSuccess(eddaFund, txnRefId, settAmt, settDt, createBy);

			}else if ("ACWC".equals(txnStatus) || "PDNG".equals(txnStatus)){
				//第二天处理或者联系银行
			}

		} catch (Exception e){
			e.printStackTrace();
			log.error(eddaFund.getApplicationId() +"DBS eDDA入金响应报文处理异常："+e.toString());
		}
	}

	private void fastPosting(ClientEddaFundApplicationEntity eddaFund, String txnRefId, String settAmt, String settDt) {
		AmountDTO amount = new AmountDTO();
		BigDecimal settAmts = new BigDecimal(settAmt);
		amount.setAmount(settAmts.setScale(2));
		amount.setAccountId(eddaFund.getClientId());
		amount.setBusinessId(eddaFund.getApplicationId());
		amount.setThawingType(ThawingType.EDDA_GOLD_DEPOSIT.getCode());
		amount.setCurrency(CashConstant.PayCurrencyTypeDescEnum.getDesc(eddaFund.getMoneyType().toString()));
		log.info("DBS eDDA入金请求入账 res:{}", JSON.toJSONString(amount));
		R result = iCustomerInfoClient.goldDeposit(amount);
		ClientEddaFundApplicationEntity updateEddaFund = new ClientEddaFundApplicationEntity();
		updateEddaFund.setId(eddaFund.getId());

		updateEddaFund.setUpdateTime(new Date());
		if (result.isSuccess()) {
			//入账成功 更新状态
			log.error(eddaFund.getApplicationId() + "DBS eDDA入金 请求入账成功");
			updateEddaFund.setBankState(SystemCommonEnum.EDDABankStateTypeEnum.LOSSOFENTRY.getValue());

			try {
				R<CustomerAccountDetailVO> customerDetailByAccountId = iCustomerInfoClient.selectCustomerDetailByAccountId(eddaFund.getClientId());
				if (customerDetailByAccountId.isSuccess()) {
					CustomerAccountDetailVO accountOpenInfoVO = customerDetailByAccountId.getData();
					List<String> params = new ArrayList<>();
					params.add(String.valueOf(amount.getAmount()));
					params.add(String.valueOf(amount.getAccountId()));

					PushUtil.builder()
						.msgGroup("P")
						.custId(accountOpenInfoVO.getCustId())
						.params(params)
						.group(MsgStaticType.DisplayGroup.SERVICE_MSG)
						.templateCode(PushTemplate.DEPOSIT_SUCCESS.getCode())
						.pushType(PushTypeEnum.STRONG_MSG.getTypeValue())
						.sendWay(InformEnum.SendWayTimeEnum.FORTHWITH.getTypeCode())
						.pushAsync();
				}


			} catch (Exception e) {
				e.printStackTrace();
				log.error("推送消息失败",e);
			}

		}else {
			log.error(eddaFund.getApplicationId() + "DBS eDDA入金请求入账失败");

			updateEddaFund.setBankState(SystemCommonEnum.EDDABankStateTypeEnum.ENTRYFAILURE.getValue());
		}
		clientEddaFundApplicationService.updateById(updateEddaFund);
		if (result.isSuccess()) {
			//入账成功 更新状态
			log.error(eddaFund.getApplicationId() + "DBS eDDA入金 请求入账成功");

			LambdaUpdateWrapper<SecDepositFundsEntity> updateWrapper = new LambdaUpdateWrapper<>();
			updateWrapper.set(SecDepositFundsEntity::getModifyTime, new Date());
			updateWrapper.eq(SecDepositFundsEntity::getEddaApplicationId, eddaFund.getApplicationId());
			updateWrapper.set(SecDepositFundsEntity::getState, DepositStatusEnum.SecDepositFundsStatus.FINISH.getCode());
			updateWrapper.set(SecDepositFundsEntity::getTxnRefId, txnRefId);
			updateWrapper.set(SecDepositFundsEntity::getSettlementAmt, settAmt);
			updateWrapper.set(SecDepositFundsEntity::getSettlementDt, new Date());
			iSecDepositFundsService.update(updateWrapper);

			try {
				SecDepositFundsEntity depositFunds = iSecDepositFundsService.selectByApplicationId(eddaFund.getApplicationId());
				bankCardInfoService.depositBankCardBinding(depositFunds);
			} catch (Exception e) {
				log.error("入金绑定银行卡失败：{}",e.getMessage());
			}
			fundDepositApplicationMapper.updateApplicationStatusByApplicationId(eddaFund.getApplicationId(), BpmCommonEnum.FundDepositStatus.DEPOSIT_SUCCESS.getStatus());
		}else {
			log.error(eddaFund.getApplicationId() + " DBS eDDA入金请求入账失败");
		}
	}

	private void updateEddaFundSuccess(ClientEddaFundApplicationEntity eddaFund, String txnRefId, String settAmt, String settDt, String createBy) {
		ClientEddaFundApplicationEntity updateEddaFund = new ClientEddaFundApplicationEntity();
		updateEddaFund.setId(eddaFund.getId());
		updateEddaFund.setBankState(SystemCommonEnum.EDDABankStateTypeEnum.SUCCESS.getValue());
		updateEddaFund.setTxnRefId(txnRefId);
		if (!StringUtils.isEmpty(settAmt)) {
			updateEddaFund.setSettlementAmt(new BigDecimal(settAmt));
		}
		if (!StringUtils.isEmpty(settDt)) {
			updateEddaFund.setSettlementDt(DateUtil.parse(settDt, "yyyy-MM-dd"));
		}
		updateEddaFund.setUpdateTime(new Date());
		updateEddaFund.setUpdateUser(createBy);
		clientEddaFundApplicationService.updateById(updateEddaFund);
		//入账
		fastPosting(eddaFund, txnRefId, settAmt, settDt);
	}



}
