package com.minigod.zero.customer.dto;

import com.minigod.zero.customer.entity.CustomerFundWithdrawRecordsEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 客户出金申请信息表 数据传输对象实体类
 *
 * @author ken
 * @since 2024-04-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CustomerFundWithdrawRecordsDTO extends CustomerFundWithdrawRecordsEntity {
	private static final long serialVersionUID = 1L;

}
