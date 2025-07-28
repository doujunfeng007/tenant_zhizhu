package com.minigod.zero.bpm.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

/**
 * 风险评测题库-选项 实体类
 *
 * @author 掌上智珠
 * @since 2023-05-18
 */
@Data
@TableName("acct_risk_question_option")
@ApiModel(value = "AcctRiskQuestionOption对象", description = "风险评测题库-选项")
public class AcctRiskQuestionOptionEntity implements Serializable {

	private static final long serialVersionUID = 1L;

    /**
     *
     */
    @ApiModelProperty(value = "")
    private Long id;
    /**
     * 问题ID
     */
    @ApiModelProperty(value = "问题ID")
    private Long questionId;
    /**
     * 问题顺序
     */
    @ApiModelProperty(value = "问题顺序")
    private Integer sort;
    /**
     * 选项内容
     */
    @ApiModelProperty(value = "选项内容")
    private String optionValue;
    /**
     * 多语言标识
     */
    @ApiModelProperty(value = "多语言标识")
    private String lang;
    /**
     * 选项分值
     */
    @ApiModelProperty(value = "选项分值")
    private Integer optionScore;
    /**
     * 选项ID
     */
    @ApiModelProperty(value = "选项ID")
    private Integer optionId;
    /**
     * 0.有效，1.失效
     */
    @ApiModelProperty(value = "0.有效，1.失效")
    private Integer flag;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

}
