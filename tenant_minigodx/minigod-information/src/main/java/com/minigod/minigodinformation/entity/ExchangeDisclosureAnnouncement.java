package com.minigod.minigodinformation.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 披露公告表
 * @TableName exchange_disclosure_announcement
 */
@TableName(value ="exchange_disclosure_announcement")
@Data
public class ExchangeDisclosureAnnouncement implements Serializable {
    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 公告类型;1.评级文件  2.业绩公告  3定期报告  4派息公告  5重大事项  6停牌公告
     */
    private Integer type;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 发布时间
     */
    private Date time;

    /**
     * 发布人id
     */
    private String custId;

    /**
     * 发布人名字
     */
    private String custName;

    /**
     * 状态;1.已保存  2待审核  3已驳回  4已审核  5已发布
     */
    private Integer status;

    /**
     * 预约号
     */
    private String applicationId;

    /**
     * 拒绝原因
     */
    private String rejectedCause;

    /**
     * 是否删除;是否删除  0未删除  1已删除
     */
    private Integer isDeleted;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新人
     */
    private String updateUser;

    /**
     * 更新时间
     */
    private Date updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
