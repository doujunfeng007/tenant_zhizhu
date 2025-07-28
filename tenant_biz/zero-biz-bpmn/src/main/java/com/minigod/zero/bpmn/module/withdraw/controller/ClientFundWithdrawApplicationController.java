package com.minigod.zero.bpmn.module.withdraw.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.bpmn.module.common.group.EditGroup;
import com.minigod.zero.bpmn.module.common.group.QueryGroup;
import com.minigod.zero.bpmn.module.withdraw.bo.ClientFundWithdrawApplicationBo;
import com.minigod.zero.bpmn.module.withdraw.bo.ClientFundWithdrawInfoBo;
import com.minigod.zero.bpmn.module.withdraw.bo.WithdrawInfoDTO;
import com.minigod.zero.bpmn.module.withdraw.entity.ClientFundWithdrawInfo;
import com.minigod.zero.bpmn.module.withdraw.enums.BpmCommonEnum;
import com.minigod.zero.bpmn.module.withdraw.enums.SystemCommonEnum;
import com.minigod.zero.bpmn.module.withdraw.service.IClientFundWithdrawApplicationService;
import com.minigod.zero.bpmn.module.withdraw.service.IClientFundWithdrawInfoService;
import com.minigod.zero.bpmn.module.withdraw.util.EntityHelper;
import com.minigod.zero.bpmn.module.withdraw.vo.*;
import com.minigod.zero.core.excel.util.ExcelUtil;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.log.exception.ServiceException;
import com.minigod.zero.core.mp.support.Query;
import com.minigod.zero.core.redis.lock.RedisLock;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.cust.feign.ICustAccountClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Arrays;
import java.util.List;

/**
 * 客户出金申请流程信息Controller
 */
@Validated
@Api(value = "客户取款申请流程管理", tags = {"客户取款申请流程管理"})
@RestController
@RequestMapping(AppConstant.BACK_API_PREFIX +"/withdraw/apply")
public class ClientFundWithdrawApplicationController {

	@Resource
	private ICustAccountClient custAccountClient;
	@Autowired
	IClientFundWithdrawInfoService clientFundWithdrawInfoService;
    @Autowired
    IClientFundWithdrawApplicationService clientFundWithdrawApplicationService;

	@GetMapping("/{applicationId}")
	public R  withdrawalDetail(@PathVariable("applicationId") String applicationId){
		return clientFundWithdrawInfoService.withdrawalDetail(applicationId);
	}

	@GetMapping("/refund/{applicationId}")
	public R  withdrawalRefundDetail(@PathVariable("applicationId")String applicationId){
		return clientFundWithdrawInfoService.withdrawalRefundDetail(applicationId);
	}


    @ApiOperation("查询客户取款申请列表")
    @PostMapping("/list")
    public R<IPage<WithdrawInfoVO>> list(@RequestBody WithdrawInfoDTO param, @RequestBody Query pageQuery) {
		List<Integer> applicationStatus = Arrays.asList(
			BpmCommonEnum.FundWithdrawApplicationStatus.FUND_WITHDRAW_APPLY_CONFIRM_VALUE,
		BpmCommonEnum.FundWithdrawApplicationStatus.FUND_WITHDRAW_APPLY_AUDIT_VALUE,
		BpmCommonEnum.FundWithdrawApplicationStatus.FUND_WITHDRAW_APPLY_COUNTER_WITHDRAW_VALUE,
		BpmCommonEnum.FundWithdrawApplicationStatus.FUND_WITHDRAW_APPLY_BANK_WITHDRAW_VALUE,
		BpmCommonEnum.FundWithdrawApplicationStatus.FUND_WITHDRAW_APPLY_REFUND_VALUE,
		BpmCommonEnum.FundWithdrawApplicationStatus.FUND_WITHDRAW_APPLY_END_VALUE);
		param.setApplicationStatusList(applicationStatus);
        // 查询列表
        return clientFundWithdrawApplicationService.withdrawalPageList(param,pageQuery);
    }

