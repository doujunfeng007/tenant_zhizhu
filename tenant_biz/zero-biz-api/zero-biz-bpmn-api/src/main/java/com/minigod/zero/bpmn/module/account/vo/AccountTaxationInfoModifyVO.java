package com.minigod.zero.bpmn.module.account.vo;

import com.minigod.zero.bpmn.module.account.entity.AccountTaxationInfoModifyEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 税务信息修改信息视图实体
 *
 * @author eric
 * @since 2024-08-05 15:19:21
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AccountTaxationInfoModifyVO extends AccountTaxationInfoModifyEntity {
	private static final long serialVersionUID = 1L;
}
