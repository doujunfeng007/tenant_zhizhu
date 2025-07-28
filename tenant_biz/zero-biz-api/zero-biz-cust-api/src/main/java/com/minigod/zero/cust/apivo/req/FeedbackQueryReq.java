package com.minigod.zero.cust.apivo.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: chen
 * @Project: zero
 * @Pcakage: com.minigod.zero.cust.apivo.req.FeedbackQueryReq
 * @Date: 2023年03月17日 10:44
 * @Description:
 */
@Data
public class FeedbackQueryReq implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "查询条件 称呼/联系方式/昵称/")
	private String condition;

	@ApiModelProperty(value = "用户id")
	private Long custId;
}
