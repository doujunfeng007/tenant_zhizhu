package com.minigod.zero.customer.mapper;

import com.minigod.zero.customer.entity.CustomerHldAssetsHistory;
import com.minigod.zero.customer.vo.CustAssetsQuarterVO;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
* @author dell
* @description 针对表【customer_hld_assets_history(用户活利得资产历史记录)】的数据库操作Mapper
* @createDate 2024-05-27 20:11:00
* @Entity com.minigod.zero.customer.entity.CustomerHldAssetsHistory
*/
public interface CustomerHldAssetsHistoryMapper {

    int deleteByPrimaryKey(Long id);

    int insert(CustomerHldAssetsHistory record);

    int insertSelective(CustomerHldAssetsHistory record);

    CustomerHldAssetsHistory selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CustomerHldAssetsHistory record);

    int updateByPrimaryKey(CustomerHldAssetsHistory record);

    BigDecimal selSumAssets(@Param("custId") Long custId, @Param("startTime") Date createTime, @Param("endTime") Date newEndTime);

	List<CustAssetsQuarterVO> selSumQuarter(@Param("startTime") Date startTime, @Param("endTime") Date marchFirst);
}
