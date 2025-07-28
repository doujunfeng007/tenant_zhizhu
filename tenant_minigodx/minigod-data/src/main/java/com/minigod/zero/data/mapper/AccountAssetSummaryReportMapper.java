package com.minigod.zero.data.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.data.entity.AccountAssetSummaryReport;
import com.minigod.zero.data.dto.AccountAssetSummaryReportDTO;
import com.minigod.zero.data.vo.SummaryVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 账户资产汇总 Mapper
 *
 * @author dell
 * @description 针对表【account_asset_summary_report(客户账户资产汇总)】的数据库操作Mapper
 * @createDate 2024-10-22 19:44:45
 * @Entity com.minigod.zero.entity.AccountAssetSummaryReport
 */
@Mapper
public interface AccountAssetSummaryReportMapper {
	/**
	 * 根据主键删除数据
	 *
	 * @param id
	 * @return
	 */
	int deleteByPrimaryKey(Long id);

	/**
	 * 插入一条数据
	 *
	 * @param record
	 * @return
	 */
	int insert(AccountAssetSummaryReport record);

	/**
	 * 插入不为空的列数据
	 *
	 * @param record
	 * @return
	 */
	int insertSelective(AccountAssetSummaryReport record);

	/**
	 * 根据主键查询数据
	 *
	 * @param id
	 * @return
	 */
	AccountAssetSummaryReport selectByPrimaryKey(Long id);

	/**
	 * 根据主键修改不为空的列数据
	 *
	 * @param record
	 * @return
	 */
	int updateByPrimaryKeySelective(AccountAssetSummaryReport record);

	/**
	 * 根据主键修改数据
	 *
	 * @param record
	 * @return
	 */
	int updateByPrimaryKey(AccountAssetSummaryReport record);

	/**
	 * 获取最小的结算日期
	 *
	 * @return
	 */
	String getMinFlingDate();

	/**
	 * 获取最大的结算日期
	 *
	 * @return
	 */
	String getMaxFlingDate();

	/**
	 * 批量插入或更新统计结果
	 *
	 * @param list
	 */
	void batchInsertOrUpdate(List<AccountAssetSummaryReport> list);

	/**
	 * 客户账户资产汇总报表
	 *
	 * @param page
	 * @param reportDTO
	 * @return
	 */
	List<AccountAssetSummaryReport> queryPage(IPage page, @Param("reportDTO") AccountAssetSummaryReportDTO reportDTO);

	/**
	 * 账户资产汇总统计
	 *
	 * @param reportDTO
	 * @return
	 */
	List<SummaryVO> selectCustomerFundDetailsSummary(@Param("reportDTO") AccountAssetSummaryReportDTO reportDTO);

}
