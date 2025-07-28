package com.minigod.zero.customer.vo;

import com.minigod.zero.customer.entity.CustomerTradeSubAccountEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 交易子账号信息 VO
 *
 * @author ken
 * @since 2024-04-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CustomerTradeSubAccountVO extends CustomerTradeSubAccountEntity {
    
    @ApiModelProperty("市场类型描述")
    private String marketTypeDesc;
} 