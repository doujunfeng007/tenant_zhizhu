package com.minigod.zero.customer.mapper;

import com.minigod.zero.customer.entity.CustomerBondCapitalAccount;

import java.util.List;

/**
* @author dell
* @description 针对表【customer_bond_capital_account(债券易子账户信息表)】的数据库操作Mapper
* @createDate 2024-05-09 09:44:05
* @Entity com.minigod.zero.customer.entity.CustomerBondCapitalAccount
*/
public interface CustomerBondCapitalAccountMapper {

    int deleteByPrimaryKey(Long id);

    int insert(CustomerBondCapitalAccount record);

    int insertSelective(CustomerBondCapitalAccount record);

    CustomerBondCapitalAccount selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CustomerBondCapitalAccount record);

    int updateByPrimaryKey(CustomerBondCapitalAccount record);

	List<CustomerBondCapitalAccount> selectSubAccountByCustId(Long custId);

	List<CustomerBondCapitalAccount> selectByAccountId(String accountId);
}
