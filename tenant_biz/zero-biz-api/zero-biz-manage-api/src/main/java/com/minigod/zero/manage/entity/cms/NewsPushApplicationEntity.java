package com.minigod.zero.manage.entity.cms;

import com.baomidou.mybatisplus.annotation.TableName;
import com.minigod.zero.core.mp.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.ibatis.type.Alias;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * 资讯推送申请表 实体类
 *
 * @author 掌上智珠
 * @since 2023-03-13
 */
@Data
@TableName("cms_news_push_application")
@ApiModel(value = "NewsPushApplication对象", description = "资讯推送申请表")
public class NewsPushApplicationEntity extends BaseEntity {

	private static final long serialVersionUID = 1L;

    /**
     * 资讯ID
     */
    @ApiModelProperty(value = "资讯ID")
    private Long newsId;
    /**
     * 审核人
     */
    @ApiModelProperty(value = "审核人")
    private Long confirmUser;
	/**
	 * 审核时间
	 */
	@ApiModelProperty(value = "审核时间")
	private Date confirmTime;
    /**
     * 推送标题
     */
    @ApiModelProperty(value = "推送标题")
	@NotNull(message = "推送标题不能为空")
	@Size(max = 40, message = "推送标题不能超过40个字")
    private String pushTitle;
    /**
     * 繁体-推送标题
     */
    @ApiModelProperty(value = "繁体-推送标题")
	@Size(max = 40, message = "繁体-推送标题不能超过40个字")
    private String pushTitleHant;
    /**
     * 英文-推送标题
     */
    @ApiModelProperty(value = "英文-推送标题")
	@Size(max = 150, message = "英文-推送标题不能超过150个字母")
    private String pushTitleEn;
    /**
     * 新闻类型
     */
    @ApiModelProperty(value = "新闻类型")
    private Boolean newsType;
    /**
     * 摘要
     */
    @ApiModelProperty(value = "推送内容")
	@NotNull(message = "推送内容不能为空")
	@Size(max = 120, message = "推送内容不能超过120个字")
    private String summary;
    /**
     * 繁体-摘要
     */
    @ApiModelProperty(value = "繁体-推送内容")
	@Size(max = 120, message = "繁体-推送内容不能超过120个字")
    private String summaryHant;
    /**
     * 英文-摘要
     */
    @ApiModelProperty(value = "英文-推送内容")
	@Size(max = 600, message = "英文-推送内容不能超过600个字")
    private String summaryEn;
    /**
     * 申请状态[1-待提交 2-待审核 3-审不过 4-待推送 5-已推送 6-已撤销]
     */
    @ApiModelProperty(value = "申请状态[1-待提交 2-待审核 3-审不过 4-待推送 5-已推送 6-已撤销]")
    private Integer applyStatus;
    /**
     * 推送方式[1-push 2-短信 3-邮件]
     */
    @ApiModelProperty(value = "推送方式[1-push 2-短信 3-邮件]")
	@NotNull(message = "推送方式不能为空")
    private String pushWay;
    /**
     * 发送方式[0-即时 1-定时]
     */
    @ApiModelProperty(value = "发送方式[0-即时 1-定时]")
	@NotNull(message = "发送方式不能为空")
    private Integer sendWay;
    /**
     * 发送时间
     */
    @ApiModelProperty(value = "发送时间")
    private Date sendTime;
    /**
     * 适应人群[0-全部用户(A-全站)  1-特定用户(除0外都是P-个人)]
     */
    @ApiModelProperty(value = "适应人群[0-全部用户(A-全站)  1-特定用户(除0外都是P-个人)]")
	@NotNull(message = "适应人群不能为空")
    private Integer showType;
    /**
     * 跳转方式[1-打开app首页 2-资讯详情首页(若为快讯则定位快讯列表) 3-个股行情首页 4-指定H5链接]
     */
    @ApiModelProperty(value = "跳转方式[1-打开app首页 2-资讯详情首页(若为快讯则定位快讯列表) 3-个股行情首页 4-指定H5链接]")
	@NotNull(message = "跳转设置不能为空")
    private Integer jumpWay;
    /**
     * 跳转地址
     */
    @ApiModelProperty(value = "跳转地址")
    private String url;
    /**
     * 客户端类型(0-全部终端 1-Android 2-IOS)
     */
    @ApiModelProperty(value = "客户端类型(0-安卓 1-ios 2-wp系统 3 全部 4 其他)")
	@NotNull(message = "推送终端不能为空")
    private Integer clientType;
	/**
	 * 跳转3关联个股
	 */
	@ApiModelProperty(value = "跳转3关联个股代码")
	private String stockCode;
}
