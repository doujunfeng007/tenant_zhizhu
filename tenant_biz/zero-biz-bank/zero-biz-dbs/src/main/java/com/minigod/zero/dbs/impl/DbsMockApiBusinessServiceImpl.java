package com.minigod.zero.dbs.impl;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.dbs.bo.DbsIccBankFlowEntity;
import com.minigod.zero.dbs.bo.FundTransferEntity;
import com.minigod.zero.dbs.config.DbsBankConfig;
import com.minigod.zero.dbs.feign.DbsCallbackClient;
import com.minigod.zero.dbs.service.DbsMockApiBusinessService;
import com.minigod.zero.dbs.util.DecimalUtils;
import com.minigod.zero.dbs.vo.*;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @ClassName: DbsMockApiBusinessServiceImpl
 * @Description:
 * @Author chenyu
 * @Date 2024/3/23
 * @Version 1.0
 */
@Slf4j
@Service
@AllArgsConstructor
public class DbsMockApiBusinessServiceImpl implements DbsMockApiBusinessService {

    private final DbsBankConfig dbsBankConfig;
    private final DbsCallbackClient dbsCallbackClient;

    @Override
    public R<String> sendEDDAInitiation(EddaInfoRequestVO eddaInfo) {
        return R.fail();
    }

    @Override
    public R<String> sendFpsGpcFund(FpsTransactionRequestVO fpsTransaction) {
        String date = DateUtil.format(new Date(), "yyyy-MM-dd'T'HH:mm:ss.SSS");
        String day = DateUtil.format(new Date(), "yyyy-MM-dd");
        String msgId = UUID.randomUUID().toString().replace("-", "");
        String customerReference = UUID.randomUUID().toString().replace("-", "");
        String txnRefId = UUID.randomUUID().toString().replace("-", "");
        Map<String, Object> dataMap = new HashMap<>();

        // 创建header子对象的Map
        Map<String, Object> headerMap = new HashMap<>();
        headerMap.put("msgId", "GPC" + msgId);
        headerMap.put("timeStamp", date);

        // 创建txnResponse子对象的Map
        Map<String, Object> txnResponseMap = new HashMap<>();
        txnResponseMap.put("customerReference", "GCP" + customerReference);
        txnResponseMap.put("paymentReference", "");
        txnResponseMap.put("txnRefId", txnRefId);
        txnResponseMap.put("bankReference", txnRefId);
        txnResponseMap.put("txnType", "GPC");
        txnResponseMap.put("txnRejectCode", "");
        txnResponseMap.put("txnStatusDescription", "Success");
        txnResponseMap.put("txnStatus", "ACTC");
        txnResponseMap.put("txnSettlementAmt", DecimalUtils.formatDecimal(DecimalUtils.toDecimal(fpsTransaction.getTxnAmount())));
        txnResponseMap.put("txnSettlementDt", day);

        // 将header和txnResponse添加到dataMap中
        dataMap.put("header", headerMap);
        dataMap.put("txnResponse", txnResponseMap);
        return R.data(JSON.toJSONString(dataMap));
    }

