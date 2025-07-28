package com.minigod.zero.bpm.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

/**
 * @author zxw
 * @date 2023-10-07 10:42
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PhillipProtocolSaveDTO {
    /**
     * 0-拒绝，1-同意
     */
    @NotEmpty
    String isAgree;
}
