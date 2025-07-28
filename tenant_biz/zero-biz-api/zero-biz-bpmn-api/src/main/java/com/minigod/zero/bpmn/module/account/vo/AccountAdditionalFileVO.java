package com.minigod.zero.bpmn.module.account.vo;

import com.minigod.zero.bpmn.module.account.entity.AccountAdditionalFileEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *  模型VO
 *
 * @author Chill
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AccountAdditionalFileVO extends AccountAdditionalFileEntity {
	private static final long serialVersionUID = 1L;
	private Integer delPermissions;
	private String remark;

}
