package com.minigod.zero.manage.service.impl;

import com.minigod.zero.core.mp.base.BaseServiceImpl;
import com.minigod.zero.core.tool.utils.BeanUtil;
import com.minigod.zero.manage.dto.CashDepositBankSaveDTO;
import com.minigod.zero.manage.entity.CashDepositBankEntity;
import com.minigod.zero.manage.entity.CashDepositWay;
import com.minigod.zero.manage.mapper.CashDepositBankMapper;
import com.minigod.zero.manage.mapper.CashDepositWayMapper;
import com.minigod.zero.manage.mapper.CashPayeeBankMapper;
import com.minigod.zero.manage.service.ICashDepositBankService;
import com.minigod.zero.manage.vo.DepositBankVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 入金银行信息维护表 服务实现类
 *
 * @author 掌上智珠
 * @since 2023-05-18
 */
@Slf4j
@Service
public class CashDepositBankServiceImpl extends BaseServiceImpl<CashDepositBankMapper, CashDepositBankEntity> implements ICashDepositBankService {

	@Resource
	private CashPayeeBankMapper cashPayeeBankMapper;

	@Resource
	private CashDepositWayMapper cashDepositWayMapper;

	/**
	 * 保存入金银行信息
	 *
	 * @param dto
	 */
	@Override
	public void save(CashDepositBankSaveDTO dto) {
		CashDepositBankEntity depositBank = BeanUtil.copyProperties(dto, CashDepositBankEntity.class);
		this.saveOrUpdate(depositBank);
	}

	/**
	 * 据银行类型、币种、支持类型查询银行列表
	 *
	 * @param bankType
	 * @param currency
	 * @param supportType
	 * @return
	 */
	@Override
	public List<DepositBankVO> depositBankList(Integer bankType, Integer currency, String supportType, String language) {
		return cashPayeeBankMapper.getDepositBank(bankType, supportType, currency, language);
	}

	/**
	 * 根据银行类型查询银行列表
	 *
	 * @param bankType
	 * @return
	 */
	@Override
	public List<DepositBankVO> allDepositBankList(Integer bankType) {
		return baseMapper.getAllDepositBank(bankType);
	}

	/**
	 * 根据depositId查询银行信息
	 *
	 * @param depositId
	 * @return
	 */
	@Override
	public DepositBankVO getDepositBankByDepositId(Long depositId) {
		return cashPayeeBankMapper.getDepositBankByDepositId(depositId);
	}

	/**
	 * 根据payeeBankDetailId、币种、支持类型查询银行信息
	 *
	 * @param payeeBankDetailId
	 * @param supportType
	 * @param currency
	 * @return
	 */
	@Override
	public List<DepositBankVO> getDepositBankByPayeeBankDetailId(Long payeeBankDetailId, String supportType, Integer currency) {
		return cashPayeeBankMapper.getDepositBankByPayeeBankDetailId(payeeBankDetailId, supportType, currency);
	}

	/**
	 * 根据币种、银行类型查询支持方式
	 *
	 * @param currency
	 * @param bankType
	 * @return
	 */
	@Override
	public List<CashDepositWay> selectDepositWay(Integer currency, Integer bankType) {
		return cashDepositWayMapper.selectDepositWay(currency, bankType);
	}
}
