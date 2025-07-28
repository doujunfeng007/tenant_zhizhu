package com.minigod.zero.bpmn.module.stock.domain.vo;

import com.minigod.zero.bpmn.module.stock.entity.CustomerAccountStockApplicationEntity;
import com.minigod.zero.bpmn.module.stock.enums.StockStatusEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 股票增开申请流程表 VO
 *
 * @author Administrator
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CustomerAccountStockApplicationVO extends CustomerAccountStockApplicationEntity {
	private Integer dealPermissions;
	private String applicationStatusName;
	private String statusName;
	private CustomerAccountStockInfoVO info;

	public String getApplicationStatusName() {
		return StockStatusEnum.ApplicationStatus.getApplicationStatus(this.getApplicationStatus());
	}

	public String getStatusName() {
		return StockStatusEnum.OpenAccountModifyApproveStatus.getMessage(this.getStatus());
	}

}
