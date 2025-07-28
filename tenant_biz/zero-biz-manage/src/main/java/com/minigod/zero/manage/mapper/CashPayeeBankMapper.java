package com.minigod.zero.manage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.manage.entity.CashPayeeBankEntity;
import com.minigod.zero.manage.vo.CashPayeeBankVO;
import com.minigod.zero.manage.vo.DepositBankVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 收款、付款银行信息配置 Mapper 接口
 *
 * @author 掌上智珠
 * @since 2023-05-18
 */
public interface CashPayeeBankMapper extends BaseMapper<CashPayeeBankEntity> {

	/**
	 * 收款、付款银行信息配置分页查询
	 *
	 * @param page
	 * @param cashPayeeBank
	 * @return
	 */
	List<CashPayeeBankVO> selectCashPayeeBankPage(IPage page, CashPayeeBankVO cashPayeeBank);

	/**
	 * 根据收款方式和币种查询收款银行（入金）
	 *
	 * @param supportType
	 * @param currency
	 * @return
	 */
	List<Map<String, Object>> getPayeeBankList(@Param("supportType") Integer supportType,
											   @Param("currency") Integer currency);

	/**
	 * 根据付款方式和币种查询付款银行（出金）
	 *
	 * @param supportType
	 * @param currency
	 * @return
	 */
	List<Map<String, Object>> getPaymentBankList(@Param("supportType") Integer supportType,
												 @Param("currency") Integer currency);

	/**
	 * 根据银行类型、收款方式、币种查询入金银行信息
	 *
	 * @param bankType
	 * @param supportType
	 * @param currency
	 * @return
	 */
	List<DepositBankVO> getDepositBank(@Param("bankType") Integer bankType,
									   @Param("supportType") String supportType,
									   @Param("currency") Integer currency,
									   @Param("language") String language);

	/**
	 * 根据入金银行ID查询入金银行信息
	 *
	 * @param depositId
	 * @return
	 */
	DepositBankVO getDepositBankByDepositId(Long depositId);

	/**
	 * 根据收款银行配置详情ID、收款方式、币种查询入金银行信息
	 *
	 * @param payeeBankDetailId
	 * @param supportType
	 * @param currency
	 * @return
	 */
	List<DepositBankVO> getDepositBankByPayeeBankDetailId(@Param("payeeBankDetailId") Long payeeBankDetailId,
														  @Param("supportType") String supportType,
														  @Param("currency") Integer currency);


}
