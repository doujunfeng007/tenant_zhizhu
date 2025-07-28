package com.minigod.zero.ca.bo.gd.response;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 银行卡信息对比结果
 *
 * @author eric
 * @since 2024-05-12 21:09:12
 */
@Data
public class UnionPayVerifyObject {
    /**
     * 银联认证单号
     */
    @JSONField(name = "bankauthId")
    @JsonProperty(value = "bankauthId")
    private String bankAuthId;

    /**
     * 银联认证单号过期时间
     */
    private String expireDate;
}
