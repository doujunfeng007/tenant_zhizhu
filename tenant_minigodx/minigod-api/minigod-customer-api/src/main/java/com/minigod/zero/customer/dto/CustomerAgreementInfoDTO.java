package com.minigod.zero.customer.dto;

import com.minigod.zero.customer.entity.CustomerAgreementInfoEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 大账户协议信息表 DTO
 *
 * @author ken
 * @since 2024-04-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CustomerAgreementInfoDTO extends CustomerAgreementInfoEntity {
    // 如果需要额外的DTO字段，可以在这里添加
} 