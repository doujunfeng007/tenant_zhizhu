package com.minigod.zero.dbs.protocol;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.minigod.zero.dbs.bo.FundTransferEntity;
import com.minigod.zero.dbs.enums.FpsIdTypeEnum;
import com.minigod.zero.dbs.enums.MoneyEnum;
import com.minigod.zero.dbs.util.DecimalUtils;
import com.minigod.zero.dbs.vo.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 包装请求DBS数据
 */
public class DbsReqPackag {
    /**
     * 是否包含中文
     * @param text
     * @return
     */
    public static boolean isIncludeChinese(String text) {
        if (CharSequenceUtil.isBlank(text)) {
            return false;
        }
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(text);
        return m.find();
    }
    private static List<Map<String,Object>>  splitField(String field){
        List<Map<String, Object>> map = new ArrayList<>(3);
        int chunkSize = 35;
        if(isIncludeChinese(field)){
            chunkSize = 7;
        }
        String[] chunks = chunkString(field, chunkSize);
        for (int i = 1; i < chunks.length;i++){
            Map<String, Object> receivingNameExpansion = new HashMap<>(1);
            receivingNameExpansion.put("address",chunks[i]);
            map.add(receivingNameExpansion);
        }

        return map;
    }

    public static String[] chunkString(String str, int chunkSize) {
        int length = str.length();
        int numOfChunks = (int) Math.ceil((double) length / chunkSize);
        String[] chunks = new String[numOfChunks];

        for (int i = 0; i < numOfChunks; i++) {
            int startIndex = i * chunkSize;
            int endIndex = Math.min(startIndex + chunkSize, length);
            chunks[i] = str.substring(startIndex, endIndex);
        }

        return chunks;
    }
    /**
     * Are 请求参数包装
     *
     * @param msgId
     * @param accInfo
     * @return
     */
    public static String accountRangeEnquityReq(String msgId, String orgId, Map<String, Object> accInfo) {
        //封装header
        Map<String, Object> header = new HashMap<>();
        header.put("msgId", msgId);
        header.put("orgId", orgId);
        header.put("timeStamp", DateUtil.format(new Date(), "yyyy-MM-dd'T'HH:mm:ss.SSS"));
        header.put("ctry", "HK");

        //封装body
        Map<String, Object> body = new HashMap<String, Object>();
        body.put("header", header);
        body.put("accInfo", accInfo);
        return JSONUtil.parseObj(body).toString();
    }

    /**
     * edda Initiation 请求参数包装
     *
     * @param msgId
     * @param eddaInfo
     * @return
     */
    public static String eDDAInitiationReq(String msgId, String orgId, String ddaRef, EddaInfoRequestVO eddaInfo) {
        //封装header
        Map<String, Object> header = new HashMap<String, Object>(4);
        header.put("msgId", msgId);
        header.put("orgId", orgId);
        header.put("timeStamp", DateUtil.format(new Date(), "yyyy-MM-dd'T'HH:mm:ss.SSS"));
        header.put("ctry", "HK");

        //封装body
        Map<String, Object> body = new HashMap<String, Object>(2);
        body.put("header", header);

        Map<String, Object> txnInfo = new HashMap<String, Object>();
        txnInfo.put("txnType", "DDA");
        txnInfo.put("ddaRef", ddaRef);
        txnInfo.put("mandateType", "DDMP");
        txnInfo.put("seqType", "RCUR");
        txnInfo.put("frqcyType", "ADHO");
        txnInfo.put("countPerPeriod", "1");
        //注意生效日期 "Effective date is past date-effDate"
        if(!CharSequenceUtil.isBlank(eddaInfo.getEffDate())){
            txnInfo.put("effDate", eddaInfo.getEffDate());
        }else{
            txnInfo.put("effDate", DateUtil.format(new Date(), "yyyy-MM-dd"));
        }
        // 过期时间
        if(!CharSequenceUtil.isBlank(eddaInfo.getExpDate())){
            txnInfo.put("expDate", eddaInfo.getExpDate());
        }

        txnInfo.put("maxAmt", eddaInfo.getMaxAmt().setScale(2, RoundingMode.DOWN).toString());
        txnInfo.put("maxAmtCcy", eddaInfo.getAmtCcy());
        txnInfo.put("reason", "NWSTUP");

        Map<String, Object> creditor = new HashMap<String, Object>(3);
        creditor.put("name", eddaInfo.getBenefitAccount());
        creditor.put("accountNo", eddaInfo.getBenefitAccountNo());
        creditor.put("accountCcy", eddaInfo.getAmtCcy());
        txnInfo.put("creditor", creditor);

        Map<String, Object> debtor = new HashMap<String, Object>(5);
        debtor.put("name", eddaInfo.getDepositAccountName());
        debtor.put("proxyType", "B");
        debtor.put("proxyValue", eddaInfo.getDepositAccountNo());
        debtor.put("bankId", eddaInfo.getDepositBankId());

        List<Map<String, Object>> prvtCustomer = new ArrayList<Map<String, Object>>();
        Map<String, Object> prvtCustomerMap1 = new HashMap<String, Object>();
        prvtCustomerMap1.put("prvtCustomerId", eddaInfo.getBankIdNo());
        //银行开户证件类型:1 中华人民共和国居民身份证, 2 中华人民共和国往来港澳通行证, 3 香港居民身份证, 4 护照
		// 第二版 [0,-1=未知 1=大陆居民身份证 2=香港居民身份证 3=护照 4=驾驶证 5=港澳通行证]    或者取字典  "id_kind"
        if (3 == eddaInfo.getBankIdKind()) {
            prvtCustomerMap1.put("prvtCustomerIdType", "CCPT");
        } else if (2 == eddaInfo.getBankIdKind()) {
            prvtCustomerMap1.put("prvtCustomerIdType", "NIDN");
        } else {
            prvtCustomerMap1.put("prvtCustomerIdType", "OTHR");
        }
        prvtCustomer.add(prvtCustomerMap1);
        debtor.put("prvtCustomer", prvtCustomer);
        txnInfo.put("debtor", debtor);

        body.put("txnInfo", txnInfo);
        return JSON.toJSONString(body, SerializerFeature.WriteBigDecimalAsPlain);
    }

