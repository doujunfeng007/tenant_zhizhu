package com.minigod.zero.bpm.vo;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * @author zxw
 * @date 2023-10-07 10:59
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PhillipProtocolVo {
    /**
     * 客户id
     */
    Long custId;
    /**
     * 0-拒绝，1-同意
     */
    String isAgree;

}
