package com.minigod.zero.bpmn.module.deposit.dto;

import com.minigod.zero.bpmn.module.deposit.entity.SecDepositTypeEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 入金方式管理配置表 数据传输对象实体类
 *
 * @author 掌上智珠
 * @since 2024-03-01
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SecDepositTypeDTO extends SecDepositTypeEntity {
	private static final long serialVersionUID = 1L;

}
