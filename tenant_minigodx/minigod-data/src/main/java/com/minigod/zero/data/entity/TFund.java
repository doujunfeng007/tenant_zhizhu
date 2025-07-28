package com.minigod.zero.data.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * 交易系统同步-基金商品表
 * @TableName t_fund
 */
@TableName(value ="t_fund")
@Data
public class TFund implements Serializable {
    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Long productid;

    /**
     *
     */
    private String afbcode;

    /**
     *
     */
    private String productcode;

    /**
     * 基金ISIN
     */
    private String isin;

    /**
     * MStar ID
     */
    private String mstarid;

    /**
     * MStar Performance ID
     */
    private String performanceid;

    /**
     * 晨星的fund_id
     */
    private String mstarfundid;

    /**
     * 基金名称
     */
    private String name;

    /**
     * 基金法定名称
     */
    private String legalname;

    /**
     * 货币单位
     */
    private String currency;

    /**
     * 对接上手使用的币种
     */
    private String custodiancurrency;

    /**
     * Sedol Code
     */
    private String sedol;

    /**
     *
     */
    private String citicode;

    /**
     * 基金类型, 1-Traditional, 2-Unit Trust, 3-Hedge Fund 4-Domestic Fund
     */
    private Integer type;

    /**
     * 晨星分类ID
     */
    private String mstarcategoryid;

    /**
     * 基金风险等级
     */
    private Integer risklevel;

    /**
     * 基金归属地
     */
    private String domicile;

    /**
     * 基金成立时间
     */
    private String launchdate;

    /**
     * 基金规模
     */
    private String fundsize;

    /**
     * 基金规模的货币单位
     */
    private String fundsizecurrency;

    /**
     * 基金规模更新时间
     */
    private Date fundsizeenddate;

    /**
     * 收入类型
     */
    private String incometype;

    /**
     * 0-不可交易, 1-可进行买卖交易, 2-仅支持申购, 3-仅支持赎回
     */
    private Integer status;

    /**
     * 基金品牌名称
     */
    private String branding;

    /**
     * 基金品牌ID
     */
    private Integer brandingid;

    /**
     * 基金公司ID
     */
    private Integer companyid;

    /**
     * 投资策略(英文)
     */
    private String investmentstrategyen;

    /**
     * 投资策略(中文简体)
     */
    private String investmentstrategycn;

    /**
     * 投资策略(中文繁体)
     */
    private String investmentstrategytw;

    /**
     * 基金点评(英文)
     */
    private String recommenden;

    /**
     * 基金点评(中文简体)
     */
    private String recommendcn;

    /**
     * 基金点评(中文繁体)
     */
    private String recommendtw;

    /**
     * 主要基准ID
     */
    private String primarybenchmarkid;

    /**
     * 主要基准名称
     */
    private String primarybenchmarkname;

    /**
     * 主要基准权重
     */
    private String primarybenchmarkweight;

    /**
     * 申购到结算时间
     */
    private Integer purchasesettlementdays;

    /**
     * 再次申购到结算时间
     */
    private Integer repurchasesettlementdays;

    /**
     * 申购到结算的时间类型
     */
    private String daystosettlementtype;

    /**
     * 分红类型, 0-None, 1-Accumulation, 2-Income
     */
    private Integer dividendtype;

    /**
     * 分红频率, 0-None, 1-Daily, 2-Weekly, 3-Fortnightly, 4-Monthly, 5-Bimonthly, 6-Quarterly, 7-Semi-Annually, 8-Annually
     */
    private Integer dividendfrequency;

    /**
     * 股息生息率
     */
    private BigDecimal dividendyield;

    /**
     * 股息生息率最后时间
     */
    private Date dividendyieldenddate;

    /**
     * 年度管理费率(%) == 实际管理费率(%)
     */
    private BigDecimal annualmanagementfee;

    /**
     * 实际管理费率(%)
     */
    private BigDecimal actualmanagementfee;

    /**
     * 最高管理费率(%)
     */
    private BigDecimal maxmanagementfee;

    /**
     * 申购费率(%)
     */
    private BigDecimal subscriptionfee;

    /**
     * 赎回费率(%)
     */
    private BigDecimal redemptionfee;

    /**
     * (%)
     */
    private BigDecimal performancefee;

    /**
     * 盈利费率(%)
     */
    private BigDecimal gainsfee;

    /**
     * 分销商费率(%)
     */
    private BigDecimal distributionfee;

    /**
     * 存款费率(%)
     */
    private BigDecimal depositfee;

    /**
     * 管理员费率(%)
     */
    private BigDecimal administrationfee;

    /**
     * 总费率(%)
     */
    private BigDecimal totalfee;

    /**
     * 是否有公司行动, 包括分红?
     */
    private Integer hascorporateaction;

    /**
     * 是否有分红
     */
    private Integer hasdividend;

    /**
     *
     */
    private Integer hasbreakdown;

    /**
     * 是否有文档
     */
    private Integer hasdocument;

    /**
     * 是否认可投资基金
     */
    private Integer piflag;

    /**
     *
     */
    private String trackingerror12months;

    /**
     * 分配率
     */
    private String distributionrate;

    /**
     *
     */
    private Date createtime;

    /**
     *
     */
    private Date updatetime;

    /**
     *
     */
    private String lastupdatedby;

    /**
     * 私有数据的市场分布
     */
    private String privatedatamarket;

    /**
     * 0 没有签署 da 协议，1 签署了 da 协议，2 没有签署 subda 协议
     */
    private Integer dastatus;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
