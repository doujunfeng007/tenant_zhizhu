package com.minigod.zero.bpmn.module.deposit.vo;

import com.minigod.zero.bpmn.module.deposit.entity.BankCardApplication;
import com.minigod.zero.bpmn.module.deposit.enums.DepositStatusEnum;
import com.minigod.zero.core.tool.utils.ObjectUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @ClassName: BankCardApplicationVO
 * @Description:
 * @Author chenyu
 * @Date 2024/3/11
 * @Version 1.0
 */
@Data
@ApiModel("银行卡审核信息")
@Slf4j
public class BankCardApplicationVO extends BankCardApplication {

	private Integer dealPermissions;
	@ApiModelProperty("当前的银行卡信息")
	private BankCardInfoVO beforeInfo;
	@ApiModelProperty("审核的银行卡信息")
	private BankCardReviewInfoVO reviewInfo;
	@ApiModelProperty("银行卡图片信息")
	private List<BankCardImageVO> imageInfos;
	@ApiModelProperty("银行卡审核状态名称")
	private String statusName;
	@ApiModelProperty("流程状态名称")
	private String applicationStatusName;

	public String getApplicationTypeName() {
		if (ObjectUtil.isNotEmpty(this.reviewInfo)) {
			switch (this.reviewInfo.getApplicationType()) {
				case 1:
					return "绑定";
				case 2:
					return "解绑";
				case 3:
					return "修改";
			}
		}
		return "";
	}
}
