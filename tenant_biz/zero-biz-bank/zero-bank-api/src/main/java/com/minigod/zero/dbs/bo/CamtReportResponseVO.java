package com.minigod.zero.dbs.bo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;


@Setter
@Getter
public class CamtReportResponseVO implements Serializable {

    // 消息流水号
    private String msgId;
    // 响应状态
    private String enqStatus;
    // 拒绝编码
    private String enqRejectCode;
    // 拒绝描述
    private String enqStatusDescription;
    // 账户号码
    private String accountNo;
    // 账户币种
    private String accountCcy;
    // 业务日期 格式: 20200922
    private String bizDate;
    // 消息类型[CAMT053XML-日报 CAMT052XML:小时报]
    private String messageType;

    // 交易统计信息

    // 交易数量
    private String nbOfNtries;
    // 总交易量
    private String txsSummrySum;
    // 总交易金额
    private String ttlNetNtryAmt;
    // 总交易方向
    private String cdtDbtInd;

    // 交易明细
    private List<CamtItemReportResponseVO> items;

}
