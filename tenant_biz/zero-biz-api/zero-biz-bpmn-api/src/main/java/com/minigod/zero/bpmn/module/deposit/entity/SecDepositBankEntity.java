package com.minigod.zero.bpmn.module.deposit.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 入金银行配置表 实体类
 *
 * @author 掌上智珠
 * @since 2024-03-01
 */
@Data
@TableName("sec_deposit_bank")
@ApiModel(value = "SecDepositBank对象", description = "入金银行配置表")
public class SecDepositBankEntity implements Serializable {

	private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private Long id;
    /**
     * 排序
     */
    @ApiModelProperty(value = "排序")
    private Integer sort;
    /**
     * 银行类型
     */
    @ApiModelProperty(value = "银行类型")
    private Integer bankType;
    /**
     * 币种
     */
    @ApiModelProperty(value = "币种")
    private Integer moneyType;
    /**
     * 汇款银行名称
     */
    @ApiModelProperty(value = "汇款银行名称")
    private String remitBankName;
    /**
     * 汇款银行编号
     */
    @ApiModelProperty(value = "汇款银行编号")
    private String remitBankCode;
    /**
     * pc端图标
     */
    @ApiModelProperty(value = "pc端图标")
    private String pcIcon;
    /**
     * app端图标
     */
    @ApiModelProperty(value = "app端图标")
    private String appIcon;
    /**
     * 收费
     */
    @ApiModelProperty(value = "收费")
    private String chargeMoney;
    /**
     * 到账时间
     */
    @ApiModelProperty(value = "到账时间")
    private String timeArrival;
    /**
     * 到账时间描述
     */
    @ApiModelProperty(value = "到账时间描述")
    private String timeArrivalRemark;
    /**
     * 收款银行名称
     */
    @ApiModelProperty(value = "收款银行名称")
    private String receiptBankName;
    /**
     *  收款银行名称拼音
     */
    @ApiModelProperty(value = " 收款银行名称拼音")
    private String receiptBankNameSpell;
    /**
     * 收款银行编号
     */
    @ApiModelProperty(value = "收款银行编号")
    private String receiptBankCode;
    /**
     * 收款银行地址
     */
    @ApiModelProperty(value = "收款银行地址")
    private String receiptBankAddress;
    /**
     *  swift code
     */
    @ApiModelProperty(value = " swift code")
    private String swiftCode;
    /**
     * 账户类型
     */
    @ApiModelProperty(value = "账户类型")
    private Integer accountType;
    /**
     * 港币收款账户
     */
    @ApiModelProperty(value = "港币收款账户")
    private String receiptAccountPayeeHkd;
    /**
     * 港币收款账户描述
     */
    @ApiModelProperty(value = "港币收款账户描述")
    private String receiptAccountPayeeHkdRemark;
    /**
     * 美元收款账户
     */
    @ApiModelProperty(value = "美元收款账户")
    private String receiptAccountPayeeUsd;
    /**
     * 美元收款账户描述
     */
    @ApiModelProperty(value = "美元收款账户描述")
    private String receiptAccountPayeeUsdRemark;
    /**
     * 收款账户名称
     */
    @ApiModelProperty(value = "收款账户名称")
    private String receiptAccountName;
    /**
     * 收款账户名称描述
     */
    @ApiModelProperty(value = "收款账户名称描述")
    private String receiptAccountNameRemark;
    /**
     * 收款人地址
     */
    @ApiModelProperty(value = "收款人地址")
    private String receiptPayeeAddress;
    /**
     *
     */
    @ApiModelProperty(value = "")
    private String appWyGuideUrlHkd;
    /**
     *
     */
    @ApiModelProperty(value = "")
    private String appWyGuideUrlUsd;
    /**
     *
     */
    @ApiModelProperty(value = "")
    private String pcWyGuideUrlHkd;
    /**
     *
     */
    @ApiModelProperty(value = "")
    private String pcWyGuideUrlUsd;
    /**
     * 是否显示
     */
    @ApiModelProperty(value = "是否显示")
    private Integer isShow;
    /**
     *
     */
    @ApiModelProperty(value = "")
    private String depositCertImg;
    /**
     *
     */
    @ApiModelProperty(value = "")
    private String founder;
    /**
     *
     */
    @ApiModelProperty(value = "")
    private Date createTime;
    /**
     *
     */
    @ApiModelProperty(value = "")
    private Date updateTime;
    /**
     * 接受转账方式1-fps 2-网银 3-支票
     */
    @ApiModelProperty(value = "接受转账方式1-fps 2-网银 3-支票 4 edda")
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
     * FPS到账时间描述
     */
    @ApiModelProperty(value = "FPS到账时间描述")
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
     * FPS收款户名
     */
    @ApiModelProperty(value = "FPS收款户名")
    private String receiptAccountNameFps;
    /**
     * FPS收款银行编码
     */
    @ApiModelProperty(value = "FPS收款银行编码")
    private String receiptBankNoFps;
    /**
     * 快捷入金bankId 分行值,用逗号分隔
     */
    @ApiModelProperty(value = "快捷入金bankId")
    private String bankIdQuick;
    /**
     * 收款银行名称(银证)
     */
    @ApiModelProperty(value = "收款银行名称(银证)")
    private String receiptBankNameSec;
    /**
     * 收款银行名称英文(银证)
     */
    @ApiModelProperty(value = "收款银行名称英文(银证)")
    private String receiptBankNameSpellSec;
    /**
     * 收款银行代码(银证)
     */
    @ApiModelProperty(value = "收款银行代码(银证)")
    private String receiptBankIdSec;
    /**
     * 收款银行编码(银证)
     */
    @ApiModelProperty(value = "收款银行编码(银证)")
    private String receiptBankCodeSec;
    /**
     * 收款银行地址(银证)
     */
    @ApiModelProperty(value = "收款银行地址(银证)")
    private String receiptBankAddressSec;
    /**
     * SWIFT代码(银证)
     */
    @ApiModelProperty(value = "SWIFT代码(银证)")
    private String swiftCodeSec;
    /**
     * 收款账户号码(银证)
     */
    @ApiModelProperty(value = "收款账户号码(银证)")
    private String receiptAccountNoSec;
    /**
     * 收款账户名称(银证)
     */
    @ApiModelProperty(value = "收款账户名称(银证)")
    private String receiptAccountNameSec;
    /**
     * 收款人地址(银证)
     */
    @ApiModelProperty(value = "收款人地址(银证)")
    private String receiptPayeeAddressSec;
    /**
     * app银证汇款指引(银证)
     */
    @ApiModelProperty(value = "app银证汇款指引(银证)")
    private String appWyGuideUrlSec;
    /**
     * 租户 ID
     */
    @ApiModelProperty(value = "租户 ID")
    private String tenantId;


    /**
     * 单笔限额
     */
	//单笔限额
	@ApiModelProperty(value = "单笔限额")
	private BigDecimal maxAmt;

}
