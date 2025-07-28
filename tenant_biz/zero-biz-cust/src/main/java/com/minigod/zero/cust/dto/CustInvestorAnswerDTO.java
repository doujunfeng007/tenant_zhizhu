package com.minigod.zero.cust.dto;

import com.minigod.zero.cust.entity.CustInvestorAnswerEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 客户行情声明答卷 数据传输对象实体类
 *
 * @author 掌上智珠
 * @since 2023-02-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CustInvestorAnswerDTO extends CustInvestorAnswerEntity {
	private static final long serialVersionUID = 1L;

}
