package com.minigod.zero.bpmn.module.fundTrans.controller;

import com.minigod.zero.bpmn.module.common.group.AddGroup;
import com.minigod.zero.bpmn.module.fundTrans.domain.ClientFundTransInfo;
import com.minigod.zero.bpmn.module.fundTrans.domain.bo.FundTransInfoBo;
import com.minigod.zero.bpmn.module.fundTrans.service.ClientFundTransInfoService;
import com.minigod.zero.bpmn.module.fundTrans.service.impl.ClientFundTransInfoServiceImpl;
import com.minigod.zero.core.boot.ctrl.ZeroController;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.tool.api.R;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.Valid;

/**
 * 资金调拨申请记录(client_fund_trans_info)表控制层
 *
 * @author xxxxx
 */
@RestController
@AllArgsConstructor
@RequestMapping(AppConstant.BACK_API_PREFIX + "/fundTrans/info")
public class ClientFundTransInfoController extends ZeroController {
    /**
     * 服务对象
     */
    @Autowired
    private ClientFundTransInfoService clientFundTransInfoService;

    @ApiOperation("后台提交调拨申请")
    @PostMapping("/submitInfo")
    public R<?> submitInfo(@RequestBody @Validated(AddGroup.class) FundTransInfoBo bo) {
        clientFundTransInfoService.submitInfo(bo);
        return R.success();
    }

}
