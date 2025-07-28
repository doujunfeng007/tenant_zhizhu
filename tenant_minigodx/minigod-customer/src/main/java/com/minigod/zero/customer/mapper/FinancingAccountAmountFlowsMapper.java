package com.minigod.zero.customer.mapper;

import cn.hutool.core.date.DateTime;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.customer.dto.CustStatementDTO;
import com.minigod.zero.customer.entity.CustomerInfoEntity;
import com.minigod.zero.customer.entity.FinancingAccountAmount;
import com.minigod.zero.customer.entity.FinancingAccountAmountFlows;
import com.minigod.zero.customer.vo.CapitalFlowVO;
import com.minigod.zero.customer.vo.CustFlowCurrencyVO;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
* @author dell
* @description 针对表【financing_account_amount_flows(理财账户金额流水)】的数据库操作Mapper
* @createDate 2024-04-24 15:27:30
* @Entity com.minigod.zero.customer.entity.FinancingAccountAmountFlows
*/
@DS("customer")
public interface FinancingAccountAmountFlowsMapper extends BaseMapper<FinancingAccountAmountFlows> {

    int deleteByPrimaryKey(Long id);

    int insert(FinancingAccountAmountFlows record);

    int insertSelective(FinancingAccountAmountFlows record);

    FinancingAccountAmountFlows selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(FinancingAccountAmountFlows record);

    int updateByPrimaryKey(FinancingAccountAmountFlows record);

	/**
	 * app分页查询
	 * @param currency
	 * @param starTime
	 * @param endTime
	 * @return
	 */
	List<FinancingAccountAmountFlows> querypage(@Param("accountId") String accountId,
												@Param("currency") String currency,
												@Param("starTime")String starTime,
												@Param("endTime")String endTime);


	List<FinancingAccountAmountFlows> queryList(IPage<CapitalFlowVO> page,@Param("accountId") String accountId);



	List<String> selectCustByFlowList(@Param("startTime") Date startTime, @Param("endTime") Date endTime, @Param("businessTypeList") List<Integer> businessTypeList);



	List<FinancingAccountAmountFlows> selectSameDayFlowsByAccountIdAndCurrency(@Param("accountId")String accountId,
																			   @Param("currency")String currency,
																			   @Param("date")String date);

	List<FinancingAccountAmountFlows> selectByAccountId(String accountId);


	List<FinancingAccountAmountFlows> selectByAccountBusinessFlowId(Integer accountBusinessFlowId);

	List<FinancingAccountAmountFlows> selectThatTimeFlowsByCurrency(@Param("accountId")String accountId,
																	@Param("currency")String currency,
																	@Param("date")String date);

	List<FinancingAccountAmountFlows> selectFlowList(@Param("custStatementDTO") CustStatementDTO custStatementDTO, @Param("operationTypeList") ArrayList<Integer> operationTypeList, @Param("statusList") ArrayList<Integer> statusList);
}
