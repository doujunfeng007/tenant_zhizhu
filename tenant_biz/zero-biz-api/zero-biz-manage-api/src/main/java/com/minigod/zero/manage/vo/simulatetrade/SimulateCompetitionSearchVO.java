package com.minigod.zero.manage.vo.simulatetrade;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 模拟比赛搜索参数
 */
@Data
public class SimulateCompetitionSearchVO implements Serializable {
    /**
     * 模拟比赛ID
     */
    private Long competitionId;
	/**
	 * 比赛名称
	 */
	private String competitionName;
    /**
     * 开始时间
     */
    private String startTime;
    /**
     * 结束时间
     */
    private String endTime;
    /**
     * 客户帐号
     */
    private String tradeAccount;
    /**
     * 用户ID
     */
    private Integer userId;
    /**
     * 股票代码
     */
    private String stockCode;
    /**
     * 市场类型: hk / us
     */
    private String market;
    /**
     * 交易类型：1:买入，2:卖出，3:拆分（买入，后台手工上传），4:派股（买入，后台手工上传）
     */
    private Integer orderAction;
    /**
     * 订单状态：1未成，2已成 3已撤
     */
    private Integer orderState;
    private Integer status;
	/**
	 * 比赛ID集合
	 */
	private List<Long> ids;
	private String tenantId;
	private String assetId;
	/**
	 * 搜索条件
	 */
	private String searchCondition;
	private List<Long> userIds;
}
