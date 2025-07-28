package com.minigod.zero.bpmn.module.dbs.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONObject;
import cn.jiguang.common.utils.StringUtils;
import com.alibaba.fastjson2.JSON;
import com.minigod.zero.biz.common.utils.RestTemplateUtil;
import com.minigod.zero.bpmn.module.account.mapper.AccountOpenInfoMapper;
import com.minigod.zero.bpmn.module.dbs.DbsEddaInfoBusinessService;
import com.minigod.zero.bpmn.module.edda.entity.ClientEddaInfoApplicationEntity;
import com.minigod.zero.bpmn.module.edda.entity.DbsEddaReqLogEntity;
import com.minigod.zero.bpmn.module.edda.mapper.ClientEddaInfoApplicationMapper;
import com.minigod.zero.bpmn.module.edda.service.DbsEddaReqLogService;
import com.minigod.zero.bpmn.module.exchange.enums.CurrencyExcEnum;
import com.minigod.zero.bpmn.module.feign.ICashBankClient;
import com.minigod.zero.bpmn.module.feign.ICustomerInfoClient;
import com.minigod.zero.bpmn.module.feign.vo.CustomerAccountDetailVO;
import com.minigod.zero.bpmn.module.feign.vo.ReceivingBankVO;
import com.minigod.zero.bpmn.module.withdraw.enums.SystemCommonEnum;
import com.minigod.zero.bpmn.utils.ApplicationIdUtil;
import com.minigod.zero.cms.enums.SupportTypeEnum;
import com.minigod.zero.core.log.exception.ServiceException;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.utils.ObjectUtil;
import com.minigod.zero.core.tool.utils.StringUtil;
import com.minigod.zero.dbs.service.DbsApiBusinessService;
import com.minigod.zero.dbs.vo.EddaInfoRequestVO;
import com.minigod.zero.dbs.vo.EddaTransactionEnquiryRequestVO;
import com.minigod.zero.platform.enums.*;
import com.minigod.zero.platform.utils.PushUtil;
import com.minigod.zero.platform.utils.SmsUtil;
import com.minigod.zero.system.feign.IDictBizClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ClassName: com.minigod.zero.bpmn.module.dbs.impl.DbsEddaBusinessServiceImpl
 * @Description:
 * @Author: linggr
 * @CreateDate: 2024/5/13 15:27
 * @Version: 1.0
 */
@Slf4j
@Service
@AllArgsConstructor
public class DbsEddaInfoBusinessServiceImpl implements DbsEddaInfoBusinessService {
	@Autowired
	private ICashBankClient cashBankClient;
	@Autowired
	private DbsEddaReqLogService dbsEddaReqLogService;
	@Autowired
	private DbsApiBusinessService dbsApiBusinessService;
	@Autowired
	private ClientEddaInfoApplicationMapper clientEddaInfoApplicationMapper;
	@Autowired
	private AccountOpenInfoMapper accountOpenInfoMapper;
	@Autowired
	private IDictBizClient dictBizClient;
	@Autowired
	private ICustomerInfoClient customerInfoClient;
	@Resource
	private RestTemplateUtil restTemplateUtil;

	/**
	 * 入金授权动作
	 *
	 * @param eddaInfo
	 * @param createBy
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void sendEDDAInitiation(ClientEddaInfoApplicationEntity eddaInfo, String createBy) {
		log.info("入金授权动作 开始");
		String ddaRef = "RDF" + eddaInfo.getApplicationId();
		DbsEddaReqLogEntity eddaReqLog = new DbsEddaReqLogEntity();
		eddaReqLog.setApplicationid(eddaInfo.getApplicationId());
		eddaReqLog.setBusinesstype("initiation");
		eddaReqLog.setCreatetime(new Date());
		eddaReqLog.setUpdatetime(new Date());
		eddaReqLog.setReqtime(new Date());
		dbsEddaReqLogService.save(eddaReqLog);

		ClientEddaInfoApplicationEntity updateDbsEddaInfo = new ClientEddaInfoApplicationEntity();
		updateDbsEddaInfo.setId(eddaInfo.getId());
		String msgId = "DDAINIT" + ApplicationIdUtil.generateDbsMsgId("000000");

		updateDbsEddaInfo.setMsgId(msgId);
		updateDbsEddaInfo.setDdaRef(ddaRef);
		updateDbsEddaInfo.setReqTime(new Date());
		updateDbsEddaInfo.setEddaState(SystemCommonEnum.EddaStateEnum.AUTHORIZING.getCode());
		updateDbsEddaInfo.setUpdateTime(new Date());
		clientEddaInfoApplicationMapper.updateById(updateDbsEddaInfo);

		/*-----请求dbsedda-----*/
		EddaInfoRequestVO eddaInfoRequestVO = new EddaInfoRequestVO();
		eddaInfoRequestVO.setMsgId(msgId);
		eddaInfoRequestVO.setDdaRef(ddaRef);
		//eddaInfoRequestVO.setEffDate();
		//eddaInfoRequestVO.setExpDate();

