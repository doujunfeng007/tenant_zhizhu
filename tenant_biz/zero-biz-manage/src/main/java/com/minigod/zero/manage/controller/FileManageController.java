package com.minigod.zero.manage.controller;

import com.minigod.zero.manage.entity.FileManageEntity;
import com.minigod.zero.manage.service.IFileManageService;
import com.minigod.zero.manage.vo.FileManageVO;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.secure.utils.AuthUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import lombok.AllArgsConstructor;
import javax.validation.Valid;

import com.minigod.zero.core.mp.support.Condition;
import com.minigod.zero.core.mp.support.Query;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.utils.Func;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.manage.wrapper.FileManageWrapper;
import com.minigod.zero.core.boot.ctrl.ZeroController;

import java.util.Date;

/**
 * 文件管理 控制器
 *
 * @author 掌上智珠
 * @since 2023-03-22
 */
@RestController
@AllArgsConstructor
@RequestMapping(AppConstant.BACK_API_PREFIX + "/fileManage")
@Api(value = "文件管理", tags = "文件管理接口")
public class FileManageController extends ZeroController {

	private final IFileManageService fileManageService;

	/**
	 * 文件管理 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入fileManage")
	public R<FileManageVO> detail(FileManageEntity fileManage) {
		FileManageEntity detail = fileManageService.getOne(Condition.getQueryWrapper(fileManage));
		return R.data(FileManageWrapper.build().entityVO(detail));
	}

	/**
	 * 文件管理 自定义分页
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入fileManage")
	public R<IPage<FileManageVO>> page(FileManageVO fileManage, Query query) {
		IPage<FileManageVO> pages = fileManageService.selectFileManagePage(Condition.getPage(query), fileManage);
		return R.data(pages);
	}


	/**
	 * 文件管理 新增或修改
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入fileManage")
	public R<Object> submit(@Valid @RequestBody FileManageEntity fileManage) {
		if(null == fileManage.getId()){
			fileManage.setCreateUser(AuthUtil.getUserId());
			fileManage.setCreateDept(Long.parseLong(AuthUtil.getDeptId()));
			fileManage.setCreateTime(new Date());
			fileManage.setUpdateTime(new Date());
			fileManage.setUpdateUser(AuthUtil.getUserId());
			fileManage.setUpdateUserName(AuthUtil.getUserName());
			fileManage.setStatus(1);
		}else {
			fileManage.setUpdateTime(new Date());
			fileManage.setUpdateUser(AuthUtil.getUserId());
			fileManage.setUpdateUserName(AuthUtil.getUserName());
		}
		return R.status(fileManageService.saveOrUpdate(fileManage));
	}

	/**
	 * 文件管理 删除
	 */
	@DeleteMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R<Object> remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(fileManageService.deleteLogic(Func.toLongList(ids)));
	}

}
