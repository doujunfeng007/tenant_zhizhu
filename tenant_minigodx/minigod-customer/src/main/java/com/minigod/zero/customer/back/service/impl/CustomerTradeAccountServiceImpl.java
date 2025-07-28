package com.minigod.zero.customer.back.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.utils.DateUtil;
import com.minigod.zero.customer.back.service.ICustomerAgreementInfoService;
import com.minigod.zero.customer.back.service.ICustomerTradeAccountService;
import com.minigod.zero.customer.back.service.ICustomerTradeSubAccountService;
import com.minigod.zero.customer.emuns.BusinessTypeEnums;
import com.minigod.zero.customer.emuns.MarketTypeEnums;
import com.minigod.zero.customer.entity.CustomerAgreementInfoEntity;
import com.minigod.zero.customer.entity.CustomerFinancingAccountEntity;
import com.minigod.zero.customer.entity.CustomerTradeAccountEntity;
import com.minigod.zero.customer.entity.CustomerTradeSubAccountEntity;
import com.minigod.zero.customer.mapper.CustomerFinancingAccountMapper;
import com.minigod.zero.customer.mapper.CustomerTradeAccountMapper;
import com.minigod.zero.trade.vo.sjmb.req.OpenAccountReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 客户交易账户表 服务实现类
 *
 * @author ken
 * @since 2024-04-11
 */
