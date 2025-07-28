package com.minigod.zero.cust.apivo.params;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: chen
 * @Project: zero
 * @Pcakage: com.minigod.zero.cust.apivo.params.Cert
 * @Date: 2023年02月15日 19:38
 * @Description:
 */
@Data
public class Cert implements Serializable {

	private static final long serialVersionUID = -5665564973595707430L;

	@ApiModelProperty(value = "凭证类型 (0-手机,1-邮箱,2-微信,3-微博,4-QQ)")
	private Integer certType;

	@ApiModelProperty(value = "凭证内容(手机号、邮箱、QQ号，微博号、微信号、sunline用户名、OpenID等)")
	private String certCode;
}
