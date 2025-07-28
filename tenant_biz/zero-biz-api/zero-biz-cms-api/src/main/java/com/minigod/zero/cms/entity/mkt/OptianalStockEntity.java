package com.minigod.zero.cms.entity.mkt;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 客户自选股列表 实体类
 *
 * @author 掌上智珠
 * @since 2023-03-16
 */
@Data
@TableName("cust_optianal_stock")
@ApiModel(value = "OptianalStock对象", description = "客户自选股列表")
public class OptianalStockEntity implements Serializable {

	private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private Long id;
    /**
     * 客户号
     */
    @ApiModelProperty(value = "客户号")
    private Long custId;
    /**
     * 用户分组列表
     */
    @ApiModelProperty(value = "用户分组列表")
    private String tabIds;
    /**
     * 自选股数据
     */
    @ApiModelProperty(value = "自选股数据")
    private String content;
    /**
     * 自选股版本号
     */
    @ApiModelProperty(value = "自选股版本号")
    private Long version;
    /**
     * 0表示未同步，1表示已同步
     */
    @ApiModelProperty(value = "0表示未同步，1表示已同步")
    private Integer syncFlag;
    /**
     * 5.0 新库导入 1 已同步  0 未同步
     */
    @ApiModelProperty(value = "5.0 新库导入 1 已同步  0 未同步")
    private Integer syncStatus;
    /**
     * 状态同步时间
     */
    @ApiModelProperty(value = "状态同步时间")
    private Date syncTime;
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
     * 状态：0-不允许 1-运行
     */
    @ApiModelProperty(value = "状态：0-不允许 1-运行")
    private Integer status;
    /**
     * 是否已删除：1-已删除
     */
    @ApiModelProperty(value = "是否已删除：1-已删除")
    private Integer isDeleted;

}
