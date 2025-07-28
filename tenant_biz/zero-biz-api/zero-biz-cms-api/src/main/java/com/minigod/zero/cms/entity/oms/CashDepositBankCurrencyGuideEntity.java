package com.minigod.zero.cms.entity.oms;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 获取银行卡简介信息 实体类
 *
 * @author 掌上智珠
 * @since 2023-05-18
 */
@Data
@TableName("cash_deposit_bank_currency_guide")
@ApiModel(value = "CashDepositBankCurrencyGuide对象", description = "获取银行卡简介信息")
public class CashDepositBankCurrencyGuideEntity implements Serializable {

	private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private Long id;
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
     * 入金银行卡配置表id
     */
    @ApiModelProperty(value = "入金银行卡配置表id")
    private Long depositId;
    /**
     * 币种
     */
    @ApiModelProperty(value = "币种")
    private Integer currency;
    /**
     * 转账类型 1-FPS转速快 2-网银转账 4-快捷转账 5-银证转账
     */
    @ApiModelProperty(value = "转账类型 1-FPS转速快 2-网银转账 4-快捷转账 5-银证转账")
    private String supportType;
    /**
     * 示例（上传图片）
     */
    @ApiModelProperty(value = "示例（上传图片）")
    private String example;

}
