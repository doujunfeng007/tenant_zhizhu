package com.minigod.zero.bpmn.module.deposit.controller;

import com.minigod.zero.bpmn.module.deposit.service.FundDepositImageService;
import com.minigod.zero.bpmn.module.deposit.service.IClientFundService;
import com.minigod.zero.bpmn.module.deposit.vo.SecImgRespVo;
import com.minigod.zero.core.log.annotation.ApiLog;
import com.minigod.zero.core.tool.api.R;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.minigod.zero.core.boot.ctrl.ZeroController;
import com.minigod.zero.core.launch.constant.AppConstant;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

/**
 * 入金凭证表(client_fund_deposit_image)表控制层
 *
 * @author chenyu
 */
@Validated
@RestController
@AllArgsConstructor
@Api(value = "入金凭证表", tags = "入金凭证表接口")
@RequestMapping(AppConstant.BACK_API_PREFIX + "/deposit/fundDepositImage")
public class FundDepositImageController extends ZeroController {
    private final FundDepositImageService fundDepositImageService;
    private final IClientFundService clientFundService;


    @RequestMapping(value = "/deposit/save_img")
    @ApiLog("保存入金凭证")
    public R<SecImgRespVo> depositFundImg(@RequestParam("file") MultipartFile multipartFile,
										  @RequestParam("custId")Long custId) {
        return R.data(clientFundService.saveSecAccImg(multipartFile,custId));
    }
}
