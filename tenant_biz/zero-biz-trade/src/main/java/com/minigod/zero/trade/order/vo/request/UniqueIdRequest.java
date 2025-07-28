package com.minigod.zero.trade.order.vo.request;

import com.minigod.zero.trade.vo.request.BaseRequest;

import lombok.Data;

/**
 * 获取唯一ID
 */
@Data
public class UniqueIdRequest extends BaseRequest {
    /**
     * 唯一ID类型
     */
    private String type;
}
