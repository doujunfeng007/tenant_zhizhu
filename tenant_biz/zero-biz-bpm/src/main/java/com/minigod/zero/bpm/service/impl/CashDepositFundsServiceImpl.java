package com.minigod.zero.bpm.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.minigod.zero.biz.common.constant.MqTopics;
import com.minigod.zero.biz.common.enums.CommonEnums;
import com.minigod.zero.biz.common.mq.AddMessageReq;
import com.minigod.zero.biz.common.utils.DateUtil;
import com.minigod.zero.biz.common.utils.HttpClientUtils;
import com.minigod.zero.bpm.constant.CashConstant;
import com.minigod.zero.bpm.dto.BpmSecuritiesRespDto;
import com.minigod.zero.bpm.dto.BpmTradeAcctRespDto;
import com.minigod.zero.bpm.dto.acct.req.CashPolicyReqDto;
import com.minigod.zero.bpm.dto.acct.resp.CashPolicyRespDto;
import com.minigod.zero.bpm.entity.BpmSecuritiesInfoEntity;
import com.minigod.zero.bpm.entity.CashAccessImageEntity;
import com.minigod.zero.bpm.entity.CashDepositFundsEntity;
import com.minigod.zero.bpm.entity.CashExtractingMoneyEntity;
import com.minigod.zero.bpm.mapper.CashDepositFundsMapper;
import com.minigod.zero.bpm.mq.product.CustOperationLogProducer;
import com.minigod.zero.bpm.mq.product.DepositApplyProducer;
import com.minigod.zero.bpm.service.*;
import com.minigod.zero.bpm.service.api.IBpmOpenApiService;
import com.minigod.zero.bpm.service.openAccount.IBpmAccountService;
import com.minigod.zero.bpm.utils.BpmRespCodeUtils;
import com.minigod.zero.bpm.vo.BpmSecuritiesInfoVO;
import com.minigod.zero.bpm.vo.CashDepositFundsVO;
import com.minigod.zero.bpm.vo.ClientDepositFundsApplyProtocol;
import com.minigod.zero.bpm.vo.request.*;
import com.minigod.zero.bpm.vo.response.ResponseVO;
import com.minigod.zero.core.launch.constant.TokenConstant;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.api.ResultCode;
import com.minigod.zero.core.tool.utils.CollectionUtil;
import com.minigod.zero.core.tool.utils.StringUtil;
import com.minigod.zero.core.tool.utils.WebUtil;
import com.minigod.zero.cust.entity.CustInfoEntity;
import com.minigod.zero.cust.enums.CustStaticType;
import com.minigod.zero.cust.feign.ICustInfoClient;
import com.minigod.zero.platform.constants.CommonTemplateCode;
import com.minigod.zero.platform.dto.SendEmailDTO;
import com.minigod.zero.platform.feign.IPlatformMsgClient;
import com.minigod.zero.trade.entity.CustOperationLogEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 存入资金表 服务实现类
 *
 * @author 掌上智珠
 * @since 2023-05-18
 */
@Service
@Slf4j
public class CashDepositFundsServiceImpl extends ServiceImpl<CashDepositFundsMapper, CashDepositFundsEntity> implements ICashDepositFundsService {

	@Resource
	private ICustInfoClient custInfoClient;
	@Resource
	private IPlatformMsgClient platformMsgClient;
	@Resource
	private IBpmSecuritiesInfoService bpmSecuritiesInfoService;
	@Resource
	private IBpmSystemConfigService bpmSystemConfigService;
	@Resource
	@Lazy
	private ICashExtractingMoneyService cashExtractingMoneyService;
	@Resource
	private ICashAccessImageService cashAccessImageService;
	@Resource
	private CustOperationLogProducer custOperationLogProducer;
	@Resource
	private IBpmOpenApiService bpmOpenApiService;
	@Resource
	private IBpmAccountService bpmAccountService;
	@Resource
	private DepositApplyProducer depositApplyProducer;
	@Resource
	@Lazy
	private CashSendMsgCommonService cashSendMsgCommonService;

