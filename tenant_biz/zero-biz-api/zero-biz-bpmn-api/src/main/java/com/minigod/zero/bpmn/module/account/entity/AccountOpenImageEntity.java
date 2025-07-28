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
@TableName("customer_account_open_image")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "AccountOpenImage对象", description = "")
public class AccountOpenImageEntity extends TenantEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 预约流水号
	 */
	@ApiModelProperty(value = "预约流水号")
	private String applicationId;
	/**
	 * 文件名
	 */
	@ApiModelProperty(value = "文件名")
	private String fileName;
	/**
	 * 存储文件名
	 */
	@ApiModelProperty(value = "存储文件名")
	private String fileStorageName;
	/**
	 * 文件拓展名
	 */
	@ApiModelProperty(value = "文件拓展名")
	private String extName;
	/**
	 * 图片位置[0=未知 1=身份证正面照坐标 2=身份证反面照片坐标 3=指定动作1照坐标 4=指定动作2照坐标 5=指定动作3照坐标 6=电子签名照坐标 9=活体]
	 */
	@ApiModelProperty(value = "图片位置[0=未知 1=身份证正面照坐标 2=身份证反面照片坐标 3=指定动作1照坐标 4=指定动作2照坐标 5=指定动作3照坐标 6=电子签名照坐标 9=活体]")
	private Integer imageLocation;
	/**
	 * 图片位置类型[0=未知 1=身份证正面照 2=身份证反面照片 3=左手竖起1个指头 4=右手竖起1个指头 5=左手竖起2个指头 6=右手竖起2个指头 7=左手竖起3个指头 8=右手竖起3个指头 9=正面照 10=电子签名照]
	 */
	@ApiModelProperty(value = "图片位置类型[0=未知 1=身份证正面照 2=身份证反面照片 3=左手竖起1个指头 4=右手竖起1个指头 5=左手竖起2个指头 6=右手竖起2个指头 7=左手竖起3个指头 8=右手竖起3个指头 9=正面照 10=电子签名照]")
	private Integer imageLocationType;
	/**
	 * 指定存储点路径
	 */
	@ApiModelProperty(value = "指定存储点路径")
	private String storagePath;
	/**
	 * 备注
	 */
	@ApiModelProperty(value = "备注")
	private String remark;

}
