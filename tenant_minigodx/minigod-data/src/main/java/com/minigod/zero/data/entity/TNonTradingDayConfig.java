package com.minigod.zero.data.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @TableName t_non_trading_day_config
 */
@TableName(value ="t_non_trading_day_config")
@Data
public class TNonTradingDayConfig implements Serializable {
    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 非交易日
     */
    private Date nontradingday;

    /**
     * 创建时间
     */
    private Date createtime;

    /**
     * 修改时间
     */
    private Date updatetime;

    /**
     * 时间段 0 全天 1 上午 2 下午
     */
    private Integer timePeriod;

    /**
     * 休市原因
     */
    private String reasonClosure;

    /**
     * 最后更新人
     */
    private String uin;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
