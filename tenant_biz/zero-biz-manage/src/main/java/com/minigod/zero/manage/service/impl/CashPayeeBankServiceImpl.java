package com.minigod.zero.manage.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.core.mp.base.BaseServiceImpl;
import com.minigod.zero.manage.entity.CashPayeeBankEntity;
import com.minigod.zero.manage.mapper.CashPayeeBankMapper;
import com.minigod.zero.manage.service.ICashPayeeBankService;
import com.minigod.zero.manage.vo.CashPayeeBankVO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 收款、付款银行信息配置服务实现类
 *
 * @author 掌上智珠
 * @since 2023-05-18
 */
@Service
public class CashPayeeBankServiceImpl extends BaseServiceImpl<CashPayeeBankMapper, CashPayeeBankEntity> implements ICashPayeeBankService {
	/**
	 * 收款、付款银行信息配置分页查询
	 *
	 * @param page
	 * @param cashPayeeBank
	 * @return
	 */
	@Override
	public IPage<CashPayeeBankVO> selectCashPayeeBankPage(IPage<CashPayeeBankVO> page, CashPayeeBankVO cashPayeeBank) {
		return page.setRecords(baseMapper.selectCashPayeeBankPage(page, cashPayeeBank));
	}

	/**
	 * 根据收款方式和币种查询收款银行
	 *
	 * @param supportType
	 * @param currency
	 * @return
	 */
	@Override
	public List<Map<String, Object>> getPayeeBankList(Integer supportType, Integer currency) {
		return baseMapper.getPayeeBankList(supportType, currency);
	}

	/**
	 * 根据付款方式和币种查询付款银行
	 *
	 * @param supportType
	 * @param currency
	 * @return
	 */
	@Override
	public List<Map<String, Object>> getPaymentBankList(Integer supportType, Integer currency) {
		return baseMapper.getPaymentBankList(supportType, currency);
	}

	/**
	 * 保存收款、付款银行信息配置
	 *
	 * @param cashPayeeBank
	 */
	@Override
	public boolean saveCashPayeeBank(CashPayeeBankEntity cashPayeeBank) {
		return this.save(cashPayeeBank);
	}

	/**
	 * 更新收款、付款银行信息配置
	 *
	 * @param cashPayeeBank
	 */
	@Override
	public boolean updateCashPayeeBank(CashPayeeBankEntity cashPayeeBank) {
		return this.updateById(cashPayeeBank);
	}
}
