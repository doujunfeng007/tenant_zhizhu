package com.minigod.zero.manage.entity.cms;

import com.baomidou.mybatisplus.annotation.TableName;
import com.minigod.zero.core.mp.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 新闻用户访问信息 实体类
 *
 * @author 掌上智珠
 * @since 2022-11-24
 */
@Data
@TableName("cms_news_info_user")
@ApiModel(value = "NewsInfoUser对象", description = "新闻用户访问信息")
@EqualsAndHashCode(callSuper = true)
public class NewsInfoUserEntity extends BaseEntity {

    /**
     * 新闻ID
     */
    @ApiModelProperty(value = "新闻ID")
    private Long newsId;
    /**
     * 用户ID
     */
    @ApiModelProperty(value = "用户ID")
    private Long userId;
    /**
     * 收藏
     */
    @ApiModelProperty(value = "收藏")
    private Boolean isFavorite;
    /**
     * 点赞
     */
    @ApiModelProperty(value = "点赞")
    private Boolean isLaud;
    /**
     * 发布时间
     */
    @ApiModelProperty(value = "发布时间")
    private Date issueTime;
    /**
     * 收藏时间
     */
    @ApiModelProperty(value = "收藏时间")
    private Date favorTime;

}
