package com.minigod.zero.bpm.mapper;

import com.minigod.zero.bpm.entity.CashDepositFundsEntity;
import com.minigod.zero.bpm.vo.CashDepositFundsVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * 存入资金表 Mapper 接口
 *
 * @author 掌上智珠
 * @since 2023-05-18
 */
public interface CashDepositFundsMapper extends BaseMapper<CashDepositFundsEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param cashDepositFunds
	 * @return
	 */
	List<CashDepositFundsVO> selectCashDepositFundsPage(IPage page, CashDepositFundsVO cashDepositFunds);

	/**
	 * 根据币种查询存入金额总数
	 * @param currency
	 * @param custId
	 * @return
	 */
    BigDecimal selectDepositMoneySumByCurrency(@Param("currency") int currency, @Param("custId") Long custId);
}
