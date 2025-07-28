package com.minigod.zero.customer.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.minigod.zero.customer.entity.CustomerStockCapitalAccountEntity;

/**
* @author dell
* @description 针对表【customer_stock_capital_account(客户资金账号信息表)】的数据库操作Mapper
* @createDate 2024-04-19 16:04:22
* @Entity com.minigod.zero.customer.entity.CustomerStockCapitalAccount
*/
@DS("customer")
public interface CustomerStockCapitalAccountMapper {

    int deleteByPrimaryKey(Long id);

    int insert(CustomerStockCapitalAccountEntity record);

    int insertSelective(CustomerStockCapitalAccountEntity record);

    CustomerStockCapitalAccountEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CustomerStockCapitalAccountEntity record);

    int updateByPrimaryKey(CustomerStockCapitalAccountEntity record);

}
