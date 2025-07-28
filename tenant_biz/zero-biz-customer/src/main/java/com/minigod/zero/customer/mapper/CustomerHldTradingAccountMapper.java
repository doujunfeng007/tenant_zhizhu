package com.minigod.zero.customer.mapper;

import com.minigod.zero.customer.entity.CustomerHldTradingAccount;

/**
* @author dell
* @description 针对表【customer_hld_trading_account(活利得交易账户信息表)】的数据库操作Mapper
* @createDate 2024-05-09 09:44:29
* @Entity com.minigod.zero.customer.entity.CustomerHldTradingAccount
*/
public interface CustomerHldTradingAccountMapper {

    int deleteByPrimaryKey(Long id);

    int insert(CustomerHldTradingAccount record);

    int insertSelective(CustomerHldTradingAccount record);

    CustomerHldTradingAccount selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CustomerHldTradingAccount record);

    int updateByPrimaryKey(CustomerHldTradingAccount record);

	CustomerHldTradingAccount selectByCustId(Long custId);
}
