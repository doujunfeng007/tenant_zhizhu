package com.minigod.zero.trade.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.minigod.zero.trade.entity.CustStrategyOrderEntity;

/**
 * 条件单信息表 Mapper 接口
 *
 * @author 云锋有鱼
 * @since 2023-05-18
 */
public interface CustStrategyOrderMapper extends BaseMapper<CustStrategyOrderEntity> {

	Long getMaxId();
}
