package com.minigod.zero.customer.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/5/7 17:03
 * @description：
 */
@Data
public class ClientAccountVO implements Serializable {
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
	private List<AccountVO> accounts;
}
