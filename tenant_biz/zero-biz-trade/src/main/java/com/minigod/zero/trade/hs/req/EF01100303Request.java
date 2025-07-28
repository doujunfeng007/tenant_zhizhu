package com.minigod.zero.trade.hs.req;

import com.minigod.zero.trade.hs.vo.GrmRequestVO;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by sunline
 * Date:4/9/16
 * Time:10:12 PM
 * 查当日可撤改委托
 */
@Data
public class EF01100303Request extends GrmRequestVO implements Serializable {
    private static final long serialVersionUID = 1L;
    private String fundAccount;
    private String password;
    private String exchangeType;
}
