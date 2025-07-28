package com.minigod.zero.bpmn.module.account.api;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName: RiskDisclosureInfo
 * @Description:
 * @Author chenyu
 * @Date 2022/8/7
 * @Version 1.0
 */
@Data
public class RiskDisclosureInfo implements Serializable {
    private static final long serialVersionUID = 3292358324087632087L;

    @JSONField(name = "riskChecked")
    private Integer riskChecked;
}
