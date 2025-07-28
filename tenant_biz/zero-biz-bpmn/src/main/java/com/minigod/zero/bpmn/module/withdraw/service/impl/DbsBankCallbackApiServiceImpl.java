package com.minigod.zero.bpmn.module.withdraw.service.impl;

import static com.minigod.zero.biz.common.utils.DateUtil.YYYY_MM_DD;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.minigod.zero.biz.common.utils.DateUtil;
import com.minigod.zero.bpmn.module.account.mapper.AccountOpenInfoMapper;
import com.minigod.zero.bpmn.module.bank.entity.DbsCompanyDepositInfo;
import com.minigod.zero.bpmn.module.bank.entity.DepositBankBillFlow;
import com.minigod.zero.bpmn.module.bank.service.DbsCompanyDepositInfoService;
import com.minigod.zero.bpmn.module.bank.service.DepositBankBillFlowService;
import com.minigod.zero.bpmn.module.edda.entity.ClientEddaInfoApplicationEntity;
import com.minigod.zero.bpmn.module.edda.entity.DbsEddaReqLogEntity;
import com.minigod.zero.bpmn.module.edda.mapper.DbsEddaReqLogMapper;
import com.minigod.zero.bpmn.module.edda.service.ClientEddaInfoApplicationService;
import com.minigod.zero.bpmn.module.edda.service.DbsEddaReqLogService;
import com.minigod.zero.bpmn.module.feign.ICustomerInfoClient;
import com.minigod.zero.bpmn.module.feign.vo.CustomerAccountDetailVO;
import com.minigod.zero.bpmn.module.withdraw.bo.ClientFundWithdrawInfoBo;
import com.minigod.zero.bpmn.module.withdraw.constant.RedisKeyConstants;
import com.minigod.zero.bpmn.module.withdraw.entity.ClientFundWithdrawInfo;
import com.minigod.zero.bpmn.module.withdraw.entity.DbsRemitReqLog;
import com.minigod.zero.bpmn.module.withdraw.enums.BpmCommonEnum;
import com.minigod.zero.bpmn.module.withdraw.enums.SystemCommonEnum;
import com.minigod.zero.bpmn.module.withdraw.mapper.DbsRemitReqLogMapper;
import com.minigod.zero.bpmn.module.withdraw.service.DbsBankCallbackApiService;
import com.minigod.zero.bpmn.module.withdraw.service.IClientFundWithdrawApplicationService;
import com.minigod.zero.bpmn.module.withdraw.service.IClientFundWithdrawInfoService;
import com.minigod.zero.bpmn.module.withdraw.service.IDbsRemitReqLogService;
import com.minigod.zero.core.redis.lock.LockType;
import com.minigod.zero.core.redis.lock.RedisLockClient;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.utils.ObjectUtil;
import com.minigod.zero.core.tool.utils.StringUtil;
import com.minigod.zero.dbs.bo.DbsHeader;
import com.minigod.zero.dbs.bo.DbsTransaction;
import com.minigod.zero.dbs.bo.icn.IcnInfo;
import com.minigod.zero.dbs.bo.icn.IdnInfo;
import com.minigod.zero.platform.enums.*;
import com.minigod.zero.platform.utils.PushUtil;
import com.minigod.zero.platform.utils.SmsUtil;
import com.minigod.zero.system.feign.IDictBizClient;

import lombok.extern.slf4j.Slf4j;

/**
 * todo
 *
 * @author chenyu
 * @title DbsBankCallbackApiServiceImpl
 * @date 2023-04-13 0:29
 * @description
 */
@Slf4j
@Service
public class DbsBankCallbackApiServiceImpl implements DbsBankCallbackApiService {

    @Autowired
    private IDbsRemitReqLogService dbsRemitReqLogService;

    @Autowired
    private IClientFundWithdrawInfoService clientFundWithdrawInfoService;

    @Autowired
    private IClientFundWithdrawApplicationService withdrawApplicationService;

	@Autowired
	private DbsEddaReqLogService dbsEddaReqLogService;

	@Autowired
	private DbsEddaReqLogMapper dbsEddaReqLogMapper;
	@Autowired
	private DbsRemitReqLogMapper dbsRemitReqLogMapper;

	@Autowired
	private ClientEddaInfoApplicationService clientEddaInfoApplicationService;


	@Autowired
    private DbsCompanyDepositInfoService dbsCompanyDepositInfoService;

    @Autowired
    private DepositBankBillFlowService depositBankBillFlowService;

    @Resource
    RedisLockClient redisLockClient;

	@Autowired
	private AccountOpenInfoMapper accountOpenInfoMapper;

