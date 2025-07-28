package com.minigod.zero.dbs.impl;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson2.JSON;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.dbs.bo.DbsIccBankFlowEntity;
import com.minigod.zero.dbs.bo.FundTransferEntity;
import com.minigod.zero.dbs.config.DbsBankConfig;
import com.minigod.zero.dbs.enums.BankRespCodeEnum;
import com.minigod.zero.dbs.enums.PayTypeEnum;
import com.minigod.zero.dbs.protocol.DbsReqPackag;
import com.minigod.zero.dbs.service.DbsApiBusinessService;
import com.minigod.zero.dbs.util.DbsCommManageUtils;
import com.minigod.zero.dbs.vo.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * @ClassName: DbsApiBusinessServiceImpl
 * @Description: 报文加密并请求银行对应接口
 * @Author chenyu
 * @Date 2024/3/21
 * @Version 1.0
 */
@Slf4j
@Service("dbsApiBusinessService")
@AllArgsConstructor
public class DbsApiBusinessServiceImpl implements DbsApiBusinessService {

    /**
     * 银行配置
     */
    private final DbsBankConfig dbsBankConfig;

    /**
     * Dbs银行Api路径定义
     */
    private static String EDDA_SETUP_URL = "api/hk/edda/v4/provision/setup";
    private static String EDDA_ENQUIRY_URL = "api/hk/edda/v4/enquiry/transactionstatus";
    private static String HK_BLE_URL = "api/rg/account/v4/enquiry/balance";
    private static String HK_VAC_URL = "api/hk/sva/v4/provision/request";
    private static String HK_FPS_URL = "api/hk/fps/v4/payment/transaction";
    private static String HK_ACT_URL = "api/hk/remittance/v4/payment/transaction";
    private static String HK_FPS_ID_ENQUIRY_URL = "api/hk/proxy/v4/enquiry/verification";
    private static String HK_RFD_URL = "api/hk/fps/v4/refund/transaction";
    private static String HK_CHATS_URL = "api/hk/remittance/v4/payment/transaction";
    private static String HK_TT_URL = "api/hk/remittance/v4/payment/transaction";
    private static String HK_ARE_URL = "api/rg/account/v4/enquiry/range";
    private static String HK_DAY_REPORT = "api/rg/account/v4/statement/eod";
    private static String HK_HOUR_REPORT = "api/rg/account/v4/statement/intraday";
    private static String HK_TSE_URL = "api/rg/remittance/v4/enquiry/transactionstatus";

    /**
     * edda initiation 授权请求
     *
     * @param eddaInfo
     * @return
     */
    @Override
    public R<String> sendEDDAInitiation(EddaInfoRequestVO eddaInfo) {
        R responseVO = new R();
        String decResponse = null;
        try {
            String msgId = eddaInfo.getMsgId();
            String ddaRef = eddaInfo.getDdaRef();
            String pubStr = DbsReqPackag.eDDAInitiationReq(msgId, dbsBankConfig.getOrgId(), ddaRef, eddaInfo);
			log.info("请求DBS eDDA授权,请求 pubStr:{}", pubStr);

            String business = "DbsEDDAInitiation";
            //开始加密明文报文
            String sendStr = DbsCommManageUtils.encrypt(pubStr, business, msgId);
            //开始发送请求,获得响应密文 (调试不同接口需要修改 accountUrl/KeyId)
            String responseMes = DbsCommManageUtils.send(EDDA_SETUP_URL, sendStr, business, msgId);
            if (StringUtils.isEmpty(responseMes)) {
                log.info("请求DBS eDDA授权,返回报文为空,msgId:{}", eddaInfo.getMsgId());
                responseVO.setCode(BankRespCodeEnum.ErrorCodeType.CONNECT_TIMEOUT.getCode());
                responseVO.setMsg(BankRespCodeEnum.ErrorCodeType.CONNECT_TIMEOUT.getMessage());
            } else {
                decResponse = DbsCommManageUtils.decrypt(responseMes, business, msgId);
                if (StringUtils.isEmpty(decResponse)) {
                    log.info(msgId + "DBS edda setup 账户授权，银行响应的报文无法解析");
                    responseVO.setCode(BankRespCodeEnum.ErrorCodeType.ERROR_DECRYPT.getCode());
                    responseVO.setMsg(BankRespCodeEnum.ErrorCodeType.ERROR_DECRYPT.getMessage());
                } else {
                    //responseVO.setData(decResponse);
					return  R.data(decResponse);
                }
            }

            // 测试
//            decResponse = " {\"header\":{\"msgId\":\"2021020211218100026\",\"orgId\":\"HKGUOS01\",\"timeStamp\":\"2021-05-07T11:35:26.264\",\"ctry\":\"HK\"},\"txnResponse\":{\"responseType\":\"ACK1\",\"txnRefId\":\"HKEDDA21020200000001\",\"txnStatus\":\"ACCP\",\"mandateId\":\"016/MNDTEDDA21020200000001\"}}";
//
//            responseVO.setData(decResponse);

        } catch (Exception e) {
            log.error("请求DBS eDDA授权异常, msgId:{}", eddaInfo.getMsgId(), e);
            return R.fail(e.getMessage());

        }
        return responseVO;
    }

    /**
     * FPS GPC 转账
     *
     * @param fpsTransaction
     */
    @Override
    public R<String> sendFpsGpcFund(FpsTransactionRequestVO fpsTransaction) {
        R responseVO = new R();
        String decResponse = null;
        try {
            String msgId = fpsTransaction.getMsgId();
            //生成请求明文信息
            String pubStr = DbsReqPackag.hkFPSGPCReq(msgId, dbsBankConfig.getOrgId(), fpsTransaction);
			log.info("请求DBS eDDA FPS GPC 转账 pubStr:{}", pubStr);
            String business = "FPS GCP Specification";
            //开始加密明文报文
            String sendStr = DbsCommManageUtils.encrypt(pubStr, business, msgId);
            //开始发送请求,获得响应密文 (调试不同接口需要修改 accountUrl/KeyId)
            String responseMes = DbsCommManageUtils.send(HK_FPS_URL, sendStr, business, msgId);
            if (StringUtils.isEmpty(responseMes)) {
                log.info("请求DBS Fps GPC 入金请求银行，返回报文为空, msgId:{}", fpsTransaction.getMsgId());
                responseVO.setCode(BankRespCodeEnum.ErrorCodeType.CONNECT_TIMEOUT.getCode());
                responseVO.setMsg(BankRespCodeEnum.ErrorCodeType.CONNECT_TIMEOUT.getMessage());
            } else {
                decResponse = DbsCommManageUtils.decrypt(responseMes, business, msgId);
                if (StringUtils.isEmpty(decResponse)) {
                    log.info(msgId + "DBS edda setup 账户授权，银行响应的报文无法解析");
                    responseVO.setCode(BankRespCodeEnum.ErrorCodeType.ERROR_DECRYPT.getCode());
                    responseVO.setMsg(BankRespCodeEnum.ErrorCodeType.ERROR_DECRYPT.getMessage());
                } else {
                    //responseVO.setData(decResponse);
					return  R.data(decResponse);
                }
            }

            // 测试
//            decResponse = "{\"header\":{\"msgId\":\"GPC20210202124881100171\",\"timeStamp\":\"2021-02-02T13:00:40.025\"},\"txnResponse\":{\"customerReference\":\"GCP202102021248811001\",\"paymentReference\":\"\",\"txnRefId\":\"IRGPCHK020221130039A0003004\",\"bankReference\":\"IRGPCHK020221130039A0003004\",\"txnType\":\"GPC\",\"txnRejectCode\":\"\",\"txnStatusDescription\":\"Success\",\"txnStatus\":\"ACTC\",\"txnSettlementAmt\":\"119\",\"txnSettlementDt\":\"2021-02-02\"}}";
//
//            responseVO.setData(decResponse);

        } catch (Exception e) {
            log.error("请求DBS fps GPC入金请求银行异常, msgId：{}", fpsTransaction.getMsgId(), e);
            return R.fail(e.getMessage());
        }
        return responseVO;
    }

