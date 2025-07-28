package com.minigod.zero.bpmn.module.withdraw.bo;


import com.minigod.zero.bpmn.module.common.group.AddGroup;
import com.minigod.zero.bpmn.module.common.group.EditGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 海外银行信息记录业务对象 client_bank_info_os
 *
 * @author chenyu
 * @date 2023-04-08
 */

@Data
@ApiModel("海外银行信息记录业务对象")
public class ClientBankInfoOsBo {

    /**
     * 自增ID
     */
    @ApiModelProperty(value = "自增ID", required = true)
    @NotNull(message = "自增ID不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 银行代码
     */
    @ApiModelProperty(value = "银行代码", required = true)
    @NotBlank(message = "银行代码不能为空", groups = { AddGroup.class, EditGroup.class })
    private String bankCode;

    /**
     * 银行名称
     */
    @ApiModelProperty(value = "银行名称", required = true)
    @NotBlank(message = "银行名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String bankName;

    /**
     * 银行名称(英文)
     */
    @ApiModelProperty(value = "银行名称(英文)", required = true)
    @NotBlank(message = "银行名称(英文)不能为空", groups = { AddGroup.class, EditGroup.class })
    private String bankNameEn;

    /**
     * 状态[0-无效 1-有效]
     */
    @ApiModelProperty(value = "状态[0-无效 1-有效]", required = true)
    @NotNull(message = "状态[0-无效 1-有效]不能为空", groups = { AddGroup.class, EditGroup.class })
    private Integer status;

    /**
     * 类型[0-存款 1-取款]
     */
    @ApiModelProperty(value = "类型[0-存款 1-取款]", required = true)
    @NotNull(message = "类型[0-存款 1-取款]不能为空", groups = { AddGroup.class, EditGroup.class })
    private Integer deposit;


}
