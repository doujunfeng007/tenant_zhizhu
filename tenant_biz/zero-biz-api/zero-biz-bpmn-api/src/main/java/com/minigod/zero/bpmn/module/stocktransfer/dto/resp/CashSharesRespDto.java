package com.minigod.zero.bpmn.module.stocktransfer.dto.resp;

import lombok.Data;

import java.io.Serializable;

@Data
public class CashSharesRespDto implements Serializable {

    private Integer isFind;

    private String sharesCode;

    private String sharesName;

    private Integer sharesNum;

    private Integer sharesType;
}
