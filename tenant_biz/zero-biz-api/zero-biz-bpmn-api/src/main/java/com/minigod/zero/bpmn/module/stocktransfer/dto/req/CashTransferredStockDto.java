package com.minigod.zero.bpmn.module.stocktransfer.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class CashTransferredStockDto implements Serializable {

	@ApiModelProperty(value = "主键")
	private Integer id;
	/**
	 * 客户ID
	 */
	@ApiModelProperty(value = "客户ID")
	private Long custId;
	/**
	 * 劵商名称
	 */
	@ApiModelProperty(value = "劵商名称")
	private String cashName;
	/**
	 * 账户姓名
	 */
	@ApiModelProperty(value = "账户姓名")
	private String accountName;
	/**
	 * 账户号码
	 */
	@ApiModelProperty(value = "账户号码")
	private String accountNumber;
	/**
	 * 接收劵商
	 */
	@ApiModelProperty(value = "接收劵商")
	private String receiveSec;
	/**
	 * 接收账户
	 */
	@ApiModelProperty(value = "接收账户")
	private String receiveAccount;
	/**
	 * 邀请人
	 */
	@ApiModelProperty(value = "邀请人")
	private String inviter;
	/**
	 * 转入股票  1港股 2美股
	 */
	@ApiModelProperty(value = "转入股票  1港股 2美股")
	private Integer isShares;
	/**
	 * CCASS代码
	 */
	@ApiModelProperty(value = "CCASS代码")
	private String ccass;
	/**
	 * 状态 0 已保存 1已提交 2已受理 3 已退回 4已完成
	 */
	@ApiModelProperty(value = "状态 0 已保存 1已提交 2已受理 3 已退回 4已完成")
	private Integer status;
	/**
	 * 是否全部加载 0 否 1 是
	 */
	@ApiModelProperty(value = "是否全部加载 0 否 1 是")
	private Integer isFind;
	/**
	 * 转入状态 0未转入 1已转入
	 */
	@ApiModelProperty(value = "转入状态 0未转入 1已转入")
	private Integer transferState;
	/**
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间")
	private Date createdTime;
	/**
	 * 更新时间
	 */
	@ApiModelProperty(value = "更新时间")
	private Date modifyTime;
	/**
	 * 退回操作人
	 */
	@ApiModelProperty(value = "退回操作人")
	private String backPerson;
	/**
	 * 退回理由
	 */
	@ApiModelProperty(value = "退回理由")
	private String backReason;

	private String fileUrl;

	private String appFileUrl;

	private Long accImgId;

	private String pwd;

	@ApiModelProperty(value = "转仓类型:1.转入,2.转出")
	private Integer transferType;

	@ApiModelProperty(value = "券商联系人邮箱")
	private String contactsEmail;

	/**
	 * 是否发放奖励
	 */
	@ApiModelProperty(value = "是否发放奖励")
	private Integer isReward;
	/**
	 * 交易账号
	 */
	@ApiModelProperty(value = "交易账号")
	private String tradeAccount;
	/**
	 * 转出券商的联系人
	 */
	@ApiModelProperty(value = "转出券商的联系人")
	private String rolloutContacts;
	/**
	 * 转出券商的联系人电话
	 */
	@ApiModelProperty(value = "转出券商的联系人电话")
	private String contactsPhone;

	@ApiModelProperty(value = "清算银行名称")
	private String clearingBankName;

	@ApiModelProperty(value = "清算银行账号")
	private String clearingBankAccount;

	private String dtcno;

	private List<CashSharesDataDto> sharesData;
}
