package com.minigod.zero.biz.common.vo;

import lombok.Data;

import javax.validation.Valid;
import java.io.Serializable;

@Data
public class CommonReqVO<T> implements Serializable {
	private static final long serialVersionUID = -2260388125919493487L;

	@Valid
	private T params;

}
