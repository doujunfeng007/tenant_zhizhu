package com.minigod.zero.cust.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class ConfigTemp implements Serializable {
    private Integer keyName;
    private String keyValue;
}
