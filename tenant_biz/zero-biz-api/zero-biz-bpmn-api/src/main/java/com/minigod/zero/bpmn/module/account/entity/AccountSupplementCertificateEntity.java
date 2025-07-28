package com.minigod.zero.bpmn.module.account.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.minigod.zero.core.tenant.mp.TenantEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


/**
 *  开户补充证件信息
 *
 * @author Chill
 */
@Data
@TableName("open_account_supplement_certificate")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "AccountSupplementCertificate对象", description = "")
public class AccountSupplementCertificateEntity extends TenantEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 流水号
	 */
	@ApiModelProperty(value = "流水号")
	private String applicationId;
	/**
	 * 用户ID
	 */
	@ApiModelProperty(value = "用户ID")
	private Long userId;
	/**
	 * 补充证件类型
	 */
	@ApiModelProperty(value = "补充证件类型")
	private Integer supIdKind;
	/**
	 * 是否永久居民
	 */
	@ApiModelProperty(value = "是否永久居民")
	private Integer isPermanentResident;
	/**
	 * 是否香港证件
	 */
	@ApiModelProperty(value = "是否香港证件")
	private Integer isHkIdCard;
	/**
	 * 补充证件号
	 */
	@ApiModelProperty(value = "补充证件号")
	private String supIdCardNumber;
	/**
	 * 证件结束日期
	 */
	@ApiModelProperty(value = "证件结束日期")
	private String supIdCardValidDateEnd;
	/**
	 * 证件开始日期
	 */
	@ApiModelProperty(value = "证件开始日期")
	private String supIdCardValidDateStart;
	/**
	 * 补充证书编号
	 */
	@ApiModelProperty(value = "补充证书编号")
	private String supCertificateNo;

	/**
	 * 是否持有国民身份证
	 */
	@ApiModelProperty("是否持有国民身份证")
	private Integer passportCitizenIdCard;
	/**
	 * 证件护照签发地
	 */
	@ApiModelProperty("证件护照签发地")
	private Integer placeOfIssue;

	/**
	 * 国民证件签发地
	 */
	@ApiModelProperty("国民证件签发地")
	private Integer idCardPlaceOfIssue;
}
