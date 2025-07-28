package com.minigod.zero.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 国际化配置表 实体类
 *
 * @author 掌上智珠
 * @since 2023-03-08
 */
@Data
@TableName("zero_i18n_config")
@ApiModel(value = "I18nConfig对象", description = "国际化配置表")
public class I18nConfigEntity implements Serializable {

	private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private Integer id;
    /**
     * 系统模块
     */
    @ApiModelProperty(value = "系统模块")
    private String sysModel;
    /**
     * 配置key
     */
    @ApiModelProperty(value = "配置key")
    private String configKey;
    /**
     * 语言类型
     */
    @ApiModelProperty(value = "语言类型")
    private String languageType;
    /**
     * 内容
     */
    @ApiModelProperty(value = "内容")
    private String content;
    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间")
    private Long createUser;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间")
    private Long updateUser;
    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间")
    private Date updateTime;
    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间")
    private Integer status;
    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间")
    private Integer isDeleted;

}
