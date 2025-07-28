package com.minigod.zero.customer.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 客户生物特征信息
 * @TableName customer_biology_feature
 */
@Data
public class CustomerBiologyFeature implements Serializable {
    /**
     * 令牌
     */
    private String token;

    /**
     * 主键
     */
    private Long id;

    /**
     * 客户ID
     */
    private Long custId;

    /**
     * 设备号
     */
    private String deviceCode;

    /**
     * 生物识别码
     */
    private String bioCode;

    /**
     * 创建人
     */
    private Long createUser;

    /**
     * 创建部门
     */
    private Long createDept;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改人
     */
    private Long updateUser;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 状态：0-无效/禁用 1-有效/启用
     */
    private Integer status;

    /**
     * 是否已删除：1-已删除
     */
    private Integer isDeleted;

    private static final long serialVersionUID = 1L;
}
