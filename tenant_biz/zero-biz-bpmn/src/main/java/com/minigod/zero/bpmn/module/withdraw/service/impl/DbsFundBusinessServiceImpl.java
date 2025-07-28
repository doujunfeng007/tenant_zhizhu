package com.minigod.zero.bpmn.module.withdraw.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.minigod.zero.bpmn.module.withdraw.bo.ClientFundWithdrawInfoBo;
import com.minigod.zero.bpmn.module.withdraw.mapper.ClientFundWithdrawInfoMapper;
import com.minigod.zero.bpmn.module.withdraw.service.IClientFundWithdrawInfoService;
import com.minigod.zero.dbs.bo.AccountBalanceRequestBo;
import com.minigod.zero.dbs.bo.DbsBaseRequestVO;
import com.minigod.zero.dbs.bo.FundTransferEntity;
import com.minigod.zero.dbs.enums.BankApiFuncTypeEnum;
import com.minigod.zero.dbs.protocol.AccountBalResponse;
import com.minigod.zero.dbs.protocol.DbsApiProtocol;
import com.minigod.zero.dbs.vo.FpsTransactionRequestVO;
import com.minigod.zero.dbs.vo.TseTransactionEnquiryRequestVO;
import com.minigod.zero.bpmn.module.bank.feign.DbsService;
import com.minigod.zero.bpmn.module.withdraw.entity.DbsRemitReqLog;
import com.minigod.zero.bpmn.module.withdraw.enums.SystemCommonEnum;
import com.minigod.zero.bpmn.module.withdraw.service.DbsFundBusinessService;
import com.minigod.zero.bpmn.module.withdraw.service.IDbsRemitReqLogService;
import com.minigod.zero.bpmn.utils.ApplicationIdUtil;
import com.minigod.zero.core.log.exception.ServiceException;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.utils.StringUtil;
import com.minigod.zero.platform.utils.RandomStringGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Dbs业务处理
 *
 * @author chenyu
 * @title DbsFundBusinessServiceImpl
 * @date 2023-04-13 7:32
 * @description
 */
@Slf4j
@Service
public class DbsFundBusinessServiceImpl implements DbsFundBusinessService {

//    @Autowired
//    private GspDbsService gspDbsService;

   @Autowired
	   @Lazy
	IClientFundWithdrawInfoService clientFundWithdrawInfoService;
    @Autowired
    IDbsRemitReqLogService dbsRemitReqLogService;

    @Autowired
    DbsService dbsService;

    /**
     * 查询银行账户单币种余额（BLE）
     *
     * @param accountNo  银行账号
     * @param accountCcy 币种
     * @return
     */
    @Override
    public AccountBalResponse sendBleQuery(String tenantId, String accountNo, String accountCcy) {
        //交易对账流水号,每笔交易唯一
        String msgId = "BLE" + ApplicationIdUtil.generateDbsMsgId("");
        R responseVO = null;
        try {
            if ("MCA".equals(accountCcy)) {
                R.fail("接口暂时不支持");
            }
            AccountBalanceRequestBo accountBalanceRequestBo = new AccountBalanceRequestBo();
            accountBalanceRequestBo.setMsgId(msgId);
            accountBalanceRequestBo.setAccountNo(accountNo);
            accountBalanceRequestBo.setAccountCcy(accountCcy);
            responseVO = new R();// gspDbsService.ble(accountBalanceRequestBo);
            if (responseVO.isSuccess()) {
                return (AccountBalResponse) responseVO.getData();
            }
        } catch (Exception e) {
            log.error("查询银行账户异常", e);
        }
        return null;
    }