    /**
     * 银行自动转账(Fps Gpp)
     *
     * @param fundWithdrawApplyEntity
     * @return
     */
    @Override
    public R<String> sendFpsGppFund(FundTransferEntity fundWithdrawApplyEntity) {
        String dbsApiName = PayTypeEnum.FPS_GPP.getShortValue();

        //交易对账流水号,每笔交易唯一
        //String msgId = dbsApiName + fundWithdrawApplyEntity.getApplicationId();
        //String cusRef = dbsApiName + fundWithdrawApplyEntity.getApplicationId();
        // 封装参数
        String msgId = fundWithdrawApplyEntity.getMsgId();
        if (StringUtils.isBlank(fundWithdrawApplyEntity.getMsgId())) {
            msgId = dbsApiName + fundWithdrawApplyEntity.getApplicationId();
        }

        //生成请求明文信息
        String pubStr = DbsReqPackag.hkFPSGPPReq(msgId, dbsBankConfig.getOrgId(), fundWithdrawApplyEntity);

        String business = "FPS " + dbsApiName + " Specification";
        //开始加密明文报文
        String sendStr = DbsCommManageUtils.encrypt(pubStr, business, msgId);
        //开始发送请求,获得响应密文 (调试不同接口需要修改 accountUrl/KeyId)
        String responseMes = DbsCommManageUtils.send(HK_FPS_URL, sendStr, business, msgId);
        if (StringUtils.isEmpty(responseMes)) {
            log.info(fundWithdrawApplyEntity.getApplicationId() + "请求DBS FPS GPP 出金请求银行，返回报文为空");
            return R.fail(100, "请求银行接口异常");
        }
        String decResponse = DbsCommManageUtils.decrypt(responseMes, business, msgId);
        if (StringUtils.isEmpty(decResponse)) {
            log.info("DBS {} {} 出金请求，银行响应的报文无法解析", dbsApiName, fundWithdrawApplyEntity.getApplicationId());
            return R.fail(100, "操作失败，解析银行响应的数据异常！");
        }
        // 响应处理
        return R.data(decResponse);
    }

    /**
     * 银行自动转账(Fps PPP)
     *
     * @param fundWithdrawApplyEntity
     * @return
     */
    @Override
    public R<String> sendFpsPPPFund(FundTransferEntity fundWithdrawApplyEntity) {
        String dbsApiName = PayTypeEnum.FPS_PPP.getShortValue();

        //交易对账流水号,每笔交易唯一
        //String msgId = dbsApiName + fundWithdrawApplyEntity.getApplicationId();
        String msgId = fundWithdrawApplyEntity.getMsgId();
        if (StringUtils.isBlank(fundWithdrawApplyEntity.getMsgId())) {
            msgId = dbsApiName + fundWithdrawApplyEntity.getApplicationId();
        }

        // 封装参数
        //fundWithdrawApplyEntity.setCusRef(cusRef);

        //生成请求明文信息
        String pubStr = DbsReqPackag.hkFPSPPPReq(msgId, dbsBankConfig.getOrgId(), fundWithdrawApplyEntity);

        String business = "FPS " + dbsApiName + " Specification";
        //开始加密明文报文
        String sendStr = DbsCommManageUtils.encrypt(pubStr, business, msgId);
        //开始发送请求,获得响应密文 (调试不同接口需要修改 accountUrl/KeyId)
        String responseMes = DbsCommManageUtils.send(HK_FPS_URL, sendStr, business, msgId);
        if (StringUtils.isEmpty(responseMes)) {
            log.info(fundWithdrawApplyEntity.getApplicationId() + "请求DBS FPS {} 出金请求银行，返回报文为空", dbsApiName);
            return R.fail(100, "请求银行接口异常");
        }
        String decResponse = DbsCommManageUtils.decrypt(responseMes, business, msgId);
        if (StringUtils.isEmpty(decResponse)) {
            log.info(fundWithdrawApplyEntity.getApplicationId() + "DBS FPS {} 出金请求，银行响应的报文无法解析", dbsApiName);
            return R.fail(100, "操作失败，解析银行响应的数据异常！");
        }
        // 响应处理
        return R.data(decResponse);
    }

    /**
     * 银行转账(Dbs Act 同行转账)
     *
     * @param fundWithdrawApplyEntity
     * @return
     */
    @Override
    public R<String> sendActFund(FundTransferEntity fundWithdrawApplyEntity) {
        String dbsApiName = PayTypeEnum.DBS_ACT.getShortValue();
        //交易对账流水号,每笔交易唯一
        //String msgId = dbsApiName + fundWithdrawApplyEntity.getApplicationId();
//        String cusRef = OrderUtil.getCusRefByLogId(remitReqLog.getId());
        String msgId = fundWithdrawApplyEntity.getMsgId();
        if (StringUtils.isBlank(fundWithdrawApplyEntity.getMsgId())) {
            msgId = dbsApiName + fundWithdrawApplyEntity.getApplicationId();
        }

        // 生成请求明文信息
        String pubStr = DbsReqPackag.hkActReq(msgId, dbsBankConfig.getOrgId(), fundWithdrawApplyEntity);

        String business = dbsApiName + " Specification";
        //开始加密明文报文
        String sendStr = DbsCommManageUtils.encrypt(pubStr, business, msgId);
        //开始发送请求,获得响应密文 (调试不同接口需要修改 accountUrl/KeyId)
        String responseMes = DbsCommManageUtils.send(HK_ACT_URL, sendStr, business, msgId);
        if (StringUtils.isEmpty(responseMes)) {
            log.info(fundWithdrawApplyEntity.getApplicationId() + "请求DBS {} 出金请求银行，返回报文为空", dbsApiName);
            return R.fail(100, "请求银行接口异常");
        }
        String decResponse = DbsCommManageUtils.decrypt(responseMes, business, msgId);
        //String decResponse = "{\"header\":{\"msgId\":\"20180722HKLTT1301\",\"orgId\":\"HK0001\",\"timeStamp\":\"2018-07-22T08:13:30.690\",\"ctry\":\"HK\"},\"txnResponses\":[{\"responseType\":\"ACK1\",\"msgRefId\":\"TEST0001\",\"txnStatus\":\"ACTC\",\"txnStatusDescription\":\"Request Received\"}]}";
        if (StringUtils.isEmpty(decResponse)) {
            log.info("DBS {} {} 出金请求，银行响应的报文无法解析", dbsApiName, fundWithdrawApplyEntity.getApplicationId());
            return R.fail(100, "操作失败，解析银行响应的数据异常！");
        }

        // 响应处理
        return R.data(decResponse);
    }

    /**
     * fpsid查询
     *
     * @param fundWithdrawApplyEntity
     * @return
     */
    @Override
    public R<String> sendFpsIdEnquiryFund(FundTransferEntity fundWithdrawApplyEntity) {
        String dbsApiName = "FpsId";

        //交易对账流水号,每笔交易唯一
        //String msgId = dbsApiName + fundWithdrawApplyEntity.getApplicationId();
//        String cusRef = OrderUtil.getCusRefByLogId(remitReqLog.getId());
        String msgId = fundWithdrawApplyEntity.getMsgId();
        if (StringUtils.isBlank(msgId)) {
            msgId = dbsApiName + fundWithdrawApplyEntity.getApplicationId();
        }

        // 生成请求明文信息
        String pubStr = DbsReqPackag.hkFpsIdEnquiryReq(msgId, dbsBankConfig.getOrgId(), fundWithdrawApplyEntity);
		log.info("fpsid查询请求报文：{}", pubStr);

        String business = dbsApiName + " Specification";
        //开始加密明文报文
        String sendStr = DbsCommManageUtils.encrypt(pubStr, business, msgId);
        //开始发送请求,获得响应密文 (调试不同接口需要修改 accountUrl/KeyId)
        String responseMes = DbsCommManageUtils.send(HK_FPS_ID_ENQUIRY_URL, sendStr, business, msgId);
        if (StringUtils.isEmpty(responseMes)) {
            log.info(fundWithdrawApplyEntity.getApplicationId() + "请求DBS {} 出金请求银行，返回报文为空", dbsApiName);
            return R.fail(100, "请求银行接口异常");
        }
        String decResponse = DbsCommManageUtils.decrypt(responseMes, business, msgId);
        //String decResponse = "{\"header\":{\"msgId\":\"20180722HKLTT1301\",\"orgId\":\"HK0001\",\"timeStamp\":\"2018-07-22T08:13:30.690\",\"ctry\":\"HK\"},\"txnResponses\":[{\"responseType\":\"ACK1\",\"msgRefId\":\"TEST0001\",\"txnStatus\":\"ACTC\",\"txnStatusDescription\":\"Request Received\"}]}";
        if (StringUtils.isEmpty(decResponse)) {
            log.info("DBS {} {} 出金请求，银行响应的报文无法解析", dbsApiName, fundWithdrawApplyEntity.getApplicationId());
            return R.fail(100, "操作失败，解析银行响应的数据异常！");
        }

        // 响应处理
        return R.data(decResponse);
    }


