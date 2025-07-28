package com.minigod.zero.bpmn.module.account.ca.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @ClassName: verifyFourBo
 * @Description:
 * @Author chenyu
 * @Date 2022/8/12
 * @Version 1.0
 */
@Data
public class VerifyFourBo {
    @NotBlank(message = "姓名不能为空")
    @ApiModelProperty("姓名")
    private String name;
    @ApiModelProperty("证件号")
    @NotBlank(message = "证件号不能为空")
    private String idNo;
    @ApiModelProperty("银行卡号")
    @NotBlank(message = "银行卡号不能为空")
    private String bankNo;
    @ApiModelProperty("手机号码")
    @NotBlank(message = "手机号码不能为空")
    private String phoneNumber;
    @ApiModelProperty("用户ID")
    @NotNull(message = "用户ID不能为空")
    private Long userId;
}
