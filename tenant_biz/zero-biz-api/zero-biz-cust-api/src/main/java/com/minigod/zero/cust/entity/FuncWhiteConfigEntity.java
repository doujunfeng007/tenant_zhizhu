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
 * 功能白名单启用配置 实体类
 *
 * @author 掌上智珠
 * @since 2023-03-16
 */
@Data
@TableName("cust_func_white_config")
@ApiModel(value = "FuncWhiteConfig对象", description = "功能白名单启用配置")
public class FuncWhiteConfigEntity implements Serializable {

	private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private Long id;
    /**
     * 功能ID
     */
    @ApiModelProperty(value = "功能ID")
    private String funcId;
    /**
     * 功能名称
     */
    @ApiModelProperty(value = "功能名称")
    private String funcName;
    /**
     * 功能状态：1.全局使用,2.白名单使用,3.停用
     */
    @ApiModelProperty(value = "功能状态：1.全局使用,2.白名单使用,3.停用")
    private Integer funcStatus;
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