	@Value("${bpm.api.url:http://10.9.68.165:7777/bpm}")
	private String cubpBaseUrl;
	@Value("${hsbc.edda.accountIdentification:002021632001}")
	private String hsbcEddaAccountIdentification;
	@Value("${hsbc.edda.accountName:zhizhu SECURITIES}")
	private String hsbcEddaAccountName;
	@Value("${hsbc.edda.getBankCode:HSBCHK}")
	private String hsbcEddaGetBankCode;
	@Value("${hsbc.edda.bankNameCn:汇丰银行}")
	private String hsbcEddaBankNameCn;
	@Value("${hsbc.edda.bankNameEn:HONGKONG AND SHANGHAI BANKING CORPORATION LIMITED}")
	private String hsbcEddaBankNameEn;
	@Value("${cash.deposit.job.sender.email:noreply@zhizhu.cn}")
	private String cashDepositJobSenderEmail;
	@Value("${cash.deposit.job.accept.email:noreply@zhizhu.cn}")
	private String cashDepositJobAcceptEmail;
	// 汇款银行卡校验
	private String DEPOSIT_SUCCESS_BANKNO_VALIDATE = "";
	//恒生柜台状态
	private String HS_STATUS = "";

	@PostConstruct
	protected void initRestUrl() {
		cubpBaseUrl = cubpBaseUrl.trim();
		if (!cubpBaseUrl.endsWith("/")) {
			cubpBaseUrl += "/";
		}
		DEPOSIT_SUCCESS_BANKNO_VALIDATE = cubpBaseUrl + "/proxy/fund/remittanceBankAccountValidate";
		HS_STATUS = cubpBaseUrl + "/crm_api/getHsStauts";
	}

	@Override
	public IPage<CashDepositFundsVO> selectCashDepositFundsPage(IPage<CashDepositFundsVO> page, CashDepositFundsVO cashDepositFunds) {
		return page.setRecords(baseMapper.selectCashDepositFundsPage(page, cashDepositFunds));
	}

