package com.minigod.zero.customer.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.minigod.zero.customer.entity.FundCapitalAccountAmount;
import com.minigod.zero.customer.entity.FundTradingAccountAmount;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
* @author dell
* @description 针对表【fund_capital_account_amount(基金资金账号账户金额)】的数据库操作Mapper
* @createDate 2024-04-24 13:35:10
* @Entity com.minigod.zero.customer.entity.FundCapitalAccountAmount
*/
@DS("customer")
public interface FundCapitalAccountAmountMapper {

    int deleteByPrimaryKey(Long id);

    int insert(FundCapitalAccountAmount record);

    int insertSelective(FundCapitalAccountAmount record);

    FundCapitalAccountAmount selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(FundCapitalAccountAmount record);

    int updateByPrimaryKey(FundCapitalAccountAmount record);
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
	 * 不传币种查全部金额
	 * @param account
	 * @param currency
	 * @return
	 */
	FundCapitalAccountAmount selectByAccountAndCurrency(@Param("account")String account, @Param("currency")String currency);

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
	 * 获取金额
	 * @param account
	 * @param currency
	 * @return
	 */
	List<FundCapitalAccountAmount> selectByTradingAccount(@Param("account")String account,@Param("currency")String currency);
}
