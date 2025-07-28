package com.minigod.zero.trade.auth.vo.request;

import com.minigod.zero.trade.vo.request.BaseRequest;

import lombok.Data;

/**
 * 指纹解锁
 */

@Data
public class SwitchTokenRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;

    /**
     * 1 指纹， 2 面容
     */
    private int type = 1;
    /**
     * 1 启用，0 禁用
     */
    private int switchFlag = 1;
}
