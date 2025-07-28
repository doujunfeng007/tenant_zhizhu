package com.minigod.zero.flow.workflow.domain.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 流程业务对象
 *
 * @author zsdp
 * @createTime 2022/6/11 01:15
 */
@Data
@ApiModel("流程业务对象")
public class WfProcessBo {

    @ApiModelProperty("流程名称")
    private String processName;
    @ApiModelProperty("流水号")
    private String applicationId;
}
