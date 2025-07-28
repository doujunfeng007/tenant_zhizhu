package com.minigod.zero.platform.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class EmailHookMessageDTO implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String event;
	private String apiUser;
	private String category;
	private Integer labelId;
	private String emailId;
	private String maillistTaskId;
	private String mail_list_task_id;
	private String recipient;
	private long timestamp;
	private String token;
	private String signature;
	private String userHeaders;
	private Integer subStat;
	private String subStatDesc;
}
