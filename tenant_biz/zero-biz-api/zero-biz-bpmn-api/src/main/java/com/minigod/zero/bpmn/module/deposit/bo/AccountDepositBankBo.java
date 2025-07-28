package com.minigod.zero.bpmn.module.deposit.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName: SecDepositBankBo
 * @Description:
 * @Author chenyu
 * @Date 2024/3/1
 * @Version 1.0
 */
@Data
public class AccountDepositBankBo {
    private Integer bankCount = 5; // 获取银行卡数量
    private String clientId; // 资金账号
    @ApiModelProperty(value = "银行卡类型[1-香港本地银行 2-中国大陆银行 3-海外银行]")
    private Integer bankType;
    @ApiModelProperty(value = "银行卡[0-综合账户 1-港币 2-美元 3-人民币]")
    private Integer bankAccountCategory;
}
