package com.minigod.zero.manage.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.core.log.exception.ServiceException;
import com.minigod.zero.core.mp.base.BaseServiceImpl;
import com.minigod.zero.core.tool.utils.WebUtil;
import com.minigod.zero.manage.entity.CashPayeeBankDetailEntity;
import com.minigod.zero.manage.entity.CashPayeeBankEntity;
import com.minigod.zero.manage.mapper.CashPayeeBankDetailMapper;
import com.minigod.zero.manage.mapper.CashPayeeBankMapper;
import com.minigod.zero.manage.service.ICashPayeeBankDetailService;
import com.minigod.zero.manage.vo.ReceivingBankVO;
import com.minigod.zero.manage.vo.CashPayeeBankDetailVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 付款账户信息 服务实现类
 *
 * @author 掌上智珠
 * @since 2023-05-18
 */
@Service
public class CashPayeeBankDetailServiceImpl extends BaseServiceImpl<CashPayeeBankDetailMapper, CashPayeeBankDetailEntity> implements ICashPayeeBankDetailService {


	@Autowired
	private CashPayeeBankMapper cashPayeeBankMapper;

	/**
	 * 收款、付款账户详情信息分页查询
	 *
	 * @param page
	 * @param cashPayeeBankDetail
	 * @return
	 */
	@Override
	public IPage<CashPayeeBankDetailVO> selectCashPayeeBankDetailPage(IPage<CashPayeeBankDetailVO> page, CashPayeeBankDetailVO cashPayeeBankDetail) {
		return page.setRecords(baseMapper.selectCashPayeeBankDetailPage(page, cashPayeeBankDetail));
	}

	/**
	 * 根据银行获取转账类型信息
	 *
	 * @param depositId
	 * @param supportTypeList
	 * @return
	 */
	@Override
	public List<CashPayeeBankDetailEntity> findSupportInfoByDeposit(Long depositId, String[] supportTypeList) {
		return baseMapper.findSupportInfoByDeposit(depositId, supportTypeList);
	}

	/**
	 * 根据入金银行id和汇款方式查询币种
	 *
	 * @param depositId
	 * @param supportType
	 * @return
	 */
	@Override
	public List<String> findCurrencyByDepositIdAndSupportType(Long depositId, String supportType) {
		return baseMapper.findCurrencyByDepositIdAndSupportType(depositId, supportType);
	}

	/**
	 * 根据币种和汇款方式查询收款银行信息
	 *
	 * @param currency
	 * @param supportType
	 * @return
	 */
	@Override
	public ReceivingBankVO findReceivingBank(Integer currency, String supportType) {
		CashPayeeBankDetailEntity bankDetail = baseMapper.findPayeeBankByCurrencyAndSupportType(currency, supportType);
		if (bankDetail == null) {
			throw new ServiceException("未配置收款信息");
		}
		Long payeeBankId = bankDetail.getPayeeBankId();
		CashPayeeBankEntity payeeBank = cashPayeeBankMapper.selectById(payeeBankId);

		ReceivingBankVO receivingBank = new ReceivingBankVO();
		receivingBank.setBankId(payeeBank.getBankId());
		receivingBank.setBankCode(payeeBank.getBankCode());
		receivingBank.setBankIdQuick(payeeBank.getBankIdQuick());
		receivingBank.setBankAddress(payeeBank.getAddress());
		receivingBank.setBankEnName(payeeBank.getBankNameEn());
		receivingBank.setSwiftCode(payeeBank.getSwiftCode());
		receivingBank.setFpsID(bankDetail.getFpsId());
		receivingBank.setAccount(bankDetail.getBankAccount());
		receivingBank.setAccountName(bankDetail.getPayeeName());
		receivingBank.setPayeeBankDetailId(bankDetail.getId());
		receivingBank.setPayeeBankId(payeeBank.getId());

		String language = WebUtil.getLanguage();
		if (language != null) {
			switch (language) {
				case "zh-hans":
					receivingBank.setBankName(payeeBank.getBankName());
					break;
				case "zh-hant":
					receivingBank.setBankName(payeeBank.getBankNameHant());
					break;
				case "en":
					receivingBank.setBankName(payeeBank.getBankNameEn());
					break;
				default:
					receivingBank.setBankName(payeeBank.getBankName());
					break;
			}
		}
		return receivingBank;
	}

	/**
	 * 根据收款银行详情id查询收款银行信息
	 *
	 * @param payeeBankDetailId
	 * @return
	 */
	@Override
	public ReceivingBankVO findReceivingBankById(Long payeeBankDetailId) {
		CashPayeeBankDetailEntity bankDetail = baseMapper.findReceivingBankById(payeeBankDetailId);
		if (bankDetail == null) {
			throw new ServiceException("未配置收款信息");
		}
		CashPayeeBankEntity payeeBank = cashPayeeBankMapper.selectById(bankDetail.getPayeeBankId());

		ReceivingBankVO receivingBank = new ReceivingBankVO();
		receivingBank.setBankId(payeeBank.getBankId());
		receivingBank.setBankCode(payeeBank.getBankCode());
		receivingBank.setBankIdQuick(payeeBank.getBankIdQuick());
		receivingBank.setBankAddress(payeeBank.getAddress());
		receivingBank.setBankName(payeeBank.getBankName());
		receivingBank.setBankEnName(payeeBank.getBankNameEn());
		receivingBank.setSwiftCode(payeeBank.getSwiftCode());

		receivingBank.setFpsID(bankDetail.getFpsId());
		receivingBank.setAccount(bankDetail.getBankAccount());
		receivingBank.setAccountName(bankDetail.getPayeeName());
		receivingBank.setPayeeBankDetailId(bankDetail.getId());
		receivingBank.setPayeeBankId(payeeBank.getId());
		return receivingBank;
	}

