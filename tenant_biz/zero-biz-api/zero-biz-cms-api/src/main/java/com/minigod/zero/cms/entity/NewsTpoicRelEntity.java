package com.minigod.zero.cms.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.minigod.zero.core.mp.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.ibatis.type.Alias;

/**
 * 新闻与专题关联表 实体类
 *
 * @author 掌上智珠
 * @since 2022-11-24
 */
@Data
@TableName("cms_news_tpoic_rel")
@ApiModel(value = "NewsTpoicRel对象", description = "新闻与专题关联表")
@EqualsAndHashCode(callSuper = true)
@Alias("cmsNewsTpoicRelEntity")
public class NewsTpoicRelEntity extends BaseEntity {

    /**
     * 专题ID
     */
    @ApiModelProperty(value = "专题ID")
    private Long topicId;
    /**
     * 新闻ID
     */
    @ApiModelProperty(value = "新闻ID")
    private Long newsId;

}