	@Autowired
	private IDictBizClient dictBizClient;

	@Autowired
	private ICustomerInfoClient iCustomerInfoClient;

	/**
     * 接收银行Remit流水信息 (RTGS 付款回调通知)
     *
     * @param text Remit 流水信息
     */
    @Override
    public void remitAck(String tenantId,String text) {

        // 锁相关
        String redisLockKey = null;
        boolean isLock = false;

        log.info("{} DBS API remitAck push data：{}" ,tenantId, text);
		JSONObject jsonObj = null;
		String msgId = null;
		try {
			jsonObj = JSONObject.parseObject(text);
			String headerStr = jsonObj.getString("header");
			msgId = JSONObject.parseObject(headerStr).getString("msgId");
		} catch (Exception e) {
			log.error("{} DBS API remitAck 解析数据异常：{}", text, e.getMessage());
			return;
		}

		LambdaQueryWrapper<DbsRemitReqLog> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(DbsRemitReqLog::getMsgId, msgId).orderByDesc(DbsRemitReqLog::getCreateTime).last("limit 1");
		DbsRemitReqLog dbsRemitReqLog = dbsRemitReqLogMapper.selectOne(queryWrapper);
		if (dbsRemitReqLog != null) {
            // 默认取一条(多条为极端情况)
            // 获得报文的阶段以及状态
            JSONArray txnResponses = jsonObj.getJSONArray("txnResponses");
            if (null != txnResponses && txnResponses.size() > 0) {
                // 现阶段 1笔转账只含一笔交易
                JSONObject jsonObject = txnResponses.getJSONObject(0);
                String txnStatus = jsonObject.getString("txnStatus"); // "RJCT"
                String responseType = jsonObject.getString("responseType"); // "ACK3"
                String txnRefId = jsonObject.getString("txnRefId"); // "RHH2104160002264"

                log.info("DBS API remit {}: msgId:{} appId:{} txnStatus:{}", responseType, msgId, dbsRemitReqLog.getApplicationId(), txnStatus);
                // 更新日志信息
                dbsRemitReqLog.setEnqStatus(txnStatus);
                dbsRemitReqLog.setEnqType(responseType);
                dbsRemitReqLog.setTxnRefId(txnRefId);
                dbsRemitReqLog.setUpdateTime(new Date());
                if ("ACK2".equals(responseType)) {
                    dbsRemitReqLog.setAck2Message(text);
                    dbsRemitReqLog.setAck2Time(new Date());

                    try {
                        redisLockKey = RedisKeyConstants.LOCK_DBS_REMIT_ACK_KEY_PREFIX + dbsRemitReqLog.getApplicationId();
                        isLock = redisLockClient.tryLock(redisLockKey, LockType.REENTRANT, RedisKeyConstants.MS_WAIT_EXPIRETIME, RedisKeyConstants.MS_EXPIRETIME, TimeUnit.MILLISECONDS);
                        log.info("DBS Remit ACk2获取交易锁: {} ,预约号: {}, Result: {}", redisLockKey, dbsRemitReqLog.getApplicationId(), isLock);
                        if (isLock) {
                            // 中间状态
                            if ("ACCP".equals(txnStatus) || "ACWC".equals(txnStatus)) {
                                // 更新日志
                                updateRemitFundEntity(dbsRemitReqLog, txnRefId, SystemCommonEnum.BankStateTypeEnum.PROCESSING.getValue());

                            } else if ("RJCT".equals(txnStatus)) {
                                String txnRejectCode = jsonObject.getString("txnRejectCode");
                                String txnStatusDescription = jsonObject.getString("txnStatusDescription");
                                dbsRemitReqLog.setRejCode(txnRejectCode);
                                dbsRemitReqLog.setRejDescription(txnStatusDescription);

                                // 银行转账失败 自动流转到下一步
                                ClientFundWithdrawInfo clientFundWithdrawInfoVo = clientFundWithdrawInfoService.queryByApplicationId(dbsRemitReqLog.getApplicationId());
                                //if (SystemCommonEnum.BankStateTypeEnum.COMMITTED.getValue() == clientFundWithdrawInfoVo.getBankState()) {
								if (Objects.equals(BpmCommonEnum.FundWithdrawStatus.BANK_WITHDRAW.getStatus(), clientFundWithdrawInfoVo.getStatus())) {
										// 更新状态
                                    updateRemitFundEntity(dbsRemitReqLog, txnRefId, SystemCommonEnum.BankStateTypeEnum.FAIL.getValue());
                                    log.info("ACK2执行失败，流水号:{} log:{}", dbsRemitReqLog.getApplicationId(), JSON.toJSONString(dbsRemitReqLog));

                                    // 失败后业务处理  暂时停止流程  人工打印或退款申请处理
                                    withdrawApplicationService.doBusinessAfterPayFailed(clientFundWithdrawInfoVo);
                                }
                            }
                        }
                    } catch (Exception e) {
                        log.error("Dbs Remit ACk2回调异常", e);
                    } finally {
                        if (isLock) {
                            log.info("DBS Remit ACk2解锁: {} ,预约号: {}, Result: {}", redisLockKey, dbsRemitReqLog.getApplicationId(), isLock);
                            redisLockClient.unLock(redisLockKey, LockType.REENTRANT);
							dbsRemitReqLogService.updateById(dbsRemitReqLog);

						}
                    }
                }
                else if ("ACK3".equals(responseType)) {
                    String bankReference = jsonObject.getString("bankReference"); // "1513OI7529393"
                    dbsRemitReqLog.setBankReference(bankReference);
                    dbsRemitReqLog.setAck3Message(text);
                    dbsRemitReqLog.setAck3Time(new Date());

                    try {
                        redisLockKey = RedisKeyConstants.LOCK_DBS_REMIT_ACK_KEY_PREFIX + dbsRemitReqLog.getApplicationId();
                        isLock = redisLockClient.tryLock(redisLockKey, LockType.REENTRANT, RedisKeyConstants.MS_WAIT_EXPIRETIME, RedisKeyConstants.MS_EXPIRETIME, TimeUnit.MILLISECONDS);
                        log.info("DBS Remit ACK获取交易锁: {} ,预约号: {}, Result: {}", redisLockKey, dbsRemitReqLog.getApplicationId(), isLock);
                        if (isLock) {
                            // 状态
                            if ("ACSP".equals(txnStatus) || "ACWC".equals(txnStatus)) {
                                String tranSettlementAmt = jsonObject.getString("tranSettlementAmt");
                                String tranSettlementDt = jsonObject.getString("tranSettlementDt");
                                // 校验是否完成结算
                                if (StringUtil.isNotBlank(tranSettlementAmt) && StringUtil.isNotBlank(tranSettlementDt)) {
                                    ClientFundWithdrawInfo clientFundWithdrawInfoVo = clientFundWithdrawInfoService.queryByApplicationId(dbsRemitReqLog.getApplicationId());
                                    // 银行转账成功 自动流转到下一步
                                    //if (SystemCommonEnum.BankStateTypeEnum.PROCESSING.getValue() == clientFundWithdrawInfoVo.getBankState()) {
                                    if (Objects.equals(BpmCommonEnum.FundWithdrawStatus.BANK_WITHDRAW.getStatus(), clientFundWithdrawInfoVo.getStatus())) {
                                        // 更新状态
                                        updateRemitFundEntity(dbsRemitReqLog, txnRefId, SystemCommonEnum.BankStateTypeEnum.SUCCESS.getValue());
                                        // 执行下一步
                                        String approvalOption = "DBS付款成功";
                                        withdrawApplicationService.doCommitNodeByPaySuccess(dbsRemitReqLog.getApplicationId(), approvalOption);
                                        log.info("ACK3执行成功, 流程审批完成。流水号:{} log:{}", dbsRemitReqLog.getApplicationId(), JSON.toJSONString(dbsRemitReqLog));
                                    } else {
                                        log.info("ACK3执行成功, 银行执行状态异常, 非执行中。流水号:{} BankState:{}", clientFundWithdrawInfoVo.getApplicationId(), clientFundWithdrawInfoVo.getBankState());
                                    }
                                } else {
                                    // ACK3 结算金额和结算日期为空
                                    log.info("ACK3执行失败, 未完成结算，结算金额和结算日期为空。流水号:{} log:{}", dbsRemitReqLog.getApplicationId(), JSON.toJSONString(dbsRemitReqLog));
                                    // ACK3 未完成结算 支付失败
                                    String txnRejectCode = String.valueOf(SystemCommonEnum.CodeType.DBS_REMIT_TSE_FAILED_NO_SETTLEMENT.getCode());
                                    String txnStatusDescription = SystemCommonEnum.CodeType.DBS_REMIT_TSE_FAILED_NO_SETTLEMENT.getMessage();

                                    dbsRemitAck3Failed(txnRefId, txnRejectCode, txnStatusDescription, dbsRemitReqLog);
                                }

                            } else if ("RJCT".equals(txnStatus)) {
                                // ACK3 银行拒绝
                                log.info("ACK3执行失败, DBS付款拒绝。流水号:{} log:{}", dbsRemitReqLog.getApplicationId(), JSON.toJSONString(dbsRemitReqLog));

                                String txnRejectCode = jsonObject.getString("txnRejectCode");
                                String txnStatusDescription = jsonObject.getString("txnStatusDescription");

                                // ACK3 银行拒绝 支付失败
                                dbsRemitAck3Failed(txnRefId, txnRejectCode, txnStatusDescription, dbsRemitReqLog);
                            }
                        }
                    } catch (Exception e) {
                        log.error("Dbs Remit ACk3回调异常", e);
                    } finally {
                        if (isLock) {
                            log.info("DBS Remit ACK解锁: {} ,预约号: {}, Result: {}", redisLockKey, dbsRemitReqLog.getApplicationId(), isLock);
                            redisLockClient.unLock(redisLockKey, LockType.REENTRANT);
                        }
                        dbsRemitReqLogService.updateById(dbsRemitReqLog);
                    }
                }
            }
        }else {
			log.info("DBS Remit ACK回调参数异常, 忽略处理。");
			log.info("DBS Remit ACK回调参数:{}", text);
		}
    }

