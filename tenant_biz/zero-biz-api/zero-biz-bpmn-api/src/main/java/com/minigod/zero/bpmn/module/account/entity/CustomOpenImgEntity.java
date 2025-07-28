package com.minigod.zero.bpmn.module.account.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.minigod.zero.core.mp.base.AppEntity;
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
@TableName("custom_open_img")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "CustomOpenImg对象", description = "")
public class CustomOpenImgEntity extends AppEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 用户 ID
	 */
	@ApiModelProperty(value = "用户 ID")
	private Long userId;
	/**
	 * 图片位置[0=未知 1=身份证正面照坐标 2=身份证反面照片坐标 3=指定动作1照坐标 4=指定动作2照坐标 5=指定动作3照坐标 6=电子签名照坐标]
	 */
	@ApiModelProperty(value = "图片位置[0=未知 1=身份证正面照坐标 2=身份证反面照片坐标 3=指定动作1照坐标 4=指定动作2照坐标 5=指定动作3照坐标 6=电子签名照坐标]")
	private String locationKey;
	/**
	 * 图片类型[0=未知 1=身份证正面照 2=身份证反面照片 3=左手竖起1个指头 4=右手竖起1个指头 5=左手竖起2个指头 6=右手竖起2个指头 7=左手竖起3个指头 8=右手竖起3个指头 9=正面照 10=电子签名照]
	 */
	@ApiModelProperty(value = "图片类型[0=未知 1=身份证正面照 2=身份证反面照片 3=左手竖起1个指头 4=右手竖起1个指头 5=左手竖起2个指头 6=右手竖起2个指头 7=左手竖起3个指头 8=右手竖起3个指头 9=正面照 10=电子签名照]")
	private String locationType;
	/**
	 *  链接
	 */
	@ApiModelProperty(value = " 链接")
	private String url;
	/**
	 * 标识错误图片，1:图片错误
	 */
	@ApiModelProperty(value = "标识错误图片，1:图片错误")
	private Boolean isError;

	@ApiModelProperty(value = "租户ID")
	private String tenantId;

}