    /**
     * 查询客户取款信息列表
     */
    @ApiOperation("查询客户取款申请列表(新增列表)")
    @PostMapping("/addlist")
    public R<IPage<WithdrawInfoVO>> withdrawApplicationList(@Validated(QueryGroup.class)
																			 @RequestBody WithdrawInfoDTO param,
																			 @RequestBody Query pageQuery) {
        // 查询列表
        // 由综合后台提交
		param.setApplySource(SystemCommonEnum.ApplySource.BACK_ADMIN.getType());
        // 状态为新建 已提交
        List<Integer> applicationStatus = Arrays.asList(
                BpmCommonEnum.FundWithdrawApplicationStatus.FUND_WITHDRAW_APPLY_STATUS_NEW_VALUE,
                BpmCommonEnum.FundWithdrawApplicationStatus.FUND_WITHDRAW_APPLY_STATUS_COMMIT_VALUE,
                BpmCommonEnum.FundWithdrawApplicationStatus.FUND_WITHDRAW_APPLY_ACCEPT_AUDIT_VALUE);
		param.setApplicationStatusList(applicationStatus);
		param.setTenantId(AuthUtil.getTenantId());
        return clientFundWithdrawApplicationService.withdrawalPageList(param, pageQuery);
    }

    /**
     * 查询客户取款信息列表(复核)
     */
    @ApiOperation("查询客户取款申请列表(复核)")
    @PostMapping("/auditList")
    public R<IPage<WithdrawInfoVO>> auditList(@Validated(QueryGroup.class) @RequestBody WithdrawInfoDTO param, @RequestBody Query pageQuery) {
		param.setApplicationStatus(BpmCommonEnum.FundWithdrawApplicationStatus.FUND_WITHDRAW_APPLY_ACCEPT_AUDIT_VALUE);
		param.setApplySource(SystemCommonEnum.ApplySource.BACK_ADMIN.getType());
		param.setTenantId(AuthUtil.getTenantId());
        return clientFundWithdrawApplicationService.withdrawalPageList(param, pageQuery);
    }

    /**
     * 查询客户取款信息列表(电汇-客服确认)
     */
    @ApiOperation("查询客户取款申请列表(客服确认)")
    @PostMapping("/comfirmList")
    public R<IPage<WithdrawInfoVO>> comfirmList(@Validated(QueryGroup.class) @RequestBody WithdrawInfoDTO param, @RequestBody Query pageQuery) {
        // 客服确认 流程状态为：审批中  业务状态为：已提交
        // 收款银行卡类型为 非香港本地银行 非后台录单
        param.setApplySource(SystemCommonEnum.ApplySource.ONLINE_TRADE.getType()); // 客户提交
        param.setApplicationStatus(BpmCommonEnum.FundWithdrawApplicationStatus.FUND_WITHDRAW_APPLY_CONFIRM_VALUE);
        param.setTransferType(SystemCommonEnum.TransferTypeEnum.OVERSEAS.getType());
        param.setTenantId(AuthUtil.getTenantId());
		return clientFundWithdrawApplicationService.withdrawalPageList(param, pageQuery);
    }


    /**
     * 查询客户取款信息列表(受理)
     */
    @ApiOperation("查询客户取款申请列表(受理)")
    @PostMapping("/acceptList")
    public R<IPage<WithdrawInfoVO>> acceptList(@Validated(QueryGroup.class) @RequestBody WithdrawInfoDTO param, @RequestBody Query pageQuery) {
        // 受理 流程状态为：审批中  业务状态为：已提交 已复核 已审核
        // 受理截止时间
        List<Integer> applicationStatus = Arrays.asList(
			BpmCommonEnum.FundWithdrawApplicationStatus.FUND_WITHDRAW_APPLY_CONFIRM_VALUE,
			BpmCommonEnum.FundWithdrawApplicationStatus.FUND_WITHDRAW_APPLY_AUDIT_VALUE);

		param.setApplicationStatusList(applicationStatus);
        // 受理：业务状态为 1-已提交, 则 取款类型不能为 1-电汇到大陆海外 同时来源不能为 后台申请
		param.setAcceptType(SystemCommonEnum.YesNo.YES.getIndex());
		param.setTenantId(AuthUtil.getTenantId());
		return clientFundWithdrawApplicationService.withdrawalPageList(param, pageQuery);
    }