    @Override
    public R<String> sendFpsGppFund(FundTransferEntity fundWithdrawApplyEntity) {
        String date = DateUtil.format(new Date(), "yyyy-MM-dd'T'HH:mm:ss.SSS");
        String day = DateUtil.format(new Date(), "yyyy-MM-dd");
        String msgId = UUID.randomUUID().toString().replace("-", "");
        String customerReference = UUID.randomUUID().toString().replace("-", "");
        String txnRefId = UUID.randomUUID().toString().replace("-", "");
        Map<String, Object> dataMap = new HashMap<>();

        // 创建header子对象的Map
        Map<String, Object> headerMap = new HashMap<>();
        headerMap.put("msgId", "GPP" + msgId);
        headerMap.put("timeStamp", date);

        // 创建txnResponse子对象的Map
        Map<String, Object> txnResponseMap = new HashMap<>();
        txnResponseMap.put("customerReference", "GPP" + customerReference);
        txnResponseMap.put("paymentReference", "");
        txnResponseMap.put("txnRefId", txnRefId);
        txnResponseMap.put("bankReference", txnRefId);
        txnResponseMap.put("txnType", "GPP");
        txnResponseMap.put("txnRejectCode", "");
        txnResponseMap.put("txnStatusDescription", "Success");
        txnResponseMap.put("txnStatus", "ACTC");
        txnResponseMap.put("txnSettlementAmt", DecimalUtils.formatDecimal(DecimalUtils.toDecimal(fundWithdrawApplyEntity.getActualBalance())));
        txnResponseMap.put("txnSettlementDt", day);

        // 将header和txnResponse添加到dataMap中
        dataMap.put("header", headerMap);
        dataMap.put("txnResponse", txnResponseMap);
        return R.data(JSON.toJSONString(dataMap));
    }

    @Override
    public R<String> sendFpsPPPFund(FundTransferEntity fundWithdrawApplyEntity) {
        String date = DateUtil.format(new Date(), "yyyy-MM-dd'T'HH:mm:ss.SSS");
        String day = DateUtil.format(new Date(), "yyyy-MM-dd");
        String msgId = UUID.randomUUID().toString().replace("-", "");
        String customerReference = UUID.randomUUID().toString().replace("-", "");
        String txnRefId = UUID.randomUUID().toString().replace("-", "");
        Map<String, Object> dataMap = new HashMap<>();

        // 创建header子对象的Map
        Map<String, Object> headerMap = new HashMap<>();
        headerMap.put("msgId", "PPP" + msgId);
        headerMap.put("timeStamp", date);

        // 创建txnResponse子对象的Map
        Map<String, Object> txnResponseMap = new HashMap<>();
        txnResponseMap.put("customerReference", "PPP" + customerReference);
        txnResponseMap.put("paymentReference", "");
        txnResponseMap.put("txnRefId", txnRefId);
        txnResponseMap.put("bankReference", txnRefId);
        txnResponseMap.put("txnType", "PPP");
        txnResponseMap.put("txnRejectCode", "");
        txnResponseMap.put("txnStatusDescription", "Success");
        txnResponseMap.put("txnStatus", "ACTC");
        txnResponseMap.put("txnSettlementAmt", DecimalUtils.formatDecimal(DecimalUtils.toDecimal(fundWithdrawApplyEntity.getActualBalance())));
        txnResponseMap.put("txnSettlementDt", day);

        // 将header和txnResponse添加到dataMap中
        dataMap.put("header", headerMap);
        dataMap.put("txnResponse", txnResponseMap);
        return R.data(JSON.toJSONString(dataMap));
    }

    @Override
    public R<String> sendActFund(FundTransferEntity fundWithdrawApplyEntity) {
        String date = DateUtil.format(new Date(), "yyyy-MM-dd'T'HH:mm:ss.SSS");
        String msgId = UUID.randomUUID().toString().replace("-", "");
        Map<String, Object> headerMap = new HashMap<>();
        headerMap.put("msgId", msgId);
        headerMap.put("orgId", dbsBankConfig.getOrgId());
        headerMap.put("timeStamp", date);
        headerMap.put("ctry", "HK");

        // 创建txnResponses子对象的List
        List<Map<String, Object>> txnResponsesList = new ArrayList<>();

        // 创建第一个txnResponse子对象的Map
        Map<String, Object> txnResponseMap = new HashMap<>();
        txnResponseMap.put("responseType", "ACK1");
        txnResponseMap.put("msgRefId", fundWithdrawApplyEntity.getMsgId());
        txnResponseMap.put("txnStatus", "ACTC");
        txnResponseMap.put("txnStatusDescription", "Request Received");

        // 将第一个txnResponse添加到txnResponsesList中
        txnResponsesList.add(txnResponseMap);

        // 创建最终的dataMap对象
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("header", headerMap);
        dataMap.put("txnResponses", txnResponsesList);
        delayedSendingRemitAck("ACT", fundWithdrawApplyEntity);

        return R.data(JSON.toJSONString(dataMap));
    }

