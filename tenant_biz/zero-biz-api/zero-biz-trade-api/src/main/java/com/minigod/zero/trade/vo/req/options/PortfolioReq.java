package com.minigod.zero.trade.vo.req.options;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author chen
 * @ClassName PortfolioReq.java
 * @Description TODO
 * @createTime 2024年08月28日 14:16:00
 */
@Data
public class PortfolioReq {

	@ApiModelProperty(value = "期权代码")
	private String optionsCode;
}