    private void dbsRemitAck3Failed(String txnRefId, String txnRejectCode, String txnStatusDescription, DbsRemitReqLog dbsRemitReqLog) {

        dbsRemitReqLog.setRejCode(txnRejectCode);
        dbsRemitReqLog.setRejDescription(txnStatusDescription);
        updateRemitFundEntity(dbsRemitReqLog, txnRefId, SystemCommonEnum.BankStateTypeEnum.FAIL.getValue());
        log.info("ACK3执行失败, 流程审批完成。流水号:{} log:{}", dbsRemitReqLog.getApplicationId(), JSON.toJSONString(dbsRemitReqLog));
        // 失败后业务处理  暂时停止流程  人工打印或退款申请处理
        ClientFundWithdrawInfo clientFundWithdrawInfoVo = clientFundWithdrawInfoService.queryByApplicationId(dbsRemitReqLog.getApplicationId());
        withdrawApplicationService.doBusinessAfterPayFailed(clientFundWithdrawInfoVo);
    }

    /**
     * 更新银行和业务状态
     *
     * @param dbsRemitReqLogEntity
     * @param txnRefId
     * @param bankState
     */
    public void updateRemitFundEntity(DbsRemitReqLog dbsRemitReqLogEntity, String txnRefId, int bankState) {
        ClientFundWithdrawInfoBo fundWithdrawInfoBo = new ClientFundWithdrawInfoBo();
        fundWithdrawInfoBo.setApplicationId(dbsRemitReqLogEntity.getApplicationId());
        fundWithdrawInfoBo.setBankState(bankState);
        fundWithdrawInfoBo.setBankRefId(txnRefId);
        fundWithdrawInfoBo.setBankReference(dbsRemitReqLogEntity.getBankReference());
        fundWithdrawInfoBo.setBankRtCode(dbsRemitReqLogEntity.getRejCode());
        fundWithdrawInfoBo.setBankRtMsg(dbsRemitReqLogEntity.getRejDescription());
        clientFundWithdrawInfoService.updateClientFundWithdrawInfo(fundWithdrawInfoBo);
        log.info("DBS Remit ACK3回调更新业务状态, 流水号：{} bankStata:{} ankRtCode:{} BankMsg：{}", dbsRemitReqLogEntity.getApplicationId(), bankState,
                dbsRemitReqLogEntity.getRejCode(), dbsRemitReqLogEntity.getRejDescription());

//        // 失败更新业务状态
//        if (bankState == SystemCommonEnum.BankStateTypeEnum.FAIL.getValue()) {
//            ClientFundWithdrawApplicationBo fundWithdrawApplicationBo = new ClientFundWithdrawApplicationBo();
//            fundWithdrawApplicationBo.setApplicationId(dbsRemitReqLogEntity.getApplicationId());
//            fundWithdrawApplicationBo.setApplicationStatus(BpmCommonEnum.FundWithdrawApplicationStatus.FUND_WITHDRAW_APPLY_PAYING_FAILURE_VALUE);
//            fundWithdrawApplicationBo.setUpdateTime(new Date());
//            log.info("DBS Remit ACK3回调转账失败 更新流程状态, 流水号：{} bankStata:{} applicationStatus:{}", dbsRemitReqLogEntity.getApplicationId(), bankState, fundWithdrawApplicationBo.getApplicationStatus());
//            withdrawApplicationService.updateClientFundWithdrawApplication(fundWithdrawApplicationBo);
//        }
    }