	@Override
	public R saveIntoMoney(Long custId, CashDepositFundsReqVo vo) {
		try {
			//如果是汇丰edda，需要校验是否在入金时间段内
			if (CashConstant.EDDA.equals(vo.getBankCode())) {
				if (!isEddaTime()) {
					return R.fail(ResultCode.H5_DISPLAY_ERROR, "当前不在EDDA入金时段内");
				}
			}

			//校验汇款银行账号是否有其他客户已经入金成功
			Map<String, Object> map = new HashMap<>();
			map.put("clientId", vo.getClientId());
			map.put("fundAccount", vo.getDepositAccount());
			map.put("depositNo", vo.getRemittanceBankAccount());
			String json = getRequestJson(map);
			log.info("请求cubp校验汇款账号是否存在传入参数：" + json);
			String jsonResult = HttpClientUtils.postJson(DEPOSIT_SUCCESS_BANKNO_VALIDATE, json);
			log.info("请求cubp校验汇款账号是否存在返回信息：" + jsonResult);
			if (StringUtils.isNotBlank(jsonResult)) {
				ResponseVO responseVO = JSON.parseObject(jsonResult, ResponseVO.class);
				if (responseVO.getCode() != 0) {
					return R.fail(ResultCode.H5_DISPLAY_ERROR, "请确认您提交的汇款账号是您本人的汇款账号");
				}
			} else {
				return R.fail(ResultCode.H5_DISPLAY_ERROR, "请求校验汇款账号失败");
			}

			BpmSecuritiesInfoVO bpmSecuritiesInfoVO = new BpmSecuritiesInfoVO();
			bpmSecuritiesInfoVO.setCustId(custId);
			BpmSecuritiesInfoEntity securitiesUserInfoRespVo = bpmSecuritiesInfoService.selectBpmSecuritiesInfo(bpmSecuritiesInfoVO);

			if (securitiesUserInfoRespVo != null) {
				//线上开户
				if (securitiesUserInfoRespVo.getOpenAccountType() != null && securitiesUserInfoRespVo.getOpenAccountType() == 1) {
					if (securitiesUserInfoRespVo.getAppointmentId() != null) {
						Integer accountLevel = securitiesUserInfoRespVo.getAccountLevel();
						// 除标准账户外
						if (accountLevel != null && !"3".equals(accountLevel.toString())) {
							//如果是快捷入金 未绑卡需要做首次入金1W判断
							boolean eddafirst = true;
							if (CashConstant.EDDA.equals(vo.getBankCode()) && (vo.getDepositMoney().compareTo(new BigDecimal(10000)) < 0)) {
								//查询汇款银行账号是否有完成入金的记录
								List<CashDepositFundsEntity> successFundsByBankAccountList = baseMapper.selectList(Wrappers.<CashDepositFundsEntity>lambdaQuery()
									.eq(CashDepositFundsEntity::getCustId, custId).eq(CashDepositFundsEntity::getStatus, 3).eq(CashDepositFundsEntity::getRemittanceBankAccount, vo.getRemittanceBankAccount()));
								if (successFundsByBankAccountList == null || successFundsByBankAccountList.size() < 1) {
									eddafirst = false;
								}
							}

							if (!eddafirst) {
								return R.fail(ResultCode.H5_DISPLAY_ERROR, "该银行账号为首次入金，需存入大于1万港币以满足证监会要求");
							}
						}
					}
				}
			}

			// 获取推荐人
			CustInfoEntity custInfo = custInfoClient.userInfoByUserId(custId);
			Long inviter = custInfo.getInvCustId();
			// 默认提交状态为0
			CashDepositFundsEntity depositFunds = new CashDepositFundsEntity();
			BeanUtils.copyProperties(vo, depositFunds);
			depositFunds.setTradeAccount(vo.getClientId());
			depositFunds.setStatus(0);
			depositFunds.setCreatedTime(new Date());
			depositFunds.setModifyTime(new Date());
			depositFunds.setInviter(inviter);
			depositFunds.setCustId(custId);
			depositFunds.setCreatedTime(new Date());
			depositFunds.setModifyTime(new Date());
			if (CashConstant.EDDA.equals(vo.getBankCode())) {
				depositFunds.setGetAccount(hsbcEddaAccountIdentification);
				depositFunds.setGetAccountName(hsbcEddaAccountName);
				depositFunds.setGetBankCode(hsbcEddaGetBankCode);
				depositFunds.setGetBankNameCn(hsbcEddaBankNameCn);
				depositFunds.setGetBankNameEn(hsbcEddaBankNameEn);
			}

			baseMapper.insert(depositFunds);

			//发送通知
			cashSendMsgCommonService.sendPushEmail(custId, WebUtil.getLanguage(), CommonTemplateCode.Push.DEPOSIT_INSTRUCTION_RECEIVED, CommonTemplateCode.Email.DEPOSIT_INSTRUCTION_RECEIVED);

			sendLogMsg(vo, custId, CashConstant.EDDA.equals(vo.getBankCode()) ?
				CommonEnums.CustOperationType.FUND_APPLY_EDDI.code : CommonEnums.CustOperationType.FUND_APPLY_INTO.code);
			return R.success();
		} catch (Exception e) {
			log.error("存入资金异常", e);
			return R.fail(ResultCode.INTERNAL_ERROR);
		}
	}

	void sendLogMsg(CashDepositFundsReqVo vo, Long custId, int operationType){
		try {
			CustOperationLogEntity entity = new CustOperationLogEntity();
			entity.setCustId(custId);
			entity.setCapitalAccount(vo.getDepositAccount());
			entity.setTradeAccount(vo.getClientId());
			entity.setIp(WebUtil.getIP());
			entity.setDeviceCode(WebUtil.getHeader(TokenConstant.DEVICE_CODE));
			entity.setReqParams(com.alibaba.fastjson.JSON.toJSONString(vo));
			entity.setReqTime(new Date());
			entity.setOperationType(operationType);
			AddMessageReq addMessageReq = new AddMessageReq();
			addMessageReq.setMessage(JSONUtil.toJsonStr(entity));
			addMessageReq.setTopic(MqTopics.CUST_OPERATION_LOG_MESSAGE);
			custOperationLogProducer.sendMsg(addMessageReq);
		} catch (Exception e) {
			log.error("记录用户操作日志异常", e);
		}
	}

