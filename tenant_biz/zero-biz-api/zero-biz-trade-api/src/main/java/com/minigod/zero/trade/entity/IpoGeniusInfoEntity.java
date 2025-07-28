package com.minigod.zero.trade.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.minigod.zero.core.mp.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * IPO打新牛人信息表 实体类
 *
 * @author 掌上智珠
 * @since 2023-01-16
 */
@Data
@TableName("ipo_genius_info")
@ApiModel(value = "IpoGeniusInfo对象", description = "IPO打新牛人信息表")
@EqualsAndHashCode(callSuper = true)
public class IpoGeniusInfoEntity extends BaseEntity {

    /**
     * 用户号
     */
    @ApiModelProperty(value = "用户号")
    private Long custId;
    /**
     * 交易密码
     */
    @ApiModelProperty(value = "交易密码")
    private String tradePwd;
    /**
     * 手机设备号
     */
    @ApiModelProperty(value = "手机设备号")
    private String opStation;
    /**
     * 牛人初始资金
     */
    @ApiModelProperty(value = "牛人初始资金")
    private BigDecimal initialAmount;
    /**
     * 订阅人数
     */
    @ApiModelProperty(value = "订阅人数")
    private Integer subscribeNumber;
    /**
     * 状态 0:失效 1:有效
     */
    @ApiModelProperty(value = "状态 0:失效 1:有效")
    private Boolean isStatus;

}