    /**
     * edda Enquiry 请求参数包装
     *
     * @param msgId
     * @param eddaTransaction
     * @return
     */
    public static String eDDAEnquiryReq(String msgId, String orgId, EddaTransactionEnquiryRequestVO eddaTransaction) {
        //封装header
        Map<String, Object> header = new HashMap<String, Object>();
        header.put("msgId", msgId);
        header.put("orgId", orgId);
        header.put("timeStamp", DateUtil.format(new Date(), "yyyy-MM-dd'T'HH:mm:ss.SSS"));
        header.put("ctry", "HK");

        Map<String, Object> txnInfo = new HashMap<String, Object>();
        txnInfo.put("txnType", "DAE");
        Map<String, Object> txnEnquiry = new HashMap<String, Object>();
        txnEnquiry.put("enqAccountNo", eddaTransaction.getBenefitAccountNo());
        txnEnquiry.put("accountCcy", eddaTransaction.getAmtCcy());
        //需要使用 eDDA Initiation 原接口请求Id
        txnEnquiry.put("originalMsgId", eddaTransaction.getOriginalMsgId());
        //edda enquiry中的txnRef要用本來edda initation返回的txnRefId
        txnEnquiry.put("txnRefId", eddaTransaction.getTxnRefId());

        //封装body
        Map<String, Object> body = new HashMap<String, Object>(3);
        body.put("header", header);
        body.put("txnInfo", txnInfo);
        body.put("txnEnquiry", txnEnquiry);
        return JSONUtil.parseObj(body).toString();
    }

    /**
     * Tse Enquiry 请求参数包装
     *
     * @param msgId
     * @param requestVO
     * @return
     */
    public static String tseEnquiryReq(String msgId, String orgId, TseTransactionEnquiryRequestVO requestVO) {
        //封装header
        Map<String, Object> header = new HashMap<String, Object>();
        header.put("msgId", msgId);
        header.put("orgId", orgId);
        header.put("timeStamp", DateUtil.format(new Date(), "yyyy-MM-dd'T'HH:mm:ss.SSS"));
        header.put("ctry", "HK");

        Map<String, Object> txnInfo = new HashMap<String, Object>(2);
        txnInfo.put("txnType", "TSE");
        if (CharSequenceUtil.isNotBlank(requestVO.getEnqType())) {
            txnInfo.put("enqType", requestVO.getEnqType());
        } else {
            txnInfo.put("enqType", "REM");
        }

        Map<String, Object> txnEnquiry = new HashMap<String, Object>();
        txnEnquiry.put("enqAccountNo", requestVO.getEnqAccountNo());
        txnEnquiry.put("customerReference", requestVO.getCustomerReference());
        txnEnquiry.put("paymentReference", requestVO.getPaymentReference());
        txnEnquiry.put("txnRefId", requestVO.getTxnRefId());
        txnEnquiry.put("refId", requestVO.getRefId());

        //封装body
        Map<String, Object> body = new HashMap<String, Object>(3);
        body.put("header", header);
        body.put("txnInfo", txnInfo);
        body.put("txnEnquiry", txnEnquiry);
        return JSONUtil.parseObj(body).toString();
    }


