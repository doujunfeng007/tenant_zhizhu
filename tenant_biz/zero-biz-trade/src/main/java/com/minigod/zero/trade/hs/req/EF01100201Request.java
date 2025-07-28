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
public class EF01100201Request extends GrmRequestVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String fundAccount;
    private String password;
    private String newPassword;
    //
    private String clientId;
    private String opStation;

    /**
     * 是否校验登录 存量用户迁移过渡新增
     */
    private Boolean needLogin;

}