    /**
     * 查询客户取款信息列表(打印)
     */
    @ApiOperation("查询客户取款申请列表(打印)")
    @PostMapping("/printList")
    public R<IPage<WithdrawInfoVO>> printList(@Validated(QueryGroup.class) @RequestBody WithdrawInfoDTO param, @RequestBody Query pageQuery) {
        // 受理 流程状态为：审批中  业务状态为：DBS打款失败
        List<Integer> applicationStatus = Arrays.asList(
                BpmCommonEnum.FundWithdrawApplicationStatus.FUND_WITHDRAW_APPLY_BANK_WITHDRAW_VALUE);
		param.setApplicationStatusList(applicationStatus);
		param.setTenantId(AuthUtil.getTenantId());
		return clientFundWithdrawApplicationService.withdrawalPageList(param, pageQuery);
    }

    /**
     * 查询客户取款信息列表(退款)
     */
    @ApiOperation("查询客户取款申请列表(退款申请)")
    @PostMapping("/refundList")
    public R<IPage<ClientFundWithdrawApplicationVo>> refundList(@Validated(QueryGroup.class) @RequestBody  WithdrawInfoDTO param, @RequestBody Query pageQuery) {
		List<Integer> refundApplicationStatusList = Arrays.asList(
			BpmCommonEnum.FundWithdrawStatus.REFUND_FAIL.getStatus(),
			BpmCommonEnum.FundWithdrawStatus.REFUND_SUCCESS.getStatus(),
			//BpmCommonEnum.FundWithdrawStatus.REFUND.getStatus(),
			BpmCommonEnum.FundWithdrawStatus.BANK_FAIL.getStatus()
		);
		param.setApplicationStatusList(refundApplicationStatusList);
		return clientFundWithdrawApplicationService.withdrawalFailPageList(param, pageQuery);
    }

    /**
     * 查询客户取款信息列表(退款)
     */
    @ApiOperation("查询客户取款申请列表(退款审核)")
    @PostMapping("/refundAuditList")
    public R<IPage<ClientFundWithdrawApplicationVo>> refundAuditList(@Validated(QueryGroup.class) @RequestBody  WithdrawInfoDTO param, @RequestBody Query pageQuery) {
        // 取款申请业务状态为： 申请退款
        // 退款流程状态
        List<Integer> refundApplicationStatusList = Arrays.asList(
                BpmCommonEnum.FundRefundApplyStatus.FUND_REFUND_APPLY_STATUS_COMMIT_VALUE
        );
		param.setApplicationStatusList(refundApplicationStatusList);
        return clientFundWithdrawApplicationService.withdrawalRefundPageList(param, pageQuery);
    }

    /**
     * 查询客户取款信息
     */
    @ApiOperation("查询客户取款信息")
    @PostMapping("/detail/{applicationId}")
    public R<ClientFundWithdrawApplicationVo> detail(@PathVariable("applicationId") String applicationId) {
        return R.data(clientFundWithdrawApplicationService.queryByApplicationId(applicationId));
    }

    /**
     * 批量审批通过
     *
     * @param applicationIds
     * @param reason
     * @return
     */
    @ApiOperation("批量审批通过")
    @PostMapping(value = "passNode")
    public R<String> passNode(@ApiParam("流水号数组")
                            @NotNull(message = "请选择记录")
                            @RequestParam(value = "applicationIds") String[] applicationIds,
                            @ApiParam("任务编号")
                            @NotNull(message = "任务编号不能为空")
                            @RequestParam(value = "taskIds") String[] taskIds,
                            @RequestParam(value = "reason", required = false) String reason
    ) {
        return R.data(clientFundWithdrawApplicationService.passNode(applicationIds, taskIds, reason));
    }

