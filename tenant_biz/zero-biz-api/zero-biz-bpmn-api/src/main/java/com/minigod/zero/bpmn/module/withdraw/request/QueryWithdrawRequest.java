package com.minigod.zero.bpmn.module.withdraw.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @ClassName: QueryWithdrawRequest
 * @Description:
 * @Author chenyu
 * @Date 2024/4/1
 * @Version 1.0
 */
@Data
public class QueryWithdrawRequest {
    private Integer status;
	@ApiModelProperty(value = "币种代码[CNY-人民币 USD-美元 HKD-港币]")
	private Integer currency;

	private Integer size;
    //@NotBlank(message = "开始时间不能为空")
    private String beginDate;
    //@NotBlank(message = "结束时间不能为空")
    private String endDate;
}
