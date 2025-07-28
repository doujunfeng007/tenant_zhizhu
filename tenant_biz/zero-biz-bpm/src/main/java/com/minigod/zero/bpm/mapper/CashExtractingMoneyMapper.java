package com.minigod.zero.bpm.mapper;

import com.minigod.zero.bpm.entity.CashExtractingMoneyEntity;
import com.minigod.zero.bpm.vo.CashExtractingMoneyVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * 提取资金表 Mapper 接口
 *
 * @author 掌上智珠
 * @since 2023-05-18
 */
public interface CashExtractingMoneyMapper extends BaseMapper<CashExtractingMoneyEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param cashExtractingMoney
	 * @return
	 */
	List<CashExtractingMoneyVO> selectCashExtractingMoneyPage(IPage page, CashExtractingMoneyVO cashExtractingMoney);

	/**
	 * 根据币种查询提取资金总数
	 * @param currency
	 * @param custId
	 * @return
	 */
    BigDecimal selectExtractingMoneySumByCurrency(@Param("currency") int currency, @Param("custId") Long custId);
}
