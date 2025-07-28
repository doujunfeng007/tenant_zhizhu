package com.minigod.zero.bpmn.module.stocktransfer.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.minigod.zero.core.mp.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * @ClassName SecSharesInfo.java
 * @author chen
 * @Description TODO
 * @createTime 2024年03月06日 17:20:00
 */

/**
 * 转入转出关联的股票信息
 */
@ApiModel(description = "转入转出关联的股票信息")
@Data
@TableName(value = "sec_shares_info")
@EqualsAndHashCode(callSuper = true)
public class SecSharesInfoEntity extends BaseEntity {

    /**
     * 用户id
     */
    @TableField(value = "cust_id")
    @ApiModelProperty(value = "用户id")
    private Long custId;

    /**
     * 股票信息
     */
    @TableField(value = "shares_name")
    @ApiModelProperty(value = "股票信息")
    private String sharesName;

    /**
     * 股票代码
     */
    @TableField(value = "shares_code")
    @ApiModelProperty(value = "股票代码")
    private String sharesCode;

    /**
     * 转出数量
     */
    @TableField(value = "shares_num")
    @ApiModelProperty(value = "转出数量")
    private Integer sharesNum;

    /**
     * 股票类型
     */
    @TableField(value = "shares_type")
    @ApiModelProperty(value = "股票类型")
    private Integer sharesType;

    /**
     * 是否全部加载 0 否 1 是
     */
    @TableField(value = "is_find")
    @ApiModelProperty(value = "是否全部加载 0 否 1 是")
    private Integer isFind;

    /**
     * 关联转入股票表
     */
    @TableField(value = "stock_id")
    @ApiModelProperty(value = "关联转入股票表")
    private Long stockId;

    /**
     * 参考成本价
     */
    @TableField(value = "cost_price")
    @ApiModelProperty(value = "参考成本价")
    private BigDecimal costPrice;


}
