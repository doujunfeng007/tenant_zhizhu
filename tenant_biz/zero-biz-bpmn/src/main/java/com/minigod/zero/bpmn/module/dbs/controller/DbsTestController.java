package com.minigod.zero.bpmn.module.dbs.controller;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.minigod.zero.bpmn.module.account.properties.AccountPdfPropertis;
import com.minigod.zero.bpmn.module.account.service.ICustomerAccOpenReportGenerateService;
import com.minigod.zero.bpmn.module.withdraw.service.DbsBankCallbackApiService;
import com.minigod.zero.bpmn.module.withdraw.service.IClientFundWithdrawInfoService;
import com.minigod.zero.dbs.bo.DbsTransaction;
import com.minigod.zero.dbs.bo.icn.IcnInfo;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.*;

/**
 * @ClassName: com.minigod.zero.bpmn.module.account.controller.TestController
 * @Description: dbs流水测试  勿删
 * @Author: linggr
 * @CreateDate: 2024/6/2 17:48
 * @Version: 1.0
 */
@RestController
@AllArgsConstructor
@RequestMapping("/dbsTest")
public class DbsTestController {
	@Autowired
	DbsBankCallbackApiService dbsBankCallbackApiService;

	@Autowired
	AccountPdfPropertis accountPdfPropertis;

	@Autowired
	ICustomerAccOpenReportGenerateService customerAccOpenReportGenerateService;

	@Autowired
	IClientFundWithdrawInfoService clientFundWithdrawInfoService;

	/**
	 * 测试回调 银行流水通知
	 */
	@PostMapping("/icnTest")
	public String icnTest(@RequestBody String icn) throws Exception {


		ExecutorService executorService = Executors.newCachedThreadPool();
//        String icn = "{\"header\":{\"msgId\":\"M270421150801193\",\"orgId\":\"HKGUOS01\",\"ctry\":\"HK\",\"timeStamp\":\"2021-04-27T16:01:13.367\"},\"txnInfo\":{\"amtDtls\":{\"txnAmt\":\"100\",\"txnCcy\":\"HKD\"},\"customerReference\":\"DICN661222227356\",\"senderParty\":{\"name\":\"TEYUGYUSG\",\"accountNo\":\"786040809\",\"senderBankId\":\"016\"},\"txnRefId\":\"T270421150801\",\"receivingParty\":{\"accountNo\":\"001242462\",\"name\":\"GUOSEN SECURITIES (HK) BROKERAGE COMPANY, LIMITED\"},\"txnType\":\"I3\",\"txnDate\":\"2024-03-07\",\"valueDt\":\"15:37:01\",\"rmtInf\":{\"purposeCode\":\"CXSALA\"}}}";
    /*String icn ="{\n" +
      "\"header\": {\n" +
      "\"msgId\": \"202522022020071428772239\",\n" +
      "\"orgId\": \"ABC01\",\n" +
      "\"timeStamp\": \"2020-08-08T15:22:02.002\",\n" +
      "\"ctry\": \"SG\"\n" +
      "},\n" +
      "\"txnInfo\": {\n" +
      "\"txnType\": \"INCOMING TT\",\n" +
      "\"customerReference\": \"CustomerRefAbcd\",\n" +
      "\"txnRefId\": \"0016IT5297952\",\n" +
      "\"txnDate\": \"2020-08-08\",\n" +
      "\"valueDt\": \"2020-08-08\",\n" +
      "\"receivingParty\": {\n" +
      "\"name\": \"ABC Company Name\",\n" +
      "\"accountNo\": \"01234567891\"\n" +
      "}, \"amtDtls\": {\n" +
      "\"txnCcy\": \"SGD\",\n" +
      "\"txnAmt\": \"10.00\"\n" +
      "},\n" +
      "\"senderParty\": {\n" +
      "\"name\": \"ABCD\",\n" +
      "\"accountNo\": \"08987877868\",\n" +
      "\"senderBankId\": \"DBSSSGXX\"\n" +
      "},\n" +
      "\"rmtInf\": {\n" +
      "\"paymentDetails \": \"TEST REMITTANCE\"\n" +
      "}\n" +
      "}\n" +
      "}";*/
		DbsTransaction<IcnInfo> infoDbsTransaction = JSONObject.parseObject(icn, new TypeReference<DbsTransaction<IcnInfo>>() {
		});
		dbsBankCallbackApiService.icn("000000", infoDbsTransaction);


		return "test";
	}

	/**
	 * 出金测试
	 */
	@PostMapping("/withdrawTest")
	public String withdrawTest(@RequestParam("applicationId") String applicationId) throws Exception {
		clientFundWithdrawInfoService.doDbsTransfer(applicationId);

		return "withdrawTest";
	}

}
