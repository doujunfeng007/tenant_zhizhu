package com.minigod.zero.bpm.dto;

import com.minigod.zero.bpm.entity.CashExtractingMoneyEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 提取资金表 数据传输对象实体类
 *
 * @author 掌上智珠
 * @since 2023-05-18
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CashExtractingMoneyDTO extends CashExtractingMoneyEntity {
	private static final long serialVersionUID = 1L;

}