	@Override
	public ResponseVO updateEddaFundsStatus(ClientDepositFundsCallBackProtocol protocol) {
		Long bizId = protocol.getBizId();
		String applicationId = protocol.getApplicationId();
		String eddaFundApplicationId = protocol.getEddaFundApplicationId();
		Integer depositStatus = protocol.getDepositStatus();
		String backReason = protocol.getBackReason();

		if (bizId == null && StringUtils.isBlank(applicationId) && StringUtils.isBlank(eddaFundApplicationId)) {
			return BpmRespCodeUtils.getErrorMsg(-1, "bizId、eddaFundApplicationId不能都为空");
		}
		if (depositStatus == null) {
			return BpmRespCodeUtils.getErrorMsg(-1, "edda入金状态不能为空");
		}

		CashDepositFundsEntity depositFund = new LambdaQueryChainWrapper<>(baseMapper)
			.eq(CashDepositFundsEntity::getId, bizId)
			.one();
		if (depositFund == null && StringUtils.isNotBlank(eddaFundApplicationId)) {
			depositFund = new LambdaQueryChainWrapper<>(baseMapper)
				.eq(CashDepositFundsEntity::getEddaApplicationId, eddaFundApplicationId)
				.one();
		}
		if (depositFund == null) {
			return BpmRespCodeUtils.getErrorMsg(-1, "edda入金记录不存在");
		}

		CashDepositFundsEntity update = new CashDepositFundsEntity();
		update.setId(depositFund.getId());
		update.setStatus(depositStatus);
		update.setBackReason(backReason);
		update.setModifyTime(new Date());
		if (StringUtils.isBlank(depositFund.getEddaApplicationId()) && StringUtils.isNotBlank(eddaFundApplicationId)) {
			update.setEddaApplicationId(eddaFundApplicationId);
		}
		updateById(update);
		return BpmRespCodeUtils.getSuccessMsg(new ResponseVO());
	}

	@Override
	public ResponseVO updateEddaFundsApplicationId(ClientEddaFundsCallBackProtocol protocol) {
		Long bizId = protocol.getBizId();
		String applicationId = protocol.getApplicationId();
		String eddaApplicationId = protocol.getEddaApplicationId();

		if (bizId == null && StringUtils.isBlank(eddaApplicationId)) {
			return BpmRespCodeUtils.getErrorMsg(-1, "bizId、eddaApplicationId不能都为空");
		}
		if (StringUtils.isBlank(applicationId)) {
			return BpmRespCodeUtils.getErrorMsg(-1, "入金预约号不能为空");
		}

		CashDepositFundsEntity depositFund = new LambdaQueryChainWrapper<>(baseMapper)
			.eq(CashDepositFundsEntity::getId, bizId)
			.one();
		if (depositFund == null && StringUtils.isNotBlank(eddaApplicationId)) {
			depositFund = new LambdaQueryChainWrapper<>(baseMapper)
				.eq(CashDepositFundsEntity::getEddaApplicationId, eddaApplicationId)
				.one();
		}
		if (depositFund == null) {
			return BpmRespCodeUtils.getErrorMsg(-1, "edda入金记录不存在");
		}

		depositFund.setApplicationId(applicationId);
		depositFund.setModifyTime(new Date());
		updateById(depositFund);
		return BpmRespCodeUtils.getSuccessMsg(new ResponseVO());
	}

