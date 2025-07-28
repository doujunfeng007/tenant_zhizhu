package com.minigod.zero.customer.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.minigod.zero.customer.entity.CustomerCashAssetsHistory;
import com.minigod.zero.customer.vo.CustAssetsQuarterVO;
import com.minigod.zero.customer.vo.CustomerCashAssetsVO;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
* @author dell
* @description 针对表【customer_cash_assets_history(用户现金资产历史记录)】的数据库操作Mapper
* @createDate 2024-05-27 21:04:48
* @Entity com.minigod.zero.customer.entity.CustomerCashAssetsHistory
*/
public interface CustomerCashAssetsHistoryMapper {

    int deleteByPrimaryKey(Long id);

    int insert(CustomerCashAssetsHistory record);

    int insertSelective(CustomerCashAssetsHistory record);

    CustomerCashAssetsHistory selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CustomerCashAssetsHistory record);

    int updateByPrimaryKey(CustomerCashAssetsHistory record);

	List<CustomerCashAssetsVO> accountAssetSummaryReport(Page page,
														 @Param("startTime")String startTime,
														 @Param("endTime")String endTime,
														 @Param("accountId")String accountId,
														 @Param("currency")String currency,
														 @Param("ids")List<String> ids);

	CustomerCashAssetsHistory  selectCashAssetsHistoryByAccountAndDay(@Param("accountId")String accountId,@Param("day")String day);

	List<CustomerCashAssetsHistory> selectByAccountId(String accountId);

    BigDecimal selAverageAssets(@Param("custId") Long custId, @Param("newStartTime") Date newStartTime, @Param("newEndTime") Date newEndTime);

	List<CustAssetsQuarterVO> selAssetsQuarter(Date startTime, Date marchFirst);
}
