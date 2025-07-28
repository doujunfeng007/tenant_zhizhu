package com.minigod.zero.bpmn.module.derivative.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 衍生品评估用户记录
 *
 * @author eric
 * @since 2024-06-26 10:06:01
 */
@Data
public class DerivativeUserRecordVO {
	@ApiModelProperty(value = "主键")
	private Long id;

	@ApiModelProperty(value = "用户ID")
	private Long custId;

	@ApiModelProperty(value = "租户ID")
	private String tenantId;

	@ApiModelProperty(value = "用户中文名")
	private String clientName;

	@ApiModelProperty(value = "用户中文名拼音")
	private String clientNameSpell;

	@ApiModelProperty(value = "英文名字")
	private String givenName;

	@ApiModelProperty(value = "英文姓氏")
	private String familyName;

	@ApiModelProperty(value = "证件号码")
	private String idNo;

	@ApiModelProperty(value = "证件类型：0=未知 1=大陆居民身份证 2=香港居民身份证 3=护照 4=香港临时身份证 5=其他")
	private String idKind;

	@ApiModelProperty(value = "性别")
	private String sex;

	@ApiModelProperty(value = "手机号区号")
	private String phoneArea;

	@ApiModelProperty(value = "手机号")
	private String phoneNumber;

	@ApiModelProperty(value = "邮箱")
	private String email;

	@ApiModelProperty(value = "交易账号")
	private String clientId;

	@ApiModelProperty(value = "资金账号")
	private String fundAccount;

	@ApiModelProperty(value = "开户渠道")
	private String accessWay;

	@ApiModelProperty(value = "评估日期")
	private String createTime;
}
