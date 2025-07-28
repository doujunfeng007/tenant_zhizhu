package com.minigod.zero.bpmn.module.deposit.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.minigod.zero.core.tenant.mp.TenantEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 银行卡审核凭证表
 */
@ApiModel(description="银行卡审核凭证表")
@Data
@TableName(value = "client_bank_card_image")
public class BankCardImageEntity extends TenantEntity implements Serializable {
	/**
	 * 自增ID
	 */
	@TableId(value = "id", type = IdType.ASSIGN_ID)
	@ApiModelProperty(value = "自增ID")
	@NotNull(message = "自增ID不能为null")
	private Long id;

	/**
	 * 预约流水号
	 */
	@TableField(value = "application_id")
	@ApiModelProperty(value = "预约流水号")
	@Size(max = 32, message = "预约流水号最大长度要小于 32")
	@NotBlank(message = "预约流水号不能为空")
	private String applicationId;

	/**
	 * 凭证类型[0-银行卡 1-银行凭证]
	 */
	@TableField(value = "image_type")
	@ApiModelProperty(value = "凭证类型[0-银行卡 1-银行凭证]")
	@NotNull(message = "凭证类型[0-银行卡 1-银行凭证]不能为null")
	private Integer imageType;

	/**
	 * 文件名
	 */
	@TableField(value = "file_name")
	@ApiModelProperty(value = "文件名")
	@Size(max = 255, message = "文件名最大长度要小于 255")
	@NotBlank(message = "文件名不能为空")
	private String fileName;

	/**
	 * 存储文件名
	 */
	@TableField(value = "file_storage_name")
	@ApiModelProperty(value = "存储文件名")
	@Size(max = 255, message = "存储文件名最大长度要小于 255")
	@NotBlank(message = "存储文件名不能为空")
	private String fileStorageName;

	/**
	 * 文件拓展名
	 */
	@TableField(value = "ext_name")
	@ApiModelProperty(value = "文件拓展名")
	@Size(max = 32, message = "文件拓展名最大长度要小于 32")
	@NotBlank(message = "文件拓展名不能为空")
	private String extName;

	/**
	 * 指定存储点路径
	 */
	@TableField(value = "storage_path")
	@ApiModelProperty(value = "指定存储点路径")
	@Size(max = 500, message = "指定存储点路径最大长度要小于 500")
	@NotBlank(message = "指定存储点路径不能为空")
	private String storagePath;

	/**
	 * 备注
	 */
	@TableField(value = "remark")
	@ApiModelProperty(value = "备注")
	@Size(max = 2000, message = "备注最大长度要小于 2000")
	private String remark;


	private static final long serialVersionUID = 1L;
}
