package com.minigod.zero.bpmn.module.paycategory.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 收款记录类别图片表
 * @TableName payee_category_img
 */
@TableName(value ="payee_category_img")
@Data
public class PayeeCategoryImgEntity implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 存入的路径
     */
    @TableField(value = "img_path")
    private String imgPath;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;

    /**
     * 用户 ID
     */
    @TableField(value = "cust_id")
    private Long custId;

    /**
     * 理财账号
     */
    @TableField(value = "account_id")
    private String accountId;

    /**
     * 图片对应交易流水号
     */
    @TableField(value = "transact_id")
    private Long transactId;

    /**
     * 租户 ID
     */
    @TableField(value = "tenant_id")
    private String tenantId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
