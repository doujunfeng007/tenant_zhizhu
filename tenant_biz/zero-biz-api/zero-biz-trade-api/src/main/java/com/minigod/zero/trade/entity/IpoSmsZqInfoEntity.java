package com.minigod.zero.trade.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.minigod.zero.core.mp.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * IPO已中签短信通知 实体类
 *
 * @author 掌上智珠
 * @since 2023-02-02
 */
@Data
@TableName("ipo_sms_zq_info")
@ApiModel(value = "IpoSmsZqInfo对象", description = "IPO已中签短信通知")
@EqualsAndHashCode(callSuper = true)
public class IpoSmsZqInfoEntity extends BaseEntity {

    /**
     * 用户号
     */
    @ApiModelProperty(value = "用户号")
    private Long custId;
    /**
     * 资产ID
     */
    @ApiModelProperty(value = "资产ID")
    private String assetId;
    /**
     * 资金账号
     */
    @ApiModelProperty(value = "资金账号")
    private String fundAccount;
    /**
     * 交易账号
     */
    @ApiModelProperty(value = "交易账号")
    private String clientId;
    /**
     * 是否发送已中签短信0:否 1:是
     */
    @ApiModelProperty(value = "是否发送已中签短信0:否 1:是")
    private Boolean isSend;
    /**
     * 中签数量
     */
    @ApiModelProperty(value = "中签数量")
    private Integer quantityAllotted;
    /**
     * 类型：1、中签 2、未中签
     */
    @ApiModelProperty(value = "类型：1、中签 2、未中签")
    private Integer type;

}
