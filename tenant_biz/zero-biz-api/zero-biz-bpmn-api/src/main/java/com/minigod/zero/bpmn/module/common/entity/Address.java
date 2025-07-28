package com.minigod.zero.bpmn.module.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

/**
*@ClassName: Address
*@Description: ${description}
*@Author chenyu
*@Date 2024/3/9
*@Version 1.0
*
*/
/**
 * 地址表
 */
@ApiModel(description="地址表")
@Data
@TableName(value = "sys_address")
public class Address implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value="")
    @NotNull(message = "不能为null")
    private Long id;

    /**
     * 名称
     */
    @TableField(value = "`name`")
    @ApiModelProperty(value="名称")
    @Size(max = 40,message = "名称最大长度要小于 40")
    @NotBlank(message = "名称不能为空")
    private String name;

	/**
	 * 名称英文
	 */
	@TableField(value = "`name_en`")
	@ApiModelProperty(value="名称英文")
	@Size(max = 40,message = "名称英文最大长度要小于 40")
	@NotBlank(message = "名称英文不能为空")
	private String name_en;

	/**
	 * 名称繁体
	 */
	@TableField(value = "`name_hant`")
	@ApiModelProperty(value="名称繁体")
	@Size(max = 40,message = "名称繁体最大长度要小于 40")
	@NotBlank(message = "名称繁体不能为空")
	private String name_hant;

    /**
     * 上级ID
     */
    @TableField(value = "parent_id")
    @ApiModelProperty(value="上级ID")
    private Long parentId;

    /**
     * 是否启用 0 否 1是
     */
    @TableField(value = "`enable`")
    @ApiModelProperty(value="是否启用 0 否 1是")
    private Integer enable;

    /**
     * 对应系统代码
     */
    @TableField(value = "code")
    @ApiModelProperty(value="对应系统代码")
    @Size(max = 255,message = "对应系统代码最大长度要小于 255")
    private String code;

    /**
     * 级别
     */
    @TableField(value = "`level`")
    @ApiModelProperty(value="级别")
    private Integer level;

    /**
     * 祖先
     */
    @TableField(value = "ancestors")
    @ApiModelProperty(value="祖先")
    private String ancestors;

    /**
     * 区域类型 1内地 2港澳台
     */
    @TableField(value = "area_type")
    @ApiModelProperty(value="区域类型 1内地 2港澳台")
    private Integer areaType;

    /**
     * 查询code
     */
    @TableField(value = "query_code")
    @ApiModelProperty(value="查询code")
    @Size(max = 255,message = "查询code最大长度要小于 255")
    private String queryCode;

    /**
     * 创建人
     */
    @TableField(value = "create_user")
    @ApiModelProperty(value="创建人")
    @Size(max = 32,message = "创建人最大长度要小于 32")
    private String createUser;

    /**
     * 修改人
     */
    @TableField(value = "update_user")
    @ApiModelProperty(value="修改人")
    @Size(max = 32,message = "修改人最大长度要小于 32")
    private String updateUser;

    /**
     * 修改时间
     */
    @TableField(value = "update_time")
    @ApiModelProperty(value="修改时间")
    private Date updateTime;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    @ApiModelProperty(value="创建时间")
    private Date createTime;

    private static final long serialVersionUID = 1L;
}