    /**
     * edda 快捷入金（FPS GPC） 请求参数包装
     *
     * @param msgId
     * @return
     */
    public static String hkFPSGPCReq(String msgId, String orgId, FpsTransactionRequestVO fpsTransaction) {
        //封装header
        Map<String, Object> header = new HashMap<String, Object>(3);
        header.put("msgId", msgId);
        header.put("orgId", orgId);
        header.put("timeStamp", DateUtil.format(new Date(), "yyyy-MM-dd'T'HH:mm:ss.SSS"));

        Map<String, Object> accInfo = new HashMap<String, Object>(10);
        accInfo.put("customerReference", fpsTransaction.getCustomerReference());
        accInfo.put("paymentReference", "");
        accInfo.put("txnType", "GPC");
        accInfo.put("txnDate", DateUtil.format(new Date(), "yyyy-MM-dd"));
        accInfo.put("txnCcy", fpsTransaction.getTxnCcy());
        accInfo.put("txnAmount", BigDecimal.valueOf(fpsTransaction.getTxnAmount()));
        accInfo.put("purposeOfPayment", "DDOTHR");

        Map<String, Object> senderParty = new HashMap<String, Object>(4);
        senderParty.put("name", fpsTransaction.getBenefitAccountName());
        senderParty.put("accountNo", fpsTransaction.getBenefitAccountNo());
        //GPC 方式下值必须传
        senderParty.put("ddaRef", fpsTransaction.getDdaRef());
        if(CharSequenceUtil.isNotBlank(fpsTransaction.getMandateId())){
            senderParty.put("mandateId", fpsTransaction.getMandateId());
        }
        accInfo.put("senderParty", senderParty);

        Map<String, Object> receivingParty = new HashMap<String, Object>(6);
        receivingParty.put("name", fpsTransaction.getDepositAccountName());
        receivingParty.put("bankId", fpsTransaction.getDepositBankId());
        receivingParty.put("bankCtryCode", "HK");
        receivingParty.put("proxyType", "B");
        receivingParty.put("proxyValue", fpsTransaction.getDepositAccountNo());
        List<Map<String, Object>> addresses = new ArrayList(0);
        receivingParty.put("addresses", addresses);
        accInfo.put("receivingParty", receivingParty);

        //封装body
        Map<String, Object> body = new HashMap<String, Object>(2);
        body.put("header", header);
        body.put("txnInfo", accInfo);
        return JSON.toJSONString(body, SerializerFeature.WriteBigDecimalAsPlain);
    }

    /**
     * FPS 快捷转账（FPS GPP） 请求参数包装
     *
     * @param msgId
     * @return
     */
    public static String hkFPSGPPReq(String msgId, String orgId, FundTransferEntity withdrawApplyEntity) {
        //封装header
        Map<String, Object> header = new HashMap<String, Object>(3);
        header.put("msgId", msgId);
        header.put("orgId", orgId);
        header.put("timeStamp", DateUtil.format(new Date(), "yyyy-MM-dd'T'HH:mm:ss.SSS"));

        Map<String, Object> accInfo = new HashMap<String, Object>(7);
        accInfo.put("customerReference", withdrawApplyEntity.getCusRef()); // 客户流水
        accInfo.put("paymentReference", "");
        accInfo.put("txnType", "GPP");
        accInfo.put("txnDate", DateUtil.format(new Date(), "yyyy-MM-dd"));

        String ccy = MoneyEnum.getIndex(withdrawApplyEntity.getMoneyType());
        accInfo.put("txnCcy", ccy);
        accInfo.put("txnAmount", DecimalUtils.toDecimal(withdrawApplyEntity.getActualBalance()).toString());
        accInfo.put("purposeOfPayment", "CXBSNS");

        Map<String, Object> senderParty = new HashMap<String, Object>(2);
        senderParty.put("name", withdrawApplyEntity.getDepositAccount()); // 付款人名称
        senderParty.put("accountNo", withdrawApplyEntity.getDepositNo()); // 付款人银行账号
        //GPP\PPP 方式下值忽略
//        senderParty.put("ddaRef", "");
//        senderParty.put("mandateId","");
        accInfo.put("senderParty", senderParty);

        Map<String, Object> receivingParty = new HashMap<String, Object>(5);
        receivingParty.put("name", withdrawApplyEntity.getClientNameSpell()); // 收款人名称 支持中文和繁体
        receivingParty.put("bankId", withdrawApplyEntity.getBankId()); // 银行编号 3位数字
        receivingParty.put("bankCtryCode", "HK");
        receivingParty.put("proxyType", "B"); // 银行账号类型
        receivingParty.put("proxyValue", withdrawApplyEntity.getBankNo()); // 银行账号
        accInfo.put("receivingParty", receivingParty);

        //封装body
        Map<String, Object> body = new HashMap<String, Object>();
        body.put("header", header);
        body.put("txnInfo", accInfo);
        return JSONUtil.parseObj(body).toString();
    }

