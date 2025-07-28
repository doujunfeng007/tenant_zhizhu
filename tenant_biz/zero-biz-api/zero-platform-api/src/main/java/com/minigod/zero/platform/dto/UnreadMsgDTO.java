/*
 * FileName: UnreadMsgVO.java
 * Copyright: Copyright 2014-12-4 Sunline Tech. Co. Ltd.All right reserved.
 * Description:
 *
 */
package com.minigod.zero.platform.dto;

import lombok.Data;

import java.io.Serializable;

/**
 *
 * @description 这里描述类的用处
 *
 * @author Sunline
 * @date 2015-4-11 下午4:00:22
 * @version v1.0
 */
@Data
public class UnreadMsgDTO extends PlatformOpenCommonDTO implements Serializable {

	private static final long serialVersionUID = 3464825410615914588L;

	private Long locateVersion;
	private Integer count;
	private Integer messageGroup;
	private Integer []messageGroups;
	private Long version;//用于兼容旧版拉取投资圈的形式
	private Integer msgId;
	private Integer page;
	private String msgType;

}
