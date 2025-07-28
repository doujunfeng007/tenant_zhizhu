package com.minigod.zero.data.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.minigod.zero.data.dto.CustStatementDTO;
import com.minigod.zero.data.entity.CustomerBondTradingTransaction;
import com.minigod.zero.data.vo.CustomerHldBondDailyAccountVO;
import com.minigod.zero.data.vo.CustomerHldBondMonthAccountVO;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
* @author dell
* @description 针对表【customer_bond_trading_transaction(债券交易流水历史表)】的数据库操作Mapper
* @createDate 2024-05-23 23:21:45
* @Entity generator.domain.CustomerBondTradingTransactionEntity
*/
public interface CustomerBondTradingTransactionMapper extends BaseMapper<CustomerBondTradingTransaction> {
	/**
	 * 获取债券易日结单买入确认单集合
	 *
	 * @author zxq
	 * @date  2024/5/24
	 **/
	List<CustomerHldBondDailyAccountVO.BuyConfirmForm> getDailyBuyConfirmFormList(@Param("custStatementDTO") CustStatementDTO custStatementDTO);

	/**
	 * 获取债券易日结单卖出确认单集合
	 *
	 * @author zxq
	 * @date  2024/5/24
	 **/
	List<CustomerHldBondDailyAccountVO.SellConfirmForm> getDailySellConfirmFormList(@Param("custStatementDTO") CustStatementDTO custStatementDTO);

	/**
	 * 获取债券易日结单持仓总览集合
	 * 读取当前持仓
	 * @author zxq
	 * @date  2024/5/24
	 **/
	List<CustomerHldBondDailyAccountVO.HoldingOverView> getDailyHoldingOverViewList(@Param("custStatementDTO") CustStatementDTO custStatementDTO);

	/**
	 * 获取债券易月结单交易明细集合
	 *
	 * @author zxq
	 * @date  2024/5/24
	 **/
	List<CustomerHldBondMonthAccountVO.TradeDetail> getMonthTradeDetailList(@Param("custStatementDTO") CustStatementDTO custStatementDTO);

	/**
	 * 获取债券易月结单持仓总览集合
	 *
	 * @author zxq
	 * @date  2024/5/24
	 **/
	List<CustomerHldBondMonthAccountVO.HoldingOverView> getMonthHoldingOverviewList(@Param("custStatementDTO") CustStatementDTO custStatementDTO);

	/**
	 * 获取债券易日结单持仓总览集合
	 * 	 * 读取历史持仓
	 * @param custStatementDTO
	 * @return
	 */
	List<CustomerHldBondDailyAccountVO.HoldingOverView> getDailyHoldingHistoryOverViewList(@Param("custStatementDTO")CustStatementDTO custStatementDTO);

	/**
	 * 查询历史持仓是否符合条件
	 * @param productId
	 * @param custStatementDTO
	 * @return
	 */
	BigDecimal getDailyHoldingHistoryOverViewByFundCode(@Param("productId") String productId, @Param("custStatementDTO") CustStatementDTO custStatementDTO);
}




