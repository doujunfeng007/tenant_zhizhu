package com.minigod.zero.platform.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 未读消息
 * 包括：消息分组名称、图标、未读数量（超过99的返回99+）、跳转标识
 * @author rose
 */
@Data
public class MessageUnreadVO implements Serializable {
    private static final long serialVersionUID = 5306658783512625108L;
    private Integer messageGroup;
    private String messageIcon;
    private String unReadMessageCount;
    private String targetFlag;
    private String messageGroupName;
	private String messagePreview;
	private Long ts;
//    private Integer count;
//    private Integer page;
}
