package com.minigod.zero.bpm.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.minigod.zero.core.mp.base.AppEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 交易账户信息表 实体类
 *
 * @author 掌上智珠
 * @since 2023-03-27
 */
@Data
@TableName("bpm_account_info")
@ApiModel(value = "BpmAccountInfo对象", description = "交易账户信息表")
@EqualsAndHashCode(callSuper = true)
public class BpmAccountInfoEntity extends AppEntity {

    /**
     * 客户ID
     */
    @ApiModelProperty(value = "客户ID")
    private Long custId;
    /**
     * 交易账号
     */
    @ApiModelProperty(value = "交易账号")
    private String tradeAccount;
	/**
	 * 授权人邮箱
	 */
	@ApiModelProperty(value = "授权人邮箱")
	private String authorEmail;
	/**
	 * 授权人状态：0-无效 1-有效
	 */
	@ApiModelProperty(value = "授权人状态：0-无效 1-有效")
	private Integer authorStatus;
    /**
     * 账号类型：1-个人 2-联名 3-公司
     */
    @ApiModelProperty(value = "账号类型：1-个人 2-联名 3-公司 4-ESOP")
    private Integer acctType;
	/**
	 * 交易授权：0-否 1-是
	 */
	@ApiModelProperty(value = "交易授权：0-否 1-是")
	private Integer tradeAuth;
	/**
	 * APP权限，格式：[a,b,c,d]
	 */
	@ApiModelProperty(value = "APP权限，格式：[a,b,c,d]")
	private String appPermission;
	/**
	 * 是否当前选中：0-否 1-是
	 */
	@ApiModelProperty(value = "是否当前选中：0-否 1-是")
	private Integer isCurrent;
	/**
	 * 账户状态[0-正常 1-冻结 2-挂失 3-销户 D-休眠 E-不合格 F-锁定]
	 */
	@ApiModelProperty(value = "账户状态[0-正常 1-冻结 2-挂失 3-销户 D-休眠 E-不合格 F-锁定]")
	private String status;


	/**
	 * Esop标识落库规则：
	 * aecode between 2000 and 2999   is_esop = 1 else 0
	 * aecode = 1248  is_ibd = 1 else 0
	 * aecode between 2000 and 2999 and trade_account like '2%'   is_esop_limit  = 1 else 0
	 */
	/*@ApiModelProperty(value = "是否esop账户：0-否 1-是")
	private Integer isEsop;

	@ApiModelProperty(value = "是否esop限制性账户：0-否 1-是")
	private Integer isEsopLimit;

	@ApiModelProperty(value = "是否投行账户：0-否 1-是")
	private Integer isIbd;*/

}
