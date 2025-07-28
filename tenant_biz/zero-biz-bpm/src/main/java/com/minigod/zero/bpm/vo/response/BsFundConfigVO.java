package com.minigod.zero.bpm.vo.response;

import lombok.Data;

import java.util.Date;

@Data
public class BsFundConfigVO {
	private Long id;
	private String key;
	private String value;
	private Integer status;
	private String name;
	private String remark;
	private Integer busType;
	private String bank;
	private Date createTime;
	private String createUser;
	private Date updateTime;
	private String updateUser;
}
