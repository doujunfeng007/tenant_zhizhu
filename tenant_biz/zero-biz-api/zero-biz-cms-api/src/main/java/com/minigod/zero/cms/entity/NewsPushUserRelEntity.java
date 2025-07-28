package com.minigod.zero.cms.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;

/**
 * 新闻推送用户关联表 实体类
 *
 * @author 掌上智珠
 * @since 2023-03-13
 */
@Data
@TableName("cms_news_push_user_rel")
@Alias("cms_news_push_user_rel")
@ApiModel(value = "NewsPushUserRel对象", description = "新闻推送用户关联表")
public class NewsPushUserRelEntity implements Serializable {

	private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private Long id;
    /**
     * 资讯推送申请表id
     */
    @ApiModelProperty(value = "资讯推送申请表id")
    private Long newsPushId;
    /**
     * 关联的推送用户
     */
    @ApiModelProperty(value = "关联的推送用户")
    private Long userId;

}
