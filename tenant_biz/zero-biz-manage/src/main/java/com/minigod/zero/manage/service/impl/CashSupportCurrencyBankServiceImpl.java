package com.minigod.zero.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.minigod.zero.core.mp.base.BaseServiceImpl;
import com.minigod.zero.core.mp.support.Condition;
import com.minigod.zero.core.mp.support.Query;
import com.minigod.zero.core.tool.utils.StringUtil;
import com.minigod.zero.manage.entity.CashDepositBankEntity;
import com.minigod.zero.manage.entity.CashPayeeBankEntity;
import com.minigod.zero.manage.entity.CashSupportCurrencyBankEntity;
import com.minigod.zero.manage.mapper.CashSupportCurrencyBankMapper;
import com.minigod.zero.manage.service.ICashDepositBankService;
import com.minigod.zero.manage.service.ICashPayeeBankService;
import com.minigod.zero.manage.service.ICashSupportCurrencyBankService;
import com.minigod.zero.manage.vo.CashSupportCurrencyBankVO;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.utils.CollectionUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 入金银行 收款方式 币种 收款银行关联表 服务实现类
 *
 * @author 掌上智珠
 * @since 2023-05-18
 */
@Service
public class CashSupportCurrencyBankServiceImpl extends BaseServiceImpl<CashSupportCurrencyBankMapper, CashSupportCurrencyBankEntity> implements ICashSupportCurrencyBankService {

	private final ICashPayeeBankService cashPayeeBankService;
	private final ICashDepositBankService cashDepositBankService;

	public CashSupportCurrencyBankServiceImpl(ICashPayeeBankService cashPayeeBankService,
											  ICashDepositBankService cashDepositBankService) {
		this.cashPayeeBankService = cashPayeeBankService;
		this.cashDepositBankService = cashDepositBankService;
	}

