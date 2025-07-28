package com.minigod.zero.bpmn.module.deposit.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.minigod.zero.bpmn.module.deposit.bo.FundDepositApplicationQuery;
import com.minigod.zero.bpmn.module.deposit.entity.SecDepositFundsEntity;
import com.minigod.zero.bpmn.module.deposit.vo.DepositRecordVO;
import com.minigod.zero.bpmn.module.deposit.vo.MoneySumVO;
import com.minigod.zero.bpmn.module.deposit.vo.SecDepositFundsVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.bpmn.module.report.vo.DepositAndWithdrawalFundsReportVO;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 存入资金表 Mapper 接口
 *
 * @author 掌上智珠
 * @since 2024-03-01
 */
public interface SecDepositFundsMapper extends BaseMapper<SecDepositFundsEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param secDepositFundsVO
	 * @return
	 */
	List<SecDepositFundsVO> selectSecDepositFundsPage(IPage page, SecDepositFundsVO secDepositFundsVO);

	/**
	 * 存入资金
	 *
	 * @param custId
	 * @param tenantId
	 * @return
	 */
	MoneySumVO allDepositFunds(Long custId, String tenantId);


	List<DepositRecordVO> queryPage(IPage page, @Param("applicationQuery") FundDepositApplicationQuery applicationQuery);

	List<DepositAndWithdrawalFundsReportVO> queryReportPage(Page page,
															@Param("startTime") String startTime,
															@Param("endTime") String endTime,
															@Param("clientId") String clientId,
															@Param("currency") Integer currency,
															@Param("depositStatus") Integer depositStatus,
															@Param("withdrawalStatus") Integer withdrawalStatus,
															@Param("applicationIdList") List<String> applicationIdList,
															@Param("type") Integer type);

	DepositRecordVO queryDepositRecordDetail(String applicationId);


	int updateSettlementTimeByApplicationId(@Param("time") Date time, @Param("applicationId") String applicationId);

}
