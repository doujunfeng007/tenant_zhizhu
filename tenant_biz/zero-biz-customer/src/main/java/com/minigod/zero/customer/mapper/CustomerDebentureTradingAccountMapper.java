package com.minigod.zero.customer.mapper;

import com.minigod.zero.customer.entity.CustomerDebentureTradingAccount;

/**
* @author dell
* @description 针对表【customer_debenture_trading_account(债券交易账户信息表)】的数据库操作Mapper
* @createDate 2024-12-23 14:02:55
* @Entity com.minigod.zero.customer.entity.CustomerDebentureTradingAccount
*/
public interface CustomerDebentureTradingAccountMapper {

    int deleteByPrimaryKey(Long id);

    int insert(CustomerDebentureTradingAccount record);

    int insertSelective(CustomerDebentureTradingAccount record);

    CustomerDebentureTradingAccount selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CustomerDebentureTradingAccount record);

    int updateByPrimaryKey(CustomerDebentureTradingAccount record);

	CustomerDebentureTradingAccount selectByCustId(Long custId);

}
