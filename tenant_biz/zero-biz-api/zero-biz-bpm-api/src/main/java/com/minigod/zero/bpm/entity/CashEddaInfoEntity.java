package com.minigod.zero.bpm.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import java.math.BigDecimal;
import java.io.Serializable;

/**
 * edda申请流水表 实体类
 *
 * @author 掌上智珠
 * @since 2023-05-18
 */
@Data
@TableName("cash_edda_info")
@ApiModel(value = "CashEddaInfo对象", description = "edda申请流水表")
public class CashEddaInfoEntity implements Serializable {

	private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private Long id;
    /**
     * 客户ID
     */
    @ApiModelProperty(value = "客户ID")
    private Long custId;
    /**
     * 银行 1大陆 2香港 3其他
     */
    @ApiModelProperty(value = "银行 1大陆 2香港 3其他")
    private Integer bankType;
    /**
     * 交易账户
     */
    @ApiModelProperty(value = "交易账户")
    private String tradeAccount;
    /**
     * 资金账号
     */
    @ApiModelProperty(value = "资金账号")
    private String fundAccount;
    /**
     * 银行名称
     */
    @ApiModelProperty(value = "银行名称")
    private String bankName;
    /**
     * 银行代码
     */
    @ApiModelProperty(value = "银行代码")
    private String bankCode;
    /**
     * 银行bankid
     */
    @ApiModelProperty(value = "银行bankid")
    private String bankId;
    /**
     * 存入银行账户
     */
    @ApiModelProperty(value = "存入银行账户")
    private String depositAccount;
    /**
     * 存入账户名称
     */
    @ApiModelProperty(value = "存入账户名称")
    private String depositAccountName;
    /**
     * 存入账户类型:1 港币账户; 2 综合多币种账户
     */
    @ApiModelProperty(value = "存入账户类型:1 港币账户; 2 综合多币种账户")
    private Integer depositAccountType;
    /**
     * 银行开户证件类型:1 中华人民共和国居民身份证, 2 中华人民共和国往来港澳通行证, 3 香港居民身份证, 4 护照
     */
    @ApiModelProperty(value = "银行开户证件类型:1 中华人民共和国居民身份证, 2 中华人民共和国往来港澳通行证, 3 香港居民身份证, 4 护照")
    private Integer bankIdKind;
    /**
     * 银行开户证件号码
     */
    @ApiModelProperty(value = "银行开户证件号码")
    private String bankIdNo;
    /**
     * 收款银行core
     */
    @ApiModelProperty(value = "收款银行core")
    private String benefitBankCore;
    /**
     * 收款账号
     */
    @ApiModelProperty(value = "收款账号")
    private String benefitNo;
    /**
     * 收款账户名称
     */
    @ApiModelProperty(value = "收款账户名称")
    private String benefitAccount;
    /**
     * 状态 0未授权 1授权中 2授权失败 3授权成功 4解除授权 5 短信验证未通过
     */
    @ApiModelProperty(value = "状态 0未授权 1授权中 2授权失败 3授权成功 4解除授权 5 短信验证未通过")
    private Integer eddaState;
    /**
     * 失败原因
     */
    @ApiModelProperty(value = "失败原因")
    private String eddaFailReason;
    /**
     * 数据状态 0 用户自己删除 1正常 2更新删除
     */
    @ApiModelProperty(value = "数据状态 0 用户自己删除 1正常 2更新删除")
    private Integer dataState;
    /**
     * 数据删除时间
     */
    @ApiModelProperty(value = "数据删除时间")
    private Date deleteTime;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;
    /**
     * 回调状态
     */
    @ApiModelProperty(value = "回调状态 0：异常 1：正常")
    private Integer pushRecved;
    /**
     * 预约号
     */
    @ApiModelProperty(value = "预约流水号")
    private String applicationId;
    /**
     * 回调错误次数
     */
    @ApiModelProperty(value = "回调错误次数")
    private Integer errCnt;
    /**
     * 银行单笔限额
     */
    @ApiModelProperty(value = "银行单笔限额")
    private BigDecimal bankQuota;
    /**
     * 图标
     */
    @ApiModelProperty(value = "图标")
    private String icon;
    /**
     * 汇丰返回的edda授权唯一标识
     */
    @ApiModelProperty(value = "汇丰返回的edda授权唯一标识")
    private String mandateId;
    /**
     * 每次edda授权短信验证的唯一标识 当bankId=004时才记录
     */
    @ApiModelProperty(value = "每次edda授权短信验证的唯一标识 当bankId=004时才记录")
    private String otpId;
    /**
     * 汇丰返回的edda授权状态
     */
    @ApiModelProperty(value = "汇丰返回的edda授权状态")
    private String mandateStatus;
    /**
     * 汇丰edda授权短信验证码校验次数 当bankId=004时才记录
     */
    @ApiModelProperty(value = "汇丰edda授权短信验证码校验次数 当bankId=004时才记录")
    private Integer smsConformCount;
    /**
     * 汇丰edda授权短信验证码已重发次数 当bankId=004时才记录
     */
    @ApiModelProperty(value = "汇丰edda授权短信验证码已重发次数 当bankId=004时才记录")
    private Integer smsRetryCount;
    /**
     * 汇丰edda银行预留手机号
     */
    @ApiModelProperty(value = "汇丰edda银行预留手机号")
    private String bankPhone;
    /**
     * 汇丰返回的脱敏手机号码
     */
    @ApiModelProperty(value = "汇丰返回的脱敏手机号码")
    private String phoneMask;

}
