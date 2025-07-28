package com.minigod.zero.cust.dto;

import com.minigod.zero.cust.entity.CustSerialNumberEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 序列号管理 数据传输对象实体类
 *
 * @author 掌上智珠
 * @since 2023-03-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CustSerialNumberDTO extends CustSerialNumberEntity {
	private static final long serialVersionUID = 1L;

}
