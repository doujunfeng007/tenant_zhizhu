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
 * 功能白名单用户信息 实体类
 *
 * @author 掌上智珠
 * @since 2023-03-16
 */
@Data
@TableName("cust_func_white_list")
@ApiModel(value = "FuncWhiteList对象", description = "功能白名单用户信息")
public class FuncWhiteListEntity implements Serializable {

	private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private Long id;
    /**
     * 功能ID
     */
    @ApiModelProperty(value = "功能ID",required = true)
    private String funcId;
    /**
     * 客户ID
     */
    @ApiModelProperty(value = "客户ID",required = true)
    private Long custId;
    /**
     * 资金账号
     */
    @ApiModelProperty(value = "资金账号")
    private String fundAccount;
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
