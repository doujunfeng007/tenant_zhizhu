package com.minigod.zero.ca.bo.sz.request;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @ClassName: Sign
 * @Description:
 * @Author chenyu
 * @Date 2022/7/30
 * @Version 1.0
 */
@Data
public class Sign {
    private String certDn;
    private String certSn;
    private String fileID;
    @JSONField(name = "P1SignData")
    private String P1SignData;
}
