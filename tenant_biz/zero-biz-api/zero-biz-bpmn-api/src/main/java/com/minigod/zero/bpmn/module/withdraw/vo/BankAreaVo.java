package com.minigod.zero.bpmn.module.withdraw.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * 区域视图对象 bank_area
 *
 * @author chenyu
 * @date 2023-04-20
 */
@Data
@ApiModel("区域视图对象")
@ExcelIgnoreUnannotated
public class BankAreaVo {

    private static final long serialVersionUID = 6840397475620248041L;

    /**
     *
     */
    @ExcelProperty(value = "")
    @ApiModelProperty("")
    private Long id;

    /**
     * 名字
     */
    @ExcelProperty(value = "名字")
    @ApiModelProperty("名字")
    private String name;

    /**
     * 父级编号
     */
    @ExcelProperty(value = "父级编号")
    @ApiModelProperty("父级编号")
    private Long pid;

    /**
     * 区域等级(1-省 2-市 3-区县)
     */
    @ExcelProperty(value = "区域等级(1-省 2-市 3-区县)")
    @ApiModelProperty("区域等级(1-省 2-市 3-区县)")
    private Integer level;

    /**
     * 状态(1可用 2不可用)
     */
    @ExcelProperty(value = "状态(1可用 2不可用)")
    @ApiModelProperty("状态(1可用 2不可用)")
    private Integer status;


}