    /**
     * 查询银行账户综合余额（BLE）
     *
     * @param accountNo 银行账号
     * @return
     */
    @Override
    public List<AccountBalResponse> sendBleQuery(String tenantId, String accountNo) {
        //交易对账流水号,每笔交易唯一
        String msgId = "BLE" + ApplicationIdUtil.generateDbsMsgId("");
        R responseVO = null;
        try {
            AccountBalanceRequestBo accountBalanceRequestBo = new AccountBalanceRequestBo();
            accountBalanceRequestBo.setMsgId(msgId);
            accountBalanceRequestBo.setAccountNo(accountNo);
            accountBalanceRequestBo.setAccountCcy("MCA");
            responseVO = new R();// gspDbsService.ble(accountBalanceRequestBo);
            if (responseVO.isSuccess()) {
                return (List<AccountBalResponse>) responseVO.getData();
            }
        } catch (Exception e) {
            log.error("查询银行账户异常", e);
        }
        return null;
    }

    /**
     * FPS Id 信息查询
     *
     * @param fpsIdQueryBo
     * @return
     */
    @Override
    public R<?> sendFpsIdQuery(BankApiFuncTypeEnum funcTypeEnum, FundTransferEntity fpsIdQueryBo) {
        //交易对账流水号,每笔交易唯一
        String msgId = "CSE" + ApplicationIdUtil.generateDbsMsgId(fpsIdQueryBo.getTenantId());
        fpsIdQueryBo.setMsgId(msgId);

        R<String> result = null;
        try {
            // 执行请求
            log.info("dbs send {} quest: {} ", msgId, JSON.toJSONString(fpsIdQueryBo));
            result = dbsService.sendFpsIdEnquiryFund(fpsIdQueryBo);
            log.info("dbs send {} reult: {} ", msgId, JSON.toJSONString(result));
            if (result.isSuccess()) {
                // 解析报文
                DbsApiProtocol dbsApiProtocol = JSONObject.parseObject(result.getData(), DbsApiProtocol.class);
                DbsApiProtocol.EnqResponse enqInfo = dbsApiProtocol.getEnqResponse();
                if (enqInfo != null) {
                    return R.data(enqInfo, "查询成功");
                } else {
                    return R.fail();
                }
            } else {
                return result;
            }
        } catch (Exception e) {
            log.error("dbs send {} quest: {} ", msgId, fpsIdQueryBo, e);
        }
        return result;
    }

    /**
     * 查询交易状态信息（TSE）
     * request
     *
     * @return
     */
    @Override
    public R sendTseQuery(TseTransactionEnquiryRequestVO requestVO) {
        if (null == requestVO) {
            return R.fail(SystemCommonEnum.CodeType.PARAMETER_ERROR.getCode(), SystemCommonEnum.CodeType.PARAMETER_ERROR.getMessage());
        }

        //交易对账流水号,每笔交易唯一
        String msgId = "TSE" + ApplicationIdUtil.generateDbsMsgId("");
        requestVO.setMsgId(msgId);

        R<String> result = null;
        try {
            log.info("dbs send {} quest: {} ", msgId, JSON.toJSONString(requestVO));
            result = dbsService.sendTseEnquiry(requestVO);
            log.info("dbs send {} reult: {} ", msgId, JSON.toJSONString(result));
            if (result.isSuccess()) {
                String data = result.getData();
                if (StringUtil.isNotBlank(data)) {
                    DbsApiProtocol protocol = JSONObject.parseObject(data, DbsApiProtocol.class);
                    if (protocol != null) {
                        if (protocol.getError() == null && null != protocol.getTxnEnqResponse()) {
                            String enqStatus = protocol.getTxnEnqResponse().getEnqStatus();
                            if (null != enqStatus && SystemCommonEnum.BankRespStatus.ACSP.getType().equals(enqStatus)) {
                                return R.data(protocol.getTxnEnqResponse());
                            } else if (null != enqStatus && SystemCommonEnum.BankRespStatus.RJCT.getType().equals(enqStatus)) {
                                return R.fail(protocol.getTxnEnqResponse().getEnqStatusDescription());
                            }
                        } else {
                            return R.fail(SystemCommonEnum.CodeType.API_GATEWAY_ERROR.getCode(), SystemCommonEnum.CodeType.API_GATEWAY_ERROR.getMessage());
                        }
                    }
                }
            } else {
                return R.fail();
            }
        } catch (Exception e) {
            return R.fail("查询交易状态信息出错");
        }
        return R.fail("无满足条件的数据");
    }

