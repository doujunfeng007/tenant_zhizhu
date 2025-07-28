package com.minigod.zero.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 语言配置表 实体类
 *
 * @author 掌上智珠
 * @since 2023-03-08
 */
@Data
@TableName("zero_i18n_language")
@ApiModel(value = "I18nLanguage对象", description = "语言配置表")
public class I18nLanguageEntity implements Serializable {

	private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private Long id;
    /**
     * 语言类型
     */
    @ApiModelProperty(value = "语言类型")
    private String languageType;
    /**
     * 语言名称
     */
    @ApiModelProperty(value = "语言名称")
    private String languageName;
    /**
     * 是否启用 1启用 0关闭
     */
    @ApiModelProperty(value = "是否启用 1启用 0关闭")
    private Integer status;
    /**
     * 排序
     */
    @ApiModelProperty(value = "排序")
    private Integer sort;
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
    private Integer isDeleted;

}
