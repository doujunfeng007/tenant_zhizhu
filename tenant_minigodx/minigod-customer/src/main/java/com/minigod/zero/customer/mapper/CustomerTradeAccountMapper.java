package com.minigod.zero.customer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.minigod.zero.customer.entity.CustomerTradeAccountEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 客户交易账户表 Mapper 接口
 *
 * @author ken
 * @since 2024-04-11
 */
@Mapper
public interface CustomerTradeAccountMapper extends BaseMapper<CustomerTradeAccountEntity> {
    
    /**
     * 根据理财账户ID查询交易账户列表
     */
    List<CustomerTradeAccountEntity> selectByAccountId(@Param("accountId") String accountId);
    
    /**
     * 更新当前选中状态
     */
    int updateCurrentStatus(@Param("accountId") String accountId, @Param("tradeAccount") String tradeAccount);
} 