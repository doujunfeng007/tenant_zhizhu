package com.minigod.zero.bpmn.module.account.vo;

import com.minigod.zero.bpmn.module.account.entity.AccountIdentityConfirmModifyEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 身份确认信息修改数据查询视图实体
 *
 * @author eric
 * @since 2024-08-05 13:23:01
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AccountIdentityConfirmModifyVO extends AccountIdentityConfirmModifyEntity {
	private static final long serialVersionUID = 1L;
}
