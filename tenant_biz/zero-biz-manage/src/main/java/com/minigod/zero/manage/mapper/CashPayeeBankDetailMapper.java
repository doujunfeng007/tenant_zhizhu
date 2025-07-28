package com.minigod.zero.manage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.manage.entity.CashPayeeBankDetailEntity;
import com.minigod.zero.manage.vo.CashPayeeBankDetailVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 付款账户信息 Mapper 接口
 *
 * @author 掌上智珠
 * @since 2023-05-18
 */
public interface CashPayeeBankDetailMapper extends BaseMapper<CashPayeeBankDetailEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param cashPayeeBankDetail
	 * @return
	 */
	List<CashPayeeBankDetailVO> selectCashPayeeBankDetailPage(IPage page, CashPayeeBankDetailVO cashPayeeBankDetail);

	/**
	 * 根据入金银行id和汇款方式获取收款银行信息
	 *
	 * @param depositId
	 * @param supportTypeList
	 * @return
	 */
	List<CashPayeeBankDetailEntity> findSupportInfoByDeposit(@Param("depositId") Long depositId, @Param("supportTypeList") String[] supportTypeList);

	/**
	 * 根据入金银行id和汇款方式查询币种列表
	 *
	 * @return
	 */
	List<String> findCurrencyByDepositIdAndSupportType(@Param("depositId") Long depositId, @Param("supportType") String supportType);

	/**
	 * 根据币种和汇款方式查询收款银行信息
	 *
	 * @param currency
	 * @param supportType
	 * @return
	 */
	CashPayeeBankDetailEntity findPayeeBankByCurrencyAndSupportType(@Param("currency") Integer currency, @Param("supportType") String supportType);

	/**
	 * 根据收款银行id查询收款银行信息
	 *
	 * @param payeeBankDetailId
	 * @return
	 */
	CashPayeeBankDetailEntity findReceivingBankById(@Param("payeeBankDetailId") Long payeeBankDetailId);

	/**
	 * 根据入金银行id和汇款方式及币种查询收款银行信息
	 *
	 * @param depositBankById
	 * @param currency
	 * @param supportType
	 * @return
	 */
	CashPayeeBankDetailEntity findPayeeBankByDepositBankById(@Param("depositBankById") Long depositBankById,
															 @Param("currency") String currency,
															 @Param("supportType") String supportType);

	/**
	 * 根揣银行id和汇款方式及币种查询付款银行信息
	 *
	 * @param withdrawalsBankById
	 * @param currency
	 * @param supportType
	 * @return
	 */
	CashPayeeBankDetailEntity findPaymentBankByWithdrawalsBankById(@Param("withdrawalsBankById") Long withdrawalsBankById,
																   @Param("currency") String currency,
																   @Param("supportType") String supportType);
}
