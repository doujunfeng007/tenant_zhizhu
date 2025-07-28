package com.minigod.zero.bpmn.module.account.controller;

import com.minigod.zero.bpmn.utils.FileUtils;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.log.exception.ServiceException;
import com.minigod.zero.core.secure.utils.AuthUtil;
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
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.metadata.IPage;

import com.minigod.zero.bpmn.module.account.entity.AccountAdditionalFileEntity;
import com.minigod.zero.bpmn.module.account.vo.AccountAdditionalFileVO;
import com.minigod.zero.bpmn.module.account.service.IAccountAdditionalFileService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


/**
 *  控制器
 *
 * @author Chill
 */
@RestController
@AllArgsConstructor
@RequestMapping(AppConstant.BACK_API_PREFIX+"/account/accountAdditionalFile")
@Api(value = "", tags = "")
public class AccountAdditionalFileController extends ZeroController {

	private final IAccountAdditionalFileService account_additional_fileService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入account_additional_file")
	public R<AccountAdditionalFileEntity> detail(AccountAdditionalFileEntity account_additional_file) {
		AccountAdditionalFileEntity detail = account_additional_fileService.getOne(Condition.getQueryWrapper(account_additional_file));
		return R.data(detail);
	}

	/**
	 * 分页 代码自定义代号
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入account_additional_file")
	public R<IPage<AccountAdditionalFileEntity>> list(AccountAdditionalFileEntity account_additional_file, Query query) {
		IPage<AccountAdditionalFileEntity> pages = account_additional_fileService.page(Condition.getPage(query), Condition.getQueryWrapper(account_additional_file));
		return R.data(pages);
	}

	/**
	 * 新增 代码自定义代号
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入account_additional_file")
	public R save(@Valid @RequestBody AccountAdditionalFileEntity account_additional_file) {
		return R.status(account_additional_fileService.save(account_additional_file));
	}

	/**
	 * 修改 代码自定义代号
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入account_additional_file")
	public R update(@Valid @RequestBody AccountAdditionalFileEntity account_additional_file) {
		return R.status(account_additional_fileService.updateById(account_additional_file));
	}

	/**
	 * 新增或修改 代码自定义代号
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入account_additional_file")
	public R submit(@Valid @RequestBody AccountAdditionalFileEntity account_additional_file) {
		return R.status(account_additional_fileService.saveOrUpdate(account_additional_file));
	}


	/**
	 * 删除 代码自定义代号
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(account_additional_fileService.removeBatchByIds(Func.toLongList(ids)));
	}


	@ApiOperation("查询补充资料文件上传列表")
	@GetMapping("/list/{applicationId}")
	public R<List<AccountAdditionalFileVO>> list(@ApiParam("流水号")
													 @NotBlank(message = "流水号不能为空")
													 @PathVariable("applicationId") String applicationId,
													 @ApiParam("文件类型")
													 @RequestParam(value = "type", required = false) Integer type) {

		return R.data(account_additional_fileService.queryListByApplicationId(applicationId, type, AuthUtil.getUserId()));
	}


	@ApiOperation("补充文件上传")
	@PostMapping(value = "/uploadAdditionalFile", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
	public R<String> uploadAdditionalFile(@ApiParam(value = "流水号", required = true)
										  @RequestParam("applicationId") String applicationId,
										  @ApiParam(value = "备注", required = true)
										  @RequestParam("remark") String remark,
										  @ApiParam(value = "文件所属类型", required = true)
										  @RequestParam("type") Integer type,
										  @ApiParam(value = "文件类型", required = true)
										  @RequestParam("fileType") Integer fileType,
										  @ApiParam(value = "文件", required = true)
										  @RequestPart("file") MultipartFile file) {
		if(!FileUtils.checkFormats(file.getOriginalFilename())){
			throw new ServiceException("非法文件类型");
		}
		String uuid = account_additional_fileService.uploadAdditionalFile(applicationId, type,fileType, remark, file);
		return R.data(uuid);
	}
}
