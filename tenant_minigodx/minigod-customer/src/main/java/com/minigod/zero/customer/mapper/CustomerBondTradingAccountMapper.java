package com.minigod.zero.customer.mapper;

import com.minigod.zero.customer.entity.CustomerBondTradingAccount;

/**
* @author dell
* @description 针对表【customer_bond_trading_account(债券交易账户信息表)】的数据库操作Mapper
* @createDate 2024-05-09 09:44:05
* @Entity com.minigod.zero.customer.entity.CustomerBondTradingAccount
*/
public interface CustomerBondTradingAccountMapper {

    int deleteByPrimaryKey(Long id);

    int insert(CustomerBondTradingAccount record);

    int insertSelective(CustomerBondTradingAccount record);

    CustomerBondTradingAccount selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CustomerBondTradingAccount record);

    int updateByPrimaryKey(CustomerBondTradingAccount record);

	CustomerBondTradingAccount selectByCustId(Long userId);
}
