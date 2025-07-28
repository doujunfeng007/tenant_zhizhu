package com.minigod.zero.flow.workflow.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.minigod.zero.core.tenant.mp.TenantEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 流程抄送对象 wf_copy
 *
 * @author zsdp
 * @date 2022-05-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("wf_copy")
@ApiModel("流程抄送对象")
public class WfCopy extends TenantEntity {

    private static final long serialVersionUID=1L;

    /**
     * 抄送标题
     */
	@ApiModelProperty(value = "抄送标题")
    private String title;
    /**
     * 流程主键
     */
	@ApiModelProperty(value = "流程主键")
    private String processId;
    /**
     * 流程名称
     */
	@ApiModelProperty(value = "流程名称")
    private String processName;
    /**
     * 流程分类主键
     */
	@ApiModelProperty(value = "流程分类主键")
    private String categoryId;
    /**
     * 部署主键
     */
	@ApiModelProperty(value = "部署主键")
    private String deploymentId;
    /**
     * 流程实例主键
     */
	@ApiModelProperty(value = "流程实例主键")
    private String instanceId;
    /**
     * 任务主键
     */
	@ApiModelProperty(value = "任务主键")
    private String taskId;
    /**
     * 用户主键
     */
	@ApiModelProperty(value = "用户主键")
    private Long userId;
    /**
     * 发起人Id
     */
	@ApiModelProperty(value = "发起人Id")
    private Long originatorId;
    /**
     * 发起人名称
     */
	@ApiModelProperty(value = "发起人名称")
    private String originatorName;
}
