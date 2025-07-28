package com.minigod.zero.bpmn.module.withdraw.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 银行信息记录视图对象 client_bank_info
 *
 * @author zsdp
 * @date 2023-03-24
 */
@Data
@ApiModel("银行信息记录视图对象")
@ExcelIgnoreUnannotated
public class ClientBankInfoVo  {

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
     * SWIFT代码
     */
    @ExcelProperty(value = "SWIFT代码")
    @ApiModelProperty("SWIFT代码")
    private String swiftCode;

    /**
     * bank_id
     */
    @ExcelProperty(value = "bank_id")
    @ApiModelProperty("bank_id")
    private String bankId;

    /**
     * 银行地区类型[1-香港本地银行 2-中国大陆银行 3-海外银行]
     */
    @ExcelProperty(value = "银行地区类型[1-香港本地银行 2-中国大陆银行 3-海外银行]")
    @ApiModelProperty("银行地区类型[1-香港本地银行 2-中国大陆银行 3-海外银行]")
    private Integer bankAreaType;

    /**
     * 支持多种取款方式[1-FPSID 2-香港本地银行 2-电汇至中国大陆银行 3 电汇至海外银行]
     */
    @ExcelProperty(value = "支持多种取款方式[1-FPSID 2-香港本地银行 2-电汇至中国大陆银行 3 电汇至海外银行]")
    @ApiModelProperty("支持多种取款方式[1-FPSID 2-香港本地银行 2-电汇至中国大陆银行 3 电汇至海外银行]")
    private String withdrawType;

    /**
     * 状态[0-无效 1-有效]
     */
    @ExcelProperty(value = "状态[0-无效 1-有效]")
    @ApiModelProperty("状态[0-无效 1-有效]")
    private Integer status;

    /**
     * 类型[0-存款 1-取款]
     */
    @ApiModelProperty(value = "类型[0-存款 1-取款]")
    private Integer deposit;


}
