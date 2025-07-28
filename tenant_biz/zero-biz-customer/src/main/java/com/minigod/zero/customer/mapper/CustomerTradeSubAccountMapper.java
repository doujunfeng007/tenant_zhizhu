package com.minigod.zero.customer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.minigod.zero.customer.entity.CustomerTradeSubAccountEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 交易子账号信息 Mapper 接口
 *
 * @author ken
 * @since 2024-04-11
 */
@Mapper
public interface CustomerTradeSubAccountMapper extends BaseMapper<CustomerTradeSubAccountEntity> {
    
    /**
     * 根据交易账号查询子账号列表
     */
    List<CustomerTradeSubAccountEntity> selectByTradeAccount(@Param("tradeAccount") String tradeAccount);
    
    /**
     * 根据理财账户ID查询子账号列表
     */
    List<CustomerTradeSubAccountEntity> selectByAccountId(@Param("accountId") String accountId);
} 