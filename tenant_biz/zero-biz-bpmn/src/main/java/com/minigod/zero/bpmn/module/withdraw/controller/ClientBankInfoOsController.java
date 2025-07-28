package com.minigod.zero.bpmn.module.withdraw.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.bpmn.module.common.group.QueryGroup;
import com.minigod.zero.bpmn.module.withdraw.bo.ClientBankInfoOsBo;
import com.minigod.zero.bpmn.module.withdraw.entity.ClientBankInfoOs;
import com.minigod.zero.bpmn.module.withdraw.service.ClientBankInfoOsService;
import com.minigod.zero.core.boot.ctrl.ZeroController;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * (client_bank_info_os)表控制层
 *
 * @author chenyu
 */
@Validated
@Api(value = "海外银行SwiftCode信息记录控制器", tags = {"海外银行SwiftCode信息管理"})
@RequiredArgsConstructor
@RestController
@RequestMapping(AppConstant.BACK_API_PREFIX + "/withdraw/bankInfoOs")
public class ClientBankInfoOsController extends ZeroController {
    private final ClientBankInfoOsService clientBankInfoOsService;

    /**
     * 查询海外银行信息记录列表
     */
    @ApiOperation("查询海外银行信息记录列表")
    //@SaCheckPermission("withdraw:bankInfoOs:list")
    @GetMapping("/list")
    public R<IPage<ClientBankInfoOs>> list(@Validated(QueryGroup.class) ClientBankInfoOsBo bo, Query query) {
        return R.data(clientBankInfoOsService.queryPageList(Condition.getPage(query), bo));
    }

    /**
     * 查询海外银行信息记录列表
     */
    @ApiOperation("查询海外银行信息记录列表")
    //@SaCheckPermission("withdraw:bankInfoOs:queryList")
    @GetMapping("/queryList")
    public R<List<ClientBankInfoOs>> queryList(@Validated(QueryGroup.class) ClientBankInfoOsBo bo) {
        List<ClientBankInfoOs> list = clientBankInfoOsService.queryList(bo);
        return R.data(list);
    }

    /**
     * 获取海外银行信息记录详细信息
     */
    @ApiOperation("获取海外银行信息记录详细信息")
    //@SaCheckPermission("withdraw:bankInfoOs:query")
    @GetMapping("/{id}")
    public R<ClientBankInfoOs> getInfo(@ApiParam("主键")
                                         @NotNull(message = "主键不能为空")
                                         @PathVariable("id") Long id) {
        return R.data(clientBankInfoOsService.getById(id));
    }

}
