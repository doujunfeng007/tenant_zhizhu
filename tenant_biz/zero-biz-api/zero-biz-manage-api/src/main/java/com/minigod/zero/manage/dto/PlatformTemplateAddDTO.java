package com.minigod.zero.manage.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 新增修改短信模板VO
 * @author Zhe.Xiao
 */
@Data
public class PlatformTemplateAddDTO {
	@ApiModelProperty("模板ID")
	private Long id;

	@ApiModelProperty("业务类型ID")
	private Long busId;

	@ApiModelProperty("业务类型 1-邮件 2-短信 3-系统通知 4-消息通知")
	private Integer busType;

	@ApiModelProperty("模板类型父类ID")
	private Long parentId;

	@ApiModelProperty("模板类型名称-简体中文")
	private String tempName;

	@ApiModelProperty("模板类型名称-繁体中文")
	private String tempNameHant;

	@ApiModelProperty("模板类型名称-英文")
	private String tempNameEn;

	@ApiModelProperty("短信类型")
	private Integer msgType;

	@ApiModelProperty("发送方式(0-自动 1-手动)")
	private Integer sendWay;

	@ApiModelProperty("内容-简体中文")
	private String content;

	@ApiModelProperty("内容-繁体中文")
	private String contentHant;

	@ApiModelProperty("内容-简体中文")
	private String contentEn;

	@ApiModelProperty("申请说明")
	private String applyExplain;

	@ApiModelProperty("跳转url")
	private String url;

	private String title;

	private String titleEn;

	private String titleHant;
}
