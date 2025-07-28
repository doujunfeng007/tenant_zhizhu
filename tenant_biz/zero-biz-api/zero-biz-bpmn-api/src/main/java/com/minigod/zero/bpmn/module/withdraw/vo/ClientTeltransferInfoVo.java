package com.minigod.zero.bpmn.module.withdraw.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * 客户电汇取款信息视图对象 client_teltransfer_info
 *
 * @author chenyu
 * @date 2023-04-06
 */
@Data
@ApiModel("客户电汇取款信息视图对象")
@ExcelIgnoreUnannotated
public class ClientTeltransferInfoVo  {

    /**
     * 自增ID
     */
    @ExcelProperty(value = "自增ID")
    @ApiModelProperty("自增ID")
    private Long id;

    /**
     * 流水号
     */
    @ExcelProperty(value = "流水号")
    @ApiModelProperty("流水号")
    private String applicationId;

    /**
     * 客户帐号
     */
    @ExcelProperty(value = "客户帐号")
    @ApiModelProperty("客户帐号")
    private String clientId;

    /**
     * 资金帐号
     */
    @ExcelProperty(value = "资金帐号")
    @ApiModelProperty("资金帐号")
    private String fundAccount;

    /**
     * 收款银行代码
     */
    @ExcelProperty(value = "收款银行代码")
    @ApiModelProperty("收款银行代码")
    private String bankCode;

    /**
     * 收款银行名称
     */
    @ExcelProperty(value = "收款银行名称")
    @ApiModelProperty("收款银行名称")
    private String bankName;

    /**
     * 收款银行帐户
     */
    @ExcelProperty(value = "收款银行帐户")
    @ApiModelProperty("收款银行帐户")
    private String bankAcct;

    /**
     * 国际[0-中国大陆 2-海外]
     */
    @ExcelProperty(value = "国际[0-中国大陆 2-海外]")
    @ApiModelProperty("国际[0-中国大陆 2-海外]")
    private String nationality;

    /**
     * 收款银行省份id
     */
    @ExcelProperty(value = "收款银行省份id")
    @ApiModelProperty("收款银行省份id")
    private Long provinceId;

    /**
     * 收款银行省份
     */
    @ExcelProperty(value = "收款银行省份")
    @ApiModelProperty("收款银行省份")
    private String provinceName;

    /**
     * 收款银行城市ID
     */
    @ExcelProperty(value = "收款银行城市ID")
    @ApiModelProperty("收款银行城市ID")
    private Long cityId;

    /**
     * 收款银行城市名称
     */
    @ExcelProperty(value = "收款银行城市名称")
    @ApiModelProperty("收款银行城市名称")
    private String cityName;

    /**
     * 收款SWIFT代码
     */
    @ExcelProperty(value = "收款SWIFT代码")
    @ApiModelProperty("收款SWIFT代码")
    private String swiftCode;

    /**
     * 是否可见[0-否 1-是]
     */
    @ExcelProperty(value = "是否可见[0-否 1-是]")
    @ApiModelProperty("是否可见[0-否 1-是]")
    private String isVisible;

    /**
     * 国家
     */
    @ExcelProperty(value = "国家")
    @ApiModelProperty("国家")
    private String country;

    /**
     * 地址
     */
    @ExcelProperty(value = "地址")
    @ApiModelProperty("地址")
    private String address;

}
