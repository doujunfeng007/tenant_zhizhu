package com.minigod.zero.bpm.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 银行卡四要素验证信息表 实体类
 *
 * @author 掌上智珠
 * @since 2023-05-17
 */
@Data
@TableName("acct_card_verify")
@ApiModel(value = "AcctCardVerify对象", description = "银行卡四要素验证信息表")
public class AcctCardVerifyEntity implements Serializable {

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
     * 真实姓名
     */
    @ApiModelProperty(value = "真实姓名")
    private String realName;
    /**
     * 手机号
     */
    @ApiModelProperty(value = "手机号")
    private String cellPhone;
    /**
     * 身份证
     */
    @ApiModelProperty(value = "身份证")
    private String idCard;
    /**
     * 银行卡号
     */
    @ApiModelProperty(value = "银行卡号")
    private String bankCard;
    /**
     * 验证时间
     */
    @ApiModelProperty(value = "验证时间")
    private Date createDate;
    /**
     * 状态(0:不通过 1:通过)
     */
    @ApiModelProperty(value = "状态(0:不通过 1:通过)")
    private Integer status;
    /**
     * 验证次数
     */
    @ApiModelProperty(value = "验证次数")
    private Integer verifyCount;
    /**
     * 交易类型
     */
    @ApiModelProperty(value = "交易类型")
    private String txCode;
    /**
     * 交易流水号
     */
    @ApiModelProperty(value = "交易流水号")
    private String txSn;
    /**
     * 响应报文
     */
    @ApiModelProperty(value = "响应报文")
    private String rtnInfo;

}
