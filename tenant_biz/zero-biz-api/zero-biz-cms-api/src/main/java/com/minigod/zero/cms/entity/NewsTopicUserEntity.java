package com.minigod.zero.cms.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.minigod.zero.core.mp.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 专题订阅用户表 实体类
 *
 * @author 掌上智珠
 * @since 2022-11-24
 */
@Data
@TableName("cms_news_topic_user")
@ApiModel(value = "NewsTopicUser对象", description = "专题订阅用户表")
@EqualsAndHashCode(callSuper = true)
public class NewsTopicUserEntity extends BaseEntity {

    /**
     * 专题ID
     */
    @ApiModelProperty(value = "专题ID")
    private Long topicId;
    /**
     * 用户ID
     */
    @ApiModelProperty(value = "用户ID")
    private Long userId;

}