    @Override
    public R<String> sendRFDFund(FundTransferEntity fundWithdrawApplyEntity) {
        return R.fail();
    }

    @Override
    public R<String> sendFpsIdEnquiryFund(FundTransferEntity fundWithdrawApplyEntity) {
        return R.fail();
    }

    @Override
    public R<String> sendChatsFund(FundTransferEntity fundWithdrawApplyEntity) {
        String date = DateUtil.format(new Date(), "yyyy-MM-dd'T'HH:mm:ss.SSS");
        String msgId = UUID.randomUUID().toString().replace("-", "");
        Map<String, Object> headerMap = new HashMap<>();
        headerMap.put("msgId", msgId);
        headerMap.put("orgId", dbsBankConfig.getOrgId());
        headerMap.put("timeStamp", date);
        headerMap.put("ctry", "HK");

        // 创建txnResponses子对象的List
        List<Map<String, Object>> txnResponsesList = new ArrayList<>();

        // 创建第一个txnResponse子对象的Map
        Map<String, Object> txnResponseMap = new HashMap<>();
        txnResponseMap.put("responseType", "ACK1");
        txnResponseMap.put("msgRefId", fundWithdrawApplyEntity.getMsgId());
        txnResponseMap.put("txnStatus", "ACTC");
        txnResponseMap.put("txnStatusDescription", "Request Received");

        // 将第一个txnResponse添加到txnResponsesList中
        txnResponsesList.add(txnResponseMap);

        // 创建最终的dataMap对象
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("header", headerMap);
        dataMap.put("txnResponses", txnResponsesList);
        delayedSendingRemitAck("RTGS", fundWithdrawApplyEntity);

        return R.data(JSON.toJSONString(dataMap));
    }

    @Override
    public R<String> sendTTFund(FundTransferEntity fundWithdrawApplyEntity) {
        String date = DateUtil.format(new Date(), "yyyy-MM-dd'T'HH:mm:ss.SSS");
        String msgId = UUID.randomUUID().toString().replace("-", "");
        Map<String, Object> headerMap = new HashMap<>();
        headerMap.put("msgId", msgId);
        headerMap.put("orgId", dbsBankConfig.getOrgId());
        headerMap.put("timeStamp", date);
        headerMap.put("ctry", "HK");

        // 创建txnResponses子对象的List
        List<Map<String, Object>> txnResponsesList = new ArrayList<>();

        // 创建第一个txnResponse子对象的Map
        Map<String, Object> txnResponseMap = new HashMap<>();
        txnResponseMap.put("responseType", "ACK1");
        txnResponseMap.put("msgRefId", fundWithdrawApplyEntity.getMsgId());
        txnResponseMap.put("txnStatus", "ACTC");
        txnResponseMap.put("txnStatusDescription", "Request Received");

        // 将第一个txnResponse添加到txnResponsesList中
        txnResponsesList.add(txnResponseMap);

        // 创建最终的dataMap对象
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("header", headerMap);
        dataMap.put("txnResponses", txnResponsesList);
        delayedSendingRemitAck("TT", fundWithdrawApplyEntity);
        return R.data(JSON.toJSONString(dataMap));
    }

