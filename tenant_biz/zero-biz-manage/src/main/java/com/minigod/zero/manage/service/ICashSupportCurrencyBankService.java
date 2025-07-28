package com.minigod.zero.manage.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.minigod.zero.core.mp.support.Query;
import com.minigod.zero.manage.entity.CashSupportCurrencyBankEntity;
import com.minigod.zero.manage.vo.CashSupportCurrencyBankVO;
import com.minigod.zero.core.tool.api.R;

/**
 * 入金银行 收款方式 币种 收款银行关联表 服务类
 *
 * @author 掌上智珠
 * @since 2023-05-18
 */
public interface ICashSupportCurrencyBankService extends IService<CashSupportCurrencyBankEntity> {

	/**
	 * 入金银行收款方式 币种 收款银行关联表分页
	 *
	 * @param query
	 * @param cashSupportCurrencyBank
	 * @return
	 */
	IPage<CashSupportCurrencyBankVO> selectCashSupportCurrencyBankPage(Query query, CashSupportCurrencyBankVO cashSupportCurrencyBank);

	/**
	 * 勾选/取消勾选 入金银行卡收款方式对应的收款银行
	 * @param depositId
	 * @param supportType
	 * @param currency
	 * @param accountType
	 * @param payeeBankId
	 * @param payeeBankDetailId
	 * @return
	 */
    R editSupportCurrencyBank(Long depositId, Integer supportType, Integer currency, Integer accountType, Long payeeBankId, Long payeeBankDetailId);

	/**
	 * 勾选/取消勾选 入金银行卡收款方式对应的默认收款银行
	 * @param depositId
	 * @param supportType
	 * @param currency
	 * @param accountType
	 * @param payeeBankId
	 * @param payeeBankDetailId
	 * @return
	 */
	R editSupportCurrencyBankDefault(Long depositId, Integer supportType, Integer currency, Integer accountType, Long payeeBankId, Long payeeBankDetailId);
}
