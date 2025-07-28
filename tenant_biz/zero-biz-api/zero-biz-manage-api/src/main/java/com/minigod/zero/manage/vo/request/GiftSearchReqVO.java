package com.minigod.zero.manage.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 礼品搜索请求参数
 *
 * @author eric
 * @since 2024-12-24 09:34:08
 */
@Data
@ApiModel(description = "礼品搜索请求参数")
public class GiftSearchReqVO implements Serializable {
	private static final long serialVersionUID = -2260388125919493487L;
	/**
	 * 渠道ID
	 */
	@ApiModelProperty(value = "渠道ID")
	private String channelId;
	/**
	 * 渠道名称
	 */
	@ApiModelProperty(value = "渠道名称")
	private String channelName;
	/**
	 * 公司名称(实际渠道名称)
	 */
	@ApiModelProperty(value = "公司名称(实际渠道名称)")
	private String companyName;
	/**
	 * 渠道礼包密码
	 */
	@ApiModelProperty(value = "渠道礼包密码")
	private String channelPwd;
	/**
	 * 记录状态 0有效 1失效
	 */
	@ApiModelProperty(value = "记录状态 0有效 1失效")
	private Integer status;
	/**
	 * 渠道礼包名称
	 */
	@ApiModelProperty(value = "渠道礼包名称")
	private String giftName;
	/**
	 * 礼包数量
	 */
	@ApiModelProperty(value = "礼包数量")
	private Integer count;
}
