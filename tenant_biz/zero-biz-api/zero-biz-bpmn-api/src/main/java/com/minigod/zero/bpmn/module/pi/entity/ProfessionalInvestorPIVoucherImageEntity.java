package com.minigod.zero.bpmn.module.pi.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.minigod.zero.core.tenant.mp.TenantEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 专业投资者FI申请凭证图片 实体类
 *
 * @author eric
 * @since 2024-05-07 14:49:17
 */
@Data
@TableName("professional_investor_pi_voucher_image")
@ApiModel(value = "ProfessionalInvestorPIVoucherImage对象", description = "专业投资者FI申请凭证图片")
public class ProfessionalInvestorPIVoucherImageEntity extends TenantEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 流水号
	 */
	@ApiModelProperty(value = "流水号")
	private String applicationId;

	/**
	 * 客户ID
	 */
	@ApiModelProperty(value = "客户ID")
	private Long clientId;

	/**
	 * 凭证类型[0-客户凭证 1-银行凭证]
	 */
	@TableField(value = "image_type")
	@ApiModelProperty(value = "凭证类型[0-资产凭证 1-补充证明]")
	@NotNull(message = "凭证类型[0-资产凭证 1-补充证明]不能为null")
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
}
