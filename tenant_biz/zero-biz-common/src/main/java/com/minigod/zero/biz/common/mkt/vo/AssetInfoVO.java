package com.minigod.zero.biz.common.mkt.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 股票码表
 *
 * @author 掌上智珠
 * @since 2022-11-17
 */
@Data
public class AssetInfoVO implements Serializable {

    /**
     * 资产ID
     */
    @ApiModelProperty(value = "资产ID")
    private String assetId;
    /**
     * 股票代码
     */
    @ApiModelProperty(value = "股票代码")
    private String stkCode;
    /**
     * (香港/A股)映射股票代码
     */
    @ApiModelProperty(value = "(香港/A股)映射股票代码")
    private String mapStkCode;
    /**
     * 市场
     */
    @ApiModelProperty(value = "市场")
    private String mktCode;
    /**
     * 证券类别
     */
    @ApiModelProperty(value = "证券类别")
    private Integer secType;
    /**
     * 细分类别
     */
    @ApiModelProperty(value = "细分类别")
    private Integer secStype;
    /**
     * 公司ID
     */
    @ApiModelProperty(value = "公司ID")
    private Integer corpId;
    /**
     * 股票简称
     */
    @ApiModelProperty(value = "股票简称")
    private String stkName;
    /**
     * 股票全称
     */
    @ApiModelProperty(value = "股票全称")
    private String stkNameLong;
    /**
     * 繁体简称
     */
    @ApiModelProperty(value = "繁体简称")
    private String traditionalName;
    /**
     * 繁体全称
     */
    @ApiModelProperty(value = "繁体全称")
    private String traditionalLong;
    /**
     * 拼音全称
     */
    @ApiModelProperty(value = "拼音全称")
    private String spelling;
    /**
     * 拼音简称
     */
    @ApiModelProperty(value = "拼音简称")
    private String spellingAbbr;
    /**
     * 简称
     */
    @ApiModelProperty(value = "简称")
    private String spellingShort;
    /**
     * 英文名称
     */
    @ApiModelProperty(value = "英文名称")
    private String engName;
    /**
     * 板块代码
     */
    @ApiModelProperty(value = "板块代码")
    private Integer boardCode;
    /**
     * 每手股数
     */
    @ApiModelProperty(value = "每手股数")
    private Integer lotSize;
    /**
     * 涨跌幅限制
     */
    @ApiModelProperty(value = "涨跌幅限制")
    private BigDecimal changeLimit;
    /**
     * 上市日期
     */
    @ApiModelProperty(value = "上市日期")
	@DateTimeFormat(
		pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@JsonFormat(
		pattern = "yyyy-MM-dd HH:mm:ss"
	)
    private Date listingDate;
    /**
     * 退市日期
     */
    @ApiModelProperty(value = "退市日期")
	@DateTimeFormat(
		pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@JsonFormat(
		pattern = "yyyy-MM-dd HH:mm:ss"
	)
    private Date delistDate;
    /**
     * 币种代码
     */
    @ApiModelProperty(value = "币种代码")
    private String ccyType;
    /**
     * 最新版本号
     */
    @ApiModelProperty(value = "最新版本号")
    private Integer version;
    /**
     * 记录创建版本号
     */
    @ApiModelProperty(value = "记录创建版本号")
    private Integer addVersion;
    /**
     * 股票是否可以交易
     */
    @ApiModelProperty(value = "股票是否可以交易")
    private Boolean isInvest;

	/**
	 * 是否未退市
	 */
	@ApiModelProperty(value = "是否未退市")
	private Boolean isStatus;

    /**
     * 是否支持模拟交易
     */
    @ApiModelProperty(value = "是否支持模拟交易")
    private Integer isSimuInvest;
    /**
     * 记录外部系统时间
     */
    @ApiModelProperty(value = "记录外部系统时间")
	@DateTimeFormat(
		pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@JsonFormat(
		pattern = "yyyy-MM-dd HH:mm:ss"
	)
    private Date extTime;
    /**
     * 是否确认
     */
    @ApiModelProperty(value = "是否确认")
    private Integer isConfirm;
    /**
     * 是否修改
     */
    @ApiModelProperty(value = "是否修改")
    private Integer isRevise;
    /**
     * 上市发行价
     */
    @ApiModelProperty(value = "上市发行价")
    private BigDecimal issuePrice;
    /**
     * 美股交易市场
     */
    @ApiModelProperty(value = "美股交易市场")
    private String exchange;
    /**
     * 0,可覆盖,1,不允许覆盖
     */
    @ApiModelProperty(value = "0,可覆盖,1,不允许覆盖")
    private Integer stkNameIscover;
    /**
     * 价差类型
     */
    @ApiModelProperty(value = "价差类型")
    private String spreadTableCode;

	//private String stkTraditionalName;//繁体
	//private String stkTraditionalNameLong;

}
