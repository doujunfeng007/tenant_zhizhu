package com.minigod.zero.flow.workflow.domain.bo;

import com.minigod.zero.core.mp.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 流程分类业务对象
 *
 * @author zsdp
 * @date 2022-01-15
 */

@Data
@ApiModel("流程分类业务对象")
public class WfCategoryBo  {

    /**
     * 分类ID
     */
    @ApiModelProperty(value = "分类ID", required = true)
    private Long id;

    /**
     * 分类名称
     */
    @ApiModelProperty(value = "分类名称", required = true)
    private String categoryName;

    /**
     * 分类编码
     */
    @ApiModelProperty(value = "分类编码", required = true)
    private String code;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注", required = true)
    private String remark;


}
