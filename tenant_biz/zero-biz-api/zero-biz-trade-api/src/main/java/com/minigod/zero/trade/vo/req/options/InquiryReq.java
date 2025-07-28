package com.minigod.zero.trade.vo.req.options;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author chen
 * @ClassName InquiryReq.java
 * @Description TODO
 * @createTime 2024年08月27日 11:01:00
 */
@Data
public class InquiryReq {

	@NotBlank(message = "代码不能为空")
	private String stockCode;

	@NotBlank(message = "到期日不能为空")
	private String expiration;

	@ApiModelProperty(value = "周(W)期权，季(Q)期权标识")
	private String flag;
}
