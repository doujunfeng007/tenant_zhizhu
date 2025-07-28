package com.minigod.zero.data.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 产品表
 * @TableName t_product
 */
@TableName(value ="t_product")
@Data
public class TProductEntity implements Serializable {
    /**
     * ProductID
     */
    @TableId(type = IdType.AUTO)
    private Integer productid;

    /**
     * 产品ISIN编码
     */
    private String isin;

    /**
     * 产品名称
     */
    private String name;

    /**
     * 保证息率
     */
    private BigDecimal fixinterestrate;

    /**
     * 买入费率
     */
    private BigDecimal buyingrate;

    /**
     * 卖出费率
     */
    private BigDecimal sellingrate;

    /**
     * 币种
     */
    private String currency;

    /**
     * 面值
     */
    private BigDecimal nominalvalue;

    /**
     * 估值
     */
    private BigDecimal appraisement;

    /**
     * 发行份额
     */
    private BigDecimal unit;

    /**
     * 发行规模
     */
    private BigDecimal issueamount;

    /**
     * 交易/认购单位 最低认购份额 100份起，可+1
     */
    private BigDecimal tradingunit;

    /**
     * 发行日期
     */
    private Date issuedate;

    /**
     * 到期日即最后交易日
     */
    private Date maturitydate;

    /**
     * 产品风险等级 1-5
     */
    private Integer risklevel;

    /**
     * 利息计算天数
     */
    private Integer interestcalculationdays;

    /**
     * 清盘日
     */
    private Date liquidationdate;

    /**
     * 开盘时间
     */
    private Date marketopentime;

    /**
     * 收盘时间
     */
    private Date closingtime;

    /**
     * 发行人
     */
    private String issuer;

    /**
     * 产品描述或产品档案
     */
    private String productdescription;

    /**
     * 交易印花税
     */
    private BigDecimal stamptax;

    /**
     * 经纪佣金
     */
    private BigDecimal brokeragecharges;

    /**
     * 开售时间
     */
    private Date onsaletime;

    /**
     * 限价盘 1 是 0 否
     */
    private Integer limitorder;

    /**
     * 产品类型 64 活利得 65 债券易
     */
    private Integer type;

    /**
     * 产品审核状态 1 初次提交保存 2 待审核（即点击上架） 3 通过 4 拒绝
     */
    private Integer auditstatus;

    /**
     * 0-不可交易, 1-可进行买卖交易, 2-仅支持购买, 3-仅支持出售
     */
    private Integer status;

    /**
     * 风险提示 1 正常 2 迟付利息 3 到期未兑付
     */
    private Integer riskwarning;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 最后更新人
     */
    private String lastupdatedby;

    /**
     * 同步状态 1 已同步 2 未同步 3 已更新 4 未更新
     */
    private Integer synchstate;

    /**
     *
     */
    private Date createtime;

    /**
     *
     */
    private Date updatetime;

    /**
     * 结算日
     */
    private Date settlementdate;

    /**
     * 清算价格
     */
    private BigDecimal clearingvalue;

    /**
     * 上架时间
     */
    private Date listingtime;

    /**
     * 产品自定义表单字段
     */
    private String customerform;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
