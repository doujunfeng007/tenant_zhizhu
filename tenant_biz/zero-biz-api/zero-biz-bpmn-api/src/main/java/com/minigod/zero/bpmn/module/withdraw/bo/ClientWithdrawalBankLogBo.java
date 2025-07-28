package com.minigod.zero.bpmn.module.withdraw.bo;


import com.minigod.zero.bpmn.module.common.group.AddGroup;
import com.minigod.zero.bpmn.module.common.group.EditGroup;
import com.minigod.zero.core.mp.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * 收款银行登记操作日志业务对象 client_withdrawal_bank_log
 *
 * @author zsdp
 * @date 2023-04-09
 */

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("收款银行登记操作日志业务对象")
public class ClientWithdrawalBankLogBo extends BaseEntity {

    /**
     * 自增ID
     */
    @ApiModelProperty(value = "自增ID", required = true)
    private Long id;

    /**
     * 操作编码
     */
    @ApiModelProperty(value = "操作编码", required = true)
    private String oprcode;

    /**
     * 操作时间
     */
    @ApiModelProperty(value = "操作时间", required = true)
    private Date oprtime;

    /**
     * 操作类型
     */
    @ApiModelProperty(value = "操作类型", required = true)
    private String oprtype;

    /**
     * 渠道[MOBILE-手机 INTERNET-网厅]
     */
    @ApiModelProperty(value = "渠道[MOBILE-手机 INTERNET-网厅]", required = true)
    private String channel;

    /**
     * 文件名
     */
    @ApiModelProperty(value = "文件名", required = true)
    @NotBlank(message = "文件名不能为空", groups = { AddGroup.class, EditGroup.class })
    private String fileName;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注", required = true)
    @NotBlank(message = "备注不能为空", groups = { AddGroup.class, EditGroup.class })
    private String remark;


}