    /**
     * 银行转账入口(FPS PPP GPP ACT CHATS\RTGS TT)
     *
     * @param fundWithdrawInfoBo
     * @return
     */
    @Override
    public <T extends DbsBaseRequestVO> SystemCommonEnum.BankStateTypeEnum sendDbsCommonRemitFund(BankApiFuncTypeEnum funcTypeEnum, SystemCommonEnum.PayTypeEnum payTypeEnum, T fundWithdrawInfoBo) {
        String dbsApiName = payTypeEnum.getShortValue();

        //交易对账流水号,每笔交易唯一
        String msgId = dbsApiName + ApplicationIdUtil.generateDbsMsgId(fundWithdrawInfoBo.getTenantId()==null?"000000":fundWithdrawInfoBo.getTenantId());
		String cusRef = fundWithdrawInfoBo.getCusRef();

        // 封装参数
        fundWithdrawInfoBo.setMsgId(msgId);

        // 请求日志
        DbsRemitReqLog dbsRemitReq = new DbsRemitReqLog();
        dbsRemitReq.setApplicationId(fundWithdrawInfoBo.getApplicationId());
        dbsRemitReq.setTenantId(fundWithdrawInfoBo.getTenantId()==null?"000000":fundWithdrawInfoBo.getTenantId());
        dbsRemitReq.setTxnType(payTypeEnum.getShortValue());
        dbsRemitReq.setMsgId(msgId);
        dbsRemitReq.setCusRef(cusRef);
        dbsRemitReq.setReqMessage(JSON.toJSONString(fundWithdrawInfoBo));
        dbsRemitReq.setReqTime(new Date());
        dbsRemitReq.setCreateTime(new Date());
        dbsRemitReqLogService.save(dbsRemitReq);

        // 执行请求
        log.info("dbs send {} quest: {} ", msgId, fundWithdrawInfoBo);
        R<String> result = sendDbsFund(funcTypeEnum, fundWithdrawInfoBo);
        log.info("dbs send {} reult: {} ", msgId, JSON.toJSONString(result));
        if (!result.isSuccess()) {
            log.info("DBS {} {} 出金请求,请求银行连接失败", dbsApiName, fundWithdrawInfoBo.getApplicationId());
            dbsRemitReq.setEnqStatus(String.valueOf(result.getCode()));
            dbsRemitReq.setEnqType(SystemCommonEnum.BankEnqType.ACK1.getType());
            dbsRemitReq.setRejCode(String.valueOf(result.getCode()));
            dbsRemitReq.setRejDescription(result.getMsg());
            dbsRemitReq.setAck1Time(new Date());
            dbsRemitReq.setUpdateTime(new Date());
            dbsRemitReqLogService.updateById(dbsRemitReq);
            throw new ServiceException(result.getMsg());
        }

        // 银行报文
        String responsBody = result.getData();

        // 过滤网关错误
        JSONObject responseJsonObj = JSONObject.parseObject(responsBody);
        if (responseJsonObj.containsKey("error")) {
            responseJsonObj = responseJsonObj.getJSONObject("error");
            String status = responseJsonObj.getString("status");
            String code = responseJsonObj.getString("code");
            String description = responseJsonObj.getString("description");

            log.info("DBS {} {} 付款请求，银行反馈错误", dbsApiName, fundWithdrawInfoBo.getApplicationId());
            dbsRemitReq.setEnqStatus(status);
            dbsRemitReq.setEnqType(SystemCommonEnum.BankEnqType.ACK1.getType());
            dbsRemitReq.setAck1Message(responsBody);
            dbsRemitReq.setAck1Time(new Date());
            dbsRemitReq.setUpdateTime(new Date());
            dbsRemitReq.setRejCode(code);
            dbsRemitReq.setRejDescription(description);
            dbsRemitReqLogService.updateById(dbsRemitReq);
            throw new ServiceException(description);
        } else {
            dbsRemitReq.setAck1Message(responsBody);
            dbsRemitReq.setAck1Time(new Date());
            dbsRemitReq.setUpdateTime(new Date());
            // 更新请求日志
            dbsRemitReqLogService.updateById(dbsRemitReq);

			String txnRefId = responseJsonObj.getString("txnRefId");//银行流水号
			if (StringUtil.isNotBlank(txnRefId)) {
				ClientFundWithdrawInfoBo fundWithdrawInfoVo = new ClientFundWithdrawInfoBo();
				fundWithdrawInfoVo.setApplicationId(fundWithdrawInfoBo.getApplicationId());
				fundWithdrawInfoVo.setBankRefId(txnRefId);
				clientFundWithdrawInfoService.updateClientFundWithdrawInfo(fundWithdrawInfoVo);
			}
        }

        return analysisResponsBody(payTypeEnum,responsBody, dbsRemitReq.getId());
    }

