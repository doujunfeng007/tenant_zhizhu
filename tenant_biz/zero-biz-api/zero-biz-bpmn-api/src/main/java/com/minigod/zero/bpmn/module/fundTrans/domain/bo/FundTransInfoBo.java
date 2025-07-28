package com.minigod.zero.bpmn.module.fundTrans.domain.bo;

import com.minigod.zero.bpmn.module.common.group.AddGroup;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @ClassName: FundTransInfoBo
 * @Description:
 * @Author chenyu
 * @Date 2024/12/12
 * @Version 1.0
 */
@Data
public class FundTransInfoBo {
    @ApiModelProperty("入金账号")
    @NotBlank(message = "入金账号不能为空", groups = AddGroup.class)
    private String depositAccount;
    @NotBlank(message = "出金账号不能为空", groups = AddGroup.class)
    @ApiModelProperty("出金账号")
    private String withdrawAccount;
    @NotNull(message = "申请金额不能为空", groups = AddGroup.class)
    @Min(value = 1, message = "申请金额不能小于1", groups = AddGroup.class)
    @ApiModelProperty("申请调拨金额")
    private BigDecimal amount;
    @NotNull(message = "申请币种不能为空", groups = AddGroup.class)
    @ApiModelProperty("币种HKD USD CNY")
    private String currency;
    @NotBlank(message = "入金账户不能为空", groups = AddGroup.class)
    @ApiModelProperty("入金账户类型 STOCK/FUND")
    private String depositBusinessType;
    @NotBlank(message = "出金账户不能为空", groups = AddGroup.class)
    @ApiModelProperty("出金账户类型 STOCK/FUND")
    private String withdrawBusinessType;
    //    private BigDecimal fee;
//    private BigDecimal depositAmount;
//    private BigDecimal withdrawAmount;
    private String tenantId;
    //    @NotNull( message = "用户号不能为空")
    private Long userId;
    private String accountId;

}
