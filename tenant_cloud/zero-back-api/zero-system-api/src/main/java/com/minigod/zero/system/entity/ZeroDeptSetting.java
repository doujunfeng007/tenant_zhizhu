package com.minigod.zero.system.entity;

import java.io.Serializable;
import lombok.Data;

/**
 * 机构初始化设置表
 * @TableName zero_dept_setting
 */
@Data
public class ZeroDeptSetting implements Serializable {
    /**
     * 主键
     */
    private Long id;

    /**
     * 父主键
     */
    private Long parentId;

    /**
     * 部门名
     */
    private String deptName;

    /**
     * 部门全称
     */
    private String fullName;

    /**
     * 排序
     */
    private Integer sort;

    private static final long serialVersionUID = 1L;
}
