package com.minigod.zero.platform.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.minigod.zero.core.tenant.mp.TenantEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 消息模板表 实体类
 *
 * @author 掌上智珠
 * @since 2023-02-27
 */
@Data
@TableName("platform_sms_template")
@ApiModel(value = "SmsTemplate对象", description = "消息模板表")
@EqualsAndHashCode(callSuper = true)
public class PlatformSmsTemplateEntity extends TenantEntity {
    /**
     * 消息代码
     */
    @ApiModelProperty(value = "消息代码")
    private Integer code;
    /**
     *
     */
    @ApiModelProperty(value = "")
    private String event;
    /**
     * 业务类型
     */
    @ApiModelProperty(value = "业务类型")
    private Byte busType;
    /**
     *
     */
    @ApiModelProperty(value = "")
    private String channel;

}
