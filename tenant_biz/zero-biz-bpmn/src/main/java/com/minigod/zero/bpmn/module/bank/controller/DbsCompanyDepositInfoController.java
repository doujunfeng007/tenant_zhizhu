package com.minigod.zero.bpmn.module.bank.controller;
import com.minigod.zero.bpmn.module.bank.service.DbsCompanyDepositInfoService;
import org.springframework.web.bind.annotation.*;

import com.minigod.zero.core.boot.ctrl.ZeroController;
import com.minigod.zero.core.launch.constant.AppConstant;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;

/**
* dbs公司入金信息(dbs_company_deposit_info)表控制层
*
* @author chenyu
*/
@Validated
@RestController
@AllArgsConstructor
@Api(value = "dbs公司入金信息", tags = "dbs公司入金信息接口")
@RequestMapping(AppConstant.BACK_API_PREFIX+"/deposit/dbsCompanyDepositInfo")
public class DbsCompanyDepositInfoController  extends ZeroController {
        private final DbsCompanyDepositInfoService  dbsCompanyDepositInfoService;

}