    /**
     * RFD退款
     *
     * @param fundWithdrawApplyEntity
     * @return
     */
    @Override
    public R<String> sendRFDFund(FundTransferEntity fundWithdrawApplyEntity) {

        String dbsApiName = "sendRFDFund";

        //交易对账流水号,每笔交易唯一
        String msgId = dbsApiName + fundWithdrawApplyEntity.getApplicationId();
//        String cusRef = OrderUtil.getCusRefByLogId(remitReqLog.getId());

        // 生成请求明文信息
        String pubStr = DbsReqPackag.hkRFDReq(msgId, dbsBankConfig.getOrgId(), fundWithdrawApplyEntity);

        String business = dbsApiName + " Specification";
        //开始加密明文报文
        String sendStr = DbsCommManageUtils.encrypt(pubStr, business, msgId);
        //开始发送请求,获得响应密文 (调试不同接口需要修改 accountUrl/KeyId)
        String responseMes = DbsCommManageUtils.send(HK_RFD_URL, sendStr, business, msgId);
        if (StringUtils.isEmpty(responseMes)) {
            log.info(fundWithdrawApplyEntity.getApplicationId() + "请求DBS {} 出金请求银行，返回报文为空", dbsApiName);
            return R.fail(100, "请求银行接口异常");
        }
        String decResponse = DbsCommManageUtils.decrypt(responseMes, business, msgId);
        if (StringUtils.isEmpty(decResponse)) {
            log.info("DBS {} {} 出金请求，银行响应的报文无法解析", dbsApiName, fundWithdrawApplyEntity.getApplicationId());
            return R.fail(100, "操作失败，解析银行响应的数据异常！");
        }

        // 响应处理
        return R.data(decResponse);
    }

    /**
     * 银行转账(Dbs CHATS\RTGS 跨行转账)
     *
     * @param fundWithdrawApplyEntity
     * @return
     */
    @Override
    public R<String> sendChatsFund(FundTransferEntity fundWithdrawApplyEntity) {
        String dbsApiName = PayTypeEnum.DBS_RTGS.getShortValue();

        //交易对账流水号,每笔交易唯一
//        String msgId = dbsApiName + fundWithdrawApplyEntity.getApplicationId();
//        String cusRef = OrderUtil.getCusRefByLogId(remitReqLog.getId());
        String msgId = fundWithdrawApplyEntity.getMsgId();
        if (StringUtils.isBlank(fundWithdrawApplyEntity.getMsgId())) {
            msgId = dbsApiName + fundWithdrawApplyEntity.getApplicationId();
        }

        //生成请求明文信息
        String pubStr = DbsReqPackag.hkChatsReq(msgId, dbsBankConfig.getOrgId(), fundWithdrawApplyEntity);

        String business = dbsApiName + " Specification";
        //开始加密明文报文
        String sendStr = DbsCommManageUtils.encrypt(pubStr, business, msgId);
        //开始发送请求,获得响应密文 (调试不同接口需要修改 accountUrl/KeyId)
        String responseMes = DbsCommManageUtils.send(HK_CHATS_URL, sendStr, business, msgId);
        if (StringUtils.isEmpty(responseMes)) {
            log.info(fundWithdrawApplyEntity.getApplicationId() + "请求DBS {} 出金请求银行，返回报文为空", dbsApiName);
            return R.fail(100, "请求银行" + dbsApiName + "接口异常");
        }
        String decResponse = DbsCommManageUtils.decrypt(responseMes, business, msgId);
        if (StringUtils.isEmpty(decResponse)) {
            log.info("DBS {} {} 出金请求，银行响应的报文无法解析", dbsApiName, fundWithdrawApplyEntity.getApplicationId());
            return R.fail(100, "操作失败，银行响应的报文无法解析！");
        }

        // 响应处理
        return R.data(decResponse);
    }

    /**
     * 银行转账(Dbs TT 海外转账)
     *
     * @param fundWithdrawApplyEntity
     * @return
     */
    @Override
    public R<String> sendTTFund(FundTransferEntity fundWithdrawApplyEntity) {
        String dbsApiName = PayTypeEnum.DBS_TT.getShortValue();

        //交易对账流水号,每笔交易唯一
//        String msgId = dbsApiName + fundWithdrawApplyEntity.getApplicationId() + remitReqLog.getId();
//        String cusRef = OrderUtil.getCusRefByLogId(remitReqLog.getId());
        String msgId = fundWithdrawApplyEntity.getMsgId();
        if (StringUtils.isBlank(fundWithdrawApplyEntity.getMsgId())) {
            msgId = dbsApiName + fundWithdrawApplyEntity.getApplicationId();
        }

        //生成请求明文信息
        String pubStr = DbsReqPackag.hkTTReq(msgId, dbsBankConfig.getOrgId(), fundWithdrawApplyEntity);

        String business = dbsApiName + " Specification";
        //开始加密明文报文
        String sendStr = DbsCommManageUtils.encrypt(pubStr, business, msgId);
        //开始发送请求,获得响应密文 (调试不同接口需要修改 accountUrl/KeyId)
        String responseMes = DbsCommManageUtils.send(HK_TT_URL, sendStr, business, msgId);
        if (StringUtils.isEmpty(responseMes)) {
            log.info(fundWithdrawApplyEntity.getApplicationId() + "请求DBS {} 出金请求银行，返回报文为空", dbsApiName);
            return R.fail(100, "请求银行" + dbsApiName + "接口异常");
        }
        String decResponse = DbsCommManageUtils.decrypt(responseMes, business, msgId);
        if (StringUtils.isEmpty(decResponse)) {
            log.info("DBS {} {} 出金请求，银行响应的报文无法解析", dbsApiName, fundWithdrawApplyEntity.getApplicationId());
            return R.fail(100, "操作失败，银行响应的报文无法解析！");
        }

        // 响应处理
        return R.data(decResponse);
    }

    /** #######################################################*/
    /** ####################  DBS API 查询服务 #################*/
    /** #######################################################*/

    /**
     * 查询银行账户（BLE）
     * accountNo  银行账号
     * accountCcy 币种 MCA:全币种 HKD:港币
     *
     * @return
     */
    @Override
    public R<String> sendBleQuery(AccountBalanceRequestVO accountBalanceRequestVO) {
        R responseVO = new R();
        //交易对账流水号,每笔交易唯一
        String msgId = accountBalanceRequestVO.getMsgId();
        //生成请求明文信息
        String pubStr = DbsReqPackag.hkBleReq(msgId, dbsBankConfig.getOrgId(), accountBalanceRequestVO.getAccountNo(), accountBalanceRequestVO.getAccountCcy());
        String business = "BLE";
        //开始加密明文报文
        String sendStr = DbsCommManageUtils.encrypt(pubStr, business, msgId);
        //开始发送请求,获得响应密文 (调试不同接口需要修改 accountUrl/KeyId)
        String responseMes = DbsCommManageUtils.send(HK_BLE_URL, sendStr, business, msgId);
        if (StringUtils.isEmpty(responseMes)) {
            log.info("请求DBS BLE 查询账户余额，返回报文为空，msgId:{}", msgId);
            responseVO.setCode(BankRespCodeEnum.ErrorCodeType.CONNECT_TIMEOUT.getCode());
            responseVO.setMsg(BankRespCodeEnum.ErrorCodeType.CONNECT_TIMEOUT.getMessage());
        } else {
            String decResponse = DbsCommManageUtils.decrypt(responseMes, business, msgId);
            if (StringUtils.isEmpty(decResponse)) {
                log.info("DBS BLE 账户查询，银行响应的报文无法解析, msgId:{}", msgId);
                responseVO.setCode(BankRespCodeEnum.ErrorCodeType.ERROR_DECRYPT.getCode());
                responseVO.setMsg(BankRespCodeEnum.ErrorCodeType.ERROR_DECRYPT.getMessage());
            } else {

                responseVO.setData(decResponse);
            }
        }

        // 测试
//        String decResponse = "{\"header\":{\"msgId\":\"BLE202104231043\",\"orgId\":\"HKGUOS01\",\"timeStamp\":\"2021-04-27T15:39:31.639\"},\"accountBalResponse\":{\"enqStatus\":\"ACSP\",\"accountName\":\"\",\"accountNo\":\"001242462\",\"accountCcy\":\"HKD\",\"halfDayHold\":\"0.0000\",\"oneDayHold\":\"0.0000\",\"twoDaysHold\":\"0.0000\",\"clsLedgerBal\":\"775527592.0000\",\"clsAvailableBal\":\"775527592.0000\",\"businessDate\":\"2021-04-27 00:00:00.0\"}}";
//
//        responseVO.setData(decResponse);

        return responseVO;
    }

