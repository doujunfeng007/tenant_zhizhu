package com.minigod.zero.bpmn.module.withdraw.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.bpmn.module.common.group.AddGroup;
import com.minigod.zero.bpmn.module.common.group.QueryGroup;
import com.minigod.zero.bpmn.module.withdraw.bo.ClientFundRefundApplicationBo;
import com.minigod.zero.bpmn.module.withdraw.entity.ClientFundRefundApplication;
import com.minigod.zero.bpmn.module.withdraw.service.IClientFundRefundApplicationService;
import com.minigod.zero.core.excel.util.ExcelUtil;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.mp.support.Condition;
import com.minigod.zero.core.mp.support.Query;
import com.minigod.zero.core.tool.api.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 客户退款申请流程信息Controller
 *
 * @author zsdp
 * @date 2023-04-05
 */
@Validated
@Api(value = "客户退款申请流程信息控制器", tags = {"客户退款申请流程管理"})
@RequiredArgsConstructor
@RestController
@RequestMapping(AppConstant.BACK_API_PREFIX +"/withdraw/refundApply")
public class ClientFundRefundApplicationController {

    @Autowired
    private final IClientFundRefundApplicationService clientFundRefundApplicationService;

    /**
     * 查询客户退款申请流程信息列表
     */
    @ApiOperation("查询客户退款申请流程信息列表")
    //@SaCheckPermission("withdraw:refundApply:list")
    @GetMapping("/list")
    public R<IPage<ClientFundRefundApplication>> list(@Validated(QueryGroup.class) ClientFundRefundApplicationBo bo, Query pageQuery) {
        return R.data(clientFundRefundApplicationService.queryPageList(Condition.getPage(pageQuery), bo));
    }

    /**
     * 导出客户退款申请流程信息列表
     */
    @ApiOperation("导出客户退款申请流程信息列表")
    //@SaCheckPermission("withdraw:refundApply:export")
    //@Log(title = "客户退款申请流程信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(@Validated ClientFundRefundApplicationBo bo, HttpServletResponse response) {
        List<ClientFundRefundApplication> list = clientFundRefundApplicationService.queryList(bo);
        ExcelUtil.export(response, "客户退款申请流程信息", "客户退款申请流程信息", list, ClientFundRefundApplication.class);
    }

    /**
     * 获取客户退款申请流程信息详细信息
     */
    @ApiOperation("获取客户退款申请流程信息详细信息")
    //@SaCheckPermission("withdraw:refundApply:getInfo")
    @GetMapping("/{id}")
    public R<ClientFundRefundApplication> getInfo(@ApiParam("主键")
                                                  @NotNull(message = "主键不能为空")
                                                  @PathVariable("id") Long id) {
        return R.data(clientFundRefundApplicationService.queryById(id));
    }

    /**
     * 新增客户退款申请流程信息
     */
    @ApiOperation("新增客户退款申请流程信息")
    //@SaCheckPermission("withdraw:refundApply:add")
    //@Log(title = "客户退款申请流程信息", businessType = BusinessType.INSERT)
    //@RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody ClientFundRefundApplicationBo bo) {
        clientFundRefundApplicationService.commitApply(bo);
        return R.success();
    }


    /**
     * 审批通过
     *
     * @param refundApplicationId
     * @param reason
     * @return
     */
    @ApiOperation("审批通过")
    //@Log(title = "审批通过", businessType = BusinessType.UPDATE)
    //@SaCheckPermission("withdraw:refundApply:passNode")
    @GetMapping(value = "/passNode")
    public R<Void> passNode(
            @ApiParam("退款流水号")
            @NotNull(message = "请选择记录")
            @RequestParam(value = "refundApplicationId") String refundApplicationId,
            @ApiParam("任务编号")
            @NotBlank(message = "任务编号不能为空")
            @RequestParam(value = "taskId") String taskId,
            @RequestParam(value = "reason", required = false) String reason
    ) {
        clientFundRefundApplicationService.passNode(refundApplicationId, taskId, reason);
        return R.success();
    }

    /**
     * 拒绝
     *
     * @param refundApplicationId
     * @param reason
     * @return
     */
    @ApiOperation("拒绝")
    //@Log(title = "拒绝", businessType = BusinessType.UPDATE)
    //@SaCheckPermission("withdraw:refundApply:rejectFlow")
    @GetMapping(value = "/rejectFlow")
    public R<Void> rejectFlow(
            @NotNull(message = "请选择记录")
            @RequestParam(value = "refundApplicationId") String refundApplicationId,
            @ApiParam("任务编号")
            @NotBlank(message = "任务编号不能为空")
            @RequestParam(value = "taskId") String taskId,
            @RequestParam(value = "reason", required = false) String reason
    ) {
        clientFundRefundApplicationService.rejectFlow(refundApplicationId, taskId, reason);
        return R.success();
    }

    /**
     * 取消
     *
     * @param refundApplicationId
     * @param reason
     * @return
     */
    @ApiOperation("取消")
    //@Log(title = "取消", businessType = BusinessType.UPDATE)
    //@SaCheckPermission("withdraw:refundApply:cancelFlow")
    @GetMapping(value = "/cancelFlow")
    public R<Void> cancelFlow(
            @NotNull(message = "请选择记录")
            @RequestParam(value = "refundApplicationId") String refundApplicationId,
            @ApiParam("任务编号")
            @NotBlank(message = "请先申请退款")
            @RequestParam(value = "taskId") String taskId,
            @RequestParam(value = "reason", required = false) String reason
    ) {
        clientFundRefundApplicationService.cancelFlow(refundApplicationId, taskId, reason);
        return R.success();
    }


}
