package com.minigod.zero.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.core.mp.base.BaseServiceImpl;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.utils.CollectionUtil;
import com.minigod.zero.core.tool.utils.StringUtil;
import com.minigod.zero.core.tool.utils.WebUtil;
import com.minigod.zero.manage.dto.CashWithdrawalsBankSaveDTO;
import com.minigod.zero.manage.entity.CashWithdrawalsBankEntity;
import com.minigod.zero.manage.entity.CashWithdrawalsSupportCurrencyBankEntity;
import com.minigod.zero.manage.mapper.CashWithdrawalsBankMapper;
import com.minigod.zero.manage.mapper.CashWithdrawalsSupportCurrencyBankMapper;
import com.minigod.zero.manage.service.ICashWithdrawalsBankService;
import com.minigod.zero.manage.vo.BankSwiftCodeVO;
import com.minigod.zero.manage.vo.CBank;
import com.minigod.zero.manage.vo.CashWithdrawalsBankVO;
import com.minigod.zero.core.tool.utils.BeanUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 出金银行卡配置表 服务实现类
 *
 * @author 掌上智珠
 * @since 2023-05-18
 */
@Service
public class CashWithdrawalsBankServiceImpl extends BaseServiceImpl<CashWithdrawalsBankMapper, CashWithdrawalsBankEntity> implements ICashWithdrawalsBankService {

	private final CashWithdrawalsSupportCurrencyBankMapper cashWithdrawalsSupportCurrencyBankMapper;

	public CashWithdrawalsBankServiceImpl(CashWithdrawalsSupportCurrencyBankMapper cashWithdrawalsSupportCurrencyBankMapper) {
		this.cashWithdrawalsSupportCurrencyBankMapper = cashWithdrawalsSupportCurrencyBankMapper;
	}

	@Override
	public IPage<CashWithdrawalsBankVO> selectCashWithdrawalsBankPage(IPage<CashWithdrawalsBankVO> page, CashWithdrawalsBankVO cashWithdrawalsBank) {
		return page.setRecords(baseMapper.selectCashWithdrawalsBankPage(page, cashWithdrawalsBank));
	}

	@Override
	public void save(CashWithdrawalsBankSaveDTO dto) {
		CashWithdrawalsBankEntity entity = null;
		if (dto.getId() != null) {
			entity = this.getById(dto.getId());
		}
		if (entity == null) {
			entity = BeanUtil.copyProperties(dto, CashWithdrawalsBankEntity.class);
		} else {
			BeanUtil.copyNonNull(dto, entity);
		}
		this.saveOrUpdate(entity);
	}

	@Override
	public R withdrawBankList(Integer bankType) {
		if (bankType == null) {
			return R.fail("bankType 不能为空");
		}
		LambdaQueryWrapper<CashWithdrawalsBankEntity> queryWrapper = new LambdaQueryWrapper();
		queryWrapper.eq(CashWithdrawalsBankEntity::getBankType, bankType);
		queryWrapper.eq(CashWithdrawalsBankEntity::getIsShow, 1);
		List<CashWithdrawalsBankEntity> list = baseMapper.selectList(queryWrapper);
		if (CollectionUtil.isEmpty(list)) {
			return R.success();
		}

		//过滤在cash_withdrawals_support_currency_bank没有配置关联付款银行的数据
		LambdaQueryWrapper<CashWithdrawalsSupportCurrencyBankEntity> supportQuery = new LambdaQueryWrapper<>();
		supportQuery.select(CashWithdrawalsSupportCurrencyBankEntity::getWithdrawalsId);
		List<CashWithdrawalsSupportCurrencyBankEntity> supportList = cashWithdrawalsSupportCurrencyBankMapper.selectList(supportQuery);
		Set<Long> withdrawalsIdSet = new HashSet<>();
		supportList.forEach(support -> withdrawalsIdSet.add(support.getWithdrawalsId()));
		list.removeIf(bank -> !withdrawalsIdSet.contains(bank.getId()));

		List<CBank> resultList = new ArrayList<>();
		String language = WebUtil.getLanguage();
		list.stream().forEach(bank -> {
			CBank cBank = new CBank();
			cBank.setWithdrawalsId(bank.getId());
			cBank.setBankId(bank.getBankId());
			cBank.setBankCode(bank.getReceiptBankCode());
			cBank.setSwiftCode(bank.getSwiftCode());
			// 根据语言设置对应的银行名称
			String bankName = bank.getReceiptBankName();
			if (language != null) {
				switch (language.toLowerCase()) {
					case "zh-hans":
						bankName = bank.getReceiptBankName();
						break;
					case "zh-hant":
						bankName = bank.getReceiptBankNameHant();
						break;
					case "en":
						bankName = bank.getReceiptBankNameEn();
						break;
					default:
						bankName = bank.getReceiptBankName();
				}
			}
			cBank.setBankName(bankName);
			resultList.add(cBank);
		});
		return R.data(resultList);
	}

	@Override
	public R<BankSwiftCodeVO> bankSwiftCode(String bankCode) {
		if (StringUtil.isBlank(bankCode)) {
			return R.fail("bankCode 不能为空");
		}
		LambdaQueryWrapper<CashWithdrawalsBankEntity> queryWrapper = new LambdaQueryWrapper();
		queryWrapper.eq(CashWithdrawalsBankEntity::getReceiptBankCode, bankCode);
		queryWrapper.eq(CashWithdrawalsBankEntity::getIsShow, 1);
		CashWithdrawalsBankEntity cashWithdrawalsBank = baseMapper.selectOne(queryWrapper);
		if (cashWithdrawalsBank == null) {
			return R.success();
		}
		BankSwiftCodeVO bankSwiftCode = new BankSwiftCodeVO();
		bankSwiftCode.setSwiftCode(cashWithdrawalsBank.getSwiftCode());
		bankSwiftCode.setBankId(cashWithdrawalsBank.getBankId());
		bankSwiftCode.setBankCode(bankCode);
		bankSwiftCode.setFree(cashWithdrawalsBank.getChargeMoney());
		return R.data(bankSwiftCode);
	}

	/**
	 * 根据bankId和bankCode查询出金银行卡配置
	 *
	 * @param bankId
	 */
	@Override
	public CashWithdrawalsBankEntity selectByBankCodeAndId(String bankCode, String bankId) {
		if (StringUtil.isBlank(bankCode) || StringUtil.isBlank(bankId)) {
			return null;
		}
		LambdaQueryWrapper<CashWithdrawalsBankEntity> queryWrapper = new LambdaQueryWrapper();
		queryWrapper.eq(CashWithdrawalsBankEntity::getReceiptBankCode, bankCode);
		queryWrapper.like(CashWithdrawalsBankEntity::getBankId, bankId);
		queryWrapper.eq(CashWithdrawalsBankEntity::getIsShow, 1);
		CashWithdrawalsBankEntity cashWithdrawalsBank = baseMapper.selectOne(queryWrapper);
		return cashWithdrawalsBank;
	}
}
