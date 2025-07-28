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
 * 焦点功能区图标管理 实体类
 *
 * @author 掌上智珠
 * @since 2023-03-21
 */
@Data
@TableName("oms_discover_icon")
@ApiModel(value = "DiscoverIcon对象", description = "焦点功能区图标管理")
@EqualsAndHashCode(callSuper = true)
public class DiscoverIconEntity extends BaseEntity {

    /**
     * 功能代码
     */
    @ApiModelProperty(value = "功能代码")
    private String functionCode;
    /**
     * 功能名称
     */
    @ApiModelProperty(value = "功能名称")
    private String functionName;
    /**
     * icon名称
     */
    @ApiModelProperty(value = "icon名称")
    private String name;
    /**
     * icon图片地址(IOS小图)
     */
    @ApiModelProperty(value = "icon图片地址(IOS小图)")
    private String urlSmall;
    /**
     * icon图片地址(IOS大图)
     */
    @ApiModelProperty(value = "icon图片地址(IOS大图)")
    private String urlBig;
    /**
     * icon图片地址(安卓)
     */
    @ApiModelProperty(value = "icon图片地址(安卓)")
    private String urlAndroid;
    /**
     * icon黑图案(IOS)
     */
    @ApiModelProperty(value = "icon黑图案(IOS)")
    private String urlIosBlack;
    /**
     * icon白图案(IOS)
     */
    @ApiModelProperty(value = "icon白图案(IOS)")
    private String urlIosWhite;
    /**
     * icon黑图案(安卓)
     */
    @ApiModelProperty(value = "icon黑图案(安卓)")
    private String urlAndroidBlack;
    /**
     * icon白图案(安卓)
     */
    @ApiModelProperty(value = "icon白图案(安卓)")
    private String urlAndroidWhite;
    /**
     * 链接地址
     */
    @ApiModelProperty(value = "链接地址")
    private String urlPage;
    /**
     * 是否控制ip
     */
    @ApiModelProperty(value = "是否控制ip")
    private Boolean ipFilter;

}
