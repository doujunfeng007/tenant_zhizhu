package com.minigod.zero.bpmn.module.deposit.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.minigod.zero.biz.common.utils.DateUtil;
import com.minigod.zero.bpmn.module.deposit.vo.DbsRecordExcel;
import com.minigod.zero.core.excel.util.ExcelUtil;
import com.minigod.zero.core.secure.utils.AuthUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.minigod.zero.bpmn.module.bank.entity.DepositBankBillFlow;
import com.minigod.zero.bpmn.module.bank.service.DepositBankBillFlowService;
import com.minigod.zero.bpmn.module.bank.vo.DepositBankBillFlowVo;
import com.minigod.zero.bpmn.module.deposit.bo.FundDepositApplicationQuery;
import com.minigod.zero.bpmn.module.deposit.service.IFundDepositApplicationService;
import com.minigod.zero.bpmn.module.deposit.vo.DepositRecordVO;
import com.minigod.zero.bpmn.module.deposit.vo.FundDepositApplicationVO;
import com.minigod.zero.core.boot.ctrl.ZeroController;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.mp.support.Condition;
import com.minigod.zero.core.mp.support.Query;
import com.minigod.zero.core.tool.api.R;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

/**
 * 客户入金申请表 控制器
 *
 * @author taro
 * @since 2024-02-29
 */
@RestController
@AllArgsConstructor
@RequestMapping(AppConstant.BACK_API_PREFIX + "/deposit/fundDepositApplication")
@Api(value = "客户入金申请表", tags = "客户入金申请表接口")
public class FundDepositApplicationController extends ZeroController {

    private final DepositBankBillFlowService depositBankBillFlowService;
    private final IFundDepositApplicationService fundDepositApplicationService;

    /**
     * 客户入金申请表 详情
     */
    @GetMapping("/{applicationId}")
    @ApiOperationSupport(order = 2)
    @ApiOperation(value = "获取入金申请详细接口")
    public R<FundDepositApplicationVO> detail(@PathVariable("applicationId") String applicationId) {
        FundDepositApplicationVO detail = fundDepositApplicationService.queryByApplicationId(applicationId);
        return R.data(detail);
    }

