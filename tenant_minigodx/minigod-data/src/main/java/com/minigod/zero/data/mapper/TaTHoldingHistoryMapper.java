package com.minigod.zero.data.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.minigod.zero.data.dto.CustStatementDTO;
import com.minigod.zero.data.entity.TaTHoldingHistoryEntity;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

/**
* @author dell
* @description 针对表【ta_t_holding_history】的数据库操作Mapper
* @createDate 2024-08-26 14:04:55
* @Entity generator.domain.TaTHoldingHistory
*/
public interface TaTHoldingHistoryMapper extends BaseMapper<TaTHoldingHistoryEntity> {

	TaTHoldingHistoryEntity getRealizedGainLoss(@Param("custStatementDTO") CustStatementDTO custStatementDTO, @Param("productId") String productId);
	TaTHoldingHistoryEntity getHldRealizedGainLoss(@Param("custStatementDTO") CustStatementDTO custStatementDTO, @Param("productId") String productId);

	BigDecimal getHldSumInterest(@Param("custStatementDTO") CustStatementDTO custStatementDTO, @Param("subaccountid") String subaccountid, @Param("productId") String productId);
	BigDecimal getHldDaySumInterest(@Param("custStatementDTO") CustStatementDTO custStatementDTO, @Param("subaccountid") String subaccountid, @Param("productId") String productId);

}




