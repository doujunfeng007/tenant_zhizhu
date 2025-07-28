package com.minigod.zero.cms.vo;

import lombok.Data;

/**
 * @ClassName: PayeeBankInfoListVo
 * @Description: 获取收款银行信息
 * @Author chenyu
 * @Date 2021/7/5
 * @Version 1.0
 */
@Data
public class PayeeBankInfoReq {
	// 币种：HKD USD CNY
    private Integer currency;
	// 入金银行ID
    private Long depositId;
	// 汇款方式:1-FPS 2-网银转账 3-支票转账 4-客户快捷入金
    private Integer supportType;
	// 银行卡类型：1-大陆银行卡 2-香港银行卡 3-其他地区银行卡
    private Integer bankType;
}
