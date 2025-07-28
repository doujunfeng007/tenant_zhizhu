package com.minigod.zero.bpmn.module.deposit.service;

import java.util.List;

import com.minigod.zero.bpmn.module.deposit.bo.BankCardReviewBo;
import com.minigod.zero.bpmn.module.deposit.entity.BankCardReviewInfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @ClassName: BankCardReviewInfoService
 * @Description: ${description}
 * @Author chenyu
 * @Date 2024/3/13
 * @Version 1.0
 */
public interface BankCardReviewInfoService extends IService<BankCardReviewInfo> {


    int batchInsert(List<BankCardReviewInfo> list);

    /**
     * 提交银行卡审批
     * @param bankCardReviewBo
     * @return
     */
    String submitBankCardReview(BankCardReviewBo bankCardReviewBo);

    /**
     * 执行绑定银行卡业务
     * @param applicationId
     */
    void bindBankInfo(String applicationId);

    /**
     * 执行解绑银行卡业务
     * @param applicationId
     */
    void unbindBankInfo(String applicationId);

    /**
     * 执行修改银行卡业务
     * @param applicationId
     */
    void editBankInfo(String applicationId);
}
