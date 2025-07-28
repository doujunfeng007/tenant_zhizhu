package com.minigod.zero.manage.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.minigod.zero.core.tenant.mp.TenantEntity;
import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

import lombok.EqualsAndHashCode;

/**
 * 公告信息表 实体类
 *
 * @author 掌上智珠
 * @since 2023-03-18
 */
@Data
@TableName("oms_board_info")
@ApiModel(value = "BoardInfo对象", description = "公告信息表")
@EqualsAndHashCode(callSuper = true)
public class BoardInfoEntity extends TenantEntity {
	/**
	 * 公告位
	 */
	@ApiModelProperty(value = "公告位")
	private String positionCode;
	/**
	 * 语言类型  0: 简体中文, 2: 英文, 3: 繁体中文, 4: 越南语
	 */
	@ApiModelProperty(value = "语言类型  0: 简体中文, 2: 英文, 3: 繁体中文, 4: 越南语")
	private Integer languageType;
	/**
	 * 1休市广告条
	 */
	@ApiModelProperty(value = "1休市广告条")
	private Integer boardType;
	/**
	 * 开始时间
	 */
	@ApiModelProperty(value = "开始时间")
	private Date startTime;
	/**
	 * 结束时间
	 */
	@ApiModelProperty(value = "结束时间")
	private Date endTime;
	/**
	 * 优先级
	 */
	@ApiModelProperty(value = "优先级")
	private Integer priority;
	/**
	 * 内容
	 */
	@ApiModelProperty(value = "内容")
	private String content;

	/**
	 * 内容-繁体
	 */
	@ApiModelProperty(value = "内容-繁体")
	private String contentHant;

	/**
	 * 内容-英文
	 */
	@ApiModelProperty(value = "内容-英文")
	private String contentEn;
	/**
	 * 链接
	 */
	@ApiModelProperty(value = "链接")
	private String link;
	/**
	 * pc公告地址
	 */
	@ApiModelProperty(value = "pc公告地址")
	private String pcLink;
}
