package com.minigod.zero.bpmn.module.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 * 系统通用文本项配置表
 *
 * @TableName sys_sub_item_config
 */
@Data
@TableName("sys_sub_item_config")
@ApiModel(value = "SysSubItemConfig系统通用文本配置", description = "系统通用文本项配置表")
public class SysSubItemConfigEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 主键
	 */
	@TableField(value = "id")
	@ApiModelProperty(value = "主键")
	@TableId(
		value = "id",
		type = IdType.AUTO
	)
	private Long id;

	/**
	 * 租户ID
	 */
	@TableField(value = "tenant_id")
	@ApiModelProperty(value = "租户ID")
	private String tenantId;

	/**
	 * 配置项主键ID
	 */
	@TableField(value = "item_id")
	@ApiModelProperty(value = "配置项主键ID")
	private Long itemId;

	/**
	 * 配置子项文本
	 */
	@TableField(value = "sub_item_label")
	@ApiModelProperty(value = "配置子项文本")
	private String subItemLabel;

	/**
	 * 配置子项值
	 */
	@TableField(value = "sub_item_value")
	@ApiModelProperty(value = "配置子项值")
	private String subItemValue;

	/**
	 * 排序
	 */
	@TableField(value = "sort")
	@ApiModelProperty(value = "排序")
	private Integer sort;

	/**
	 * 多语言标识
	 */
	@TableField(value = "lang")
	@ApiModelProperty(value = "多语言标识")
	private String lang;

	/**
	 * 创建时间
	 */
	@TableField(value = "create_time")
	@ApiModelProperty(value = "创建时间")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;

	/**
	 * 更新时间
	 */
	@TableField(value = "update_time")
	@ApiModelProperty(value = "修改时间")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date updateTime;

	/**
	 * 更新人
	 */
	@TableField(value = "update_user")
	@ApiModelProperty(value = "修改人")
	@Size(max = 32, message = "修改人最大长度要小于 32")
	private Long updateUser;

	/**
	 * 创建人
	 */
	@TableField(value = "create_user")
	@ApiModelProperty(value = "创建人")
	@Size(max = 32, message = "创建人最大长度要小于 32")
	private Long createUser;

	/**
	 * 逻辑删除
	 */
	@ApiModelProperty("是否已删除")
	@TableField(value = "is_deleted")
	private Integer isDeleted;

	@Override
	public boolean equals(Object that) {
		if (this == that) {
			return true;
		}
		if (that == null) {
			return false;
		}
		if (getClass() != that.getClass()) {
			return false;
		}
		SysSubItemConfigEntity other = (SysSubItemConfigEntity) that;
		return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
			&& (this.getTenantId() == null ? other.getTenantId() == null : this.getTenantId().equals(other.getTenantId()))
			&& (this.getItemId() == null ? other.getItemId() == null : this.getItemId().equals(other.getItemId()))
			&& (this.getSubItemLabel() == null ? other.getSubItemLabel() == null : this.getSubItemLabel().equals(other.getSubItemLabel()))
			&& (this.getSubItemValue() == null ? other.getSubItemValue() == null : this.getSubItemValue().equals(other.getSubItemValue()))
			&& (this.getSort() == null ? other.getSort() == null : this.getSort().equals(other.getSort()))
			&& (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
			&& (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
			&& (this.getUpdateUser() == null ? other.getUpdateUser() == null : this.getUpdateUser().equals(other.getUpdateUser()))
			&& (this.getCreateUser() == null ? other.getCreateUser() == null : this.getCreateUser().equals(other.getCreateUser()))
			&& (this.getIsDeleted() == null ? other.getIsDeleted() == null : this.getIsDeleted().equals(other.getIsDeleted()));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
		result = prime * result + ((getTenantId() == null) ? 0 : getTenantId().hashCode());
		result = prime * result + ((getItemId() == null) ? 0 : getItemId().hashCode());
		result = prime * result + ((getSubItemLabel() == null) ? 0 : getSubItemLabel().hashCode());
		result = prime * result + ((getSubItemValue() == null) ? 0 : getSubItemValue().hashCode());
		result = prime * result + ((getSort() == null) ? 0 : getSort().hashCode());
		result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
		result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
		result = prime * result + ((getUpdateUser() == null) ? 0 : getUpdateUser().hashCode());
		result = prime * result + ((getCreateUser() == null) ? 0 : getCreateUser().hashCode());
		result = prime * result + ((getIsDeleted() == null) ? 0 : getIsDeleted().hashCode());
		return result;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getClass().getSimpleName());
		sb.append(" [");
		sb.append("Hash = ").append(hashCode());
		sb.append(", id=").append(id);
		sb.append(", tenantId=").append(tenantId);
		sb.append(", itemId=").append(itemId);
		sb.append(", subItemLabel=").append(subItemLabel);
		sb.append(", subItemValue=").append(subItemValue);
		sb.append(", sort=").append(sort);
		sb.append(", createTime=").append(createTime);
		sb.append(", updateTime=").append(updateTime);
		sb.append(", updateUser=").append(updateUser);
		sb.append(", createUser=").append(createUser);
		sb.append(", isDeleted=").append(isDeleted);
		sb.append(", serialVersionUID=").append(serialVersionUID);
		sb.append("]");
		return sb.toString();
	}
}
