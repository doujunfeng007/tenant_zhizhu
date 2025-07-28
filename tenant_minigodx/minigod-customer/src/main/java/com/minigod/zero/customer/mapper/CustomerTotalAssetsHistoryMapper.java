package com.minigod.zero.customer.mapper;

import com.minigod.zero.customer.entity.CustomerTotalAssetsHistory;

import java.util.List;

/**
* @author dell
* @description 针对表【customer_total_assets_history(用户资产历史)】的数据库操作Mapper
* @createDate 2024-05-28 12:06:40
* @Entity com.minigod.zero.customer.entity.CustomerTotalAssetsHistory
*/
public interface CustomerTotalAssetsHistoryMapper {

    int deleteByPrimaryKey(Long id);

    int insert(CustomerTotalAssetsHistory record);

    int insertSelective(CustomerTotalAssetsHistory record);

    CustomerTotalAssetsHistory selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CustomerTotalAssetsHistory record);

    int updateByPrimaryKey(CustomerTotalAssetsHistory record);

	List<CustomerTotalAssetsHistory> selectByAccountId(String accountId);

}
