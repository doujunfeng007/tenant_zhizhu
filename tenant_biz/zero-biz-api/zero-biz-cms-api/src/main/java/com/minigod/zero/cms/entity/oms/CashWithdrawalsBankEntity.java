package com.minigod.zero.cms.entity.oms;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 出金银行卡配置表 实体类
 *
 * @author 掌上智珠
 * @since 2023-05-18
 */
@Data
@TableName("cash_withdrawals_bank")
@ApiModel(value = "CashWithdrawalsBank对象", description = "出金银行卡配置表")
public class CashWithdrawalsBankEntity implements Serializable {

	private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private Long id;
    /**
     * 序号
     */
    @ApiModelProperty(value = "序号")
    private Integer sort;
    /**
     * 银行卡类型[1-大陆银行卡 2-香港银行卡]
     */
    @ApiModelProperty(value = "银行卡类型[1-大陆银行卡 2-香港银行卡]")
    private Integer bankType;
    /**
     * 收款银行名称
     */
    @ApiModelProperty(value = "收款银行名称")
    private String receiptBankName;
    /**
     * 收款银行编码
     */
    @ApiModelProperty(value = "收款银行编码")
    private String receiptBankCode;
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
	 * 手续费备注繁体
	 */
	@ApiModelProperty(value = "手续费备注繁体")
	private String chargeMoneyRemarkHant;
	/**
	 * 手续费备注英文
	 */
	@ApiModelProperty(value = "手续费备注英文")
	private String chargeMoneyRemarkEn;
    /**
     * 出金到账时间
     */
    @ApiModelProperty(value = "出金到账时间")
    private String timeArrival;
	/**
	 * 出金到账时间繁体
	 */
	@ApiModelProperty(value = "出金到账时间繁体")
	private String timeArrivalHant;
	/**
	 * 出金到账时间英文
	 */
	@ApiModelProperty(value = "出金到账时间英文")
	private String timeArrivalEn;
    /**
     * 可用时间
     */
    @ApiModelProperty(value = "可用时间")
    private String effectiveTime;
    /**
     * swift编码
     */
    @ApiModelProperty(value = "swift编码")
    private String swiftCode;
    /**
     * 是否可见[0-不可见 1-可见]
     */
    @ApiModelProperty(value = "是否可见[0-不可见 1-可见]")
    private Integer isShow;
    /**
     * 最后操作人
     */
    @ApiModelProperty(value = "最后操作人")
    private String founder;
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
    /**
     * 银行卡背景颜色
     */
    @ApiModelProperty(value = "银行卡背景颜色")
    private String bgColor;
    /**
     * PC端图标
     */
    @ApiModelProperty(value = "PC端图标")
    private String pcIcon;
    /**
     * APP端图标
     */
    @ApiModelProperty(value = "APP端图标")
    private String appIcon;
    /**
     * 银行机构号
     */
    @ApiModelProperty(value = "银行机构号")
    private String bankId;

}
