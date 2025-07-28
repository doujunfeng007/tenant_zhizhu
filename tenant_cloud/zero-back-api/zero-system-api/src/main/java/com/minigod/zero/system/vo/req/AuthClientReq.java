package com.minigod.zero.system.vo.req;

import com.minigod.zero.system.entity.AuthClient;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AuthClientReq extends AuthClient {

	private static final long serialVersionUID = 1L;

	/**
	 * 过滤智珠中台应用
	 */
	@ApiModelProperty(value = "过滤智珠中台应用", example = "空/true 都会过滤")
	private Boolean filterZhiZhu;
}
