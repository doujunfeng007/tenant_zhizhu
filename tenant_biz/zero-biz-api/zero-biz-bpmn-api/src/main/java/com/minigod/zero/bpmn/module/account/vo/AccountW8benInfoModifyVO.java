package com.minigod.zero.bpmn.module.account.vo;

import com.minigod.zero.bpmn.module.account.entity.AccountW8benInfoModifyEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * W-8BEN表格美国税务局表修改信息视图实体
 *
 * @author eric
 * @since 2024-08-05 15:14:01
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AccountW8benInfoModifyVO extends AccountW8benInfoModifyEntity {
	private static final long serialVersionUID = 1L;
}
