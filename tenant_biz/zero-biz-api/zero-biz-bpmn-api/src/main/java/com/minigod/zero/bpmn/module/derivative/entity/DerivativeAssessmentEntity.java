package com.minigod.zero.bpmn.module.derivative.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 衍生品及结构性投资产品的认识评估
 *
 * @TableName derivative_knowledge_assessment
 */
@Data
@TableName("derivative_knowledge_assessment")
@ApiModel(value = "DerivativeAssessment衍生品及结构性投资产品的认识评估", description = "衍生品及结构性投资产品的认识评估")
public class DerivativeAssessmentEntity implements Serializable {
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

	@TableField(value = "cust_id")
	@ApiModelProperty(value = "用户号")
	private Long custId;

	@TableField(value = "tenant_id")
	@ApiModelProperty(value = "租户ID")
	private String tenantId;

	@TableField(value = "option_item")
	@ApiModelProperty(value = "选项项目")
	private String optionItem;

	@TableField(value = "option_value")
	@ApiModelProperty(value = "选项值")
	private String optionValue;

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
		DerivativeAssessmentEntity other = (DerivativeAssessmentEntity) that;
		return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
			&& (this.getCustId() == null ? other.getCustId() == null : this.getCustId().equals(other.getCustId()))
			&& (this.getTenantId() == null ? other.getTenantId() == null : this.getTenantId().equals(other.getTenantId()))
			&& (this.getOptionItem() == null ? other.getOptionItem() == null : this.getOptionItem().equals(other.getOptionItem()))
			&& (this.getOptionValue() == null ? other.getOptionValue() == null : this.getOptionValue().equals(other.getOptionValue()))
			&& (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
			&& (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
		result = prime * result + ((getCustId() == null) ? 0 : getCustId().hashCode());
		result = prime * result + ((getTenantId() == null) ? 0 : getTenantId().hashCode());
		result = prime * result + ((getOptionItem() == null) ? 0 : getOptionItem().hashCode());
		result = prime * result + ((getOptionValue() == null) ? 0 : getOptionValue().hashCode());
		result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
		result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
		return result;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getClass().getSimpleName());
		sb.append(" [");
		sb.append("Hash = ").append(hashCode());
		sb.append(", id=").append(id);
		sb.append(", custId=").append(custId);
		sb.append(", tenantId=").append(tenantId);
		sb.append(", optionItem=").append(optionItem);
		sb.append(", optionValue=").append(optionValue);
		sb.append(", createTime=").append(createTime);
		sb.append(", updateTime=").append(updateTime);
		sb.append(", serialVersionUID=").append(serialVersionUID);
		sb.append("]");
		return sb.toString();
	}
}
