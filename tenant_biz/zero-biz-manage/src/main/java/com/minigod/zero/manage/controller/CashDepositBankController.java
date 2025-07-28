package com.minigod.zero.manage.controller;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.minigod.zero.manage.dto.CashDepositBankDTO;
import com.minigod.zero.manage.dto.CashDepositBankSaveDTO;
import com.minigod.zero.manage.entity.CashDepositBankEntity;
import com.minigod.zero.manage.service.ICashDepositBankService;
import com.minigod.zero.core.boot.ctrl.ZeroController;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.mp.support.Condition;
import com.minigod.zero.core.mp.support.Query;
import com.minigod.zero.core.secure.ZeroUser;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.core.tool.api.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;

/**
 * 入金银行配置模块
 *
 * @author 掌上智珠
 * @since 2023-05-18
 */
@RestController
@RequestMapping(AppConstant.BACK_API_PREFIX + "/cashDepositBank")
@Api(value = "入金银行信息维护表", tags = "入金银行信息维护表接口")
@Slf4j
public class CashDepositBankController extends ZeroController {

    @Resource
    private ICashDepositBankService cashDepositBankService;

    /**
     * 入金银行卡配置 分页（原/deposit_bank_info）
     */
    @GetMapping("/list")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "入金银行卡配置 分页")
    public R<IPage<CashDepositBankEntity>> list(CashDepositBankDTO cashDepositBankDTO, Query query) {
        LambdaQueryWrapper<CashDepositBankEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Objects.nonNull(cashDepositBankDTO.getBankType()), CashDepositBankEntity::getBankType, cashDepositBankDTO.getBankType());
        if (StringUtils.isNotBlank(cashDepositBankDTO.getStartDate())) {
            Date date = DateUtil.parse(cashDepositBankDTO.getStartDate());
            queryWrapper.ge(CashDepositBankEntity::getUpdateTime, date);
        }
        if (StringUtils.isNotBlank(cashDepositBankDTO.getEndDate())) {
            Date date = DateUtil.parse(cashDepositBankDTO.getEndDate());
            queryWrapper.le(CashDepositBankEntity::getUpdateTime, DateUtil.endOfDay(date));
        }
        if (Objects.nonNull(cashDepositBankDTO.getIsShow()) && cashDepositBankDTO.getIsShow() != -1) {
            queryWrapper.eq(CashDepositBankEntity::getIsShow, cashDepositBankDTO.getIsShow());
        }
        queryWrapper.orderByDesc(CashDepositBankEntity::getSort);
        IPage<CashDepositBankEntity> cashDepositBankEntityIPage = cashDepositBankService.getBaseMapper().selectPage(Condition.getPage(query), queryWrapper.orderByDesc(CashDepositBankEntity::getCreateTime));
        return R.data(cashDepositBankEntityIPage);
    }

    /**
     * 入金银行卡配置编辑页面详情（原/deposit_bank_update_ui）
     */
    @GetMapping("/detail")
    @ApiOperationSupport(order = 2)
    @ApiOperation(value = "入金银行信息编辑页面详情")
    public R<CashDepositBankEntity> detail(Long id) {
        CashDepositBankEntity detail = cashDepositBankService.getById(id);
        return R.data(detail);
    }

    /**
     * 入金银行卡配置编辑页-收款信息-币种列表（原/depositBankSupportAdd）
     */
    @GetMapping("/currencyList")
    @ApiOperationSupport(order = 3)
    @ApiOperation(value = "入金银行卡配置编辑页-收款信息-币种列表")
    public R<List<Map>> currencyList() {
        List<Map> currencyList = new ArrayList<>();
        Map<String, Object> mapHk = new HashMap<>();
        mapHk.put("type", 1);
        mapHk.put("name", "港币");
        currencyList.add(mapHk);
        Map<String, Object> mapUSD = new HashMap<>();
        mapUSD.put("type", 2);
        mapUSD.put("name", "美元");
        currencyList.add(mapUSD);
        Map<String, Object> mapCNY = new HashMap<>();
        mapCNY.put("type", 3);
        mapCNY.put("name", "人民币");
        currencyList.add(mapCNY);
        return R.data(currencyList);
    }

    /**
     * 入金银行卡配置 新增页/编辑页 提交按钮
     *
     */
    @PostMapping("/submit")
    @ApiOperationSupport(order = 4)
    @ApiOperation(value = "入金银行卡配置 新增页/编辑页 提交按钮")
    public R<Void> depositBankAdd(@RequestBody @Validated CashDepositBankSaveDTO dto) {
		String userName = null;
		try {
			ZeroUser user = AuthUtil.getUser();
			userName = user.getUserName();
		} catch (Exception e) {
			log.error("获取ZeroUser异常", e.getMessage(), e);
		}
		dto.setFounder(userName);
		cashDepositBankService.save(dto);
        return R.success();
    }

}