    @Override
    public R<String> sendBleQuery(AccountBalanceRequestVO accountBalanceRequestVo) {
        String date = DateUtil.format(new Date(), "yyyy-MM-dd'T'HH:mm:ss.SSS");
        String msgId = UUID.randomUUID().toString().replace("-", "");
        Map<String, Object> dataMap = new HashMap<>();
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("msgId", "BLE" + msgId);
        headerMap.put("orgId", dbsBankConfig.getOrgId());
        headerMap.put("timeStamp", date);

        // 创建accountBalResponse子对象的Map
        Map<String, String> accountBalResponseMap = new HashMap();
        accountBalResponseMap.put("enqStatus", "ACSP");
        accountBalResponseMap.put("accountName", "");
        accountBalResponseMap.put("accountNo", accountBalanceRequestVo.getAccountNo());
        accountBalResponseMap.put("accountCcy", accountBalanceRequestVo.getAccountCcy());
        accountBalResponseMap.put("halfDayHold", "0.0000");
        accountBalResponseMap.put("oneDayHold", "0.0000");
        accountBalResponseMap.put("twoDaysHold", "0.0000");
        accountBalResponseMap.put("clsLedgerBal", "775527592.0000");
        accountBalResponseMap.put("clsAvailableBal", "775527592.0000");
        accountBalResponseMap.put("businessDate", "2021-04-27 00:00:00.0");

        // 将header和accountBalResponse添加到dataMap中
        dataMap.put("header", headerMap);
        dataMap.put("accountBalResponse", accountBalResponseMap);
        return R.data(JSON.toJSONString(dataMap));
    }

    @Override
    public R<String> sendProvisionQuery(SubAccountRequestVO subAccountRequestVO) {
        return R.fail();
    }

    @Override
    public R<String> sendICCARE(DbsIccBankFlowEntity dbsIccBankFlowEntity) {
        return R.fail();
    }

    @Override
    public R<String> sendEDDAEnquiry(EddaTransactionEnquiryRequestVO eddaTransaction) {
        return R.fail();
    }

    @Override
    public R<String> sendCamt053(Camt053ReportRequestVO reportRequestVO) {
        return R.fail();
    }

    @Override
    public R<String> sendCamt052(Camt052ReportRequestVO reportRequestVO) {
        return R.fail();

    }