    /**
     * 子账户增删操作
     */
    @Override
    public R<String> sendProvisionQuery(SubAccountRequestVO subAccountRequestVO) {
        R responseVO = new R();
        //交易对账流水号,每笔交易唯一
        String msgId = subAccountRequestVO.getMsgId();
        //生成请求明文信息
        String pubStr = DbsReqPackag.hkProvisionReq(dbsBankConfig.getOrgId(), subAccountRequestVO);
        String business = "VAC";
        //开始加密明文报文
        String sendStr = DbsCommManageUtils.encrypt(pubStr, business, msgId);
        //开始发送请求,获得响应密文 (调试不同接口需要修改 accountUrl/KeyId)
        String responseMes = "";
        //如果是删除，采用delete方式
        if ("D".equals(subAccountRequestVO.getRequestType())) {
            responseMes = DbsCommManageUtils.sendDelete(HK_VAC_URL, sendStr, business, msgId);
        } else {
            responseMes = DbsCommManageUtils.send(HK_VAC_URL, sendStr, business, msgId);
        }

        if (StringUtils.isEmpty(responseMes)) {
            log.info("请求DBS VAC 子账户操作，返回报文为空，msgId:{}", msgId);
            responseVO.setCode(BankRespCodeEnum.ErrorCodeType.CONNECT_TIMEOUT.getCode());
            responseVO.setMsg(BankRespCodeEnum.ErrorCodeType.CONNECT_TIMEOUT.getMessage());
        } else {
            String decResponse = DbsCommManageUtils.decrypt(responseMes, business, msgId);
            if (StringUtils.isEmpty(decResponse)) {
                log.info("DBS VAC 子账户操作，银行响应的报文无法解析, msgId:{}", msgId);
                responseVO.setCode(BankRespCodeEnum.ErrorCodeType.ERROR_DECRYPT.getCode());
                responseVO.setMsg(BankRespCodeEnum.ErrorCodeType.ERROR_DECRYPT.getMessage());
            } else {

                responseVO.setData(decResponse);
            }
        }

        return responseVO;
    }


    /**
     * ICC流水请求星展 ACCOUNT RANGE ENQUIRY MESSAGE
     */
    @Override
    public R<String> sendICCARE(DbsIccBankFlowEntity dbsIccBankFlowEntity) {

        String decResponse = null;
        try {
            String areMsgId = DateUtil.format(new Date(), "yyyyMMddHHmmssSSS");
            Map<String, Object> accInfo = new HashMap<String, Object>(5);
            accInfo.put("accountNo", dbsIccBankFlowEntity.getReceiveAccNo());
            accInfo.put("accountCcy", dbsIccBankFlowEntity.getTxnCcy());
            accInfo.put("fromDate", DateUtil.format(dbsIccBankFlowEntity.getTxnDate(), "yyyy-MM-dd"));
            accInfo.put("toDate", DateUtil.format(dbsIccBankFlowEntity.getTxnDate(), "yyyy-MM-dd"));
            accInfo.put("drCrInd", "D");
            //accInfo.put("noTxnInd", "1000");
            //accInfo.put("chronoFlag","Y");
            String pubStr = DbsReqPackag.accountRangeEnquityReq(areMsgId, dbsBankConfig.getOrgId(), accInfo);
            log.info("sendICCARE data: {}", pubStr);

            String business = "AREBusiness";
            //开始加密明文报文
            String sendStr = DbsCommManageUtils.encrypt(pubStr, business, areMsgId);
            //开始发送请求,获得响应密文 (调试不同接口需要修改 accountUrl/KeyId)
            String responseMes = DbsCommManageUtils.send(HK_ARE_URL, sendStr, business, areMsgId);
            decResponse = DbsCommManageUtils.decrypt(responseMes, business, areMsgId);
            //"{\"header\":{\"msgId\":\"2020052137LXTEST\",\"orgId\":\"HK9FPS\",\"timeStamp\":\"2020-05-21T15:15:22.127\",\"ctry\":\"HK\"},\"txnEnqResponse\":{\"enqStatus\":\"ACSP\",\"acctInfo\":[{\"accountNo\":\"000292053\",\"accountCcy\":\"USD\",\"availableBal\":\"10000.0000 (as of 2020-05-21)\",\"initiatingParty\":[{\"name\":\"Name1\",\"txnInfo\":[{\"drCrInd\":\"D\",\"txnCode\":\"TRF\",\"txnDesc\": \"TRANSFER\\rCHGS T210520152026 EBHVT91105898309 EBHVT91105898309 DBS-IDEALPAYE\\rDBS-IDEALPAYEEWITHENDINGSPACE      \\rHKD 100  \\rUETR Ref:01ca35bb-3ff2-448a-9693-bf0b15c8cffc\",\"txnDate\":\"2020-05-21 00:00:00.000\",\"valueDate\":\"2020-05-21 00:00:00.000\",\"txnCcy\":\"USD\",\"txnAmount\":\"100.0000\"},{\"drCrInd\":\"D\",\"txnCode\":\"TRF\",\"txnDesc\": \"FPS FEE for Dan202001051512 \\rT210520151936 \",\"valueDate\":\"2020-05-21 00:00:00.000\",\"txnCcy\":\"USD\",\"txnAmount\":\"100.0000\"},{\"drCrInd\":\"D\",\"txnCode\":\"TRF\",\"txnDesc\": \"FPS FEE for Dan202001051512 \\rT210520151852 \",\"valueDate\":\"2020-05-21 00:00:00.000\",\"txnCcy\":\"USD\",\"txnAmount\":\"100.0000\"},{\"drCrInd\":\"D\",\"txnCode\":\"TRF\",\"txnDesc\": \"FPS FEE for Dan202001051512 \\rT210520151804 \",\"valueDate\":\"2020-05-21 00:00:00.000\",\"txnCcy\":\"USD\",\"txnAmount\":\"100.0000\"},{\"drCrInd\":\"D\",\"txnCode\":\"TRF\",\"txnDesc\": \"TRANSFER\\rCHGS T210520151717 EBHVT91105898309 EBHVT91105898309 DBS-IDEALPAYE\\rDBS-IDEALPAYEEWITHENDINGSPACE      \\rHKD 100  \\rUETR Ref:01ca35bb-3ff2-448a-9693-bf0b15c8cffc\",\"txnDate\":\"2020-05-21 00:00:00.000\",\"valueDate\":\"2020-05-21 00:00:00.000\",\"txnCcy\":\"USD\",\"txnAmount\":\"100.0000\"},{\"drCrInd\":\"D\",\"txnCode\":\"TRF\",\"txnDesc\": \"TRANSFER\\rCHGS T210520151619 EBHVT91105898309 EBHVT91105898309 DBS-IDEALPAYE\\rDBS-IDEALPAYEEWITHENDINGSPACE      \\rHKD 100  \\rUETR Ref:01ca35bb-3ff2-448a-9693-bf0b15c8cffc\",\"txnDate\":\"2020-05-21 00:00:00.000\",\"valueDate\":\"2020-05-21 00:00:00.000\",\"txnCcy\":\"USD\",\"txnAmount\":\"100.0000\"},{\"drCrInd\":\"D\",\"txnCode\":\"TRF\",\"txnDesc\": \"TRANSFER\\rCHGS T210520151522 EBHVT91105898309 EBHVT91105898309 DBS-IDEALPAYE\\rDBS-IDEALPAYEEWITHENDINGSPACE      \\rHKD 100  \\rUETR Ref:01ca35bb-3ff2-448a-9693-bf0b15c8cffc\",\"txnDate\":\"2020-05-21 00:00:00.000\",\"valueDate\":\"2020-05-21 00:00:00.000\",\"txnCcy\":\"USD\",\"txnAmount\":\"100.0000\"},{\"drCrInd\":\"D\",\"txnCode\":\"TRF\",\"txnDesc\": \"FPS FEE for Dan202001051512 \\rT210520151440 \",\"valueDate\":\"2020-05-21 00:00:00.000\",\"txnCcy\":\"USD\",\"txnAmount\":\"100.0000\"},{\"drCrInd\":\"D\",\"txnCode\":\"TRF\",\"txnDesc\": \"FPS FEE for Dan202001051512 \\rT210520151249 \",\"valueDate\":\"2020-05-21 00:00:00.000\",\"txnCcy\":\"USD\",\"txnAmount\":\"100.0000\"},{\"drCrInd\":\"D\",\"txnCode\":\"TRF\",\"txnDesc\": \"FPS FEE for Dan202001051512 \\rT210520151150 \",\"valueDate\":\"2020-05-21 00:00:00.000\",\"txnCcy\":\"USD\",\"txnAmount\":\"100.0000\"},{\"drCrInd\":\"D\",\"txnCode\":\"TRF\",\"txnDesc\": \"FPS FEE for Dan202001051512 \\rT210520150946 \",\"valueDate\":\"2020-05-21 00:00:00.000\",\"txnCcy\":\"USD\",\"txnAmount\":\"100.0000\"},{\"drCrInd\":\"D\",\"txnCode\":\"TRF\",\"txnDesc\": \"TRANSFER\\rCHGS T210520150622 EBHVT91105898309 EBHVT91105898309 DBS-IDEALPAYE\\rDBS-IDEALPAYEEWITHENDINGSPACE      \\rHKD 100  \\rUETR Ref:01ca35bb-3ff2-448a-9693-bf0b15c8cffc\",\"txnDate\":\"2020-05-21 00:00:00.000\",\"valueDate\":\"2020-05-21 00:00:00.000\",\"txnCcy\":\"USD\",\"txnAmount\":\"100.0000\"},{\"drCrInd\":\"D\",\"txnCode\":\"TRF\",\"txnDesc\": \"TRANSFER\\rCHGS T210520150516 EBHVT91105898309 EBHVT91105898309 DBS-IDEALPAYE\\rDBS-IDEALPAYEEWITHENDINGSPACE      \\rHKD 100  \\rUETR Ref:01ca35bb-3ff2-448a-9693-bf0b15c8cffc\",\"txnDate\":\"2020-05-21 00:00:00.000\",\"valueDate\":\"2020-05-21 00:00:00.000\",\"txnCcy\":\"USD\",\"txnAmount\":\"20.0000\"},{\"drCrInd\":\"D\",\"txnCode\":\"TRF\",\"txnDesc\": \"FPS FEE for Dan202001051512 \\rT210520150425 \",\"valueDate\":\"2020-05-21 00:00:00.000\",\"txnCcy\":\"USD\",\"txnAmount\":\"100.0000\"},{\"drCrInd\":\"D\",\"txnCode\":\"TRF\",\"txnDesc\": \"FPS FEE for Dan202001051512 \\rT210520150320 \",\"valueDate\":\"2020-05-21 00:00:00.000\",\"txnCcy\":\"USD\",\"txnAmount\":\"100.0000\"},{\"drCrInd\":\"D\",\"txnCode\":\"TRF\",\"txnDesc\": \"FPS FEE for Dan202001051512 \\rT210520150204 \",\"valueDate\":\"2020-05-21 00:00:00.000\",\"txnCcy\":\"USD\",\"txnAmount\":\"20.0000\"},{\"drCrInd\":\"D\",\"txnCode\":\"TRF\",\"txnDesc\": \"FPS FEE for Dan202001051512 \\rT210520150109 \",\"valueDate\":\"2020-05-21 00:00:00.000\",\"txnCcy\":\"USD\",\"txnAmount\":\"100.0000\"},{\"drCrInd\":\"D\",\"txnCode\":\"TRF\",\"txnDesc\": \"FPS FEE for Dan202001051512 \\rT210520150006 \",\"valueDate\":\"2020-05-21 00:00:00.000\",\"txnCcy\":\"USD\",\"txnAmount\":\"100.0000\"},{\"drCrInd\":\"D\",\"txnCode\":\"TRF\",\"txnDesc\": \"FPS FEE for Dan202001051512 \\rT210520142943 \",\"valueDate\":\"2020-05-21 00:00:00.000\",\"txnCcy\":\"USD\",\"txnAmount\":\"100.0000\"},{\"drCrInd\":\"D\",\"txnCode\":\"TRF\",\"txnDesc\": \"FPS FEE for Dan202001051512 \\rT210520141002 \",\"valueDate\":\"2020-05-21 00:00:00.000\",\"txnCcy\":\"USD\",\"txnAmount\":\"100.0000\"},{\"drCrInd\":\"D\",\"txnCode\":\"TRF\",\"txnDesc\": \"FPS FEE for Dan202001051512 \\rT210520151335LX \",\"valueDate\":\"2020-05-21 00:00:00.000\",\"txnCcy\":\"USD\",\"txnAmount\":\"100.0000\"},{\"drCrInd\":\"D\",\"txnCode\":\"TRF\",\"txnDesc\": \"FPS FEE for Dan202001051512 \\rT210520143155LX \",\"valueDate\":\"2020-05-21 00:00:00.000\",\"txnCcy\":\"USD\",\"txnAmount\":\"20.0000\"},{\"drCrInd\":\"D\",\"txnCode\":\"TRF\",\"txnDesc\": \"FPS FEE for Dan202001051512 \\rT210520151655LX \",\"valueDate\":\"2020-05-21 00:00:00.000\",\"txnCcy\":\"USD\",\"txnAmount\":\"100.0000\"},{\"drCrInd\":\"D\",\"txnCode\":\"TRF\",\"txnDesc\": \"FPS FEE for Dan202001051512 \\rT210520150233LX \",\"valueDate\":\"2020-05-21 00:00:00.000\",\"txnCcy\":\"USD\",\"txnAmount\":\"20.0000\"}]}]}]}}";

        } catch (Exception e) {
            log.error("请求DBS ARE查询异常：", e);
            return R.fail(e.getMessage());

        }

        return R.data(decResponse);
    }

