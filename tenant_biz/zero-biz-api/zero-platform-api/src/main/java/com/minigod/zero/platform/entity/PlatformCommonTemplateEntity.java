package com.minigod.zero.platform.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.minigod.zero.core.tenant.mp.TenantEntity;
import com.minigod.zero.platform.enums.MsgType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 模块公共信息表
 *
 * @author 掌上智珠
 */
@Data
@TableName("platform_common_template")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "模块公共信息表", description = "模块公共信息表")
public class PlatformCommonTemplateEntity extends TenantEntity {

	private static final long serialVersionUID = 1L;
	/** 模板类型编码 */
	private Integer tempCode;

	/** 短信模板(简体) */
	private String tempCodeHans;

	/** 短信模板(繁体) */
	private String tempCodeHant;

	/** 短信模板(英文) */
	private String tempCodeEn;

	/** 短信模板参数 */
	private String tempParam;

	/** 标题 */
	private String title;

	/** 标题-繁体 */
	private String titleHant;

	/** 标题-英语 */
	private String titleEn;

	/** 内容 */
	private String content;

	/** 内容-繁体 */
	private String contentHant;

	/** 内容-英语 */
	private String contentEn;

	/** 申请说明 */
	private String applyExplain;

	/** 短信类型
	 *
	 * {@link MsgType}
	 * */
	private Integer msgType = 0;

	/** 推送跳转链接 */
	private String url;
	/**
	 * msgType =1表示短信渠道，1.腾讯云
	 * msgType =2表示邮件渠道，1.sendcloud
	 * msgType =3表示推送渠道，1.极光'
	 */
	private Integer channel;
	/**
	 * 创建人名称
	 */
	private String createUserName;
}
