package com.minigod.zero.manage.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.minigod.zero.core.tenant.mp.TenantEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 活动营销奖品库
 *
 * @author eric
 * @date 2024-12-23 14:38:08
 * @TableName sn_active_config_item
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sn_active_config_item")
public class SnActiveConfigItemEntity extends TenantEntity {

    /**
     * 活动配置名称
     */
    private String activeItemName;

    /**
     * 活动id
     */
    private Long snActiveConfigId;

    /**
     * 奖励类型: 1.免佣 2.现金 3.行情 4.赠股 5.积分
     */
    private Integer rewardType;

    /**
     * 奖励金额
     */
    private BigDecimal rewardMoney;

    /**
     * 行情类型产品ID
     */
    private Long packageId;

    /**
     * 库存数量
     */
    private Integer remainCount;

    /**
     * 奖品介绍
     */
    private String introduce;

    /**
     * 备注
     */
    private String remark;

    /**
     * 类型 1:系统生成 2:用户创建
     */
    private Integer type;

    /**
     * 操作人员ID
     */
    private Integer oprId;

    /**
     * 操作人员名称
     */
    private String oprName;

    /**
     * 免佣类型免佣天数
     */
    private Integer commissionDay;

    /**
     * 规则信息
     */
    private String ruleInfo;

    /**
     * 证券代码
     */
    private String assetId;

    /**
     * 股数
     */
    private Integer stkNum;

    /**
     * 积分
     */
    private Integer points;

    /**
     * 奖励子类型
     */
    private Integer rewardSubtype;

    /**
     * 免佣类型 0=普通免佣，1=vip免佣券
     */
    private Integer commissionType;

    /**
     * 每月领取数
     */
    private Integer monthNum;

    /**
     * 卡券图标
     */
    private String stockIco;

    /**
     * 卡券背景图片
     */
    private String stockBg;

    /**
     * 股票名称
     */
    private String stkName;

    /**
     * 股票名称英文
     */
    private String stkNameEng;
}
