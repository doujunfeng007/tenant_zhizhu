package com.minigod.zero.customer.mapper;

import com.minigod.zero.customer.entity.CustomerDebentureCapitalAccount;

/**
* @author dell
* @description 针对表【customer_debenture_capital_account(债券子账户信息表)】的数据库操作Mapper
* @createDate 2024-12-23 14:02:55
* @Entity com.minigod.zero.customer.entity.CustomerDebentureCapitalAccount
*/
public interface CustomerDebentureCapitalAccountMapper {

    int deleteByPrimaryKey(Long id);

    int insert(CustomerDebentureCapitalAccount record);

    int insertSelective(CustomerDebentureCapitalAccount record);

    CustomerDebentureCapitalAccount selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CustomerDebentureCapitalAccount record);

    int updateByPrimaryKey(CustomerDebentureCapitalAccount record);

}
