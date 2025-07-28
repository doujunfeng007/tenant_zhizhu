package com.minigod.zero.customer.back.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minigod.zero.customer.back.service.ICustomerTradeSubAccountService;
import com.minigod.zero.customer.entity.CustomerTradeSubAccountEntity;
import com.minigod.zero.customer.enums.StatusEnum;
import com.minigod.zero.customer.mapper.CustomerTradeSubAccountMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 交易子账号信息 服务实现类
 *
 * @author ken
 * @since 2024-04-11
 */
@Slf4j
@Service
public class CustomerTradeSubAccountServiceImpl extends ServiceImpl<CustomerTradeSubAccountMapper, CustomerTradeSubAccountEntity>
	implements ICustomerTradeSubAccountService {

	@Override
	public List<CustomerTradeSubAccountEntity> getSubAccounts(String tradeAccount) {
		return baseMapper.selectByTradeAccount(tradeAccount);
	}

	@Override
	public List<CustomerTradeSubAccountEntity> getSubAccountsByAccountId(String accountId) {
		return baseMapper.selectByAccountId(accountId);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean addSubAccount(CustomerTradeSubAccountEntity entity) {
		entity.setCreateTime(new Date());
		entity.setUpdateTime(new Date());
		entity.setIsDeleted(0);
		return save(entity);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean batchAddSubAccounts(List<CustomerTradeSubAccountEntity> entities) {
		if (entities == null || entities.isEmpty()) {
			return false;
		}

		Date now = new Date();
		entities.forEach(entity -> {
			entity.setCreateTime(now);
			entity.setUpdateTime(now);
			entity.setIsDeleted(0);
		});

		return saveBatch(entities);
	}

	@Override
	public List<CustomerTradeSubAccountEntity> selecctSubAccountByTradeId(Long id) {
		LambdaQueryWrapper<CustomerTradeSubAccountEntity> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(CustomerTradeSubAccountEntity::getTradeAccountId, id);
		wrapper.eq(CustomerTradeSubAccountEntity::getIsDeleted, 0);
		return this.list(wrapper);
	}

	@Override
	public List<CustomerTradeSubAccountEntity> selectByAccountId(String accountId) {
		LambdaQueryWrapper<CustomerTradeSubAccountEntity> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(CustomerTradeSubAccountEntity::getAccountId, accountId);
		wrapper.eq(CustomerTradeSubAccountEntity::getIsDeleted, 0);
		return this.list(wrapper);
	}

	@Override
	public void updateMasterAccount(CustomerTradeSubAccountEntity capitalInfo, String capitalAccount, String tradeAccount,String marketType) {
		capitalInfo.setUpdateTime(new Date());
		LambdaQueryWrapper<CustomerTradeSubAccountEntity> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(CustomerTradeSubAccountEntity::getSubAccount, capitalAccount);
		wrapper.eq(CustomerTradeSubAccountEntity::getMarketType,marketType);
		wrapper.eq(CustomerTradeSubAccountEntity::getIsDeleted, 0);
		baseMapper.update(capitalInfo,wrapper );

		capitalInfo.setIsMaster(StatusEnum.NO.getCode());
		wrapper.clear();
		wrapper.eq(CustomerTradeSubAccountEntity::getMarketType,marketType);
		wrapper.eq(CustomerTradeSubAccountEntity::getIsDeleted, 0);
		wrapper.eq(CustomerTradeSubAccountEntity::getTradeAccount, tradeAccount);
		wrapper.ne(CustomerTradeSubAccountEntity::getSubAccount, capitalAccount);
		baseMapper.update(capitalInfo,wrapper );

	}
}
