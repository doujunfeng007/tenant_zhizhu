package com.minigod.zero.bpmn.module.account.openapi;

import com.minigod.zero.bpmn.module.common.service.impl.SysItemConfigService;
import com.minigod.zero.bpmn.module.common.vo.SysItemsAndSubItemConfigVO;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.utils.WebUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 客户投资知识问卷调查
 */
@RestController
@AllArgsConstructor
@RequestMapping(AppConstant.OPEN_API_PREFIX + "/open-api/questionnaire")
@Api(value = "客户投资知识问卷调查Open-API服务", tags = "客户投资知识问卷调查Open-API服务")
public class CustomerQuestionnaireOpenController {
	private final SysItemConfigService sysItemConfigService;

	@ApiOperation("根据配置项类型查询客户投资知识问卷调查配置项和配置子项")
	@GetMapping("/select/items-subitems/type-lang")
	public R<SysItemsAndSubItemConfigVO> selectItemsAndSubItemByItemType(@RequestParam("itemType") String itemType) {
		String language = WebUtil.getLanguage();
		return R.data(sysItemConfigService.selectItemsAndSubItemByItemType(itemType, language));
	}
}
