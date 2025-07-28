package com.minigod.zero.data.mapper;

import com.minigod.zero.data.entity.CustomerCashAssetsHistory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author dell
* @description 针对表【customer_cash_assets_history(用户现金资产历史记录)】的数据库操作Mapper
* @createDate 2024-09-26 16:13:17
* @Entity com.minigod.zero.report.entity.CustomerCashAssetsHistory
*/
@Mapper
public interface CustomerCashAssetsHistoryMapper {
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
    int insert(CustomerCashAssetsHistory record);
    /**
     * 选择性插入
     * @param record
     * @return
     */
    int insertSelective(CustomerCashAssetsHistory record);

    /**
     * 根据主键查询
     * @param id
     * @return
     */
    CustomerCashAssetsHistory selectByPrimaryKey(Long id);
    /**
     * 选择性更新
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(CustomerCashAssetsHistory record);

    /**
     * 根据主键更新
     * @param record
     * @return
     */
    int updateByPrimaryKey(CustomerCashAssetsHistory record);

    /**
     * 获取最小统计时间
     * @return
     */
	String getMinStatisticalTime();

	/**
	 * 获取最大统计时间
	 * @return
	 */
	String getMaxStatisticalTime();
    /**
     * 根据统计时间查询现金资产历史记录
     * @param statisticalTime
     * @return
     */
	List<CustomerCashAssetsHistory> selectCashAssetsHistoryByStatisticalTime(String statisticalTime);

	/**
	 * 根据账户ID和统计时间查询现金资产历史记录
	 * @param accountId
	 * @param statisticalTime
	 * @return
	 */
	CustomerCashAssetsHistory selectByAccountIdAndStatisticalTime(@Param("accountId") String accountId,
																  @Param("statisticalTime")String statisticalTime);
}
