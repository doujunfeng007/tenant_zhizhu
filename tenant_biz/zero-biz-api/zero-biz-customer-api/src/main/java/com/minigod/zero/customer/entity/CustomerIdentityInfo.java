package com.minigod.zero.customer.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @TableName customer_pc_role
 */
@Data
public class CustomerIdentityInfo implements Serializable {
    /**
     *
     */
    private Integer id;

    /**
     *
     */
    private Long custId;

    /**
     * 角色id
     */
    private Integer roleId;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 0可用，1不可用
     */
    private Integer status;

    /**
     * 0不是默认，1默认
     */
    private Integer isDefault;

    /**
     *
     */
    private Date createTime;

    /**
     *
     */
    private Date updateTime;

    /**
     * 创建人
     */
    private String createrName;

    /**
     * 修改人
     */
    private String updaterName;

    private static final long serialVersionUID = 1L;

}
