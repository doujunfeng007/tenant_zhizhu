package com.minigod.zero.cust.vo.icbc;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author:yanghu.luo
 * @create: 2023-03-08 14:19
 * @Description: 串流信息
 */
@Data
public class Stream implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 申请串流首月支付金额: "HKD 280.00" or "HKD 0.00"
	 */
	@ApiModelProperty(value = "申请串流首月支付金额: \"HKD 280.00\" or \"HKD 0.00\"")
	private String firstamount;

	/**
	 * 0: 文件已经上传，不需要提示  1: 文件未上传，需要提示
	 */
	@ApiModelProperty(value = "0: 文件已经上传，不需要提示  1: 文件未上传，需要提示")
	private String prompt;

	/**
	 * 写死的  HKD 200.00
	 */
	@ApiModelProperty(value = "HKD 200.00")
	private String amount;
}
