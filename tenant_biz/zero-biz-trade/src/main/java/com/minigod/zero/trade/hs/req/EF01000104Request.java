package com.minigod.zero.trade.hs.req;

import com.minigod.zero.trade.hs.vo.GrmRequestVO;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by sunline
 * Date:4/9/16
 * Time:10:12 PM
 * 所属营业部查询请求对象
 */
@Data
public class EF01000104Request extends GrmRequestVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String opStation;
    private String clientId;

}
