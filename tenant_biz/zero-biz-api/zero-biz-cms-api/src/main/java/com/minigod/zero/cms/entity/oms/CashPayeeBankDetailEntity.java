package com.minigod.zero.cms.entity.oms;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 付款账户信息 实体类
 *
 * @author 掌上智珠
 * @since 2023-05-18
 */
@Data
@TableName("cash_payee_bank_detail")
@ApiModel(value = "CashPayeeBankDetail对象", description = "付款账户信息")
public class CashPayeeBankDetailEntity implements Serializable {

	private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private Long id;
    /**
     * 收款银行主表
     */
    @ApiModelProperty(value = "收款银行主表")
    private Long payeeBankId;
    /**
     * 转账类型 1fps 2网银 3支票 4快捷入金 5银证转账
     */
    @ApiModelProperty(value = "转账类型 1fps 2网银 3支票 4快捷入金 5银证转账")
    private String supportType;
    /**
     * 币种
     */
    @ApiModelProperty(value = "币种")
    private Integer currency;
    /**
     * 收款银行账户
     */
    @ApiModelProperty(value = "收款银行账户")
    private String bankAccount;
    /**
     * 收款人地址
     */
    @ApiModelProperty(value = "收款人地址")
    private String payeeAddress;
    /**
     * app端指引
     */
    @ApiModelProperty(value = "app端指引")
    private String appGuide;
    /**
     * pc端指引
     */
    @ApiModelProperty(value = "pc端指引")
    private String pcGuide;
    /**
     * 示例
     */
    @ApiModelProperty(value = "示例")
    private String example;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    private String createUser;
    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间")
    private Date updateTime;
    /**
     * 修改人
     */
    @ApiModelProperty(value = "修改人")
    private String updateUser;
    /**
     * 是否启用
     */
    @ApiModelProperty(value = "是否启用")
    private Boolean enable;
    /**
     * fps识别号 support_type=1时必填
     */
    @ApiModelProperty(value = "fps识别号 support_type=1时必填")
    private String fpsId;
    /**
     * 到账时间
     */
    @ApiModelProperty(value = "到账时间")
    private String timeArrival;
	/**
	 * 到账时间繁体
	 */
	@ApiModelProperty(value = "到账时间繁体")
	private String timeArrivalHant;
	/**
	 * 到账时间英文
	 */
	@ApiModelProperty(value = "到账时间英文")
	private String timeArrivalEn;
    /**
     * 到账时间备注
     */
    @ApiModelProperty(value = "到账时间备注")
    private String timeArrivalRemark;
    /**
     * 手续费
     */
    @ApiModelProperty(value = "手续费")
    private String chargeMoney;
	/**
	 * 手续费繁体
	 */
	@ApiModelProperty(value = "手续费繁体")
	private String chargeMoneyHant;
	/**
	 * 手续费英文
	 */
	@ApiModelProperty(value = "手续费英文")
	private String chargeMoneyEn;
    /**
     * 手续费备注
     */
    @ApiModelProperty(value = "手续费备注")
    private String chargeMoneyRemark;
    /**
     * 收款人名称
     */
    @ApiModelProperty(value = "收款人名称")
    private String payeeName;
    /**
     * 收款人名称备注
     */
    @ApiModelProperty(value = "收款人名称备注")
    private String payeeNameRemark;
    /**
     * 账户类型 1大账户 2子账户
     */
    @ApiModelProperty(value = "账户类型 1大账户 2子账户")
    private Integer accountType;

	@ApiModelProperty(value = "最大限额")
	private BigDecimal maxAmt;
}
