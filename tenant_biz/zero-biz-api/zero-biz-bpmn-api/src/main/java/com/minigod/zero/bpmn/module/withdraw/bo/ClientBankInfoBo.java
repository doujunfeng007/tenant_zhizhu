package com.minigod.zero.bpmn.module.withdraw.bo;


import com.minigod.zero.bpmn.module.account.constants.RegexpConstants;
import com.minigod.zero.bpmn.module.common.group.AddGroup;
import com.minigod.zero.bpmn.module.common.group.EditGroup;
import com.minigod.zero.core.mp.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * 银行信息记录业务对象 client_bank_info
 *
 * @author zsdp
 * @date 2023-03-24
 */

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("银行信息记录业务对象")
public class ClientBankInfoBo extends BaseEntity {

    /**
     * 自增ID
     */
    @ApiModelProperty(value = "自增ID")
    @NotNull(message = "ID流水号不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 银行代码
     */
    @ApiModelProperty(value = "银行代码")
    @NotBlank(message = "银行代码不能为空", groups = { AddGroup.class })
    private String bankCode;

    /**
     * 银行名称
     */
    @ApiModelProperty(value = "银行名称")
    @NotBlank(message = "银行名称不能为空", groups = { AddGroup.class })
    private String bankName;

    /**
     * 银行名称(英文)
     */
    @ApiModelProperty(value = "银行名称(英文)")
    private String bankNameEn;

    /**
     * SWIFT代码
     */
    @ApiModelProperty(value = "SWIFT代码")
    @NotBlank(message = "SWIFT代码不能为空", groups = { AddGroup.class, EditGroup.class })
    @Pattern(regexp = RegexpConstants.REGEX_ENG_NUMBER, message = "SWIFT代码格式错误, 限字母数字", groups = { AddGroup.class, EditGroup.class })
    private String swiftCode;

    /**
     * bank_id
     */
    @ApiModelProperty(value = "bankId")
    @NotBlank(message = "bank_id不能为空", groups = { AddGroup.class, EditGroup.class })
    private String bankId;

    /**
     * 银行地区类型[1-香港本地银行 2-中国大陆银行 3-海外银行]
     */
    @ApiModelProperty(value = "银行地区类型[1-香港本地银行 2-中国大陆银行 3-海外银行]")
    private Integer bankAreaType;

    /**
     * 支持多种取款方式[1-FPSID 2-香港本地银行 2-电汇至中国大陆银行 3 电汇至海外银行]
     */
    @ApiModelProperty(value = "支持多种取款方式[1-FPSID 2-香港本地银行 2-电汇至中国大陆银行 3 电汇至海外银行]")
    private String withdrawType;

    /**
     * 状态[0-无效 1-有效]
     */
    @ApiModelProperty(value = "状态[0-无效 1-有效]")
    private Integer status;

    /**
     * 类型[0-存款 1-取款]
     */
    @ApiModelProperty(value = "类型[0-存款 1-取款]")
    private Integer deposit;



}
