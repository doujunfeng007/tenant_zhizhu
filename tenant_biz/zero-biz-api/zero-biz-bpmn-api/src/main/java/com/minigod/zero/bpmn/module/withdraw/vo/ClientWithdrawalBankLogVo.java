package com.minigod.zero.bpmn.module.withdraw.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;


/**
 * 收款银行登记操作日志视图对象 client_withdrawal_bank_log
 *
 * @author zsdp
 * @date 2023-04-09
 */
@Data
@ApiModel("收款银行登记操作日志视图对象")
@ExcelIgnoreUnannotated
public class ClientWithdrawalBankLogVo {

    private static final long serialVersionUID = 6840397475620248041L;

    /**
     * 自增ID
     */
    @ExcelProperty(value = "自增ID")
    @ApiModelProperty("自增ID")
    private Long id;

    /**
     * 操作编码
     */
    @ExcelProperty(value = "操作编码")
    @ApiModelProperty("操作编码")
    private String oprcode;

    /**
     * 操作时间
     */
    @ExcelProperty(value = "操作时间")
    @ApiModelProperty("操作时间")
    private Date oprtime;

    /**
     * 操作类型
     */
    @ExcelProperty(value = "操作类型")
    @ApiModelProperty("操作类型")
    private String oprtype;

    /**
     * 渠道[MOBILE-手机 INTERNET-网厅]
     */
    @ExcelProperty(value = "渠道[MOBILE-手机 INTERNET-网厅]")
    @ApiModelProperty("渠道[MOBILE-手机 INTERNET-网厅]")
    private String channel;

    /**
     * 文件名
     */
    @ExcelProperty(value = "文件名")
    @ApiModelProperty("文件名")
    private String fileName;

    /**
     * 备注
     */
    @ExcelProperty(value = "备注")
    @ApiModelProperty("备注")
    private String remark;


}