    /**
     * FPS 快捷转账（FPS PPP） 请求参数包装
     *
     * @param msgId
     * @return
     */
    public static String hkFPSPPPReq(String msgId, String orgId, FundTransferEntity withdrawApplyEntity) {
        //封装header
        Map<String, Object> header = new HashMap<String, Object>(3);
        header.put("msgId", msgId);
        header.put("orgId", orgId);
        header.put("timeStamp", DateUtil.format(new Date(), "yyyy-MM-dd'T'HH:mm:ss.SSS"));

        Map<String, Object> txnInfo = new HashMap<String, Object>(7);
        txnInfo.put("customerReference", withdrawApplyEntity.getCusRef()); // 客户流水
        txnInfo.put("paymentReference", "");
        txnInfo.put("txnType", "PPP");
        txnInfo.put("txnDate", DateUtil.format(new Date(), "yyyy-MM-dd"));

        String ccy = MoneyEnum.getIndex(withdrawApplyEntity.getMoneyType());
        txnInfo.put("txnCcy", ccy);
        txnInfo.put("txnAmount", DecimalUtils.toDecimal(withdrawApplyEntity.getActualBalance()).toString());
        txnInfo.put("purposeOfPayment", "CXBSNS");

        Map<String, Object> senderParty = new HashMap<String, Object>(2);
        senderParty.put("name", withdrawApplyEntity.getDepositAccount()); // 付款人名称
        senderParty.put("accountNo", withdrawApplyEntity.getDepositNo()); // 付款人银行账号
        //GPP\PPP 方式下值忽略
//        senderParty.put("ddaRef", "");
//        senderParty.put("mandateId","");
        txnInfo.put("senderParty", senderParty);

        Map<String, Object> receivingParty = new HashMap<String, Object>(5);
        receivingParty.put("name", withdrawApplyEntity.getClientNameSpell()); // 收款人名称 支持中文和繁体
        //receivingParty.put("bankId", withdrawApplyEntity.getBankCode()); // 银行编号 3位数字 可选
        receivingParty.put("bankCtryCode", "HK");

        // 判断银行账户类型
        FpsIdTypeEnum idTypeEnum = FpsIdTypeEnum.getEnum(withdrawApplyEntity.getBankAccountType());
        receivingParty.put("proxyType", idTypeEnum.getValue()); // 银行账号类型
		receivingParty.put("proxyValue", withdrawApplyEntity.getBankNo()); // 银行账号
		if (receivingParty.get("proxyType").equals(FpsIdTypeEnum.FPS_HKID.getValue())){
			receivingParty.put("proxyValue", withdrawApplyEntity.getBankNo().replaceAll("[()]", "")); // 银行账号
		}
        txnInfo.put("receivingParty", receivingParty);

        //封装body
        Map<String, Object> body = new HashMap<String, Object>();
        body.put("header", header);
        body.put("txnInfo", txnInfo);
        return JSONUtil.parseObj(body).toString();
    }

    /**
     * ACt 同行转账 请求参数包装
     *
     * @param msgId
     * @return
     */
    public static String hkActReq(String msgId, String orgId, FundTransferEntity withdrawApplyEntity) {
        //封装header
        Map<String, Object> header = new HashMap<String, Object>(3);
        header.put("msgId", msgId);
        header.put("orgId", orgId);
        header.put("timeStamp", DateUtil.format(new Date(), "yyyy-MM-dd'T'HH:mm:ss.SSS"));
        header.put("ctry", "HK");
        header.put("noOfTxs", "1");
        header.put("totalTxnAmount", DecimalUtils.toDecimal(withdrawApplyEntity.getActualBalance()).toString());

        Map<String, Object> txnInfo = new HashMap<String, Object>(8);
        txnInfo.put("customerReference", withdrawApplyEntity.getCusRef()); // 客户流水
        txnInfo.put("txnType", "ACT");
        txnInfo.put("txnDate", DateUtil.format(new Date(), "yyyy-MM-dd"));

        String ccy = MoneyEnum.getIndex(withdrawApplyEntity.getMoneyType());
        txnInfo.put("txnCcy", ccy);
        txnInfo.put("txnAmount", DecimalUtils.toDecimal(withdrawApplyEntity.getActualBalance()).toString());

        // 付款人币种
        String debitCcy = MoneyEnum.getIndex(withdrawApplyEntity.getDebitMoneyType());
        txnInfo.put("debitAccountCcy", debitCcy);

        // 暂时不考虑 FX
        // txnInfo.put("debitAccountAmount", DecimalUtils.toDecimal(withdrawApplyEntity.getActualBalance()).toString());
        //txnInfo.put("fxContractRef1", ccy);
        //txnInfo.put("fxAmountUtilized1", ccy);
        Map<String, Object> senderParty = new HashMap<String, Object>(3);
        senderParty.put("name", withdrawApplyEntity.getDepositAccount()); // 付款人名称
        senderParty.put("accountNo", withdrawApplyEntity.getDepositNo()); // 付款人银行账号
        senderParty.put("bankCtryCode", withdrawApplyEntity.getSenderBankCtryCode()); // "HK" - for Hong Kong Limited. "HB" - for Hong Kong Branch.
        txnInfo.put("senderParty", senderParty);

        Map<String, Object> receivingParty = new HashMap<String, Object>(5);
        receivingParty.put("name", withdrawApplyEntity.getClientNameSpell()); // 收款人名称 支持中文和繁体
        receivingParty.put("accountNo", withdrawApplyEntity.getBankNo()); // 银行账号
        txnInfo.put("receivingParty", receivingParty);

        // txnInfoDetails
        Map<String, Object> txnInfoDetails = new HashMap<String, Object>(7);
        List<Map<String, Object>> lstTxnInfo = new ArrayList<Map<String, Object>>(1);
        lstTxnInfo.add(txnInfo);
        txnInfoDetails.put("txnInfo", lstTxnInfo);

        //封装body
        Map<String, Object> body = new HashMap<String, Object>();
        body.put("header", header);
        body.put("txnInfoDetails", txnInfoDetails);
        return JSONUtil.parseObj(body).toString();
    }

