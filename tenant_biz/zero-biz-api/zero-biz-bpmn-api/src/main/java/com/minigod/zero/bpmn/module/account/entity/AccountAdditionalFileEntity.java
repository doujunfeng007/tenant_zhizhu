package com.minigod.zero.bpmn.module.account.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.minigod.zero.core.mp.base.BaseEntity;
import com.minigod.zero.core.tenant.mp.TenantEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import java.time.LocalDateTime;


/**
 *  实体类
 *
 * @author Chill
 */
@Data
@TableName("open_account_additional_file")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "AccountAdditionalFile对象", description = "")
public class AccountAdditionalFileEntity extends TenantEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 预约流水号
	 */
	@ApiModelProperty(value = "预约流水号")
	private String applicationId;
	/**
	 * 关联ID（UUID）
	 */
	@ApiModelProperty(value = "关联ID（UUID）")
	private String additionalId;
	/**
	 * 文件名
	 */
	@ApiModelProperty(value = "文件名")
	private String fileName;
	/**
	 * 文件类型[0-图片 1-视频/音频 2-AML 3=编辑资料凭证]
	 */
	@ApiModelProperty(value = "文件类型[0-图片 1-视频/音频 2-AML 3=编辑资料凭证] ")
	private Integer fileType;
	/**
	 * 存储文件名
	 */
	@ApiModelProperty(value = "存储文件名")
	private String fileStorageName;
	/**
	 * 文件拓展名
	 */
	@ApiModelProperty(value = "文件拓展名")
	private String fileExtName;
	/**
	 * 指定存储点路径
	 */
	@ApiModelProperty(value = "指定存储点路径")
	private String filePath;
	/**
	 *
	 */
	@ApiModelProperty(value = "")
	private Integer type;

}
