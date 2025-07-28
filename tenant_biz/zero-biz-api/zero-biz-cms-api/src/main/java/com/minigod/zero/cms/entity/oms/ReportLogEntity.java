package com.minigod.zero.cms.entity.oms;

import com.baomidou.mybatisplus.annotation.TableName;
import com.minigod.zero.core.mp.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * APP日志下载 实体类
 *
 * @author 掌上智珠
 * @since 2023-03-23
 */
@Data
@TableName("oms_report_log")
@ApiModel(value = "ReportLog对象", description = "APP日志下载")
@EqualsAndHashCode(callSuper = true)
public class ReportLogEntity extends BaseEntity {

    /**
     * 客户ID
     */
    @ApiModelProperty(value = "客户ID")
    private Long userId;
    /**
     * 业务账号
     */
    @ApiModelProperty(value = "业务账号")
    private String busAccount;
    /**
     * 手机
     */
    @ApiModelProperty(value = "手机")
    private String phoneNumber;
    /**
     * 邮箱
     */
    @ApiModelProperty(value = "邮箱")
    private String email;
    /**
     * 日志内容
     */
    @ApiModelProperty(value = "日志内容")
    private String logContent;
    /**
     * 日志路径
     */
    @ApiModelProperty(value = "日志路径")
    private String logPath;

}
