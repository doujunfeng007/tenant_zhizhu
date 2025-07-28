package com.minigod.zero.bpm.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.minigod.zero.bpm.service.IBpmSecuritiesInfoService;
import com.minigod.zero.bpm.vo.BpmSecuritiesInfoVO;
import com.minigod.zero.core.boot.ctrl.ZeroController;
import com.minigod.zero.core.mp.support.Condition;
import com.minigod.zero.core.mp.support.Query;
import com.minigod.zero.core.tool.api.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 证券客户资料信息表 控制器
 *
 * @author eric
 * @since 2024-05-06 21:52:09
 */
@RestController
@AllArgsConstructor
@RequestMapping("api/bpmSecuritiesInfo")
@Api(value = "证券客户资料信息表", tags = "证券客户资料信息表接口")
public class BpmSecuritiesInfoController extends ZeroController {

	private final IBpmSecuritiesInfoService bpmSecuritiesInfoService;

	/**
	 * 证券客户资料信息表-分页查询
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页带条件查询", notes = "传入bpmSecuritiesInfoVO")
	public R<IPage<BpmSecuritiesInfoVO>> list(BpmSecuritiesInfoVO bpmSecuritiesInfoVO, Query query) {
		IPage<BpmSecuritiesInfoVO> pages = bpmSecuritiesInfoService.selectBpmSecuritiesInfoPage(Condition.getPage(query), bpmSecuritiesInfoVO);
		return R.data(pages);
	}
}
