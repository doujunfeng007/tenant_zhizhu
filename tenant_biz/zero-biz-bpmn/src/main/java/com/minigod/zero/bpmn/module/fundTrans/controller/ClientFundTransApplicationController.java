package com.minigod.zero.bpmn.module.fundTrans.controller;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.minigod.zero.bpmn.module.fundTrans.domain.bo.ClientFundTransApplicationQuery;
import com.minigod.zero.bpmn.module.fundTrans.domain.vo.ClientFundTransApplicationVO;
import com.minigod.zero.bpmn.module.fundTrans.service.ClientFundTransApplicationService;
import com.minigod.zero.bpmn.module.fundTrans.service.ClientFundTransInfoService;
import com.minigod.zero.core.boot.ctrl.ZeroController;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.mp.support.Condition;
import com.minigod.zero.core.mp.support.Query;
import com.minigod.zero.core.tool.api.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Arrays;

/**
* 资金调拨申请流程表(client_fund_trans_application)表控制层
*
* @author xxxxx
*/
@RestController
@AllArgsConstructor
@RequestMapping(AppConstant.BACK_API_PREFIX +"/fundTrans/application")
@Api(value = "资金调拨审核申请表", tags = "资金调拨审核申请表")
public class ClientFundTransApplicationController extends ZeroController {
/**
* 服务对象
*/
    private final ClientFundTransApplicationService clientFundTransApplicationService;
    private final ClientFundTransInfoService clientFundTransInfoService;

    /**
     * 客户调拨表 详情
     */
    @GetMapping("/{applicationId}")
    @ApiOperationSupport(order = 2)
    @ApiOperation(value = "获取调拨详细接口")
    public R<ClientFundTransApplicationVO> detail(@PathVariable("applicationId") String applicationId) {
        ClientFundTransApplicationVO detail = clientFundTransApplicationService.queryByApplicationId(applicationId);
        return R.data(detail);
    }

    /**
     * 客户调拨表 自定义分页
     */
    @GetMapping("/applicationList")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "调拨查询分页接口")
    public R<IPage<ClientFundTransApplicationVO>> page(ClientFundTransApplicationQuery applicationQuery,
                                                       Query query) {
        IPage<ClientFundTransApplicationVO> pages = clientFundTransApplicationService.selectFundTransApplicationPage(Condition.getPage(query), applicationQuery);
        return R.data(pages);
    }

    @ApiOperation("批量申领")
    @PostMapping(value = "/batchClaim")
    public R<String> batchClaim(@ApiParam(value = "流程号串", required = true)
                                @NotEmpty(message = "流程号不能为空")
                                @RequestParam String[] applicationIds,
                                @ApiParam(value = "流程状态", required = true)
                                @NotNull(message = "流程状态不能为空")
                                @RequestParam("applicationStatus") Integer applicationStatus) {
        return R.data(clientFundTransApplicationService.batchClaim(applicationStatus, Arrays.asList(applicationIds)));
    }

    @ApiOperation("批量取消认领")
    @PostMapping(value = "batchUnClaim")
    public R<String> batchUnClaim(@ApiParam(value = "流程号串", required = true)
                                  @NotEmpty(message = "任务不能为空")
                                  @RequestParam String[] applicationIds) {
        return R.data(clientFundTransApplicationService.batchUnClaim(Arrays.asList(applicationIds)));
    }

    @ApiOperation("拒绝节点")
    @PostMapping(value = "rejectNode")
    public R<String> rejectNode(@ApiParam(value = "流程号串", required = true)
                                @NotBlank(message = "任务不能为空")
                                @RequestParam("applicationId") String applicationId,
                                @ApiParam(value = "任务实例ID", required = true)
                                @RequestParam("instanceId") String instanceId,
                                @NotBlank(message = "请填写拒绝原因")
                                @ApiParam(value = "原因", required = true)
                                @RequestParam("reason") String reason) {
        clientFundTransApplicationService.rejectNode(applicationId, instanceId, reason);
        return R.success();
    }


    @ApiOperation("通过节点")
    @PostMapping(value = "passNode")
    public R<String> passCustomer(@ApiParam(value = "流程号串", required = true)
                                  @NotBlank(message = "任务不能为空")
                                  @RequestParam("applicationId") String applicationId,
                                  @ApiParam(value = "任务 ID", required = true)
                                  @RequestParam("taskId") String taskId,
                                  @ApiParam(value = "原因")
                                  @RequestParam(value = "reason", required = false) String reason) {
        clientFundTransApplicationService.passNode(applicationId, taskId, reason);
        return R.success();
    }

}
