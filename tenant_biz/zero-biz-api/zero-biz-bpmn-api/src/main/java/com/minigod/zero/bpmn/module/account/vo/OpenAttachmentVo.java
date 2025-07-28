package com.minigod.zero.bpmn.module.account.vo;

import lombok.Data;

import java.util.Date;

/**
 * 开户附件上传
 *
 * @author eric
 * @since 2024-06-12 18:09:00
 */
@Data
public class OpenAttachmentVo {
	private Long attachmentId;
	private String attachmentUrl;
	private String attachmentName;
	private String location;
	private String type;
	private Date createTime;
	private Date updateTime;
}
