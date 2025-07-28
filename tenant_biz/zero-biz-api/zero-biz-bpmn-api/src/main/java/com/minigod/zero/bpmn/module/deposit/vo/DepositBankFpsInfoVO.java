package com.minigod.zero.bpmn.module.deposit.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName: com.minigod.zero.bpmn.module.deposit.vo.DepositBankFpsInfoVO
 * @Description: fps收款
 * @Author: linggr
 * @CreateDate: 2024/5/14 11:50
 * @Version: 1.0
 */
@Data
public class DepositBankFpsInfoVO {

	/**
	 * FPS识别码
	 */
	@ApiModelProperty(value = "FPS识别码")
	private String accountFps;
	/**
	 * FPS收款账户号码
	 */
	@ApiModelProperty(value = "FPS收款账户号码")
	private String receiptBankCodeFps;
	/**
	 * FPS收款银行名称
	 */
	@ApiModelProperty(value = "FPS收款银行名称")
	private String receiptBankNameFps;
	/**
	 * FPS银行费用
	 */
	@ApiModelProperty(value = "FPS银行费用")
	private String chargeMoneyFps;
	/**
	 * FPS银行费用备注
	 */
	@ApiModelProperty(value = "FPS银行费用备注")
	private String chargeMoneyRemarkFps;
	/**
	 * FPS到账时间
	 */
	@ApiModelProperty(value = "FPS到账时间")
	private String timeArrivalFps;
	/**
	 * FPS到账时间描述
	 */
	@ApiModelProperty(value = "FPS到账时间描述")
	private String timeArrivalRemarkFps;
	/**
	 * FPS汇款指引app端
	 */
	@ApiModelProperty(value = "FPS汇款指引app端")
	private String appGuideUrlFps;
	/**
	 * FPS汇款指引pc端
	 */
	@ApiModelProperty(value = "FPS汇款指引pc端")
	private String pcGuideUrlFps;
	/**
	 * FPS汇款凭证示例
	 */
	@ApiModelProperty(value = "FPS汇款凭证示例")
	private String depositCertImgFps;
	/**
	 * FPS收款户名
	 */
	@ApiModelProperty(value = "FPS收款户名")
	private String receiptAccountNameFps;
	/**
	 * FPS收款银行编码
	 */
	@ApiModelProperty(value = "FPS收款银行编码")
	private String receiptBankNoFps;
}