	@Override
	public ResponseVO updateDepositFundsStatus(ClientDepositFundsCallBackProtocol protocol) {
		String applicationId = protocol.getApplicationId();
		String eddaFundApplicationId = protocol.getEddaFundApplicationId();
		Integer depositStatus = protocol.getDepositStatus();
		Long bizId = protocol.getBizId();
		String backReason = protocol.getBackReason();

		if (bizId == null && StringUtils.isBlank(applicationId) && StringUtils.isBlank(eddaFundApplicationId)) {
			return BpmRespCodeUtils.getErrorMsg(-1, "bizId、applicationId不能都为空");
		}
		if (depositStatus == null) {
			return BpmRespCodeUtils.getErrorMsg(-1, "入金状态不能为空");
		}

		CashDepositFundsEntity depositFund = new LambdaQueryChainWrapper<>(baseMapper)
			.eq(CashDepositFundsEntity::getId, bizId)
			.one();
		if (depositFund == null && StringUtils.isNotBlank(applicationId)) {
			depositFund = new LambdaQueryChainWrapper<>(baseMapper)
				.eq(CashDepositFundsEntity::getApplicationId, applicationId)
				.one();
		}
		if (depositFund == null) {
			return BpmRespCodeUtils.getErrorMsg(-1, "中台入金记录不存在");
		}

		CashDepositFundsEntity update = new CashDepositFundsEntity();
		update.setId(depositFund.getId());
		if (StringUtils.isBlank(depositFund.getApplicationId()) && StringUtils.isNotBlank(applicationId)) {
			update.setApplicationId(applicationId);
		}
		if (StringUtils.isBlank(depositFund.getEddaApplicationId()) && StringUtils.isNotBlank(eddaFundApplicationId)) {
			update.setEddaApplicationId(eddaFundApplicationId);
		}
		depositFund.setStatus(depositStatus);
		depositFund.setBackReason(backReason);
		depositFund.setModifyTime(new Date());
		if (depositStatus == 1) {
			//BPM提交入金或eDDI成功，并且回调中台成功
			depositFund.setPushRecved(2);
		}
		updateById(depositFund);
		return BpmRespCodeUtils.getSuccessMsg(new ResponseVO());
	}

	@Override
	public R findDepositRecord(Long custId, HistoryRecordReqVo historyRecord) {
		try {
			Map<String, Object> map = new HashMap<>(2);
			// 如果类型等于1就是存入资金 等于2就是提取资金
			if (historyRecord.getType() == 1) {
				List<CashDepositFundsEntity> findDepositFunds = findDepositFunds(custId, historyRecord);
				Map<String, BigDecimal> allDepositFunds = allDepositFunds(custId);
				map.put("moneySum", allDepositFunds);
				map.put("moneyList", findDepositFunds);

			} else if (historyRecord.getType() == 2) {
				List<CashExtractingMoneyEntity> findExtratingMoney = cashExtractingMoneyService
					.findExtratingMoney(custId, historyRecord);
				Map<String, BigDecimal> allExtractingMoney = cashExtractingMoneyService
					.allExtractingMoney(custId);

				//获取万通出金记录
				try{
					CashPolicyReqDto dto = new CashPolicyReqDto();
					if (historyRecord.getCurrency() != null && historyRecord.getCurrency() != 0) {
						String desc = CashConstant.MoneyTypeDescEnum.getDesc(historyRecord.getCurrency().toString());
						if(StringUtils.isNotBlank(desc)) dto.setCurrency(desc);
					}
					if(null != historyRecord.getState() && historyRecord.getState() != 99) dto.setStatus(historyRecord.getState());
					dto.setCustId(custId);

					ResponseVO responseVO = bpmOpenApiService.findPolicyPaymentList(dto);
					if(null != responseVO && responseVO.getCode() == 0) {
						if(null != responseVO.getResult()) {
							JSONArray array = (JSONArray) responseVO.getResult();
							if(null != array && CollectionUtil.isNotEmpty(array)) {
								List<CashPolicyRespDto> policyList = JSONArray.parseArray(array.toJSONString(),CashPolicyRespDto.class);

								if(null == findExtratingMoney) findExtratingMoney = new ArrayList<>();

								BigDecimal hk = BigDecimal.ZERO;
								BigDecimal dollar = BigDecimal.ZERO;
								if(null != allExtractingMoney) {
									if(null != allExtractingMoney.get("hk")) hk = allExtractingMoney.get("hk");
									if(null != allExtractingMoney.get("dollar")) dollar = allExtractingMoney.get("dollar");
								}

								for(CashPolicyRespDto obj : policyList) {
									CashExtractingMoneyEntity extObj = new CashExtractingMoneyEntity();
									BeanUtil.copyProperties(obj,extObj);
									if(null != extObj.getCurrency()) {
										if(1 == extObj.getCurrency()) hk = hk.add(obj.getExtractionAmount());
										if(2 == extObj.getCurrency()) dollar = dollar.add(obj.getExtractionAmount());
									}
									findExtratingMoney.add(extObj);
								}

								allExtractingMoney.put("hk",hk);
								allExtractingMoney.put("dollar",dollar);

								if(findExtratingMoney.size() > 1) {
									findExtratingMoney = findExtratingMoney.stream()
										.sorted(Comparator.comparing(CashExtractingMoneyEntity::getCreatedTime,
											Comparator.nullsLast(Comparator.reverseOrder()))).collect(Collectors.toList());
								}
							}

						}
					}

				}catch (Exception ex) {
					log.error("findPolicyPaymentList error",ex);
				}

				map.put("moneySum", allExtractingMoney);
				map.put("moneyList", findExtratingMoney);
			}

			return R.data(map);
		} catch (Exception e) {
			log.error("资金存入记录查询异常", e);
			return R.fail(ResultCode.INTERNAL_ERROR);
		}
	}

