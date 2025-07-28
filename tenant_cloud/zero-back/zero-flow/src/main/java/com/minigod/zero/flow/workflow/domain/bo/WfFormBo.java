package com.minigod.zero.flow.workflow.domain.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/**
 * 流程表单业务对象
 *
 * @author zsdp
 * @createTime 2022/3/7 22:07
 */
@Data
@ApiModel("流程表单业务对象")
public class WfFormBo {
    private static final long serialVersionUID = 1L;

    /**
     * 表单主键
     */
    @ApiModelProperty(value = "表单ID", required = true)
    private Long formId;

    /**
     * 表单名称
     */
    @ApiModelProperty(value = "表单名称", required = true)
    private String formName;

    /**
     * 表单内容
     */
    @ApiModelProperty(value = "表单内容", required = true)
    private String content;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注", required = true)
    private String remark;
}
