package com.minigod.zero.customer.mapper;

import com.minigod.zero.customer.entity.CustomerBondAssetsHistory;

/**
* @author dell
* @description 针对表【customer_bond_assets_history(用户债券易资产历史记录)】的数据库操作Mapper
* @createDate 2024-05-27 20:11:00
* @Entity com.minigod.zero.customer.entity.CustomerBondAssetsHistory
*/
public interface CustomerBondAssetsHistoryMapper {

    int deleteByPrimaryKey(Long id);

    int insert(CustomerBondAssetsHistory record);

    int insertSelective(CustomerBondAssetsHistory record);

    CustomerBondAssetsHistory selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CustomerBondAssetsHistory record);

    int updateByPrimaryKey(CustomerBondAssetsHistory record);

}
