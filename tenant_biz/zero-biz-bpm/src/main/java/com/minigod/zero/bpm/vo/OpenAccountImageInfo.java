package com.minigod.zero.bpm.vo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @author LiYangFeng
 * @createDate 2017/2/22
 * @description
 * @email justbelyf@gmail.com
 */
@Data
public class OpenAccountImageInfo {
    @JSONField(name = "fileKey")
    private String fileKey;

    @JSONField(name = "imageLocation")
    private Integer imageLocation;

    @JSONField(name = "imageLocationType")
    private Integer imageLocationType;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
