package com.minigod.zero.manage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.minigod.zero.manage.entity.CashDepositBankEntity;
import com.minigod.zero.manage.entity.CashDepositWay;
import com.minigod.zero.manage.vo.DepositBankVO;
import com.minigod.zero.manage.dto.CashDepositBankSaveDTO;

import java.util.List;

/**
 * 入金银行信息维护表 服务类
 */
public interface ICashDepositBankService extends IService<CashDepositBankEntity> {

	/**
	 * 保存入金银行信息
	 *
	 * @param dto
	 */
	void save(CashDepositBankSaveDTO dto);

	/**
	 * 根据银行类型、币种、支持类型查询银行列表
	 *
	 * @param bankType
	 * @param currency
	 * @param supportType
	 * @return
	 */
	List<DepositBankVO> depositBankList(Integer bankType, Integer currency, String supportType, String language);

	/**
	 * 根据银行类型查询银行列表
	 *
	 * @param bankType
	 * @return
	 */
	List<DepositBankVO> allDepositBankList(Integer bankType);

	/**
	 * 根据depositId查询银行信息
	 *
	 * @param depositId
	 * @return
	 */
	DepositBankVO getDepositBankByDepositId(Long depositId);

	/**
	 * 根据payeeBankDetailId、币种、支持类型查询银行信息
	 *
	 * @param payeeBankDetailId
	 * @param supportType
	 * @param currency
	 * @return
	 */
	List<DepositBankVO> getDepositBankByPayeeBankDetailId(Long payeeBankDetailId,
														  String supportType,
														  Integer currency);

	/**
	 * 根据币种、银行类型查询支持方式
	 *
	 * @param currency
	 * @param bankType
	 * @return
	 */
	List<CashDepositWay> selectDepositWay(Integer currency, Integer bankType);
}
