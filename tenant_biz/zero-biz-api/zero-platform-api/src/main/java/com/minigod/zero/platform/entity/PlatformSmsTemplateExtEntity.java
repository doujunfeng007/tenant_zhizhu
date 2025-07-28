package com.minigod.zero.platform.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.minigod.zero.core.tenant.mp.TenantEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 消息模板扩展表 实体类
 *
 * @author 掌上智珠
 * @since 2023-02-27
 */
@Data
@TableName("platform_sms_template_ext")
@ApiModel(value = "SmsTemplateExt对象", description = "消息模板扩展表")
@EqualsAndHashCode(callSuper = true)
public class PlatformSmsTemplateExtEntity extends TenantEntity {

    /**
     * 消息代码
     */
    @ApiModelProperty(value = "消息代码")
    private Integer code;
    /**
     *
     */
    @ApiModelProperty(value = "")
    private String keyName;
    /**
     *
     */
    @ApiModelProperty(value = "")
    private String keyValue;
    /**
     *
     */
    @ApiModelProperty(value = "")
    private String remark;

}
