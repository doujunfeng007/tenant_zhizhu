package com.minigod.zero.system.entity;

import java.io.Serializable;
import lombok.Data;

/**
 * 岗位初始化设置表
 * @TableName zero_post_setting
 */
@Data
public class ZeroPostSetting implements Serializable {
    /**
     * 主键
     */
    private Long id;

    /**
     * 岗位类型
     */
    private Integer category;

    /**
     * 岗位编号
     */
    private String postCode;

    /**
     * 岗位名称
     */
    private String postName;

    /**
     * 岗位排序
     */
    private Integer sort;

    private static final long serialVersionUID = 1L;
}
