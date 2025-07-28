package com.minigod.zero.customer.mapper;

import com.minigod.zero.customer.entity.CustomerFundAssetsHistory;
import com.minigod.zero.customer.vo.CustAssetsQuarterVO;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
* @author dell
* @description 针对表【customer_fund_assets_history(用户基金资产历史记录)】的数据库操作Mapper
* @createDate 2024-05-27 20:11:00
* @Entity com.minigod.zero.customer.entity.CustomerFundAssetsHistory
*/
public interface CustomerFundAssetsHistoryMapper {

    int deleteByPrimaryKey(Long id);

    int insert(CustomerFundAssetsHistory record);

    int insertSelective(CustomerFundAssetsHistory record);

    CustomerFundAssetsHistory selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CustomerFundAssetsHistory record);

    int updateByPrimaryKey(CustomerFundAssetsHistory record);

    BigDecimal selSumAssets(@Param("custId") Long custId, @Param("startTime") Date newStartTime, @Param("endTime") Date newEndTime);

	List<CustAssetsQuarterVO> selSumQuarter(@Param("startTime") Date startTime, @Param("endTime") Date endTime);
}
