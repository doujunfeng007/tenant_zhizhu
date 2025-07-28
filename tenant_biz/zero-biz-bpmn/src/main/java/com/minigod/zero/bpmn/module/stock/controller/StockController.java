package com.minigod.zero.bpmn.module.stock.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.minigod.zero.bpmn.module.constant.OpenAccountMessageConstant;
import com.minigod.zero.bpmn.module.feign.ICustomerInfoClient;
import com.minigod.zero.bpmn.module.feign.vo.CustOpenAccountInfoVO;
import com.minigod.zero.bpmn.module.stock.domain.bo.StockQueryInfoBO;
import com.minigod.zero.bpmn.module.stock.service.ICustomerAccountStockApplicationService;
import com.minigod.zero.core.boot.ctrl.ZeroController;
import com.minigod.zero.core.i18n.utils.I18nUtil;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.tool.api.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author chen
 * @ClassName StockController.java
 * @Description 股票的相关接口
 * @createTime 2024年12月17日 19:26:00
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(AppConstant.BACK_API_PREFIX + "/stock")
@Api(value = "股票的相关API服务", tags = "股票的相关API服务")
public class StockController extends ZeroController {

	@Autowired
	private ICustomerInfoClient iCustomerInfoClient;

	@Autowired
	private ICustomerAccountStockApplicationService customerAccountStockApplicationService;

	/**
	 * 获取用户开户资料
	 */
	@GetMapping("/openAcountInfo")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "获取开户资料", notes = "")
	public R<CustOpenAccountInfoVO> detail(StockQueryInfoBO stockQueryInfoBO) {
		R<CustOpenAccountInfoVO> result = iCustomerInfoClient.selectCustOpenAccountInfoByPhone(stockQueryInfoBO.getPhoneArea(), stockQueryInfoBO.getPhoneNumber());
		CustOpenAccountInfoVO custOpenAccountInfoVO = result.getData();
		if(null == custOpenAccountInfoVO.getCustomerBasicInfo()){
			return R.success(I18nUtil.getMessage(OpenAccountMessageConstant.NOT_OPEN_ACCOUNT));
		}
		return result;
	}


}
