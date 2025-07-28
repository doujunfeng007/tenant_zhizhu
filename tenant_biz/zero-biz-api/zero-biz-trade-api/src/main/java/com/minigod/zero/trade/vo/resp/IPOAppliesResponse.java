package com.minigod.zero.trade.vo.resp;


import java.util.List;

/**
 * 认购列表
 * @author sunline
 *
 */
public class IPOAppliesResponse {
	private List<IPOApplyDetails> applies;

    public List<IPOApplyDetails> getApplies() {
        return applies;
    }

    public void setApplies(List<IPOApplyDetails> applies) {
        this.applies = applies;
    }
}
