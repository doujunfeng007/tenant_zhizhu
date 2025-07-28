package com.minigod.zero.bpmn.module.account.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.minigod.zero.core.mp.base.BaseEntity;
import com.minigod.zero.core.tenant.mp.TenantEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import java.time.LocalDateTime;


/**
 *  实体类
 *
 * @author Chill
 */
@Data
@TableName("open_account_ca_verity_info")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "AccountCaVerityInfo对象", description = "")
public class AccountCaVerityInfoEntity extends TenantEntity {

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
	 * 用户证书主题
	 */
	@ApiModelProperty(value = "用户证书主题")
	private String caCertDn;
	/**
	 * 证书序列号
	 */
	@ApiModelProperty(value = "证书序列号")
	private String caCertSn;
	/**
	 * 认证时间
	 */
	@ApiModelProperty(value = "认证时间")
	private Date certTime;
	/**
	 * 文件ID
	 */
	@ApiModelProperty(value = "文件ID")
	private String fileId;
	/**
	 * 文件hash
	 */
	@ApiModelProperty(value = "文件hash")
	private String fileHash;

}
