package com.minigod.zero.biz.task.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class BpmAccountInfoVo implements Serializable {
    private Long custId;
    private String custName;
}
