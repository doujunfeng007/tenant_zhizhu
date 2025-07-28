package com.minigod.zero.manage.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.lang.Boolean;
import java.util.Date;
import lombok.EqualsAndHashCode;
import com.minigod.zero.core.mp.base.BaseEntity;

/**
 * 焦点功能 实体类
 *
 * @author 掌上智珠
 * @since 2023-03-21
 */
@Data
@TableName("oms_discover_role_icon")
@ApiModel(value = "DiscoverRoleIcon对象", description = "焦点功能")
@EqualsAndHashCode(callSuper = true)
public class DiscoverRoleIconEntity extends BaseEntity {

    /**
     * 角色ID 1:注册用户 2:开户用户 3:交易用户
     */
    @ApiModelProperty(value = "角色ID 1:注册用户 2:开户用户 3:交易用户")
    private Integer roleId;
    /**
     * icon图标ID
     */
    @ApiModelProperty(value = "icon图标ID")
    private Integer iconId;
    /**
     * 对应角色下排序优先级
     */
    @ApiModelProperty(value = "对应角色下排序优先级")
    private Integer grade;
    /**
     * 对应角色下是否展示
     */
    @ApiModelProperty(value = "对应角色下是否展示")
    private Boolean isDisplay;

}
