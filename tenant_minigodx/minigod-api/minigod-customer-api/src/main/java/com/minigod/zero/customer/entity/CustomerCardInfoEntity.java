package com.minigod.zero.customer.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.lang.Boolean;
import lombok.EqualsAndHashCode;
import com.minigod.zero.core.mp.base.BaseEntity;

/**
 * 客户银行卡记录 实体类
 *
 * @author ken
 * @since 2024-04-11
 */
@Data
@TableName("customer_card_info")
@ApiModel(value = "CustomerCardInfo对象", description = "客户银行卡记录")
@EqualsAndHashCode(callSuper = true)
public class CustomerCardInfoEntity extends BaseEntity {

    /**
     * 客户id
     */
    @ApiModelProperty(value = "客户id")
    private Long custId;
    /**
     * 客户账号（交易账号）
     */
    @ApiModelProperty(value = "客户账号（交易账号）")
    private String tradeAccount;
    /**
     * 账户类型0股票账户，1基金账户，2.债券账户
     */
    @ApiModelProperty(value = "账户类型0股票账户，1基金账户，2.债券账户")
    private Byte accountType;
    /**
     * 手机号
     */
    @ApiModelProperty(value = "手机号")
    private String phoneNumber;
    /**
     * 开户类型[0=未知 1=互联网 2=线下开户 3=BPM]
     */
    @ApiModelProperty(value = "开户类型[0=未知 1=互联网 2=线下开户 3=BPM]")
    private Byte openAccountType;
    /**
     * 银行账户类型[0-香港账户 1-非香港帐号]
     */
    @ApiModelProperty(value = "银行账户类型[0-香港账户 1-非香港帐号]")
    private Byte openBankType;
    /**
     * 银行卡类型 1:大陆银行卡 2:香港银行卡
     */
    @ApiModelProperty(value = "银行卡类型 1:大陆银行卡 2:香港银行卡")
    private Byte bankType;
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

}
