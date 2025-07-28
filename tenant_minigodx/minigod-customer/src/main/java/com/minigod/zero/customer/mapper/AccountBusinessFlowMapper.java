package com.minigod.zero.customer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.minigod.zero.customer.dto.CustStatementDTO;
import com.minigod.zero.customer.dto.StatementDTO;
import com.minigod.zero.customer.entity.AccountBusinessFlow;
import com.minigod.zero.customer.entity.FinancingAccountAmountFlows;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author dell
* @description 针对表【account_business_Flow】的数据库操作Mapper
* @createDate 2024-07-22 18:57:37
* @Entity com.minigod.zero.customer.entity.AccountBusinessFlow
*/
public interface AccountBusinessFlowMapper extends BaseMapper<AccountBusinessFlow> {

    int deleteByPrimaryKey(Long id);

    int insert(AccountBusinessFlow record);

    int insertSelective(AccountBusinessFlow record);

    AccountBusinessFlow selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AccountBusinessFlow record);

    int updateByPrimaryKey(AccountBusinessFlow record);

	List<AccountBusinessFlow> selectSameDayFlowsByAccountIdAndCurrency(@Param("accountId")String accountId,
																			   @Param("currency")String currency,
																			   @Param("date")String date);
	List<AccountBusinessFlow> selectByAccountId(String accountId);

	List<FinancingAccountAmountFlows> selWithdrawList(StatementDTO custStatementDTO);

	AccountBusinessFlow selectByBusinessIdAndSource(@Param("businessId")String businessId,
													@Param("source")String source,
													@Param("businessType") Integer businessType);
}
