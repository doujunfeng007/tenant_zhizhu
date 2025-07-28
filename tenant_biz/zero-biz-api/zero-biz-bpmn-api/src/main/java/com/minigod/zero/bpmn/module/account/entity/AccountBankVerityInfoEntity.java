package com.minigod.zero.bpmn.module.account.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.minigod.zero.core.tenant.mp.TenantEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;


/**
 *  银行卡四要素验证信息表
 *
 * @author Chill
 */
@Data
@TableName("open_account_bank_verity_info")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "AccountBankVerityInfo对象", description = "")
public class AccountBankVerityInfoEntity extends TenantEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 预约流水号
	 */
	@ApiModelProperty(value = "预约流水号")
	private String applicationId;
	/**
	 * 犇犇号
	 */
	@ApiModelProperty(value = "犇犇号")
	private Long userId;
	/**
	 * 用户名称
	 */
	@ApiModelProperty(value = "用户名称")
	private String clientName;
	/**
	 * 手机号码区号
	 */
	@ApiModelProperty(value = "手机号码区号")
	private String phoneArea;
	/**
	 * 手机号码
	 */
	@ApiModelProperty(value = "手机号码")
	private String phoneNumber;
	/**
	 * 身份证号码
	 */
	@ApiModelProperty(value = "身份证号码")
	private String idNo;
	/**
	 * 银行卡号
	 */
	@ApiModelProperty(value = "银行卡号")
	private String bankCard;
	/**
	 * 状态[0-不通过 1-通过]
	 */
	@ApiModelProperty(value = "状态[0-不通过 1-通过]")
	private Integer verifyStatus;
	/**
	 * 验证次数
	 */
	@ApiModelProperty(value = "验证次数")
	private Integer verifyCount;
	/**
	 * 验证时间
	 */
	@ApiModelProperty(value = "验证时间")
	private Date verifyTime;
	/**
	 * 活体验证时间
	 */
	@ApiModelProperty(value = "活体验证时间")
	private Date liveVerifyTime;

	/**
	 * 验证结果
	 */
	@ApiModelProperty(value = "验证结果")
	private String verifyReason;

}
