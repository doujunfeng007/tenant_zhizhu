package com.minigod.zero.bpmn.module.account.controller;

import com.minigod.zero.core.launch.constant.AppConstant;
import io.swagger.annotations.Api;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import com.minigod.zero.core.boot.ctrl.ZeroController;


import com.minigod.zero.core.mp.support.Condition;
import com.minigod.zero.core.mp.support.Query;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.utils.Func;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.metadata.IPage;

import com.minigod.zero.bpmn.module.account.entity.AccountOpenImageEntity;
import com.minigod.zero.bpmn.module.account.vo.AccountOpenImageVO;
import com.minigod.zero.bpmn.module.account.service.IAccountOpenImageService;

import java.util.List;


/**
 *  控制器
 *
 * @author Chill
 */
@RestController
@AllArgsConstructor
@RequestMapping(AppConstant.BACK_API_PREFIX+"/account/accountOpenImage")
@Api(value = "", tags = "")
public class AccountOpenImageController extends ZeroController {

	private final IAccountOpenImageService account_open_imageService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入account_open_image")
	public R<AccountOpenImageEntity> detail(AccountOpenImageEntity account_open_image) {
		AccountOpenImageEntity detail = account_open_imageService.getOne(Condition.getQueryWrapper(account_open_image));
		return R.data(detail);
	}

	/**
	 * 分页 代码自定义代号
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入account_open_image")
	public R<IPage<AccountOpenImageEntity>> list(AccountOpenImageEntity account_open_image, Query query) {
		IPage<AccountOpenImageEntity> pages = account_open_imageService.page(Condition.getPage(query), Condition.getQueryWrapper(account_open_image));
		return R.data(pages);
	}

	/**
	 * 新增 代码自定义代号
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入account_open_image")
	public R save(@Valid @RequestBody AccountOpenImageEntity account_open_image) {
		return R.status(account_open_imageService.save(account_open_image));
	}

	/**
	 * 修改 代码自定义代号
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入account_open_image")
	public R update(@Valid @RequestBody AccountOpenImageEntity account_open_image) {
		return R.status(account_open_imageService.updateById(account_open_image));
	}

	/**
	 * 新增或修改 代码自定义代号
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入account_open_image")
	public R submit(@Valid @RequestBody AccountOpenImageEntity account_open_image) {
		return R.status(account_open_imageService.saveOrUpdate(account_open_image));
	}


	/**
	 * 删除 代码自定义代号
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(account_open_imageService.deleteLogic(Func.toLongList(ids)));
	}

	@ApiOperation("查询客户开户申请图片信息列表")
	@GetMapping("/list/{applicationId}")
	public R<List<AccountOpenImageVO>> list(@ApiParam("流水号")
													@NotBlank(message = "流水号不能为空")
													@PathVariable("applicationId") String applicationId,
													@ApiParam("图片位置[0=未知 1=身份证正面照坐标 2=身份证反面照片坐标 3=指定动作1照坐标 4=指定动作2照坐标 5=指定动作3照坐标 6=电子签名照坐标]")
													@RequestParam(value = "imageLocation",required = false) Integer imageLocation
	) {
		return R.data(account_open_imageService.queryListByApplicationId(applicationId, imageLocation));
	}

}
