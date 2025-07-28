package com.minigod.zero.manage.vo.simulatetrade;

import lombok.Data;

import java.io.Serializable;

/**
 * 模拟交易服务api返回结果
 */
@Data
public class SimulateTradeApiResponseVO implements Serializable {
    private Integer code; //返回的状态
    private String message; //返回的消息
    private Object result; //返回的结果
}