    /**
     * 拒绝(限单条操作)
     *
     * @param applicationId
     * @param reason
     * @return
     */
    @ApiOperation("审核拒绝")
    @PostMapping(value = "/cancel")
    public R<Void> reject(@ApiParam("流水号")
                          @NotBlank(message = "预约流水号不能为空")
                          @RequestParam("applicationId") String applicationId,
                          @ApiParam("任务编号")
                          @NotBlank(message = "任务编号不能为空")
                          @RequestParam(value = "taskId") String taskId,
                          @ApiParam("原因")
                          @RequestParam(value = "reason", required = false) String reason
    ) {
        clientFundWithdrawApplicationService.rejectFlow(applicationId, taskId, reason);
        return R.success();
    }

    /**
     * 手工录入出金申请信息
     */
    @PostMapping()
    @ApiOperation("新增客户出金申请信息")
	@RedisLock(value = "lock:fund:back_apply_withdraw")
    public R<String> add(@RequestBody ClientWithdrawDTO bo) {
		String data = clientFundWithdrawApplicationService.backCommitApply(bo);
		clientFundWithdrawApplicationService.startWithdrawFlow(data,bo.getTransferType(),SystemCommonEnum.ApplySource.BACK_ADMIN.getType());
		return R.data(data);
    }

    /**
     * 导出客户出金申请流程信息列表
     */
    @ApiOperation("取款导出[取款明细报表]")
    @GetMapping("/export1")
    public void export1(@ApiParam("流水号数组")
                        @NotNull(message = "请选择记录")
                        @Size(min = 1, message = "请选择记录")
                        @RequestParam(value = "applicationIds") String[] applicationIds,
                        HttpServletResponse response) {

        // 根据类型打印
        SystemCommonEnum.WithdrawReportTypeEnum withdrawReportTypeEnum = SystemCommonEnum.WithdrawReportTypeEnum.ITEM_REPORT;
        if (withdrawReportTypeEnum == null) {
            throw new ServiceException("不支持的报表导出");
        }

        // 模板文件
        List<ClientFundWithdrawApplicationVo> tableDataInfo = null;
        // 根据类型打印
        switch (withdrawReportTypeEnum) {
            case ITEM_REPORT:
                // 明细导出
                tableDataInfo = getClientFundWithdrawTable(applicationIds);
                if (tableDataInfo == null || tableDataInfo.size() == 0) {
                    throw new ServiceException("未查到记录");
                }
                response.setContentType("octets/stream");
                ExcelUtil.export(response,"客户取款申请受理","客户取款申请受理", EntityHelper.convertInfoReportList(tableDataInfo), WithdrawInfoReportVo.class );
                break;
            default:
                break;
        }
    }

    /**
     * 导出客户出金申请流程信息列表
     */
    @ApiOperation("取款导出[取款明细报表]")
    @PostMapping("/export")
    public void export(@Validated(QueryGroup.class) @RequestBody ClientFundWithdrawApplicationBo bo,
                       HttpServletResponse response) {
        // 退款流程状态
        List<Integer> refundApplicationStatusList = Arrays.asList(
                BpmCommonEnum.FundRefundApplyStatus.FUND_REFUND_APPLY_STATUS_COMMIT_VALUE,
                null
        );
        bo.setRefundApplicationStatusList(refundApplicationStatusList);
        // 查询列表
        List<ClientFundWithdrawApplicationVo> tableDataInfo = clientFundWithdrawApplicationService.queryDetailInfoList(bo);

        response.setContentType("octets/stream");
        String templateFileName = SystemCommonEnum.TemplateFileTypeEnum.WITHDRAW_ITEM_TEMPLATE.getDesc();
        ExcelUtil.export(response,"取款申请查询","取款申请查询",tableDataInfo, ClientFundWithdrawApplicationVo.class);

    }


