package com.minigod.zero.manage.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.minigod.zero.core.mp.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 热门推荐股票 实体类
 *
 * @author 掌上智珠
 * @since 2023-03-18
 */
@Data
@TableName("oms_hot_recommend")
@ApiModel(value = "HotRecommend对象", description = "热门推荐股票")
@EqualsAndHashCode(callSuper = true)
public class HotRecommendEntity extends BaseEntity {

    /**
     * 码表
     */
    @ApiModelProperty(value = "股票ID")
    private String assetId;
    /**
     * 股票logo
     */
    @ApiModelProperty(value = "股票logo")
    private String logoUrl;
    /**
     * 推荐原因
     */
    @ApiModelProperty(value = "推荐原因")
    private String description;
    /**
     * 优先级
     */
    @ApiModelProperty(value = "优先级")
    private Integer priority;
    /**
     * 是否推荐;1、推荐,2、取消推荐
     */
    @ApiModelProperty(value = "是否推荐;1、推荐,2、取消推荐")
    private Integer isRecommend;
    /**
     * 类型 null:全部;1、热门;2、AI擒牛
     */
    @ApiModelProperty(value = "类型 null:全部;1、热门;2、AI擒牛")
    private Integer type;
    /**
     * 文件名
     */
    @ApiModelProperty(value = "文件名")
    private String fileName;
    /**
     * 版本号
     */
    @ApiModelProperty(value = "版本号")
    private Integer hotRmVersion;

}
