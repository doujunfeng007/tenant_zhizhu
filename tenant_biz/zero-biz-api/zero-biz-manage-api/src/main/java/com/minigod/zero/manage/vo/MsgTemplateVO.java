package com.minigod.zero.manage.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class MsgTemplateVO implements Serializable {
	private static final long serialVersionUID = -2260388125919493487L;

	private Long id;

	private String title;

	private String titleHant;

	private String titleEn;

	private String content;

	private String contentHant;

	private String contentEn;

	private Integer tempCode;

	private String tempCodeHans;

	private String tempCodeHant;

	private String tempCodeEn;

	private String channelName;

	private String statusName;

	private String createUserName;

	private Date createTime;

}
