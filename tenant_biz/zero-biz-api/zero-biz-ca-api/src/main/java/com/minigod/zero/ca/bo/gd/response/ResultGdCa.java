package com.minigod.zero.ca.bo.gd.response;

import lombok.Data;

/**
 * @Description: 广东CA响应结果
 * @Author eric
 * @Date 2024-05-12 16:54:12
 */
@Data
public class ResultGdCa<T> {
    /**
     * 状态码，0：成功，其他：根据提示修改
     */
    private String code;
    /**
     * 消息
     */
    private String message;
    /**
     * 时间戳
     */
    private String timestamp;
    /**
     * 返回数据
     */
    private T data;
}
