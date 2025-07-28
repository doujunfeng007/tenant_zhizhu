package com.minigod.zero.bpmn.module.deposit.controller;

import com.minigod.zero.bpmn.module.deposit.service.BankCardInfoService;
import com.minigod.zero.core.tool.api.R;
import org.springframework.web.bind.annotation.*;

import com.minigod.zero.core.boot.ctrl.ZeroController;
import com.minigod.zero.core.launch.constant.AppConstant;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;

/**
 * 银行卡管理记录表(client_bank_card_info)表控制层
 *
 * @author chenyu
 */
@RestController
@AllArgsConstructor
@RequestMapping(AppConstant.BACK_API_PREFIX + "/deposit/bankCardInfo")
public class BankCardInfoController extends ZeroController {
    private final BankCardInfoService bankCardInfoService;
}
