package com.minigod.zero.cust.dto;

import com.minigod.zero.cust.entity.CustInfoEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 客户信息表 数据传输对象实体类
 *
 * @author 掌上智珠
 * @since 2023-02-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CustInfoDTO extends CustInfoEntity {
	private static final long serialVersionUID = 1L;

}