    /**
     * FPSid查询
     *
     * @param msgId
     * @return
     */
    public static String hkFpsIdEnquiryReq(String msgId, String orgId, FundTransferEntity withdrawApplyEntity) {
        //封装header
        Map<String, Object> header = new HashMap<String, Object>(6);
        header.put("msgId", msgId);
        header.put("orgId", orgId);
        header.put("timeStamp", DateUtil.format(new Date(), "yyyy-MM-dd'T'HH:mm:ss.SSS"));
        header.put("bankCtryCode", "HK");
        header.put("accountNo", withdrawApplyEntity.getDepositNo());
        String ccy = MoneyEnum.getIndex(withdrawApplyEntity.getMoneyType());
        header.put("accountCcy", ccy);

        Map<String, Object> enqInfo = new HashMap<String, Object>(8);
        // 判断银行账户类型
        FpsIdTypeEnum idTypeEnum = FpsIdTypeEnum.getEnum(withdrawApplyEntity.getBankAccountType());
        enqInfo.put("proxyType", idTypeEnum==null?"S":idTypeEnum.getValue());
        enqInfo.put("proxyValue", withdrawApplyEntity.getBankNo());
		if (enqInfo.get("proxyType").equals(FpsIdTypeEnum.FPS_HKID.getValue())){
			enqInfo.put("proxyValue", withdrawApplyEntity.getBankNo().replaceAll("[()]", "")); // 银行账号
		}
        enqInfo.put("bankId", withdrawApplyEntity.getBankId());
        enqInfo.put("accountName", withdrawApplyEntity.getClientNameSpell());
        enqInfo.put("purposeCode","PAYM");

        //封装body
        Map<String, Object> body = new HashMap<String, Object>();
        body.put("header", header);
        body.put("enqInfo", enqInfo);
        return JSONUtil.parseObj(body).toString();
    }

    /**
     * 退款
     *
     * @param msgId
     * @return
     */
    public static String hkRFDReq(String msgId, String orgId, FundTransferEntity withdrawApplyEntity) {
        //封装header
        Map<String, Object> header = new HashMap<String, Object>(3);
        header.put("msgId", msgId);
        header.put("orgId", orgId);
        header.put("timeStamp", DateUtil.format(new Date(), "yyyy-MM-dd'T'HH:mm:ss.SSS"));

        Map<String, Object> txnInfo = new HashMap<String, Object>(8);
        txnInfo.put("txnType","RFD");
        txnInfo.put("refundRef",msgId);
        txnInfo.put("accountNo",withdrawApplyEntity.getHsBankAccount());
        txnInfo.put("txnRefNo","IRGPPHK241022161317A0000215");

        txnInfo.put("txnAmount","0");
        txnInfo.put("txnCcy","HKD");
        txnInfo.put("retAmount","0");
        txnInfo.put("addInfo","Wrong credit");

        //封装body
        Map<String, Object> body = new HashMap<String, Object>();
        body.put("header", header);
        body.put("txnInfo", txnInfo);
        return JSONUtil.parseObj(body).toString();
    }



