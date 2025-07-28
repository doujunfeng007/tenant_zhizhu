package com.minigod.zero.manage.controller;

import cn.hutool.core.util.NumberUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.minigod.zero.core.tool.support.Kv;
import com.minigod.zero.manage.dto.CashWithdrawalsBankSaveDTO;
import com.minigod.zero.manage.entity.CashWithdrawalsBankEntity;
import com.minigod.zero.manage.entity.CashWithdrawalsSupportCurrencyBankEntity;
import com.minigod.zero.manage.mapper.CashWithdrawalsSupportCurrencyBankMapper;
import com.minigod.zero.manage.service.ICashWithdrawalsBankService;
import com.minigod.zero.manage.vo.request.WithdrawalsBankVo;
import com.minigod.zero.manage.service.ICashPayeeBankService;
import com.minigod.zero.core.boot.ctrl.ZeroController;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.mp.support.Condition;
import com.minigod.zero.core.mp.support.Query;
import com.minigod.zero.core.secure.ZeroUser;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.utils.DateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 出金银行卡配置模块
 *
 * @author 掌上智珠
 * @since 2023-05-18
 */
@RestController
@RequestMapping(AppConstant.BACK_API_PREFIX + "/cashWithdrawalsBank")
@Api(value = "出金银行卡配置表", tags = "出金银行卡配置表接口")
@Slf4j
public class CashWithdrawalsBankController extends ZeroController {

	@Resource
	private ICashWithdrawalsBankService cashWithdrawalsBankService;

	@Resource
	private ICashPayeeBankService cashPayeeBankService;

	@Resource
	private CashWithdrawalsSupportCurrencyBankMapper cashWithdrawalsSupportCurrencyBankMapper;

	/**
	 * 出金银行卡配置表 分页
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入cashWithdrawalsBank")
	public R<IPage<CashWithdrawalsBankEntity>> list(WithdrawalsBankVo vo, Query query) {
		LambdaQueryWrapper<CashWithdrawalsBankEntity> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(Objects.nonNull(vo.getBankType()), CashWithdrawalsBankEntity::getBankType, vo.getBankType());
		if (StringUtils.isNotBlank(vo.getStartDate())) {
			Date date = DateUtil.parseDate(vo.getStartDate());
			queryWrapper.ge(CashWithdrawalsBankEntity::getUpdateTime, date);
		}
		if (StringUtils.isNotBlank(vo.getEndDate())) {
			Date date = DateUtil.parseDate(vo.getEndDate());
			queryWrapper.le(CashWithdrawalsBankEntity::getUpdateTime, date);
		}
		if (vo.getIsShow() != -1) {
			queryWrapper.eq(CashWithdrawalsBankEntity::getIsShow, vo.getIsShow());
		}

		IPage<CashWithdrawalsBankEntity> pages = cashWithdrawalsBankService.page(Condition.getPage(query), queryWrapper.orderByDesc(CashWithdrawalsBankEntity::getCreateTime));
		return R.data(pages);
	}

	/**
	 * 出金银行卡配置表 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "详情")
	public R<CashWithdrawalsBankEntity> detail(Long id) {
		CashWithdrawalsBankEntity detail = cashWithdrawalsBankService.getById(id);
		return R.data(detail);
	}

	/**
	 * 出金银行卡配置表 新增/编辑
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "出金银行卡配置 新增/编辑")
	public R<Void> withdrawalsBankAdd(@RequestBody @Validated CashWithdrawalsBankSaveDTO dto) {
		if (StringUtils.isBlank(dto.getBankId())) {
			return R.fail("银行机构号不能为空。");
		}
		String[] bankIds = dto.getBankId().split(",");
		for (String bankId : bankIds) {
			if (!NumberUtil.isNumber(bankId.trim())) {
				return R.fail(String.format("银行机构号:%s格式错误, 限数字。", bankId));
			}
		}
		if (StringUtils.isBlank(dto.getSwiftCode())) {
			return R.fail("swift编码不能为空。");
		}
		String userName = null;
		try {
			ZeroUser user = AuthUtil.getUser();
			userName = user.getUserName();
		} catch (Exception e) {
			log.error("获取ZeroUser异常", e.getMessage(), e);
		}
		dto.setFounder(userName);
		cashWithdrawalsBankService.save(dto);
		return R.success();
	}

	/**
	 * 出金付款银行卡配置编辑页面-付款信息-选择币种-付款银行信息
	 */
	@GetMapping("/withdrawalsPaymentBankList")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "出金付款银行卡配置编辑页面-付款信息-选择币种-付款银行信息")
	public R<Kv> withdrawalsBankPayment(Long id, Integer supportType, Integer currency) {
		Kv kv = Kv.create();
		List<Map<String, Object>> paymentBanks = cashPayeeBankService.getPaymentBankList(supportType, currency);
		kv.put("paymentBankList", paymentBanks);
		LambdaQueryWrapper<CashWithdrawalsSupportCurrencyBankEntity> queryWrapper = new LambdaQueryWrapper<>();

		queryWrapper
			.eq(CashWithdrawalsSupportCurrencyBankEntity::getWithdrawalsId, id)
			.eq(CashWithdrawalsSupportCurrencyBankEntity::getSupportType, supportType)
			.eq(CashWithdrawalsSupportCurrencyBankEntity::getCurrency, currency);

		List<CashWithdrawalsSupportCurrencyBankEntity> supportCurrencyBanks = cashWithdrawalsSupportCurrencyBankMapper.selectList(queryWrapper);
		for (Map<String, Object> m : paymentBanks) {
			m.put("checked", false);
			m.put("default", false);
			for (CashWithdrawalsSupportCurrencyBankEntity s : supportCurrencyBanks) {
				if (m.get("detailId") != null && s.getPaymentBankDetailId() != null
					&& m.get("accountType") != null && s.getAccountType() != null
					&& Long.valueOf(m.get("detailId").toString()).equals(s.getPaymentBankDetailId())
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
