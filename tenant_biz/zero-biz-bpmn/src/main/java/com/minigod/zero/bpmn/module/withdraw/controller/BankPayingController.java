package com.minigod.zero.bpmn.module.withdraw.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.core.mp.support.Query;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.biz.common.enums.WithdrawKeyConstants;
import com.minigod.zero.bpmn.module.withdraw.service.IBankPayingService;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.bpmn.module.common.group.QueryGroup;
import com.minigod.zero.bpmn.module.withdraw.bo.BankPayingBo;
import com.minigod.zero.bpmn.module.withdraw.entity.BankPaying;
import com.minigod.zero.bpmn.module.withdraw.enums.SystemCommonEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 公司付款银行信息控制器
 *
 * @author chenyu
 * @date 2023-04-06
 */
@Validated
@Api(value = "公司付款银行信息控制器", tags = {"公司付款银行信息管理"})
@RequiredArgsConstructor
@RestController
@RequestMapping(AppConstant.BACK_API_PREFIX + "/withdraw/bankPaying")
public class BankPayingController {

	private final IBankPayingService iBankPayingService;

	/**
	 * 查询付款银行列表
	 */
	@ApiOperation("查询付款银行列表,去掉重复值DISTINCT bank_code, bank_name")
	@GetMapping("/listPayBank")
	public R<List<BankPaying>> listPayBank(@Validated(QueryGroup.class) BankPayingBo bo) {
		// 获取资金账号
		String serviceType = WithdrawKeyConstants.PT_ACCT_TYPE_SEC;
		if (false) {
			serviceType = WithdrawKeyConstants.PT_ACCT_TYPE_FUT;
		}
		bo.setServiceType(serviceType);
		// 可用
		bo.setActive(SystemCommonEnum.YesNo.YES.getIndex());
		return R.data(iBankPayingService.queryBankList(bo));
	}

	/**
	 * 查询公司付款银行信息列表
	 */
	@ApiOperation("查询公司付款银行信息列表")
	@GetMapping("/list")
	public R<List<BankPaying>> list(@Validated(QueryGroup.class) BankPayingBo bo) {
		// 获取资金账号
		String serviceType = WithdrawKeyConstants.PT_ACCT_TYPE_SEC;
		if (false) {
			serviceType = WithdrawKeyConstants.PT_ACCT_TYPE_FUT;
		}
		bo.setServiceType(serviceType);
		// 可用
		bo.setActive(SystemCommonEnum.YesNo.YES.getIndex());
		return R.data(iBankPayingService.queryList(bo));
	}

	/**
	 * 分页查询公司付款银行信息列表
	 */
	@ApiOperation("分页查询公司付款银行信息列表")
	@GetMapping("/pageList")
	public R<IPage<BankPaying>> pageList(Query query, BankPayingBo bo) {
		// 获取资金账号
		String serviceType = WithdrawKeyConstants.PT_ACCT_TYPE_SEC;
		if (false) {
			serviceType = WithdrawKeyConstants.PT_ACCT_TYPE_FUT;
		}
		bo.setServiceType(serviceType);
		return R.data(iBankPayingService.queryPageList(query, bo));
	}

	/**
	 * 查询公司付款银行信息详情
	 */
	@ApiOperation("查询公司付款银行信息详情")
	@GetMapping("/get")
	public R<BankPaying> get(BankPayingBo bo) {
		return R.data(iBankPayingService.queryEntity(bo));
	}

	/**
	 * 根据主键ID查询公司付款银行信息
	 */
	@ApiOperation("根据主键ID查询公司付款银行信息")
	@GetMapping("/getById")
	public R<BankPaying> getById(Long id) {
		return R.data(iBankPayingService.queryById(id));
	}

	/**
	 * 插入公司付款银行信息
	 */
	@ApiOperation("插入公司付款银行信息")
	@PostMapping("/insert")
	public R<Boolean> insert(@RequestBody BankPayingBo bo) {
		return R.data(iBankPayingService.insertByBo(bo));
	}

	/**
	 * 修改公司付款银行信息
	 */
	@ApiOperation("修改公司付款银行信息")
	@PutMapping("/update")
	public R<Boolean> update(@RequestBody BankPayingBo bo) {
		return R.data(iBankPayingService.updateUserBo(bo));
	}

	/**
	 * 批量删除公司付款银行信息
	 */
	@ApiOperation("批量删除公司付款银行信息")
	@DeleteMapping("/delete")
	public R<Boolean> delete(@RequestBody List<Long> ids) {
		return R.data(iBankPayingService.deleteWithValidByIds(ids, true));
	}
}
