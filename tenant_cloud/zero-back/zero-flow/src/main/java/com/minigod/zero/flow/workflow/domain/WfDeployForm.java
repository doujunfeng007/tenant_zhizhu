package com.minigod.zero.flow.workflow.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.minigod.zero.core.tenant.mp.TenantEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 流程实例关联表单对象 sys_instance_form
 *
 * @author zsdp
 * @createTime 2022/3/7 22:07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("wf_deploy_form")
@ApiModel("部署实例和表单关联")
public class WfDeployForm extends TenantEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 表单主键
     */
	@ApiModelProperty(value = "表单主键")
    private Long formId;

	/**
	 * 表单Key
	 */
	@ApiModelProperty(value = "表单Key")
	private String formKey;

	/**
	 * 节点Key
	 */
	@ApiModelProperty(value = "节点Key")
	private String nodeKey;

	/**
	 * 表单名称
	 */
	@ApiModelProperty(value = "表单名称")
	private String formName;

	/**
	 * 节点名称
	 */
	@ApiModelProperty(value = "节点名称")
	private String nodeName;

	/**
	 * 表单内容
	 */
	@ApiModelProperty(value = "表单内容")
	private String content;
}
