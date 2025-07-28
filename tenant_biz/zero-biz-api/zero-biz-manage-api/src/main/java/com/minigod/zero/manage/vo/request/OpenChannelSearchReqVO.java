package com.minigod.zero.manage.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 开放渠道礼包入口查询参数
 *
 * @author eric
 * @since 2024-12-24 09:47:08
 */
@Data
@ApiModel(description = "开放渠道礼包入口查询参数")
public class OpenChannelSearchReqVO implements Serializable {
	private static final long serialVersionUID = -2260388125919493487L;
	/**
	 * 用户ID
	 */
	@ApiModelProperty(value = "用户ID")
	private Integer userId;
	/**
	 * 手机号
	 */
	@ApiModelProperty(value = "手机号")
	private String cellPhone;
	/**
	 * 昵称
	 */
	@ApiModelProperty(value = "昵称")
	private String nickName;
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
}
