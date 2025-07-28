package com.minigod.zero.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.minigod.zero.core.mp.base.BaseServiceImpl;
import com.minigod.zero.core.mp.support.Condition;
import com.minigod.zero.core.mp.support.Query;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.utils.CollectionUtil;
import com.minigod.zero.core.tool.utils.StringUtil;
import com.minigod.zero.manage.entity.CashPayeeBankEntity;
import com.minigod.zero.manage.entity.CashWithdrawalsBankEntity;
import com.minigod.zero.manage.entity.CashWithdrawalsSupportCurrencyBankEntity;
import com.minigod.zero.manage.mapper.CashWithdrawalsSupportCurrencyBankMapper;
import com.minigod.zero.manage.service.ICashPayeeBankDetailService;
import com.minigod.zero.manage.service.ICashPayeeBankService;
import com.minigod.zero.manage.service.ICashWithdrawalsBankService;
import com.minigod.zero.manage.service.ICashWithdrawalsSupportCurrencyBankService;
import com.minigod.zero.manage.vo.CashWithdrawalsSupportCurrencyBankVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 出金银行、付款方式、币种、付款银行关联表
 *
 * @author eric
 * @since 2024-10-18 17:59:05
 */
@Service
public class CashWithdrawalsSupportCurrencyBankServiceImpl extends BaseServiceImpl<CashWithdrawalsSupportCurrencyBankMapper, CashWithdrawalsSupportCurrencyBankEntity>
	implements ICashWithdrawalsSupportCurrencyBankService {

	private final ICashPayeeBankService cashPayeeBankService;
	private final ICashPayeeBankDetailService cashPayeeBankDetailService;

	private final ICashWithdrawalsBankService cashWithdrawalsBankService;

	public CashWithdrawalsSupportCurrencyBankServiceImpl(ICashPayeeBankService cashPayeeBankService,
														 ICashPayeeBankDetailService cashPayeeBankDetailService,
														 ICashWithdrawalsBankService cashWithdrawalsBankService) {
		this.cashPayeeBankService = cashPayeeBankService;
		this.cashPayeeBankDetailService = cashPayeeBankDetailService;
		this.cashWithdrawalsBankService = cashWithdrawalsBankService;
	}

	/**
	 * 分页查询条件封装
	 *
	 * @param vo
	 * @return
	 */
	private LambdaQueryWrapper<CashWithdrawalsSupportCurrencyBankEntity> buildQueryWrapper(CashWithdrawalsSupportCurrencyBankVO vo) {
		LambdaQueryWrapper<CashWithdrawalsSupportCurrencyBankEntity> wrapper = Wrappers.lambdaQuery();
		wrapper.eq(vo.getWithdrawalsId() != null, CashWithdrawalsSupportCurrencyBankEntity::getWithdrawalsId, vo.getWithdrawalsId());
		wrapper.eq(vo.getAccountType() != null, CashWithdrawalsSupportCurrencyBankEntity::getAccountType, vo.getAccountType());
		wrapper.eq(StringUtil.isNotBlank(vo.getCurrency()), CashWithdrawalsSupportCurrencyBankEntity::getCurrency, vo.getCurrency());
		wrapper.eq(StringUtil.isNotBlank(vo.getSupportType()), CashWithdrawalsSupportCurrencyBankEntity::getSupportType, vo.getSupportType());
		wrapper.eq(vo.getPaymentBankId() != null, CashWithdrawalsSupportCurrencyBankEntity::getPaymentBankId, vo.getPaymentBankId());
		wrapper.eq(vo.getPaymentBankDetailId() != null, CashWithdrawalsSupportCurrencyBankEntity::getPaymentBankDetailId, vo.getPaymentBankDetailId());
		wrapper.eq(vo.getIsDefault() != null, CashWithdrawalsSupportCurrencyBankEntity::getIsDefault, vo.getIsDefault());
		wrapper.orderByDesc(CashWithdrawalsSupportCurrencyBankEntity::getSortOrder);
		return wrapper;
	}

	/**
	 * 出金银行、付款方式、币种、付款银行关联表分页查询
	 *
	 * @param query
	 * @param cashWithdrawalsSupportCurrencyBankVO
	 * @return
	 */
	@Override
	public IPage<CashWithdrawalsSupportCurrencyBankVO> selectWithdrawalsSupportCurrencyBankPage(Query query,
																								CashWithdrawalsSupportCurrencyBankVO cashWithdrawalsSupportCurrencyBankVO) {
		IPage<CashWithdrawalsSupportCurrencyBankEntity> result = this.page(Condition.getPage(query), buildQueryWrapper(cashWithdrawalsSupportCurrencyBankVO));

		List<CashPayeeBankEntity> cashPayeeBankEntities = cashPayeeBankService.list();
		List<CashWithdrawalsBankEntity> cashWithdrawalsBankEntities = cashWithdrawalsBankService.list();

		IPage<CashWithdrawalsSupportCurrencyBankVO> voPage = result.convert(e -> {
			CashWithdrawalsSupportCurrencyBankVO vo = new CashWithdrawalsSupportCurrencyBankVO();
			vo.setId(e.getId());
			vo.setCreateTime(e.getCreateTime());
			vo.setUpdateTime(e.getUpdateTime());
			vo.setCreateUser(e.getCreateUser());
			vo.setUpdateUser(e.getUpdateUser());
			vo.setCreateDept(e.getCreateDept());
			vo.setTenantId(e.getTenantId());
			vo.setStatus(e.getStatus());
			vo.setIsDeleted(e.getIsDeleted());
			vo.setWithdrawalsId(e.getWithdrawalsId());
			vo.setAccountType(e.getAccountType());
			switch (e.getAccountType()) {
				case 1:
					vo.setAccountTypeName("大账户");
					break;
				case 2:
					vo.setAccountTypeName("子账户");
					break;
			}
			vo.setCurrency(e.getCurrency());
			vo.setCurrency(e.getCurrency());
			switch (e.getCurrency()) {
				case "1":
					vo.setCurrencyName("港币");
					break;
				case "2":
					vo.setCurrencyName("美元");
					break;
				case "3":
					vo.setCurrencyName("人民币");
					break;
			}
			vo.setPaymentBankId(e.getPaymentBankId());
			vo.setPaymentBankDetailId(e.getPaymentBankDetailId());
			vo.setSupportType(e.getSupportType());
			switch (e.getSupportType()) {
				case "1":
					vo.setSupportTypeName("香港银行普通转账");
					break;
				case "2":
					vo.setSupportTypeName("香港银行本地转账");
					break;
				case "3":
					vo.setSupportTypeName("电汇");
					break;
				case "4":
					vo.setSupportTypeName("FPS ID");
					break;
			}
			vo.setSortOrder(e.getSortOrder());
			vo.setIsDefault(e.getIsDefault());
			vo.setWithdrawalsBankName(cashWithdrawalsBankEntities.stream().filter(e1 -> e1.getId().equals(e.getWithdrawalsId())).findFirst().map(CashWithdrawalsBankEntity::getReceiptBankName).orElse(""));
			vo.setPaymentBankName(cashPayeeBankEntities.stream().filter(e1 -> e1.getId().equals(e.getPaymentBankId())).findFirst().map(CashPayeeBankEntity::getBankName).orElse(""));
			return vo;
		});

		return voPage;
	}

	/**
	 * 勾选/取消勾选 出金银行卡付款方式对应的收款银行
	 *
	 * @param withdrawalsId
	 * @param supportType
	 * @param currency
	 * @param accountType
	 * @param paymentBankId
	 * @param paymentBankDetailId
	 * @return
	 */
	@Override
	public R editCashWithdrawalsSupportCurrencyBank(Long withdrawalsId, Integer supportType, Integer currency, Integer accountType, Long paymentBankId, Long paymentBankDetailId) {
		List<CashWithdrawalsSupportCurrencyBankEntity> entities = new LambdaQueryChainWrapper<>(baseMapper)
			.eq(CashWithdrawalsSupportCurrencyBankEntity::getWithdrawalsId, withdrawalsId)
			.eq(CashWithdrawalsSupportCurrencyBankEntity::getSupportType, supportType)
			.eq(CashWithdrawalsSupportCurrencyBankEntity::getCurrency, currency)
			.eq(CashWithdrawalsSupportCurrencyBankEntity::getPaymentBankId, paymentBankId)
			.eq(CashWithdrawalsSupportCurrencyBankEntity::getAccountType, accountType)
			.eq(CashWithdrawalsSupportCurrencyBankEntity::getPaymentBankDetailId, paymentBankDetailId)
			.list();

		if (CollectionUtil.isEmpty(entities)) {
			//勾选，先查询是否有已关联默认的付款银行，没有则关联为默认付款银行，有则关联为非默认付款银行
			CashWithdrawalsSupportCurrencyBankEntity defaultEntity = new LambdaQueryChainWrapper<>(baseMapper)
				.eq(CashWithdrawalsSupportCurrencyBankEntity::getWithdrawalsId, withdrawalsId)
				.eq(CashWithdrawalsSupportCurrencyBankEntity::getSupportType, supportType)
				//TODO 零号代码原来没有加上币种条件，加上待测
				.eq(CashWithdrawalsSupportCurrencyBankEntity::getCurrency, currency)
				.eq(CashWithdrawalsSupportCurrencyBankEntity::getIsDefault, 1)
				.last("limit 1")
				.one();

			CashWithdrawalsSupportCurrencyBankEntity supportCurrencyBank = new CashWithdrawalsSupportCurrencyBankEntity();
			//判断有没有默认配置
			if (defaultEntity == null) {
				supportCurrencyBank.setIsDefault(1);
			} else {
				supportCurrencyBank.setIsDefault(0);
			}
			supportCurrencyBank.setCurrency(currency + "");
			supportCurrencyBank.setAccountType(accountType);
			supportCurrencyBank.setPaymentBankId(paymentBankId);
			supportCurrencyBank.setSortOrder(0);
			supportCurrencyBank.setWithdrawalsId(withdrawalsId);
			supportCurrencyBank.setSupportType(supportType + "");
			supportCurrencyBank.setPaymentBankDetailId(paymentBankDetailId);
			this.save(supportCurrencyBank);
		} else {
			//取消勾选（取消勾选的不允许为默认付款银行，必须把默认付款银行修改为另外的付款银行）
			List<CashWithdrawalsSupportCurrencyBankEntity> isDefaults = entities.stream()
				.filter(o -> o.getIsDefault() != null && o.getIsDefault() == 1)
				.collect(Collectors.toList());
			if (CollectionUtil.isNotEmpty(isDefaults)) {
				return R.fail("取消勾选的不允许为默认付款银行，必须把默认付款银行修改为其它的付款银行后才能取消。");
			}
			this.deleteLogic(entities.stream().map(CashWithdrawalsSupportCurrencyBankEntity::getId).collect(Collectors.toList()));
		}
		return R.success();
	}

	/**
	 * 勾选/取消勾选 出金银行卡付款方式对应的默认付款银行
	 *
	 * @param withdrawalsId
	 * @param supportType
	 * @param currency
	 * @param accountType
	 * @param paymentBankId
	 * @param paymentBankDetailId
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public R editCashWithdrawalsSupportCurrencyBankDefault(Long withdrawalsId, Integer supportType, Integer currency, Integer accountType, Long paymentBankId, Long paymentBankDetailId) {
		LambdaUpdateWrapper<CashWithdrawalsSupportCurrencyBankEntity> updateWrapper = new LambdaUpdateWrapper<>();
		updateWrapper.set(CashWithdrawalsSupportCurrencyBankEntity::getIsDefault, 0);
		updateWrapper.eq(CashWithdrawalsSupportCurrencyBankEntity::getWithdrawalsId, withdrawalsId);
		updateWrapper.eq(CashWithdrawalsSupportCurrencyBankEntity::getSupportType, supportType);
		updateWrapper.eq(CashWithdrawalsSupportCurrencyBankEntity::getCurrency, currency);
		baseMapper.update(null, updateWrapper);

		CashWithdrawalsSupportCurrencyBankEntity entity = new LambdaQueryChainWrapper<>(baseMapper)
			.eq(CashWithdrawalsSupportCurrencyBankEntity::getWithdrawalsId, withdrawalsId)
			.eq(CashWithdrawalsSupportCurrencyBankEntity::getSupportType, supportType)
			.eq(CashWithdrawalsSupportCurrencyBankEntity::getCurrency, currency)
			.eq(CashWithdrawalsSupportCurrencyBankEntity::getPaymentBankId, paymentBankId)
			.eq(CashWithdrawalsSupportCurrencyBankEntity::getPaymentBankDetailId, paymentBankDetailId)
			.eq(CashWithdrawalsSupportCurrencyBankEntity::getAccountType, accountType)
			.last("limit 1")
			.one();
		if (entity == null) {
			CashWithdrawalsSupportCurrencyBankEntity supportCurrencyBank = new CashWithdrawalsSupportCurrencyBankEntity();
			supportCurrencyBank.setIsDefault(1);
			supportCurrencyBank.setCurrency(currency + "");
			supportCurrencyBank.setAccountType(accountType);
			supportCurrencyBank.setPaymentBankId(paymentBankId);
			supportCurrencyBank.setSortOrder(0);
			supportCurrencyBank.setWithdrawalsId(withdrawalsId);
			supportCurrencyBank.setSupportType(supportType + "");
			supportCurrencyBank.setPaymentBankDetailId(paymentBankDetailId);
			this.save(supportCurrencyBank);
		} else {
			entity.setIsDefault(1);
			this.updateById(entity);
		}
		return R.success();
	}
}
