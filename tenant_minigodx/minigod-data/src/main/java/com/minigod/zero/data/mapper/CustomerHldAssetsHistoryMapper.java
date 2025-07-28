package com.minigod.zero.data.mapper;

import com.minigod.zero.data.entity.CustomerHldAssetsHistory;
import com.minigod.zero.data.vo.CustAssetsQuarterVO;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
* @author dell
* @description 针对表【customer_hld_assets_history(用户活利得资产历史记录)】的数据库操作Mapper
* @createDate 2024-09-26 16:13:17
* @Entity com.minigod.zero.report.entity.CustomerHldAssetsHistory
*/
public interface CustomerHldAssetsHistoryMapper {
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
    int insert(CustomerHldAssetsHistory record);
    /**
     * 选择性插入
     * @param record
     * @return
     */
    int insertSelective(CustomerHldAssetsHistory record);

    /**
     * 根据主键查询
     * @param id
     * @return
     */
    CustomerHldAssetsHistory selectByPrimaryKey(Long id);
    /**
     * 选择性更新
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(CustomerHldAssetsHistory record);
    /**
     * 根据主键更新
     * @param record
     * @return
     */
    int updateByPrimaryKey(CustomerHldAssetsHistory record);

	List<CustAssetsQuarterVO> selSumQuarter(@Param("startTime") Date startTime, @Param("endTime") Date marchFirst);
}
