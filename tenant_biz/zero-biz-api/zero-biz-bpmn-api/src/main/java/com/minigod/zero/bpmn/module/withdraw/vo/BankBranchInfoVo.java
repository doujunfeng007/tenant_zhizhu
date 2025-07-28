package com.minigod.zero.bpmn.module.withdraw.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * 分行信息视图对象 bank_branch_info
 *
 * @author chenyu
 * @date 2023-04-21
 */
@Data
@ApiModel("分行信息视图对象")
@ExcelIgnoreUnannotated
public class BankBranchInfoVo {

    private static final long serialVersionUID = 6840397475620248041L;

    /**
     *
     */
    @ExcelProperty(value = "")
    @ApiModelProperty("")
    private Long id;

    /**
     * 分行编码
     */
    @ExcelProperty(value = "分行编码")
    @ApiModelProperty("分行编码")
    private String branchCode;

    /**
     * 分行名称
     */
    @ExcelProperty(value = "分行名称")
    @ApiModelProperty("分行名称")
    private String branchName;

    /**
     * 地址
     */
    @ExcelProperty(value = "地址")
    @ApiModelProperty("地址")
    private String address;

    /**
     * 状态(1可用 2不可用)
     */
    @ExcelProperty(value = "状态(1可用 2不可用)")
    @ApiModelProperty("状态(1可用 2不可用)")
    private Integer status;


}
