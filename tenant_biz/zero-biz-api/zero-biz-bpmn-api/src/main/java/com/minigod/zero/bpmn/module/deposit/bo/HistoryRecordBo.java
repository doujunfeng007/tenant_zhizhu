package com.minigod.zero.bpmn.module.deposit.bo;

import lombok.Data;

/**
 * @ClassName: DepositRecordBo
 * @Description:
 * @Author chenyu
 * @Date 2024/2/29
 * @Version 1.0
 */
@Data
public class HistoryRecordBo  {
    private Integer currency;//币种
	/**
	 * sec_deposit_funds为主            状态  0已提交 1已受理 2已退回 3已完成 4已取消
	 * client_edda_fund_application    状态  0未提交 1处理中 2银行处理失败 3银行处理成功，4入账失败  5入账成功
	 *
	 */
    private Integer state;//状态  0已提交 1已受理 2已退回 3已完成 4已取消
    private Integer type;//1:投入 2:提取
}
