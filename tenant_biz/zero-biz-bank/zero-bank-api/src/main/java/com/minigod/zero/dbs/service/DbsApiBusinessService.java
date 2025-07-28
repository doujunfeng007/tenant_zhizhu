package com.minigod.zero.dbs.service;

import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.dbs.bo.DbsIccBankFlowEntity;
import com.minigod.zero.dbs.bo.FundTransferEntity;
import com.minigod.zero.dbs.vo.*;

/**
 * DBS代理接口定义
 */
public interface DbsApiBusinessService {
    String BLE = "/dbs/ble";
    String EDDA_SETUP = "/dbs/eddaSetup";
    String VAC = "/dbs/vac";
    String ICC = "/dbs/icc";
    String FPS_GPC = "/dbs/fpsGpc";
    String FPS_GPP = "/dbs/fpsGpp";
    String FPS_PPP = "/dbs/fpsPpp";
    String ACT = "/dbs/act";
    String RFD = "/dbs/rfd";
    String CHATS = "/dbs/chats";
    String TT = "/dbs/tt";
    String TSE = "/dbs/tse";
    String EDDA_ENQ = "/dbs/eddaEnq";
    String CAMT53 = "/dbs/camt53";
    String CAMT52 = "/dbs/camt52";
    String FPS_ID_ENQUIRY = "/dbs/FpsIdEnquiry";
    String DECRYPT = "/dbs/decrypt";
    String ENCRYPT = "/dbs/encrypt";

    /**
     * edda initiation 授权请求
     *
     * @param eddaInfo
     * @return
     */
    R<String> sendEDDAInitiation(EddaInfoRequestVO eddaInfo);

    /**
     * FPS GPC 转账
     *
     * @param fpsTransaction
     */
    R<String> sendFpsGpcFund(FpsTransactionRequestVO fpsTransaction);

    /**
     * 银行自动转账(Fps Gpp)
     *
     * @param fundWithdrawApplyEntity
     * @return
     */
    R<String> sendFpsGppFund(FundTransferEntity fundWithdrawApplyEntity);

    /**
     * 银行自动转账(Fps PPP)
     *
     * @param fundWithdrawApplyEntity
     * @return
     */
    R<String> sendFpsPPPFund(FundTransferEntity fundWithdrawApplyEntity);

    /**
     * 银行转账(Dbs Act 同行转账)
     *
     * @param fundWithdrawApplyEntity
     * @return
     */
    R<String> sendActFund(FundTransferEntity fundWithdrawApplyEntity);

    /**
     * 银行转账(Dbs RFD 退款)
     *
     * @param fundWithdrawApplyEntity
     * @return
     */
    R<String> sendRFDFund(FundTransferEntity fundWithdrawApplyEntity);


    /**
     * fpsid查询
     *
     * @param fundWithdrawApplyEntity
     * @return
     */
    R<String> sendFpsIdEnquiryFund(FundTransferEntity fundWithdrawApplyEntity);

    /**
     * 银行转账(Dbs CHATS\RTGS 跨行转账)
     *
     * @param fundWithdrawApplyEntity
     * @return
     */
    R<String> sendChatsFund(FundTransferEntity fundWithdrawApplyEntity);

    /**
     * 银行转账(Dbs TT 海外转账)
     *
     * @param fundWithdrawApplyEntity
     * @return
     */
    R<String> sendTTFund(FundTransferEntity fundWithdrawApplyEntity);


    /**
     * 查询银行账户（BLE）
     * <p>
     * accountNo  银行账号
     * accountCcy 币种
     *
     * @return
     */
    R<String> sendBleQuery(AccountBalanceRequestVO accountBalanceRequestVo);

    /**
     * 子账户增删
     */
    R<String> sendProvisionQuery(SubAccountRequestVO subAccountRequestVO);

    /**
     * ICC流水请求星展 ACCOUNT RANGE ENQUIRY MESSAGE
     */
    R<String> sendICCARE(DbsIccBankFlowEntity dbsIccBankFlowEntity);

    /**
     * edda enquiry 授权查看请求
     */
    R<String> sendEDDAEnquiry(EddaTransactionEnquiryRequestVO eddaTransaction);


    /**
     * Camt53 日报文查看请求
     */
    R<String> sendCamt053(Camt053ReportRequestVO reportRequestVO);

    /**
     * Camt052 小时报文查看请求
     */
    R<String> sendCamt052(Camt052ReportRequestVO reportRequestVO);

    /**
     * Tse 查询汇款交易状态
     */
    R<String> sendTseEnquiry(TseTransactionEnquiryRequestVO requestVO);

}
