package com.minigod.zero.manage.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.minigod.zero.manage.entity.CashPayeeBankEntity;
import com.minigod.zero.manage.vo.CashPayeeBankVO;

import java.util.List;
import java.util.Map;

/**
 * 收款、付款银行信息配置 服务类
 *
 * @author 掌上智珠
 * @since 2023-05-18
 */
public interface ICashPayeeBankService extends IService<CashPayeeBankEntity> {

	/**
	 * 收款、付款银行信息配置分页查询
	 *
	 * @param page
	 * @param cashPayeeBank
	 * @return
	 */
	IPage<CashPayeeBankVO> selectCashPayeeBankPage(IPage<CashPayeeBankVO> page, CashPayeeBankVO cashPayeeBank);

	/**
	 * 根据收款方式和币种查询收款银行
	 *
	 * @param supportType
	 * @param currency
	 * @return
	 */
	List<Map<String, Object>> getPayeeBankList(Integer supportType, Integer currency);

	/**
	 * 根据付款方式和币种查询付款银行
	 *
	 * @param supportType
	 * @param currency
	 * @return
	 */
	List<Map<String, Object>> getPaymentBankList(Integer supportType, Integer currency);

	/**
	 * 保存收款、付款银行信息配置
	 *
	 * @param cashPayeeBank
	 */
	boolean saveCashPayeeBank(CashPayeeBankEntity cashPayeeBank);

	/**
	 * 更新收款、付款银行信息配置
	 *
	 * @param cashPayeeBank
	 */
	boolean updateCashPayeeBank(CashPayeeBankEntity cashPayeeBank);

}
