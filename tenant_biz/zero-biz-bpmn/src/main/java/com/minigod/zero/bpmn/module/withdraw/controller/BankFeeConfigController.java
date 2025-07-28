package com.minigod.zero.bpmn.module.withdraw.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.biz.common.enums.WithdrawKeyConstants;
import com.minigod.zero.bpmn.module.withdraw.service.IBankFeeConfigService;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.mp.support.Condition;
import com.minigod.zero.core.mp.support.Query;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.bpmn.module.common.group.AddGroup;
import com.minigod.zero.bpmn.module.common.group.EditGroup;
import com.minigod.zero.bpmn.module.common.group.QueryGroup;
import com.minigod.zero.bpmn.module.withdraw.bo.BankFeeConfigBo;
import com.minigod.zero.bpmn.module.withdraw.entity.BankFeeConfig;
import com.minigod.zero.bpmn.module.withdraw.enums.SystemCommonEnum;
import com.minigod.zero.system.feign.IDictBizClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import java.util.Arrays;
import java.util.List;

/**
 * 取款手续费Controller
 *
 * @author chenyu
 * @date 2023-04-06
 */
@Validated
@Api(value = "取款手续费控制器", tags = {"取款手续费管理"})
@RequiredArgsConstructor
@RestController
@RequestMapping(AppConstant.BACK_API_PREFIX +"/withdraw/bankFeeConfig")
public class BankFeeConfigController {

    private final IBankFeeConfigService iBankFeeConfigService;

    private final IDictBizClient dictBizClient;

    /**
     * 查询取款手续费列表
     */
    @ApiOperation("查询取款手续费列表")
    @GetMapping("/list")
    public R<List<BankFeeConfig>> list(@Validated(QueryGroup.class) BankFeeConfigBo bo) {
        // 默认已启用的有续费
        bo.setActive(SystemCommonEnum.YesNo.YES.getIndex());
        return R.data(iBankFeeConfigService.queryList(bo));
    }

    /**
     * 查询取款手续费列表(分页)
     */
    @ApiOperation("查询取款手续费列表(分页)")
    @GetMapping("/listPage")
    public R<IPage<BankFeeConfig>> listPage(@Validated(QueryGroup.class) BankFeeConfigBo bo, Query pageQuery) {
        return R.data(iBankFeeConfigService.queryPageList(Condition.getPage(pageQuery),bo));
    }

    /**
     * 查询取款手续费列表
     */
    @ApiOperation("查询单个取款手续费")
    @GetMapping("/getChargeFee")
    public R<BankFeeConfig> getChargeFee(@Validated(QueryGroup.class) BankFeeConfigBo bo) {
        // 默认已启用的有续费
        bo.setActive(SystemCommonEnum.YesNo.YES.getIndex());

        // 获取全局手续费开关配置
       R<String> r =  dictBizClient.getLabel(WithdrawKeyConstants.DICT_KEY,WithdrawKeyConstants.FUND_WITHDRAW_FEE_SWITCH);
       if(r.isSuccess()){
           String feeSwitch = r.getData();
           if(SystemCommonEnum.YesNo.YES.getIndexStr().equals(feeSwitch)){
               bo.setBankCode(WithdrawKeyConstants.BANK_FEE_CONFIG_ALL);
           }
       }
        // 默认返回一条
        BankFeeConfig bankFeeConfigVo = null;
        List<BankFeeConfig> lstData = iBankFeeConfigService.queryList(bo);
        if (null != lstData && lstData.size() > 0) {
            bankFeeConfigVo = lstData.get(0);
        } else {
            bankFeeConfigVo = new BankFeeConfig();
        }
        return R.data(bankFeeConfigVo);
    }

    /**
     * 查询手续费银行列表
     */
    @ApiOperation("查询手续费银行列表")
    @GetMapping("/queryBankList")
    public List<BankFeeConfig> queryBankList(BankFeeConfigBo bo) {
        return iBankFeeConfigService.queryList(bo);
    }

    /**
     * 查询手续费银行列表 -所有银行
     */
    @ApiOperation("查询手续费银行列表 -所有银行")
    @GetMapping("/bankList")
    public List<BankFeeConfig> bankList(BankFeeConfigBo bo) {
        return iBankFeeConfigService.bankList(bo);
    }

    /**
     * 新增取款手续费
     */
    @ApiOperation("新增取款手续费")
    @PostMapping()
    public R<Long> add(@Validated(AddGroup.class) @RequestBody BankFeeConfigBo bo) {
        return R.data(iBankFeeConfigService.commitApply(bo));
    }

    /**
     * 修改取款手续费
     */
    @ApiOperation("修改取款手续费")
    @PostMapping("/edit")
    public R<Long> edit(@Validated(EditGroup.class) @RequestBody BankFeeConfigBo bo) {
        return R.data(iBankFeeConfigService.updateFeeConfig(bo));
    }

    /**
     * 删除取款手续费
     */
    @ApiOperation("删除取款手续费")
    @PostMapping("/{ids}")
    public R<Void> remove(@ApiParam("主键串")
                          @NotEmpty(message = "主键不能为空")
                          @PathVariable String[] ids) {
        iBankFeeConfigService.deleteWithValidByIds(Arrays.asList(ids), true);
        return R.success();
    }

}
