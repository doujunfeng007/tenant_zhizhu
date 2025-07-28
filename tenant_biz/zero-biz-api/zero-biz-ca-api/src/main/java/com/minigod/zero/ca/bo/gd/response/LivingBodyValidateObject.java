package com.minigod.zero.ca.bo.gd.response;

import lombok.Data;

/**
 * 活体公安比对结果
 *
 * @author eric
 * @since 2024-05-12 17:40:09
 */
@Data
public class LivingBodyValidateObject {
    /**
     * 活体认证 ID
     */
    private String authenticationId;
    /**
     * 认证 ID 过期时间
     */
    private String expireDate;
}
