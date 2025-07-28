package com.minigod.zero.bpmn.module.deposit.dto;

import com.minigod.zero.bpmn.module.deposit.entity.FundDepositInfoEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 客户入金申请信息表 数据传输对象实体类
 *
 * @author taro
 * @since 2024-02-29
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class FundDepositInfoDTO extends FundDepositInfoEntity {
	private static final long serialVersionUID = 1L;

}
