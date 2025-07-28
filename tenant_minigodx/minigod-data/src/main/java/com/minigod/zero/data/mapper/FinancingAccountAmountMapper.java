package com.minigod.zero.data.mapper;

import com.minigod.zero.data.entity.FinancingAccountAmount;

/**
* @author eric
* @description 针对表【financing_account_amount(理财账号账户金额)】的数据库操作Mapper
* @createDate 2024-09-26 16:13:17
* @Entity com.minigod.zero.report.entity.FinancingAccountAmount
*/
public interface FinancingAccountAmountMapper {
    /**
     * 根据主键删除
     * @param id
     * @return
     */
    int deleteByPrimaryKey(Long id);
    /**
     * 插入
     * @param record
     * @return
     */
    int insert(FinancingAccountAmount record);
    /**
     * 选择性插入
     * @param record
     * @return
     */
    int insertSelective(FinancingAccountAmount record);
    /**
     * 根据主键查询
     * @param id
     * @return
     */
    FinancingAccountAmount selectByPrimaryKey(Long id);
    /**
     * 选择性更新
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(FinancingAccountAmount record);
    /**
     * 根据主键更新
     * @param record
     * @return
     */
    int updateByPrimaryKey(FinancingAccountAmount record);

}
