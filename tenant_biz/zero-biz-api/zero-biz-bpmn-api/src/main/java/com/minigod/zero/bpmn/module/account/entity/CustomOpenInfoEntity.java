package com.minigod.zero.bpmn.module.account.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.minigod.zero.core.mp.base.AppEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.time.LocalDateTime;


/**
 *  实体类
 *
 * @author Chill
 */
@Data
@TableName("custom_open_info")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "CustomOpenInfo对象", description = "")
public class CustomOpenInfoEntity extends AppEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 用户ID
	 */
	@ApiModelProperty(value = "用户ID")
	private Long userId;
	/**
	 * 开户接入方式: 1:H5开户 2:APP开户 3:线下开户
	 */
	@ApiModelProperty(value = "开户接入方式: 1:H5开户 2:APP开户 3:线下开户")
	private Integer accessWay;
	/**
	 * 证件类型：0=未知 1=大陆居民身份证 2=香港居民身份证 3=护照 4=香港临时身份证 5=其他
	 */
	@ApiModelProperty(value = "证件类型：0=未知 1=大陆居民身份证 2=香港居民身份证 3=护照 4=香港临时身份证 5=其他")
	private Integer idKind;
	/**
	 * 身份证号
	 */
	@ApiModelProperty(value = "身份证号")
	private String idCard;
	/**
	 * 开户方式：0:未知，1:线上内地开户，2:线上香港开户，3:线下（开户宝）
	 */
	@ApiModelProperty(value = "开户方式：0:未知，1:线上内地开户，2:线上香港开户，3:线下（开户宝）")
	private Integer openType;
	/**
	 * 银行卡号
	 */
	@ApiModelProperty(value = "银行卡号")
	private String bankCard;
	/**
	 * 账户类型 1：现金账户 2：融资账户
	 */
	@ApiModelProperty(value = "账户类型 1：现金账户 2：融资账户")
	private Integer fundAccountType;
	/**
	 * 账户类型：1：港股 2：美股 3：中华通
	 */
	@ApiModelProperty(value = "账户类型：1：港股 2：美股 3：中华通")
	private String accountMarkets;
	@ApiModelProperty(value = "股票类型")
	private String stockTypes;
	/**
	 * 0:预批中，1:审批中，2:CA认证中,3:柜台开户中
	 */
	@ApiModelProperty(value = "0:预批中，1:审批中，2:CA认证中,3:柜台开户中")
	private Integer pendingType;
	/**
	 * 0:其他错误，1:基本数据错误，2:影像数据错误,3:基本或影像数据错误，4:CA数据错误
	 */
	@ApiModelProperty(value = "0:其他错误，1:基本数据错误，2:影像数据错误,3:基本或影像数据错误，4:CA数据错误")
	private Integer failType;
	/**
	 * 失败原因
	 */
	@ApiModelProperty(value = "失败原因")
	private String failReason;
	/**
	 * 是否已下发
	 */
	@ApiModelProperty(value = "是否已下发")
	private Boolean isNoticed;
	/**
	 * 下发失败次数
	 */
	@ApiModelProperty(value = "下发失败次数")
	private Integer noticeErrCount;
	/**
	 * 是否已推送
	 */
	@ApiModelProperty(value = "是否已推送")
	private Boolean isPushed;
	/**
	 * 推送失败次数
	 */
	@ApiModelProperty(value = "推送失败次数")
	private Integer pushErrCount;
	/**
	 * 远程开户系统ID
	 */
	@ApiModelProperty(value = "远程开户系统ID")
	private String remoteId;
	/**
	 * CA认证状态， 0:无，1:待认证，2:需要认证，3:认证完成（待推送），4:已推送
	 */
	@ApiModelProperty(value = "CA认证状态， 0:无，1:待认证，2:需要认证，3:认证完成（待推送），4:已推送")
	private Integer caStatus;
	/**
	 *
	 */
	@ApiModelProperty(value = "")
	private Integer caValidId;
	/**
	 * 开户日期
	 */
	@ApiModelProperty(value = "开户日期")
	private LocalDateTime openDate;
	/**
	 * 客户号（交易帐号）
	 */
	@ApiModelProperty(value = "客户号（交易帐号）")
	private String tradeAccount;
	/**
	 * 是否发送消息
	 */
	@ApiModelProperty(value = "是否发送消息")
	private Boolean isSend;
	/**
	 * 开户数据
	 */
	@ApiModelProperty(value = "开户数据")
	private String formData;
	/**
	 * 开户PDF文件地址
	 */
	@ApiModelProperty(value = "开户PDF文件地址")
	private String openAccountPdfUrl;
	/**
	 * 开户来源
	 */
	@ApiModelProperty(value = "开户来源")
	private String openSource;
	/**
	 * 开户邀请人
	 */
	@ApiModelProperty(value = "开户邀请人")
	private Integer openInviteId;
	/**
	 * 开户渠道
	 */
	@ApiModelProperty(value = "开户渠道")
	private String openChannel;

	/**
	 * 租户ID
	 */
	@ApiModelProperty(value = "租户ID")
	private String tenantId;
	/**
	 * 状态 0:未提交（默认），1:开户中，2:已取消,3:开户成功，4:开户失败，5:销户
	 */
	@ApiModelProperty(value = "状态")
	private Integer status;
}