	@Override
	public R saveMoneyCheckAccount(SecDepositFundsVo vo) {
		try {
			String clientId = vo.getClientId();
			String depositAccount = vo.getDepositAccount();
			String remittanceBankAccount = vo.getRemittanceBankAccount();

			if (StringUtils.isBlank(clientId) || StringUtils.isBlank(depositAccount) || StringUtils.isBlank(remittanceBankAccount)) {
				return R.fail(ResultCode.PARAMETER_ERROR);
			}

			Long count = new LambdaQueryChainWrapper<>(baseMapper)
				.eq(CashDepositFundsEntity::getTradeAccount, clientId)
				.eq(CashDepositFundsEntity::getDepositAccount, depositAccount)
				.eq(CashDepositFundsEntity::getRemittanceBankAccount, remittanceBankAccount)
				.eq(CashDepositFundsEntity::getStatus, 3)
				.count();
			Map<String, Object> re = new HashMap<>();
			re.put("count", count);
			return R.data(re);
		} catch (Exception e) {
			log.error("存入资金账号入金记录数量查询异常", e);
			return R.fail(ResultCode.INTERNAL_ERROR);
		}
	}

	@Override
	public R executeClientDepositFundApplyJob() {
		List<CashDepositFundsEntity> list = new LambdaQueryChainWrapper<>(baseMapper)
			.eq(CashDepositFundsEntity::getPushRecved, 0)
			.list();
		for (CashDepositFundsEntity cashDepositFundsEntity : list) {
			try {
				// 判断恒生状态
				String result = HttpClientUtils.get(HS_STATUS, null, Charset.forName("UTF-8"), true);
				if (StringUtils.isNotBlank(result)) {
					ResponseVO responseVO = JSONObject.parseObject(result, ResponseVO.class);
					if (responseVO.getCode() == 0) {
						String sysStauts = JSONObject.parseObject(responseVO.getResult().toString()).getString("sysStatus");
						String bankStatus = JSONObject.parseObject(responseVO.getResult().toString()).getString("bankStatus");
						if ("6".equals(sysStauts) || "0".equals(bankStatus)) {
							log.info("清算时间段内，无法出金");
							continue;
						}
					}
				}

				ClientDepositFundsApplyProtocol protocol = new ClientDepositFundsApplyProtocol();
				protocol.setBizId(cashDepositFundsEntity.getId());
				protocol.setCustId(cashDepositFundsEntity.getCustId());
				protocol.setClientId(cashDepositFundsEntity.getTradeAccount());
				protocol.setFundAccount(cashDepositFundsEntity.getDepositAccount());
				protocol.setSwiftCode(cashDepositFundsEntity.getSwiftCode());
				protocol.setDepositBankCode(cashDepositFundsEntity.getRemittanceBankCorde());
				protocol.setDepositBankId(cashDepositFundsEntity.getRemittanceBankId());
				if (cashDepositFundsEntity.getBankType() == 2) {
					//香港银行卡
					protocol.setDepositType(0);
				} else if (cashDepositFundsEntity.getBankType() == 1) {
					//大陆银行卡
					protocol.setDepositType(1);
				} else if (cashDepositFundsEntity.getBankType() == 3) {
					//其他银行
					protocol.setDepositType(2);
				}

				String bankCode = cashDepositFundsEntity.getBankCode();
				Integer remittanceType = 1;
				if (StringUtils.isNotBlank(bankCode) && bankCode.equals(CashConstant.CHECK)) {
					// 支票
					remittanceType = 2;
				}
				if (StringUtils.isNotBlank(bankCode) && bankCode.equals(CashConstant.FPS)) {
					// FPS
					remittanceType = 3;
				}
				if (StringUtils.isNotBlank(bankCode) && bankCode.equals(CashConstant.EDDA)) {
					//EDDA
					remittanceType = 4;
				}
				protocol.setRemittanceType(remittanceType);
				protocol.setDepositBank(cashDepositFundsEntity.getRemittanceBankName());
				protocol.setDepositNo(cashDepositFundsEntity.getRemittanceBankAccount());
				protocol.setDepositAccount(cashDepositFundsEntity.getRemittanceAccountNameEn());
				protocol.setBenefitBank(cashDepositFundsEntity.getGetBankNameCn());
				protocol.setBenefitBankCode(cashDepositFundsEntity.getGetBankCode());
				protocol.setBenefitNo(cashDepositFundsEntity.getGetAccount());
				protocol.setBenefitAccount(cashDepositFundsEntity.getGetAccountName());
				protocol.setDepositBalance(cashDepositFundsEntity.getDepositMoney());
				protocol.setContactAddress(cashDepositFundsEntity.getGetAddress());
				protocol.setApplicationTime(DateUtil.parseDateToStr("yyyy-MM-dd HH:mm:ss", cashDepositFundsEntity.getCreatedTime()));
				if (cashDepositFundsEntity.getCurrency() == 1) {
					//港币
					protocol.setMoneyType(2);
				} else if (cashDepositFundsEntity.getCurrency() == 2) {
					//美元
					protocol.setMoneyType(1);
				} else {
					//人民币
					protocol.setMoneyType(0);
				}

				List<String> picList = Lists.newArrayList();
				CashAccessImageEntity secAccImg = cashAccessImageService.getBaseMapper().selectById(cashDepositFundsEntity.getAccImgId());
				if (secAccImg != null) {
					picList.add(secAccImg.getAccImgPath());
				}
				secAccImg = cashAccessImageService.getBaseMapper().selectById(cashDepositFundsEntity.getAccImgIdA());
				if (secAccImg != null) {
					picList.add(secAccImg.getAccImgPath());
				}
				protocol.setDepositImage(picList);

				log.info(cashDepositFundsEntity.getCustId() + " deposit fund apply params：" + JSONUtil.toJsonStr(protocol));
				AddMessageReq message = new AddMessageReq();
				message.setMessage(JSONUtil.toJsonStr(protocol));
				boolean sendMsg = depositApplyProducer.sendMsg(message);
				if (sendMsg) {
					CashDepositFundsEntity update = new CashDepositFundsEntity();
					update.setId(cashDepositFundsEntity.getId());
					update.setPushRecved(1);
					update.setModifyTime(new Date());
					baseMapper.updateById(update);
				}
			} catch (Exception e) {
				log.error("入金申请异常", e);
				saveErrorInfo(cashDepositFundsEntity, "入金申请异常：" + e, "入金申请异常");
			}
		}
		return R.success();
	}

