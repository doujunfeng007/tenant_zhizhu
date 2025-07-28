package com.minigod.zero.bpm.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import java.io.Serializable;

/**
 * 线下开户-BPM回调记录表 实体类
 *
 * @author 掌上智珠
 * @since 2023-05-17
 */
@Data
@TableName("acct_open_offline")
@ApiModel(value = "AcctOpenOffline对象", description = "线下开户-BPM回调记录表")
public class AcctOpenOfflineEntity implements Serializable {

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
     * 线下开户申请信息
     */
    @ApiModelProperty(value = "线下开户申请信息")
    private String info;
    /**
     * 回调状态(0:未回调 1:成功 2:失败)
     */
    @ApiModelProperty(value = "回调状态(0:未回调 1:成功 2:失败)")
    private Integer pushRecved;
    /**
     * 申请状态(0:未申请 1:已申请)
     */
    @ApiModelProperty(value = "申请状态(0:未申请 1:已申请)")
    private Integer status;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间")
    private Date updateTime;

}
