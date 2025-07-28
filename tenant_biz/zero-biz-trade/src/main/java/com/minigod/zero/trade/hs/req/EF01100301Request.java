package com.minigod.zero.trade.hs.req;

import com.minigod.zero.trade.hs.vo.GrmRequestVO;
import lombok.Data;

import java.io.Serializable;

/**
 * 取最大可买卖数量
 * Created by sunline
 * Date:4/9/16
 * Time:10:12 PM
 * 下单
 */
@Data
public class EF01100301Request extends GrmRequestVO implements Serializable {
    private static final long serialVersionUID = 1L;
    private String fundAccount;
    private String password;
    private String exchangeType;
    private String stockCode;
    private String entrustPrice;
    private String opStation;
    private String entrustProp;
    private String clientId;
}
