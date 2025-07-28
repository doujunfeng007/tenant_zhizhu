package com.minigod.zero.customer.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.minigod.zero.customer.entity.FundCapitalAccountAmountFlows;

/**
* @author dell
* @description 针对表【fund_capital_account_amount_flows(基金账户金额流水)】的数据库操作Mapper
* @createDate 2024-04-24 15:27:30
* @Entity com.minigod.zero.customer.entity.FundCapitalAccountAmountFlows
*/
@DS("customer")
public interface FundCapitalAccountAmountFlowsMapper {

    int deleteByPrimaryKey(Long id);

    int insert(FundCapitalAccountAmountFlows record);

    int insertSelective(FundCapitalAccountAmountFlows record);

    FundCapitalAccountAmountFlows selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(FundCapitalAccountAmountFlows record);

    int updateByPrimaryKey(FundCapitalAccountAmountFlows record);

}