    /**
     * 根据支付类型 请求不同转账接口到 DBS
     * @param funcTypeEnum
     * @param fundWithdrawInfoBo
     * @param <T>
     * @return
     */
    private <T extends DbsBaseRequestVO> R sendDbsFund(BankApiFuncTypeEnum funcTypeEnum, T fundWithdrawInfoBo) {
        R<String> result = R.fail("未定义的转账类型");
        if (funcTypeEnum.equals(BankApiFuncTypeEnum.FPS_GPC)) {
			FpsTransactionRequestVO fundBo = (FpsTransactionRequestVO) fundWithdrawInfoBo;
			log.info("dbs FPS_GPC send {} ", JSON.toJSONString(fundBo));
            result = dbsService.sendFpsGpcFund( fundBo);
        } else if (funcTypeEnum.equals(BankApiFuncTypeEnum.FPS_GPP)) {
			FundTransferEntity fundBo = (FundTransferEntity) fundWithdrawInfoBo;
			log.info("dbs FPS_GPP send {} ", JSON.toJSONString(fundBo));
            result = dbsService.sendFpsGppFund( fundBo);
        } else if (funcTypeEnum.equals(BankApiFuncTypeEnum.FPS_PPP)) {
			FundTransferEntity fundBo = (FundTransferEntity) fundWithdrawInfoBo;
			log.info("dbs FPS_PPP send {} ", JSON.toJSONString(fundBo));
            result = dbsService.sendFpsPPPFund( fundBo);
        } else if (funcTypeEnum.equals(BankApiFuncTypeEnum.ACT)) {
			FundTransferEntity fundBo = (FundTransferEntity) fundWithdrawInfoBo;
			log.info("dbs ACT send {} ", JSON.toJSONString(fundBo));
            result = dbsService.sendActFund(fundBo);
        } else if (funcTypeEnum.equals(BankApiFuncTypeEnum.CHATS)) {
			FundTransferEntity fundBo = (FundTransferEntity) fundWithdrawInfoBo;
			log.info("dbs CHATS send {} ", JSON.toJSONString(fundBo));
            result = dbsService.sendChatsFund(fundBo);
        } else if (funcTypeEnum.equals(BankApiFuncTypeEnum.TT)) {
			FundTransferEntity fundBo = (FundTransferEntity) fundWithdrawInfoBo;
			log.info("dbs TT send {} ", JSON.toJSONString(fundBo));
			result = dbsService.sendTTFund(fundBo);
        }
		log.info("dbs result: {} ",  result);
        return result;
    }

