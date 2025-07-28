package com.minigod.zero.data.mapper;

import com.minigod.zero.data.entity.CustomerBondAssetsHistory;

/**
* @author dell
* @description 针对表【customer_bond_assets_history(用户债券易资产历史记录)】的数据库操作Mapper
* @createDate 2024-09-26 16:13:17
* @Entity com.minigod.zero.report.entity.CustomerBondAssetsHistory
*/
public interface CustomerBondAssetsHistoryMapper {
    /**
     * 根据主键删除
     *
     * @param id
     * @return
     */
    int deleteByPrimaryKey(Long id);

    /**
     * 插入
     *
     * @param record
     * @return
     */
    int insert(CustomerBondAssetsHistory record);

    /**
     * 选择性插入
     *
     * @param record
     * @return
     */
    int insertSelective(CustomerBondAssetsHistory record);

    /**
     * 根据主键查询
     *
     * @param id
     * @return
     */
    CustomerBondAssetsHistory selectByPrimaryKey(Long id);

    /**
     * 选择性更新
     *
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(CustomerBondAssetsHistory record);

    /**
     * 根据主键更新
     *
     * @param record
     * @return
     */
    int updateByPrimaryKey(CustomerBondAssetsHistory record);

}
