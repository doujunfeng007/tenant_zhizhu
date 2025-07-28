package com.minigod.zero.bpm.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import java.lang.Boolean;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

/**
 * 客户银行卡记录 实体类
 *
 * @author 掌上智珠
 * @since 2023-05-18
 */
@Data
@TableName("cash_deposit_account_bank")
@ApiModel(value = "CashDepositAccountBank对象", description = "客户银行卡记录")
public class CashDepositAccountBankEntity implements Serializable {

	private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private Long id;
    /**
     * 客户账号（交易账号）
     */
    @ApiModelProperty(value = "客户账号（交易账号）")
    private String tradeAccount;
    /**
     * 客户姓名
     */
    @ApiModelProperty(value = "客户姓名")
    private String clientName;
    /**
     * 英文名
     */
    @ApiModelProperty(value = "英文名")
    private String clientNameEn;
    /**
     * 证券手机号
     */
    @ApiModelProperty(value = "证券手机号")
    private String phoneNumber;
    /**
     * 开户类型[0=未知 1=互联网 2=线下开户 3=BPM]
     */
    @ApiModelProperty(value = "开户类型[0=未知 1=互联网 2=线下开户 3=BPM]")
    private Integer openAccountType;
    /**
     * 银行账户类型[0-香港账户 1-非香港帐号]
     */
    @ApiModelProperty(value = "银行账户类型[0-香港账户 1-非香港帐号]")
    private Integer openBankType;
    /**
     * 银行卡类型 1:大陆银行卡 2:香港银行卡
     */
    @ApiModelProperty(value = "银行卡类型 1:大陆银行卡 2:香港银行卡")
    private Integer bankType;
    /**
     * 银行名称
     */
    @ApiModelProperty(value = "银行名称")
    private String bankName;
    /**
     * 账户名称
     */
    @ApiModelProperty(value = "账户名称")
    private String bankAccountName;
    /**
     * 账户号码
     */
    @ApiModelProperty(value = "账户号码")
    private String bankAccount;
    /**
     * 登记时间
     */
    @ApiModelProperty(value = "登记时间")
    private Date regTime;
    /**
     * 解登时间
     */
    @ApiModelProperty(value = "解登时间")
    private Date regCancelTime;
    /**
     * 0:失效 1:正常
     */
    @ApiModelProperty(value = "0:失效 1:正常")
    private Boolean regStatus;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间")
    private Date updateTime;

}
