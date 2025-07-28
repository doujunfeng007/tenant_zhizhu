package com.minigod.zero.bpmn.module.fundTrans.controller;

import java.util.List;
import java.util.Arrays;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.bpmn.module.common.group.AddGroup;
import com.minigod.zero.bpmn.module.common.group.QueryGroup;
import com.minigod.zero.core.boot.ctrl.ZeroController;
import com.minigod.zero.core.excel.util.ExcelUtil;
import com.minigod.zero.core.mp.support.Condition;
import com.minigod.zero.core.mp.support.Query;
import com.minigod.zero.core.tool.api.R;
import lombok.RequiredArgsConstructor;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import com.minigod.zero.bpmn.module.fundTrans.domain.vo.ClientFundTransQuotaVo;
import com.minigod.zero.bpmn.module.fundTrans.domain.bo.ClientFundTransQuotaBo;
import com.minigod.zero.bpmn.module.fundTrans.service.IClientFundTransQuotaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiOperation;

/**
 *  划拨额度Controller
 *
 * @author zsdp
 * @date 2024-12-25
 */
@Validated
@Api(value = " 划拨额度控制器", tags = {" 划拨额度管理"})
@RequiredArgsConstructor
@RestController
@RequestMapping("/fundTrans/fundTransQuota")
public class ClientFundTransQuotaController extends ZeroController {

    private final IClientFundTransQuotaService iClientFundTransQuotaService;

    /**
     * 查询 划拨额度列表
     */
    @ApiOperation("查询 划拨额度列表")
    @GetMapping("/list")
    public IPage<ClientFundTransQuotaVo> list(@Validated(QueryGroup.class) ClientFundTransQuotaBo bo, Query query) {
        return iClientFundTransQuotaService.queryPageList(bo, Condition.getPage(query));
    }

    /**
     * 导出 划拨额度列表
     */
    @ApiOperation("导出 划拨额度列表")
    @PostMapping("/export")
    public void export(@Validated ClientFundTransQuotaBo bo, HttpServletResponse response) {
        List<ClientFundTransQuotaVo> list = iClientFundTransQuotaService.queryList(bo);
        ExcelUtil.export(response,"划拨额度","划拨额度",list, ClientFundTransQuotaVo.class );
    }

    /**
     * 获取 划拨额度详细信息
     */
    @ApiOperation("获取 划拨额度详细信息")
    @GetMapping("/{id}")
    public R<ClientFundTransQuotaVo> getInfo(@ApiParam("主键")
                                                  @NotNull(message = "主键不能为空")
                                                  @PathVariable("id") Long id) {
        return R.data(iClientFundTransQuotaService.queryById(id));
    }

    /**
     * 新增 划拨额度
     */
    @ApiOperation("新增 划拨额度")
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody ClientFundTransQuotaBo bo) {
        return R.status(iClientFundTransQuotaService.insertByBo(bo));
    }

    /**
     * 修改 划拨额度
     */
    @ApiOperation("修改 划拨额度")
    @PutMapping()
    public R<Void> edit(@RequestBody ClientFundTransQuotaBo bo) {
        return R.status(iClientFundTransQuotaService.updateByBo(bo));
    }

    /**
     * 删除 划拨额度
     */
    @ApiOperation("删除 划拨额度")
    @DeleteMapping("/{ids}")
    public R<Void> remove(@ApiParam("主键串")
                                       @NotEmpty(message = "主键不能为空")
                                       @PathVariable Long[] ids) {
        return R.status(iClientFundTransQuotaService.deleteWithValidByIds(Arrays.asList(ids), true));
    }
}
