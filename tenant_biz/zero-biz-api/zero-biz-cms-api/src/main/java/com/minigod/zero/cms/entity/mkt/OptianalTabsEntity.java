package com.minigod.zero.cms.entity.mkt;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 客户自选分组表 实体类
 *
 * @author 掌上智珠
 * @since 2023-03-16
 */
@Data
@TableName("cust_optianal_tabs")
@ApiModel(value = "OptianalTabs对象", description = "客户自选分组表")
public class OptianalTabsEntity implements Serializable {

	private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private Long id;
    /**
     * 分组名称
     */
    @ApiModelProperty(value = "分组名称")
    private String tabName;
    /**
     * 客户号
     */
    @ApiModelProperty(value = "客户号")
    private Long custId;
    /**
     * 是否显示  1显示 0隐藏
     */
    @ApiModelProperty(value = "是否显示  1显示 0隐藏")
    private Integer display;
    /**
     * 租户ID
     */
    @ApiModelProperty(value = "租户ID")
	@TableField(exist = false)
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

	// 冗余字段
	@TableField(exist = false)
	private String tcName;
	@TableField(exist = false)
	private String enName;
}
