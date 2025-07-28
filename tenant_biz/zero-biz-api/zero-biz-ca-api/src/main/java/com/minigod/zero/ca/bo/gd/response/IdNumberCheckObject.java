package com.minigod.zero.ca.bo.gd.response;

import lombok.Data;

/**
 * 公安两要素比对结果
 *
 * @author eric
 * @since 2024-05-12 17:56:12
 */
@Data
public class IdNumberCheckObject {
    /**
     * true：一致 false：不一致
     */
    private Boolean validity;
}
