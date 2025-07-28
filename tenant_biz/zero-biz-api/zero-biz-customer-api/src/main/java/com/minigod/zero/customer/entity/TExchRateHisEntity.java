package com.minigod.zero.customer.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 *
 * @TableName t_exch_rate_his
 */
@TableName(value ="t_exch_rate_his")
@Data
public class TExchRateHisEntity implements Serializable {
    /**
     * 自增ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 原记录ID
     */
    private Integer orgid;

    /**
     * 源币种
     */
    private String srccurrency;

    /**
     * 目的币种
     */
    private String dstcurrency;

    /**
     * 汇率，即  目的币种=源币种X汇率
     */
    private BigDecimal rate;

    /**
     * 数据更新时间
     */
    private Date dataupdtime;

    /**
     * 记录更新时间
     */
    private Date recordupdtime;

    /**
     * 记录创建时间
     */
    private Date createtime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
