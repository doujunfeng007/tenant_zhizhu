package com.minigod.zero.bpmn.module.withdraw.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.minigod.zero.bpmn.module.withdraw.enums.BpmCommonEnum;
import com.minigod.zero.core.mp.base.BaseEntity;
import com.minigod.zero.core.tool.utils.StringUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 客户出金申请流程信息视图对象 client_fund_withdraw_application
 * @author chenyu
 * @title ClientFundWithdrawApplicationVo
 * @date 2023-04-04 20:21
 * @description
 */
@Data
@ApiModel("客户出金申请流程信息视图对象")
@ExcelIgnoreUnannotated
public class ClientFundWithdrawApplicationExeVo extends BaseEntity {



    /**
     * 流水号
     */
    @ExcelProperty(value = "流水号")
    @ApiModelProperty("流水号")
    private String applicationId;

    /**
     * 申请标题
     */
    @ExcelProperty(value = "申请标题")
    @ApiModelProperty("申请标题")
    private String applicationTitle;

    /**
     * 业务流程状态[1=草稿 2=审批中 3=结束]
     */
    @ExcelProperty(value = "业务流程状态[1=草稿 2=审批中 3=结束]")
    @ApiModelProperty("业务流程状态[1=草稿 2=审批中 3=结束]")
    private Integer status;

    /**
     * 当前节点名称
     */
    @ExcelProperty(value = "当前节点名称")
    @ApiModelProperty("当前节点名称")
    private String currentNode;

    /**
     * 最后审核人
     */
    @ExcelProperty(value = "最后审核人")
    @ApiModelProperty("最后审核人")
    private String lastApprovalUser;

    /**
     * 最后审核意见
     */
    @ExcelProperty(value = "最后审核意见")
    @ApiModelProperty("最后审核意见")
    private String approvalOpinion;

    /**
     * 最后审核时间
     */
    @ExcelProperty(value = "最后审核时间")
    @ApiModelProperty("最后审核时间")
    private Date approvalTime;

    /**
     * 调度柜台操作状态[0-未知 1-待处理 2-成功 3-失败 4-处理中 5-未处理]
     */
    @ExcelProperty(value = "调度柜台操作状态[0-未知 1-待处理 2-成功 3-失败 4-处理中 5-未处理]")
    @ApiModelProperty("调度柜台操作状态[0-未知 1-待处理 2-成功 3-失败 4-处理中 5-未处理]")
    private Integer callbackStatus;

    /**
     * 预约申请状态[0-内部拒绝 1-已提交 2-已受理 3-已退回  4-已完成 5-已取消 6-已拒绝 7-已审核 8-申请退款 9-退款完成 13-已复核 11-DBS打款中 12-DBS打款失败]
     */
    @ExcelProperty(value = "预约申请状态[0-内部拒绝 1-已提交 2-已受理 3-已退回  4-已完成 5-已取消 6-已拒绝 7-已审核 8-申请退款 9-退款完成 13-已复核 11-DBS打款中 12-DBS打款失败]")
    @ApiModelProperty("预约申请状态[0-内部拒绝 1-已提交 2-已受理 3-已退回  4-已完成 5-已取消 6-已拒绝 7-已审核 8-申请退款 9-退款完成 13-已复核 11-DBS打款中 12-DBS打款失败]")
    private Integer applicationStatus;

    /**
     * 是否流程退回[0-否 1-是]
     */
    @ExcelProperty(value = "是否流程退回[0-否 1-是]")
    @ApiModelProperty("是否流程退回[0-否 1-是]")
    private Integer isBack;

    /**
     * 指定任务处理人
     */
    @ExcelProperty(value = "指定任务处理人")
    @ApiModelProperty("指定任务处理人")
    private String assignDrafter;

    /**
     *
     */
    @ExcelProperty(value = "")
    @ApiModelProperty("")
    private String flowPath;

