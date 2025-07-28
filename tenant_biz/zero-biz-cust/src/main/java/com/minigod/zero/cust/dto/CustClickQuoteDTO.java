package com.minigod.zero.cust.dto;

import com.minigod.zero.cust.entity.CustClickQuoteEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 点击报价权限表 数据传输对象实体类
 *
 * @author 掌上智珠
 * @since 2023-03-27
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CustClickQuoteDTO extends CustClickQuoteEntity {
	private static final long serialVersionUID = 1L;

}
