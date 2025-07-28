package com.minigod.zero.customer.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.customer.dto.PayOrderDTO;
import com.minigod.zero.customer.entity.FinancingAccountAmount;
import com.minigod.zero.customer.entity.FundTradingAccountAmount;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
* @author dell
* @description 针对表【financing_account_amount(理财账号账户金额)】的数据库操作Mapper
* @createDate 2024-04-24 13:35:10
* @Entity com.minigod.zero.customer.entity.FinancingAccountAmount
*/
@DS("customer")
public interface FinancingAccountAmountMapper {

    int deleteByPrimaryKey(Long id);

    int insert(FinancingAccountAmount record);

    int insertSelective(FinancingAccountAmount record);

    FinancingAccountAmount selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(FinancingAccountAmount record);

    int updateByPrimaryKey(FinancingAccountAmount record);
	/**
	 * 冻结金额（可用转冻结）
	 * @param id
	 * @param amount
	 */
	void freezeAvailableAmount(@Param("id") Long id, @Param("amount") BigDecimal amount);

	/**
	 * 扣减冻结金额
	 * @param id
	 * @param amount
	 */
	void reduceFreezeAmount(@Param("id") Long id, @Param("amount")BigDecimal amount);

	/**
	 * 冻结转可用
	 * @param id
	 * @param amount
	 */
	void freezeToAvailableAmount(@Param("id") Long id, @Param("amount")BigDecimal amount);

	/**
	 * 账号+币种查询账户金额
	 * @param account
	 * @param currency
	 * @return
	 */
	FinancingAccountAmount selectByAccountAndCurrency(@Param("account")String account, @Param("currency")String currency);

	/**
	 * 入金
	 * @param id
	 * @param amount
	 */
	void goldDeposit(@Param("id") Long id, @Param("amount")BigDecimal amount);

	/**
	 * 划扣可用金额
	 * @param id
	 * @param amount
	 */
	void scratchButton(@Param("id") Long id, @Param("amount")BigDecimal amount);

	/**
	 * 根据理财账号获取金额
	 * @param accountId
	 * @return
	 */
	List<FinancingAccountAmount> selectByAccountId(@Param("accountId")String accountId,@Param("currency")String currency);

	/**
	 * 在途金额入金
	 * @param id
	 * @param amount
	 */
	void transitedDeposit(@Param("id") Long id, @Param("amount")BigDecimal amount);

	/**
	 * 在途转可用
	 * @param id
	 * @param amount
	 */
	void transitedToAvailable(@Param("id") Long id, @Param("amount")BigDecimal amount);

}
