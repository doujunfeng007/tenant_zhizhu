package com.minigod.zero.cust.apivo.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: chen
 * @Project: zero
 * @Pcakage: com.minigod.zero.cust.apivo.req.FuncWhiteListQueryReq
 * @Date: 2023年03月17日 14:40
 * @Description:
 */
@Data
public class FuncWhiteListQueryReq implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "用户id")
	private Long custId;


	@ApiModelProperty(value = "功能id")
	private String funcId;

	@ApiModelProperty(value = "状态")
	private Integer status;


}
