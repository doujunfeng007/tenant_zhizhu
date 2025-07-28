package com.minigod.zero.manage.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.minigod.zero.manage.entity.CashPayeeBankDetailEntity;
import com.minigod.zero.manage.entity.CashPayeeBankEntity;
import com.minigod.zero.manage.entity.CashSupportCurrencyBankEntity;
import com.minigod.zero.manage.service.ICashPayeeBankDetailService;
import com.minigod.zero.manage.service.ICashSupportCurrencyBankService;
import com.minigod.zero.manage.service.ICashPayeeBankService;
import com.minigod.zero.core.boot.ctrl.ZeroController;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.mp.support.Condition;
import com.minigod.zero.core.mp.support.Query;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.support.Kv;
import com.minigod.zero.core.tool.utils.CollectionUtil;
import com.minigod.zero.user.entity.User;
import com.minigod.zero.user.feign.IUserClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;

/**
 * 收款、付款银行信息控制器
 *
 * @author 掌上智珠
 * @since 2023-05-18
 */
@RestController
@RequestMapping(AppConstant.BACK_API_PREFIX + "/cashPayeeBank")
@Api(value = "出金付款、入金收款银行信息配置", tags = "入出金付款、入金收款银行信息配置接口")
public class CashPayeeBankController extends ZeroController {
	@Resource
	private ICashPayeeBankService cashPayeeBankService;
	@Resource
	private ICashSupportCurrencyBankService cashSupportCurrencyBankService;
	@Resource
	private ICashPayeeBankDetailService cashPayeeBankDetailService;
	@Resource
	private IUserClient userClient;

	/**
	 * 出金付款、入金收款银行信息配置分页查询
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "出金付款、入金收款银行信息配置分页查询")
	public R<IPage<CashPayeeBankEntity>> list(CashPayeeBankEntity cashPayeeBank, Query query) {
		LambdaQueryWrapper queryWrapper = new LambdaQueryWrapper(cashPayeeBank);
		queryWrapper.orderByDesc("createTime");
		IPage<CashPayeeBankEntity> pages = cashPayeeBankService.page(Condition.getPage(query), Condition.getQueryWrapper(cashPayeeBank));
		return R.data(pages);
	}

	/**
	 * 出金付款、入金收款银行编辑页 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "出金付款、入金收款银行编辑页详情")
	public R<Kv> detail(Long id) {
		CashPayeeBankEntity payeeBank = cashPayeeBankService.getById(id);
		List<CashPayeeBankDetailEntity> payeeBankDetailList = new LambdaQueryChainWrapper<>(cashPayeeBankDetailService.getBaseMapper())
			.eq(CashPayeeBankDetailEntity::getPayeeBankId, id)
			.list();
		Map<String, Set<Integer>> supportTypeCurrencyMap = new HashMap<>();
		for (CashPayeeBankDetailEntity payeeBankDetail : payeeBankDetailList) {
			Set<Integer> currency = supportTypeCurrencyMap.get(payeeBankDetail.getSupportType());
			if (CollectionUtil.isEmpty(currency)) {
				currency = new HashSet<>();
				currency.add(payeeBankDetail.getCurrency());
				supportTypeCurrencyMap.put(payeeBankDetail.getSupportType(), currency);
			} else {
				currency.add(payeeBankDetail.getCurrency());
			}
		}
		return R.data(Kv.create().set("payeeBank", payeeBank).set("supportTypeCurrency", supportTypeCurrencyMap));
	}

	/**
	 * 出金付款、入金收款银行 新增/编辑
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "出金付款、入金收款银行 新增/编辑")
	public R<CashPayeeBankEntity> update(@RequestBody CashPayeeBankEntity cashPayeeBank) {
		if (cashPayeeBank.getBankCode() == null){
			cashPayeeBank.setBankCode(cashPayeeBank.getBankId());
		}
		Long userId = AuthUtil.getUserId();
		String userName=null;
		if (null!=userId){
			R<User> userR = userClient.userInfoById(userId);
			if (userR.isSuccess()&&userR.getData()!=null){
				userName=userR.getData().getName();
			}
		}

		if (cashPayeeBank.getId() == null) {
			cashPayeeBank.setCreateTime(new Date());
			cashPayeeBank.setCreateUserName(userName);
			cashPayeeBank.setUpdateUserName(userName);
			cashPayeeBankService.saveCashPayeeBank(cashPayeeBank);
		} else {
			cashPayeeBank.setUpdateTime(new Date());
			cashPayeeBank.setUpdateUserName(userName);
			cashPayeeBankService.updateCashPayeeBank(cashPayeeBank);
		}
		return R.data(cashPayeeBank);
	}

	/**
	 * 出金付款、入金收款银行卡配置编辑页面-收款信息-选择币种-收款银行信息
	 * 原/depositBankPayeeAdd
	 */
	@GetMapping("/depositPayeeBankList")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "出金付款、入金收款银行卡配置编辑页面-收款信息-选择币种-收款银行信息")
	public R<Kv> depositBankPayeeAdd(Long id, Integer supportType, Integer currency) {
		Kv kv = Kv.create();
		List<Map<String, Object>> payeeBanks = cashPayeeBankService.getPayeeBankList(supportType, currency);
		kv.put("payeeBankList", payeeBanks);
		List<CashSupportCurrencyBankEntity> supportCurrencyBanks = new LambdaQueryChainWrapper<>(cashSupportCurrencyBankService.getBaseMapper())
			.eq(CashSupportCurrencyBankEntity::getDepositId, id)
			.eq(CashSupportCurrencyBankEntity::getSupportType, supportType)
			.eq(CashSupportCurrencyBankEntity::getCurrency, currency)
			.list();
		for (Map<String, Object> m : payeeBanks) {
			m.put("checked", false);
			m.put("default", false);
			for (CashSupportCurrencyBankEntity s : supportCurrencyBanks) {
				if (m.get("detailId") != null && s.getPayeeBankDetailId() != null
					&& m.get("accountType") != null && s.getAccountType() != null
					&& Long.valueOf(m.get("detailId").toString()).equals(s.getPayeeBankDetailId())
					&& Integer.parseInt(m.get("accountType").toString()) == s.getAccountType()) {
					m.put("checked", true);
					if (s.getIsDefault() != null && s.getIsDefault() == 1) {
						m.put("default", true);
					}
				}
			}
		}
		return R.data(kv);
	}
}
