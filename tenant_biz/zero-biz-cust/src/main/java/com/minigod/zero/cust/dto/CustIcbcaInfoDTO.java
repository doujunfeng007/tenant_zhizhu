package com.minigod.zero.cust.dto;

import com.minigod.zero.cust.entity.CustIcbcaInfoEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 工银客户信息表 数据传输对象实体类
 *
 * @author 掌上智珠
 * @since 2023-03-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CustIcbcaInfoDTO extends CustIcbcaInfoEntity {
	private static final long serialVersionUID = 1L;

}
