package com.minigod.zero.bpm.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.minigod.zero.bpm.vo.request.DepositAccountBankCancelRepVo;
import com.minigod.zero.bpm.entity.CashDepositAccountBankEntity;
import com.minigod.zero.bpm.vo.CashDepositAccountBankVO;
import com.minigod.zero.bpm.vo.request.SecDepositBankVo;
import com.minigod.zero.core.tool.api.R;

/**
 * 客户银行卡记录 服务类
 *
 * @author 掌上智珠
 * @since 2023-05-18
 */
public interface ICashDepositAccountBankService extends IService<CashDepositAccountBankEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param cashDepositAccountBank
	 * @return
	 */
	IPage<CashDepositAccountBankVO> selectCashDepositAccountBankPage(IPage<CashDepositAccountBankVO> page, CashDepositAccountBankVO cashDepositAccountBank);

	/**
	 * 客户取消汇款银行卡绑定
	 * @param custId
	 * @param vo
	 * @return
	 */
    R depositAccountBankCancel(Long custId, DepositAccountBankCancelRepVo vo);

	/**
	 * 获取入金成功汇款银行卡信息
	 * @param params
	 * @return
	 */
	R depositBankInfo(SecDepositBankVo params);

}
