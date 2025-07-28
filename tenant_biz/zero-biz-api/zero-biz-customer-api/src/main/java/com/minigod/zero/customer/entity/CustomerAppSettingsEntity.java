package com.minigod.zero.customer.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.lang.Boolean;
import java.util.Date;
import lombok.EqualsAndHashCode;
import com.minigod.zero.core.mp.base.BaseEntity;

/**
 * 客户app设置信息表 实体类
 *
 * @author ken
 * @since 2024-04-11
 */
@Data
@TableName("ifund_app_settings")
@ApiModel(value = "CustomerAppSettings对象", description = "客户app设置信息表")
@EqualsAndHashCode(callSuper = true)
public class CustomerAppSettingsEntity extends BaseEntity {
    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id")
    private Long custId;
    /**
     * 隐私开关设置 每一位都用Y/N表示是否开通：	第1位：资讯推送 第2位：股价提醒 第3位：新股提醒
     */
    @ApiModelProperty(value = "隐私开关设置 每一位都用Y/N表示是否开通：	第1位：资讯推送 第2位：股价提醒 第3位：新股提醒")
    private String privacy;
    /**
     * 手势密码
     */
    @ApiModelProperty(value = "手势密码")
    private String gesturePwd;
    /**
     * 从后台切换到前台需要输入手势密码的时长限制
     */
    @ApiModelProperty(value = "从后台切换到前台需要输入手势密码的时长限制")
    private Integer getstureShowTime;
    /**
     * 0:未自动关注 1:已自动关注
     */
    @ApiModelProperty(value = "0:未自动关注 1:已自动关注")
    private Boolean robotAttentionStatus;
    /**
     * 客户是否接受协议 0:不接受，1:接受
     */
    @ApiModelProperty(value = "客户是否接受协议 0:不接受，1:接受")
    private Boolean acceptAgreement;

}