	@Override
	public R addPolicyPayment(CashPolicyReqDto dto) {
		if(null == dto) return null;
		Long custId = AuthUtil.getCustId();
		BpmTradeAcctRespDto acct = bpmAccountService.getCurrentAcctInfo(custId);
		if(null == acct || StringUtil.isBlank(acct.getCapitalAccount()) || StringUtil.isBlank(acct.getCapitalAccount())) return R.fail(ResultCode.NONE_DATA);
		BpmSecuritiesRespDto securitiesRespDto = bpmAccountService.getSecuritiesRespDto(custId);

		dto.setFundAccount(acct.getCapitalAccount());
		dto.setTradeAccount(acct.getTradeAccount());
		dto.setCustId(custId);
		if(null != securitiesRespDto && StringUtils.isNotBlank(securitiesRespDto.getCustNameSpell())) {
			dto.setClientNameSpell(securitiesRespDto.getCustNameSpell());
		}

		ResponseVO resp = bpmOpenApiService.addPolicyPayment(dto);
		if(null != resp && resp.getCode().intValue() == CustStaticType.CodeType.OK.getCode()) {
			return R.success();
		}
		return R.fail();
	}

	private void saveErrorInfo(CashDepositFundsEntity cashDepositFundsEntity, String errorMsg, String body) {
		log.info("用户号：" + cashDepositFundsEntity.getCustId() + " 入金申请任务异常发邮件");
		String title = "用户号：" + cashDepositFundsEntity.getCustId() + " 入金申请任务异常";
		errorMsg = errorMsg + "【" + body + "】";
		SendEmailDTO sendEmailDTO = new SendEmailDTO();
		sendEmailDTO.setContent(errorMsg);
		sendEmailDTO.setTitle(title);
		sendEmailDTO.setSender(cashDepositJobSenderEmail);
		sendEmailDTO.setAccepts(Arrays.asList(cashDepositJobAcceptEmail));
		platformMsgClient.sendEmail(sendEmailDTO);
	}

