package com.minigod.zero.manage.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Date;

/**
 *
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CashDepositBankSaveDTO implements Serializable {

    @ApiModelProperty(value = "id")
    Long id;
     /**
     * 序号
     */
    @ApiModelProperty(value = "序号")
    private Integer sort;
    /**
     * 1-大陆银行卡 2-香港银行卡 3-其他地区银行卡
     */
    @ApiModelProperty(value = "1-大陆银行卡 2-香港银行卡 3-其他地区银行卡")
    Integer bankType;
    /**
     * 汇款银行名称
     */
    @ApiModelProperty(value = "汇款银行名称")
    @NotEmpty(message = "汇款银行名称不能为空!")
    String remitBankName;
	/**
	 * 汇款银行名称繁体
	 */
	@ApiModelProperty(value = "汇款银行名称繁体")
	@NotEmpty(message = "汇款银行名称繁体不能为空!")
	String remitBankNameHant;
	/**
	 * 汇款银行名称英文
	 */
	@ApiModelProperty(value = "汇款银行名称英文")
	@NotEmpty(message = "汇款银行名称英文不能为空!")
	String remitBankNameEn;
    /**
     * 汇款银行编码
     */
    @ApiModelProperty(value = "汇款银行编码")
    @NotEmpty(message = "汇款银行编码不能为空!")
    String remitBankCode;
    /**
     * PC端银行Logo
     */
    @ApiModelProperty(value = "PC端银行Logo")
    @NotEmpty(message = "PC端银行Logo不能为空!")
    String pcIcon;
    /**
     * APP端银行Logo
     */
    @ApiModelProperty(value = "APP端银行Logo")
    @NotEmpty(message = "APP端银行Logo不能为空!")
    String appIcon;
    /**
     * 手续费
     */
    @ApiModelProperty(value = "手续费")
    String chargeMoney;
    /**
     * 到账时间
     */
    @ApiModelProperty(value = "到账时间")
    String timeArrival;
    /**
     * 到账时间备注
     */
    @ApiModelProperty(value = "到账时间备注")
    String timeArrivalRemark;
    /**
     * 收款银行名称
     */
    @ApiModelProperty(value = "收款银行名称")
    String receiptBankName;
    /**
     * 收款银行英文名
     */
    @ApiModelProperty(value = "收款银行英文名")
    String receiptBankNameSpell;
    /**
     * 收款银行编码
     */
    @ApiModelProperty(value = "收款银行编码")
    String receiptBankCode;
    /**
     * 收款银行地址
     */
    @ApiModelProperty(value = "收款银行地址")
    String receiptBankAddress;
    /**
     * SWIFT编码
     */
    @ApiModelProperty(value = "SWIFT编码")
    String swiftCode;
    /**
     * 账户类型
     */
    @ApiModelProperty(value = "账户类型")
    Integer accountType;
    /**
     * 收款账户号码（港币）
     */
    @ApiModelProperty(value = "收款账户号码（港币）")
    String receiptAccountPayeeHkd;
    /**
     * 收款账户号码（港币）备注
     */
    @ApiModelProperty(value = "收款账户号码（港币）备注")
    String receiptAccountPayeeHkdRemark;
    /**
     * 收款账户号码（美元）
     */
    @ApiModelProperty(value = "收款账户号码（美元）")
    String receiptAccountPayeeUsd;
    /**
     * 收款账户号码（美元）备注
     */
    @ApiModelProperty(value = "收款账户号码（美元）备注")
    String receiptAccountPayeeUsdRemark;
    /**
     * 收款户名
     */
    @ApiModelProperty(value = "收款户名")
    String receiptAccountName;
    /**
     * 收款户名备注
     */
    @ApiModelProperty(value = "收款户名备注")
    String receiptAccountNameRemark;
    /**
     * 收款人地址
     */
    @ApiModelProperty(value = "收款人地址")
    String receiptPayeeAddress;
    /**
     * app网银汇款指引（港币）
     */
    @ApiModelProperty(value = "app网银汇款指引（港币）")
    String appWyGuideUrlHkd;
    /**
     * app网银汇款指引（美元）
     */
    @ApiModelProperty(value = "app网银汇款指引（美元）")
    String appWyGuideUrlUsd;
    /**
     * pc网银汇款指引（港币）
     */
    @ApiModelProperty(value = "pc网银汇款指引（港币）")
    String pcWyGuideUrlHkd;
    /**
     * pc网银汇款指引（美元）
     */
    @ApiModelProperty(value = "pc网银汇款指引（美元）")
    String pcWyGuideUrlUsd;
    /**
     * 是否可见
     */
    @ApiModelProperty(value = "是否可见")
    Integer isShow;
    /**
     * 入金凭证
     */
    @ApiModelProperty(value = "入金凭证")
    String depositCertImg;
    /**
     * 最后编辑人
     */
    @ApiModelProperty(value = "最后编辑人")
    String founder;
    /**
     * 接受转账方式1-fps 2-网银 3-支票
     */
    @ApiModelProperty(value = "接受转账方式1-fps 2-网银 3-支票")
    String supportType;
    /**
     * 入金银行卡背景颜色
     */
    @ApiModelProperty(value = "入金银行卡背景颜色")
    String bgColor;
    /**
     * 入金银行卡阴影颜色
     */
    @ApiModelProperty(value = "入金银行卡阴影颜色")
    String shadowColor;
    /**
     * FPS识别码
     */
    @ApiModelProperty(value = "FPS识别码")
    String accountFps;
    /**
     * FPS收款账户号码
     */
    @ApiModelProperty(value = "FPS收款账户号码")
    String receiptBankCodeFps;
    /**
     * FPS收款银行名称
     */
    @ApiModelProperty(value = "FPS收款银行名称")
    String receiptBankNameFps;
    /**
     * FPS银行费用
     */
    @ApiModelProperty(value = "FPS银行费用")
    String chargeMoneyFps;
    /**
     * FPS银行费用备注
     */
    @ApiModelProperty(value = "FPS银行费用备注")
    String chargeMoneyRemarkFps;
    /**
     * FPS到账时间
     */
    @ApiModelProperty(value = "FPS到账时间")
    String timeArrivalFps;
    /**
     * FPS到账时间备注
     */
    @ApiModelProperty(value = "FPS到账时间备注")
    String timeArrivalRemarkFps;
    /**
     * FPS汇款指引app端
     */
    @ApiModelProperty(value = "FPS汇款指引app端")
    String appGuideUrlFps;
    /**
     * FPS汇款指引pc端
     */
    @ApiModelProperty(value = "FPS汇款指引pc端")
    String pcGuideUrlFps;
    /**
     * FPS汇款凭证示例
     */
    @ApiModelProperty(value = "FPS汇款凭证示例")
    String depositCertImgFps;
    /**
     * FPS收款戶名
     */
    @ApiModelProperty(value = "FPS收款戶名")
    String receiptAccountNameFps;
    /**
     * FPS收款银行编码
     */
    @ApiModelProperty(value = "FPS收款银行编码")
    String receiptBankNoFps;
    /**
     * 快捷入金bankId
     */
    @ApiModelProperty(value = "快捷入金bankId")
    String bankIdQuick;
}
