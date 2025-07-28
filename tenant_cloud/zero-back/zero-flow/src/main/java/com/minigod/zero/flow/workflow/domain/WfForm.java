package com.minigod.zero.flow.workflow.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.minigod.zero.core.tenant.mp.TenantEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 流程表单对象 wf_form
 *
 * @author zsdp
 * @createTime 2022/3/7 22:07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("wf_form")
@ApiModel("流程表单对象")
public class WfForm extends TenantEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 表单名称
     */
	@ApiModelProperty(value = "表单名称")
    private String formName;

    /**
     * 表单内容
     */
	@ApiModelProperty(value = "表单内容")
    private String content;

    /**
     * 备注
     */
	@ApiModelProperty(value = "备注")
    private String remark;
}
