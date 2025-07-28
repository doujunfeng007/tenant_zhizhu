package com.minigod.zero.bpmn.module.deposit.controller;

import com.minigod.zero.core.launch.constant.AppConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import lombok.AllArgsConstructor;

import javax.validation.Valid;

import com.minigod.zero.core.mp.support.Condition;
import com.minigod.zero.core.mp.support.Query;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.utils.Func;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.bpmn.module.deposit.entity.FundDepositInfoEntity;
import com.minigod.zero.bpmn.module.deposit.vo.FundDepositInfoVO;
import com.minigod.zero.bpmn.module.deposit.service.IFundDepositInfoService;
import com.minigod.zero.core.boot.ctrl.ZeroController;

/**
 * 客户入金申请信息表 控制器
 *
 * @author taro
 * @since 2024-02-29
 */
@RestController
@AllArgsConstructor
@RequestMapping(AppConstant.BACK_API_PREFIX + "/deposit/fundDepositInfo")
@Api(value = "客户入金申请信息表", tags = "客户入金申请信息表接口")
public class FundDepositInfoController extends ZeroController {

    private final IFundDepositInfoService fundDepositInfoService;

    /**
     * 客户入金申请信息表 详情
     */
    @GetMapping("/detail")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "详情", notes = "传入fundDepositInfo")
    public R<FundDepositInfoEntity> detail(FundDepositInfoEntity fundDepositInfo) {
        FundDepositInfoEntity detail = fundDepositInfoService.getOne(Condition.getQueryWrapper(fundDepositInfo));
        return R.data(detail);
    }

    /**
     * 客户入金申请信息表 分页
     */
    @GetMapping("/list")
    @ApiOperationSupport(order = 2)
    @ApiOperation(value = "分页", notes = "传入fundDepositInfo")
    public R<IPage<FundDepositInfoEntity>> list(FundDepositInfoEntity fundDepositInfo, Query query) {
        IPage<FundDepositInfoEntity> pages = fundDepositInfoService.page(Condition.getPage(query), Condition.getQueryWrapper(fundDepositInfo));
        return R.data(pages);
    }

    /**
     * 客户入金申请信息表 自定义分页
     */
    @GetMapping("/page")
    @ApiOperationSupport(order = 3)
    @ApiOperation(value = "分页", notes = "传入fundDepositInfo")
    public R<IPage<FundDepositInfoVO>> page(FundDepositInfoVO fundDepositInfo, Query query) {
        IPage<FundDepositInfoVO> pages = fundDepositInfoService.selectFundDepositInfoPage(Condition.getPage(query), fundDepositInfo);
        return R.data(pages);
    }

    /**
     * 客户入金申请信息表 新增
     */
    @PostMapping("/save")
    @ApiOperationSupport(order = 4)
    @ApiOperation(value = "新增", notes = "传入fundDepositInfo")
    public R<Object> save(@Valid @RequestBody FundDepositInfoEntity fundDepositInfo) {
        return R.status(fundDepositInfoService.save(fundDepositInfo));
    }

    /**
     * 客户入金申请信息表 修改
     */
    @PostMapping("/update")
    @ApiOperationSupport(order = 5)
    @ApiOperation(value = "修改", notes = "传入fundDepositInfo")
    public R<Object> update(@Valid @RequestBody FundDepositInfoEntity fundDepositInfo) {
        return R.status(fundDepositInfoService.updateById(fundDepositInfo));
    }

    /**
     * 客户入金申请信息表 新增或修改
     */
    @PostMapping("/submit")
    @ApiOperationSupport(order = 6)
    @ApiOperation(value = "新增或修改", notes = "传入fundDepositInfo")
    public R<Object> submit(@Valid @RequestBody FundDepositInfoEntity fundDepositInfo) {
        return R.status(fundDepositInfoService.saveOrUpdate(fundDepositInfo));
    }

    /**
     * 客户入金申请信息表 删除
     */
    @DeleteMapping("/remove")
    @ApiOperationSupport(order = 7)
    @ApiOperation(value = "删除", notes = "传入ids")
    public R<Object> remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
        return R.status(fundDepositInfoService.removeByIds(Func.toLongList(ids)));
    }
}
