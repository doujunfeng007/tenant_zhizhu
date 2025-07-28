package com.minigod.zero.bpmn.module.fundTrans.openapi;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.biz.common.vo.ipo.PageDto;
import com.minigod.zero.bpmn.module.common.group.AddGroup;
import com.minigod.zero.bpmn.module.fundTrans.domain.ClientFundTransInfo;
import com.minigod.zero.bpmn.module.fundTrans.domain.bo.FundTransInfoBo;
import com.minigod.zero.bpmn.module.fundTrans.domain.bo.FundTransQuery;
import com.minigod.zero.bpmn.module.fundTrans.domain.vo.ClientFundTransApplicationVO;
import com.minigod.zero.bpmn.module.fundTrans.domain.vo.ClientFundTransInfoVO;
import com.minigod.zero.bpmn.module.fundTrans.service.ClientFundTransInfoService;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.mp.support.Condition;
import com.minigod.zero.core.mp.support.Query;
import com.minigod.zero.core.tool.api.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName: FundTransController
 * @Description:
 * @Author chenyu
 * @Date 2024/12/13
 * @Version 1.0
 */
@RestController
@AllArgsConstructor
@RequestMapping(AppConstant.OPEN_API_PREFIX + "/fund_trans_api")
@Api(value = "调拨相关", tags = "调拨相关接口")
public class FundTransController {

    private final ClientFundTransInfoService clientFundTransInfoService;

    @ApiOperation("客户提交调拨申请")
    @PostMapping("/submitInfo")
    public R<?> submitInfo(@RequestBody @Validated(AddGroup.class) FundTransInfoBo bo) {
        clientFundTransInfoService.submitInfo(bo);
        return R.success();
    }


	@ApiOperation("客户调拨记录查询")
	@GetMapping("/page")
	public R<IPage<ClientFundTransInfoVO>> page(Query query , FundTransQuery bo) {
		return R.data(clientFundTransInfoService.queryInfoPage(Condition.getPage(query),bo));
	}
}