	/**
	 * 根据入金银行id和汇款方式、币种查询收款银行信息
	 *
	 * @param depositBankById
	 * @param currency
	 * @param supportType
	 * @return
	 */
	@Override
	public ReceivingBankVO findReceivingBankByDepositBankById(Long depositBankById, String currency, String supportType) {
		CashPayeeBankDetailEntity bankDetail = baseMapper.findPayeeBankByDepositBankById(depositBankById, currency, supportType);
		if (bankDetail == null) {
			throw new ServiceException("未配置收款信息");
		}
		CashPayeeBankEntity payeeBank = cashPayeeBankMapper.selectById(bankDetail.getPayeeBankId());

		ReceivingBankVO receivingBank = new ReceivingBankVO();
		receivingBank.setBankId(payeeBank.getBankId());
		receivingBank.setBankCode(payeeBank.getBankCode());
		receivingBank.setBankAddress(payeeBank.getAddress());
		receivingBank.setBankName(payeeBank.getBankName());
		receivingBank.setBankEnName(payeeBank.getBankNameEn());
		receivingBank.setSwiftCode(payeeBank.getSwiftCode());
		receivingBank.setPayeeBankDetailId(bankDetail.getId());
		receivingBank.setPayeeBankId(payeeBank.getId());
		receivingBank.setFpsID(bankDetail.getFpsId());
		receivingBank.setAccount(bankDetail.getBankAccount());
		receivingBank.setAccountName(bankDetail.getPayeeName());
		receivingBank.setMaxAmt(bankDetail.getMaxAmt());

		return receivingBank;
	}

	/**
	 * 根据出金银行id和汇款方式、币种查询付款银行信息
	 *
	 * @param withdrawalsBankById
	 * @param currency
	 * @param supportType
	 * @return
	 */
	@Override
	public ReceivingBankVO findPaymentBankByWithdrawalsBankById(Long withdrawalsBankById, String currency, String supportType) {
		CashPayeeBankDetailEntity bankDetail = baseMapper.findPaymentBankByWithdrawalsBankById(withdrawalsBankById, currency, supportType);
		if (bankDetail == null) {
			log.error(String.format("未找到对应参数配置的出金付款银行信息,withdrawalsId:%s,currency:%s,supportType:%s", withdrawalsBankById, currency, supportType));
			throw new ServiceException("未找到对应参数配置的出金付款银行信息!");
		}
		CashPayeeBankEntity payeeBank = cashPayeeBankMapper.selectById(bankDetail.getPayeeBankId());
		if (payeeBank == null) {
			log.error(String.format("未找到ID为:%s的付款银行信息!", bankDetail.getPayeeBankId()));
			throw new ServiceException("未找到指定ID的付款银行信息");
		}

		ReceivingBankVO receivingBank = new ReceivingBankVO();
		receivingBank.setBankId(payeeBank.getBankId());
		receivingBank.setBankCode(payeeBank.getBankCode());
		receivingBank.setBankAddress(payeeBank.getAddress());
		receivingBank.setBankName(payeeBank.getBankName());
		receivingBank.setBankEnName(payeeBank.getBankNameEn());
		receivingBank.setSwiftCode(payeeBank.getSwiftCode());
		receivingBank.setPayeeBankDetailId(bankDetail.getId());
		receivingBank.setPayeeBankId(payeeBank.getId());
		receivingBank.setFpsID(bankDetail.getFpsId());
		receivingBank.setAccount(bankDetail.getBankAccount());
		receivingBank.setAccountName(bankDetail.getPayeeName());
		receivingBank.setMaxAmt(bankDetail.getMaxAmt());

		return receivingBank;
	}

	/**
	 * 更新收、付账户信息
	 *
	 * @param cashPayeeBankDetail
	 * @return
	 */
	@Override
	public boolean updateCashPayeeBankDetail(CashPayeeBankDetailEntity cashPayeeBankDetail) {
		return this.updateById(cashPayeeBankDetail);
	}

	/**
	 * 新增收、付账户信息
	 *
	 * @param cashPayeeBankDetail
	 * @return
	 */
	@Override
	public boolean saveCashPayeeBankDetail(CashPayeeBankDetailEntity cashPayeeBankDetail) {
		return this.save(cashPayeeBankDetail);
	}

	/**
	 * 删除收、付款账户信息
	 *
	 * @param ids
	 * @return
	 */
	@Override
	public boolean deleteCashPayeeBankDetail(List<Long> ids) {
		return this.deleteLogic(ids);
	}
}
