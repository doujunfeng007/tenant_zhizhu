package com.minigod.zero.bpmn.module.account.entity;

import javax.validation.constraints.Size;

import java.io.Serializable;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;
import com.minigod.zero.core.tenant.mp.TenantEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

/**
* 客户开户投资知识问卷表
* @TableName customer_invest_knowledge_questionnaire
*/
@Data
@TableName("customer_invest_knowledge_questionnaire")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "InvestKnowledgeQuestionnaire对象", description = "客户开户投资知识问卷表")
public class CustomerInvestKnowledgeQuestionnaire extends TenantEntity implements Serializable {

	private static final long serialVersionUID = 1L;
    /**
    * 预约流水号
    */
    @ApiModelProperty("预约流水号")
    private String applicationId;
    /**
    * 用户ID
    */
    @ApiModelProperty("用户ID")
    private Long custId;
    /**
    * 问卷选项内容
    */
    @ApiModelProperty("问卷选项内容")
    private String optionData;

}
