package com.minigod.zero.bpmn.module.stocktransfer.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.minigod.zero.core.mp.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @ClassName SecTransferredStock.java
 * @author chen
 * @Description TODO
 * @createTime 2024年03月06日 17:20:00
 */

/**
 * 股票转入转出信息表
 */
@ApiModel(description = "股票转入转出信息表")
@Data
@TableName(value = "sec_transferred_stock")
@EqualsAndHashCode(callSuper = true)
public class SecTransferredStockEntity extends BaseEntity {
    /**
     * 外部券商名称
     */
    @TableField(value = "sec_name")
    @ApiModelProperty(value = "外部券商名称")
    private String secName;

	@TableField(value = "tenant_id")
	@ApiModelProperty(value = "租户id")
	private String tenantId;

    /**
     * 外部券商姓名
     */
    @TableField(value = "account_name")
    @ApiModelProperty(value = "外部券商姓名")
    private String accountName;

    /**
     * 外部券商号码
     */
    @TableField(value = "account_number")
    @ApiModelProperty(value = "外部券商号码")
    private String accountNumber;

    /**
     * 接收方名称
     */
    @TableField(value = "receive_sec")
    @ApiModelProperty(value = "接收方名称")
    private String receiveSec;

    /**
     * 接收方账户
     */
    @TableField(value = "receive_account")
    @ApiModelProperty(value = "接收方账户")
    private String receiveAccount;

    /**
     * 邀请人
     */
    @TableField(value = "inviter")
    @ApiModelProperty(value = "邀请人")
    private String inviter;

    /**
     * 用户id
     */
    @TableField(value = "cust_id")
    @ApiModelProperty(value = "用户id")
    private Long custId;

    /**
     * 转入股票  1港股 2美股
     */
    @TableField(value = "is_shares")
    @ApiModelProperty(value = "转入股票  1港股 2美股")
    private Integer isShares;

    /**
     * CCASS代码
     */
    @TableField(value = "ccass")
    @ApiModelProperty(value = "CCASS代码")
    private String ccass;

    /**
     * 是否全部加载 0 否 1 是
     */
    @TableField(value = "is_find")
    @ApiModelProperty(value = "是否全部加载 0 否 1 是")
    private Integer isFind;

    /**
     * 转入状态 0未转入 1已转入
     */
    @TableField(value = "transfer_state")
    @ApiModelProperty(value = "转入状态 0未转入 1已转入")
    private Integer transferState;


    /**
     * 退回操作人
     */
    @TableField(value = "back_person")
    @ApiModelProperty(value = "退回操作人")
    private Long backPerson;

    /**
     * 退回理由
     */
    @TableField(value = "back_reason")
    @ApiModelProperty(value = "退回理由")
    private String backReason;

    /**
     * 转入凭证图片
     */
    @TableField(value = "acc_img")
    @ApiModelProperty(value = "转入凭证图片")
    private String accImg;

    /**
     * 交易账号
     */
    @TableField(value = "client_id")
    @ApiModelProperty(value = "交易账号")
    private String clientId;

    /**
     * 外部券商联系地址
     */
    @TableField(value = "rollout_contacts")
    @ApiModelProperty(value = "转出券商的联系人")
    private String rolloutContacts;

    /**
     * 外部券商的联系人电话
     */
    @TableField(value = "contacts_phone")
    @ApiModelProperty(value = "外部券商的联系人电话")
    private String contactsPhone;

    /**
     * 1转入记录2转出记录
     */
    @TableField(value = "regulation_type")
    @ApiModelProperty(value = "1转入记录2转出记录")
    private Integer regulationType;

    /**
     * 清算行名称
     */
    @TableField(value = "clearing_bank_name")
    @ApiModelProperty(value = "清算行名称")
    private String clearingBankName;

    /**
     * 清算行账号
     */
    @TableField(value = "clearing_bank_account")
    @ApiModelProperty(value = "清算行账号")
    private String clearingBankAccount;

    /**
     * 转出券商的联系人邮箱
     */
    @TableField(value = "contacts_email")
    @ApiModelProperty(value = "转出券商的联系人邮箱")
    private String contactsEmail;

	@TableField(value = "dtcno")
	@ApiModelProperty(value = "DTC No")
	private String dtcno;
}