		//eddaInfoRequestVO.setMaxAmt();
		String currency = CurrencyExcEnum.CurrencyType.getIndex(eddaInfo.getAmtCcy()).toString();
		R<ReceivingBankVO> result = cashBankClient.findReceivingBankByDepositBankById(eddaInfo.getSecDepositBankId(),
			currency, SupportTypeEnum.EDDA.getType());
		if (!result.isSuccess()) {
			log.error("edda授权用户{}，银行卡={}失败，获取收款配置失败：{}", eddaInfo.getDepositAccountName(),
				eddaInfo.getDepositAccount(), result.getMsg());
			throw new ServiceException("edda授权失败");
		}
		ReceivingBankVO receivingBank = result.getData();
		if (receivingBank == null) {
			log.error("edda授权用户{}，银行卡={}失败，未找到配置收款账户", eddaInfo.getDepositAccountName(), eddaInfo.getDepositAccount());
			throw new ServiceException("edda授权失败");
		}
		//公司的收款账户账号

		updateDbsEddaInfo.setBenefitNo(receivingBank.getAccount());
		updateDbsEddaInfo.setBenefitAccount(receivingBank.getAccountName());
		updateDbsEddaInfo.setBenefitBank(receivingBank.getBankName());
		updateDbsEddaInfo.setBenefitBankCode(receivingBank.getBankCode());
		updateDbsEddaInfo.setMaxAmt(receivingBank.getMaxAmt());
		updateDbsEddaInfo.setUpdateTime(new Date());
		clientEddaInfoApplicationMapper.updateById(updateDbsEddaInfo);

		eddaInfoRequestVO.setBenefitBank(receivingBank.getBankName());
		eddaInfoRequestVO.setBenefitBankCode(receivingBank.getBankCode());
		eddaInfoRequestVO.setBenefitAccountNo(receivingBank.getAccount());
		eddaInfoRequestVO.setBenefitAccount(receivingBank.getAccountName());
		eddaInfoRequestVO.setMaxAmt(receivingBank.getMaxAmt());

		eddaInfoRequestVO.setAmtCcy(eddaInfo.getAmtCcy());
		eddaInfoRequestVO.setDepositBankId(eddaInfo.getBankIdQuick());
		eddaInfoRequestVO.setDepositAccountNo(eddaInfo.getDepositAccount());
		eddaInfoRequestVO.setDepositAccountName(eddaInfo.getDepositAccountName());
		eddaInfoRequestVO.setBankIdKind(eddaInfo.getBankIdKind());
		eddaInfoRequestVO.setBankIdNo(eddaInfo.getBankIdNo());

		//eddaInfoRequestVO.setBusinessBankCode();
		eddaInfoRequestVO.setApplicationId(eddaInfo.getApplicationId());
		//eddaInfoRequestVO.setCusRef();
		String tenantId = ObjectUtil.isNotEmpty(AuthUtil.getTenantId()) ? AuthUtil.getTenantId() : "000000";

		eddaInfoRequestVO.setTenantId(tenantId);

		DbsEddaReqLogEntity reqEddaReqLog = new DbsEddaReqLogEntity();
		reqEddaReqLog.setId(eddaReqLog.getId());
		reqEddaReqLog.setUpdatetime(new Date());

