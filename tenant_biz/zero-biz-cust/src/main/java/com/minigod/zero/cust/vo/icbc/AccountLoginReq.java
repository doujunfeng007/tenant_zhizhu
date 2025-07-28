package com.minigod.zero.cust.vo.icbc;

import lombok.Data;

import java.io.Serializable;

/**
 * @author:yanghu.luo
 * @create: 2023-03-07 14:33
 * @Description: account_login_req
 */
@Data
public class AccountLoginReq implements Serializable {
	private static final long serialVersionUID = 1L;
	private String account;
	private String login_token;
	private String rule;
	private String change_rule;
	private String device_type;
	private String device_push_token;
	private String device_uuid;
	private String app_version;
	private String auth_type;
	private String area_code;
	private String login_timestamp;
	private String serial_no;
	private String ip_addr;
	private String os_ver;
}
