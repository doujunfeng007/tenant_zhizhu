package com.minigod.zero.dbs.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @description: 账户余额请求实体
 * @author: chenyu
 * @date: 2021/04/21 10:11
 * @version: v1.0
 */
@ApiModel("账户余额请求实体")
@Data
public class AccountBalanceRequestBo extends DbsBaseRequestVO implements Serializable {

    // 消息流水号
    @ApiModelProperty(value = "消息唯一流水号", required = true)
    @NotBlank(message = "消息唯一流水号不能为空")
    private String msgId;
    // 银行账号
    @ApiModelProperty(value = "银行账号", required = true)
    @NotBlank(message = "银行账号不能为空")
    private String accountNo;
    // 银行账号
    @ApiModelProperty(value = "账户币种 HKD:港币 CNY:人民币 USD:美元", required = true)
    @NotBlank(message = "账户币种不能为空")
    private String accountCcy;

}
