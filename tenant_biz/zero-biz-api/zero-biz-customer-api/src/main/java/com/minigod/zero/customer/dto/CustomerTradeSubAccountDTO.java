package com.minigod.zero.customer.dto;

import com.minigod.zero.customer.entity.CustomerTradeSubAccountEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 交易子账号信息 DTO
 *
 * @author ken
 * @since 2024-04-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CustomerTradeSubAccountDTO extends CustomerTradeSubAccountEntity {
    // 如果需要额外的DTO字段，可以在这里添加
} 