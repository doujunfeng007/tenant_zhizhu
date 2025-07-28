package com.minigod.zero.data.mapper;

import com.minigod.zero.data.dto.CustStatementDTO;
import com.minigod.zero.data.entity.FinancingAccountAmountFlows;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
* @author eric
* @description 针对表【financing_account_amount_flows(理财账户金额流水)】的数据库操作Mapper
* @createDate 2024-09-26 16:13:17
* @Entity com.minigod.zero.report.entity.FinancingAccountAmountFlows
*/
@Mapper
public interface FinancingAccountAmountFlowsMapper {
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
    int insert(FinancingAccountAmountFlows record);
    /**
     * 选择性插入
     * @param record
     * @return
     */
    int insertSelective(FinancingAccountAmountFlows record);
    /**
     * 根据主键查询
     * @param id
     * @return
     */
    FinancingAccountAmountFlows selectByPrimaryKey(Long id);
    /**
     * 选择性更新
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(FinancingAccountAmountFlows record);
    /**
     * 根据主键更新
     * @param record
     * @return
     */
    int updateByPrimaryKey(FinancingAccountAmountFlows record);

    /**
     * 获取今日流水
     * @param accountId
     * @param currency
     * @param date
     * @return
     */
	List<FinancingAccountAmountFlows> getTodayFlows(@Param("accountId") String accountId,
													@Param("currency") String currency,
													@Param("date") String date);


	List<String> selectCustByFlowList(@Param("startTime") Date startTime, @Param("endTime") Date endTime, @Param("businessTypeList") List<Integer> businessTypeList);


	List<FinancingAccountAmountFlows> selectFlowList(@Param("custStatementDTO") CustStatementDTO custStatementDTO, @Param("operationTypeList") ArrayList<Integer> operationTypeList, @Param("statusList") ArrayList<Integer> statusList);



}
