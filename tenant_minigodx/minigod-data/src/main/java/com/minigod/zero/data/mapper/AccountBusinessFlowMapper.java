package com.minigod.zero.data.mapper;

import com.minigod.zero.data.dto.StatementDTO;
import com.minigod.zero.data.entity.AccountBusinessFlow;
import com.minigod.zero.data.entity.FinancingAccountAmountFlows;

import java.util.List;

/**
* @author dell
* @description 针对表【account_business_flow】的数据库操作Mapper
* @createDate 2024-09-26 16:13:17
* @Entity com.minigod.zero.report.entity.AccountBusinessFlow
*/
public interface AccountBusinessFlowMapper {

    int deleteByPrimaryKey(Long id);

    int insert(AccountBusinessFlow record);

    int insertSelective(AccountBusinessFlow record);

    AccountBusinessFlow selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AccountBusinessFlow record);

    int updateByPrimaryKey(AccountBusinessFlow record);

	List<FinancingAccountAmountFlows> selWithdrawList(StatementDTO custStatementDTO);
}
