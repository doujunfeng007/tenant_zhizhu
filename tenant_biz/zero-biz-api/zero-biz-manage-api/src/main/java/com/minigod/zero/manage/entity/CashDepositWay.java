package com.minigod.zero.manage.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 入金方式管理配置表
 * @TableName cash_deposit_way
 */
@Data
public class CashDepositWay implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 入金方式[1:FPS,2:网银转账,3:支票转账,4:快捷入金,5:银证转账,6:EDDA]
     */
    private Integer supportType;

    /**
     * 方式名称
     */
    private String wayName;

    /**
     * 币种
     */
    private Integer currency;

    /**
     * 账户类型1香港账户，2大陆账户，3其他账户
     */
    private Integer bankType;

    /**
     * 入金手续费
     */
    private BigDecimal chargeMoney;

    /**
     * 手续费备注
     */
    private String chargeMoneyRemark;

    /**
     * 入金到账时间
     */
    private String timeArrival;

    /**
     * 到账时间备注
     */
    private String timeArrivalRemark;

    /**
     * pc入金指南链接
     */
    private String pcGuideUrl;

    /**
     * app入金指南链接
     */
    private String appGuideUrl;

    /**
     * 操作人
     */
    private String founder;

    /**
     *
     */
    private Date createTime;

    /**
     *
     */
    private Date updateTime;

    /**
     * 租户 ID
     */
    private String tenantId;
	/**
	 * 0可用，1不可用
	 */
	private Integer status;

    private static final long serialVersionUID = 1L;
}
