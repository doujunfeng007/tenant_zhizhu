package com.minigod.zero.bpmn.module.deposit.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.NullSerializer;
import com.minigod.zero.bpmn.module.bank.entity.DepositBankBillFlow;
import com.minigod.zero.bpmn.module.deposit.entity.FundDepositApplicationEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 客户入金申请表 视图实体类
 *
 * @author taro
 * @since 2024-02-29
 */
@Data
@EqualsAndHashCode(callSuper = true)
@JsonSerialize(nullsUsing = NullSerializer.class)
public class FundDepositApplicationVO extends FundDepositApplicationEntity {
    private List<Integer> applicationStatusList;
    private static final long serialVersionUID = 1L;
    private FundDepositInfoVO info;
    private DepositBankBillFlow flow;
    private List<FundDepositImageVO> imagesList;
    private Integer dealPermissions;
	private String statusName;
}
