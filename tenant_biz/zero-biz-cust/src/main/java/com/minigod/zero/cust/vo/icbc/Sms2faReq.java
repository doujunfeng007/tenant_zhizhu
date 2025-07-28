package com.minigod.zero.cust.vo.icbc;

import lombok.Data;

import java.io.Serializable;

/**
 * @author:yanghu.luo
 * @create: 2023-03-07 16:44
 * @Description: 工银短信发送对象
 */
@Data
public class Sms2faReq implements Serializable {
	private static final long serialVersionUID = 1L;
	private String app_name;
	private String locale;
	private String phone;
	private String need_report;
	private String content_type;
	private String sms_content;

	/**
	 * 接口5发送短信验证码时需要传入
	 */
	private String ci_no;
	private String medium_id;
	private String medium_no;
}
