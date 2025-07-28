package com.minigod.zero.cms.entity.oms;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.minigod.zero.core.mp.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 系统维护 实体类
 *
 * @author 掌上智珠
 * @since 2023-03-23
 */
@Data
@TableName("oms_sys_maintenance")
@ApiModel(value = "SysMaintenance对象", description = "系统维护")
@EqualsAndHashCode(callSuper = true)
public class SysMaintenanceEntity extends BaseEntity {

    /**
     * 系统类型 0:全部 1:android 2:ios
     */
    @ApiModelProperty(value = "系统类型 0:全部 1:android 2:ios")
    private Integer sysType;
    /**
     * 登录状态 0:全部 1:已登录 2:未登录
     */
    @ApiModelProperty(value = "登录状态 0:全部 1:已登录 2:未登录")
    private Integer loginState;
    /**
     * 开户状态 0:全部 1:已开户 2:未开户
     */
    @ApiModelProperty(value = "开户状态 0:全部 1:已开户 2:未开户")
    private Integer openState;
    /**
     * 行情权限 0:全部 1:L2 2:非L2
     */
    @ApiModelProperty(value = "行情权限 0:全部 1:L2 2:非L2")
    private Integer quotLevel;
    /**
     * 生效时间
     */
    @ApiModelProperty(value = "生效时间")
	@DateTimeFormat(
		pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@JsonFormat(
		pattern = "yyyy-MM-dd HH:mm:ss"
	)
    private Date forceTime;
    /**
     * 失效时间
     */
    @ApiModelProperty(value = "失效时间")
	@DateTimeFormat(
		pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@JsonFormat(
		pattern = "yyyy-MM-dd HH:mm:ss"
	)
    private Date deadTime;
    /**
     * 提示内容(简)
     */
    @ApiModelProperty(value = "提示内容(简)")
    private String tipsContentHans;
    /**
     * 提示内容（繁）
     */
    @ApiModelProperty(value = "提示内容（繁）")
    private String tipsContentHant;
    /**
     * 提示内容（英）
     */
    @ApiModelProperty(value = "提示内容（英）")
    private String tipsContentEn;

}
