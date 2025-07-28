package com.minigod.zero.manage.controller;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import com.minigod.zero.manage.entity.ArticleLibraryEntity;
import com.minigod.zero.manage.vo.ArticleLibraryVO;
import com.minigod.zero.manage.vo.request.ArticleLibraryRequest;
import com.minigod.zero.manage.wrapper.ArticleLibraryWrapper;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.minigod.zero.core.boot.ctrl.ZeroController;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.mp.support.Condition;
import com.minigod.zero.core.mp.support.Query;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.utils.BeanUtil;
import com.minigod.zero.core.tool.utils.Func;
import com.minigod.zero.manage.service.IArticleLibraryService;
import cn.hutool.core.util.IdUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;

/**
 * 文章库 控制器
 *
 * @author 掌上智珠
 * @since 2023-03-22
 */
@RestController
@AllArgsConstructor
@RequestMapping(AppConstant.BACK_API_PREFIX + "/articleLibrary")
@Api(value = "协议文章库", tags = "协议文章库接口")
public class ArticleLibraryController extends ZeroController {

	private final IArticleLibraryService articleLibraryService;

	/**
	 * 协议文章库 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "协议文章库详情", notes = "传入ID")
	public R<ArticleLibraryVO> detail(ArticleLibraryEntity articleLibrary) {
		articleLibrary.setTenantId(AuthUtil.getTenantId());
		ArticleLibraryEntity detail = articleLibraryService.getOne(Condition.getQueryWrapper(articleLibrary));
		return R.data(ArticleLibraryWrapper.build().entityVO(detail));
	}

	/**
	 * 协议文章库列表查询
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "协议文章库列表查询", notes = "传入articleLibrary")
	public R<List<ArticleLibraryEntity>> list(ArticleLibraryEntity articleLibrary) {
		articleLibrary.setTenantId(AuthUtil.getTenantId());
		return R.data(articleLibraryService.findByTitle(articleLibrary));
	}

	/**
	 * 文章库 自定义分页
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入articleLibrary")
	public R<IPage<ArticleLibraryVO>> page(ArticleLibraryVO articleLibrary, Query query) {
		articleLibrary.setTenantId(AuthUtil.getTenantId());
		IPage<ArticleLibraryVO> pages = articleLibraryService.selectArticleLibraryPage(Condition.getPage(query), articleLibrary);
		return R.data(pages);
	}

	/**
	 * 文章库 新增或修改
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入articleLibrary")
	public R<Object> submit(@Valid @RequestBody ArticleLibraryRequest libraryRequest) {
		ArticleLibraryEntity articleLibrary = new ArticleLibraryEntity();
		if (null == libraryRequest.getId()) {
			BeanUtil.copyProperties(libraryRequest, articleLibrary);
			long id = IdUtil.getSnowflakeNextId();
			articleLibrary.setId(id);
			articleLibrary.setKeyId(articleLibrary.getKeyId());
			articleLibrary.setStatus(1);
			articleLibrary.setCreateUser(AuthUtil.getUserId());
			articleLibrary.setUpdateUser(AuthUtil.getUserId());
			articleLibrary.setCreateTime(new Date());
			articleLibrary.setCreateDept(Func.firstLong(AuthUtil.getDeptId()));

			return R.status(articleLibraryService.saveArticleLibrary(articleLibrary));
		} else {
			BeanUtil.copyProperties(libraryRequest, articleLibrary);
			articleLibrary.setKeyId(articleLibrary.getKeyId());
			articleLibrary.setUpdateUserName(AuthUtil.getUserName());
			return R.status(articleLibraryService.updateArticleLibrary(articleLibrary));
		}
	}

	/**
	 * 文章库 删除
	 */
	@DeleteMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R<Object> remove(@ApiParam(value = "主键集合", required = true) @RequestBody List<Long> ids) {
		return R.status(articleLibraryService.deleteArticleByIds(ids));
	}
}
