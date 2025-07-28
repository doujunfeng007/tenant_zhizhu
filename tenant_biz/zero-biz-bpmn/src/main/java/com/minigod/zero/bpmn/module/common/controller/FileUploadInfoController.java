package com.minigod.zero.bpmn.module.common.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.minigod.zero.bpmn.module.common.service.IFileUploadInfoService;
import com.minigod.zero.bpmn.module.common.entity.FileUploadInfoEntity;
import com.minigod.zero.core.boot.ctrl.ZeroController;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.log.annotation.ApiLog;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.utils.Func;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 附件上传表 控制器
 *
 * @author 掌上智珠
 * @since 2024-03-13
 */
@RestController
@AllArgsConstructor
@RequestMapping(AppConstant.BACK_API_PREFIX +"/fileUploadInfo")
@Api(value = "附件上传表", tags = "附件上传表接口")
public class FileUploadInfoController extends ZeroController {

	private final IFileUploadInfoService fileUploadInfoService;


	@ApiLog("上传附件")
	@ApiOperation("上传附件")
	@PostMapping("/save")
	public R saveCacheCnImg(@RequestBody FileUploadInfoEntity fileUploadInfo) {
		return R.status(fileUploadInfoService.save(fileUploadInfo));
	}


	/**
	 * 删除
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(fileUploadInfoService.deleteLogic(Func.toLongList(ids)));
	}




}
