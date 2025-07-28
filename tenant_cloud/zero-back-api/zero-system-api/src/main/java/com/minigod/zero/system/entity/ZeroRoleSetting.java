package com.minigod.zero.system.entity;

import java.io.Serializable;
import lombok.Data;

/**
 * 角色初始化设置表
 * @TableName zero_role_setting
 */
@Data
public class ZeroRoleSetting implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 父主键
     */
    private Long parentId;

    /**
     * 角色名
     */
    private String roleName;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 角色别名
     */
    private String roleAlias;

    private static final long serialVersionUID = 1L;
}