    /**
     * edda enquiry 授权查看请求
     */
    @Override
    public R<String> sendEDDAEnquiry(EddaTransactionEnquiryRequestVO eddaTransaction) {
        R responseVO = new R();
        String decResponse = null;
        try {
            String msgId = eddaTransaction.getMsgId();
            String pubStr = DbsReqPackag.eDDAEnquiryReq(msgId, dbsBankConfig.getOrgId(), eddaTransaction);
			log.info("请求DBS sendEDDAEnquiry,请求 pubStr:{}", pubStr);
			String business = "DbsEDDAEnquiry";
            //开始加密明文报文
            String sendStr = DbsCommManageUtils.encrypt(pubStr, business, msgId);
            //开始发送请求,获得响应密文 (调试不同接口需要修改 accountUrl/KeyId)
            String responseMes = DbsCommManageUtils.send(EDDA_ENQUIRY_URL, sendStr, business, msgId);
            if (StringUtils.isEmpty(responseMes)) {
                log.info("请求DBS eDDA授权结果查询，返回报文为空, msgId:{}", eddaTransaction.getMsgId());
                responseVO.setCode(BankRespCodeEnum.ErrorCodeType.CONNECT_TIMEOUT.getCode());
                responseVO.setMsg(BankRespCodeEnum.ErrorCodeType.CONNECT_TIMEOUT.getMessage());
            } else {
                decResponse = DbsCommManageUtils.decrypt(responseMes, business, msgId);
                if (StringUtils.isEmpty(decResponse)) {
                    responseVO.setCode(BankRespCodeEnum.ErrorCodeType.ERROR_DECRYPT.getCode());
                    responseVO.setMsg(BankRespCodeEnum.ErrorCodeType.ERROR_DECRYPT.getMessage());
                } else {

                    //responseVO.setData(decResponse);
					return  R.data(decResponse);
                }
            }

            // 测试
//            decResponse = " {\"header\":{\"msgId\":\"2021020211218100032\",\"orgId\":\"HKGYSSL\",\"timeStamp\":\"2021-02-02T12:04:50.194\",\"ctry\":\"HK\"},\"enqResponse\":{\"enqStatus\":\"ACTC\",\"txnRefId\":\"HKENQ21021000000001\",\"txnStatus\":\"ACTC\",\"mandateId\":\"016/MNDTEDDA21020200000001\",\"ddaRef\":\"EDDA202102021121810002\",\"mandateType\":\"DDMP\",\"creditor\":{\"name\":\"Guo Sen\",\"accountNo\":\"001242462\",\"accountCcy\":\"HKD\"},\"debtor\":{\"name\":\"LEETSZ HANG\",\"proxyType\":\"B\",\"proxyValue\":\"001242453\",\"bankId\":\"016\"}}}";
//
//            responseVO.setData(decResponse);

        } catch (Exception e) {
            log.error("请求DBS eDDA查询异常, msgId:{}", eddaTransaction.getMsgId(), e);
            return R.fail(e.getMessage());
        }
        return responseVO;
    }

    /**
     * Tse 查询汇款交易状态
     */
    @Override
    public R<String> sendTseEnquiry(TseTransactionEnquiryRequestVO requestVO) {
        R responseVO = new R();
        String decResponse = null;
        try {
            String msgId = requestVO.getMsgId();
            String pubStr = DbsReqPackag.tseEnquiryReq(msgId, dbsBankConfig.getOrgId(), requestVO);
            String business = "DbsTSEEnquiry";
            //开始加密明文报文
            String sendStr = DbsCommManageUtils.encrypt(pubStr, business, msgId);
            //开始发送请求,获得响应密文 (调试不同接口需要修改 accountUrl/KeyId)
            String responseMes = DbsCommManageUtils.send(HK_TSE_URL, sendStr, business, msgId);
            if (StringUtils.isEmpty(responseMes)) {
                log.info("请求DBS TSE查询，返回报文为空, msgId:{}", requestVO.getMsgId());
                responseVO.setCode(BankRespCodeEnum.ErrorCodeType.CONNECT_TIMEOUT.getCode());
                responseVO.setMsg(BankRespCodeEnum.ErrorCodeType.CONNECT_TIMEOUT.getMessage());
            } else {
                decResponse = DbsCommManageUtils.decrypt(responseMes, business, msgId);
                if (StringUtils.isEmpty(decResponse)) {
                    responseVO.setCode(BankRespCodeEnum.ErrorCodeType.ERROR_DECRYPT.getCode());
                    responseVO.setMsg(BankRespCodeEnum.ErrorCodeType.ERROR_DECRYPT.getMessage());
                } else {

                    responseVO.setData(decResponse);
                }
            }
        } catch (Exception e) {
            log.error("请求DBS TSE查询异常, msgId:{}", requestVO.getMsgId(), e);
            return R.fail(e.getMessage());
        }
        return responseVO;
    }


