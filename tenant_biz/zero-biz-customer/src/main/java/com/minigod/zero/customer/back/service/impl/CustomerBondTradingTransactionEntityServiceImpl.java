package com.minigod.zero.customer.back.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minigod.zero.customer.entity.CustomerBondTradingTransaction;
import com.minigod.zero.customer.back.service.CustomerBondTradingTransactionEntityService;
import com.minigod.zero.customer.mapper.CustomerBondTradingTransactionMapper;
import org.springframework.stereotype.Service;

/**
* @author dell
* @description 针对表【customer_bond_trading_transaction(债券交易流水历史表)】的数据库操作Service实现
* @createDate 2024-05-23 23:21:45
*/
@Service
public class CustomerBondTradingTransactionEntityServiceImpl extends ServiceImpl<CustomerBondTradingTransactionMapper, CustomerBondTradingTransaction>
    implements CustomerBondTradingTransactionEntityService{

}




