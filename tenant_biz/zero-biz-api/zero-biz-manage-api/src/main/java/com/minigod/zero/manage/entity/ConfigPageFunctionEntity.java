package com.minigod.zero.manage.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.lang.Boolean;
import lombok.EqualsAndHashCode;
import com.minigod.zero.core.mp.base.BaseEntity;

/**
 * 配置页面组件 实体类
 *
 * @author 掌上智珠
 * @since 2023-03-21
 */
@Data
@TableName("oms_config_page_func")
@ApiModel(value = "ConfigPageFunction对象", description = "配置页面组件")
@EqualsAndHashCode(callSuper = true)
public class ConfigPageFunctionEntity extends BaseEntity {

    /**
     * 功能代码
     */
    @ApiModelProperty(value = "功能代码")
    private String functionCode;
    /**
     * 功能页类型：1-发现页；2-自选页；3-选股策略页；4-焦点功能区；5-交易页；88-广告页；
     */
    @ApiModelProperty(value = "功能页类型：1-发现页；2-自选页；3-选股策略页；4-焦点功能区；5-交易页；88-广告页；")
    private Integer functionType;
    /**
     * 功能名称
     */
    @ApiModelProperty(value = "功能名称")
    private String functionName;
    /**
     * 用户权限：1-有查看权限；0-无查看权限；第一位表示注册用户权限，第二位表示开户用户权限，第三位表示入金用户权限，第四位表示交易用户权限
     */
    @ApiModelProperty(value = "用户权限：1-有查看权限；0-无查看权限；第一位表示注册用户权限，第二位表示开户用户权限，第三位表示入金用户权限，第四位表示交易用户权限")
    private String functionRight;
    /**
     * 在该功能页类型下的排序号
     */
    @ApiModelProperty(value = "在该功能页类型下的排序号")
    private Integer grade;
    /**
     * 描述
     */
    @ApiModelProperty(value = "描述")
    private String remark;
    /**
     * 是否ip控制：0-否；1-是；
     */
    @ApiModelProperty(value = "是否ip控制：0-否；1-是；")
    private Boolean ipFilter;

}
