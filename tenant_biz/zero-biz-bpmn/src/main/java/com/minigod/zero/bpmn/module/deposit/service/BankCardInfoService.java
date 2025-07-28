package com.minigod.zero.bpmn.module.deposit.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.minigod.zero.bpmn.module.deposit.bo.AccountDepositBankBo;
import com.minigod.zero.bpmn.module.deposit.bo.BankCardReviewBo;
import com.minigod.zero.bpmn.module.deposit.entity.BankCardInfo;
import com.minigod.zero.bpmn.module.deposit.entity.SecDepositFundsEntity;
import com.minigod.zero.bpmn.module.deposit.vo.BankCardInfoVO;
import com.minigod.zero.bpmn.module.deposit.vo.BankCardVO;

import java.util.List;

/**
 * @ClassName: BankCardInfoService
 * @Description: ${description}
 * @Author chenyu
 * @Date 2024/3/11
 * @Version 1.0
 */
public interface BankCardInfoService extends IService<BankCardInfo> {


    int batchInsert(List<BankCardInfo> list);

    List<BankCardInfoVO> selectBankInfoList(AccountDepositBankBo params);

    void validateClientBankNo(String clientId, String remittanceBankAccount);

	BankCardInfo checkClientBankNo(String clientId, String remittanceBankAccount);

    void submitBusinessCheck(BankCardReviewBo bankCardReviewBo);

    IPage<BankCardInfo> selectBankCardInfoPage(IPage page);

    BankCardInfo selectBankCardInfoById(Long id);

	void depositBankCardBinding(SecDepositFundsEntity depositFunds);

	List<BankCardVO> getCustomerBankCard(Long custId,String bankId,String bankCode);
}
