
package com.minigod.zero.bpmn.module.stock.controller;

import com.minigod.zero.bpmn.module.common.group.AddGroup;
import com.minigod.zero.bpmn.module.fundTrans.domain.bo.FundTransInfoBo;
import com.minigod.zero.bpmn.module.fundTrans.service.ClientFundTransInfoService;
import com.minigod.zero.bpmn.module.stock.domain.bo.CustomerAccountStockBO;
import com.minigod.zero.bpmn.module.stock.service.ICustomerAccountStockInfoService;
import com.minigod.zero.core.boot.ctrl.ZeroController;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.tool.api.R;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 股票增开申请记录(customer_account_stock_info)表控制层
 *
 * @author xxxxx
 */
@RestController
@AllArgsConstructor
@RequestMapping(AppConstant.BACK_API_PREFIX + "/stock/info")
public class CustomerAccountStockInfoController extends ZeroController {
    /**
     * 服务对象
     */
    @Autowired
    private ICustomerAccountStockInfoService customerAccountStockInfoService;

    @ApiOperation("后台提交增开股票申请")
    @PostMapping("/submitInfo")
    public R<?> submitInfo(@RequestBody @Validated(AddGroup.class) CustomerAccountStockBO bo) {
		customerAccountStockInfoService.submitInfo(bo);
        return R.success();
    }

}
