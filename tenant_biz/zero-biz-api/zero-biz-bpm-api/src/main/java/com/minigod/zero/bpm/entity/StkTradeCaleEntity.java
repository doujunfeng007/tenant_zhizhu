package com.minigod.zero.bpm.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 交易日历 实体类
 *
 * @author 掌上智珠
 * @since 2023-06-01
 */
@Data
@TableName("stk_trd_cale")
@ApiModel(value = "StkTrdCale对象", description = "交易日历")
public class StkTradeCaleEntity implements Serializable {

	private static final long serialVersionUID = 1L;

    /**
     * 交易日历ID
     */
    @ApiModelProperty(value = "交易日历ID")
    private Integer calendarId;
    /**
     * 自然日期
     */
    @ApiModelProperty(value = "自然日期")
    private Date normalDate;
    /**
     * 地区
     */
    @ApiModelProperty(value = "地区")
    private String regionCode;
    /**
     * 当天是否交易日
     */
    @ApiModelProperty(value = "当天是否交易日")
    private Boolean isTradeDay;
    /**
     * 上一个交易日
     */
    @ApiModelProperty(value = "上一个交易日")
    private Date lastTrd;
    /**
     * 下一个交易日
     */
    @ApiModelProperty(value = "下一个交易日")
    private Date nextTrd;
    /**
     * 是否本周最后一个交易日
     */
    @ApiModelProperty(value = "是否本周最后一个交易日")
    private Boolean isWeekEnd;
    /**
     * 是否本月最后一个交易日
     */
    @ApiModelProperty(value = "是否本月最后一个交易日")
    private Boolean isMonthEnd;
    /**
     * 是否本年最后一个交易日
     */
    @ApiModelProperty(value = "是否本年最后一个交易日")
    private Boolean isYearEnd;
    /**
     * 上周最后一个交易日
     */
    @ApiModelProperty(value = "上周最后一个交易日")
    private Date lastWeekTrd;
    /**
     * 上月最后一个交易日
     */
    @ApiModelProperty(value = "上月最后一个交易日")
    private Date lastMonthTrd;
    /**
     * 上年最后一个交易日
     */
    @ApiModelProperty(value = "上年最后一个交易日")
    private Date lastYearTrd;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间")
    private Date updateTime;
    /**
     * 外部系统时间
     */
    @ApiModelProperty(value = "外部系统时间")
    private Date extTime;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

}
