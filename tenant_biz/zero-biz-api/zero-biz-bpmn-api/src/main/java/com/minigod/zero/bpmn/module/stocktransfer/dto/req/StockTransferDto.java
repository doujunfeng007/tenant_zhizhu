package com.minigod.zero.bpmn.module.stocktransfer.dto.req;

import lombok.Data;

import java.io.Serializable;

@Data
public class StockTransferDto implements Serializable {

    private Long custId;

    private Integer isShares;

    private Integer status;

	private Integer transferType;

}
