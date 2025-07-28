package com.minigod.zero.dbs.impl;

import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.dbs.feign.DbsCallbackClient;
import com.minigod.zero.dbs.service.DbsCallBackService;
import com.minigod.zero.dbs.util.DbsCommManageUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

/**
 * @ClassName: DbsCallBackServiceImpl
 * @Description: DBS回调接口，解密并请求业务系统
 * @Author chenyu
 * @Date 2024/3/21
 * @Version 1.0
 */
@Slf4j
@Service("dbsCallBackService")
@AllArgsConstructor
public class DbsCallBackServiceImpl implements DbsCallBackService {
    private final DbsCallbackClient dbsCallbackClient;

    /**
     * icn
     *
     * @param text 银行报文信息
     */
    @Override
    public R<String> icn(String text) {
        log.info("DBS API ICC push data：" + text);
        // 验签以及解密银行提交的信息
        String business = "ICN_ACK";
        //String jsonData = DbsCommManageUtils.decrypt(text, business);
        String jsonData = DbsCommManageUtils.iccDecrypt(text, business);
        //String jsonData = text;
		log.info("DBS API ICC decrypt jsonData：" + jsonData);
        // 测试
//        jsonData = "{\"header\":{\"msgId\":\"M270421150801193\",\"orgId\":\"HKGUOS01\",\"ctry\":\"HK\",\"timeStamp\":\"2021-04-27T16:01:13.367\"},\"txnInfo\":{\"amtDtls\":{\"txnAmt\":\"100\",\"txnCcy\":\"HKD\"},\"customerReference\":\"DICN661222227356\",\"senderParty\":{\"name\":\"TEYUGYUSG\",\"accountNo\":\"786040809\",\"senderBankId\":\"016\"},\"txnRefId\":\"T270421150801\",\"receivingParty\":{\"accountNo\":\"001242462\",\"name\":\"GUOSEN SECURITIES (HK) BROKERAGE COMPANY, LIMITED\"},\"txnType\":\"I3\",\"txnDate\":\"2021-04-27\",\"valueDt\":\"2021-04-27\",\"rmtInf\":{\"purposeCode\":\"CXSALA\"}}}";

        if (StringUtils.isBlank(jsonData)) {
            return R.fail(-1, "dbs icn decrypt body is null.");
        }

		R icn = dbsCallbackClient.icn(jsonData);

		log.info("DBS API ICC BPMN R：" + icn.toString());

		return icn;
    }

    /**
     * edda-Ack2
     *
     * @param text 银行报文信息
     */
    @Override
    public R<String> eddaAck2(String text) {
        log.info("DBS API EDDA_ACK2 push data：" + text);
        // 验签以及解密银行提交的信息
        String business = "EDDA_ACK2";
        String jsonData = DbsCommManageUtils.decrypt(text, business);
		log.info("DBS API EDDA_ACK2 decrypt jsonData：" + jsonData);
        // 测试
//        jsonData = "{\"header\":{\"msgId\":\"2021020211218100026\",\"orgId\":\"HKGYSSL\",\"timeStamp\":\"2021-02-02T11:35:26.150\",\"ctry\":\"HK\"},\"txnResponse\":{\"responseType\":\"ACK2\",\"txnRefId\":\"HKEDDA21020200000001\",\"txnStatus\":\"ACCT\",\"mandateId\":\"016/MNDTEDDA21020200000001\",\"ddaRef\":\"EDDA202102021121810002\",\"mandateType\":\"DDMP\",\"seqType\":\"RCUR\",\"frqcyType\":\"ADHO\",\"countPerPeriod\":\"1\",\"effDate\":\"2021-02-02\",\"creditor\":{\"name\":\"Glory Sun Security\",\"accountNo\":\"0720064784\",\"accountCcy\":null},\"debtor\":{\"name\":\"LEETSZ HANG\",\"proxyType\":\"B\",\"proxyValue\":\"09354164784\",\"bankId\":\"016\",\"ultimateName\":\"User Name 1\",\"corpCustomer\":null,\"prvtCustomer\":[{\"prvtCustomerId\":\"Z6598170\",\"prvtCustomerIdType\":\"NIDN\"}]}}}";
        if (StringUtils.isBlank(jsonData)) {
            return R.fail(-1, "dbs edda decrypt body is null.");
        }
        return dbsCallbackClient.eddaAck2(jsonData);

    }

    /**
     * remit-Ack2 ACK3
     *
     * @param text 银行报文信息
     */
    @Override
    public R<String> remitAck(String text) {
        log.info("DBS API remitAck push data：" + text);
        // 验签以及解密银行提交的信息
        String business = "REMIT_ACK";
        String jsonData = DbsCommManageUtils.decrypt(text, business);
		log.info("DBS API remitAck decrypt jsonData：" + jsonData);

        // 测试
//        jsonData = "{\"header\":{\"msgId\":\"M270421161751469\",\"orgId\":\"HKGUOS01\",\"timeStamp\":\"2021-04-27T16:19:39.708\",\"ctry\":\"HK\"},\"txnInfo\":{\"txnType\":\"INCOMING ACT\",\"customerReference\":\"20210427161902\",\"txnRefId\":\"T270421161751\",\"txnDate\":\"2021-04-27\",\"valueDt\":\"2021-04-27\",\"receivingParty\":{\"name\":\"GUOSEN SECURITIES (HK) BROKERAGE COMPANY, LIMITED\",\"accountNo\":\"001242462\"},\"amtDtls\":{\"txnCcy\":\"HKD\",\"txnAmt\":100.00},\"senderParty\":{\"name\":\"Testing\",\"accountNo\":\"000804344\"}}}";
        if (StringUtils.isBlank(jsonData)) {
            return R.fail(-1, "dbs remitAck decrypt body is null.");
        }


        return dbsCallbackClient.remitAck(jsonData);


    }

    /**
     * edda-Ack2
     *
     * @param text 银行报文信息
     */
    @Override
    public R<String> idn(String text) {
        log.info("DBS API idn push data：" + text);
        // 验签以及解密银行提交的信息
        String business = "IDN";
        String jsonData = DbsCommManageUtils.decrypt(text, business);

        // 测试
//        jsonData = "{\"header\":{\"msgId\":\"M270421161751469\",\"orgId\":\"HKGUOS01\",\"timeStamp\":\"2021-04-27T16:19:39.708\",\"ctry\":\"HK\"},\"txnInfo\":{\"txnType\":\"INCOMING ACT\",\"customerReference\":\"20210427161902\",\"txnRefId\":\"T270421161751\",\"txnDate\":\"2021-04-27\",\"valueDt\":\"2021-04-27\",\"receivingParty\":{\"name\":\"GUOSEN SECURITIES (HK) BROKERAGE COMPANY, LIMITED\",\"accountNo\":\"001242462\"},\"amtDtls\":{\"txnCcy\":\"HKD\",\"txnAmt\":100.00},\"senderParty\":{\"name\":\"Testing\",\"accountNo\":\"000804344\"}}}";
        if (StringUtils.isBlank(jsonData)) {
            return R.fail(-1, "dbs idn decrypt body is null.");
        }

        return dbsCallbackClient.idn(jsonData);

    }
}