    @Override
    public R<String> sendTseEnquiry(TseTransactionEnquiryRequestVO requestVO) {
        String date = DateUtil.format(new Date(), "yyyy-MM-dd'T'HH:mm:ss.SSS");
        String msgId = UUID.randomUUID().toString().replace("-", "");
        String refId = UUID.randomUUID().toString().replace("-", "");
        Map<String, Object> jsonDataMap = new HashMap<>();

        // Populate the header properties
        Map<String, Object> headerMap = new HashMap<>();
        headerMap.put("msgId", msgId);
        headerMap.put("orgId", dbsBankConfig.getOrgId());
        headerMap.put("timeStamp", date);
        jsonDataMap.put("header", headerMap);

        // Populate the txnEnqResponse properties
        Map<String, Object> txnEnqResponseMap = new HashMap<>();
        txnEnqResponseMap.put("enqType", requestVO.getEnqType());
        txnEnqResponseMap.put("enqStatus", "ACSP");
        txnEnqResponseMap.put("customerReference", requestVO.getApplicationId());
        txnEnqResponseMap.put("customerTxnId", "");
        txnEnqResponseMap.put("msgRefId", "IAA"+refId);
        txnEnqResponseMap.put("txnRefId", "RAA"+refId);
        txnEnqResponseMap.put("bankReference", "0016RF0000000009");
        txnEnqResponseMap.put("refId", "");
        txnEnqResponseMap.put("txnType", "ACT");
        txnEnqResponseMap.put("txnStatus", "ACSP");
        txnEnqResponseMap.put("txnRejectCode", "");
        txnEnqResponseMap.put("txnStatusDescription", "Completed");
        txnEnqResponseMap.put("txnCcy", requestVO.getTxnCcy());
        txnEnqResponseMap.put("txnAmount", requestVO.getTxnAmount().toString());
        txnEnqResponseMap.put("debitAccountCcy", requestVO.getTxnCcy());
        txnEnqResponseMap.put("txnSettlementAmt", requestVO.getTxnAmount().toString());
        txnEnqResponseMap.put("txnSettlementDt", date);

        List<Map<String, Object>> clientReferencesList = new ArrayList<>();
        Map<String, Object> clientReferencesMap1 = new HashMap<>();
        clientReferencesMap1.put("clientReference", "Client Reference 1");
        clientReferencesList.add(clientReferencesMap1);
        Map<String, Object> clientReferencesMap2 = new HashMap<>();
        clientReferencesMap2.put("clientReference", "client Reference2");
        clientReferencesList.add(clientReferencesMap2);
        txnEnqResponseMap.put("clientReferences", clientReferencesList);

        txnEnqResponseMap.put("fxContractRef1", "");
        txnEnqResponseMap.put("fxAmountUtilized1", "0.0");
        txnEnqResponseMap.put("fxContractRef2", "");
        txnEnqResponseMap.put("fxAmountUtilized2", "0.0");
        txnEnqResponseMap.put("chargeBearer", "");
        txnEnqResponseMap.put("debitAccountForBankCharges", "");
        txnEnqResponseMap.put("chargesCcy", "");
        txnEnqResponseMap.put("chargesAmount", "");

        Map<String, Object> senderPartyMap = new HashMap<>();
        senderPartyMap.put("name", "");
        txnEnqResponseMap.put("senderParty", senderPartyMap);

        Map<String, Object> receivingPartyMap = new HashMap<>();
        receivingPartyMap.put("name", "");
        receivingPartyMap.put("accountNo", "");
        receivingPartyMap.put("bankCtryCode", "");
        receivingPartyMap.put("swiftBic", "");
        receivingPartyMap.put("bankName", "");

        List<Map<String, Object>> beneficiaryAddressesList = new ArrayList<>();
        Map<String, Object> beneficiaryAddressesMap1 = new HashMap<>();
        beneficiaryAddressesMap1.put("address", "");
        beneficiaryAddressesList.add(beneficiaryAddressesMap1);
        Map<String, Object> beneficiaryAddressesMap2 = new HashMap<>();
        beneficiaryAddressesMap2.put("address", "");
        beneficiaryAddressesList.add(beneficiaryAddressesMap2);
        Map<String, Object> beneficiaryAddressesMap3 = new HashMap<>();
        beneficiaryAddressesMap3.put("address", "");
        beneficiaryAddressesList.add(beneficiaryAddressesMap3);
        receivingPartyMap.put("beneficiaryAddresses", beneficiaryAddressesList);

        txnEnqResponseMap.put("receivingParty", receivingPartyMap);

        jsonDataMap.put("txnEnqResponse", txnEnqResponseMap);

        return R.data(JSON.toJSONString(jsonDataMap));
    }

    /**
     * 延时发送模拟 ACK2 ACK3回调
     *
     * @param txnType
     * @param fundTransferEntity
     */
    private void delayedSendingRemitAck(String txnType, FundTransferEntity fundTransferEntity) {
        new Thread(() -> {
            new Thread(new RemitAck2(this.dbsCallbackClient, fundTransferEntity, txnType, this.dbsBankConfig)).start();
            new Thread(new RemitAck3(this.dbsCallbackClient, fundTransferEntity, txnType, this.dbsBankConfig)).start();
        }).start();
    }

}

@Slf4j
class RemitAck2 implements Runnable {
    private FundTransferEntity fundTransferEntity;
    private DbsCallbackClient dbsCallbackClient;
    private DbsBankConfig dbsBankConfig;
    private String txnType;

    public RemitAck2(DbsCallbackClient dbsCallbackClient, FundTransferEntity fundTransferEntity, String txnType, DbsBankConfig dbsBankConfig) {
        this.txnType = txnType;
        this.dbsBankConfig = dbsBankConfig;
        this.dbsCallbackClient = dbsCallbackClient;
        this.fundTransferEntity = fundTransferEntity;
    }

