package com.minigod.zero.bpm.dto;

import com.minigod.zero.bpm.entity.CashEddaInfoEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * edda申请流水表 数据传输对象实体类
 *
 * @author 掌上智珠
 * @since 2023-05-18
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CashEddaInfoDTO extends CashEddaInfoEntity {
	private static final long serialVersionUID = 1L;

}
