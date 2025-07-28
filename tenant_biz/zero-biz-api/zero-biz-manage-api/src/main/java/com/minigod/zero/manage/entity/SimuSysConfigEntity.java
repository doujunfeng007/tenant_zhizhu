package com.minigod.zero.manage.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统参数配置表(SysConfig)实体类
 *
 * @author makejava
 * @since 2025-01-11 14:43:05
 */
@Data
@TableName("sys_config")
@ApiModel(value = "SimuSysConfig对象", description = "系统参数配置表")
public class SimuSysConfigEntity implements Serializable {
    private static final long serialVersionUID = 343603227766029334L;
    /**
     * 配置ID
     */
    private Integer configId;

    private String moduleName;

    private String keyName;

    private String remark;

    private String keyValue;
    /**
     * 状态(0停用,默认1正常使用)
     */
    private Integer isStatus;
    /**
     * 记录创建时间
     */
    private Date createTime;
    /**
     * 记录最后修改时间
     */
    private Date updateTime;

    private Long updVersion;
}

