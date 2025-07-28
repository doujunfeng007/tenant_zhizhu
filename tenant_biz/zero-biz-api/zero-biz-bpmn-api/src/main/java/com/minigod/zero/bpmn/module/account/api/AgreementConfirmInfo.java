package com.minigod.zero.bpmn.module.account.api;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName: AgreementConfirmInfo
 * @Description:
 * @Author chenyu
 * @Date 2022/8/7
 * @Version 1.0
 */
@Data
public class AgreementConfirmInfo implements Serializable {
    private static final long serialVersionUID = 3292358324087632087L;

    private Integer agreementChecked;
}