    @Override
    public void icn(String tenantId, DbsTransaction<IcnInfo> transaction) {
        String lockKey = "ICN:" + tenantId + ":" + transaction.getTxnInfo().getTxnRefId();
        try {
            //循环直到取得锁
            while (!redisLockClient.tryLock(lockKey, LockType.FAIR, 1, 1, TimeUnit.SECONDS)) {
//                log.info(Thread.currentThread().getName() + "尝试获取锁"+DateUtil.dateTimeNow());
            }
//            log.info(Thread.currentThread().getName() +"取到锁"+DateUtil.dateTimeNow());
            IcnInfo icnInfo = transaction.getTxnInfo();
            DbsHeader header = transaction.getHeader();
            DepositBankBillFlow depositBankBillFlow = new DepositBankBillFlow();
            depositBankBillFlow.setTenantId(tenantId);
            depositBankBillFlow.setBankName("星展银行");
            //depositBankBillFlow.setBankName(icnInfo.getReceivingParty().getName());

            //到账金额
            depositBankBillFlow.setActualMoney(new BigDecimal(icnInfo.getAmtDtls().getTxnAmt()).setScale(2));
            //币种
            depositBankBillFlow.setCurrency(icnInfo.getAmtDtls().getTxnCcy());
            depositBankBillFlow.setCheckStatus(0);
            //交易类型
            depositBankBillFlow.setTxnType(icnInfo.getTxnType());
            List<DbsCompanyDepositInfo> depositInfos = dbsCompanyDepositInfoService.queryInfoByTenantId(tenantId);
            if (depositInfos.size() > 0) {
                //账户名称
                DbsCompanyDepositInfo companyDepositInfo = depositInfos.stream().filter(item -> item.getCompanyAccount().equals(icnInfo.getReceivingParty().getAccountNo())).findAny().orElse(null);
                if (companyDepositInfo == null) {
                    //子账户
                    depositBankBillFlow.setSubAccNo(icnInfo.getReceivingParty().getAccountNo());
                    //设置一个默认的公司名称
                    depositBankBillFlow.setAccName(depositInfos.get(0).getCompanyName());
                    depositBankBillFlow.setAccNo(depositInfos.get(0).getCompanyAccount());
                    //虚拟账号是子账号?
//                    depositBankBillFlow.setSubAccNo(icnInfo.getReceivingParty().getVirtualAccountNo());
//                    depositBankBillFlow. setSubAccName(icnInfo.getReceivingParty().getVirtualAccountName());

                    //账户号
                } else {
                    //公司账户名称
                    depositBankBillFlow.setAccName(icnInfo.getReceivingParty().getName());
                    //公司账户号
                    depositBankBillFlow.setAccNo(icnInfo.getReceivingParty().getAccountNo());
                }
            } else {
                //账户名称
                depositBankBillFlow.setAccName(icnInfo.getReceivingParty().getName());
                //账户号
                depositBankBillFlow.setAccNo(icnInfo.getReceivingParty().getAccountNo());
            }

            if (depositBankBillFlow.getSubAccNo() != null) {
                //设置默认入资账户
            }
            //汇款用户名称
            depositBankBillFlow.setSenderAccName(icnInfo.getSenderParty().getName());
            //汇款银行账号
            depositBankBillFlow.setSenderAccNo(icnInfo.getSenderParty().getAccountNo());

            //汇款银行编号
            depositBankBillFlow.setSenderBankId(icnInfo.getSenderParty().getSenderBankId());
            depositBankBillFlow.setFlowSource(1);
            depositBankBillFlow.setTimeStamp(header.getTimeStamp());
            depositBankBillFlow.setProcessingTime(cn.hutool.core.date.DateUtil.formatTime(header.getTimeStamp()));
            depositBankBillFlow.setMsgId(header.getMsgId());
            depositBankBillFlow.setParticulars(com.alibaba.fastjson.JSONObject.toJSONString(transaction));
//            depositBankBillFlow.setValueDate(DateUtil.parseDate(icnInfo.getValueDt(), YYYY_MM_DD));
            depositBankBillFlow.setValueDate(DateUtil.parseDate(icnInfo.getTxnDate(), YYYY_MM_DD));
            depositBankBillFlow.setReferenceNo(icnInfo.getTxnRefId());
            depositBankBillFlow.setCustomerReference(icnInfo.getCustomerReference());
            depositBankBillFlow.setIsRepeat(false);
            if (ObjectUtil.isNotEmpty(depositBankBillFlowService.queryByFlow(depositBankBillFlow))) {
                depositBankBillFlow.setIsRepeat(true);
            }
            depositBankBillFlowService.save(depositBankBillFlow);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
//            log.info(Thread.currentThread().getName()+"解锁");
            redisLockClient.unLock(lockKey, LockType.FAIR);
        }
    }
    @Override
    public void idn(String tenantId, DbsTransaction<IdnInfo> transaction) {
        String lockKey = "IDN:" + tenantId + ":" + transaction.getTxnInfo().getTxnRefId();
        try {
            //循环直到取得锁
            while (!redisLockClient.tryLock(lockKey, LockType.FAIR, 1, 1, TimeUnit.SECONDS)) {
//                log.info(Thread.currentThread().getName() + "尝试获取锁"+DateUtil.dateTimeNow());
            }
//            log.info(Thread.currentThread().getName() +"取到锁"+DateUtil.dateTimeNow());
            IcnInfo icnInfo = transaction.getTxnInfo();
            DbsHeader header = transaction.getHeader();
            DepositBankBillFlow depositBankBillFlow = new DepositBankBillFlow();
            depositBankBillFlow.setTenantId(tenantId);
            depositBankBillFlow.setBankName("星展银行");

            //到账金额
            depositBankBillFlow.setActualMoney(new BigDecimal(icnInfo.getAmtDtls().getTxnAmt()).setScale(2));
            //币种
            depositBankBillFlow.setCurrency(icnInfo.getAmtDtls().getTxnCcy());
            depositBankBillFlow.setCheckStatus(0);
            //交易类型
            depositBankBillFlow.setTxnType(icnInfo.getTxnType());
            List<DbsCompanyDepositInfo> depositInfos = dbsCompanyDepositInfoService.queryInfoByTenantId(tenantId);
            if (depositInfos.size() > 0) {
                //账户名称
                DbsCompanyDepositInfo companyDepositInfo = depositInfos.stream().filter(item -> item.getCompanyAccount().equals(icnInfo.getReceivingParty().getAccountNo())).findAny().orElse(null);
                if (companyDepositInfo == null) {
                    //子账户
                    depositBankBillFlow.setSubAccNo(icnInfo.getReceivingParty().getAccountNo());
                    //设置一个默认的公司名称
                    depositBankBillFlow.setAccName(depositInfos.get(0).getCompanyName());
                    depositBankBillFlow.setAccNo(depositInfos.get(0).getCompanyAccount());
                    //虚拟账号是子账号?
//                    depositBankBillFlow.setSubAccNo(icnInfo.getReceivingParty().getVirtualAccountNo());
//                    depositBankBillFlow. setSubAccName(icnInfo.getReceivingParty().getVirtualAccountName());

                    //账户号
                } else {
                    //公司账户名称
                    depositBankBillFlow.setAccName(icnInfo.getReceivingParty().getName());
                    //公司账户号
                    depositBankBillFlow.setAccNo(icnInfo.getReceivingParty().getAccountNo());
                }
            } else {
                //账户名称
                depositBankBillFlow.setAccName(icnInfo.getReceivingParty().getName());
                //账户号
                depositBankBillFlow.setAccNo(icnInfo.getReceivingParty().getAccountNo());
            }

            if (depositBankBillFlow.getSubAccNo() != null) {
                //设置默认入资账户
            }
            //汇款用户名称
            depositBankBillFlow.setSenderAccName(icnInfo.getSenderParty().getName());
            //汇款银行账号
            depositBankBillFlow.setSenderAccNo(icnInfo.getSenderParty().getAccountNo());

            //汇款银行编号
            depositBankBillFlow.setSenderBankId(icnInfo.getSenderParty().getSenderBankId());
            depositBankBillFlow.setFlowSource(1);
            depositBankBillFlow.setTimeStamp(header.getTimeStamp());
            depositBankBillFlow.setMsgId(header.getMsgId());
            depositBankBillFlow.setParticulars(com.alibaba.fastjson.JSONObject.toJSONString(transaction));
//            depositBankBillFlow.setValueDate(DateUtil.parseDate(icnInfo.getValueDt(), YYYY_MM_DD));
            depositBankBillFlow.setValueDate(DateUtil.parseDate(icnInfo.getTxnDate(), YYYY_MM_DD));
            depositBankBillFlow.setReferenceNo(icnInfo.getTxnRefId());
            depositBankBillFlow.setCustomerReference(icnInfo.getCustomerReference());
            depositBankBillFlow.setIsRepeat(false);
            if (ObjectUtil.isNotEmpty(depositBankBillFlowService.queryByFlow(depositBankBillFlow))) {
                depositBankBillFlow.setIsRepeat(true);
            }
            depositBankBillFlowService.save(depositBankBillFlow);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
//            log.info(Thread.currentThread().getName()+"解锁");
            redisLockClient.unLock(lockKey, LockType.FAIR);
        }
    }

