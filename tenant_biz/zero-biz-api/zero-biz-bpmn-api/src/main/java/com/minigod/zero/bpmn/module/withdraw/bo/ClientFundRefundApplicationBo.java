package com.minigod.zero.bpmn.module.withdraw.bo;


import com.minigod.zero.bpmn.module.common.group.AddGroup;
import com.minigod.zero.core.mp.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 客户退款申请流程信息业务对象 client_fund_refund_application
 *
 * @author chenyu
 * @title ClientFundRefundApplicationBo
 * @date 2023-04-04 20:43
 * @description
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("客户退款申请流程信息业务对象")
public class ClientFundRefundApplicationBo extends BaseEntity {

    /**
     * 自增ID
     */
    @ApiModelProperty(value = "自增ID")
    private Long id;

    /**
     * 流水号
     */
    @ApiModelProperty(value = "流水号")
    private String applicationId;

    /**
     * 申请标题
     */
    @ApiModelProperty(value = "申请标题")
    private String applicationTitle;

    /**
     * 取款流水号
     */
    @ApiModelProperty(value = "取款流水号")
    @NotBlank(message = "取款流水号不能为空", groups = { AddGroup.class })
    private String withdrawApplicationId;

    /**
     * 客户帐号
     */
    @ApiModelProperty(value = "客户帐号")
    private String clientId;

    /**
     * 交易帐号
     */
    @ApiModelProperty(value = "交易帐号")
    private String fundAccount;

    /**
     * 币种代码[CNY-人民币 USD-美元 HKD-港币]
     */
    @ApiModelProperty(value = "币种代码[CNY-人民币 USD-美元 HKD-港币]")
    private String ccy;

    /**
     * 退款金额
     */
    @ApiModelProperty(value = "退款金额")
    @NotNull(message = "退款金额不能为空", groups = { AddGroup.class })
    @DecimalMin(value = "0.01", message = "退款金额必须大于0", groups = { AddGroup.class })
    private BigDecimal refundAmount;

    /**
     * 手续费
     */
    @ApiModelProperty(value = "手续费")
    private BigDecimal refundBankFee;

    /**
     * 实际退款金额
     */
    @ApiModelProperty(value = "实际退款金额")
    private BigDecimal netRefundAmount;

    /**
     * 退款方式[0-无手续费 1-有手续费]
     */
    @ApiModelProperty(value = "退款方式[0-无手续费 1-有手续费]")
    private Integer refundType;

    /**
     * 柜台业务处理步骤[10000-资金冻结 10002-资金取出 10001-资金解冻 10003-存入资金]
     */
    @ApiModelProperty(value = "柜台业务处理步骤[10000-资金冻结 10002-资金取出 10001-资金解冻 10003-存入资金]")
    private Integer gtBusinessStep;

    /**
     * 柜台处理状态[0-未知 1-处理成功 2-处理失败 3-待处理]
     */
    @ApiModelProperty(value = "柜台处理状态[0-未知 1-处理成功 2-处理失败]")
    private Integer gtDealStatus;

    /**
     * 柜台处理时间
     */
    @ApiModelProperty(value = "柜台处理时间")
    private Date gtDealDate;

    /**
     * 柜台响应编码
     */
    @ApiModelProperty(value = "柜台响应编码")
    private String gtRtCode;

    /**
     * 柜台响应消息
     */
    @ApiModelProperty(value = "柜台响应消息")
    private String gtMsg;

    /**
     * 退款原因
     */
    @ApiModelProperty(value = "退款原因")
    @NotBlank(message = "退款原因不能为空", groups = { AddGroup.class })
    private String refundReason;

    /**
     * 业务流程状态[1=草稿 2=审批中 3=结束]
     */
    @ApiModelProperty(value = "业务流程状态[1=草稿 2=审批中 3=结束]")
    private Integer status;

    /**
     * 当前节点名称
     */
    @ApiModelProperty(value = "当前节点名称")
    private String currentNode;

    /**
     * 最后审核人
     */
    @ApiModelProperty(value = "最后审核人")
    private String lastApprovalUser;

    /**
     * 最后审核意见
     */
    @ApiModelProperty(value = "最后审核意见")
    private String approvalOpinion;

    /**
     * 最后审核时间
     */
    @ApiModelProperty(value = "最后审核时间")
    private Date approvalTime;

    /**
     * 审核结果回调结果状态[0-未知 1-待处理 2-成功 3-失败 4-处理中 5-未处理]
     */
    @ApiModelProperty(value = "调度柜台操作状态[0-未知 1-待处理 2-成功 3-失败 4-处理中 5-未处理")
    private Integer callbackStatus;

    /**
     * 预约申请状态[0-新建 1-申请退款 2-退款完成 3-已拒绝 4-已取消]
     */
    @ApiModelProperty(value = "预约申请状态[0-新建 1-申请退款 2-退款完成 3-已拒绝 4-已取消]")
    private Integer applicationStatus;

    /**
     * 是否流程退回[0-否 1-是]
     */
    @ApiModelProperty(value = "是否流程退回[0-否 1-是]")
    private Integer isBack;

    /**
     * 指定任务处理人
     */
    @ApiModelProperty(value = "指定任务处理人")
    private String assignDrafter;

    /**
     *
     */
    @ApiModelProperty(value = "")
    private String flowPath;

    /**
     * 加急处理[0-未加急 1-已加急]
     */
    @ApiModelProperty(value = "加急处理[0-未加急 1-已加急]")
    private Integer fireAid;

    /**
     * 流程实例ID
     */
    @ApiModelProperty(value = "流程实例ID")
    private String instanceId;

    /**
     * 流程定义ID
     */
    @ApiModelProperty(value = "流程定义ID")
    private String definitionId;

    /**
     *
     */
    @ApiModelProperty(value = "")
    private String processInstanceId;

    /**
     *
     */
    @ApiModelProperty(value = "")
    private String taskId;

    /**
     *
     */
    @ApiModelProperty(value = "")
    private String deployId;


}
