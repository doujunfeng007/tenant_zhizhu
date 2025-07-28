package com.minigod.zero.bpmn.module.account.bo.query;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName: ApplicationQuery
 * @Description:
 * @Author chenyu
 * @Date 2022/7/25
 * @Version 1.0
 */
@Data
public class ApplicationQuery implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("申请记录状态")
    private Integer applicationStatus;

    @ApiModelProperty("流水号")
    private String applicationId;

    @ApiModelProperty("流程节点")
    private String currentNode;

    @ApiModelProperty("客户中文名")
    private String clientName;

    @ApiModelProperty("客户英文名")
    private String clientNameSpell;

    @ApiModelProperty("证件号码")
    private String idNo;

    @ApiModelProperty("手机号")
    private String phoneNumber;

    @ApiModelProperty("开户客户来源渠道ID")
    private String sourceChannelId;

	@ApiModelProperty("开户方式")
	private Integer openAccountAccessWay;

    @ApiModelProperty("申领状态 0 全部 1已申领 2未申领")
    private Integer assignDrafterStatus;

	@ApiModelProperty("当前处理人")
	private String assignDrafter;

    @ApiModelProperty("团队ID")
    private Long deptId;

    @ApiModelProperty("邀请码")
    private List<String> aeCodes;

    @ApiModelProperty("邀请码")
    private List<String> unAeCodes;

    @ApiModelProperty("邀请码")
    private String aeCode;

    @ApiModelProperty("状态列表")
    private List<Integer> applicationStatusList;

    @ApiModelProperty("是否禁止开户")
    private Integer blacklist;

	@ApiModelProperty("客户ID")
    private Long userId;

	@ApiModelProperty("状态")
    private String status;

	@ApiModelProperty("客户ID")
    private String clientId;

	@ApiModelProperty("租户ID")
    private String tenantId;

}
