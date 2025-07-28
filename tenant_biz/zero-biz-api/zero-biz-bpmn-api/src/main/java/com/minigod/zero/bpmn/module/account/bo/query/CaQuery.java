package com.minigod.zero.bpmn.module.account.bo.query;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName: CaQuery
 * @Description:
 * @Author chenyu
 * @Date 2022/8/4
 * @Version 1.0
 */
@Data
public class CaQuery implements Serializable {
    private static final long serialVersionUID = 6358047798182770731L;

    @ApiModelProperty("用户名称")
    private String clientName;
    @ApiModelProperty("手机号")
    private String phoneNumber;
    @ApiModelProperty("证件号")
    private String idNo;
    @ApiModelProperty("证书申请开始时间")
    private String beginDate;
    @ApiModelProperty("证书申请结束时间")
    private String endDate;
}