	private Map<String, BigDecimal> allDepositFunds(Long custId) {
		Map<String, BigDecimal> map = new HashMap<>(2);
		try {
			BigDecimal hk = baseMapper.selectDepositMoneySumByCurrency(1, custId);
			BigDecimal dollar = baseMapper.selectDepositMoneySumByCurrency(2, custId);
			map.put("hk", hk);
			map.put("dollar", dollar);
		} catch (Exception e) {
			log.error("查询提取资金异常" + e.getMessage());
		}
		return map;
	}

	private List<CashDepositFundsEntity> findDepositFunds(Long custId, HistoryRecordReqVo historyRecord) {
		try {
			LambdaQueryWrapper<CashDepositFundsEntity> queryWrapper = new LambdaQueryWrapper();
			queryWrapper.eq(CashDepositFundsEntity::getIsFind, 1);
			if (custId != null) {
				queryWrapper.eq(CashDepositFundsEntity::getCustId, custId);
			}
			if (historyRecord.getState() != 99) {
				queryWrapper.eq(CashDepositFundsEntity::getStatus, historyRecord.getState());
			}
			if (historyRecord.getCurrency() != null && historyRecord.getCurrency() != 0) {
				queryWrapper.eq(CashDepositFundsEntity::getCurrency, historyRecord.getCurrency());
			}
			queryWrapper.orderByDesc(CashDepositFundsEntity::getId);
			return baseMapper.selectList(queryWrapper);
		} catch (Exception e) {
			log.error("查询存入资金异常：", e);
		}
		return null;
	}

	private boolean isEddaTime() {
		boolean isEddaTime = true;
		try {
			Date now = new Date();
			String begin = bpmSystemConfigService.getSysConfigValue("HSBC_EDDA_TRADE_TIME_BEGIN", "09:00:00");
			String end = bpmSystemConfigService.getSysConfigValue("HSBC_EDDA_TRADE_TIME_END", "17:00:00");
			Date beginToday = DateUtil.parseDate(DateUtil.parseDateToStr(DateUtil.YYYY_MM_DD, now) + " " + begin);
			Date endToday = DateUtil.parseDate(DateUtil.parseDateToStr(DateUtil.YYYY_MM_DD, now) + " " + end);
			if (now.getTime() < beginToday.getTime() || now.getTime() > endToday.getTime()) {
				isEddaTime = false;
			}
		} catch (Exception e) {
			log.error("get isEddaTime Error:{}", e.getMessage(), e);
		}
		return isEddaTime;
	}

	public static String getRequestJson(Map<String, Object> requestMap) {
		Map<String, Object> finalMap = new HashMap<>();
		finalMap.put("params", requestMap);
		return JSONUtils.toJSONString(finalMap);
	}

}