@Slf4j
@Service
public class CustomerTradeAccountServiceImpl extends ServiceImpl<CustomerTradeAccountMapper, CustomerTradeAccountEntity>
	implements ICustomerTradeAccountService {

	@Value("${tenant.counter.type:1}")
	private Integer counterType;

	@Value("${tenant.market}")
	private String tenantMarket;

	@Autowired
	private CustomerFinancingAccountMapper customerFinancingAccountMapper;

	@Autowired
	private ICustomerAgreementInfoService customerAgreementInfoService;

	@Autowired
	private ICustomerTradeSubAccountService customerTradeSubAccountService;

	@Override
	public List<CustomerTradeAccountEntity> getTradeAccounts(String accountId) {
		return baseMapper.selectByAccountId(accountId);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean switchCurrentAccount(String accountId, String tradeAccount) {
		return baseMapper.updateCurrentStatus(accountId, tradeAccount) > 0;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean addTradeAccount(CustomerTradeAccountEntity entity) {
		entity.setCreateTime(new Date());
		entity.setUpdateTime(new Date());
		entity.setIsDeleted(0);
		return save(entity);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean updateAccountStatus(String tradeAccount, Integer status) {
		LambdaUpdateWrapper<CustomerTradeAccountEntity> wrapper = new LambdaUpdateWrapper<>();
		wrapper.eq(CustomerTradeAccountEntity::getTradeAccount, tradeAccount)
			.set(CustomerTradeAccountEntity::getAccountStatus, status)
			.set(CustomerTradeAccountEntity::getUpdateTime, new Date());
		return update(wrapper);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public R saveTradeAccountAndSubAccount(OpenAccountReq openAccountReq, Long custId) {

		CustomerFinancingAccountEntity financingAccount = customerFinancingAccountMapper.selectByCustId(custId);
		// 插入协议
		addAgreementInfo(openAccountReq, financingAccount);

		CustomerTradeAccountEntity customerTradeAccountEntity = new CustomerTradeAccountEntity();
		customerTradeAccountEntity.setTradeAccount(openAccountReq.getFundAccount());
		customerTradeAccountEntity.setReletionTradeAccount(openAccountReq.getTradeAccount());
		customerTradeAccountEntity.setAccountType(openAccountReq.getFundAccountType() == 1 ? "0" : "M");
		customerTradeAccountEntity.setAccountId(financingAccount.getAccountId());
		customerTradeAccountEntity.setBusinessType(BusinessTypeEnums.SEC.getBusinessType());
		customerTradeAccountEntity.setAccountStatus(0);
		customerTradeAccountEntity.setIsCurrent(1);
		customerTradeAccountEntity.setCounterType(counterType);
		addTradeAccount(customerTradeAccountEntity);

		/**
		 * 保存交易子账号
		 */
		addSubAccount(openAccountReq,customerTradeAccountEntity);
		return R.success();
	}

	@Override
	public CustomerTradeAccountEntity selectTradeByAccountAndType(String accountId, String businessType) {
		LambdaUpdateWrapper<CustomerTradeAccountEntity> wrapper = new LambdaUpdateWrapper<>();
			wrapper.eq(CustomerTradeAccountEntity::getAccountId, accountId);
			wrapper.eq(CustomerTradeAccountEntity::getBusinessType, businessType);
			wrapper.eq(CustomerTradeAccountEntity::getIsDeleted, 0);
			return baseMapper.selectOne(wrapper);
	}

	@Override
	public List<CustomerTradeAccountEntity> selectTradeByAccount(String accountId) {
		LambdaUpdateWrapper<CustomerTradeAccountEntity> wrapper = new LambdaUpdateWrapper<>();
			wrapper.eq(CustomerTradeAccountEntity::getAccountId, accountId);
			wrapper.eq(CustomerTradeAccountEntity::getIsDeleted, 0);
			return baseMapper.selectList(wrapper);
	}


	private void addSubAccount(OpenAccountReq openAccountReq,CustomerTradeAccountEntity customerTradeAccountEntity) {
		// 港股

		if(1==openAccountReq.getIsOpenHkMarket().intValue()){
			CustomerTradeSubAccountEntity hk =new CustomerTradeSubAccountEntity();
			hk.setAccountId(customerTradeAccountEntity.getAccountId());
			hk.setTradeAccountId(customerTradeAccountEntity.getId());
			hk.setTradeAccount(customerTradeAccountEntity.getTradeAccount());
			hk.setSubAccount(customerTradeAccountEntity.getTradeAccount());
			hk.setMarketType(MarketTypeEnums.HK.getMarketType());
			customerTradeSubAccountService.addSubAccount(hk);
		}
		if(1==openAccountReq.getIsOpenUsMarket().intValue()){
			CustomerTradeSubAccountEntity us =new CustomerTradeSubAccountEntity();
			us.setAccountId(customerTradeAccountEntity.getAccountId());
			us.setTradeAccountId(customerTradeAccountEntity.getId());
			us.setTradeAccount(customerTradeAccountEntity.getTradeAccount());
			us.setSubAccount(customerTradeAccountEntity.getTradeAccount());
			us.setMarketType(MarketTypeEnums.US.getMarketType());
			customerTradeSubAccountService.addSubAccount(us);
		}
		if(1==openAccountReq.getIsOpenCnMarket().intValue()){
			CustomerTradeSubAccountEntity cn =new CustomerTradeSubAccountEntity();
			cn.setAccountId(customerTradeAccountEntity.getAccountId());
			cn.setTradeAccountId(customerTradeAccountEntity.getId());
			cn.setTradeAccount(customerTradeAccountEntity.getTradeAccount());
			cn.setSubAccount(customerTradeAccountEntity.getTradeAccount());
			cn.setMarketType(MarketTypeEnums.ML.getMarketType());
			customerTradeSubAccountService.addSubAccount(cn);
		}

	}

	private void addAgreementInfo(OpenAccountReq openAccountReq, CustomerFinancingAccountEntity financingAccount) {
		CustomerAgreementInfoEntity info =
			customerAgreementInfoService.getByCustomerAndAccount(financingAccount.getCustId(), financingAccount.getAccountId());

		openAccountReq.setIsOpenCnMarket(1);
		openAccountReq.setIsOpenHkMarket(1);
		openAccountReq.setIsOpenUsMarket(1);
		if(null == info){
			info = new CustomerAgreementInfoEntity();
			info.setCustId(financingAccount.getCustId());
			info.setAccountId(financingAccount.getAccountId());
		}
		if(tenantMarket.contains("HK")&& 1 == openAccountReq.getIsOpenHkMarket().intValue()){
			info.setHkdirStatus(1);
			info.setHkdirSignDate(DateUtil.format(new Date(), "yyyy-MM-dd"));
			info.setHkderiStatus(1);
			info.setHkderiSignDate(DateUtil.format(new Date(), "yyyy-MM-dd"));
			info.setHkgemStatus(1);
			info.setHkgemSignDate(DateUtil.format(new Date(), "yyyy-MM-dd"));
			info.setHkvaStatus(1);
			info.setHkvaSignDate(DateUtil.format(new Date(), "yyyy-MM-dd"));

		}
		customerAgreementInfoService.saveOrUpdate(info);

	}
}
