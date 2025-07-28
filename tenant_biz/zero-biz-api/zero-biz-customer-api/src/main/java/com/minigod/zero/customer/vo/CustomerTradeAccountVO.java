package com.minigod.zero.customer.vo;

import com.minigod.zero.customer.entity.CustomerTradeAccountEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 客户交易账户表 VO
 *
 * @author ken
 * @since 2024-04-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CustomerTradeAccountVO extends CustomerTradeAccountEntity {
    
    @ApiModelProperty("账户状态描述")
    private String accountStatusDesc;
    
    @ApiModelProperty("业务类型描述")
    private String businessTypeDesc;
    
    @ApiModelProperty("操作类型描述")
    private String operTypeDesc;
} 