	@Override
	public IPage<CashSupportCurrencyBankVO> selectCashSupportCurrencyBankPage(Query query, CashSupportCurrencyBankVO cashSupportCurrencyBank) {
		IPage<CashSupportCurrencyBankEntity> result = this.page(Condition.getPage(query), buildQueryWrapper(cashSupportCurrencyBank));

		List<CashPayeeBankEntity> cashPayeeBankEntities = cashPayeeBankService.list();
		List<CashDepositBankEntity> cashPayeeBankDetailEntities = cashDepositBankService.list();

		IPage<CashSupportCurrencyBankVO> voPage = result.convert(e -> {
			CashSupportCurrencyBankVO vo = new CashSupportCurrencyBankVO();
			vo.setId(e.getId());
			vo.setCreateTime(e.getCreateTime());
			vo.setUpdateTime(e.getUpdateTime());
			vo.setCreateUser(e.getCreateUser());
			vo.setUpdateUser(e.getUpdateUser());
			vo.setCreateDept(e.getCreateDept());
			vo.setTenantId(e.getTenantId());
			vo.setStatus(e.getStatus());
			vo.setIsDeleted(e.getIsDeleted());
			vo.setDepositId(e.getDepositId());
			vo.setDepositBankName(cashPayeeBankDetailEntities.stream().filter(e1 -> e1.getId().equals(e.getDepositId())).findFirst().map(CashDepositBankEntity::getRemitBankName).orElse(""));
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
			vo.setPayeeBankId(e.getPayeeBankId());
			vo.setRemitBankName(cashPayeeBankEntities.stream().filter(e1 -> e1.getId().equals(e.getPayeeBankId())).findFirst().map(CashPayeeBankEntity::getBankName).orElse(""));
			vo.setPayeeBankDetailId(e.getPayeeBankDetailId());
			vo.setSupportType(e.getSupportType());
			switch (e.getSupportType()) {
				case "1":
					vo.setSupportTypeName("FPS转数快");
					break;
				case "2":
					vo.setSupportTypeName("网银转账");
					break;
				case "3":
					vo.setSupportTypeName("支票转账");
					break;
				case "4":
					vo.setSupportTypeName("快捷入金");
					break;
				case "5":
					vo.setSupportTypeName("银证转账");
					break;
			}
			vo.setSortOrder(e.getSortOrder());
			vo.setIsDefault(e.getIsDefault());
			return vo;
		});

		return voPage;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public R editSupportCurrencyBank(Long depositId, Integer supportType, Integer currency, Integer accountType, Long payeeBankId, Long payeeBankDetailId) {
		List<CashSupportCurrencyBankEntity> entities = new LambdaQueryChainWrapper<>(baseMapper)
			.eq(CashSupportCurrencyBankEntity::getDepositId, depositId)
			.eq(CashSupportCurrencyBankEntity::getSupportType, supportType)
			.eq(CashSupportCurrencyBankEntity::getCurrency, currency)
			.eq(CashSupportCurrencyBankEntity::getPayeeBankId, payeeBankId)
			.eq(CashSupportCurrencyBankEntity::getAccountType, accountType)
			.eq(CashSupportCurrencyBankEntity::getPayeeBankDetailId, payeeBankDetailId)
			.list();

		if (CollectionUtil.isEmpty(entities)) {
			//勾选，先查询是否有已关联默认的收款银行，没有则关联为默认收款银行，有则关联为非默认收款银行
			CashSupportCurrencyBankEntity defaultEntity = new LambdaQueryChainWrapper<>(baseMapper)
				.eq(CashSupportCurrencyBankEntity::getDepositId, depositId)
				.eq(CashSupportCurrencyBankEntity::getSupportType, supportType)
				//TODO 零号代码原来没有加上币种条件，加上待测
				.eq(CashSupportCurrencyBankEntity::getCurrency, currency)
				.eq(CashSupportCurrencyBankEntity::getIsDefault, 1)
				.last("limit 1")
				.one();

			CashSupportCurrencyBankEntity supportCurrencyBank = new CashSupportCurrencyBankEntity();
			//判断有没有默认配置
			if (defaultEntity == null) {
				supportCurrencyBank.setIsDefault(1);
			} else {
				supportCurrencyBank.setIsDefault(0);
			}
			supportCurrencyBank.setCurrency(currency + "");
			supportCurrencyBank.setAccountType(accountType);
			supportCurrencyBank.setPayeeBankId(payeeBankId);
			supportCurrencyBank.setSortOrder(0);
			supportCurrencyBank.setDepositId(depositId);
			supportCurrencyBank.setSupportType(supportType + "");
			supportCurrencyBank.setPayeeBankDetailId(payeeBankDetailId);
			this.save(supportCurrencyBank);
		} else {
			//取消勾选（取消勾选的不允许为默认收款银行，必须把默认收款银行修改为另外的收款银行）
			List<CashSupportCurrencyBankEntity> isDefaults = entities.stream()
				.filter(o -> o.getIsDefault() != null && o.getIsDefault() == 1)
				.collect(Collectors.toList());
			if (CollectionUtil.isNotEmpty(isDefaults)) {
				return R.fail("请修改默认收款信息后再取消");
			}
			this.deleteLogic(entities.stream().map(CashSupportCurrencyBankEntity::getId).collect(Collectors.toList()));
		}
		return R.success();
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public R editSupportCurrencyBankDefault(Long depositId, Integer supportType, Integer currency, Integer accountType, Long payeeBankId, Long payeeBankDetailId) {
		LambdaUpdateWrapper<CashSupportCurrencyBankEntity> updateWrapper = new LambdaUpdateWrapper<>();
		updateWrapper.set(CashSupportCurrencyBankEntity::getIsDefault, 0);
		updateWrapper.eq(CashSupportCurrencyBankEntity::getDepositId, depositId);
		updateWrapper.eq(CashSupportCurrencyBankEntity::getSupportType, supportType);
		updateWrapper.eq(CashSupportCurrencyBankEntity::getCurrency, currency);
		baseMapper.update(null, updateWrapper);

		CashSupportCurrencyBankEntity entity = new LambdaQueryChainWrapper<>(baseMapper)
			.eq(CashSupportCurrencyBankEntity::getDepositId, depositId)
			.eq(CashSupportCurrencyBankEntity::getSupportType, supportType)
			.eq(CashSupportCurrencyBankEntity::getCurrency, currency)
			.eq(CashSupportCurrencyBankEntity::getPayeeBankId, payeeBankId)
			.eq(CashSupportCurrencyBankEntity::getPayeeBankDetailId, payeeBankDetailId)
			.eq(CashSupportCurrencyBankEntity::getAccountType, accountType)
			.last("limit 1")
			.one();
		if (entity == null) {
			CashSupportCurrencyBankEntity supportCurrencyBank = new CashSupportCurrencyBankEntity();
			supportCurrencyBank.setIsDefault(1);
			supportCurrencyBank.setCurrency(currency + "");
			supportCurrencyBank.setAccountType(accountType);
			supportCurrencyBank.setPayeeBankId(payeeBankId);
			supportCurrencyBank.setSortOrder(0);
			supportCurrencyBank.setDepositId(depositId);
			supportCurrencyBank.setSupportType(supportType + "");
			supportCurrencyBank.setPayeeBankDetailId(payeeBankDetailId);
			this.save(supportCurrencyBank);
		} else {
			entity.setIsDefault(1);
			this.updateById(entity);
		}
		return R.success();
	}

	/**
	 * 分页查询条件封装
	 *
	 * @param vo
	 * @return
	 */
	private LambdaQueryWrapper<CashSupportCurrencyBankEntity> buildQueryWrapper(CashSupportCurrencyBankVO vo) {
		LambdaQueryWrapper<CashSupportCurrencyBankEntity> wrapper = Wrappers.lambdaQuery();
		wrapper.eq(vo.getDepositId() != null, CashSupportCurrencyBankEntity::getDepositId, vo.getDepositId());
		wrapper.eq(vo.getAccountType() != null, CashSupportCurrencyBankEntity::getAccountType, vo.getAccountType());
		wrapper.eq(StringUtil.isNotBlank(vo.getCurrency()), CashSupportCurrencyBankEntity::getCurrency, vo.getCurrency());
		wrapper.eq(StringUtil.isNotBlank(vo.getSupportType()), CashSupportCurrencyBankEntity::getSupportType, vo.getSupportType());
		wrapper.eq(vo.getPayeeBankId() != null, CashSupportCurrencyBankEntity::getPayeeBankId, vo.getPayeeBankId());
		wrapper.eq(vo.getPayeeBankDetailId() != null, CashSupportCurrencyBankEntity::getPayeeBankDetailId, vo.getPayeeBankDetailId());
		wrapper.eq(vo.getIsDefault() != null, CashSupportCurrencyBankEntity::getIsDefault, vo.getIsDefault());
		wrapper.orderByDesc(CashSupportCurrencyBankEntity::getSortOrder);
		return wrapper;
	}

}
