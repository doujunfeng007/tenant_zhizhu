package com.minigod.zero.trade.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.minigod.zero.core.mp.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * IPO认购融资利率 实体类
 *
 * @author 掌上智珠
 * @since 2023-01-30
 */
@Data
@TableName("ipo_finance_rate")
@ApiModel(value = "IpoFinanceRate对象", description = "IPO认购融资利率")
@EqualsAndHashCode(callSuper = true)
public class IpoFinanceRateEntity extends BaseEntity {

    /**
     * 证券代码
     */
    @ApiModelProperty(value = "证券代码")
    private String assetId;
    /**
     * 融资利率
     */
    @ApiModelProperty(value = "融资利率")
    private String financeRate;
    /**
     *
     */
    @ApiModelProperty(value = "")
    private Date ctime;
    /**
     *
     */
    @ApiModelProperty(value = "")
    private Date mtime;

}
