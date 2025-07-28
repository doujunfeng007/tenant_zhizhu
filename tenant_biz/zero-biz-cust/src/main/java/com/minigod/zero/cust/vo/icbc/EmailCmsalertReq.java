package com.minigod.zero.cust.vo.icbc;

import lombok.Data;

import java.io.Serializable;

/**
 * @author:yanghu.luo
 * @create: 2023-03-07 16:49
 * @Description: 工银邮件发送请求
 */
@Data
public class EmailCmsalertReq implements Serializable {
	private static final long serialVersionUID = 1L;
	private String app_name;
	private String locale;
	private String email_to;
	private String subject;
	private String content;
}