    /**
     * 导出客户出金申请流程信息列表
     */
    @ApiOperation("取款导出[渣打普通导出]")
    @GetMapping("/export3")
    public void export3(@ApiParam("流水号数组")
                        @NotNull(message = "请选择记录")
                        @Size(min = 1, message = "请选择记录")
                        @RequestParam(value = "applicationIds") String[] applicationIds, HttpServletResponse response) {

        // 根据类型打印
        SystemCommonEnum.WithdrawReportTypeEnum withdrawReportTypeEnum = SystemCommonEnum.WithdrawReportTypeEnum.SCB_COMMON_CSV_REPORT;
        if (withdrawReportTypeEnum == null) {
            throw new ServiceException("不支持的报表导出");
        }

        // 模板文件
        String templateFileName = null;
        R<IPage<ClientFundWithdrawApplicationVo>> tableDataInfo = null;
        List<ClientFundWithdrawInfo> dataList = null;
        // 根据类型打印
        switch (withdrawReportTypeEnum) {
            case SCB_COMMON_CSV_REPORT: // 渣打普通导出
                // 明细导出
                ClientFundWithdrawInfoBo queryInfoBo = new ClientFundWithdrawInfoBo();
                queryInfoBo.setApplicationIdList(Arrays.asList(applicationIds));
                dataList = clientFundWithdrawInfoService.queryList(queryInfoBo);
                if (dataList == null || dataList.size() == 0) {
                    throw new ServiceException("未查到记录");
                }
                try {
                    response.setContentType("application/csv;charset=UTF-8");
                } catch (Exception e) {
                    throw new ServiceException("渣打普通打印失败");
                }

                break;
            default:
                break;
        }
    }

    /**
     * 导出客户出金申请流程信息列表
     */
    @ApiOperation("取款导出[渣打特快导出]")
    @GetMapping("/export7")
    public void export7(@ApiParam("流水号数组")
                        @NotNull(message = "请选择记录")
                        @Size(min = 1, message = "请选择记录")
                        @RequestParam(value = "applicationIds") String[] applicationIds, HttpServletResponse response) {
        // 根据类型打印
        SystemCommonEnum.WithdrawReportTypeEnum withdrawReportTypeEnum = SystemCommonEnum.WithdrawReportTypeEnum.SCB_RTGS_CSV_REPORT;
        if (withdrawReportTypeEnum == null) {
            throw new ServiceException("不支持的报表导出");
        }

        // 模板文件
        List<ClientFundWithdrawInfo> dataList = null;
        // 根据类型打印
        switch (withdrawReportTypeEnum) {
            case SCB_RTGS_CSV_REPORT: // 渣打普通导出
                // 明细导出
                ClientFundWithdrawInfoBo queryInfoBo = new ClientFundWithdrawInfoBo();
                queryInfoBo.setApplicationIdList(Arrays.asList(applicationIds));
                dataList = clientFundWithdrawInfoService.queryList(queryInfoBo);
                if (dataList == null || dataList.size() == 0) {
                    throw new ServiceException("未查到记录");
                }
                try {
                    response.setContentType("application/csv;charset=UTF-8");
                } catch (Exception e) {
                    throw new ServiceException("渣打特快打印失败");
                }

                break;
            default:
                break;
        }
    }

