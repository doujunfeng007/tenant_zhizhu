package com.minigod.zero.bpm.vo;

import com.minigod.zero.bpm.entity.BpmSecuritiesInfoEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

/**
 * 证券客户资料表 视图实体类
 *
 * @author 掌上智珠
 * @since 2023-05-18
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BpmSecuritiesInfoVO extends BpmSecuritiesInfoEntity {
	private static final long serialVersionUID = 1L;

	// 开户开始时间
	private Date openAccountStartTime;
	// 开户结束时间
	private Date openAccountEndTime;
	// 用户号（批量查询）
	private List<String> batchCustIdList;
//	// 交易帐号（批量查询）
//	private List<String> batchTradeAccountList;

}