    /** #######################################################*/
    /** ####################  DBS API 报表 #################*/
    /** #######################################################*/

    /**
     * Camt53 日报文查看请求
     */
    @Override
    public R<String> sendCamt053(Camt053ReportRequestVO reportRequestVO) {
        R<String> responseVO = new R<String>();
        String decResponse = null;
        try {

            log.info("请求DBS Camt53报表，数据原文：{}", JSON.toJSONString(reportRequestVO));
            String msgId = reportRequestVO.getMsgId();
            String pubStr = DbsReqPackag.hkCamt53Xml(msgId, dbsBankConfig.getOrgId(), reportRequestVO);
            String business = "DbsCamt53";
            //开始加密明文报文
            String sendStr = DbsCommManageUtils.encrypt(pubStr, business, msgId);
            //开始发送请求,获得响应密文 (调试不同接口需要修改 accountUrl/KeyId)
            String responseMes = DbsCommManageUtils.send(HK_DAY_REPORT, sendStr, business, msgId);
            if (StringUtils.isEmpty(responseMes)) {
                log.info("请求DBS Camt53报表，返回报文为空, msgId:{}", reportRequestVO.getMsgId());

                responseVO.setCode(BankRespCodeEnum.ErrorCodeType.CONNECT_TIMEOUT.getCode());
                responseVO.setMsg(BankRespCodeEnum.ErrorCodeType.CONNECT_TIMEOUT.getMessage());
            } else {
                decResponse = DbsCommManageUtils.decrypt(responseMes, business, msgId);
                if (StringUtils.isEmpty(decResponse)) {
                    log.info("请求DBS Camt53报表，报文验签解密异常, msgId:{}", reportRequestVO.getMsgId());
                    responseVO.setCode(BankRespCodeEnum.ErrorCodeType.ERROR_DECRYPT.getCode());
                    responseVO.setMsg(BankRespCodeEnum.ErrorCodeType.ERROR_DECRYPT.getMessage());
                } else {

                    responseVO.setData(decResponse);
                }
            }

            // 测试
//            String dataXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Document>  <Header>    <MsgId>CAMT20210630</MsgId>    <OrgId>HKGUOS01</OrgId>    <TimeStamp>2021-05-31T10:12:40.945</TimeStamp>    <Ctry>HK</Ctry>  </Header>  <TxnEnqResponse>    <EnqStatus>ACSP</EnqStatus>    <EnqRejectCode/>    <EnqStatusDescription/>    <AcctInfo>      <AccountNo>001242462</AccountNo>      <AccountCcy>HKD</AccountCcy>    </AcctInfo>    <BizDate>20210528</BizDate>    <MessageType>CAMT053XML</MessageType>    <Statements>      <Statement Sequence=\"00001\"><![CDATA[<?xml version=\"1.0\" encoding=\"UTF-8\"?><Document xmlns=\"urn:iso:std:iso:20022:tech:xsd:camt.053.001.02\"><BkToCstmrStmt><GrpHdr><MsgId>001242462HKD20210303</MsgId><CreDtTm>2021-03-03T08:13:38+08:00</CreDtTm></GrpHdr><Stmt><Id>00027</Id><CreDtTm>2021-03-03T08:13:38+08:00</CreDtTm><Acct><Id><Othr><Id>001242462</Id></Othr></Id><Ccy>HKD</Ccy><Nm>C DBS UAT SG5TESTING</Nm><Svcr><FinInstnId><BIC>DBSSSGS0XXX</BIC></FinInstnId></Svcr></Acct><Bal><Tp><CdOrPrtry><Cd>OPBD</Cd></CdOrPrtry></Tp><Amt Ccy=\"HKD\">73891798.26</Amt><CdtDbtInd>CRDT</CdtDbtInd><Dt><Dt>2021-05-28</Dt></Dt></Bal><Bal><Tp><CdOrPrtry><Cd>CLAV</Cd></CdOrPrtry></Tp><Amt Ccy=\"HKD\">74162142.71</Amt><CdtDbtInd>CRDT</CdtDbtInd><Dt><Dt>2021-05-28</Dt></Dt></Bal><Bal><Tp><CdOrPrtry><Cd>CLBD</Cd></CdOrPrtry></Tp><Amt Ccy=\"HKD\">74162142.71</Amt><CdtDbtInd>CRDT</CdtDbtInd><Dt><Dt>2021-05-28</Dt></Dt></Bal><TxsSummry><TtlNtries><NbOfNtries>12</NbOfNtries><Sum>276344.45</Sum><TtlNetNtryAmt>270344.45</TtlNetNtryAmt><CdtDbtInd>CRDT</CdtDbtInd></TtlNtries></TxsSummry><Ntry><NtryRef>0001074359000700000000009462848</NtryRef><Amt Ccy=\"HKD\">3000.00</Amt><CdtDbtInd>DBIT</CdtDbtInd><Sts>BOOK</Sts><BookgDt><DtTm>2021-05-28T04:40:29+08:00</DtTm></BookgDt><ValDt><Dt>2021-05-28</Dt></ValDt><AcctSvcrRef>NONREF</AcctSvcrRef><BkTxCd><Prtry><Cd>452</Cd></Prtry></BkTxCd><NtryDtls><TxDtls><Refs><EndToEndId>EBLVT01106119073</EndToEndId></Refs><RltdPties><Dbtr><Nm>HARBOURFRONT CENTRE PTE. LTD.</Nm><PstlAdr><Ctry>SG</Ctry></PstlAdr><Id><OrgId><Othr><Id>HKGUOS01XXXX</Id></Othr></OrgId></Id></Dbtr></RltdPties></TxDtls></NtryDtls><AddtlNtryInf>TD?INCMG GIRO DR?ORDP?16/11/2020 90001?OTH3?EBLVT01106119073</AddtlNtryInf></Ntry><Ntry><NtryRef>0001074359000700000000003068757</NtryRef><Amt Ccy=\"HKD\">279.60</Amt><CdtDbtInd>CRDT</CdtDbtInd><Sts>BOOK</Sts><BookgDt><DtTm>2021-05-28T05:10:48+08:00</DtTm></BookgDt><ValDt><Dt>2021-05-28</Dt></ValDt><AcctSvcrRef>0101</AcctSvcrRef><BkTxCd><Prtry><Cd>120</Cd></Prtry></BkTxCd><NtryDtls><TxDtls><Refs><EndToEndId>EPS</EndToEndId></Refs><RltdPties><Dbtr><Nm>NONREF</Nm></Dbtr><Cdtr><Nm>BOUGAINVILLEA       REALTY PTE LTD</Nm><PstlAdr><Ctry>SG</Ctry></PstlAdr><Id><OrgId><Othr><Id>HKGUOS01XXXX</Id></Othr></OrgId></Id></Cdtr></RltdPties></TxDtls></NtryDtls><AddtlNtryInf>TD?POINT OF SALE?OTH2?EPS</AddtlNtryInf></Ntry><Ntry><NtryRef>0001074359000700000000004082535</NtryRef><Amt Ccy=\"HKD\">594.30</Amt><CdtDbtInd>CRDT</CdtDbtInd><Sts>BOOK</Sts><BookgDt><DtTm>2021-05-28T05:10:48+08:00</DtTm></BookgDt><ValDt><Dt>2021-05-28</Dt></ValDt><AcctSvcrRef>0101</AcctSvcrRef><BkTxCd><Prtry><Cd>120</Cd></Prtry></BkTxCd><NtryDtls><TxDtls><Refs><EndToEndId>EPS</EndToEndId></Refs><RltdPties><Dbtr><Nm>NONREF</Nm></Dbtr><Cdtr><Nm>BOUGAINVILLEA       REALTY PTE LTD</Nm><PstlAdr><Ctry>SG</Ctry></PstlAdr><Id><OrgId><Othr><Id>HKGUOS01XXXX</Id></Othr></OrgId></Id></Cdtr></RltdPties></TxDtls></NtryDtls><AddtlNtryInf>TD?POINT OF SALE?OTH2?EPS</AddtlNtryInf></Ntry><Ntry><NtryRef>0001074359000700000000002301268</NtryRef><Amt Ccy=\"HKD\">10.50</Amt><CdtDbtInd>CRDT</CdtDbtInd><Sts>BOOK</Sts><BookgDt><DtTm>2021-05-28T05:10:59+08:00</DtTm></BookgDt><ValDt><Dt>2021-05-28</Dt></ValDt><AcctSvcrRef>7564</AcctSvcrRef><BkTxCd><Prtry><Cd>120</Cd></Prtry></BkTxCd><NtryDtls><TxDtls><Refs><EndToEndId>BOUGAINVIL</EndToEndId></Refs><RltdPties><Dbtr><Nm>NONREF</Nm></Dbtr><Cdtr><Nm>BOUGAINVILLEA       REALTY PTE LTD</Nm><PstlAdr><Ctry>SG</Ctry></PstlAdr><Id><OrgId><Othr><Id>HKGUOS01XXXX</Id></Othr></OrgId></Id></Cdtr></RltdPties></TxDtls></NtryDtls><AddtlNtryInf>TD?POINT OF SALE?OTH2?BOUGAINVIL</AddtlNtryInf></Ntry><Ntry><NtryRef>0001074359000700000000004885500</NtryRef><Amt Ccy=\"HKD\">293.80</Amt><CdtDbtInd>CRDT</CdtDbtInd><Sts>BOOK</Sts><BookgDt><DtTm>2021-05-28T05:11:01+08:00</DtTm></BookgDt><ValDt><Dt>2021-05-28</Dt></ValDt><AcctSvcrRef>0101</AcctSvcrRef><BkTxCd><Prtry><Cd>120</Cd></Prtry></BkTxCd><NtryDtls><TxDtls><Refs><EndToEndId>EPS</EndToEndId></Refs><RltdPties><Dbtr><Nm>NONREF</Nm></Dbtr><Cdtr><Nm>BOUGAINVILLEA       REALTY PTE LTD</Nm><PstlAdr><Ctry>SG</Ctry></PstlAdr><Id><OrgId><Othr><Id>HKGUOS01XXXX</Id></Othr></OrgId></Id></Cdtr></RltdPties></TxDtls></NtryDtls><AddtlNtryInf>TD?POINT OF SALE?OTH2?EPS</AddtlNtryInf></Ntry><Ntry><NtryRef>0001074359000700000000004342319</NtryRef><Amt Ccy=\"HKD\">329.00</Amt><CdtDbtInd>CRDT</CdtDbtInd><Sts>BOOK</Sts><BookgDt><DtTm>2021-05-28T05:11:11+08:00</DtTm></BookgDt><ValDt><Dt>2021-05-28</Dt></ValDt><AcctSvcrRef>0101</AcctSvcrRef><BkTxCd><Prtry><Cd>120</Cd></Prtry></BkTxCd><NtryDtls><TxDtls><Refs><EndToEndId>EPS</EndToEndId></Refs><RltdPties><Dbtr><Nm>NONREF</Nm></Dbtr><Cdtr><Nm>BOUGAINVILLEA       REALTY PTE LTD</Nm><PstlAdr><Ctry>SG</Ctry></PstlAdr><Id><OrgId><Othr><Id>HKGUOS01XXXX</Id></Othr></OrgId></Id></Cdtr></RltdPties></TxDtls></NtryDtls><AddtlNtryInf>TD?POINT OF SALE?OTH2?EPS</AddtlNtryInf></Ntry><Ntry><NtryRef>0001074359000700000000009716713</NtryRef><Amt Ccy=\"HKD\">77.04</Amt><CdtDbtInd>CRDT</CdtDbtInd><Sts>BOOK</Sts><BookgDt><DtTm>2021-05-28T15:42:41+08:00</DtTm></BookgDt><ValDt><Dt>2021-05-28</Dt></ValDt><AcctSvcrRef>NONREF</AcctSvcrRef><BkTxCd><Prtry><Cd>175</Cd></Prtry></BkTxCd><NtryDtls><TxDtls><Refs><EndToEndId>NONREF</EndToEndId></Refs><RltdPties><Dbtr><Nm>NONREF</Nm></Dbtr><Cdtr><Nm>BOUGAINVILLEA       REALTY PTE LTD</Nm><PstlAdr><Ctry>SG</Ctry></PstlAdr><Id><OrgId><Othr><Id>HKGUOS01XXXX</Id></Othr></OrgId></Id></Cdtr></RltdPties></TxDtls></NtryDtls><AddtlNtryInf>TD?CHEQUE DEPOSIT</AddtlNtryInf></Ntry><Ntry><NtryRef>0001074359000700000000009356352</NtryRef><Amt Ccy=\"HKD\">31139.30</Amt><CdtDbtInd>CRDT</CdtDbtInd><Sts>BOOK</Sts><BookgDt><DtTm>2021-05-28T15:42:42+08:00</DtTm></BookgDt><ValDt><Dt>2021-05-28</Dt></ValDt><AcctSvcrRef>NONREF</AcctSvcrRef><BkTxCd><Prtry><Cd>175</Cd></Prtry></BkTxCd><NtryDtls><TxDtls><Refs><EndToEndId>NONREF</EndToEndId></Refs><RltdPties><Dbtr><Nm>NONREF</Nm></Dbtr><Cdtr><Nm>BOUGAINVILLEA       REALTY PTE LTD</Nm><PstlAdr><Ctry>SG</Ctry></PstlAdr><Id><OrgId><Othr><Id>HKGUOS01XXXX</Id></Othr></OrgId></Id></Cdtr></RltdPties></TxDtls></NtryDtls><AddtlNtryInf>TD?CHEQUE DEPOSIT</AddtlNtryInf></Ntry><Ntry><NtryRef>2021030200988133</NtryRef><Amt Ccy=\"HKD\">13677.33</Amt><CdtDbtInd>CRDT</CdtDbtInd><Sts>BOOK</Sts><BookgDt><DtTm>2021-05-28T23:59:59+08:00</DtTm></BookgDt><ValDt><Dt>2021-05-28</Dt></ValDt><AcctSvcrRef>NONREF</AcctSvcrRef><BkTxCd><Prtry><Cd>142</Cd></Prtry></BkTxCd><NtryDtls><TxDtls><Refs><EndToEndId>5003763893</EndToEndId></Refs><RltdPties><Dbtr><Nm>NONREF</Nm></Dbtr><Cdtr><Nm>HARBOURFRONT CENTRE PTE. LTD.</Nm><PstlAdr><Ctry>SG</Ctry></PstlAdr><Id><OrgId><Othr><Id>HKGUOS01XXXX</Id></Othr></OrgId></Id></Cdtr></RltdPties></TxDtls></NtryDtls><AddtlNtryInf>TD?INCMG GIRO CR?ORDP?ACCOUNTANT-GENERAL?OTH1?IVPT?OTH3?5003763893</AddtlNtryInf></Ntry><Ntry><NtryRef>2021030200169844</NtryRef><Amt Ccy=\"HKD\">37155.20</Amt><CdtDbtInd>CRDT</CdtDbtInd><Sts>BOOK</Sts><BookgDt><DtTm>2021-05-28T23:59:59+08:00</DtTm></BookgDt><ValDt><Dt>2021-05-28</Dt></ValDt><AcctSvcrRef>NONREF</AcctSvcrRef><BkTxCd><Prtry><Cd>142</Cd></Prtry></BkTxCd><NtryDtls><TxDtls><Refs><EndToEndId>NOV 2020 VENDOR PAYMENT</EndToEndId></Refs><RltdPties><Dbtr><Nm>NONREF</Nm></Dbtr><Cdtr><Nm>HARBOURFRONT CENTRE PTE. LTD.</Nm><PstlAdr><Ctry>SG</Ctry></PstlAdr><Id><OrgId><Othr><Id>HKGUOS01XXXX</Id></Othr></OrgId></Id></Cdtr></RltdPties></TxDtls></NtryDtls><AddtlNtryInf>TD?INCMG GIRO CR?ORDP?NIHON KOHDEN SINGAPORE PTE LTD?OTH1?IVPT?OTH3?NOV 2020 VENDOR PAYMENT</AddtlNtryInf></Ntry><Ntry><NtryRef>2021030200762614</NtryRef><Amt Ccy=\"HKD\">75515.52</Amt><CdtDbtInd>CRDT</CdtDbtInd><Sts>BOOK</Sts><BookgDt><DtTm>2021-05-28T23:59:59+08:00</DtTm></BookgDt><ValDt><Dt>2021-05-28</Dt></ValDt><AcctSvcrRef>NONREF</AcctSvcrRef><BkTxCd><Prtry><Cd>142</Cd></Prtry></BkTxCd><NtryDtls><TxDtls><Refs><EndToEndId>300003890</EndToEndId></Refs><RltdPties><Dbtr><Nm>NONREF</Nm></Dbtr><Cdtr><Nm>VISTA REAL ESTATE   INVESTMENTS PTE LTD</Nm><PstlAdr><Ctry>SG</Ctry></PstlAdr><Id><OrgId><Othr><Id>HKGUOS01XXXX</Id></Othr></OrgId></Id></Cdtr></RltdPties></TxDtls></NtryDtls><AddtlNtryInf>TD?INCMG GIRO CR?ORDP?MARITIME AND PORT AUTHORITY OF S'PO?OTH1?OTHR?OTH3?300003890</AddtlNtryInf></Ntry><Ntry><NtryRef>2021030200305351</NtryRef><Amt Ccy=\"HKD\">114272.86</Amt><CdtDbtInd>CRDT</CdtDbtInd><Sts>BOOK</Sts><BookgDt><DtTm>2021-05-28T23:59:59+08:00</DtTm></BookgDt><ValDt><Dt>2021-05-28</Dt></ValDt><AcctSvcrRef>NONREF</AcctSvcrRef><BkTxCd><Prtry><Cd>142</Cd></Prtry></BkTxCd><NtryDtls><TxDtls><Refs><EndToEndId>SGGP201110028010</EndToEndId></Refs><RltdPties><Dbtr><Nm>NONREF</Nm></Dbtr><Cdtr><Nm>HARBOURFRONT CENTRE PTE. LTD.</Nm><PstlAdr><Ctry>SG</Ctry></PstlAdr><Id><OrgId><Othr><Id>HKGUOS01XXXX</Id></Othr></OrgId></Id></Cdtr></RltdPties></TxDtls></NtryDtls><AddtlNtryInf>TD?INCMG GIRO CR?ORDP?FOOD JUNCTION MANAGEMENT PTE LTD?OTH1?OTHR?OTH3?SGGP201110028010</AddtlNtryInf></Ntry></Stmt></BkToCstmrStmt></Document>]]></Statement>    </Statements>  </TxnEnqResponse></Document>";
//
//            responseVO.setData(dataXml);

        } catch (Exception e) {
            log.error("请求DBS Camt53报表异常, msgId:{}", reportRequestVO.getMsgId(), e);
            return R.fail(e.getMessage());
        }
        return responseVO;
    }

