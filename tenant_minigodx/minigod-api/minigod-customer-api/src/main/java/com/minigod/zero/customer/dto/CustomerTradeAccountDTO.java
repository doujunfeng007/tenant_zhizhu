package com.minigod.zero.customer.dto;

import com.minigod.zero.customer.entity.CustomerTradeAccountEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 客户交易账户表 DTO
 *
 * @author ken
 * @since 2024-04-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CustomerTradeAccountDTO extends CustomerTradeAccountEntity {
    // 如果需要额外的DTO字段，可以在这里添加
} 