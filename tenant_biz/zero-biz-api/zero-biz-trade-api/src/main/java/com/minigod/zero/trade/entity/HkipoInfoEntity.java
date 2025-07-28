package com.minigod.zero.trade.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 港股IPO信息表 实体类
 *
 * @author 掌上智珠
 * @since 2022-11-24
 */
@Data
public class HkipoInfoEntity implements Serializable {

	private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    @TableId(
        value = "id",
        type = IdType.ASSIGN_ID
    )
    private Long id;
    /**
     * 股票代码
     */
    @ApiModelProperty(value = "股票代码")
    private String symbol;
    /**
     * 招股开始时间
     */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "招股开始时间")
    private Date startDate;
    /**
     * 公司全称
     */
    @ApiModelProperty(value = "公司全称")
    private String companyName;
    /**
     * 公司简称
     */
    @ApiModelProperty(value = "公司简称")
    private String shortName;
    /**
     * 上市途径
     */
    @ApiModelProperty(value = "上市途径")
    private String listedMode;
    /**
     * 上市板块
     */
    @ApiModelProperty(value = "上市板块")
    private String sector;
    /**
     * 行业
     */
    @ApiModelProperty(value = "行业")
    private String industry;
    /**
     * 招股价上限
     */
    @ApiModelProperty(value = "招股价上限")
    private BigDecimal priceCeiling;
    /**
     * 招股价下限
     */
    @ApiModelProperty(value = "招股价下限")
    private BigDecimal priceFloor;
    /**
     * 照顾定价
     */
    @ApiModelProperty(value = "照顾定价")
    private BigDecimal ipoPricing;
    /**
     * 照顾截止时间
     */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "照顾截止时间")
    private Date endDate;
    /**
     * 配发结果公布日
     */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "配发结果公布日")
    private Date resultDate;
    /**
     * 上市日期
     */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "上市日期")
    private Date listedDate;
    /**
     * 每手股数
     */
    @ApiModelProperty(value = "每手股数")
    private Integer shares;
    /**
     * 保荐人
     */
    @ApiModelProperty(value = "保荐人")
    private String sponsors;
    /**
     * 协调人
     */
    @ApiModelProperty(value = "协调人")
    private String coordinator;
    /**
     * 牵头经办人-创业板
     */
    @ApiModelProperty(value = "牵头经办人-创业板")
    private String ledAgent;
    /**
     * 联席牵头经办人
     */
    @ApiModelProperty(value = "联席牵头经办人")
    private String coLeadAgent;
    /**
     * 联席保荐人
     */
    @ApiModelProperty(value = "联席保荐人")
    private String coSponsors;
    /**
     * 联席全球协调人
     */
    @ApiModelProperty(value = "联席全球协调人")
    private String coCoordinator;
    /**
     * 联席账簿管理人
     */
    @ApiModelProperty(value = "联席账簿管理人")
    private String bookRunners;
    /**
     * 全球发售股份数目
     */
    @ApiModelProperty(value = "全球发售股份数目")
    private BigDecimal issueNumber;
    /**
     * 香港发售股份数目
     */
    @ApiModelProperty(value = "香港发售股份数目")
    private BigDecimal issueNumberHk;
    /**
     * 国际发售股份数目
     */
    @ApiModelProperty(value = "国际发售股份数目")
    private BigDecimal issueNumberOther;
    /**
     * 配售价-创业板
     */
    @ApiModelProperty(value = "配售价-创业板")
    private BigDecimal priceGem;
    /**
     * 配售股份数目-创业板
     */
    @ApiModelProperty(value = "配售股份数目-创业板")
    private BigDecimal issueNumberGem;
    /**
     * 募集资金金额
     */
    @ApiModelProperty(value = "募集资金金额")
    private BigDecimal raiseMoney;
    /**
     * 资金用途
     */
    @ApiModelProperty(value = "资金用途")
    private String useMoney;
    /**
     * 认购倍数
     */
    @ApiModelProperty(value = "认购倍数")
    private BigDecimal subscribed;
    /**
     * 手中签率
     */
    @ApiModelProperty(value = "手中签率")
    private BigDecimal codesRate;
    /**
     * 入场费
     */
    @ApiModelProperty(value = "入场费")
    private BigDecimal minimumCapital;
    /**
     * 暗盘价
     */
    @ApiModelProperty(value = "暗盘价")
    private BigDecimal grayPrice;
    /**
     * 暗盘价升股
     */
    @ApiModelProperty(value = "暗盘价升股")
    private BigDecimal grayPriceChg;
    /**
     * 币种
     */
    @ApiModelProperty(value = "币种")
    private String currency;
    /**
     * 首日开盘价
     */
    @ApiModelProperty(value = "首日开盘价")
    private BigDecimal firstDayOpen;
    /**
     * 首日涨跌幅
     */
    @ApiModelProperty(value = "首日涨跌幅")
    private BigDecimal firstDayChg;
    /**
     * ？链接
     */
    @ApiModelProperty(value = "？链接")
    private String link;
    /**
     * 供销？
     */
    @ApiModelProperty(value = "供销？")
    private BigDecimal offerSale;
    /**
     * 网络配售？
     */
    @ApiModelProperty(value = "网络配售？")
    private BigDecimal intlPlacing;
    /**
     * 创建时间
     */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    /**
     * 修改人
     */
    @ApiModelProperty(value = "修改人")
    private Long updateUser;
    /**
     * 修改时间
     */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "修改时间")
    private Date updateTime;
    /**
     * 是否推送 0:否 1:是
     */
    @ApiModelProperty(value = "是否推送 0:否 1:是")
    private Integer isPush;
    /**
     * 是否？推送 0:否 1:是
     */
    @ApiModelProperty(value = "是否？推送 0:否 1:是")
    private Integer isCfPush;

	/**
	 * 是否取消上市，1是 0否
	 */
	@ApiModelProperty(value = "是否取消上市，1是 0否")
	private Integer isCancel;

	/**
	 * 0=未更新，1=已更新
	 */
	@ApiModelProperty(value = "0=未更新，1=已更新")
	private Integer updateStatus;

    @TableField(exist = false)
    @ApiModelProperty(value = "业务简介")
    private String priBiz;
    @TableField(exist = false)
    @ApiModelProperty(value = "公司电话")
    private String telCode;
    @TableField(exist = false)
    @ApiModelProperty(value = "公司网址")
    private String webSit;
    @TableField(exist = false)
    @ApiModelProperty(value = "董事会成员")
    private String managers;

    @ApiModelProperty(value = "英文名称")
    private String engName;
}
