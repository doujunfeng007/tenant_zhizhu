package com.minigod.zero.customer.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.minigod.zero.customer.entity.FundTradingAccountAmountFlows;

/**
* @author dell
* @description 针对表【fund_trading_account_amount_flows(基金账户金额流水)】的数据库操作Mapper
* @createDate 2024-04-24 15:27:30
* @Entity com.minigod.zero.customer.entity.FundTradingAccountAmountFlows
*/
@DS("customer")
public interface FundTradingAccountAmountFlowsMapper {

    int deleteByPrimaryKey(Long id);

    int insert(FundTradingAccountAmountFlows record);

    int insertSelective(FundTradingAccountAmountFlows record);

    FundTradingAccountAmountFlows selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(FundTradingAccountAmountFlows record);

    int updateByPrimaryKey(FundTradingAccountAmountFlows record);

}
