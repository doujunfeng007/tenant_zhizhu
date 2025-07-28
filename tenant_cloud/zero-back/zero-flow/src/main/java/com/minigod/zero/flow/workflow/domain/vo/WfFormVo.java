package com.minigod.zero.flow.workflow.domain.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zsdp
 * @createTime 2022/3/7 22:07
 */
@Data
@ApiModel("流程分类视图对象")
public class WfFormVo {

    private static final long serialVersionUID = 1L;

    /**
     * 表单主键
     */
    @ApiModelProperty("表单ID")
    private Long formId;

    /**
     * 表单名称
     */
    @ApiModelProperty("表单名称")
    private String formName;

    /**
     * 表单内容
     */
    @ApiModelProperty("表单内容")
    private String content;

    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String remark;
}
