package com.minigod.zero.cust.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.minigod.zero.core.mp.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 客户行情声明答卷 实体类
 *
 * @author 掌上智珠
 * @since 2023-02-08
 */
@Data
@TableName("cust_investor_answer")
@ApiModel(value = "CustInvestorAnswer对象", description = "客户行情声明答卷")
@EqualsAndHashCode(callSuper = true)
public class CustInvestorAnswerEntity extends BaseEntity {

    /**
     * 问卷索引位置
     */
    @ApiModelProperty(value = "问卷索引位置")
    private Integer answerIndex;
    /**
     * 问卷结果 0表示否,1表示是
     */
    @ApiModelProperty(value = "问卷结果 0表示否,1表示是")
    private Integer answerResult;
    /**
     * 投资者声明ID
     */
    @ApiModelProperty(value = "投资者声明ID")
    private Long investorId;

}
