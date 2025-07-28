package com.minigod.zero.bpmn.module.account;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.minigod.zero.bpmn.ZeroBpmnApplication;
import com.minigod.zero.bpmn.module.account.enums.AccountPdfEnum;
import com.minigod.zero.bpmn.module.account.properties.AccountPdfPropertis;
import com.minigod.zero.bpmn.module.account.service.ICustomerAccOpenReportGenerateService;
import com.minigod.zero.bpmn.module.deposit.service.ISecDepositAccountBankService;
import com.minigod.zero.bpmn.module.withdraw.service.DbsBankCallbackApiService;
import com.minigod.zero.bpmn.utils.ImageUtils;
import com.minigod.zero.ca.bo.sz.response.RandomNumberResponse;
import com.minigod.zero.ca.bo.sz.response.ResultSzCa;
import com.minigod.zero.ca.feign.SzCaService;
import com.minigod.zero.core.test.ZeroBootTest;
import com.minigod.zero.core.test.ZeroSpringExtension;
import com.minigod.zero.dbs.bo.DbsTransaction;
import com.minigod.zero.dbs.bo.icn.IcnInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;


/**
 * @ClassName: AccountTest
 * @Description:
 * @Author chenyu
 * @Date 2024/2/21
 * @Version 1.0
 */
@ExtendWith(ZeroSpringExtension.class)
@SpringBootTest(classes = ZeroBpmnApplication.class)
@ZeroBootTest(appName = "zero-biz-bpmn",profile="sit", enableLoader = true)
public class AccountTest {
    @Autowired
    SzCaService szCaService;

    @Autowired
    DbsBankCallbackApiService dbsBankCallbackApiService;

    @Autowired
    AccountPdfPropertis accountPdfPropertis;
    @Autowired
    ICustomerAccOpenReportGenerateService customerAccOpenReportGenerateService;

    @Test
    public void getToken() {
        ResultSzCa<String> resultSzCa =  szCaService.getToken();
        System.out.println(resultSzCa.toString());
    }

    @Test
    public void test1() {
        customerAccOpenReportGenerateService.generateReport("2024041600000101000002", AccountPdfEnum.ACCOUNT_OPEN_REPORT_USER_FORM_REPORT);
    }
    @Test
    public void getRandomNumber() {
        String token = "eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNzA4NDk3MDAwLCJpc3MiOiJzemNhX2V2aWRlbmNlYXV0aCIsImV4cCI6MTcwODUwNDMyMCwiY2xpZW50X2lkIjoidGVzdF96eWYifQ.rwZGdquNugcw3iClEPUcMd2ZQ_5cAzbfJOWJRz3GVoFnSxQB20thNhe7UUBAZDvOwFwzit7FBAiPk0gJz1KISg";
        ResultSzCa<RandomNumberResponse> resultSzCa =   szCaService.getRandomNumber(token);
        System.out.println(resultSzCa.toString());
    }

    @Autowired
    private ISecDepositAccountBankService iSecDepositAccountBankService;

    @Test
    public void test() {
       List list = iSecDepositAccountBankService.findAccountDepositBankInfo(10,1);
    }


    public static void main(String[] args) {
        System.out.println(ImageUtils.loadImgBase64ByUrl("http://cloud-api.zsdp.zszhizhu.com:9000/zero-public/upload/20240229/135f4c22372482faf79f074cda7cb7b3.jpg"));
    }

    @Test
    public void dbsIcn() throws ExecutionException, InterruptedException {
     ExecutorService executorService =  Executors.newCachedThreadPool();
//        String icn = "{\"header\":{\"msgId\":\"M270421150801193\",\"orgId\":\"HKGUOS01\",\"ctry\":\"HK\",\"timeStamp\":\"2021-04-27T16:01:13.367\"},\"txnInfo\":{\"amtDtls\":{\"txnAmt\":\"100\",\"txnCcy\":\"HKD\"},\"customerReference\":\"DICN661222227356\",\"senderParty\":{\"name\":\"TEYUGYUSG\",\"accountNo\":\"786040809\",\"senderBankId\":\"016\"},\"txnRefId\":\"T270421150801\",\"receivingParty\":{\"accountNo\":\"001242462\",\"name\":\"GUOSEN SECURITIES (HK) BROKERAGE COMPANY, LIMITED\"},\"txnType\":\"I3\",\"txnDate\":\"2024-03-07\",\"valueDt\":\"15:37:01\",\"rmtInf\":{\"purposeCode\":\"CXSALA\"}}}";
        String icn ="{\n" +
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
                "}";
        DbsTransaction<IcnInfo> infoDbsTransaction =   JSONObject.parseObject(icn,new TypeReference<DbsTransaction<IcnInfo>>(){});
        CountDownLatch countDownLatch = new CountDownLatch(10);
        List<FutureTask<Boolean>> list = new ArrayList<>();
        for (int i = 0; i <= 9; i++) {
            FutureTask<Boolean> futureTask = (FutureTask<Boolean>) executorService.submit(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    countDownLatch.await();
                    dbsBankCallbackApiService.icn("000000",infoDbsTransaction);
                    return true;
                }
            });
            list.add(futureTask);
            countDownLatch.countDown();
        }
        for (FutureTask<Boolean> futureTask :list){
            futureTask.get();
        }
    }
}
