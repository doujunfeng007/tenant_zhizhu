package com.minigod.zero.trade.hs.req;

import com.minigod.zero.trade.hs.vo.GrmRequestVO;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by sunline
 * Date:4/9/16
 * Time:10:12 PM
 * 取客户帐号列表
 */
@Data
public class EF01100808Request extends GrmRequestVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String clientId;
}
