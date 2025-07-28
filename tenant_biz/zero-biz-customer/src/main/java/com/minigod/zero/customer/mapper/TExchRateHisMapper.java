package com.minigod.zero.customer.mapper;

import com.minigod.zero.customer.entity.TExchRateHisEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Date;

/**
* @author dell
* @description 针对表【t_exch_rate_his】的数据库操作Mapper
* @createDate 2024-09-09 17:57:36
* @Entity generator.domain.TExchRateHis
*/
public interface TExchRateHisMapper extends BaseMapper<TExchRateHisEntity> {

	BigDecimal selRateByDate(@Param("endDate") Date endDate, @Param("currency") String currency);
}




