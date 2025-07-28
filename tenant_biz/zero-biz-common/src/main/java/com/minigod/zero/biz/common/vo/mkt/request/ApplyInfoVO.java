package com.minigod.zero.biz.common.vo.mkt.request;

import com.minigod.zero.biz.common.mkt.cache.ApplyCommonVO;
import lombok.Data;

/**
 * @description: TODO 新股日历 认购记录
 * @author: sunline
 * @date: 2018/4/8 9:42
 * @version: v1.0
 */
@Data
public class ApplyInfoVO extends ApplyCommonVO {

    private String quantityApply; // 认购股数
    private String applyAmount; // 认购金额
    private String capitalAccount; // 资金账号
    private String applyDate; // 提交认购日期 yyyy-MM-dd
    private String applyTime; // 提交认购时间 yyyy-MM-dd HH:mm:ss
    private String resultDate; // 结果公布日期
    private String endDate; // 截至日期
    private String listingDate; // 上市日期
    private String applyStatus; // 认购状态 0:已提交，1:已受理 2:已拒绝,3:待公布，4:已中签，5:未中签,6：已撤销 7：认购失败,8.排队中,9.未抢到额度
    private String quantityAllotted; // 中签数量
    private String currDate; // 当前日期
    private String handlingCharge; // 手续费
    private String applyAmountTotal; // 总认购金额
    private String type; // 申购类型（0：现金1：融资）
    private String useAmount;// 使用金额
    private String financeAmount;// 融资金额
    private String financeInterest;// 融资利息
    private boolean enableApplyCancel= false;//是否允许撤回
    private String updateTime; // 状态更新日期 认购失败，已撤销等状态更新时间
    private boolean overApplyTime = true;//是否已过认购时间，true是，false否
    private String financeRate;//融资利率
    private Integer shares; //每手股数
    private int isQueue ;

    private String greyTradeDate; // 暗盘交易日期
    private String greyTradeStartTime; // 暗盘交易开始时间
    private String greyTradeEndTime; // 暗盘交易结束时间

	/**
	 * double 最终申购金额 = 中签金额
	 */
	private String finalAmount;

	/**
	 * 中签价格
	 */
	private String finalPrice;

	/**
	 * 应退/应补差额
	 */
	private String diffPrice;
}
