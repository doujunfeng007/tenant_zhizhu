package com.minigod.zero.ca.bo.sz.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @ClassName: asd
 * @Description:
 * @Author chenyu
 * @Date 2022/7/29
 * @Version 1.0
 */
@Data
public class LivingResponse {
    @JsonProperty(value = "confidence")
    private Double confidence;
    @JsonProperty(value = "image_best")
    private String imageBest;
    @JsonProperty(value = "thresholds")
    private ThresholdsResponse thresholds;
}

@Data
class ThresholdsResponse {
    @JsonProperty(value = "1e-3")
    private Double e3;
    @JsonProperty(value = "1e-4")
    private Double e4;
    @JsonProperty(value = "1e-5")
    private Double e5;
    @JsonProperty(value = "1e-6")
    private Double e6;
}
