package com.minigod.zero.customer.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @TableName customer_package
 */
@Data
public class CustomerPackage implements Serializable {
    /**
     *
     */
    private Integer id;

    /**
     * 客户id
     */
    private Long custId;

    /**
     * 套餐id
     */
    private Long packageId;

    /**
     * 0有效，1无效
     */
    private Integer status;

    /**
     * 绑定时间
     */
    private Date createTime;

    /**
     * 租户id
     */
    private String tenantId;

    private static final long serialVersionUID = 1L;

}