    /**
     * Chats 夸行转账 请求参数包装
     *
     * @param msgId
     * @return
     */
    public static String hkChatsReq(String msgId, String orgId, FundTransferEntity withdrawApplyEntity) {
        //封装header
        Map<String, Object> header = new HashMap<String, Object>(3);
        header.put("msgId", msgId);
        header.put("orgId", orgId);
        header.put("timeStamp", DateUtil.format(new Date(), "yyyy-MM-dd'T'HH:mm:ss.SSS"));
        header.put("ctry", "HK");
        header.put("noOfTxs", "1");
        header.put("totalTxnAmount", DecimalUtils.toDecimal(withdrawApplyEntity.getActualBalance()).toString());

        Map<String, Object> txnInfo = new HashMap<String, Object>(10);
        txnInfo.put("customerReference", withdrawApplyEntity.getCusRef()); // 客户流水
        txnInfo.put("txnType", "RTGS");
        txnInfo.put("txnDate", DateUtil.format(new Date(), "yyyy-MM-dd"));
        String ccy = MoneyEnum.getIndex(withdrawApplyEntity.getMoneyType());
        txnInfo.put("txnCcy", ccy); // 收款人币种
        txnInfo.put("txnAmount", DecimalUtils.toDecimal(withdrawApplyEntity.getActualBalance()).toString());

        // 付款人币种
        String debitCcy = MoneyEnum.getIndex(withdrawApplyEntity.getDebitMoneyType());
        txnInfo.put("debitAccountCcy", debitCcy);

        // 手续费扣除方 CRED：收款人 DEBT：付款人 SHAR：付款人扣除DBS手续费 收款方扣除代理银行手续费
        txnInfo.put("chargeBearer", "SHAR");
        txnInfo.put("chargesCcy", debitCcy); // 手续费币种

        // 暂时不考虑 FX
        //txnInfo.put("fxContractRef1", ccy);
        //txnInfo.put("fxAmountUtilized1", ccy);
        Map<String, Object> senderParty = new HashMap<String, Object>(3);
        senderParty.put("name", withdrawApplyEntity.getDepositAccount()); // 付款人名称
        senderParty.put("accountNo", withdrawApplyEntity.getDepositNo()); // 付款人银行账号
        senderParty.put("bankCtryCode", withdrawApplyEntity.getSenderBankCtryCode()); // "HK" - for Hong Kong Limited. "HB" - for Hong Kong Branch.
        txnInfo.put("senderParty", senderParty);

        Map<String, Object> receivingParty = new HashMap<String, Object>(5);
        receivingParty.put("name", withdrawApplyEntity.getClientNameSpell().length()>35?withdrawApplyEntity.getClientNameSpell().substring(0,35):withdrawApplyEntity.getClientNameSpell()); // 收款人名称 支持中文和繁体
        receivingParty.put("accountNo", withdrawApplyEntity.getBankNo()); // 银行账号
        receivingParty.put("bankCtryCode", withdrawApplyEntity.getReceiveBankCtryCode()); // "HK" - for Hong Kong Limited. "HB" - for Hong Kong Branch.
        receivingParty.put("swiftBic", withdrawApplyEntity.getSwiftCode());
        // 地址
        Map<String, Object> beneficiaryAddress = new HashMap<String, Object>(1);
        beneficiaryAddress.put("address", withdrawApplyEntity.getContactAddress());
        List<Map<String, Object>> lstBeneficiaryAddress = new ArrayList<Map<String, Object>>(1);
        if(withdrawApplyEntity.getClientNameSpell().length()>35){
            //收款人账户超过 35 个字符时
            List<Map<String, Object>> receivingNameExpansionList =  splitField(withdrawApplyEntity.getClientNameSpell());
            for (Map<String, Object> address :receivingNameExpansionList){
                //将字段裁缝成多个地址
                lstBeneficiaryAddress.add(address);
            }
        }
        lstBeneficiaryAddress.add(beneficiaryAddress);
        //最多三个地址
        receivingParty.put("beneficiaryAddresses", lstBeneficiaryAddress.subList(0,lstBeneficiaryAddress.size()>3?3:lstBeneficiaryAddress.size()));
        txnInfo.put("receivingParty", receivingParty);

        // txnInfoDetails
        Map<String, Object> txnInfoDetails = new HashMap<String, Object>(7);
        List<Map<String, Object>> lstTxnInfo = new ArrayList<Map<String, Object>>(1);
        lstTxnInfo.add(txnInfo);
        txnInfoDetails.put("txnInfo", lstTxnInfo);

        //封装body
        Map<String, Object> body = new HashMap<String, Object>();
        body.put("header", header);
        body.put("txnInfoDetails", txnInfoDetails);
        return JSONUtil.parseObj(body).toString();
    }


