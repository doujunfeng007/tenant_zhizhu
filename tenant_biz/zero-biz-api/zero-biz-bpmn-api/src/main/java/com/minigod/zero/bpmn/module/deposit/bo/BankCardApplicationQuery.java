package com.minigod.zero.bpmn.module.deposit.bo;

import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.util.List;

/**
 * @ClassName: BankCardApplicationQuery
 * @Description:
 * @Author chenyu
 * @Date 2024/3/11
 * @Version 1.0
 */
@Data
public class BankCardApplicationQuery {
    @ApiParam("用户 ID")
    private Long userId;
    @ApiParam("客户账号")
    private String clientId;
    @ApiParam("资金账号")
    private String fundAccount;
    @ApiParam("证券手机号码")
    private String phoneNumber;
    @ApiParam("银行卡类型")
    private Integer bankType;
    @ApiParam("账户类别")
    private Integer bankAccountCategory;
    @ApiParam("审核类型")
    private Integer applicationType;
    @ApiParam("流程审核状态")
    private Integer applicationStatus;
	@ApiParam("银行卡审核状态")
	private Integer status;
    @ApiParam("租户 ID")
    private String tenantId;
    @ApiParam("流水号")
    private String applicationId;
    @ApiParam("审核状态列表")
    private List<Integer> applicationStatusList;
}
