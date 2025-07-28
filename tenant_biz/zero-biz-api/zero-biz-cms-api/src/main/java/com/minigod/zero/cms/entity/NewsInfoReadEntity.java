package com.minigod.zero.cms.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.minigod.zero.core.mp.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.ibatis.type.Alias;

import javax.validation.constraints.Size;
import java.util.Date;

/**
 * 新闻阅读情况表 实体类
 *
 * @author 掌上智珠
 * @since 2022-11-24
 */
@Data
@TableName("cms_news_info_read")
@Alias("cms_news_info_read")
@ApiModel(value = "NewsInfoRead对象", description = "新闻阅读情况表")
@EqualsAndHashCode(callSuper = true)
public class NewsInfoReadEntity extends BaseEntity {

    /**
     * 新闻ID
     */
    @ApiModelProperty(value = "新闻ID")
    private Long newsId;
    /**
     * 阅读次数
     */
    @ApiModelProperty(value = "阅读次数")
	@Size(min = 0, message = "阅读次数不能小于0")
    private Integer readNum = 0;
    /**
     * 是否要闻
     */
    @ApiModelProperty(value = "是否要闻")
    private Boolean isImportant;
    /**
     * 是否直播
     */
    @ApiModelProperty(value = "是否直播")
    private Boolean isLive;
    /**
     * 8-头条 9-热点资讯
     */
    @ApiModelProperty(value = "8-头条 9-热点资讯")
    private Integer tag;
    /**
     * 发布时间
     */
    @ApiModelProperty(value = "发布时间")
    private Date issueTime;
    /**
     * 点赞数
     */
    @ApiModelProperty(value = "点赞数")
	@Size(min = 0, message = "点赞数不能小于0")
    private Integer laudNum = 0;
    /**
     * 收藏数
     */
    @ApiModelProperty(value = "收藏数")
	@Size(min = 0, message = "收藏数不能小于0")
    private Integer favorNum = 0;
    /**
     * 分享数
     */
    @ApiModelProperty(value = "分享数")
    private Integer shareNum = 0;
    /**
     * 调整后的阅读数
     */
    @ApiModelProperty(value = "调整后的阅读数")
	@Size(min = 0, message = "调整后的阅读数不能小于0")
    private Integer modifyReadNum = 0;
    /**
     * 调整后的点赞数
     */
    @ApiModelProperty(value = "调整后的点赞数")
	@Size(min = 0, message = "调整后的点赞数不能小于0")
    private Integer modifyLaudNum = 0;
    /**
     * 调整后的收藏数
     */
    @ApiModelProperty(value = "调整后的收藏数")
	@Size(min = 0, message = "调整后的收藏数不能小于0")
    private Integer modifyFavorNum = 0;
    /**
     * 调整后的分享数
     */
    @ApiModelProperty(value = "调整后的分享数")
    private Integer modifyShareNum = 0;
    /**
     * 是否百科
     */
    @ApiModelProperty(value = "是否百科")
    private Integer isWiki;

}
