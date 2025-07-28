package com.minigod.zero.manage.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.minigod.zero.core.mp.support.Query;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.manage.entity.CashWithdrawalsSupportCurrencyBankEntity;
import com.minigod.zero.manage.vo.CashWithdrawalsSupportCurrencyBankVO;

/**
 * 出金银行、付款方式、币种、付款银行关联表 服务类
 *
 * @author eric
 * @since 2024-10-18 17:51:06
 */
public interface ICashWithdrawalsSupportCurrencyBankService extends IService<CashWithdrawalsSupportCurrencyBankEntity> {
	/**
	 * 出金银行、付款方式、币种、付款银行关联表分页查询
	 *
	 * @param query
	 * @param cashWithdrawalsSupportCurrencyBankVO
	 * @return
	 */
	IPage<CashWithdrawalsSupportCurrencyBankVO> selectWithdrawalsSupportCurrencyBankPage(Query query,
																						 CashWithdrawalsSupportCurrencyBankVO cashWithdrawalsSupportCurrencyBankVO);

	/**
	 * 勾选/取消勾选 入金银行卡收款方式对应的收款银行
	 * @param withdrawalsId
	 * @param supportType
	 * @param currency
	 * @param accountType
	 * @param paymentBankId
	 * @param paymentBankDetailId
	 * @return
	 */
	R editCashWithdrawalsSupportCurrencyBank(Long withdrawalsId, Integer supportType, Integer currency, Integer accountType, Long paymentBankId, Long paymentBankDetailId);

	/**
	 * 勾选/取消勾选 出金银行卡付款方式对应的默认付款银行
	 * @param withdrawalsId
	 * @param supportType
	 * @param currency
	 * @param accountType
	 * @param paymentBankId
	 * @param paymentBankDetailId
	 * @return
	 */
	R editCashWithdrawalsSupportCurrencyBankDefault(Long withdrawalsId, Integer supportType, Integer currency, Integer accountType, Long paymentBankId, Long paymentBankDetailId);
}
