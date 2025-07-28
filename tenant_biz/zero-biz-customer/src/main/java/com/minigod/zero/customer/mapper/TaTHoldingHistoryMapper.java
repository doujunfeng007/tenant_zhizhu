package com.minigod.zero.customer.mapper;

import com.minigod.zero.customer.dto.CustStatementDTO;
import com.minigod.zero.customer.entity.TaTHoldingHistoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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

	BigDecimal getHldSumInterest(@Param("custStatementDTO") CustStatementDTO custStatementDTO, @Param("subaccountid") String subaccountid, @Param("productId") String productId);

}




