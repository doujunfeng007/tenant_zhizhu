package com.minigod.zero.customer.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.minigod.zero.customer.entity.FundLatestPriceEntity;
import org.apache.ibatis.annotations.Param;

/**
* @author dell
* @description 针对表【fund_latest_price(基金最新价)】的数据库操作Mapper
* @createDate 2024-05-06 21:11:04
* @Entity com.minigod.zero.customer.entity.FundLatestPrice
*/
@DS("customer")
public interface FundLatestPriceMapper {

    int deleteByPrimaryKey(Long id);

    int insert(FundLatestPriceEntity record);

    int insertSelective(FundLatestPriceEntity record);

    FundLatestPriceEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(FundLatestPriceEntity record);

    int updateByPrimaryKey(FundLatestPriceEntity record);

	FundLatestPriceEntity selectByFundCodeAndDay(@Param("fundCode") String fundCode,
												 @Param("day")String day,
												 @Param("currency")String currency);

}
