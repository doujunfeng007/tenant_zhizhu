package com.minigod.zero.bpmn.module.bank.feign;

import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.dbs.bo.DbsIccBankFlowEntity;
import com.minigod.zero.dbs.bo.FundTransferEntity;
import com.minigod.zero.dbs.service.DbsApiBusinessService;
import com.minigod.zero.dbs.vo.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @ClassName: DbsService
 * @Description: 请求网关服务接口客户端 ,继承自网关服务端接口
 * @Author chenyu
 * @Date 2024/3/21
 * @Version 1.0
 */
@FeignClient(value = "zero-biz-dbs"
        //通过拦截器找到对应租户的DBS网关地址
        //configuration = DbsGatewayFeignRequestInterceptor.class
)
public interface DbsService extends DbsApiBusinessService {

	public static String path = "/bank/proxy/api";

    @Override
    @PostMapping(path+EDDA_SETUP)
    R<String> sendEDDAInitiation(EddaInfoRequestVO eddaInfo);


    @Override
    @PostMapping(path+FPS_GPC)
    R<String> sendFpsGpcFund(FpsTransactionRequestVO fpsTransaction);

    @Override
    @PostMapping(path+FPS_GPP)
    R<String> sendFpsGppFund(FundTransferEntity fundWithdrawApplyEntity);

    /**
     * 银行自动转账(Fps PPP)
     *
     * @param fundWithdrawApplyEntity
     * @return
     */
    @Override
    @PostMapping(path+FPS_PPP)
    R<String> sendFpsPPPFund(FundTransferEntity fundWithdrawApplyEntity);

    /**
     * 银行转账(Dbs Act 同行转账)
     *
     * @param fundWithdrawApplyEntity
     * @return
     */
    @Override
    @PostMapping(path+ACT)
    R<String> sendActFund(FundTransferEntity fundWithdrawApplyEntity);

    /**
     * 银行转账(Dbs RFD 退款)
     *
     * @param fundWithdrawApplyEntity
     * @return
     */
    @Override
    @PostMapping(path+RFD)
    R<String> sendRFDFund(FundTransferEntity fundWithdrawApplyEntity);


    /**
     * fpsid查询
     *
     * @param fundWithdrawApplyEntity
     * @return
     */
    @Override
    @PostMapping(path+FPS_ID_ENQUIRY)
    R<String> sendFpsIdEnquiryFund(FundTransferEntity fundWithdrawApplyEntity);

    /**
     * 银行转账(Dbs CHATS\RTGS 跨行转账)
     *
     * @param fundWithdrawApplyEntity
     * @return
     */
    @Override
    @PostMapping(path+CHATS)
    R<String> sendChatsFund(FundTransferEntity fundWithdrawApplyEntity);

    /**
     * 银行转账(Dbs TT 海外转账)
     *
     * @param fundWithdrawApplyEntity
     * @return
     */
    @Override
    @PostMapping(path+TT)
    R<String> sendTTFund(FundTransferEntity fundWithdrawApplyEntity);


    /**
     * 查询银行账户（BLE）
     * <p>
     * accountNo  银行账号
     * accountCcy 币种
     *
     * @return
     */
    @Override
    @PostMapping(path+BLE)
    R<String> sendBleQuery(AccountBalanceRequestVO accountBalanceRequestVo);

    /**
     * 子账户增删
     */
    @Override
    @PostMapping(path+VAC)
    R<String> sendProvisionQuery(SubAccountRequestVO subAccountRequestVO);

    /**
     * ICC流水请求星展 ACCOUNT RANGE ENQUIRY MESSAGE
     */
    @Override
    @PostMapping(path+ICC)
    R<String> sendICCARE(DbsIccBankFlowEntity dbsIccBankFlowEntity);

    /**
     * edda enquiry 授权查看请求
     */
    @Override
    @PostMapping(path+EDDA_ENQ)
    R<String> sendEDDAEnquiry(EddaTransactionEnquiryRequestVO eddaTransaction);


    /**
     * Camt53 日报文查看请求
     */
    @Override
    @PostMapping(path+CAMT53)
    R<String> sendCamt053(Camt053ReportRequestVO reportRequestVO);

    /**
     * Camt052 小时报文查看请求
     */
    @Override
    @PostMapping(path+CAMT52)
    R<String> sendCamt052(Camt052ReportRequestVO reportRequestVO);

    /**
     * Tse 查询汇款交易状态
     */
    @Override
    @PostMapping(path+TSE)
    R<String> sendTseEnquiry(TseTransactionEnquiryRequestVO requestVO);
}