    /**
     * 导出客户出金申请流程信息列表
     */
    @ApiOperation("取款导出[渣打电汇导出]")
    @GetMapping("/export4")
    public void export4(@ApiParam("流水号数组")
                        @NotNull(message = "请选择记录")
                        @Size(min = 1, message = "请选择记录")
                        @RequestParam(value = "applicationIds") String[] applicationIds, HttpServletResponse response)
    {

        // 根据类型打印
        SystemCommonEnum.WithdrawReportTypeEnum withdrawReportTypeEnum = SystemCommonEnum.WithdrawReportTypeEnum.SCB_TT_CSV_REPORT;
        if (withdrawReportTypeEnum == null) {
            throw new ServiceException("不支持的报表导出");
        }

        // 模板文件
        R<IPage<ClientFundWithdrawApplicationVo>> tableDataInfo = null;
        List<ClientFundWithdrawInfo> dataList = null;
        // 根据类型打印
        switch (withdrawReportTypeEnum) {
            case SCB_TT_CSV_REPORT: // 渣打电汇导出
                // 明细导出
                ClientFundWithdrawInfoBo queryInfoBo2 = new ClientFundWithdrawInfoBo();
                queryInfoBo2.setApplicationIdList(Arrays.asList(applicationIds));
                dataList = clientFundWithdrawInfoService.queryList(queryInfoBo2);
                if (dataList == null || dataList.size() == 0) {
                    throw new ServiceException("未查到记录");
                }
                try {
                    response.setContentType("application/csv;charset=UTF-8");
                } catch (Exception e) {
                    throw new ServiceException("渣打电汇打印失败");
                }
                break;
            default:
                break;
        }
    }

    /**
     * 导出客户出金申请流程信息列表
     */
    @ApiOperation("取款导出[取款明细打印]")
    @GetMapping("/export5")
    public void export5(@ApiParam("流水号数组")
                        @NotNull(message = "请选择记录")
                        @Size(min = 1, message = "请选择记录")
                        @RequestParam(value = "applicationIds") String[] applicationIds, HttpServletResponse response) {
        // 根据类型打印
        SystemCommonEnum.WithdrawReportTypeEnum withdrawReportTypeEnum = SystemCommonEnum.WithdrawReportTypeEnum.BANK_ITEM_PDF_PRINT;
        if (withdrawReportTypeEnum == null) {
            throw new ServiceException("不支持的报表导出");
        }

        // 模板文件
        List<ClientFundWithdrawApplicationVo> tableDataInfo = null;
        List<ClientFundWithdrawInfo> dataList = null;
        // 根据类型打印
        switch (withdrawReportTypeEnum) {
            case BANK_ITEM_PDF_PRINT: // 明细打印
                tableDataInfo = getClientFundWithdrawTable(applicationIds);
                if (tableDataInfo == null || tableDataInfo.size() == 0) {
                    throw new ServiceException("未查到记录");
                }
                response.setContentType("application/pdf");
                break;
            default:

                break;
        }
    }

    /**
     * 导出客户出金申请流程信息列表
     */
    @ApiOperation("取款汇总导出[取款汇总报表]")
    @GetMapping("/exportSummer2")
    public void exportSummer2(HttpServletResponse response) {
        // 根据类型打印
        SystemCommonEnum.WithdrawReportTypeEnum withdrawReportTypeEnum = SystemCommonEnum.WithdrawReportTypeEnum.SUMMARY_REPORT;
        if (withdrawReportTypeEnum == null) {
            throw new ServiceException("不支持的报表导出");
        }
    }

    private List<ClientFundWithdrawApplicationVo> getClientFundWithdrawTable(@NotNull(message = "请选择记录") @Size(min = 1, message = "请选择记录") String[] applicationIds) {
        ClientFundWithdrawApplicationBo queryFundWithdrawBo = new ClientFundWithdrawApplicationBo();
        queryFundWithdrawBo.setApplicationIdList(Arrays.asList(applicationIds));
        List<ClientFundWithdrawApplicationVo> tableDataInfo = clientFundWithdrawApplicationService.queryDetailInfoList(queryFundWithdrawBo);
        return tableDataInfo;
    }

    /**
     * 获取客户出金申请信息详细信息
     */
    @ApiOperation("查看客户出金申请详细信息")
    @GetMapping("/getInfo/{applicationId}")
    public R<ClientFundWithdrawApplicationVo> getInfo(
            @ApiParam("流水号")
            @NotBlank(message = "预约流水号不能为空")
            @PathVariable("applicationId") String applicationId
    ) {
        ClientFundWithdrawApplicationVo vo = clientFundWithdrawApplicationService.queryByApplicationId(applicationId);
        return R.data(vo);
    }


