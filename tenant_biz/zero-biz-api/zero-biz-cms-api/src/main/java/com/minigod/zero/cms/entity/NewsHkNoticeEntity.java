package com.minigod.zero.cms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.minigod.zero.core.mp.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.ibatis.type.Alias;

/**
 * 公告表 实体类
 *
 * @author 掌上智珠
 * @since 2023-01-16
 */
@Data
@TableName("cms_news_hk_notice")
@ApiModel(value = "NewsHkNotice对象", description = "公告表")
@EqualsAndHashCode(callSuper = true)
@Alias("cmsNewsHkNoticeEntity")
public class NewsHkNoticeEntity extends BaseEntity {

    /**
     * 访问地址
     */
    @ApiModelProperty(value = "访问地址")
    private String url;
    /**
     * 主题
     */
    @ApiModelProperty(value = "主题")
    private String title;
    /**
     * 路径
     */
    @ApiModelProperty(value = "路径")
    private String path;
    /**
     * 文件类型
     */
    @ApiModelProperty(value = "文件类型")
    private String fileType;
    /**
     * 本地路径
     */
    @ApiModelProperty(value = "本地路径")
    private String localPath;
    /**
     * 资产ID
     */
    @ApiModelProperty(value = "资产ID")
    private String assetId;
    /**
     * 内容
     */
    @ApiModelProperty(value = "内容")
    private String content;
    /**
     * 文件名
     */
    @ApiModelProperty(value = "文件名")
    private String fileName;
	/**
	 * 自增id
	 */
	@ApiModelProperty("自增id")
	private Long logId;
}
