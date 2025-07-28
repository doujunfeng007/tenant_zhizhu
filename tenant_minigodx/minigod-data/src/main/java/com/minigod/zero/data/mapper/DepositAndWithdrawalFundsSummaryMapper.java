package com.minigod.zero.data.mapper;

import com.minigod.zero.data.dto.DepositAndWithdrawalStatisticsDTO;
import com.minigod.zero.data.vo.DepositAndWithdrawalFundsRecordVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 出入金明细及总额统计
 *
 * @author eric
 * @date 2024-10-30 11:21:05
 */
@Mapper
public interface DepositAndWithdrawalFundsSummaryMapper {

	/**
	 * 查询出入金明细及总额统计
	 *
	 * @param depositAndWithdrawalStatisticsDTO
	 * @return
	 */
	List<DepositAndWithdrawalFundsRecordVO> queryDepositAndWithdrawalFundsSummary(@Param("depositAndWithdrawalStatisticsDTO") DepositAndWithdrawalStatisticsDTO depositAndWithdrawalStatisticsDTO);

	/**
	 * 统计各币种入金总额
	 *
	 * @return 各币种入金总额统计结果
	 */
	List<Map<String, Object>> selectDepositTotalAmount();

	/**
	 * 统计各币种出金总额
	 *
	 * @return 各币种出金总额统计结果
	 */
	List<Map<String, Object>> selectWithdrawalTotalAmount();

	/**
	 * 统计手工入金申请数
	 */
	Long countDepositApply();

	/**
	 * 统计出金申请数
	 */
	Long countWithdrawalApply();
}
