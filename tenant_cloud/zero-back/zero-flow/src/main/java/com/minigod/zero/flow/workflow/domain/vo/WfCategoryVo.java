package com.minigod.zero.flow.workflow.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * 流程分类视图对象 flow_category
 *
 * @author zsdp
 * @date 2022-01-15
 */
@Data
@ApiModel("流程分类视图对象")
public class WfCategoryVo {

    private static final long serialVersionUID = 1L;

    /**
     * 分类ID
     */
    @ApiModelProperty("分类ID")
    private Long id;

    /**
     * 分类名称
     */
    @ApiModelProperty("分类名称")
    private String categoryName;

    /**
     * 分类编码
     */
    //@ExcelProperty(value = "分类编码")
    @ApiModelProperty("分类编码")
    private String code;

    /**
     * 备注
     */
    //@ExcelProperty(value = "备注")
    @ApiModelProperty("备注")
    private String remark;


}