		try {
			log.info("入金授权动作 请求dbsedda re :{}", JSON.toJSONString(eddaInfoRequestVO));
			R<String> stringR = dbsApiBusinessService.sendEDDAInitiation(eddaInfoRequestVO);
			log.info("入金授权动作 请求dbsedda stringR :{}", JSON.toJSONString(stringR));


			if (!stringR.isSuccess()) {
				log.info(eddaInfo.getApplicationId() + "请求DBS eDDA授权异常 " + stringR.getMsg());
				reqEddaReqLog.setReqstatus("0");
			} else if (!(stringR.getCode() == R.success().getCode())) {
				log.info(eddaInfo.getApplicationId() + "请求DBS eDDA授权请求失败  " + stringR.getMsg());
				reqEddaReqLog.setReqstatus("0");
			} else {
				String responseMes = stringR.getData();
				if (StringUtil.isEmpty(responseMes)) {
					log.info(eddaInfo.getApplicationId() + "请求DBS eDDA授权，返回报文为空");
					reqEddaReqLog.setReqstatus("0");
				} else {
					JSONObject jsonObj = new JSONObject(stringR.getData());
					msgId = jsonObj.getJSONObject("header").getStr("msgId");
					Date date = jsonObj.getJSONObject("header").getDate("timeStamp");

					//保存响应报文
					DbsEddaReqLogEntity reqEddaInfoReqLog = new DbsEddaReqLogEntity();
					reqEddaInfoReqLog.setId(eddaReqLog.getId());
					reqEddaInfoReqLog.setMsgid(msgId);
					reqEddaInfoReqLog.setDdaref(eddaInfoRequestVO.getDdaRef());
					reqEddaInfoReqLog.setReqmessage(JSON.toJSONString(eddaInfoRequestVO));
					reqEddaInfoReqLog.setRestime(date);
					reqEddaInfoReqLog.setUpdatetime(new Date());
					dbsEddaReqLogService.updateById(reqEddaInfoReqLog);

					//处理响应报文
					this.businessResEdda(eddaInfo, eddaReqLog, responseMes, createBy);
					return;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			log.error("msgId = " + eddaInfo.getApplicationId() + eddaReqLog.getId() + "  DBS eDDA Enquiry响应报文处理异常：" + e.toString());
			reqEddaReqLog.setReqstatus("0");
		}
		dbsEddaReqLogService.updateById(reqEddaReqLog);

	}

	/**
	 * 请求edda 授权查询
	 *
	 * @param eddaInfo
	 * @param updateBy
	 * @return
	 */
	@Override
	public boolean sendEDDAEnquiry(ClientEddaInfoApplicationEntity eddaInfo, String updateBy) {
		log.info("请求DBS eDDA授权查询开始");
		DbsEddaReqLogEntity eddaReqLog = new DbsEddaReqLogEntity();
		eddaReqLog.setApplicationid(eddaInfo.getApplicationId());
		eddaReqLog.setBusinesstype("enquiry");
		eddaReqLog.setCreatetime(new Date());
		eddaReqLog.setUpdatetime(new Date());
		eddaReqLog.setReqtime(new Date());
		dbsEddaReqLogService.save(eddaReqLog);

		String msgId = "DDAEnq" + ApplicationIdUtil.generateDbsMsgId("000000");

		EddaTransactionEnquiryRequestVO eddaFundRequestVO = new EddaTransactionEnquiryRequestVO();
		eddaFundRequestVO.setMsgId(msgId);
		eddaFundRequestVO.setOriginalMsgId(eddaInfo.getMsgId());
		eddaFundRequestVO.setTxnRefId(eddaInfo.getTxnRefId());
		eddaFundRequestVO.setAmtCcy(eddaInfo.getAmtCcy());
		eddaFundRequestVO.setBenefitAccountNo(eddaInfo.getBenefitNo());

		//eddaFundRequestVO.setBusinessBankCode();
		eddaFundRequestVO.setMsgId(eddaFundRequestVO.getMsgId());
		eddaFundRequestVO.setApplicationId(eddaInfo.getApplicationId());
		//eddaFundRequestVO.setCusRef();
		String tenantId = ObjectUtil.isNotEmpty(AuthUtil.getTenantId()) ? AuthUtil.getTenantId() : "000000";
		eddaFundRequestVO.setTenantId(tenantId);

		DbsEddaReqLogEntity reqEddaReqLog = new DbsEddaReqLogEntity();
		reqEddaReqLog.setId(eddaReqLog.getId());
		reqEddaReqLog.setUpdatetime(new Date());

		try {
			log.info("msgId = " + msgId + "  DBS eDDA Enquiry请求报文：" + JSON.toJSONString(eddaFundRequestVO));
			R<String> stringR = dbsApiBusinessService.sendEDDAEnquiry(eddaFundRequestVO);
			log.info("msgId = " + msgId + "  DBS eDDA Enquiry响应报文：" + stringR.getData());
			if (!stringR.isSuccess()) {
				log.info(eddaInfo.getApplicationId() + "请求DBS eDDA授权异常 " + stringR.getMsg());
				reqEddaReqLog.setReqstatus("0");
			} else if (!(stringR.getCode() == R.success().getCode())) {
				log.info(eddaInfo.getApplicationId() + "请求DBS eDDA授权请求失败  " + stringR.getMsg());
				reqEddaReqLog.setReqstatus("0");
			} else {
				String responseMes = stringR.getData();
				if (StringUtil.isEmpty(responseMes)) {
					log.info(eddaInfo.getApplicationId() + "请求DBS eDDA授权，返回报文为空");
					reqEddaReqLog.setReqstatus("0");
				} else {
					JSONObject jsonObj = new JSONObject(stringR.getData());
					msgId = jsonObj.getJSONObject("header").getStr("msgId");
					Date date = jsonObj.getJSONObject("header").getDate("timeStamp");

					//保存响应报文
					DbsEddaReqLogEntity reqEddaInfoReqLog = new DbsEddaReqLogEntity();
					reqEddaInfoReqLog.setId(eddaReqLog.getId());
					reqEddaInfoReqLog.setMsgid(msgId);
					reqEddaInfoReqLog.setDdaref(eddaInfo.getDdaRef());
					reqEddaInfoReqLog.setResmessage(responseMes);
					reqEddaInfoReqLog.setReqmessage(JSON.toJSONString(eddaFundRequestVO));
					reqEddaInfoReqLog.setRestime(date);
					reqEddaInfoReqLog.setUpdatetime(new Date());
					dbsEddaReqLogService.updateById(reqEddaInfoReqLog);

					//处理响应报文
					return businessEddaEnquiry(eddaInfo, eddaReqLog, responseMes, updateBy);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			log.error("msgId = " + eddaInfo.getApplicationId() + eddaReqLog.getId() + "  DBS eDDA Enquiry响应报文处理异常：" + e.toString());
			//reqEddaReqLog.setReqstatus("0");
		}
		dbsEddaReqLogService.updateById(reqEddaReqLog);

		return false;
	}

	/**
	 * 截取银行卡后4位
	 *
	 * @param cardNo
	 * @return
	 */
	private String getLastFour(String cardNo) {
		if (org.apache.commons.lang3.StringUtils.isEmpty(cardNo)) {
			return "";
		}
		return cardNo.substring(cardNo.length() - 4);
	}

	/**
	 * edda Ack1 响应报文处理
	 *
	 * @param eddaInfo
	 * @param eddaReqLog
	 * @param decResponse
	 * @param createBy
	 */
	private void businessResEdda(ClientEddaInfoApplicationEntity eddaInfo, DbsEddaReqLogEntity eddaReqLog, String decResponse, String createBy) {
		try {
			//解析响应报文
			JSONObject jsonObj = new JSONObject(decResponse);
			String txnResponse = jsonObj.getStr("txnResponse");
			String txnStatus = new JSONObject(txnResponse).getStr("txnStatus");
			//更新log表状态
			DbsEddaReqLogEntity eddaLog = new DbsEddaReqLogEntity();
			eddaLog.setId(eddaReqLog.getId());
			eddaLog.setReqstatus("1");
			eddaLog.setEnqstatus(txnStatus);
			eddaLog.setUpdatetime(new Date());

			dbsEddaReqLogService.updateById(eddaLog);
			//如果ACK1 拒绝保存拒绝信息
			ClientEddaInfoApplicationEntity updateDbsEddaInfo = new ClientEddaInfoApplicationEntity();
			updateDbsEddaInfo.setId(eddaInfo.getId());
			if ("RJCT".equals(txnStatus)) {
				//失败
				//请求完成更新数据

				String txnRejectCode = new JSONObject(txnResponse).getStr("txnRejectCode");
				String txnStatusDescription = new JSONObject(txnResponse).getStr("txnStatusDescription");
				String txnRefId = new JSONObject(txnResponse).getStr("txnRefId");
				updateDbsEddaInfo.setTxnRefId(txnRefId);
				updateDbsEddaInfo.setEddaState(SystemCommonEnum.EddaStateEnum.FAIL.getCode());
				updateDbsEddaInfo.setRejCorde(txnRejectCode);
				updateDbsEddaInfo.setRejDescription(txnStatusDescription);

				try {
					R<CustomerAccountDetailVO> accountDetail = customerInfoClient.selectCustomerDetailByAccountId(eddaInfo.getClientId());
					if (accountDetail.isSuccess()) {
						CustomerAccountDetailVO customerAccountDetailVO = accountDetail.getData();
						List<String> params = new ArrayList<>();
						params.add(eddaInfo.getBankName());
						params.add(eddaInfo.getDepositAccount().substring(eddaInfo.getDepositAccount().length() - 4));
						params.add(dictBizClient.getValue("company", "CO_Phone_HK").getData());
						PushUtil.builder()
							.msgGroup("P")
							.custId(customerAccountDetailVO.getCustId())
							.params(params)
							.group(MsgStaticType.DisplayGroup.SERVICE_MSG)
							.pushType(PushTypeEnum.STRONG_MSG.getTypeValue())
							.templateCode(PushTemplate.EDDA_AUTH_FAIL.getCode())
							.sendWay(InformEnum.SendWayTimeEnum.FORTHWITH.getTypeCode())
							.pushAsync();
					}
				} catch (Exception e) {
					log.error("发送通知失败", e);
				}


			} else if ("ACCT".equals(txnStatus)) {
				//成功
				//{"header":{"msgId":"HKO82F2449701656579","orgId":"0123","timeStamp":"2018-04-12T10:53:29.996","ctry":"HK"},"txnResponse":{"responseType":"ACK2","txnRefId":"HKD645329791","txnStatus":"ACCT","mandateId":"MNTD123","ddaRef":"HKD645329791","mandateType":"DDMP","seqType":"RCUR","frqcyType":"MNTH","countPerPeriod":"1","effDate":"2018-12-01","expDate":"2019-12-31","maxAmt":"1000.00","maxAmtCcy":"HKD","creditor":{"name":"GTS Account Company Name1","accountNo":"0001033107019","accountCcy":"HKD"},"debtor":{"name":"Peter","proxyType":"B","proxyValue":"4542854201","bankId":"016","ultimateName":"Peter Chan","prvtCustomer ":[{"prvtCustomerId":"A1234567","prvtCustomerIdType":"NIDN"}]}}}
				String mandateId = new JSONObject(txnResponse).getStr("mandateId");
				String effDate = new JSONObject(txnResponse).getStr("effDate");
				String expDate = new JSONObject(txnResponse).getStr("expDate");
				String ddaRef = new JSONObject(txnResponse).getStr("ddaRef");
				String txnRefId = new JSONObject(txnResponse).getStr("txnRefId");

				updateDbsEddaInfo.setDdaRef(ddaRef);
				updateDbsEddaInfo.setTxnRefId(txnRefId);
				updateDbsEddaInfo.setMandateId(mandateId);
				updateDbsEddaInfo.setEddaState(SystemCommonEnum.EddaStateEnum.SUCCESS.getCode());
				if (!StringUtils.isEmpty(effDate)) {
					updateDbsEddaInfo.setEffDate(DateUtil.parse(effDate, "yyyy-MM-dd"));
				}
				if (!StringUtils.isEmpty(expDate)) {
					updateDbsEddaInfo.setExpDate(DateUtil.parse(expDate, "yyyy-MM-dd"));
				}
				updateDbsEddaInfo.setUpdateTime(new Date());

				//发送短信
				if (eddaInfo.getMobile() != null) {
					String phoneNumber[] = eddaInfo.getMobile().split("-");
					String bankName = eddaInfo.getBankName();
					String depositAccount = eddaInfo.getDepositAccount();
					if (phoneNumber.length == 2) {
						Integer code = SmsTemplate.EDDA_AUTHORIZATION_SUCCESSFUL.getCode();
						SmsUtil.builder()
							.templateCode(code)
							.param(bankName)
							.param(this.getLastFour(depositAccount))
							.phoneNumber(phoneNumber[1])
							.areaCode(phoneNumber[0])
							.sendAsync();
						log.info(String.format("发送短信EDDA授权[%s]提醒成功！", "银行授权成功"));
					} else {
						log.error(String.format("手机号格式错误,未能发送短信EDDA授权[%s]提醒！", "银行授权成功"));
					}
				} else {
					log.error(String.format("手机号为空,未能发送短信EDDA授权[%s]提醒！", "银行授权成功"));
				}
			}
			if ("ACCP".equals(txnStatus)) {
				//授权中
				//{"header":{"msgId":"HKO82F2449701656579","orgId":"0123","timeStamp":"2018-04-12T10:53:29.996","ctry":"HK"},"txnResponse":{"responseType":"ACK1","txnRefId":"HKD645329791","txnStatus":"ACCP","mandateId":"MNTD123"}}
				String txnRefId = new JSONObject(txnResponse).getStr("txnRefId");
				String mandateId = new JSONObject(txnResponse).getStr("mandateId");
				updateDbsEddaInfo.setTxnRefId(txnRefId);
				updateDbsEddaInfo.setMandateId(mandateId);
				updateDbsEddaInfo.setUpdateTime(new Date());
				updateDbsEddaInfo.setEddaState(SystemCommonEnum.EddaStateEnum.AUTHORIZING.getCode());

			}
			updateDbsEddaInfo.setUpdateTime(new Date());
			updateDbsEddaInfo.setUpdateUser(createBy);
			clientEddaInfoApplicationMapper.updateById(updateDbsEddaInfo);

		} catch (Exception e) {
			log.error(eddaReqLog.getMsgid() + "DBS eDDA ACK1授权响应报文处理异常：" + e.toString());
		}
	}

	/**
	 * edda Enquiry 响应报文处理  入金授权处理
	 *
	 * @param eddaInfo
	 * @param eddaReqLog
	 * @param decResponse
	 * @param updateBy
	 * @return
	 */
	private boolean businessEddaEnquiry(ClientEddaInfoApplicationEntity eddaInfo, DbsEddaReqLogEntity eddaReqLog, String decResponse, String updateBy) {
		try {
			ClientEddaInfoApplicationEntity updateEddaInfo = new ClientEddaInfoApplicationEntity();
			updateEddaInfo.setId(eddaInfo.getId());
			updateEddaInfo.setApplicationId(eddaInfo.getApplicationId());

			//解析响应报文
			JSONObject jsonObj = new JSONObject(decResponse);
			//请求被拒绝
			String enqResponse = jsonObj.getStr("enqResponse");
			String enqStatus = new JSONObject(enqResponse).getStr("enqStatus");
			if (!StringUtils.isEmpty(enqStatus) && "RJCT".equals(enqStatus)) {
				//更新日志表状态
				DbsEddaReqLogEntity eddaLog = new DbsEddaReqLogEntity();
				eddaLog.setId(eddaReqLog.getId());
				eddaLog.setReqstatus("1");
				eddaLog.setEnqstatus(enqStatus);
				dbsEddaReqLogService.updateById(eddaLog);
				log.info(eddaReqLog.getMsgid() + "DBS eDDA Enquiry 授权查询被拒绝：" + enqResponse);

				updateEddaInfo.setEddaState(SystemCommonEnum.EddaStateEnum.FAIL.getCode());
				updateEddaInfo.setRejDescription(enqResponse);
				updateEddaInfo.setUpdateTime(new Date());
				updateEddaInfo.setUpdateUser(updateBy);
				clientEddaInfoApplicationMapper.updateById(updateEddaInfo);
				return false;
			}

			String headerStr = jsonObj.getStr("header");
			String msgId = new JSONObject(headerStr).getStr("msgId");
			String txnStatus = new JSONObject(enqResponse).getStr("txnStatus");

			updateEddaInfo.setMsgId(msgId);
			if ("ACCT".equals(txnStatus) || "ACTC".equals(txnStatus)) {//成功
				String mandateId = new JSONObject(enqResponse).getStr("mandateId");
				String effDate = new JSONObject(enqResponse).getStr("effDate");
				String expDate = new JSONObject(enqResponse).getStr("expDate");
				String ddaRef = new JSONObject(enqResponse).getStr("ddaRef");
				updateEddaInfo.setDdaRef(ddaRef);
				updateEddaInfo.setMandateId(mandateId);
				updateEddaInfo.setEddaState(SystemCommonEnum.EddaStateEnum.SUCCESS.getCode());

				if (!StringUtils.isEmpty(effDate)) {
					updateEddaInfo.setEffDate(DateUtil.parse(effDate, "yyyy-MM-dd"));
				}
				if (!StringUtils.isEmpty(expDate)) {
					updateEddaInfo.setExpDate(DateUtil.parse(expDate, "yyyy-MM-dd"));
				}
				updateEddaInfo.setUpdateTime(new Date());
				updateEddaInfo.setUpdateUser(updateBy);

				//发送短信
				if (eddaInfo.getMobile() != null) {
					String phoneNumber[] = eddaInfo.getMobile().split("-");
					String bankName = eddaInfo.getBankName();
					String depositAccount = eddaInfo.getDepositAccount();
					if (phoneNumber.length == 2) {
						Integer code = SmsTemplate.EDDA_AUTHORIZATION_SUCCESSFUL.getCode();
						SmsUtil.builder()
							.templateCode(code)
							.param(bankName)
							.param(this.getLastFour(depositAccount))
							.phoneNumber(phoneNumber[1])
							.areaCode(phoneNumber[0])
							.sendAsync();
						log.info(String.format("刷新EDDA银行状态授权申请状态,发送短信EDDA授权[%s]提醒成功！", "银行授权成功"));
					} else {
						log.error(String.format("刷新EDDA银行状态授权申请状态,手机号格式错误,未能发送短信EDDA授权[%s]提醒！", "银行授权成功"));
					}
				} else {
					log.error(String.format("刷新EDDA银行状态授权申请状态,手机号为空,未能发送短信EDDA授权[%s]提醒！", "银行授权成功"));
				}

			} else if ("RJCT".equals(txnStatus)) {//失败
				String txnRejectCode = new JSONObject(enqResponse).getStr("txnRejectCode");
				String txnStatusDescription = new JSONObject(enqResponse).getStr("txnStatusDescription");
				updateEddaInfo.setEddaState(SystemCommonEnum.EddaStateEnum.FAIL.getCode());

				updateEddaInfo.setRejCorde(txnRejectCode);
				updateEddaInfo.setRejDescription(txnStatusDescription);
				updateEddaInfo.setUpdateTime(new Date());
				updateEddaInfo.setUpdateUser(updateBy);

			} else if ("ACCP".equals(txnStatus) || "PDNG".equals(txnStatus)) {
				//授权中
				//{"header":{"msgId":"HKO82F2449701656579","orgId":"0123","timeStamp":"2018-04-12T10:53:29.996","ctry":"HK"},"txnResponse":{"responseType":"ACK1","txnRefId":"HKD645329791","txnStatus":"ACCP","mandateId":"MNTD123"}}
				String txnRefId = new JSONObject(enqResponse).getStr("txnRefId");
				String mandateId = new JSONObject(enqResponse).getStr("mandateId");
				updateEddaInfo.setTxnRefId(txnRefId);
				updateEddaInfo.setMandateId(mandateId);
				updateEddaInfo.setUpdateTime(new Date());
				updateEddaInfo.setEddaState(SystemCommonEnum.EddaStateEnum.AUTHORIZING.getCode());
			}
			clientEddaInfoApplicationMapper.updateById(updateEddaInfo);

			//更新日志表状态
			DbsEddaReqLogEntity eddaLog = new DbsEddaReqLogEntity();
			eddaLog.setId(eddaReqLog.getId());
			eddaLog.setEnqstatus(txnStatus);
			eddaLog.setReqstatus("1");
			dbsEddaReqLogService.updateById(eddaLog);

			return true;
		} catch (Exception e) {
			log.error(eddaReqLog.getMsgid() + "DBS eDDA 入金请求响应报文处理异常：" + e.toString());

			return false;
		}
	}

}
