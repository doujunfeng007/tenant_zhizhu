package com.minigod.zero.bpmn.module.withdraw.bo;


import com.minigod.zero.bpmn.module.common.group.AddGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 客户电汇取款信息业务对象 client_teltransfer_info
 *
 * @author chenyu
 * @date 2023-04-06
 */

@Data
@ApiModel("客户电汇取款信息业务对象")
public class ClientTeltransferInfoBo{

    /**
     * 自增ID
     */
    @ApiModelProperty(value = "自增ID")
    private Long id;

    /**
     * 流水号
     */
    @ApiModelProperty(value = "流水号")
    private String applicationId;

    /**
     * 客户帐号
     */
    @ApiModelProperty(value = "客户帐号")
    private String clientId;

    /**
     * 资金帐号
     */
    @ApiModelProperty(value = "资金帐号")
    private String fundAccount;

    /**
     * 收款银行代码
     */
    @ApiModelProperty(value = "收款银行代码")
    private String bankCode;

    /**
     * 收款银行名称
     */
    @ApiModelProperty(value = "收款银行名称")
    private String bankName;

    /**
     * 收款银行帐户
     */
    @ApiModelProperty(value = "收款银行帐户")
    private String bankAcct;

    /**
     * 国际[0-中国大陆 2-海外]
     */
    @ApiModelProperty(value = "国际[0-中国大陆 2-海外]")
    @NotBlank(message = "国际[0-中国大陆 2-海外]不能为空", groups = {AddGroup.class})
    private String nationality;

    /**
     * 收款银行省份id
     */
    @ApiModelProperty(value = "收款银行省份id")
    @NotNull(message = "收款银行省份id不能为空", groups = {AddGroup.class})
    private Long provinceId;

    /**
     * 收款银行省份
     */
    @ApiModelProperty(value = "收款银行省份")
    @NotBlank(message = "收款银行省份不能为空", groups = {AddGroup.class})
    private String provinceName;

    /**
     * 收款银行城市ID
     */
    @ApiModelProperty(value = "收款银行城市ID")
    @NotNull(message = "收款银行城市ID不能为空", groups = {AddGroup.class})
    private Long cityId;

    /**
     * 收款银行城市名称
     */
    @ApiModelProperty(value = "收款银行城市名称")
    @NotBlank(message = "收款银行城市名称不能为空", groups = {AddGroup.class})
    private String cityName;

    /**
     * 收款SWIFT代码
     */
    @ApiModelProperty(value = "收款SWIFT代码")
    private String swiftCode;

    /**
     * 是否可见[0-否 1-是]
     */
    @ApiModelProperty(value = "是否可见[0-否 1-是]")
    private String isVisible;

    /**
     * 国家
     */
    @ApiModelProperty(value = "国家")
    @NotBlank(message = "国家不能为空", groups = {AddGroup.class})
    private String country;

    /**
     * 地址
     */
    @ApiModelProperty(value = "地址")
    private String address;


    private String tenantId;


}