    /**
     * 修改客户出金申请流程信息(受理修改)
     */
    @ApiOperation("修改客户出金申请信息【受理修改】")
    @PostMapping("/editAudit")
    public R editAudit(@Validated(EditGroup.class) @RequestBody ClientFundWithdrawInfoBo bo) {
        clientFundWithdrawInfoService.updateClientFundWithdrawInfoAduit(bo);
        return R.success();
    }

    /**
     * 汇款编号录入
     */
    @ApiOperation("汇款编号录入")
    @GetMapping("/add/remitId")
    public R<Void> addRemitId(
            @ApiParam("流水号")
            @NotBlank(message = "预约流水号不能为空")
            @RequestParam("applicationId") String applicationId,
            @ApiParam("汇款编号")
            @NotBlank(message = "汇款编号不能为空")
            @RequestParam("remittanceId") String remittanceId

    ) {
        ClientFundWithdrawInfoBo clientFundWithdrawInfoBo = new ClientFundWithdrawInfoBo();
        clientFundWithdrawInfoBo.setApplicationId(applicationId);
        clientFundWithdrawInfoBo.setRemittanceId(remittanceId);
        return R.status(clientFundWithdrawInfoService.updateClientFundWithdrawInfo(clientFundWithdrawInfoBo));
    }

    /**
     * 客服备注
     */
    @ApiOperation("客服备注")
    @GetMapping("/add/custRemark")
    public R<Void> addCustRemark(
            @ApiParam("流水号")
            @NotBlank(message = "预约流水号不能为空")
            @RequestParam("applicationId") String applicationId,
            @ApiParam("备注")
            @NotBlank(message = "备注不能为空")
            @RequestParam("custRemark") String custRemark

    ) {
        ClientFundWithdrawInfoBo clientFundWithdrawInfoBo = new ClientFundWithdrawInfoBo();
        clientFundWithdrawInfoBo.setApplicationId(applicationId);
        clientFundWithdrawInfoBo.setCustRemark(custRemark);
        return R.status(clientFundWithdrawInfoService.updateClientFundWithdrawInfo(clientFundWithdrawInfoBo));
    }

    /**
     * 设置打印标识
     */
    @ApiOperation("标记")
    @GetMapping("/add/setPrintFlag")
    public R setPrintFlag(
            @ApiParam("流水号数组")
            @NotNull(message = "请选择记录")
            @Size(min = 1, message = "请选择记录")
                    String[] applicationIds

    ) {
        return R.data(clientFundWithdrawApplicationService.setPrintFlag(applicationIds));
    }

    /**
     * 刷新银行交易状态
     */
    @ApiOperation("刷新银行交易状态")
    @GetMapping("/refreshBankState")
    public R refreshBankState(
            @ApiParam("流水号")
            @NotNull(message = "请选择记录")
            @RequestParam(value = "applicationId") String applicationId
    ) {
        clientFundWithdrawApplicationService.refreshBankState(applicationId);
        return R.success();
    }

    /**
     * 查询当前节点下的申请
     */
    @ApiOperation("查询当前节点下的申请")
    @GetMapping("/queryListByNode")
    public R queryListByNode(
            @ApiParam("节点")
            @NotNull(message = "节点")
            @RequestParam(value = "node") String node
    ) {

        return R.data(clientFundWithdrawApplicationService.queryListByNode(node));
    }


	@PostMapping("/complete_withdraw")
	public R completeWithdraw(@RequestBody ClientFundWithdrawInfoBo clientFundWithdrawInfoBo){
		return clientFundWithdrawApplicationService.completeWithdraw(clientFundWithdrawInfoBo);
	}

	/**
	 * 查询个人账户余额分币种
	 * @param currency
	 * @param accountId
	 * @return
	 */
	@ApiOperation("查询个人账户余额分币种")
	@GetMapping("/account_balance/detail")
	public R selectAccountBalance(String currency,String accountId){
		return custAccountClient.selectAccountBalance(currency,accountId);
	}
}
