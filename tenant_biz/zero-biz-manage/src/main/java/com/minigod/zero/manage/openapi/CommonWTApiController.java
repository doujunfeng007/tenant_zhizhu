package com.minigod.zero.manage.openapi;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.minigod.zero.core.mp.support.Condition;
import com.minigod.zero.manage.entity.ArticleLibraryEntity;
import com.minigod.zero.manage.vo.ArticleLibraryVO;
import com.minigod.zero.manage.service.CommonApiService;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.cust.apivo.resp.UpdateAppInfoVO;
import com.minigod.zero.manage.service.IArticleLibraryService;
import com.minigod.zero.manage.wrapper.ArticleLibraryWrapper;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 公共调用控制器
 *
 * @author 掌上智珠
 * @since 2023-03-17
 */

@Slf4j
@RestController
@RequestMapping(AppConstant.OPEN_API_PREFIX + "/wt/common_api")
public class CommonWTApiController {

	@Resource
	private CommonApiService commonApiService;
	@Resource
	private IArticleLibraryService articleLibraryService;
	@GetMapping("/app_update_check")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "APP检测更新", notes = "传入UpdateCheckVO")
	public R<UpdateAppInfoVO> appUpdateCheck() {
		return commonApiService.appUpdateCheck();
	}

	/**
	 * 文章库 详情
	 */
	@GetMapping("/doc/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入articleLibrary")
	public R<ArticleLibraryVO> detail(ArticleLibraryEntity articleLibrary) {
		ArticleLibraryEntity detail = articleLibraryService.getOne(Condition.getQueryWrapper(articleLibrary));
		return R.data(ArticleLibraryWrapper.build().entityVO(detail));
	}
}
