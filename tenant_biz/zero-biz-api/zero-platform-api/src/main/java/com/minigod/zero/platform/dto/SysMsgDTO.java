package com.minigod.zero.platform.dto;


import lombok.Data;

import java.io.Serializable;

@Data
public class SysMsgDTO extends PlatformOpenCommonDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long version;

	private Integer count;

	private Integer page;

}