	/**
	 * edda Ack2 响应报文处理
	 * @param tenantId
	 * @param text
	 */
	@Override
	public void eddaAck2(String tenantId, String text) {
		String lockKey = "eddaAck2:" + tenantId + ":" + UUID.randomUUID();
		try {
			//循环直到取得锁
			while (!redisLockClient.tryLock(lockKey, LockType.FAIR, 1, 1, TimeUnit.SECONDS)) {
                log.info(Thread.currentThread().getName() + "尝试获取锁"+DateUtil.dateTimeNow());
			}
            log.info(Thread.currentThread().getName() +"取到锁"+DateUtil.dateTimeNow());

			log.info("DBS API EDDA ACK2 push data：" + text);
			cn.hutool.json.JSONObject jsonObj = new cn.hutool.json.JSONObject(text);
			String headerStr = jsonObj.getStr("header");
			String msgId = new cn.hutool.json.JSONObject(headerStr).getStr("msgId");
			//根据msgId获得log
			DbsEddaReqLogEntity getDbsEddaReq = new DbsEddaReqLogEntity();
			getDbsEddaReq.setMsgid(msgId);
			LambdaQueryWrapper<DbsEddaReqLogEntity> queryWrapper = new LambdaQueryWrapper<>();
			queryWrapper.eq(DbsEddaReqLogEntity::getMsgid, msgId);
			queryWrapper.orderByDesc(DbsEddaReqLogEntity::getCreatetime);
			List<DbsEddaReqLogEntity> eddaReqLogList = dbsEddaReqLogMapper.selectList(queryWrapper);

			if (eddaReqLogList != null && eddaReqLogList.size() > 0) {
				//写入响应报文到 dbs edda log表 获取唯一值 msg_id
				String txnResponse = jsonObj.getStr("txnResponse");
				String txnStatus = new cn.hutool.json.JSONObject(txnResponse).getStr("txnStatus");
				DbsEddaReqLogEntity updateDbsEddaReq = new DbsEddaReqLogEntity();
				updateDbsEddaReq.setId(eddaReqLogList.get(0).getId());
				updateDbsEddaReq.setEnqstatus(txnStatus);
				updateDbsEddaReq.setAckmessage(text);
				updateDbsEddaReq.setAcktime(DateUtil.getTime());
				updateDbsEddaReq.setUpdatetime(new Date());
				//updateDbsEddaReq.setUpdateUser(UserUtils.getBackgroundWorkflowUser().getId());
				dbsEddaReqLogService.updateById(updateDbsEddaReq);

				//调用更新edda 表数据
				LambdaQueryWrapper<ClientEddaInfoApplicationEntity> clientEddaInfoApplicationEntityLambdaQueryWrapper = new LambdaQueryWrapper<>();
				clientEddaInfoApplicationEntityLambdaQueryWrapper.eq(ClientEddaInfoApplicationEntity::getApplicationId, eddaReqLogList.get(0).getApplicationid());
				ClientEddaInfoApplicationEntity clientEddaInfo = clientEddaInfoApplicationService.getOne(clientEddaInfoApplicationEntityLambdaQueryWrapper);
				clientEddaInfo.setMsgId(msgId);
				if ("ACCT".equals(txnStatus)) {//成功
					String mandateId = new cn.hutool.json.JSONObject(txnResponse).getStr("mandateId");
					String effDate = new cn.hutool.json.JSONObject(txnResponse).getStr("effDate");
					String expDate = new cn.hutool.json.JSONObject(txnResponse).getStr("expDate");
					String ddaRef = new cn.hutool.json.JSONObject(txnResponse).getStr("ddaRef");
					String txnRefId = new cn.hutool.json.JSONObject(txnResponse).getStr("txnRefId");

					clientEddaInfo.setDdaRef(ddaRef);
					clientEddaInfo.setTxnRefId(txnRefId);
					clientEddaInfo.setMandateId(mandateId);
					clientEddaInfo.setEddaState(SystemCommonEnum.EddaStateEnum.SUCCESS.getCode());
					if (!StringUtils.isEmpty(effDate)) {
						clientEddaInfo.setEffDate(cn.hutool.core.date.DateUtil.parse(effDate, "yyyy-MM-dd"));
					}
					if (!StringUtils.isEmpty(expDate)) {
						clientEddaInfo.setExpDate(cn.hutool.core.date.DateUtil.parse(expDate, "yyyy-MM-dd"));
					}
					clientEddaInfo.setUpdateTime(new Date());

					try {
						R<CustomerAccountDetailVO> accountDetail = iCustomerInfoClient.selectCustomerDetailByAccountId(clientEddaInfo.getClientId());
						if (accountDetail.isSuccess()) {
							CustomerAccountDetailVO accountOpenInfoVO = accountDetail.getData();

						List<String> params = new ArrayList<>();
						params.add(clientEddaInfo.getBankName());
						params.add(clientEddaInfo.getDepositAccount().substring(clientEddaInfo.getDepositAccount().length()-4));

						PushUtil.builder()
							.msgGroup("P")
							.custId(accountOpenInfoVO.getCustId())
							.params(params)
							.group(MsgStaticType.DisplayGroup.SERVICE_MSG)
							.pushType(PushTypeEnum.STRONG_MSG.getTypeValue())
							.templateCode(PushTemplate.EDDA_AUTH_SUCCESS.getCode())
							.sendWay(InformEnum.SendWayTimeEnum.FORTHWITH.getTypeCode())
							.pushAsync();
						}

						//发送短信
						if (clientEddaInfo.getMobile() != null) {
							String phoneNumber[] = clientEddaInfo.getMobile().split("-");
							String bankName = clientEddaInfo.getBankName();
							String depositAccount = clientEddaInfo.getDepositAccount();
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

					} catch (Exception e) {
						e.printStackTrace();
						log.error("推送消息失败",e);
					}

				} else if ("RJCT".equals(txnStatus)) {//失败

					String txnRejectCode = new cn.hutool.json.JSONObject(txnResponse).getStr("txnRejectCode");
					String txnStatusDescription = new cn.hutool.json.JSONObject(txnResponse).getStr("txnStatusDescription");
					String txnRefId = new cn.hutool.json.JSONObject(txnResponse).getStr("txnRefId");
					clientEddaInfo.setTxnRefId(txnRefId);
					clientEddaInfo.setEddaState(SystemCommonEnum.EddaStateEnum.FAIL.getCode());
					clientEddaInfo.setRejCorde(txnRejectCode);
					clientEddaInfo.setRejDescription(txnStatusDescription);

					try {
						R<CustomerAccountDetailVO> accountDetail = iCustomerInfoClient.selectCustomerDetailByAccountId(clientEddaInfo.getClientId());
						if (accountDetail.isSuccess()) {
							CustomerAccountDetailVO accountOpenInfoVO = accountDetail.getData();
							List<String> params = new ArrayList<>();
							params.add(clientEddaInfo.getBankName());
							params.add(clientEddaInfo.getDepositAccount().substring(clientEddaInfo.getDepositAccount().length()-4));
							params.add(dictBizClient.getValue("company", "CO_Phone_HK").getData());

							PushUtil.builder()
								.msgGroup("P")
								.custId(accountOpenInfoVO.getCustId())
								.params(params)
								.group(MsgStaticType.DisplayGroup.SERVICE_MSG)
								.pushType(PushTypeEnum.STRONG_MSG.getTypeValue())
								.templateCode(PushTemplate.EDDA_AUTH_FAIL.getCode())
								.sendWay(InformEnum.SendWayTimeEnum.FORTHWITH.getTypeCode())
								.pushAsync();
						}

					} catch (Exception e) {
						e.printStackTrace();
						log.error("推送消息失败",e);
					}

				}if ("ACCP".equals(txnStatus)){
					//授权中
					//{"header":{"msgId":"HKO82F2449701656579","orgId":"0123","timeStamp":"2018-04-12T10:53:29.996","ctry":"HK"},"txnResponse":{"responseType":"ACK1","txnRefId":"HKD645329791","txnStatus":"ACCP","mandateId":"MNTD123"}}
					String txnRefId = new cn.hutool.json.JSONObject(txnResponse).getStr("txnRefId");
					String mandateId = new cn.hutool.json.JSONObject(txnResponse).getStr("mandateId");
					clientEddaInfo.setTxnRefId(txnRefId);
					clientEddaInfo.setMandateId(mandateId);
					clientEddaInfo.setUpdateTime(new Date());
					clientEddaInfo.setEddaState(SystemCommonEnum.EddaStateEnum.AUTHORIZING.getCode());

				}
				clientEddaInfo.setUpdateTime(new Date());
				clientEddaInfoApplicationService.updateById(clientEddaInfo);

			}
			log.info("DBS API EDDA ACK2 Msg ID[" + msgId + "] data be put in storage");


		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			redisLockClient.unLock(lockKey, LockType.FAIR);
		}
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

}