    /**
     * TT 海外转账 请求参数包装
     *
     * @param msgId
     * @return
     */
    public static String hkTTReq(String msgId, String orgId, FundTransferEntity withdrawApplyEntity) {
        //封装header
        Map<String, Object> header = new HashMap<String, Object>(3);
        header.put("msgId", msgId);
        header.put("orgId", orgId);
        header.put("timeStamp", DateUtil.format(new Date(), "yyyy-MM-dd'T'HH:mm:ss.SSS"));
        header.put("ctry", "HK");
        header.put("noOfTxs", "1");
        header.put("totalTxnAmount", DecimalUtils.toDecimal(withdrawApplyEntity.getActualBalance()).toString());

        Map<String, Object> txnInfo = new HashMap<String, Object>(10);
        txnInfo.put("customerReference", withdrawApplyEntity.getCusRef()); // 客户流水
        txnInfo.put("txnType", "TT");
        txnInfo.put("txnDate", DateUtil.format(new Date(), "yyyy-MM-dd"));
        String ccy = MoneyEnum.getIndex(withdrawApplyEntity.getMoneyType());
        txnInfo.put("txnCcy", ccy); // 收款人币种
        txnInfo.put("txnAmount", DecimalUtils.toDecimal(withdrawApplyEntity.getActualBalance()).toString());

        // 付款人币种
        String debitCcy = MoneyEnum.getIndex(withdrawApplyEntity.getDebitMoneyType());
        txnInfo.put("debitAccountCcy", debitCcy);

        // 手续费扣除方 CRED：收款人 DEBT：付款人 SHAR：付款人扣除DBS手续费 收款方扣除代理银行手续费
        txnInfo.put("chargeBearer", "SHAR");
        txnInfo.put("chargesCcy", debitCcy); // 手续费币种

        // 暂时不考虑 FX
        //txnInfo.put("fxContractRef1", ccy);
        //txnInfo.put("fxAmountUtilized1", ccy);
        Map<String, Object> senderParty = new HashMap<String, Object>(3);
        senderParty.put("name", withdrawApplyEntity.getDepositAccount()); // 付款人名称
        senderParty.put("accountNo", withdrawApplyEntity.getDepositNo()); // 付款人银行账号
        senderParty.put("bankCtryCode", withdrawApplyEntity.getSenderBankCtryCode()); // "HK" - for Hong Kong Limited. "HB" - for Hong Kong Branch.
        txnInfo.put("senderParty", senderParty);

        Map<String, Object> receivingParty = new HashMap<String, Object>(5);
        receivingParty.put("name", withdrawApplyEntity.getClientNameSpell().length()>35?withdrawApplyEntity.getClientNameSpell().substring(0,35):withdrawApplyEntity.getClientNameSpell()); // 收款人名称 支持中文和繁体
        receivingParty.put("accountNo", withdrawApplyEntity.getBankNo()); // 银行账号
        receivingParty.put("bankCtryCode", withdrawApplyEntity.getReceiveBankCtryCode()); // "HK" - for Hong Kong Limited. "HB" - for Hong Kong Branch.
        receivingParty.put("swiftBic", withdrawApplyEntity.getSwiftCode());
        // 地址
        Map<String, Object> beneficiaryAddress = new HashMap<String, Object>(1);
        beneficiaryAddress.put("address", withdrawApplyEntity.getContactAddress());
        List<Map<String, Object>> lstBeneficiaryAddress = new ArrayList<Map<String, Object>>(1);
        if(withdrawApplyEntity.getClientNameSpell().length()>35){
            List<Map<String, Object>> receivingNameExpansionList =  splitField(withdrawApplyEntity.getClientNameSpell());
            for (Map<String, Object> address :receivingNameExpansionList){
                lstBeneficiaryAddress.add(address);
            }
        }
        lstBeneficiaryAddress.add(beneficiaryAddress);
        receivingParty.put("beneficiaryAddresses", lstBeneficiaryAddress.subList(0,lstBeneficiaryAddress.size()>3?3:lstBeneficiaryAddress.size()));
        txnInfo.put("receivingParty", receivingParty);

        // txnInfoDetails
        Map<String, Object> txnInfoDetails = new HashMap<String, Object>(7);
        List<Map<String, Object>> lstTxnInfo = new ArrayList<Map<String, Object>>(1);
        lstTxnInfo.add(txnInfo);
        txnInfoDetails.put("txnInfo", lstTxnInfo);

        //封装body
        Map<String, Object> body = new HashMap<String, Object>();
        body.put("header", header);
        body.put("txnInfoDetails", txnInfoDetails);
        return JSONUtil.parseObj(body).toString();
    }

