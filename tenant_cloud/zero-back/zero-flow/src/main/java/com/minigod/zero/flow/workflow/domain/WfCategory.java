package com.minigod.zero.flow.workflow.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.minigod.zero.core.tenant.mp.TenantEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 流程分类对象 wf_category
 *
 * @author zsdp
 * @date 2022-01-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("wf_category")
@ApiModel("流程分类对象")
public class WfCategory  extends TenantEntity {

    private static final long serialVersionUID=1L;

    /**
     * 分类名称
     */
	@ApiModelProperty(value = "分类名称")
    private String categoryName;
    /**
     * 分类编码
     */
	@ApiModelProperty(value = "分类编码")
    private String code;
    /**
     * 备注
     */
	@ApiModelProperty(value = "备注")
    private String remark;
}
