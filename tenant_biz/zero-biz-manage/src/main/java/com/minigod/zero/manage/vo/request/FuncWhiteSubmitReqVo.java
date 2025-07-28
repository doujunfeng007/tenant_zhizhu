package com.minigod.zero.manage.vo.request;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 掌上智珠
 * @since 2023/9/13 15:54
 */
@Data
public class FuncWhiteSubmitReqVo implements Serializable {
	private String custId;

	private String funcId;
}
