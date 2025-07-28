package com.minigod.zero.bpmn.module.withdraw.service;

import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.dbs.bo.DbsBaseRequestVO;
import com.minigod.zero.dbs.bo.FundTransferEntity;
import com.minigod.zero.dbs.enums.BankApiFuncTypeEnum;
import com.minigod.zero.dbs.protocol.AccountBalResponse;
import com.minigod.zero.dbs.vo.TseTransactionEnquiryRequestVO;
import com.minigod.zero.bpmn.module.withdraw.enums.SystemCommonEnum;

import java.util.List;

/**
 * Dbs业务处理
 *
 * @author chenyu
 * @title DbsFundBusinessService
 * @date 2023-04-13 7:33
 * @description
 */
public interface DbsFundBusinessService {


    /**
     * 查询银行账户（BLE）
     *
     * @param accountNo  银行账号
     * @param accountCcy 币种
     * @return
     */
    AccountBalResponse sendBleQuery(String tenantId, String accountNo, String accountCcy);

    /**
     * 查询银行账户综合余额（BLE）
     *
     * @param accountNo 银行账号
     * @return
     */
    List<AccountBalResponse> sendBleQuery(String tenantId, String accountNo);

    /**
     * FPS Id 信息查询
     *
     * @param fpsIdQueryBo
     * @return
     */
    R<?> sendFpsIdQuery(BankApiFuncTypeEnum funcTypeEnum, FundTransferEntity fpsIdQueryBo);

    /**
     * 银行转账入口(FPS PPP GPP ACT CHATS\RTGS TT)
     *
     * @param fundWithdrawInfoBo
     * @return
     */
    <T extends DbsBaseRequestVO> SystemCommonEnum.BankStateTypeEnum sendDbsCommonRemitFund(BankApiFuncTypeEnum funcTypeEnum, SystemCommonEnum.PayTypeEnum payTypeEnum, T fundWithdrawInfoBo);

    /**
     * 查询交易状态信息（TSE）
     * request
     *
     * @return
     */
    R sendTseQuery(TseTransactionEnquiryRequestVO request);


}
