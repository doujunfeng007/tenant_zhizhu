package com.minigod.zero.bpmn.module.account.api;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName: AssetInvestmentInfo
 * @Description:
 * @Author chenyu
 * @Date 2022/8/7
 * @Version 1.0
 */
@Data
public class AssetInvestmentInfo implements Serializable {
    private static final long serialVersionUID = 3292358324087632087L;

    private Integer annualIncome;
    private String annualIncomeOther;
    private Integer netAsset;
    private String netAssetOther;
    private String capitalSource;
    private String capitalSourceOther;
    private String expectedCapitalSource;
    private String expectedCapitalSourceOther;
    private Integer tradeFrequency;
    private Integer tradeAmount;
}

