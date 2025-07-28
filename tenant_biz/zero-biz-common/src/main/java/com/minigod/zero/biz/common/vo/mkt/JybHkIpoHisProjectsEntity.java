package com.minigod.zero.biz.common.vo.mkt;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * IPO保荐人等信息 实体类
 *
 * @author 掌上智珠
 * @since 2023-05-16
 */
@Data
public class JybHkIpoHisProjectsEntity implements Serializable {

	private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private Long id;
    /**
     * 股票编号
     */
    @ApiModelProperty(value = "股票编号")
    private String assetId;
    /**
     * A,HK,HGT,US,OTHER
     */
    @ApiModelProperty(value = "A,HK,HGT,US,OTHER")
    private String market;
    /**
     * 投资人类别（0 保荐人，1 牵头经办人，2 账薄管理人，3 联席全球协办人，4 基石投资者）
     */
    @ApiModelProperty(value = "投资人类别（0 保荐人，1 牵头经办人，2 账薄管理人，3 联席全球协办人，4 基石投资者）")
    private String invertorType;
    /**
     * 投资人名称
     */
    @ApiModelProperty(value = "投资人名称")
    private String invertorName;
    /**
     * 股票名称
     */
    @ApiModelProperty(value = "股票名称")
    private String name;
    /**
     * 股票全称
     */
    @ApiModelProperty(value = "股票全称")
    private String fullName;
    /**
     * 暗盘涨幅
     */
    @ApiModelProperty(value = "暗盘涨幅")
    private String grayPriceChg;
    /**
     * 首日涨幅
     */
    @ApiModelProperty(value = "首日涨幅")
    private String fistDayChg;
    /**
     * 昨收
     */
    @ApiModelProperty(value = "昨收")
    private String prevClose;
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
    private Date listedDate;
    /**
     * 最新价
     */
    @ApiModelProperty(value = "最新价")
    private String lastPrice;
    /**
     * 累计涨跌幅
     */
    @ApiModelProperty(value = "累计涨跌幅")
    private String cumulativeChg;
    /**
     * true 上市 false 没上市
     */
    @ApiModelProperty(value = "true 上市 false 没上市")
    private Integer isListed;
    /**
     * 持股占比
     */
    @ApiModelProperty(value = "持股占比")
    private String percentage;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
	@DateTimeFormat(
		pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@JsonFormat(
		pattern = "yyyy-MM-dd HH:mm:ss"
	)
    private Date createTime;
    /**
     * 修改时间
     */
	@DateTimeFormat(
		pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@JsonFormat(
		pattern = "yyyy-MM-dd HH:mm:ss"
	)
    @ApiModelProperty(value = "修改时间")
    private Date updateTime;

}
