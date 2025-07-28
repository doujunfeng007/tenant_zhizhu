package com.minigod.gateway.i18;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class I18nConfig implements Serializable {

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
    private String sysModule;
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
