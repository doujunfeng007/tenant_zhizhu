package com.minigod.zero.customer.back.service.impl;

import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.utils.BeanUtil;
import com.minigod.zero.customer.back.service.ICustomerTradeAccountService;
import com.minigod.zero.customer.back.service.IStockService;
import com.minigod.zero.customer.emuns.BusinessTypeEnums;
import com.minigod.zero.customer.entity.CustomerFinancingAccountEntity;
import com.minigod.zero.customer.entity.CustomerTradeAccountEntity;
import com.minigod.zero.customer.mapper.CustomerFinancingAccountMapper;
import com.minigod.zero.customer.vo.CustomerTradeAccountVO;
import com.minigod.zero.trade.feign.ICounterFundClient;
import com.minigod.zero.trade.vo.sjmb.resp.JournalOrdersVO;
import com.minigod.zero.trade.vo.sjmb.resp.TotalAssetInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chen
 * @ClassName StockServiceImpl.java
 * @Description TODO
 * @createTime 2025年01月02日 17:47:00
 */
@Service
@Slf4j
public class StockServiceImpl implements IStockService {

	@Autowired
	private ICustomerTradeAccountService customerTradeAccountService;

	@Autowired
	private CustomerFinancingAccountMapper customerFinancingAccountMapper;

	@Autowired
	private ICounterFundClient counterFundClient;

	@Override
	public R<CustomerTradeAccountVO> customerAccountInfo(Long custId, String businessType) {
		//统一账号
		CustomerFinancingAccountEntity financingAccount = customerFinancingAccountMapper.selectByCustId(custId);
		CustomerTradeAccountEntity tradeAccount = customerTradeAccountService.selectTradeByAccountAndType(financingAccount.getAccountId(), businessType);
		if(null == tradeAccount){
			return R.data(new CustomerTradeAccountVO());
		}
		CustomerTradeAccountVO tradeAccountVO = BeanUtil.copy(tradeAccount, CustomerTradeAccountVO.class);
		return R.data(tradeAccountVO);
	}

	@Override
	public R<TotalAssetInfoVO> customerPositionList(Long custId) {
		CustomerFinancingAccountEntity financingAccount = customerFinancingAccountMapper.selectByCustId(custId);
		CustomerTradeAccountEntity tradeAccount = customerTradeAccountService.selectTradeByAccountAndType(financingAccount.getAccountId(),  BusinessTypeEnums.SEC.getBusinessType());
        if(null == tradeAccount){
			return R.data(new TotalAssetInfoVO());
		}
		return counterFundClient.customerPositionList(tradeAccount.getReletionTradeAccount(),tradeAccount.getTradeAccount());
	}

	@Override
	public R<List<JournalOrdersVO>> distributionRecords(Long custId, String startDate, String endDate) {
		CustomerFinancingAccountEntity financingAccount = customerFinancingAccountMapper.selectByCustId(custId);
		CustomerTradeAccountEntity tradeAccount = customerTradeAccountService.selectTradeByAccountAndType(financingAccount.getAccountId(),  BusinessTypeEnums.SEC.getBusinessType());
		if(null == tradeAccount){
			return R.data(new ArrayList<>());
		}
		return counterFundClient.customerHistoryOrders(tradeAccount.getReletionTradeAccount(),tradeAccount.getTradeAccount(),
			startDate,endDate);
	}
}