    /**
     * 加急处理[0-未加急 1-已加急]
     */
    @ExcelProperty(value = "加急处理[0-未加急 1-已加急]")
    @ApiModelProperty("加急处理[0-未加急 1-已加急]")
    private Integer fireAid;

    /**
     * 流程实例ID
     */
    @ExcelProperty(value = "流程实例ID")
    @ApiModelProperty("流程实例ID")
    private String instanceId;

    /**
     * 流程定义ID
     */
    @ExcelProperty(value = "流程定义ID")
    @ApiModelProperty("流程定义ID")
    private String definitionId;

    /**
     *
     */
    @ExcelProperty(value = "")
    @ApiModelProperty("")
    private String processInstanceId;

    /**
     *
     */
    @ExcelProperty(value = "")
    @ApiModelProperty("")
    private String taskId;

    /**
     *
     */
    @ExcelProperty(value = "")
    @ApiModelProperty("")
    private String deployId;

    /**
     * #######################################################################
     * ################################扩展字段################################
     * #######################################################################
     */

    @ApiModelProperty("客户取款信息")
    private ClientFundWithdrawInfoVo info;

    /**
     *  海外汇款信息
     */
    @ApiModelProperty(value = "海外汇款信息")
    private ClientTeltransferInfoVo telegram;

    /**
     *  海外汇款信息
     */
    @ApiModelProperty(value = "退款信息")
    private ClientFundRefundApplicationVo refund;

    @ApiModelProperty("审核人")
    private String nickName;

    /** 状态名称 */
    private Integer applicationStatusName;
    public String getApplicationStatusName(){
        if(getApplicationStatus() != null){
            BpmCommonEnum.FundWithdrawStatus fundWithdrawStatus = BpmCommonEnum.FundWithdrawStatus.valueOf(this.getStatus());
            if(fundWithdrawStatus != null){
                return fundWithdrawStatus.getDesc();
            }
        }
        return null;
    }



    /**
     *  柜台状态名称
     */
    private String gtDealStatusName;
    public String getGtDealStatusName(){
//        StringBuffer buffer = new StringBuffer();
//        if(refund != null && refund.getGtBusinessStep() != null){
//            buffer.append(SystemCommonEnum.FundWithDrawStep.valueOf(refund.getGtBusinessStep()).getDesc());
//            if(refund.getGtDealStatus() != null){
//                buffer.append("-").append(SystemCommonEnum.CommonProcessResultStatus.valueOf(refund.getGtDealStatus()).getDesc());
//                return buffer.toString();
//            }
//        }else if(info != null && info.getGtBusinessStep() != null){
//            buffer.append(SystemCommonEnum.FundWithDrawStep.valueOf(info.getGtBusinessStep()).getDesc());
//            if(info.getGtDealStatus() != null){
//                buffer.append("-").append(SystemCommonEnum.CommonProcessResultStatus.valueOf(info.getGtDealStatus()).getDesc());
//                return buffer.toString();
//            }
//        }
        return null;
    }

    /**
     * 柜台响应编码
     */
    private String gtRtCode;
    public String getGtRtCode(){
        StringBuffer buffer = new StringBuffer();
        if(info != null && StringUtil.isNotBlank(info.getGtRtCode())){
            buffer.append("取款:").append(info.getGtRtCode()).append(" ");
        }
        if(refund != null && StringUtil.isNotBlank(refund.getGtRtCode())){
            buffer.append("退款:").append(refund.getGtRtCode());
        }
        return buffer.toString();
    }

    /**
     * 柜台响应消息
     */
    private String gtMsg;
    public String getGtMsg(){
        StringBuffer buffer = new StringBuffer();
        if(info != null && StringUtil.isNotBlank(info.getGtMsg())){
            buffer.append("取款:").append(info.getGtMsg()).append(" ");
        }
        if(refund != null && StringUtil.isNotBlank(refund.getGtMsg())){
            buffer.append("退款:").append(refund.getGtMsg());
        }
        return buffer.toString();
    }

}
