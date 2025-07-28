package com.minigod.zero.bpmn.module.feign.vo;

import lombok.Data;

/**
 * @ClassName: com.minigod.zero.bpmn.module.feign.vo.CustomerAccountDetailVO
 * @Description:
 * @Author: linggr
 * @CreateDate: 2024/9/2 16:15
 * @Version: 1.0
 */
@Data
public class CustomerAccountDetailVO {
	private Long custId;

	/**
	 * 客户中文名
	 */
	private String clientName;

	/**
	 * 英文名字
	 */
	private String givenNameSpell;

	/**
	 * 账户类型，0个人户，1机构户
	 */
	private Integer accountType;


	/**
	 * 手机号
	 */
	private String phoneNumber;

	private String phoneArea;

	/**
	 * 电子邮箱
	 */
	private String email;

}
