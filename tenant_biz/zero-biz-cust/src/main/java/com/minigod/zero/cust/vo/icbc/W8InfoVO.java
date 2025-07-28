package com.minigod.zero.cust.vo.icbc;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author:yanghu.luo
 * @create: 2023-03-08 16:08
 * @Description: W8信息
 */
@Data
public class W8InfoVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 中文名
	 */
	@ApiModelProperty(value = "中文名")
	private String name;

	/**
	 * 英文名
	 */
	@ApiModelProperty(value = "英文名")
	private String enName;

	/**
	 * 国籍
	 */
	@ApiModelProperty(value = "国籍")
	private String nationality;

	/**
	 * 出生日期 YYYY-MM-DD
	 */
	@ApiModelProperty(value = "出生日期 YYYY-MM-DD")
	private String birthday;

	/**
	 * 邮箱地址
	 */
	@ApiModelProperty(value = "邮箱地址")
	private String email;

	/**
	 * 手机号码
	 */
	@ApiModelProperty(value = "手机号码")
	private String mobile;

	/**
	 * 永居地址
	 */
	@ApiModelProperty(value = "永居地址")
	private W8Addr permAddr;

	/**
	 * 邮件地址
	 */
	@ApiModelProperty(value = "邮件地址")
	private W8Addr mailAddr;
}
