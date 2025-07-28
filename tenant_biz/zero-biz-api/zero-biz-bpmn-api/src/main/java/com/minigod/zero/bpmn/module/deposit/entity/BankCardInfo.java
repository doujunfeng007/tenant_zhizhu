package com.minigod.zero.bpmn.module.deposit.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 * 银行卡管理记录表
 *
 * @ClassName: BankCardInfo
 * @Description: ${description}
 * @Author chenyu
 * @Date 2024/3/11
 * @Version 1.0
 */
@ApiModel(description = "银行卡管理记录表")
@Data
@TableName(value = "client_bank_card_info")
public class BankCardInfo implements Serializable {
	/**
	 * 自增ID
	 */
	@TableId(value = "id", type = IdType.AUTO)
	@ApiModelProperty(value = "自增ID")
	@NotNull(message = "自增ID不能为null")
	private Long id;

	/**
	 * 交易帐号
	 */
	@TableField(value = "client_id")
	@ApiModelProperty(value = "交易帐号")
	@Size(max = 32, message = "交易帐号最大长度要小于 32")
	@NotBlank(message = "交易帐号不能为空")
	private String clientId;

	/**
	 * 银行卡类型[1-香港本地银行 2-中国大陆银行 3-海外银行]
	 */
	@TableField(value = "bank_type")
	@ApiModelProperty(value = "银行卡类型[0-香港银行卡 1-大陆银行卡]")
	@NotNull(message = "银行卡类型[0-香港银行卡 1-大陆银行卡]不能为null")
	private Integer bankType;


	/**
	 * 银行名称
	 */
	@TableField(value = "bank_name")
	@ApiModelProperty(value = "银行名称")
	@Size(max = 256, message = "银行名称最大长度要小于 256")
	private String bankName;

	/**
	 * 银行账号
	 */
	@TableField(value = "bank_no")
	@ApiModelProperty(value = "银行账号")
	@Size(max = 32, message = "银行账号最大长度要小于 32")
	private String bankNo;

	/**
	 * 银行账户名
	 */
	@TableField(value = "bank_account")
	@ApiModelProperty(value = "银行账户名")
	@Size(max = 256, message = "银行账户名最大长度要小于 256")
	private String bankAccount;

	/**
	 * 登记时间
	 */
	@TableField(value = "register_time")
	@ApiModelProperty(value = "登记时间")
	@NotNull(message = "登记时间不能为null")
	private Date registerTime;

	/**
	 * 解登时间
	 */
	@TableField(value = "untied_time")
	@ApiModelProperty(value = "解登时间")
	private Date untiedTime;

	/**
	 * 当前状态[0-无效 1-有效]
	 */
	@TableField(value = "`status`")
	@ApiModelProperty(value = "当前状态[0-无效 1-有效]")
	@NotNull(message = "当前状态[0-无效 1-有效]不能为null")
	private Integer status;

	/**
	 * 创建人
	 */
	@TableField(value = "create_user")
	@ApiModelProperty(value = "创建人")
	@Size(max = 32, message = "创建人最大长度要小于 32")
	private String createUser;

	/**
	 * 更新人
	 */
	@TableField(value = "update_user")
	@ApiModelProperty(value = "更新人")
	@Size(max = 32, message = "更新人最大长度要小于 32")
	private String updateUser;

	/**
	 * 创建时间
	 */
	@TableField(value = "create_time")
	@ApiModelProperty(value = "创建时间")
	@NotNull(message = "创建时间不能为null")
	private Date createTime;

	/**
	 * 更新时间
	 */
	@TableField(value = "update_time")
	@ApiModelProperty(value = "更新时间")
	private Date updateTime;

	/**
	 * 银行代码
	 */
	@TableField(value = "bank_code")
	@ApiModelProperty(value = "银行代码")
	@Size(max = 64, message = "银行代码最大长度要小于 64")
	private String bankCode;

	/**
	 * 其他入金：0：未入，1：已入
	 */
	@TableField(value = "com_fund")
	@ApiModelProperty(value = "其他入金：0：未入，1：已入")
	@Size(max = 2, message = "其他入金：0：未入，1：已入最大长度要小于 2")
	private String comFund;

	/**
	 * edda入金：0：未入，1：已入
	 */
	@TableField(value = "edda_fund")
	@ApiModelProperty(value = "edda入金：0：未入，1：已入")
	@Size(max = 2, message = "edda入金：0：未入，1：已入最大长度要小于 2")
	private String eddaFund;

	/**
	 * 首次绑定来源：edda ；other
	 */
	@TableField(value = "bind_source")
	@ApiModelProperty(value = "首次绑定来源：edda ；other")
	@Size(max = 32, message = "首次绑定来源：edda ；other最大长度要小于 32")
	private String bindSource;

	/**
	 * 银行账户类别[1-港币 2-美元 3-人民币 0-综合账户]
	 */
	@TableField(value = "bank_account_category")
	@ApiModelProperty(value = "银行账户类别[1-港币 2-美元 3-人民币 0-综合账户]")
	private Integer bankAccountCategory;

	/**
	 * 银行卡审核状态
	 * {@link com.minigod.zero.biz.common.enums.BankCardAuthSign}
	 */
	@TableField(value = "auth_sign")
	@ApiModelProperty(value = "认证标识[0-未认证 1-待认证 2-已认证]")
	private Integer authSign;

	/**
	 * 租户ID
	 */
	@TableField(value = "tenant_id")
	@ApiModelProperty(value = "租户ID")
	@Size(max = 20, message = "租户ID最大长度要小于 20")
	@NotBlank(message = "租户ID不能为空")
	private String tenantId;

	@TableField(value = "swift_code")
	@ApiModelProperty(value = "电汇代码")
	private String swiftCode;

	@TableField(value = "bank_id")
	@ApiModelProperty(value = "银行代码")
	private String bankId;

	@TableField(value = "receive_region")
	@ApiModelProperty(value = "收款地区")
	private String receiveRegion;

	@TableField(value = "user_id")
	private Long userId;

	private static final long serialVersionUID = 1L;
}
