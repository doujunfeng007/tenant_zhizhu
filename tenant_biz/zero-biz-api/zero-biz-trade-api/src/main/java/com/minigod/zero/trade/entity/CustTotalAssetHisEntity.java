package com.minigod.zero.trade.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.minigod.zero.core.mp.base.AppEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 客户历史总资产表 实体类
 *
 * @author 掌上智珠
 * @since 2023-05-12
 */
@Data
@TableName("cust_total_asset_his")
@ApiModel(value = "CustTotalAssetHis对象", description = "客户历史总资产表")
@EqualsAndHashCode(callSuper = true)
public class CustTotalAssetHisEntity extends AppEntity {

    /**
     * 客户ID
     */
    @ApiModelProperty(value = "客户ID")
    private Long custId;
    /**
     * 交易日
     */
    @ApiModelProperty(value = "交易日")
    private Date tradeDate;
    /**
     * 总资产
     */
    @ApiModelProperty(value = "总资产")
    private BigDecimal totalAsset;

}
