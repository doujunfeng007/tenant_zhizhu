package com.minigod.zero.cust.apivo.req;

import com.minigod.zero.core.mp.support.Query;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: chen
 * @Project: zero
 * @Pcakage: com.minigod.zero.cust.apivo.req.CustInfoQueryReq
 * @Date: 2023年03月14日 19:26
 * @Description:
 */

@Data
public class CustInfoQueryReq extends Query implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "注册时间(开始)")
	private String startTime;

	@ApiModelProperty(value = "注册时间(结束)")
	private String endTime;

	@ApiModelProperty(value = "用户号")
	private Long custId;

	@ApiModelProperty(value = "恒生客户号")
	private Integer tradeAccount;

	@ApiModelProperty(value = "手机号")
	private String phoneNumber;

	@ApiModelProperty(value = "邮箱")
	private String email;

	@ApiModelProperty(value = "客户类型：0-游客 1-普通个户 2-认证投顾 3-官方账号 4-公司授权人 5-存量ESOP客户")
	private Integer custType;

	@ApiModelProperty(value = "昵称")
	private String nickName;

	@ApiModelProperty(value = "注册来源")
	private Integer regSourceType;

	@ApiModelProperty(value = "注册渠道")
	private Integer regChannel;

}
