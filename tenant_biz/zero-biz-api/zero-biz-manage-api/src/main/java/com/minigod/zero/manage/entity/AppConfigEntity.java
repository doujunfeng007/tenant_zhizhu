package com.minigod.zero.manage.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;
import com.minigod.zero.core.tenant.mp.TenantEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * APP配置 实体类
 *
 * @author 掌上智珠
 * @since 2023-03-23
 */
@Data
@TableName("oms_app_config")
@ApiModel(value = "AppConfig对象", description = "APP配置")
@EqualsAndHashCode(callSuper = true)
public class AppConfigEntity extends TenantEntity {

    /**
     * 适用人群类别 0:全部 1:指定用户 2:导入用户
     */
    @ApiModelProperty(value = "适用人群类别 0:全部 1:指定用户 2:导入用户")
    private Integer groupType;
    /**
     * 描述
     */
    @ApiModelProperty(value = "描述")
    private String configDesc;
    /**
     * key
     */
    @ApiModelProperty(value = "key")
    private String itemKey;
    /**
     * 指定用户
     */
    @ApiModelProperty(value = "指定用户")
    private String userIds;
    /**
     * 配置内容项
     */
    @ApiModelProperty(value = "配置内容项")
    private String configContent;
    /**
     * 修改人名
     */
    @ApiModelProperty(value = "修改人名")
    private String updateUserName;

	@ApiModelProperty(value = "发布时间")
    private Date publishTime;

}
