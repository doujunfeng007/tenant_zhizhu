package com.minigod.zero.trade.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.minigod.zero.core.mp.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 新股预约ipo配置 实体类
 *
 * @author 掌上智珠
 * @since 2023-01-16
 */
@Data
@TableName("ipo_predict_config")
@ApiModel(value = "IpoRredictConfig对象", description = "新股预约ipo配置")
@EqualsAndHashCode(callSuper = true)
public class IpoPredictConfigEntity extends BaseEntity {

    /**
     * 预约股票名称
     */
    @ApiModelProperty(value = "预约股票名称")
    private String assetNamePredict;
    /**
     * 预计入场费
     */
    @ApiModelProperty(value = "预计入场费")
    private BigDecimal entranceFee;
    /**
     * 普通用户倍数控制：1.10倍 , 2.20倍
     */
    @ApiModelProperty(value = "普通用户倍数控制：1.10倍 , 2.20倍")
    private Integer userIpoRate;
    /**
     * vip用户倍数控制：1.10倍 , 2.20倍
     */
    @ApiModelProperty(value = "vip用户倍数控制：1.10倍 , 2.20倍")
    private Integer vipIpoRate;
    /**
     * 预约申购开始时间
     */
    @ApiModelProperty(value = "预约申购开始时间")
    private Date beginPredictTime;
    /**
     * 预约申购结束时间
     */
    @ApiModelProperty(value = "预约申购结束时间")
    private Date endPredictTime;
    /**
     * 普通用户融资上限
     */
    @ApiModelProperty(value = "普通用户融资上限")
    private Long userFinancingCeiling;
    /**
     * VIP用户融资上限
     */
    @ApiModelProperty(value = "VIP用户融资上限")
    private Long vipFinancingCeiling;
    /**
     * 普通用户融资上限总额
     */
    @ApiModelProperty(value = "普通用户融资上限总额")
    private Long userTotalFinancingCeiling;
    /**
     * VIP用户融资上限总额
     */
    @ApiModelProperty(value = "VIP用户融资上限总额")
    private Long vipTotalFinancingCeiling;
    /**
     * 通知栏文案
     */
    @ApiModelProperty(value = "通知栏文案")
    private String noticeMsg;
    /**
     * 真实股票名称
     */
    @ApiModelProperty(value = "真实股票名称")
    private String assetName;
    /**
     * 股票id
     */
    @ApiModelProperty(value = "股票id")
    private String assetId;
    /**
     * 是否上架 0:否 1:是
     */
    @ApiModelProperty(value = "是否上架 0:否 1:是")
    private Integer enableStatus;
    /**
     * 操作时间
     */
    @ApiModelProperty(value = "操作时间")
    private Date operateTime;
    /**
     * 操作人id
     */
    @ApiModelProperty(value = "操作人id")
    private String operatorId;
    /**
     * 是否软删除 0:否 1:是
     */
    @ApiModelProperty(value = "是否软删除 0:否 1:是")
    private Boolean isDelete;

}
