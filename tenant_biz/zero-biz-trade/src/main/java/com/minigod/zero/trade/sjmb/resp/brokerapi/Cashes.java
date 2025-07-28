package com.minigod.zero.trade.sjmb.resp.brokerapi;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;

/**
 * @author chen
 * @ClassName Cashes.java
 * @Description TODO
 * @createTime 2024年01月12日 10:57:00
 */
@Data
public class Cashes implements Serializable {

    private static final long serialVersionUID = 1L;

    @JSONField(name = "CHN")
    private String chn;

    @JSONField(name = "USD")
    private String usd;

    @JSONField(name = "HKD")
    private String hkd;
}
