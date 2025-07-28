package com.minigod.zero.manage.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.minigod.zero.manage.entity.CashPayeeBankDetailEntity;
import com.minigod.zero.manage.vo.ReceivingBankVO;
import com.minigod.zero.manage.vo.CashPayeeBankDetailVO;

import java.util.List;

/**
 * 收款、付款账户详情信息 服务类
 *
 * @author 掌上智珠
 * @since 2023-05-18
 */
public interface ICashPayeeBankDetailService extends IService<CashPayeeBankDetailEntity> {

	/**
	 * 收款、付款账户详情信息分页查询
	 *
	 * @param page
	 * @param cashPayeeBankDetail
	 * @return
	 */
	IPage<CashPayeeBankDetailVO> selectCashPayeeBankDetailPage(IPage<CashPayeeBankDetailVO> page, CashPayeeBankDetailVO cashPayeeBankDetail);

	/**
	 * 根据银行获取转账类型信息
	 *
	 * @param depositId
	 * @param supportTypeList
	 * @return
	 */
	List<CashPayeeBankDetailEntity> findSupportInfoByDeposit(Long depositId, String[] supportTypeList);

	/**
	 * 根据入金银行id和汇款方式查询币种
	 *
	 * @return
	 */
	List<String> findCurrencyByDepositIdAndSupportType(Long depositId, String supportType);

	/**
	 * 根据币种和汇款方式查询收款银行信息
	 *
	 * @param currency
	 * @param supportType
	 * @return
	 */
	ReceivingBankVO findReceivingBank(Integer currency, String supportType);

	/**
	 * 根据收款银行详情id查询收款银行信息
	 *
	 * @param payeeBankDetailId
	 * @return
	 */
	ReceivingBankVO findReceivingBankById(Long payeeBankDetailId);

	/**
	 * 根据入金银行id和汇款方式、币种查询收款银行信息
	 *
	 * @param depositBankById
	 * @param currency
	 * @param supportType
	 * @return
	 */
	ReceivingBankVO findReceivingBankByDepositBankById(Long depositBankById, String currency, String supportType);

	/**
	 * 根据出金银行id和汇款方式、币种查询付款银行信息
	 *
	 * @param withdrawalsBankById
	 * @param currency
	 * @param supportType
	 * @return
	 */
	ReceivingBankVO findPaymentBankByWithdrawalsBankById(Long withdrawalsBankById, String currency, String supportType);


	/**
	 * 更新收、付款账户信息
	 *
	 * @param cashPayeeBankDetail
	 * @return
	 */
	boolean updateCashPayeeBankDetail(CashPayeeBankDetailEntity cashPayeeBankDetail);

	/**
	 * 新增收、付款账户信息
	 *
	 * @param cashPayeeBankDetail
	 * @return
	 */
	boolean saveCashPayeeBankDetail(CashPayeeBankDetailEntity cashPayeeBankDetail);

	/**
	 * 删除收、付款账户信息
	 *
	 * @param ids
	 * @return
	 */
	boolean deleteCashPayeeBankDetail(List<Long> ids);
}
