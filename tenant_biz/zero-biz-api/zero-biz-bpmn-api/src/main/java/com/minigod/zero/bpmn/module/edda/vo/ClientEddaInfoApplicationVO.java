package com.minigod.zero.bpmn.module.edda.vo;

import com.minigod.zero.bpmn.module.edda.entity.ClientEddaInfoApplicationEntity;
import lombok.Data;

/**
 * DBS edda授权申请表VO
 *
 * @author eric
 * @since 2024-11-13 16:39:05
 */
@Data
public class ClientEddaInfoApplicationVO extends ClientEddaInfoApplicationEntity {
	/**
	 * 银行类型名称
	 */
	private String bankTypeName;
}
