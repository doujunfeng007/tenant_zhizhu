package com.minigod.zero.platform.common;

import lombok.Data;

import java.io.Serializable;

@Data
public class CommonExt implements Serializable{

	private static final long serialVersionUID = 1L;

	private String id;
	private String type;
	private String url;

	private String nickName;
	private String tepNickName;
	private String freNickName;
	private String content;
	private Boolean status;

}
