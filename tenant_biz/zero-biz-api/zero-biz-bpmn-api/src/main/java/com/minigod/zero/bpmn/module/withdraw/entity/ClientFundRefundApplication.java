package com.minigod.zero.bpmn.module.withdraw.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 客户退款申请流程信息对象 client_fund_refund_application
 *
 * @author chenyu
 * @title ClientFundRefundApplication
 * @date 2023-04-04 20:42
 * @description
 */
@Data
@TableName("client_fund_refund_application")
public class ClientFundRefundApplication {

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
     * 申请标题
     */
    private String applicationTitle;
    /**
     * 取款流水号
     */
    private String withdrawApplicationId;
    /**
     * 客户帐号
     */
    private String clientId;
    /**
     * 交易帐号
     */
    private String fundAccount;
    /**
     * 币种代码[CNY-人民币 USD-美元 HKD-港币]
     */
    private String ccy;
    /**
     * 退款金额
     */
    private BigDecimal refundAmount;
    /**
     * 手续费
     */
    private BigDecimal refundBankFee;
    /**
     * 退款净金额
     */
    private BigDecimal netRefundAmount;
    /**
     * 退款方式[0-无手续费 1-有手续费]
     */
    private Integer refundType;
    /**
     * 柜台业务处理步骤[10000-资金冻结 10002-资金取出 10001-资金解冻 10003-存入资金]
     */
//    private Integer gtBusinessStep;
//    /**
//     * 柜台处理状态[0-未知 1-处理成功 2-处理失败]
//     */
//    private Integer gtDealStatus;
//    /**
//     * 柜台处理时间
//     */
//    private Date gtDealDate;
//    /**
//     * 柜台响应编码
//     */
//    private String gtRtCode;
//    /**
//     * 柜台响应消息
//     */
//    private String gtMsg;
    /**
     * 退款原因
     */
    private String refundReason;
    /**
     * 业务流程状态[1=草稿 2=审批中 3=结束]
     */
    private Integer status;
    /**
     * 当前节点名称
     */
    private String currentNode;
    /**
     * 最后审核人
     */
    private String lastApprovalUser;
    /**
     * 最后审核意见
     */
    private String approvalOpinion;
    /**
     * 最后审核时间
     */
    private Date approvalTime;
    /**
     * 调度柜台操作[0-未知 1-待处理 2-成功 3-失败 4-处理中 5-未处理]
     */
    private Integer callbackStatus;
    /**
     * 预约申请状态[0-新建 1-申请退款 2-退款完成 3-已拒绝 4-已取消]
     */
    private Integer applicationStatus;
    /**
     * 是否流程退回[0-否 1-是]
     */
    private Integer isBack;
    /**
     * 指定任务处理人
     */
    private String assignDrafter;
    /**
     *
     */
    private String flowPath;
    /**
     * 加急处理[0-未加急 1-已加急]
     */
    private Integer fireAid;
    /**
     * 流程实例ID
     */
    private String instanceId;
    /**
     * 流程定义ID
     */
    private String definitionId;
    /**
     *
     */
    private String processInstanceId;
    /**
     *
     */
    private String taskId;
    /**
     *
     */
    private String deployId;

    private String tenantId;

	//创建人
	private String createUser;
	//修改人
	private String updateUser;
	//创建时间
	private Date createTime;
	//更新时间
	private Date updateTime;
}
