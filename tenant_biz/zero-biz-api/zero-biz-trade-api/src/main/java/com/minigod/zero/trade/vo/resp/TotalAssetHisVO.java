package com.minigod.zero.trade.vo.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 客户历史总资产
 *
 * @author 掌上智珠
 * @since 2023-05-12
 */
@Data
public class TotalAssetHisVO {
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
