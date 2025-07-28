package com.minigod.zero.bpm.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 开户申请-客户填写信息缓存表 实体类
 *
 * @author 掌上智珠
 * @since 2023-05-17
 */
@Data
@TableName("acct_open_info_temp")
@ApiModel(value = "AcctOpenInfoTemp对象", description = "开户申请-客户填写信息缓存表")
public class AcctOpenInfoTempEntity implements Serializable {

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
     * 前端保存信息
     */
    @ApiModelProperty(value = "前端保存信息")
    private String jsonInfo;
    /**
     * 步骤
     */
    @ApiModelProperty(value = "步骤")
    private Integer step;
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
