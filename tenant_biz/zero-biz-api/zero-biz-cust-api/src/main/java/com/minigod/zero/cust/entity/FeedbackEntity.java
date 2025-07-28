package com.minigod.zero.cust.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

/**
 * 客户意见反馈表 实体类
 *
 * @author 掌上智珠
 * @since 2023-03-16
 */
@Data
@TableName("cust_feedback")
@ApiModel(value = "Feedback对象", description = "客户意见反馈表")
public class FeedbackEntity implements Serializable {

	private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private Long id;
    /**
     * 客户ID
     */
    @ApiModelProperty(value = "客户ID")
    private Long custId;
    /**
     * 昵称
     */
    @ApiModelProperty(value = "昵称")
    private String nickName;
    /**
     * 联系方式
     */
    @ApiModelProperty(value = "联系方式")
    private String contact;
    /**
     * ip地址
     */
    @ApiModelProperty(value = "ip地址")
    private String ip;
    /**
     * 反馈内容
     */
    @ApiModelProperty(value = "反馈内容")
    private String content;
    /**
     * 图片url
     */
    @ApiModelProperty(value = "图片url")
    private String imageUrl;
    /**
     * 来源
     */
    @ApiModelProperty(value = "来源")
    private String source;
    /**
     * 回复状态(N-未回复,Y-已回复)
     */
    @ApiModelProperty(value = "回复状态(N-未回复,Y-已回复)")
    private String replyStatus;
    /**
     * 最近回复时间
     */
    @ApiModelProperty(value = "最近回复时间")
    private Date replyTime;
    /**
     * 是否已经处理(N-待处理,Y-已处理)
     */
    @ApiModelProperty(value = "是否已经处理(N-待处理,Y-已处理)")
    private String handleStatus;
    /**
     * 处理意见
     */
    @ApiModelProperty(value = "处理意见")
    private String handleResult;
    /**
     * 处理人
     */
    @ApiModelProperty(value = "处理人")
    private Integer handleUser;
    /**
     * 处理时间
     */
    @ApiModelProperty(value = "处理时间")
    private Date handleTime;
    /**
     * 租户ID
     */
    @ApiModelProperty(value = "租户ID")
    private String tenantId;
    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    private Long createUser;
    /**
     * 创建部门
     */
    @ApiModelProperty(value = "创建部门")
    private Long createDept;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    /**
     * 修改人
     */
    @ApiModelProperty(value = "修改人")
    private Long updateUser;
    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间")
    private Date updateTime;
    /**
     * 状态：0-无效/禁用 1-有效/启用
     */
    @ApiModelProperty(value = "状态：0-无效/禁用 1-有效/启用")
    private Integer status;
    /**
     * 是否已删除：1-已删除
     */
    @ApiModelProperty(value = "是否已删除：1-已删除")
    private Integer isDeleted;

}
