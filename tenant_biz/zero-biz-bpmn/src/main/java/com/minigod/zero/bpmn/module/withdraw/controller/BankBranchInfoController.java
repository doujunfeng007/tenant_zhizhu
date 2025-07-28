package com.minigod.zero.bpmn.module.withdraw.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.bpmn.module.common.group.QueryGroup;
import com.minigod.zero.bpmn.module.withdraw.bo.BankBranchInfoBo;
import com.minigod.zero.bpmn.module.withdraw.entity.BankBranchInfo;
import com.minigod.zero.bpmn.module.withdraw.service.IBankBranchInfoService;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.mp.support.Condition;
import com.minigod.zero.core.mp.support.Query;
import com.minigod.zero.core.tool.api.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 分行信息Controller
 *
 * @author chenyu
 * @date 2023-04-21
 */
@Validated
@Api(value = "分行信息控制器", tags = {"分行信息管理"})
@RequiredArgsConstructor
@RestController
@RequestMapping(AppConstant.BACK_API_PREFIX +"/withdraw/branchInfo")
public class BankBranchInfoController {

    private final IBankBranchInfoService iBankBranchInfoService;

    /**
     * 查询分行信息列表
     */
    @ApiOperation("查询分行信息列表")
    @GetMapping("/list")
    public R<IPage<BankBranchInfo>> list(@Validated(QueryGroup.class) BankBranchInfoBo bo, Query pageQuery) {
        return R.data(iBankBranchInfoService.queryPageList(Condition.getPage(pageQuery), bo));
    }

    /**
     * 获取分行信息详细信息
     */
    @ApiOperation("获取分行信息详细信息")
    @GetMapping("/getInfo")
    public R<BankBranchInfo> getInfo(@ApiParam("主键")
                                     @RequestParam(value = "id", required = false) Long id,
                                     @ApiParam("分行编码")
                                     @RequestParam(value = "branchCode", required = false) String branchCode
    ) {

        BankBranchInfoBo branchInfoBo = new BankBranchInfoBo();
        branchInfoBo.setId(id);
        branchInfoBo.setBranchCode(branchCode);
        List<BankBranchInfo> lstData = iBankBranchInfoService.queryList(branchInfoBo);
        if (null != lstData && lstData.size() > 0) {
            return R.data(lstData.get(0));
        }
        return R.success();
    }

}
