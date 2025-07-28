package com.minigod.zero.trade.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.minigod.zero.core.mp.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * ipo垫资配置 实体类
 *
 * @author 掌上智珠
 * @since 2023-01-16
 */
@Data
@TableName("ipo_loan_config")
@ApiModel(value = "IpoLoanConfig对象", description = "ipo垫资配置")
@EqualsAndHashCode(callSuper = true)
public class IpoLoanConfigEntity extends BaseEntity {

    /**
     * 总额度
     */
    @ApiModelProperty(value = "总额度")
    private BigDecimal totalAmount;
    /**
     * 剩余额度
     */
    @ApiModelProperty(value = "剩余额度")
    private BigDecimal remainAmount;
    /**
     * 回滚周期
     */
    @ApiModelProperty(value = "回滚周期")
    private Integer withdrawDays;
    /**
     * 回滚时间，默认交易日的09:00:00
     */
    @ApiModelProperty(value = "回滚时间，默认交易日的09:00:00")
    private String withdrawTime;

}
