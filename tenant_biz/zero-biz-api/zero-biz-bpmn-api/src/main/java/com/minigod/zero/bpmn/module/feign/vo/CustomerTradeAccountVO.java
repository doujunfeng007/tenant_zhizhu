package com.minigod.zero.bpmn.module.feign.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author chen
 * @ClassName CustomerTradeAccountVO.java
 * @Description TODO
 * @createTime 2024年12月18日 17:29:00
 */
@Data
public class CustomerTradeAccountVO implements java.io.Serializable{

	private static final long serialVersionUID = 1L;

	private Long id;

	@ApiModelProperty("理财账户id")
	private String accountId;

	@ApiModelProperty("交易账号(在柜台体现为资金账号)")
	private String tradeAccount;

	@ApiModelProperty("业务类型")
	private String businessType;

	@ApiModelProperty("账户类型")
	private String accountType;

	@ApiModelProperty("操作类型")
	private Integer operType;

	@ApiModelProperty("账户状态")
	private Integer accountStatus;

	@ApiModelProperty("关联柜台交易账号")
	private String reletionTradeAccount;

	@ApiModelProperty("是否是当前选中账号")
	private Integer isCurrent;

	@ApiModelProperty("柜台类型")
	private Integer counterType;

	@ApiModelProperty("创建时间")
	private Date createTime;

	@ApiModelProperty("更新时间")
	private Date updateTime;


}
