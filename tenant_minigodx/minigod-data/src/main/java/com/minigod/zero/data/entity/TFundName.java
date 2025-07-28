package com.minigod.zero.data.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 *
 * @TableName t_fund_name
 */
@TableName(value ="t_fund_name")
@Data
public class TFundName implements Serializable {
    /**
     * 基金ProductID
     */
    @TableId
    private Integer productid;

    /**
     * 基金ISIN
     */
    private String isin;

    /**
     * 基金名称(英文)
     */
    private String nameen;

    /**
     * 基金名称(简体)
     */
    private String namecn;

    /**
     * 基金名称(繁体)
     */
    private String nametw;

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

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
