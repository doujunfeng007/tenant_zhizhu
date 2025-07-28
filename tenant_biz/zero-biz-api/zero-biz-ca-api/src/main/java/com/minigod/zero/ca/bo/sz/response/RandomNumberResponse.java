package com.minigod.zero.ca.bo.sz.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @ClassName: RandomNumberResponse
 * @Description:
 * @Author chenyu
 * @Date 2022/7/29
 * @Version 1.0
 */
@Data
public class RandomNumberResponse {

    @JsonProperty(value = "random_number")
    private String randomNumber;
}
