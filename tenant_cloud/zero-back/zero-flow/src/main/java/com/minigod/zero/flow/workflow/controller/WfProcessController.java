package com.minigod.zero.flow.workflow.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.mp.support.Condition;
import com.minigod.zero.core.mp.support.Query;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.flow.workflow.domain.bo.WfCopyBo;
import com.minigod.zero.flow.workflow.domain.bo.WfProcessBo;
import com.minigod.zero.flow.workflow.domain.vo.WfCopyVo;
import com.minigod.zero.flow.workflow.domain.vo.WfDefinitionVo;
import com.minigod.zero.flow.workflow.domain.vo.WfTaskVo;
import com.minigod.zero.flow.workflow.service.IWfCopyService;
import com.minigod.zero.flow.workflow.service.IWfProcessService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 工作流流程管理
 *
 * @author zsdp
 * @createTime 2022/3/24 18:54
 */
@Slf4j
@Api(tags = "工作流流程管理")
@RequiredArgsConstructor
@RestController
@RequestMapping(AppConstant.BACK_API_PREFIX +"/workflow/process")
public class WfProcessController {

    private final IWfProcessService processService;
    private final IWfCopyService copyService;

    @GetMapping(value = "/list")
    @ApiOperation(value = "查询可发起流程列表", response = WfDefinitionVo.class)
    public IPage<WfDefinitionVo> list(Query query) {
        return processService.processList(Condition.getPage(query));
    }

    @ApiOperation(value = "根据流程定义id启动流程实例")
    @PostMapping("/start/{processDefId}")
    public R<Void> start(@ApiParam(value = "流程定义id") @PathVariable(value = "processDefId") String processDefId,
                         @ApiParam(value = "变量集合,json对象") @RequestBody Map<String, Object> variables) {
        processService.startProcess(processDefId, variables);
        return R.success("流程启动成功");

    }

    @ApiOperation(value = "我拥有的流程", response = WfTaskVo.class)
    @GetMapping(value = "/ownList")
    public R<IPage<WfTaskVo>> ownProcess(WfProcessBo bo,Query query) {
        return R.data(processService.queryPageOwnProcessList(bo,Condition.getPage(query)));
    }

    @ApiOperation(value = "获取待办列表", response = WfTaskVo.class)
    @GetMapping(value = "/todoList")
    public R<IPage<WfTaskVo>> todoProcess(WfProcessBo processBo,Query query) {
        return R.data(processService.queryPageTodoProcessList(processBo,Condition.getPage(query)));
    }

    @ApiOperation(value = "获取待签列表", response = WfTaskVo.class)
    @GetMapping(value = "/claimList")
    public R<IPage<WfTaskVo>> claimProcess(WfProcessBo processBo, Query query) {
        return R.data(processService.queryPageClaimProcessList(processBo, Condition.getPage(query)));
    }

    @ApiOperation(value = "获取已办列表", response = WfTaskVo.class)
    @GetMapping(value = "/finishedList")
    public R<IPage<WfTaskVo>> finishedProcess(WfProcessBo processBo ,Query query) {
        return R.data(processService.queryPageFinishedProcessList(processBo,Condition.getPage(query)));
    }

    @ApiOperation(value = "获取抄送列表", response = WfTaskVo.class)
    @GetMapping(value = "/copyList")
    public R<IPage<WfCopyVo>> copyProcess(WfCopyBo copyBo, Query query) {
        copyBo.setUserId(AuthUtil.getUserId());
        return R.data(copyService.queryPageList(copyBo, Condition.getPage(query)));
    }
}
