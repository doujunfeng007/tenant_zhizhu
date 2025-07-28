package com.minigod.zero.bpm.vo.request;

import lombok.Data;

import java.io.Serializable;

/**
 * @description:
 * @author: sunline
 * @date: 2018/11/21 13:55
 * @version: v1.0
 */
@Data
public class SecDepositBankVo implements Serializable {
    private static final long serialVersionUID = -4309353087605051045L;
    private String capitalAccount; // 资金账号
    private Integer bankCount = 3; // 获取银行卡数量
    private Integer bankType; ////银行卡类型 1:大陆银行卡 2:香港银行卡
}
