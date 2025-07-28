package com.minigod.zero.cust.apivo.resp;

import com.minigod.zero.cust.apivo.QuotLevelInfoVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 返回客户端权限
 */
@Data
public class QuotLevelInfoResp implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 港股权限
	 */
	@ApiModelProperty("港股权限")
	private QuotLevelInfoVO hk;

	/**
	 * 美股权限
	 */
	@ApiModelProperty("美股权限")
	private QuotLevelInfoVO us;

	/**
	 * A股权限
	 */
	@ApiModelProperty("A股权限")
	private QuotLevelInfoVO cn;

	/**
	 * 应用ID
	 */
	@ApiModelProperty("应用ID")
	private String clientId;

	/**
	 * 是否美股专业标识
	 */
	@ApiModelProperty("是否美股专业标识")
	private Boolean isInvestor;
}
