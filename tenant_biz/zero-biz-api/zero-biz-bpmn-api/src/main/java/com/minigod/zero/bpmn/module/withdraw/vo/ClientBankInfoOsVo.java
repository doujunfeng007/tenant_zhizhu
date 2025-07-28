package com.minigod.zero.bpmn.module.withdraw.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * 海外银行信息记录视图对象 client_bank_info_os
 *
 * @author chenyu
 * @date 2023-04-08
 */
@Data
@ApiModel("海外银行信息记录视图对象")
@ExcelIgnoreUnannotated
public class ClientBankInfoOsVo {

    private static final long serialVersionUID = 6840397475620248041L;

    /**
     * 自增ID
     */
    @ExcelProperty(value = "自增ID")
    @ApiModelProperty("自增ID")
    private Long id;

    /**
     * 银行代码
     */
    @ExcelProperty(value = "银行代码")
    @ApiModelProperty("银行代码")
    private String bankCode;

    /**
     * 银行名称
     */
    @ExcelProperty(value = "银行名称")
    @ApiModelProperty("银行名称")
    private String bankName;

    /**
     * 银行名称(英文)
     */
    @ExcelProperty(value = "银行名称(英文)")
    @ApiModelProperty("银行名称(英文)")
    private String bankNameEn;

    /**
     * 状态[0-无效 1-有效]
     */
    @ExcelProperty(value = "状态[0-无效 1-有效]")
    @ApiModelProperty("状态[0-无效 1-有效]")
    private Integer status;

    /**
     * 类型[0-存款 1-取款]
     */
    @ExcelProperty(value = "类型[0-存款 1-取款]")
    @ApiModelProperty("类型[0-存款 1-取款]")
    private Integer deposit;


}
