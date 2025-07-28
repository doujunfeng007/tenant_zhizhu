package com.minigod.zero.bpmn.module.common.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.minigod.zero.core.mp.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 附件上传表 实体类
 *
 * @author 掌上智珠
 * @since 2024-03-13
 */
@Data
@TableName("common_file_upload_info")
@ApiModel(value = "FileUploadInfo对象", description = "附件上传表")
public class FileUploadInfoEntity extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 业务类型
	 */
	@TableField(value = "business_type")
	@ApiModelProperty(value="业务类型")
	private Integer businessType;

	/**
	 * 业务id
	 */
	@TableField(value = "business_id")
	@ApiModelProperty(value="业务id")
	private Long businessId;

	/**
	 * 文件名称
	 */
	@TableField(value = "file_name")
	@ApiModelProperty(value="文件名称")
	private String fileName;

	/**
	 * 文件url
	 */
	@TableField(value = "file_url")
	@ApiModelProperty(value="文件url")
	private String fileUrl;

	/**
	 * ossId
	 */
	@TableField(value = "oss_id")
	@ApiModelProperty(value="ossId")
	private String ossId;

	/**
	 * 文件类型
	 */
	@TableField(value = "file_type")
	@ApiModelProperty(value="文件类型")
	private Integer fileType;


}
