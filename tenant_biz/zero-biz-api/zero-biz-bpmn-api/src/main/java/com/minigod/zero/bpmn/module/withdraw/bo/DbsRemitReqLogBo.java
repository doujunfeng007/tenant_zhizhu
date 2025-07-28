package com.minigod.zero.bpmn.module.withdraw.bo;


import com.minigod.zero.core.mp.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * DBS汇款请求日志业务对象 dbs_remit_req_log
 *
 * @author chenyu
 * @title DbsRemitReqLogBo
 * @date 2023-04-04 20:51
 * @description
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("DBS汇款请求日志业务对象")
public class DbsRemitReqLogBo extends BaseEntity {
    /**
     * 自增ID
     */
    @ApiModelProperty(value = "自增ID", required = true)
    private Long id;

    /**
     * 流水号
     */
    @ApiModelProperty(value = "流水号", required = true)
    private String applicationId;

    /**
     * 请求业务类型：[UN_KNOW-未知 PPP-FPS PPP GCP-FPS GCP ACT-ACT DBS同行转账 RTGS-RTGS 香港本地转账 TT-TT 海外电汇
     */
    @ApiModelProperty(value = "请求业务类型：[UN_KNOW-未知 PPP-FPS PPP GCP-FPS GCP ACT-ACT DBS同行转账 RTGS-RTGS 香港本地转账 TT-TT 海外电汇", required = true)
    private String txnType;

    /**
     * 请求流水号
     */
    @ApiModelProperty(value = "请求流水号", required = true)
    private String msgId;

    /**
     * 银行流水号
     */
    @ApiModelProperty(value = "银行流水号", required = true)
    private String txnRefId;

    /**
     * 银行声明
     * 交易的唯一标识
     */
    @ApiModelProperty(value = "银行声明", required = true)
    private String bankReference;

    /**
     * 交易对账流水号
     */
    @ApiModelProperty(value = "交易对账流水号", required = true)
    private String cusRef;

    /**
     * 申请编号
     */
    @ApiModelProperty(value = "申请编号", required = true)
    private String ddaRef;

    /**
     * 请求状态:0-系统异常；1-成功
     */
    @ApiModelProperty(value = "请求状态:0-系统异常；1-成功", required = true)
    private String reqStatus;

    /**
     * 银行响应状态:ACCT-申请成功；RJCT-失败；ACCP-处理中 ACSP-交易成功 ACWC-交易成功,但付款日期有更改 PDNG-等待
     */
    @ApiModelProperty(value = "银行响应状态:ACCT-申请成功；RJCT-失败；ACCP-处理中 ACSP-交易成功 ACWC-交易成功,但付款日期有更改 PDNG-等待", required = true)
    private String enqStatus;

    /**
     * 响应阶段 ACK1 ACK2 ACK3
     */
    @ApiModelProperty(value = "响应阶段 ACK1 ACK2 ACK3", required = true)
    private String enqType;

    /**
     * 拒绝代码`
     */
    @ApiModelProperty(value = "拒绝代码", required = true)
    private String rejCode;

    /**
     * 拒绝原因
     */
    @ApiModelProperty(value = "拒绝原因", required = true)
    private String rejDescription;

    /**
     * 请求报文
     */
    @ApiModelProperty(value = "请求报文", required = true)
    private String reqMessage;

    /**
     * 请求时间
     */
    @ApiModelProperty(value = "请求时间", required = true)
    private Date reqTime;

    /**
     * ack1响应报文
     */
    @ApiModelProperty(value = "ack1响应报文", required = true)
    private String ack1Message;

    /**
     * ack1响应时间
     */
    @ApiModelProperty(value = "ack1响应时间", required = true)
    private Date ack1Time;

    /**
     * ack2响应报文
     */
    @ApiModelProperty(value = "ack2响应报文", required = true)
    private String ack2Message;

    /**
     * ack2响应时间
     */
    @ApiModelProperty(value = "ack2响应时间", required = true)
    private Date ack2Time;

    /**
     * ack3响应报文
     */
    @ApiModelProperty(value = "ack3响应报文", required = true)
    private String ack3Message;

    /**
     * ack3响应时间
     */
    @ApiModelProperty(value = "ack3响应时间", required = true)
    private Date ack3Time;

    /**
     * 租户 ID
     */
    private String tenantId;

}
