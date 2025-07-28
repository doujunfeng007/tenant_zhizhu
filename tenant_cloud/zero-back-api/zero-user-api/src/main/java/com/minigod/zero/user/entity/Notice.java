
package com.minigod.zero.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.minigod.zero.core.tenant.mp.TenantEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 实体类
 *
 * @author Chill
 */
@Data
@TableName("zero_notice")
@EqualsAndHashCode(callSuper = true)
public class Notice extends TenantEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 标题
	 */
	@ApiModelProperty(value = "标题")
	private String title;

	/**
	 * 通知类型
	 */
	@ApiModelProperty(value = "通知类型")
	private Integer category;

	/**
	 * 发布日期
	 */
	@ApiModelProperty(value = "发布日期")
	private Date releaseTime;

	/**
	 * 内容
	 */
	@ApiModelProperty(value = "内容")
	private String content;


}
