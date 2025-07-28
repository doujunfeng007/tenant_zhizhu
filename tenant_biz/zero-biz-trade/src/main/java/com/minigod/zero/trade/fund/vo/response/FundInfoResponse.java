package com.minigod.zero.trade.fund.vo.response;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 获取资产汇总信息和持仓列表的返回结果
 */
@Data
@Builder
public class FundInfoResponse implements Serializable {
    /**
     * 资金统计信息
     */
    private TotalAccountResponse fundStats;
    /**
     * 持仓列表
     */
    private List<UserPortfolioResponse> positions;
}
