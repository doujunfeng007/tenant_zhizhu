package com.minigod.zero.cust.dto;

import com.minigod.zero.cust.entity.CustInvestorStmtEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 投资者声明信息（美股） 数据传输对象实体类
 *
 * @author 掌上智珠
 * @since 2023-02-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CustInvestorStmtDTO extends CustInvestorStmtEntity {
	private static final long serialVersionUID = 1L;

}
