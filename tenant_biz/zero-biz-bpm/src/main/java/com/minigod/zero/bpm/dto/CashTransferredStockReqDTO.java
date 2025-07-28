package com.minigod.zero.bpm.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class CashTransferredStockReqDTO {

	@ApiModelProperty(value = "关联转入股票表ID")
	private Long stockId;

	@ApiModelProperty(value = "转仓股票配置")
	private List<CashSharesConfigReqDTO> params;

}
