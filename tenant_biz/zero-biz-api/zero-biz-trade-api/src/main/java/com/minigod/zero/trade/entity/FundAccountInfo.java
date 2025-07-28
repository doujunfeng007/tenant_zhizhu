package com.minigod.zero.trade.entity;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by sunline on 2016/5/7 15:47.
 * sunline
 */
@Data
public class FundAccountInfo implements Serializable{
    private static final long serialVersionUID = 1L;

	@JSONField(name="fund_account")
    private String fundAccount;
	@JSONField(name="asset_prop")
	private String assetProp;
	@JSONField(name="restriction")
	private String restriction;
	@JSONField(name="main_flag")
	private String mainFlag;
	@JSONField(name="branch_no")
    private String branchNo;
	@JSONField(name="organ_flag")
	private String organFlag;
}
