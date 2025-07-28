package com.minigod.zero.cust.vo.icbc;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author:yanghu.luo
 * @create: 2023-03-07 18:03
 * @Description: 客户信息
 */
@Data
public class ClientAccount implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * clientid 客户ID
	 */
	@ApiModelProperty(value = "客户ID")
	private  Long clientid;

	/**
	 * clientname mediumNo工银同步
	 */
	@ApiModelProperty(value = "mediumNo工银同步")
	private  String clientname;

	/**
	 * 客户中文名
	 */
	@ApiModelProperty(value = "客户中文名")
	private  String chinesename;

	/**
	 * 客户英文名
	 */
	@ApiModelProperty(value = "客户英文名")
	private  String englishname;

	/**
	 * cust_type 客户类型
	 */
	@ApiModelProperty(value = "客户类型")
	private  Integer type;

	/**
	 * status 客户状态
	 */
	@ApiModelProperty(value = "客户状态")
	private  Integer status;

	/**
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间")
	private Date createtime;

	/**
	 * 客户账户信息
	 */
	@ApiModelProperty(value = "客户账户信息")
	private List<Account> accounts;
}
