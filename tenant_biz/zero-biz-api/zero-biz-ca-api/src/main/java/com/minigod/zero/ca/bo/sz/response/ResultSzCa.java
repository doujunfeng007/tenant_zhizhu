package com.minigod.zero.ca.bo.sz.response;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @ClassName: SzR
 * @Description: 深圳ca返回
 * @Author chenyu
 * @Date 2022/7/29
 * @Version 1.0
 */
@Data
public class ResultSzCa<T> {
    @JSONField(name = "success")
    private Boolean success;
    @JSONField(name = "code")
    @JsonAlias({"resultcode", "retCode"})
    private Integer code;
    @JsonProperty(value = "msg")
    @JsonAlias({"resultmsg", "descInfo"})
    private String msg;
    @JsonProperty(value = "orderno")
    private String orderno;
    @JsonProperty("obj")
    @JsonAlias({"token", "certDN", "certSn"})
    private T obj;


    @JsonProperty(value = "fileHash")
    private String fileHash;
    @JsonProperty(value = "fileID")
    private String fileID;
    private String file;
}
