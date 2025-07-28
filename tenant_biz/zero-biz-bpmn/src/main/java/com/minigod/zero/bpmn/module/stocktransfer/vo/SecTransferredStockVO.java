package com.minigod.zero.bpmn.module.stocktransfer.vo;

import com.minigod.zero.bpmn.module.common.entity.FileUploadInfoEntity;
import com.minigod.zero.bpmn.module.stocktransfer.entity.SecSharesInfoEntity;
import com.minigod.zero.bpmn.module.stocktransfer.entity.SecTransferredStockEntity;
import com.minigod.zero.bpmn.module.stocktransfer.enums.SecTransferredStockStatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author chen
 * @ClassName SecTransferredStockVO.java
 * @Description TODO
 * @createTime 2024年03月07日 15:14:00
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "SecTransferredStockVO对象", description = "SecTransferredStockVO对象")
public class SecTransferredStockVO extends SecTransferredStockEntity {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "股票信息")
	private List<SecSharesInfoEntity> sharesData;

	@ApiModelProperty(value = "附件列表")
	private List<FileUploadInfoEntity> fileList;

	@ApiModelProperty(value = "状态名称")
	private String statusName;

	/**
	 * 状态名称
	 *
	 * @return
	 */
	public String getStatusName() {
		return SecTransferredStockStatusEnum.fromStatus(this.getStatus()).getDescription();
	}

	/**
	 * 转入状态名称
	 */
	@ApiModelProperty(value = "转入状态")
	private String transferStateName;

	public String getTransferStateName() {
		return SecTransferredStockStatusEnum.fromStatus(this.getTransferState()).getDescription();
	}

	/**
	 * 转入股票名称
	 */
	@ApiModelProperty(value = "转入股票名称")
	private String isSharesName;

	public String getIsSharesName() {
		switch (this.getIsShares()) {
			case 1:
				return "港股";
			case 2:
				return "美股";
			default:
				return "未知";
		}
	}

	/**
	 * 1-转入记录 2-转出记录名称
	 */
	@ApiModelProperty(value = "1-转入记录 2-转出记录")
	private String regulationTypeName;

	public String getRegulationTypeName() {
		switch (this.getRegulationType()) {
			case 1:
				return "转入记录";
			case 2:
				return "转出记录";
			default:
				return "未知";
		}
	}
}
