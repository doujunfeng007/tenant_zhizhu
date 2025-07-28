package com.minigod.zero.manage.controller;

import com.alibaba.nacos.common.utils.StringUtils;
import com.minigod.zero.manage.entity.AppConfigEntity;
import com.minigod.zero.manage.utils.FileReadUtil;
import com.minigod.zero.manage.dto.AppConfigSaveDTO;
import com.minigod.zero.manage.vo.AppConfigVO;
import com.minigod.zero.manage.wrapper.AppConfigWrapper;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.tool.utils.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import lombok.AllArgsConstructor;

import com.minigod.zero.core.mp.support.Condition;
import com.minigod.zero.core.mp.support.Query;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.utils.Func;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.manage.service.IAppConfigService;
import com.minigod.zero.core.boot.ctrl.ZeroController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * APP配置 控制器
 *
 * @author 掌上智珠
 * @since 2023-03-23
 */
@RestController
@AllArgsConstructor
@RequestMapping(AppConstant.BACK_API_PREFIX + "/appConfig")
@Api(value = "APP配置", tags = "APP配置接口")
public class AppConfigController extends ZeroController {

	private final IAppConfigService appConfigService;

	/**
	 * APP配置 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入appConfig")
	public R<AppConfigVO> detail(AppConfigEntity appConfig) {
		AppConfigEntity detail = appConfigService.getOne(Condition.getQueryWrapper(appConfig));
		return R.data(AppConfigWrapper.build().entityVO(detail));
	}


	/**
	 * APP配置 自定义分页
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入appConfig")
	public R<IPage<AppConfigVO>> page(AppConfigVO appConfig, Query query) {
		IPage<AppConfigVO> pages = appConfigService.selectAppConfigPage(Condition.getPage(query), appConfig);
		return R.data(pages);
	}


	/**
	 * APP配置 新增或修改
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入appConfig")
	public R<Object> submit(AppConfigSaveDTO appConfig, @RequestParam(value = "file", required = false) MultipartFile file) {
		if (null != file) {
			List<Long> lstUserIds = null;
			try {
				lstUserIds = FileReadUtil.getUserIdsFromFile(file);
			} catch (Exception e) {
				return R.fail("导入文件异常" + e.getMessage());
			}
			if (CollectionUtils.isEmpty(lstUserIds)) {
				return R.fail("导入文件为空");
			}
			String ids = StringUtils.join(lstUserIds, ",");
			appConfig.setUserIds(ids);
			appConfig.setGroupType(2);
		}
		appConfigService.save(appConfig);
		return R.success();
	}

	/**
	 * APP配置 删除
	 */
	@DeleteMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R<Object> remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(appConfigService.deleteLogic(Func.toLongList(ids)));
	}

	@GetMapping("/publish")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "发布", notes = "传入appConfig")
	public R<Object> page(String ids) {
		if (StringUtil.isBlank(ids)) {
			return R.fail("请选择行");
		}
		return appConfigService.publish(ids);
	}
}
