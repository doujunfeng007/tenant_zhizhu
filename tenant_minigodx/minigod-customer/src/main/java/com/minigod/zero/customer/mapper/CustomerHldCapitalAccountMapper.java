package com.minigod.zero.customer.mapper;

import com.minigod.zero.customer.entity.CustomerFundCapitalAccountEntity;
import com.minigod.zero.customer.entity.CustomerHldCapitalAccount;

import java.util.List;

/**
* @author dell
* @description 针对表【customer_hld_capital_account(活利得子账户信息表)】的数据库操作Mapper
* @createDate 2024-05-09 09:44:29
* @Entity com.minigod.zero.customer.entity.CustomerHldCapitalAccount
*/
public interface CustomerHldCapitalAccountMapper {

    int deleteByPrimaryKey(Long id);

    int insert(CustomerHldCapitalAccount record);

    int insertSelective(CustomerHldCapitalAccount record);

    CustomerHldCapitalAccount selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CustomerHldCapitalAccount record);

    int updateByPrimaryKey(CustomerHldCapitalAccount record);

	List<CustomerHldCapitalAccount> selectSubAccountByCustId(Long custId);

	List<CustomerHldCapitalAccount> selectByAccountId(String accountId);
}