    /**
     * 账户查询 (BLE) 请求参数包装
     *
     * @param msgId
     * @return
     */
    public static String hkBleReq(String msgId, String orgId, String accountNo, String accountCcy) {
        //封装header
        Map<String, Object> header = new HashMap<String, Object>(3);
        header.put("msgId", msgId);
        header.put("orgId", orgId);
        header.put("ctry", "HK");
        header.put("timeStamp", DateUtil.format(new Date(), "yyyy-MM-dd'T'HH:mm:ss.SSS"));

        Map<String, Object> txnInfo = new HashMap<String, Object>(1);
        txnInfo.put("txnType", "BLE");

        Map<String, Object> accountBalInfo = new HashMap<String, Object>(1);
        accountBalInfo.put("accountNo", accountNo);
        if (!CharSequenceUtil.isBlank(accountCcy)) {
            accountBalInfo.put("accountCcy", accountCcy);
        }
        //封装body
        Map<String, Object> body = new HashMap<String, Object>();
        body.put("header", header);
        body.put("txnInfo", txnInfo);
        body.put("accountBalInfo", accountBalInfo);
        return JSONUtil.parseObj(body).toString();
    }


    /**
     * 子账户新增删除 请求参数包装 VAC
     *
     */
    public static String hkProvisionReq(String orgId, SubAccountRequestVO subAccountRequestVO) {
        //封装header
        Map<String, Object> header = new HashMap<String, Object>(3);
        header.put("msgId", subAccountRequestVO.getMsgId());
        header.put("orgId", orgId);
        header.put("ctry", "HK");
        header.put("timeStamp", DateUtil.format(new Date(), "yyyy-MM-dd'T'HH:mm:ss.SSS"));

        Map<String, Object> txnInfo = new HashMap<String, Object>(1);
        txnInfo.put("txnType", "VAC");
        txnInfo.put("requestType", subAccountRequestVO.getRequestType());
        txnInfo.put("accountNo", subAccountRequestVO.getAccountNo());
        txnInfo.put("vaPrefix", subAccountRequestVO.getVaPrefix());
        txnInfo.put("corpCode", subAccountRequestVO.getCorpCode());
        txnInfo.put("vaNumber", subAccountRequestVO.getVaNumber());
        txnInfo.put("vaName", subAccountRequestVO.getVaName());


        //封装body
        Map<String, Object> body = new HashMap<String, Object>();
        body.put("header", header);
        body.put("txnInfo", txnInfo);
        return JSONUtil.parseObj(body).toString();
    }

    /**
     * Camt53 日报表 请求参数包装
     *
     * @param msgId
     * @return
     */
    public static String hkCamt53Xml(String msgId, String orgId, Camt053ReportRequestVO reportRequestVO) {
        Map<String, Object> document = new HashMap<String, Object>();

        //封装header
        Map<String, Object> header = new HashMap<String, Object>(3);
        header.put("MsgId", msgId);
        header.put("OrgId", orgId);
        header.put("Ctry", "HK");
        header.put("TimeStamp", DateUtil.format(new Date(), "yyyy-MM-dd'T'HH:mm:ss.SSS"));

        Map<String, Object> txnInfo = new HashMap<String, Object>(1);
        txnInfo.put("MessageType", "CAMT053XML");
        txnInfo.put("AccountNo", reportRequestVO.getAccountNo());
        txnInfo.put("AccountCcy", reportRequestVO.getAccountCcy());
        txnInfo.put("BizDate", reportRequestVO.getBizDate());

        Map<String, Object> body = new HashMap<String, Object>();
        body.put("Header", header);
        body.put("TxnInfo", txnInfo);
        // 文档
        document.put("Document", body);

        JSONObject jsonObject = JSONUtil.parseObj(document);

        // 转化为Xml
        return JSONUtil.toXmlStr(jsonObject);
    }

    /**
     * Camt052 日报表 请求参数包装
     *
     * @param msgId
     * @return
     */
    public static String hkCamt52Xml(String msgId, String orgId, Camt052ReportRequestVO reportRequestVO) {
        Map<String, Object> document = new HashMap<String, Object>();

        //封装header
        Map<String, Object> header = new HashMap<String, Object>(3);
        header.put("MsgId", msgId);
        header.put("OrgId", orgId);
        header.put("Ctry", "HK");
        header.put("TimeStamp", DateUtil.format(new Date(), "yyyy-MM-dd'T'HH:mm:ss.SSS"));

        Map<String, Object> txnInfo = new HashMap<String, Object>(3);
        txnInfo.put("MessageType", "CAMT052XML");
        txnInfo.put("AccountNo", reportRequestVO.getAccountNo());
        txnInfo.put("AccountCcy", reportRequestVO.getAccountCcy());

        Map<String, Object> body = new HashMap<String, Object>(2);
        body.put("Header", header);
        body.put("TxnInfo", txnInfo);
        // 文档
        document.put("Document", body);

        JSONObject jsonObject = JSONUtil.parseObj(document);

        // 转化为Xml
        return JSONUtil.toXmlStr(jsonObject);
    }
}
