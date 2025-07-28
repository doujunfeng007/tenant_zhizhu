package com.minigod.zero.flow.core.entity;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @ClassName: FlowTable
 * @Description:
 * @Author chenyu
 * @Date 2024/2/2
 * @Version 1.0
 */
@Data
public class FlowTable implements Serializable {
    @NotBlank
    private String currentNode;
    private Integer applicationStatus;
    private String flowPath;
    @NotBlank
    private String tableName;
    @NotBlank
    private String applicationId;
    private String deployId;
    private String taskId;
    private String processInstanceId;
    private String definitionId;
    private String instanceId;
    private String assignDrafter;
    private String tenantId;
}