    /**
     * 处理 FPS 付款协议包
     *
     * @param responsBody
     * @param reqLogId
     * @return
     */
    private SystemCommonEnum.BankStateTypeEnum doProcessFpsPackage(SystemCommonEnum.PayTypeEnum payTypeEnum, String responsBody, Long reqLogId) {
        // 成功标志
        boolean isSuccess = false;

        // 声明请求日志
        DbsRemitReqLog dbsRemitReqLogBo = new DbsRemitReqLog();
        dbsRemitReqLogBo.setId(reqLogId);
        dbsRemitReqLogBo.setUpdateTime(new Date());

        //解析响应报文
        JSONObject jsonObj = JSONObject.parseObject(responsBody);
        //交易请求结果响应报文
        JSONObject txnResponse = jsonObj.getJSONObject("txnResponse");
        String txnStatus = txnResponse.getString("txnStatus");
        String txnRefId = txnResponse.getString("txnRefId");//银行流水号
        String txnType = txnResponse.getString("txnType");

        if (StringUtil.isBlank(txnType)) {
            txnType = payTypeEnum.getShortValue();
        }

        // 设置参数 请求结果  银行流水号
        dbsRemitReqLogBo.setTxnType(txnType);
        dbsRemitReqLogBo.setEnqStatus(txnStatus);
        dbsRemitReqLogBo.setTxnRefId(txnRefId);
        dbsRemitReqLogBo.setEnqType(SystemCommonEnum.BankEnqType.ACK1.getType());

        //如果交易拒绝保存拒绝信息
        if ("RJCT".equals(txnStatus)) {
            String txnRejectCode = txnResponse.getString("txnRejectCode");
            String txnStatusDescription = txnResponse.getString("txnStatusDescription");

            // 出错信息
            dbsRemitReqLogBo.setRejCode(txnRejectCode);
            dbsRemitReqLogBo.setRejDescription(txnStatusDescription);

            //如果是 I103 && DUPL-ACTC-Success 交易已经成功 返回的查询结果 处理为成功
            if ("I103".equals(txnRejectCode)) {
                if (txnStatusDescription.contains("ACTC")) {
                    //String settAmt = new JSONObject(txnResponse).getStr("txnSettlementAmt");
                    //String settDt = new JSONObject(txnResponse).getStr("txnSettlementDt");

                    // 到账金额和日期
                    //dbsRemitReqLogBo.setSettlementAmt(new BigDecimal(settAmt));
                    //dbsRemitReqLogBo.setSettlementDt(DateUtil.parseYYYYMMDDDate(settDt));
                    // 成功处理
                    isSuccess = true;
                }
            }
        } else if ("ACTC".equals(txnStatus) || "ACWC".equals(txnStatus)) {
            //String settAmt = txnResponseJsonObj.getStr("txnSettlementAmt");
            //String settDt = txnResponseJsonObj.getStr("txnSettlementDt");
            //
            //// 到账金额和日期
            //resfpsReqLog.setSettlementAmt(new BigDecimal(settAmt));
            //resfpsReqLog.setSettlementDt(DateUtil.parseYYYYMMDDDate(settDt));

            //请求完成更新数据
            isSuccess = true;
        }
        // 更新请求日志
        dbsRemitReqLogService.updateById(dbsRemitReqLogBo);

        // 正在处理
        if (SystemCommonEnum.BankRespStatus.PDNG.getType().equals(txnStatus)) {
            // 进行中
            return SystemCommonEnum.BankStateTypeEnum.PROCESSING;
        } else {
            if (isSuccess) {
                // 处理成功
                return SystemCommonEnum.BankStateTypeEnum.SUCCESS;
            } else {
                // 处理失败
                return SystemCommonEnum.BankStateTypeEnum.FAIL;
            }
        }
    }

