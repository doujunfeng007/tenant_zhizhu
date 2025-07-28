package com.minigod.zero.platform.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.minigod.zero.core.tenant.mp.TenantEntity;
import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;

/**
 * 模块类型信息表 实体类
 *
 * @author 掌上智珠
 * @since 2023-03-16
 */
@Data
@TableName("platform_template_type")
@ApiModel(value = "PlatformTemplateType对象", description = "模块类型信息表")
@EqualsAndHashCode(callSuper = true)
public class PlatformTemplateTypeEntity extends TenantEntity {

    /**
     * 业务类型 1-邮件 2-短信 3-系统通知 4-消息通知
     */
    @ApiModelProperty(value = "业务类型 1-邮件 2-短信 3-系统通知 4-消息通知")
    private Integer busType;
    /**
     * 类型名称
     */
    @ApiModelProperty(value = "类型名称")
    private String tempName;
    /**
     * 子类型 0-主类型
     */
    @ApiModelProperty(value = "子类型 0-主类型")
    private Long parentId;
    /**
     * 发送方式(0-自动 1-手动)
     */
    @ApiModelProperty(value = "发送方式(0-自动 1-手动)")
    private Integer sendWay;

}
