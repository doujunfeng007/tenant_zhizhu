package com.minigod.zero.bpmn.module.withdraw.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.bpmn.module.common.group.QueryGroup;
import com.minigod.zero.bpmn.module.withdraw.bo.BankAreaBo;
import com.minigod.zero.bpmn.module.withdraw.entity.BankArea;
import com.minigod.zero.bpmn.module.withdraw.service.IBankAreaService;
import com.minigod.zero.core.boot.ctrl.ZeroController;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.mp.support.Condition;
import com.minigod.zero.core.mp.support.Query;
import com.minigod.zero.core.tool.api.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 区域Controller
 *
 * @author chenyu
 * @date 2023-04-20
 */
@Validated
@Api(value = "区域控制器", tags = {"区域管理"})
@RequiredArgsConstructor
@RestController
@RequestMapping(AppConstant.BACK_API_PREFIX + "/withdraw/area")
public class BankAreaController extends ZeroController {

    private final IBankAreaService iBankAreaService;

    /**
     * 查询区域列表
     */
    @ApiOperation("查询区域列表")
    @GetMapping("/list")
    public R<IPage<BankArea>> list(@Validated(QueryGroup.class) BankAreaBo bo, Query query) {
        return R.data(iBankAreaService.queryPageList(Condition.getPage(query), bo));
    }

}
