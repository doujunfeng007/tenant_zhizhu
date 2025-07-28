package com.minigod.zero.bpmn.module.withdraw.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * DBS汇款请求日志视图对象 dbs_remit_req_log
 *
 * @author chenyu
 * @title DbsRemitReqLogVo
 * @date 2023-04-04 20:51
 * @description
 */
@Data
@ApiModel("DBS汇款请求日志视图对象")
@ExcelIgnoreUnannotated
@ToString
public class DbsRemitReqLogVo  {

    private static final long serialVersionUID = 6840397475620248041L;

    /**
     * 自增ID
     */
    @ExcelProperty(value = "自增ID")
    @ApiModelProperty("自增ID")
    private Long id;

    /**
     * 流水号
     */
    @ExcelProperty(value = "流水号")
    @ApiModelProperty("流水号")
    private String applicationId;

    /**
     * 请求业务类型：[0-未知 1-FPS PPP 2-FPS GCP 3-ACT DBS同行转账 4-RTGS 香港本地转账 5-TT 海外电汇]
     */
    @ExcelProperty(value = "请求业务类型：[UN_KNOW-未知 PPP-FPS PPP GCP-FPS GCP ACT-ACT DBS同行转账 RTGS-RTGS 香港本地转账 TT-TT 海外电汇")
    @ApiModelProperty("请求业务类型：[UN_KNOW-未知 PPP-FPS PPP GCP-FPS GCP ACT-ACT DBS同行转账 RTGS-RTGS 香港本地转账 TT-TT 海外电汇")
    private String txnType;

    /**
     * 请求流水号
     */
    @ExcelProperty(value = "请求流水号")
    @ApiModelProperty("请求流水号")
    private String msgId;

    /**
     * 银行流水号
     */
    @ExcelProperty(value = "银行流水号")
    @ApiModelProperty("银行流水号")
    private String txnRefId;

    /**
     * 交易对账流水号
     */
    @ExcelProperty(value = "交易对账流水号")
    @ApiModelProperty("交易对账流水号")
    private String cusRef;

    /**
     * 申请编号
     */
    @ExcelProperty(value = "申请编号")
    @ApiModelProperty("申请编号")
    private String ddaRef;

    /**
     * 请求状态:0-系统异常；1-成功
     */
    @ExcelProperty(value = "请求状态:0-系统异常；1-成功")
    @ApiModelProperty("请求状态:0-系统异常；1-成功")
    private String reqStatus;

    /**
     * 银行响应状态:ACCT-申请成功；RJCT-失败；ACCP-处理中 ACSP-交易成功 ACWC-交易成功,但付款日期有更改 PDNG-等待
     */
    @ExcelProperty(value = "银行响应状态:ACCT-申请成功；RJCT-失败；ACCP-处理中 ACSP-交易成功 ACWC-交易成功,但付款日期有更改 PDNG-等待")
    @ApiModelProperty("银行响应状态:ACCT-申请成功；RJCT-失败；ACCP-处理中 ACSP-交易成功 ACWC-交易成功,但付款日期有更改 PDNG-等待")
    private String enqStatus;

    /**
     * 响应阶段 ACK1 ACK2 ACK3
     */
    @ExcelProperty(value = "响应阶段 ACK1 ACK2 ACK3")
    @ApiModelProperty("响应阶段 ACK1 ACK2 ACK3")
    private String enqType;

    /**
     * 拒绝代码
     */
    @ExcelProperty(value = "拒绝代码")
    @ApiModelProperty("拒绝代码")
    private String rejCode;

    /**
     * 拒绝原因
     */
    @ExcelProperty(value = "拒绝原因")
    @ApiModelProperty("拒绝原因")
    private String rejDescription;

    /**
     * 请求报文
     */
    @ExcelProperty(value = "请求报文")
    @ApiModelProperty("请求报文")
    private String reqMessage;

    /**
     * 请求时间
     */
    @ExcelProperty(value = "请求时间")
    @ApiModelProperty("请求时间")
    private Date reqTime;

    /**
     * ack1响应报文
     */
    @ExcelProperty(value = "ack1响应报文")
    @ApiModelProperty("ack1响应报文")
    private String ack1Message;

    /**
     * ack1响应时间
     */
    @ExcelProperty(value = "ack1响应时间")
    @ApiModelProperty("ack1响应时间")
    private Date ack1Time;

    /**
     * ack2响应报文
     */
    @ExcelProperty(value = "ack2响应报文")
    @ApiModelProperty("ack2响应报文")
    private String ack2Message;

    /**
     * ack2响应时间
     */
    @ExcelProperty(value = "ack2响应时间")
    @ApiModelProperty("ack2响应时间")
    private Date ack2Time;

    /**
     * ack3响应报文
     */
    @ExcelProperty(value = "ack3响应报文")
    @ApiModelProperty("ack3响应报文")
    private String ack3Message;

    /**
     * ack3响应时间
     */
    @ExcelProperty(value = "ack3响应时间")
    @ApiModelProperty("ack3响应时间")
    private Date ack3Time;

    /** 银行处理状态 */
    private Integer bankState;


    /**
     * 银行参考流水
     */
    private String bankReference;


}