    /**
     * 入金申请表分页查询
     */
    @GetMapping("/applicationList")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "入金申请查询分页接口")
    public R<IPage<DepositRecordVO>> page(FundDepositApplicationQuery applicationQuery, Query query) {
        IPage<DepositRecordVO> pages = fundDepositApplicationService.queryPage(Condition.getPage(query), applicationQuery);
        return R.data(pages);
    }

	@GetMapping("/detail/{applicationId}")
	public R<DepositRecordVO> depositRecordDetail(@PathVariable("applicationId") String applicationId){
		return R.data(fundDepositApplicationService.depositRecordDetail(applicationId));
	}


    @GetMapping("/billFlowAuditList")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "DBS流水核对列表")
    public R<IPage<DepositBankBillFlowVo>> billFlowAuditPage(DepositBankBillFlow applicationQuery,
															 Query query) {
        IPage<DepositBankBillFlowVo> flowIPage = depositBankBillFlowService.selectBillFlowPage(Condition.getPage(query), applicationQuery);
        return R.data(flowIPage);
    }

    @PostMapping("/checkFlow")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "核对流水")
    public R checkFlow(@RequestParam("refId") String refId) {
        return R.status(depositBankBillFlowService.checkFlow(refId) >= 1);
    }

    @PostMapping("/delFlow")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "删除流水")
    public R delFlow(@RequestParam("refId") String refId,@RequestParam("isDeleted")Integer  isDeleted) {
        return R.status(depositBankBillFlowService.delFlow(refId,isDeleted) >= 1);
    }

    @PostMapping("/unCheckFlow")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "取消核对流水")
    public R unCheckFlow(@RequestParam("refId") String refId) {
        return R.status(depositBankBillFlowService.unCheckFlow(refId) >= 1);
    }

    @GetMapping("/billFlowAudit/{refId}")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "查询DBS流水信息")
    public R<DepositBankBillFlow> billFlowAudit(@PathVariable("refId") String refId) {
        DepositBankBillFlow flow = depositBankBillFlowService.queryByRefId(refId);
        return R.data(flow);
    }

    @GetMapping("/proofList")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "入金凭证查询分页接口")
    public R<IPage<DepositRecordVO>> proofList(FundDepositApplicationQuery applicationQuery,Query query) {
        applicationQuery.setApplicationStatus(1);
        IPage<DepositRecordVO> pages = fundDepositApplicationService.queryPage(Condition.getPage(query), applicationQuery);
        return R.data(pages);
    }

    @GetMapping("/auditList")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "入金核对分页接口")
    public R<IPage<FundDepositApplicationVO>> auditList(FundDepositApplicationQuery applicationQuery,Query query) {
        applicationQuery.setApplicationStatus(2);
        IPage<FundDepositApplicationVO> pages = fundDepositApplicationService.selectFundDepositApplicationPage(Condition.getPage(query), applicationQuery);
        return R.data(pages);
    }

    @ApiOperation("批量申领")
    @PostMapping("/batchClaim")
    public R<String> batchClaim(@ApiParam("流程号串")
                                @NotEmpty(message = "流程号不能为空")
                                @RequestParam String[] applicationIds,
                                @ApiParam("流程状态")
                                @NotNull(message = "流程状态不能为空")
                                @RequestParam("applicationStatus") Integer applicationStatus
    ) {
        return R.data(fundDepositApplicationService.batchClaim(applicationStatus, Arrays.asList(applicationIds)));
    }

    @ApiOperation("批量取消认领")
    @PostMapping("batchUnClaim")
    public R<String> batchUnClaim(@ApiParam("流程号串")
                                  @NotEmpty(message = "任务不能为空")
                                  @RequestParam String[] applicationIds
    ) {
        fundDepositApplicationService.batchUnClaim(Arrays.asList(applicationIds));
        return R.success();
    }

    @ApiOperation("拒绝节点")
    @PostMapping(value = "rejectNode")
    public R<String> rejectNode(@ApiParam("流程号串")
                                @NotBlank(message = "任务不能为空")
                                @RequestParam("applicationId") String applicationId,
                                @RequestParam("instanceId") String instanceId,
                                @NotBlank(message = "请填写拒绝原因")
                                @RequestParam("reason") String reason
    ) {
        fundDepositApplicationService.rejectNode(applicationId, instanceId, reason);
        return R.success();
    }


    @ApiOperation("通过节点")
    @PostMapping(value = "passNode")
    public R<String> passCustomer(@ApiParam("流程号串")
                                  @NotBlank(message = "任务不能为空")
                                  @RequestParam("applicationId") String applicationId,
                                  @RequestParam("taskId") String taskId,
                                  @RequestParam(value = "reason", required = false) String reason
    ) {
        fundDepositApplicationService.passNode(applicationId, taskId, reason);
        return R.success();
    }

    @ApiOperation("核对通过")
    @PostMapping(value = "collateNode")
    public R<String> collateNode(@ApiParam("流程号串")
                                 @NotBlank(message = "流水不能为空")
                                 @RequestParam("applicationId") String applicationId,
                                 @NotBlank(message = "任务不能为空")
                                 @RequestParam("taskId") String taskId,
                                 @NotBlank(message = "银行流水不能为空")
                                 @RequestParam("refId") String refId,
                                 @RequestParam(value = "reason", required = false) String reason
    ) {
        fundDepositApplicationService.collateNode(applicationId, taskId, refId, reason);
        return R.success();
    }

    @PostMapping("importDbsRecord")
    @ApiOperation(value = "导入DBS流水", notes = "传入excel")
    public R<Object> importDbsRecord(@RequestParam MultipartFile file) {
        try {
            List<DbsRecordExcel> list = ExcelUtil.readWebExcelData(file, DbsRecordExcel.class);
            //转换表格对象
            List<DepositBankBillFlow> flows = list.stream().map(e -> {
                DepositBankBillFlow depositBankBillFlow = new DepositBankBillFlow();
                BeanUtils.copyProperties(e, depositBankBillFlow);
                depositBankBillFlow.setCheckStatus(0);
                depositBankBillFlow.setFlowSource(0);
                depositBankBillFlow.setIsDeleted(0);
                depositBankBillFlow.setIsRepeat(false);
                depositBankBillFlow.setTenantId(AuthUtil.getTenantId());
                depositBankBillFlow.setTimeStamp(DateUtil.dateTime(DateUtil.YYYY_MM_DD_HH_MM_SS,e.getValueDate()+" "+e.getProcessingTime()));
                depositBankBillFlow.setValueDate(DateUtil.dateTime(DateUtil.YYYY_MM_DD,e.getValueDate()));
                return depositBankBillFlow;
            }).collect(Collectors.toList());
            if (flows.size() > 0) {
                //去重
                List<String> referenceNos = flows.stream().filter(distinctByKey(DepositBankBillFlow::getReferenceNo)).map(DepositBankBillFlow::getReferenceNo).collect(Collectors.toList());
                //数据库去重
                Set<String> refs = depositBankBillFlowService.selectRepeatByRefs(referenceNos);
                flows.forEach(e->{
                    //标记重复
                    e.setIsRepeat(refs.contains(e.getReferenceNo()));
                });
                depositBankBillFlowService.batchInsert(flows);
            }

            return R.data(flows);
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
    }


    private static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }


}
