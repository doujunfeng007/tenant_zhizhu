package com.minigod.zero.bpmn.module.withdraw.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * DBS汇款请求日志对象 dbs_remit_req_log
 *
 * @author chenyu
 * @title DbsRemitReqLog
 * @date 2023-04-04 20:50
 * @description
 */
@Data
@TableName("dbs_remit_req_log")
public class DbsRemitReqLog {

    private static final long serialVersionUID=1L;


    /**
     * 自增ID
     */
    @TableId(value = "id")
    private Long id;
    /**
     * 流水号
     */
    private String applicationId;
    /**
     * 请求业务类型：[UN_KNOW-未知 PPP-FPS PPP GCP-FPS GCP ACT-ACT DBS同行转账 RTGS-RTGS 香港本地转账 TT-TT 海外电汇]
     */
    private String txnType;
    /**
     * 请求流水号
     */
    private String msgId;
    /**
     * 银行流水号
     */
    private String txnRefId;

    /**
     * 银行声明
     * 唯一的交易号 TT退款时用到
     */
    private String bankReference;
    /**
     * 交易对账流水号
     */
    private String cusRef;
    /**
     * 申请编号
     */
    private String ddaRef;
    /**
     * 请求状态:0-系统异常；1-成功
     */
    private String reqStatus;
    /**
     * 银行响应状态:ACCT-申请成功；RJCT-失败；ACCP-处理中 ACSP-交易成功 ACWC-交易成功,但付款日期有更改 PDNG-等待
     */
    private String enqStatus;
    /**
     * 响应阶段 ACK1 ACK2 ACK3
     */
    private String enqType;
    /**
     * 拒绝代码
     */
    private String rejCode;
    /**
     * 拒绝原因
     */
    private String rejDescription;
    /**
     * 请求报文
     */
    private String reqMessage;
    /**
     * 请求时间
     */
    private Date reqTime;
    /**
     * ack1响应报文
     */
    private String ack1Message;
    /**
     * ack1响应时间
     */
    private Date ack1Time;
    /**
     * ack2响应报文
     */
    private String ack2Message;
    /**
     * ack2响应时间
     */
    private Date ack2Time;
    /**
     * ack3响应报文
     */
    private String ack3Message;
    /**
     * ack3响应时间
     */
    private Date ack3Time;

    /**
     * 租户ID
     */
    private String tenantId;

    private Date createTime;
    private Date updateTime;
}
