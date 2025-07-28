package com.minigod.zero.customer.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: chen
 * @Project: zero
 * @Pcakage: com.minigod.zero.cust.apivo.req.UserSwitchVO
 * @Date: 2023年05月10日 11:23
 * @Description:
 */
@Data
public class UserSwitchDTO implements Serializable {

	private static final long serialVersionUID = -1L;


	@ApiModelProperty(value = "隐私开关Y打开,N关闭(1 资讯推送, 2 股价提醒  3 新股提醒,4 自选智能排序) 传Y 或者N就行",required = true)
	private	String  value;

	@ApiModelProperty(value = "隐私开关位置标识(1 资讯推送, 2 股价提醒  3 新股提醒 ，4 只能排序) 传 1或者2或者或者4",required = true)
	private Integer index;
}
