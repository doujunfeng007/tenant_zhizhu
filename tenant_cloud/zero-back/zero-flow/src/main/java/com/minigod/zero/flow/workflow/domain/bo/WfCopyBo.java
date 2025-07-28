package com.minigod.zero.flow.workflow.domain.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 流程抄送业务对象 wf_copy
 *
 * @author zsdp
 * @date 2022-05-19
 */

@Data
@ApiModel("流程抄送业务对象")
public class WfCopyBo  {

    /**
     * 抄送主键
     */
    @ApiModelProperty(value = "抄送主键", required = true)
    private Long copyId;

    /**
     * 抄送标题
     */
    @ApiModelProperty(value = "抄送标题", required = true)
    private String title;

    /**
     * 流程主键
     */
    @ApiModelProperty(value = "流程主键", required = true)
    private String processId;

    /**
     * 流程名称
     */
    @ApiModelProperty(value = "流程名称", required = true)
    private String processName;

    /**
     * 流程分类主键
     */
    @ApiModelProperty(value = "流程分类主键", required = true)
    private String categoryId;

    /**
     * 任务主键
     */
    @ApiModelProperty(value = "任务主键", required = true)
    private String taskId;

    /**
     * 用户主键
     */
    @ApiModelProperty(value = "用户主键", required = true)
    private Long userId;

    /**
     * 发起人Id
     */
    @ApiModelProperty(value = "发起人主键", required = true)
    private Long originatorId;
    /**
     * 发起人名称
     */
    @ApiModelProperty(value = "发起人名称", required = true)
    private String originatorName;
}
