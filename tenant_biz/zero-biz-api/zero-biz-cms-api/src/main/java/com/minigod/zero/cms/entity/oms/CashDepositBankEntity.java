package com.minigod.zero.cms.entity.oms;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 入金银行信息维护表 实体类
 *
 * @author 掌上智珠
 * @since 2023-05-18
 */
@Data
@TableName("cash_deposit_bank")
@ApiModel(value = "CashDepositBank对象", description = "入金银行信息维护表")
public class CashDepositBankEntity implements Serializable {

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
     * 1-大陆银行卡 2-香港银行卡 3-其他地区银行卡
     */
    @ApiModelProperty(value = "1-大陆银行卡 2-香港银行卡 3-其他地区银行卡")
    private Integer bankType;
    /**
     * 汇款银行名称
     */
    @ApiModelProperty(value = "汇款银行名称")
    private String remitBankName;
	/**
	 * 汇款银行名称繁体
	 */
	@ApiModelProperty(value = "汇款银行名称繁体")
	private String remitBankNameHant;
	/**
	 * 汇款银行名称英文
	 */
	@ApiModelProperty(value = "汇款银行名称英文")
	private String remitBankNameEn;
    /**
     * 汇款银行编码
     */
    @ApiModelProperty(value = "汇款银行编码")
    private String remitBankCode;
    /**
     * PC端银行Logo
     */
    @ApiModelProperty(value = "PC端银行Logo")
    private String pcIcon;
    /**
     * APP端银行Logo
     */
    @ApiModelProperty(value = "APP端银行Logo")
    private String appIcon;
    /**
     * 手续费
     */
    @ApiModelProperty(value = "手续费")
    private String chargeMoney;
    /**
     * 到账时间
     */
    @ApiModelProperty(value = "到账时间")
    private String timeArrival;
    /**
     * 到账时间备注
     */
    @ApiModelProperty(value = "到账时间备注")
    private String timeArrivalRemark;
    /**
     * 收款银行名称
     */
    @ApiModelProperty(value = "收款银行名称")
    private String receiptBankName;
    /**
     * 收款银行英文名
     */
    @ApiModelProperty(value = "收款银行英文名")
    private String receiptBankNameSpell;
    /**
     * 收款银行编码
     */
    @ApiModelProperty(value = "收款银行编码")
    private String receiptBankCode;
    /**
     * 收款银行地址
     */
    @ApiModelProperty(value = "收款银行地址")
    private String receiptBankAddress;
    /**
     * SWIFT编码
     */
    @ApiModelProperty(value = "SWIFT编码")
    private String swiftCode;
    /**
     * 账户类型
     */
    @ApiModelProperty(value = "账户类型")
    private Integer accountType;
    /**
     * 收款账户号码（港币）
     */
    @ApiModelProperty(value = "收款账户号码（港币）")
    private String receiptAccountPayeeHkd;
    /**
     * 收款账户号码（港币）备注
     */
    @ApiModelProperty(value = "收款账户号码（港币）备注")
    private String receiptAccountPayeeHkdRemark;
    /**
     * 收款账户号码（美元）
     */
    @ApiModelProperty(value = "收款账户号码（美元）")
    private String receiptAccountPayeeUsd;
    /**
     * 收款账户号码（美元）备注
     */
    @ApiModelProperty(value = "收款账户号码（美元）备注")
    private String receiptAccountPayeeUsdRemark;
    /**
     * 收款户名
     */
    @ApiModelProperty(value = "收款户名")
    private String receiptAccountName;
    /**
     * 收款户名备注
     */
    @ApiModelProperty(value = "收款户名备注")
    private String receiptAccountNameRemark;
    /**
     * 收款人地址
     */
    @ApiModelProperty(value = "收款人地址")
    private String receiptPayeeAddress;
    /**
     * app网银汇款指引（港币）
     */
    @ApiModelProperty(value = "app网银汇款指引（港币）")
    private String appWyGuideUrlHkd;
    /**
     * app网银汇款指引（美元）
     */
    @ApiModelProperty(value = "app网银汇款指引（美元）")
    private String appWyGuideUrlUsd;
    /**
     * pc网银汇款指引（港币）
     */
    @ApiModelProperty(value = "pc网银汇款指引（港币）")
    private String pcWyGuideUrlHkd;
    /**
     * pc网银汇款指引（美元）
     */
    @ApiModelProperty(value = "pc网银汇款指引（美元）")
    private String pcWyGuideUrlUsd;
    /**
     * 是否可见
     */
    @ApiModelProperty(value = "是否可见")
    private Integer isShow;
    /**
     * 入金凭证
     */
    @ApiModelProperty(value = "入金凭证")
    private String depositCertImg;
    /**
     * 最后编辑人
     */
    @ApiModelProperty(value = "最后编辑人")
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
     * 接受转账方式1-fps 2-网银 3-支票
     */
    @ApiModelProperty(value = "接受转账方式1-fps 2-网银 3-支票")
    private String supportType;
    /**
     * 入金银行卡背景颜色
     */
    @ApiModelProperty(value = "入金银行卡背景颜色")
    private String bgColor;
    /**
     * 入金银行卡阴影颜色
     */
    @ApiModelProperty(value = "入金银行卡阴影颜色")
    private String shadowColor;
    /**
     * FPS识别码
     */
    @ApiModelProperty(value = "FPS识别码")
    private String accountFps;
    /**
     * FPS收款账户号码
     */
    @ApiModelProperty(value = "FPS收款账户号码")
    private String receiptBankCodeFps;
    /**
     * FPS收款银行名称
     */
    @ApiModelProperty(value = "FPS收款银行名称")
    private String receiptBankNameFps;
    /**
     * FPS银行费用
     */
    @ApiModelProperty(value = "FPS银行费用")
    private String chargeMoneyFps;
    /**
     * FPS银行费用备注
     */
    @ApiModelProperty(value = "FPS银行费用备注")
    private String chargeMoneyRemarkFps;
    /**
     * FPS到账时间
     */
    @ApiModelProperty(value = "FPS到账时间")
    private String timeArrivalFps;
    /**
     * FPS到账时间备注
     */
    @ApiModelProperty(value = "FPS到账时间备注")
    private String timeArrivalRemarkFps;
    /**
     * FPS汇款指引app端
     */
    @ApiModelProperty(value = "FPS汇款指引app端")
    private String appGuideUrlFps;
    /**
     * FPS汇款指引pc端
     */
    @ApiModelProperty(value = "FPS汇款指引pc端")
    private String pcGuideUrlFps;
    /**
     * FPS汇款凭证示例
     */
    @ApiModelProperty(value = "FPS汇款凭证示例")
    private String depositCertImgFps;
    /**
     * FPS收款戶名
     */
    @ApiModelProperty(value = "FPS收款戶名")
    private String receiptAccountNameFps;
    /**
     * FPS收款银行编码
     */
    @ApiModelProperty(value = "FPS收款银行编码")
    private String receiptBankNoFps;
    /**
     * 快捷入金bankId
     */
    @ApiModelProperty(value = "快捷入金bankId")
    private String bankIdQuick;

}
