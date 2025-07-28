package com.minigod.zero.bpmn.module.deposit.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @ClassName: MoneySumVo
 * @Description:
 * @Author chenyu
 * @Date 2024/3/1
 * @Version 1.0
 */
@Data
public class MoneySumVO {
    private BigDecimal hk;
    private BigDecimal dollar;
}
