package com.minigod.zero.cms.entity.oms;

import com.baomidou.mybatisplus.annotation.TableName;
import com.minigod.zero.core.tenant.mp.TenantEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * APP版本信息表 实体类
 *
 * @author 掌上智珠
 * @since 2023-04-24
 */
@Data
@TableName("oms_app_version")
@ApiModel(value = "AppVersion对象", description = "APP版本信息表")
public class AppVersionEntity extends TenantEntity {

	private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private Long id;
    /**
     * 版本号
     */
    @ApiModelProperty(value = "版本号")
    private String versionNo;
    /**
     * url地址
     */
    @ApiModelProperty(value = "url地址")
    private String url;
    /**
     * 大小
     */
    @ApiModelProperty(value = "大小")
    private String size;
    /**
     * 更新说明-简体
     */
    @ApiModelProperty(value = "更新说明-简体")
    private String updateDeclare;
    /**
     * 更新说明-繁体
     */
    @ApiModelProperty(value = "更新说明-繁体")
    private String updateDeclareHant;
    /**
     * 更新说明-英语
     */
    @ApiModelProperty(value = "更新说明-英语")
    private String updateDeclareEn;
    /**
     * 更新说明-越南语
     */
    @ApiModelProperty(value = "更新说明-越南语")
    private String updateDeclareVi;
    /**
     * 设备类型(0PC;1手机;2平板)
     */
    @ApiModelProperty(value = "设备类型(0PC;1手机;2平板)")
    private Integer deviceType;
    /**
     * 操作系统类型(0安卓，1苹果，2WP)
     */
    @ApiModelProperty(value = "操作系统类型(0安卓，1苹果，2WP)")
    private Integer osType;
    /**
     * 渠道
     */
    @ApiModelProperty(value = "渠道")
    private String channel;
    /**
     * MD5
     */
    @ApiModelProperty(value = "MD5")
    private String md5;
    /**
     * 升级检测提示(0为已是最新版本无须升级,1为有新版本，无须升级,2为有新版本，建议升级,3为有新版本，必须升级方可使用)
     */
    @ApiModelProperty(value = "升级检测提示(0为已是最新版本无须升级,1为有新版本，无须升级,2为有新版本，建议升级,3为有新版本，必须升级方可使用)")
    private Integer checkCode;
    /**
     * 是否最新版本(1是，0不是)
     */
    @ApiModelProperty(value = "是否最新版本(1是，0不是)")
    private Boolean isNew;
    /**
     * 是否忽略升级提示 0:否 1：是
     */
    @ApiModelProperty(value = "是否忽略升级提示 0:否 1：是")
    private Integer isIgnoreNotice;
    /**
     * APP编码 1：智珠 2：财富
     */
    @ApiModelProperty(value = "APP编码 1：智珠 2：财富")
    private Integer appCode;
}