    /**
     * 处理 RTGS ACT TT 转账申请
     * 发起请求，等待remit ack 回调
     *
     * @param responsBody
     * @param reqLogId
     * @return
     */
    private SystemCommonEnum.BankStateTypeEnum doProcessRemitPackage(SystemCommonEnum.PayTypeEnum payTypeEnum, String responsBody, Long reqLogId) {
        // 成功标志
        boolean isSuccess = false;

        // 声明请求日志
        DbsRemitReqLog dbsRemitReqLogBo = new DbsRemitReqLog();
        dbsRemitReqLogBo.setId(reqLogId);
        dbsRemitReqLogBo.setUpdateTime(new Date());

        //解析响应报文
        JSONObject jsonObj = JSONObject.parseObject(responsBody);
        //交易请求结果响应报文 按照 一笔转账只包含一笔交易(咱不做批量转账)
        JSONArray txnResponses = jsonObj.getJSONArray("txnResponses");
        if (null != txnResponses && txnResponses.size() > 0) {
            // 现阶段 1笔转账只含一笔交易
            JSONObject jsonObject = txnResponses.getJSONObject(0);
            String txnStatus = jsonObject.getString("txnStatus");
            String responseType = jsonObject.getString("responseType");
            String txnType = jsonObject.getString("txnType");
            String txnRefId = jsonObject.getString("txnRefId");

            if (StringUtil.isBlank(txnType)) {
                txnType = payTypeEnum.getShortValue();
            }

            // 设置参数 请求结果  银行流水号
            dbsRemitReqLogBo.setEnqType(responseType);
            dbsRemitReqLogBo.setEnqStatus(txnStatus);
            dbsRemitReqLogBo.setTxnType(txnType);
            dbsRemitReqLogBo.setTxnRefId(txnRefId);

            log.info("dbs remit data : {} ", dbsRemitReqLogBo);
            //如果交易拒绝保存拒绝信息
            if ("RJCT".equals(txnStatus)) {
                String txnRejectCode = jsonObject.getString("txnRejectCode");
                String txnStatusDescription = jsonObject.getString("txnStatusDescription");
                dbsRemitReqLogBo.setRejCode(txnRejectCode);
                dbsRemitReqLogBo.setRejDescription(txnStatusDescription);
            } else if ("ACTC".equals(txnStatus)) {
                //请求完成更新数据
                isSuccess = true;
            }
            // 更新请求日志
            dbsRemitReqLogService.updateById(dbsRemitReqLogBo);

            // 查询请求日志
            if (isSuccess) {
                // 请求成功
                return SystemCommonEnum.BankStateTypeEnum.COMMITTED;
            } else {
                // 请求失败
                return SystemCommonEnum.BankStateTypeEnum.FAIL;
            }
        }

        return null;
    }




    /**
     * 解析DBS响应报文并返回银行状态枚举
     * @param payTypeEnum
     * @param responsBody
     * @param reqLogId
     * @return
     */
    private SystemCommonEnum.BankStateTypeEnum analysisResponsBody(SystemCommonEnum.PayTypeEnum payTypeEnum ,String responsBody, Long reqLogId) {
        // 根据转账类型处理
        SystemCommonEnum.BankStateTypeEnum bankStateTypeEnum = null;
        switch (payTypeEnum) {
            case FPS_PPP:
                bankStateTypeEnum = doProcessFpsPackage(payTypeEnum, responsBody, reqLogId);
                break;
            case FPS_GPP:
                bankStateTypeEnum = doProcessFpsPackage(payTypeEnum, responsBody, reqLogId);
                break;
            case FPS_GCP:
                bankStateTypeEnum = doProcessFpsPackage(payTypeEnum, responsBody, reqLogId);
                break;
            case DBS_RTGS:
                bankStateTypeEnum = doProcessRemitPackage(payTypeEnum, responsBody, reqLogId);
                break;
            case DBS_TT:
                bankStateTypeEnum = doProcessRemitPackage(payTypeEnum, responsBody, reqLogId);
                break;
            case DBS_ACT:
                bankStateTypeEnum = doProcessRemitPackage(payTypeEnum, responsBody, reqLogId);
                break;
            default:
                bankStateTypeEnum = null;
                break;
        }
        // 响应处理
        return bankStateTypeEnum;
    }

}
