package com.minigod.zero.bpmn.module.withdraw.bo;


import com.minigod.zero.core.mp.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;
import java.util.List;

/**
 * 客户出金申请流程信息业务对象 client_fund_withdraw_application
 * @author chenyu
 * @title ClientFundWithdrawApplicationBo
 * @date 2023-04-04 20:22
 * @description
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString
@ApiModel("客户出金申请流程信息业务对象")
public class ClientFundWithdrawApplicationBo extends BaseEntity {

    /**
     * 自增ID
     */
    @ApiModelProperty(value = "自增ID")
    private Long id;

    /**
     * 流水号
     */
    @ApiModelProperty(value = "流水号")
    //@NotBlank(message = "流水号不能为空", groups = { AddGroup.class, EditGroup.class })
    private String applicationId;

    /**
     * 申请标题
     */
    @ApiModelProperty(value = "申请标题")
    private String applicationTitle;

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
     * 调度柜台操作状态[0-未知 1-待处理 2-成功 3-失败 4-处理中 5-未处理]
     */
    @ApiModelProperty(value = "调度柜台操作状态[0-未知 1-待处理 2-成功 3-失败 4-处理中 5-未处理]")
    private Integer callbackStatus;

    /**
     * 预约申请状态[状态 10审批中 11拒绝 12取消、 20出账中、 21 出账成功、22 出账失败、 30 汇款中 、31 汇款成功、 32 汇款失败 、40 退款中、41 退款成功、 42 退款失败]
     */
    @ApiModelProperty(value = "状态 10审批中 11拒绝 12取消、 20出账中、 21 出账成功、22 出账失败、 30 汇款中 、31 汇款成功、 32 汇款失败 、40 退款中、41 退款成功、 42 退款失败]")
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

    /**
     * #######################################################################
     * ################################扩展字段################################
     * #######################################################################
     */

    @ApiModelProperty("当前节点")
    private String previousNodeIndex;

    @ApiModelProperty("客户姓名")
    private String clientName;

    @ApiModelProperty("申领状态 0-全部 1-已申领 2-未申领")
    private Integer assignStatus;

    @ApiModelProperty("状态列表")
    private List<Integer> applicationStatusList;

    @ApiModelProperty("退款流程状态列表")
    private List<Integer> refundApplicationStatusList;

    @ApiModelProperty("取款方式列表")
    private List<Integer> transferTypeList;

    @ApiModelProperty("流水号列表")
    private List<String> applicationIdList;

    @ApiModelProperty("客户银行卡是否上传[0-否 1-是]")
    private Integer cardImgFlag;

    @ApiModelProperty("提交开始时间")
    private String beginCreateTime;

    @ApiModelProperty("提交结束时间")
    private String endCreateTime;

    /** 非后台录单 */
    private Integer nonBackend;

    /** 非香港银行 电汇到海外银行 */
    private Integer nonTransfer;

    /** 受理单限制 取款类型不能为 1-电汇到大陆海外 同时来源不能为 后台申请 */
    private Integer acceptType;

    /** 退款审核 1-是 */
    private Integer refundAuditFlag;

    @ApiModelProperty("受理截止时间类型 [1-3:00]")
    private Integer deadlineType;
    @ApiModelProperty("受理截止时间")
    private String deadlineTime;

    @ApiModelProperty("客户取款信息")
    private ClientFundWithdrawInfoBo info = new ClientFundWithdrawInfoBo();

    @ApiModelProperty("客户退款信息")
    private ClientFundRefundApplicationBo refund = new ClientFundRefundApplicationBo();

    private String tenantId;
	/**
	 * 查询参数字段
	 */
	private String keyword;

	private String startTime;

	private String endTime;

}