    @SneakyThrows
    @Override
    public void run() {
        Thread.sleep(1000 * 30);
        String date = DateUtil.format(new Date(), "yyyy-MM-dd'T'HH:mm:ss.SSS");
        String msgId = UUID.randomUUID().toString().replace("-", "");
        String customerReference = UUID.randomUUID().toString().replace("-", "");
        Map<String, Object> headerMap = new HashMap<>();
        headerMap.put("msgId", fundTransferEntity.getMsgId());
        headerMap.put("orgId", dbsBankConfig.getOrgId());
        headerMap.put("timeStamp", date);
        headerMap.put("ctry", "HK");
        headerMap.put("noOfTxs", "1");
        headerMap.put("totalTxnAmount", fundTransferEntity.getActualBalance());

        // 创建txnResponses子对象的List
        List<Map<String, Object>> txnResponsesList = new ArrayList<>();

        // 创建第一个txnResponse子对象的Map
        Map<String, Object> txnResponseMap = new HashMap<>();
        txnResponseMap.put("responseType", "ACK2");
        txnResponseMap.put("customerReference", customerReference);
        txnResponseMap.put("msgRefId", "IRS" + msgId);
        txnResponseMap.put("txnRefId", "RTH" + msgId);
        txnResponseMap.put("txnType", txnType);
        txnResponseMap.put("txnStatus", "ACCP");
        txnResponseMap.put("txnStatusDescription", "Pending Bank processing");

        // 将第一个txnResponse添加到txnResponsesList中
        txnResponsesList.add(txnResponseMap);

        // 创建最终的dataMap对象
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("header", headerMap);
        dataMap.put("txnResponses", txnResponsesList);
        String ack2 = JSON.toJSONString(dataMap);
        R r = dbsCallbackClient.remitAck(ack2);
        if (r.isSuccess()) {
            log.info("发送ACK2成功:{}", ack2);
        } else {
            log.error("发送ACK2失败:{} - {}", r.getMsg(), ack2);
        }
    }
}

@Slf4j
class RemitAck3 implements Runnable {
    private FundTransferEntity fundTransferEntity;
    private DbsCallbackClient dbsCallbackClient;
    private DbsBankConfig dbsBankConfig;
    private String txnType;

    public RemitAck3(DbsCallbackClient dbsCallbackClient, FundTransferEntity fundTransferEntity, String txnType, DbsBankConfig dbsBankConfig) {
        this.txnType = txnType;
        this.dbsBankConfig = dbsBankConfig;
        this.dbsCallbackClient = dbsCallbackClient;
        this.fundTransferEntity = fundTransferEntity;
    }

