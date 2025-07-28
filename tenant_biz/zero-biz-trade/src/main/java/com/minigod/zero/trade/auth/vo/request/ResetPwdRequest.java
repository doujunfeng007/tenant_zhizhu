package com.minigod.zero.trade.auth.vo.request;

import com.minigod.zero.trade.vo.request.BaseRequest;

import lombok.Data;

/**
 * @description: 重置密码
 * @author: Larry Lai
 * @date: 2020/4/13 15:19
 * @version: v1.0
 */

@Data
public class ResetPwdRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;

    /**
     * 电子邮箱
     */
    private String email;

    /**
     * 手机号码
     */
    private String mobile;

    /**
     * 新密码
     */
    private String newPassword;
}