    /**
     * Camt052 小时报文查看请求
     */
    @Override
    public R<String> sendCamt052(Camt052ReportRequestVO reportRequestVO) {
        R responseVO = new R();
        String decResponse = null;
        try {

            log.info("请求DBS Camt52报表，数据原文：{}", JSON.toJSONString(reportRequestVO));
            String msgId = reportRequestVO.getMsgId();
            String pubStr = DbsReqPackag.hkCamt52Xml(msgId, dbsBankConfig.getOrgId(), reportRequestVO);
            String business = "DbsCamt52";
            //开始加密明文报文
            String sendStr = DbsCommManageUtils.encrypt(pubStr, business, msgId);
            //开始发送请求,获得响应密文 (调试不同接口需要修改 accountUrl/KeyId)
            String responseMes = DbsCommManageUtils.send(HK_HOUR_REPORT, sendStr, business, msgId);
            if (StringUtils.isEmpty(responseMes)) {
                log.info("请求DBS Camt52报表，返回报文为空, msgId:{}", reportRequestVO.getMsgId());
                responseVO.setCode(BankRespCodeEnum.ErrorCodeType.CONNECT_TIMEOUT.getCode());
                responseVO.setMsg(BankRespCodeEnum.ErrorCodeType.CONNECT_TIMEOUT.getMessage());
            } else {
                decResponse = DbsCommManageUtils.decrypt(responseMes, business, msgId);
                if (StringUtils.isEmpty(decResponse)) {
                    log.info("请求DBS Camt52报表，报文验签解密异常, msgId:{}", reportRequestVO.getMsgId());
                    responseVO.setCode(BankRespCodeEnum.ErrorCodeType.ERROR_DECRYPT.getCode());
                    responseVO.setMsg(BankRespCodeEnum.ErrorCodeType.ERROR_DECRYPT.getMessage());
                } else {

                    responseVO.setData(decResponse);
                }
            }

//            String dataXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Document>  <Header>    <MsgId>CAMT202106303</MsgId>    <OrgId>HKGUOS01</OrgId>    <TimeStamp>2021-06-10T10:17:35.509</TimeStamp>    <Ctry>HK</Ctry>  </Header>  <TxnEnqResponse>    <EnqStatus>ACSP</EnqStatus>    <EnqRejectCode/>    <EnqStatusDescription/>    <AcctInfo>      <AccountNo>001242462</AccountNo>      <AccountCcy>HKD</AccountCcy>    </AcctInfo>    <MessageType>CAMT052XML</MessageType>    <Statements>      <Statement Sequence=\"00001\"><![CDATA[<?xml version=\"1.0\" encoding=\"UTF-8\"?><Document xmlns=\"urn:iso:std:iso:20022:tech:xsd:camt.052.001.02\">  <BkToCstmrAcctRpt>    <GrpHdr>      <MHKId>0039011288HKD20201109120503990</MHKId>      <CreDtTm>2020-11-09T12:05:03+08:00</CreDtTm>    </GrpHdr>    <Rpt>      <Id>00002</Id>      <CreDtTm>2020-11-09T12:05:03+08:00</CreDtTm>      <Acct>        <Id>          <Othr>            <Id>001242462</Id>          </Othr>        </Id>        <Ccy>HKD</Ccy>        <Nm>UAT COMPANY2 LIMITED</Nm>        <Svcr>          <FinInstnId>            <BIC>DBSSHKHKXXX</BIC>          </FinInstnId>        </Svcr>      </Acct>      <Bal>        <Tp>          <CdOrPrtry>            <Cd>OPBD</Cd>          </CdOrPrtry>        </Tp>        <Amt Ccy=\"HKD\">16641055.00</Amt>        <CdtDbtInd>CRDT</CdtDbtInd>        <Dt>          <Dt>2021-06-10</Dt>        </Dt>      </Bal>      <Bal>        <Tp>          <CdOrPrtry>            <Cd>CLAV</Cd>          </CdOrPrtry>        </Tp>        <Amt Ccy=\"HKD\">61579302.90</Amt>        <CdtDbtInd>CRDT</CdtDbtInd>        <Dt>          <Dt>2021-06-10</Dt>        </Dt>      </Bal>      <Bal>        <Tp>          <CdOrPrtry>            <Cd>CLBD</Cd>          </CdOrPrtry>        </Tp>        <Amt Ccy=\"HKD\">61579302.90</Amt>        <CdtDbtInd>CRDT</CdtDbtInd>        <Dt>          <Dt>2021-06-10</Dt>        </Dt>      </Bal>      <TxsSummry>        <TtlNtries>          <NbOfNtries>1</NbOfNtries>          <Sum>44938247.90</Sum>          <TtlNetNtryAmt>44938247.90</TtlNetNtryAmt>          <CdtDbtInd>CRDT</CdtDbtInd>        </TtlNtries>      </TxsSummry>      <Ntry>\\t  <NtryRef>0001800130000400419734538144100</NtryRef>        <Amt Ccy=\"HKD\">44938247.90</Amt>        <CdtDbtInd>CRDT</CdtDbtInd>        <Sts>BOOK</Sts>        <BookgDt>          <DtTm>2020-11-09T11:09:04+08:00</DtTm>        </BookgDt>        <ValDt>          <Dt>2021-06-10</Dt>        </ValDt>        <AcctSvcrRef>0016II1715145</AcctSvcrRef>        <BkTxCd>          <Prtry>            <Cd>195</Cd>          </Prtry>        </BkTxCd>        <NtryDtls>          <TxDtls>            <Refs>              <EndToEndId>1SX0000015981763</EndToEndId>            </Refs>            <RltdPties>              <Dbtr>                <Nm>UAT COMPANY2 LTD    </Nm>              </Dbtr>              <DbtrAcct>                <Id>                  <Othr>                    <Id>0000000001300106950</Id>                  </Othr>                </Id>              </DbtrAcct>              <Cdtr>                <Nm>UAT COMPANY2 LIMITED</Nm>                <PstlAdr>                  <Ctry>HK</Ctry>                </PstlAdr>                <Id>                  <OrgId>                    <Othr>                      <Id>HKGUOS01XXXX</Id>                    </Othr>                  </OrgId>                </Id>              </Cdtr>            </RltdPties>          </TxDtls>        </NtryDtls>        <AddtlNtryInf>TD?INCOMING MEPS?ORDP?UAT COMPANY2 LTD    10 PASIR PANJANG ROAD 13 01 MAPLETREE BUSINESS CITY SINGAPORE 117438?RA?SGD44938247,90?OB?UNITED OVERSEAS BANK LIMITED - SINGAPORE -?BC?UOVBHKHKXXX</AddtlNtryInf>      </Ntry>    </Rpt>  </BkToCstmrAcctRpt></Document>]]></Statement>    </Statements>  </TxnEnqResponse></Document>";
//
//            responseVO.setData(dataXml);

        } catch (Exception e) {
            log.error("请求DBS Camt52报表异常, msgId:{}", reportRequestVO.getMsgId(), e);
            return R.fail(e.getMessage());

        }
        return responseVO;
    }

}
