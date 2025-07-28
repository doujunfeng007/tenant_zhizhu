package com.minigod.zero.flow.workflow.domain.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 流程设计业务对象
 *
 * @author zsdp
 * @createTime 2022/3/10 00:12
 */
@Data
@ApiModel("流程设计业务对象")
public class WfDesignerBo {

    /**
     * 流程名称
     */
    @ApiModelProperty(value = "流程名称", required = true)
    private String name;

    /**
     * 流程分类
     */
    @ApiModelProperty(value = "流程分类", required = true)
    private String category;

    /**
     * XML字符串
     */
    @ApiModelProperty(value = "XML字符串", required = true)
    private String xml;
}
