package com.minigod.zero.bpmn.module.deposit.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

/**
 * 入金方式管理配置表 实体类
 *
 * @author 掌上智珠
 * @since 2024-03-01
 */
@Data
@TableName("sec_deposit_type")
@ApiModel(value = "SecDepositType对象", description = "入金方式管理配置表")
public class SecDepositTypeEntity implements Serializable {

	private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private Long id;
    /**
     * 入金方式[1-fps 2-网银 3-支票]
     */
    @ApiModelProperty(value = "入金方式[1-fps 2-网银 3-支票]")
    private Integer depositType;
    /**
     * 入金手续费
     */
    @ApiModelProperty(value = "入金手续费")
    private String chargeMoney;
    /**
     * 手续费备注
     */
    @ApiModelProperty(value = "手续费备注")
    private String chargeMoneyRemark;
    /**
     * 入金到账时间
     */
    @ApiModelProperty(value = "入金到账时间")
    private String timeArrival;
    /**
     * 到账时间备注
     */
    @ApiModelProperty(value = "到账时间备注")
    private String timeArrivalRemark;
    /**
     * 银行名称
     */
    @ApiModelProperty(value = "银行名称")
    private String bankName;
    /**
     * 银行代码
     */
    @ApiModelProperty(value = "银行代码")
    private String bankCode;
    /**
     * 收款户名
     */
    @ApiModelProperty(value = "收款户名")
    private String receiptAccountName;
    /**
     * 收款户名备注
     */
    @ApiModelProperty(value = "收款户名备注")
    private String receiptAccountNameRemark;
    /**
     * pc入金指南链接
     */
    @ApiModelProperty(value = "pc入金指南链接")
    private String pcGuideUrl;
    /**
     * app入金指南链接
     */
    @ApiModelProperty(value = "app入金指南链接")
    private String appGuideUrl;
    /**
     * 入金凭证
     */
    @ApiModelProperty(value = "入金凭证")
    private String depositCertImg;
    /**
     * 操作人
     */
    @ApiModelProperty(value = "操作人")
    private String founder;
    /**
     *
     */
    @ApiModelProperty(value = "")
    private Date createTime;
    /**
     *
     */
    @ApiModelProperty(value = "")
    private Date updateTime;
    /**
     * FPS识别码
     */
    @ApiModelProperty(value = "FPS识别码")
    private String accountFps;
    /**
     * 租户 ID
     */
    @ApiModelProperty(value = "租户 ID")
    private String tenantId;

}
