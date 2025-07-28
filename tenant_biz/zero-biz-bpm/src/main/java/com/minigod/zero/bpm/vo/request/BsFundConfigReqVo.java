package com.minigod.zero.bpm.vo.request;

import lombok.Data;

@Data
public class BsFundConfigReqVo {
    //银证转账银行类型[1-CMBC 2-CMB 3-CCB]
    private Integer bsBank;
    //关键词
    private String key;
}
