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
@TableName("cash_deposit_bank_guide")
@ApiModel(value = "CashDepositBankGuide对象", description = "获取银行卡简介信息")
public class CashDepositBankGuideEntity implements Serializable {

	private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private Long id;
    /**
     * 银行ID
     */
    @ApiModelProperty(value = "银行ID")
    private Long depositId;
    /**
     * 转账方式说明
     */
    @ApiModelProperty(value = "转账方式说明")
    private String explains;
    /**
     * 操作说明
     */
    @ApiModelProperty(value = "操作说明")
    private String operation;
	/**
	 * 操作说明繁体
	 */
	@ApiModelProperty(value = "操作说明繁体")
	private String operationHant;
	/**
	 * 操作说明英文
	 */
	@ApiModelProperty(value = "操作说明英文")
	private String operationEn;
    /**
     * 注意事项
     */
    @ApiModelProperty(value = "注意事项")
    private String notes;
	/**
	 * 注意事项繁体
	 */
	@ApiModelProperty(value = "注意事项繁体")
	private String notesHant;
	/**
	 * 注意事项英文
	 */
	@ApiModelProperty(value = "注意事项英文")
	private String notesEn;
    /**
     * 转账方式
     */
    @ApiModelProperty(value = "转账方式")
    private String supportType;

}
