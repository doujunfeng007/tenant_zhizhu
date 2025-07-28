package com.minigod.minigodinformation.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 披露产品公告表
 * @TableName exchange_disclosure_announcement_product
 */
@TableName(value ="exchange_disclosure_announcement_product")
@Data
public class ExchangeDisclosureAnnouncementProduct implements Serializable {
    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 公告id
     */
    private Integer announcementId;

    /**
     * 产品id
     */
    private String productId;

    /**
     * 产品名字
     */
    private String productName;

    /**
     * 产品isin
     */
    private String productIsin;

    /**
     * 是否删除;是否删除  0未删除  1已删除
     */
    private Integer isDeleted;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新人
     */
    private String updateUser;

    /**
     * 更新时间
     */
    private Date updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
