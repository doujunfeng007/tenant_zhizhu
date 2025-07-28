package com.minigod.zero.customer.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 大账户协议信息表 实体类
 *
 * @author ken
 * @since 2024-04-11
 */
@Data
@TableName("customer_agreement_info")
@ApiModel(value = "CustomerAgreementInfo对象", description = "大账户协议信息表")
public class CustomerAgreementInfoEntity  {

	@JsonSerialize(
		using = ToStringSerializer.class
	)
	@ApiModelProperty("主键id")
	@TableId(
		value = "id",
		type = IdType.ASSIGN_ID
	)
	private Long id;

    @ApiModelProperty("客户号（个人/授权人）")
    private Long custId;

    @ApiModelProperty("理财账户id")
    private String accountId;

    @ApiModelProperty("港股实名制签署状态")
    private Integer hkdirStatus;

    @ApiModelProperty("港股实名制签署日期")
    private String hkdirSignDate;

    @ApiModelProperty("港股衍生品协议签署状态")
    private Integer hkderiStatus;

    @ApiModelProperty("港股衍生品签署日期")
    private String hkderiSignDate;

    @ApiModelProperty("港股创业版协议签署状态")
    private Integer hkgemStatus;

    @ApiModelProperty("港股创业版协议签署时间")
    private String hkgemSignDate;

    @ApiModelProperty("港股虚拟资产协议签署状态")
    private Integer hkvaStatus;

    @ApiModelProperty("港股虚拟资产协议签署时间")
    private String hkvaSignDate;

    @ApiModelProperty("美股W8签署状态")
    private Integer usw8Status;

    @ApiModelProperty("美股W8签署时间")
    private String usw8SignDate;

    @ApiModelProperty("客户风险级别")
    private Integer riskLevel;

    @ApiModelProperty("风险评测签署时间")
    private String riskSignDate;

    @ApiModelProperty("客户PI认证等级")
    private Integer piLevel;

    @ApiModelProperty("pi签署时间")
    private String piSignDate;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("更新时间")
    private Date updateTime;

	@TableLogic(
		value = "0",
		delval = "1"
	)
	@ApiModelProperty("是否已删除")
	private Integer isDeleted;

    private static final long serialVersionUID = 1L;
}
