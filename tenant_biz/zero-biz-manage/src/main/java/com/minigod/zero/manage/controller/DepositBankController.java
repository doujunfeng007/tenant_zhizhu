package com.minigod.zero.manage.controller;

import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.manage.service.ICashDepositBankService;
import com.minigod.zero.manage.vo.DepositBankVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/8/20 10:28
 * @description：
 */
@RestController
@RequestMapping(AppConstant.BACK_API_PREFIX + "/cash")
public class DepositBankController {
	@Resource
	private ICashDepositBankService cashDepositBankService;

	@GetMapping("/all_deposit_bank_list")
	public R<List<DepositBankVO>> allDepositBankList(@RequestParam("bankType") Integer bankType) {
		return R.data(cashDepositBankService.allDepositBankList(bankType));
	}
}
