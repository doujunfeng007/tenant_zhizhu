package com.minigod.zero.manage.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * @Author Xiaowei.Zhu
 * @Date 2023-07-24 14:05:14
 * @Description
 **/
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CashWithdrawalsBankSaveDTO implements Serializable {
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
    @NotEmpty(message =  "收款银行名称不能为空!")
    private String receiptBankName;

	/**
	 * 收款银行繁体名称
	 */
	@ApiModelProperty(value = "收款银行繁体名称")
	@NotEmpty(message =  "收款银行繁体名称不能为空!")
	private String receiptBankNameHant;

	/**
	 * 收款银行英文名称
	 */
	@ApiModelProperty(value = "收款银行英文名称")
	@NotEmpty(message =  "收款银行英文名称不能为空!")
	private String receiptBankNameEn;
    /**
     * 收款银行编码
     */
    @ApiModelProperty(value = "收款银行编码")
    @NotEmpty(message =  "收款银行编码不能为空")
    private String receiptBankCode;
	/**
	 * 接受转账方式1-香港银行普通转账 2-香港银行本地转账 3-电汇 4-FPS ID
	 */
	@ApiModelProperty(value = "接受转账方式1-香港银行普通转账 2-香港银行本地转账 3-电汇 4-FPS ID")
	private String supportType;
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
	 * 手续费备注繁体
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
     * 银行卡背景颜色
     */
    @ApiModelProperty(value = "银行背景颜色")
    private String bgColor;
    /**
     * PC端图标
     */
    @ApiModelProperty(value = "PC端图标")
    @NotEmpty(message = "PC端图标不能为空!")
    private String pcIcon;
    /**
     * APP端图标
     */
    @ApiModelProperty(value = "APP端图标")
    @NotEmpty(message = "APP端图标不能为空!")
    private String appIcon;
    /**
     * 银行机构号
     */
    @ApiModelProperty(value = "银行机构号")
    @NotEmpty(message = "银行机构号不能为空!")
    private String bankId;
}
