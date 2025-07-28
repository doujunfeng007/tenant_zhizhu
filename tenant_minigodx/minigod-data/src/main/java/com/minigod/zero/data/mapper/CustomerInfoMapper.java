package com.minigod.zero.data.mapper;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.data.dto.BrokerOrderInfoDTO;
import com.minigod.zero.data.dto.CustStatementDTO;
import com.minigod.zero.data.dto.OrderInfoDTO;
import com.minigod.zero.data.dto.StatementListDTO;
import com.minigod.zero.data.entity.CustomerCashAssetsHistory;
import com.minigod.zero.data.entity.CustomerInfo;
import com.minigod.zero.data.vo.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author eric
 * @description 针对表【customer_info(客户信息表)】的数据库操作Mapper
 * @createDate 2024-09-26 16:13:17
 * @Entity com.minigod.zero.report.entity.CustomerInfo
 */
@Mapper
public interface CustomerInfoMapper {
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
	int insert(CustomerInfo record);

	/**
	 * 选择性插入
	 *
	 * @param record
	 * @return
	 */
	int insertSelective(CustomerInfo record);

	/**
	 * 根据主键查询
	 *
	 * @param id
	 * @return
	 */
	CustomerInfo selectByPrimaryKey(Long id);

	/**
	 * 选择性更新
	 *
	 * @param record
	 * @return
	 */
	int updateByPrimaryKeySelective(CustomerInfo record);

	/**
	 * 根据主键更新
	 *
	 * @param record
	 * @return
	 */
	int updateByPrimaryKey(CustomerInfo record);

	/**
	 * 统计客户信息
	 *
	 * @return
	 */
	CustomerInfoForPICountVO statisticsByPiLevel();

	/**
	 * 统计PI客户总数
	 *
	 * @return
	 */
	Integer statisticsPiUserCount();



	List<HldHoldingHistoryStatementDailyVO> hldHoldingHistoryList(@Param("custStatementDTO") CustStatementDTO custStatementDTO);
	List<HldHoldingHistoryStatementDailyVO> fundHoldingHistoryList(@Param("custStatementDTO") CustStatementDTO custStatementDTO);

	List<String> selHldOrderList(@Param("startTime") DateTime startTime, @Param("endTime") DateTime endTime);

	List<String> selBondOrderList(@Param("startTime") DateTime startTime, @Param("endTime") DateTime endTime);
	List<String> selFundOrderList(@Param("startTime") DateTime startTime, @Param("endTime") DateTime endTime);

	BigDecimal hldHoldingHistoryByFundCode(@Param("productId") String productId, @Param("custStatementDTO") CustStatementDTO custStatementDTO);
	BigDecimal fundHoldingHistoryByFundCode(@Param("productId") String productId, @Param("custStatementDTO") CustStatementDTO custStatementDTO);

	BigDecimal getAverageCost(@Param("custStatementDTO") CustStatementDTO custStatementDTO, @Param("productId") String productId);

	List<CustomerOpenAccountVO> customerStatementList(IPage<CustomerOpenAccountVO> page, @Param("statementListDTO") StatementListDTO statementListDTO);

	List<String> selCashByCustList(@Param("startTime") DateTime startTime, @Param("endTime") DateTime endTime);

	List<BrokerOrderInfoDTO> selHldOrderInfoList(@Param("orderInfoDTO") OrderInfoDTO orderInfoDTO);

	List<BrokerOrderInfoDTO> selBondOrderInfoList(@Param("orderInfoDTO") OrderInfoDTO orderInfoDTO);

	CustomerCashAssetsHistory selCashBalanceList(@Param("date") String date, @Param("custId") String custId);

	CustStatementVO selCustHldStatement(String id);
	CustStatementVO selCustBondStatement(String id);
	CustStatementVO selCustFundStatement(String custId);

	//债券易活力得  汇总日cust列表
	List<String> selCustByDateList(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
	List<String> selCustList();
	//债券易活力得  汇总月cust列表
	List<String> selCustMonthList(@Param("startDate") Date startDate, @Param("endDate") Date endDate);


	//活利得月结单持仓总览
	List<HldHoldingStatementMonthVO> hldHoldingStatementList(@Param("custStatementDTO") CustStatementDTO custStatementDTO);

	//活利得日结单-活利得交易明细-活利得买入确认单
	List<HldTradingBuyStatementDailyVO> hldTradingStatementList(@Param("custStatementDTO") CustStatementDTO custStatementDTO);
	//活利得日结单-活利得交易明细-活利得卖出确认单
	List<HldTradingSaleStatementDailyVO> hldTradingSaleStatementList(@Param("custStatementDTO") CustStatementDTO custStatementDTO);

	//活利得日结单-2-2.活利得持仓总览
	List<HldHoldingHistoryStatementDailyVO> hldHoldingHistoryStatementList(@Param("custStatementDTO") CustStatementDTO custStatementDTO);

	//基金买入确认单
	List<FundTradingBuyStatementDailyVO> fundTradingStatementList(@Param("custStatementDTO") CustStatementDTO custStatementDTO);
	//基金卖出确认单
	List<FundTradingSaleStatementDailyVO> fundTradingSaleStatementList(@Param("custStatementDTO") CustStatementDTO custStatementDTO);


}