    @SneakyThrows
    @Override
    public void run() {
        Thread.sleep(1000 * 60);
        String date = DateUtil.format(new Date(), "yyyy-MM-dd'T'HH:mm:ss.SSS");
        String msgId = UUID.randomUUID().toString().replace("-", "");
        String customerReference = UUID.randomUUID().toString().replace("-", "");
        String bankReference = UUID.randomUUID().toString().replace("-", "");
        Map<String, Object> headerMap = new HashMap<>();
        headerMap.put("msgId", fundTransferEntity.getMsgId());
        headerMap.put("orgId", dbsBankConfig.getOrgId());
        headerMap.put("timeStamp", date);
        headerMap.put("ctry", "HK");
        headerMap.put("noOfTxs", "1");
        headerMap.put("totalTxnAmount", fundTransferEntity.getActualBalance());

        // 创建txnResponses子对象的List
        List<Map<String, Object>> txnResponsesList = new ArrayList<>();

        // 创建第一个txnResponse子对象的Map
        Map<String, Object> txnResponseMap = new HashMap<>();
        txnResponseMap.put("responseType", "ACK3");
        txnResponseMap.put("customerReference", customerReference);
        txnResponseMap.put("msgRefId", msgId);
        txnResponseMap.put("txnRefId", msgId);
        txnResponseMap.put("bankReference", bankReference);
        txnResponseMap.put("txnType", txnType);
        txnResponseMap.put("txnStatus", "ACWC");
        txnResponseMap.put("txnStatusDescription", "Completed with Change");
        txnResponseMap.put("txnCcy", "HKD");
        txnResponseMap.put("txnAmount", fundTransferEntity.getActualBalance());
        txnResponseMap.put("debitAccountCcy", fundTransferEntity.getMoneyType());
        txnResponseMap.put("tranSettlementDt", date);
        txnResponseMap.put("tranSettlementAmt", fundTransferEntity.getActualBalance());
        txnResponseMap.put("fxContractRef1", "");
        txnResponseMap.put("fxAmountUtilized1", "0.0");
        txnResponseMap.put("fxContractRef2", "");
        txnResponseMap.put("fxAmountUtilized2", "0.0");
        txnResponseMap.put("chargeBearer", "DEBT");
        txnResponseMap.put("debitAccountForBankCharges", "");
        txnResponseMap.put("chargesCcy", "HKD");
        txnResponseMap.put("chargesAmount", "0.0");

        // 创建senderParty子对象的Map
        Map<String, Object> senderPartyMap = new HashMap<>();
        senderPartyMap.put("name", "HXXPX XRXXXIXX CX");

        // 创建receivingParty子对象的Map
        Map<String, Object> receivingPartyMap = new HashMap<>();
        receivingPartyMap.put("name", "HK Beneficiary - HK Account");
        receivingPartyMap.put("accountNo", "300112345678");
        receivingPartyMap.put("bankCtryCode", "SG");
        receivingPartyMap.put("swiftBic", "DBSSSGS0XXX");
        receivingPartyMap.put("bankName", "Beneficiary Bank Name");

        // 创建beneficiaryAddresses子对象的List
        List<Map<String, Object>> beneficiaryAddressesList = new ArrayList<>();

        // 创建第一个beneficiaryAddress子对象的Map
        Map<String, Object> beneficiaryAddressMap1 = new HashMap<>();
        beneficiaryAddressMap1.put("address", "Beneficiary address1");

        // 创建第二个beneficiaryAddress子对象的Map
        Map<String, Object> beneficiaryAddressMap2 = new HashMap<>();
        beneficiaryAddressMap2.put("address", "Beneficiary address2");

        // 创建第三个beneficiaryAddress子对象的Map
        Map<String, Object> beneficiaryAddressMap3 = new HashMap<>();
        beneficiaryAddressMap3.put("address", "Beneficiary address3");

        // 将beneficiaryAddress添加到beneficiaryAddressesList中
        beneficiaryAddressesList.add(beneficiaryAddressMap1);
        beneficiaryAddressesList.add(beneficiaryAddressMap2);
        beneficiaryAddressesList.add(beneficiaryAddressMap3);

        // 将beneficiaryAddresses添加到receivingPartyMap中
        receivingPartyMap.put("beneficiaryAddresses", beneficiaryAddressesList);

        // 将senderParty和receivingParty添加到txnResponseMap中
        txnResponseMap.put("senderParty", senderPartyMap);
        txnResponseMap.put("receivingParty", receivingPartyMap);

        // 将第一个txnResponse添加到txnResponsesList中
        txnResponsesList.add(txnResponseMap);

        // 创建最终的dataMap对象
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("header", headerMap);
        dataMap.put("txnResponses", txnResponsesList);
        String ack3 = JSON.toJSONString(dataMap);
        R r = dbsCallbackClient.remitAck(ack3);
        if (r.isSuccess()) {
            log.info("发送ACK3成功:{}", ack3);
        } else {
            log.error("发送ACK3失败:{} - {}", r.getMsg(), ack3);
        }
    }
}
