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
 * 风险评测题库 实体类
 *
 * @author 掌上智珠
 * @since 2023-05-18
 */
@Data
@TableName("acct_risk_question")
@ApiModel(value = "AcctRiskQuestion对象", description = "风险评测题库")
public class AcctRiskQuestionEntity implements Serializable {

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
    private Integer questionId;
    /**
     * 评测问题
     */
    @ApiModelProperty(value = "评测问题")
    private String question;
    /**
     * 问题顺序
     */
    @ApiModelProperty(value = "问题顺序")
    private Integer sort;
    /**
     * 多语言标识
     */
    @ApiModelProperty(value = "多语言标识")
    private String lang;
    /**
     * 1.个人户题库，2.公司户题库
     */
    @ApiModelProperty(value = "1.个人户题库，2.公司户题库，3.PI认证户题库")
    private Integer questionType;
    /**
     * 1.主观题,2.客观题
     */
    @ApiModelProperty(value = "1.主观题,2.客观题")
    private Integer optType;
    /**
     * 1.单选题,2.复选题
     */
    @ApiModelProperty(value = "1.单选题,2.复选题")
    private Integer checkType;
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
