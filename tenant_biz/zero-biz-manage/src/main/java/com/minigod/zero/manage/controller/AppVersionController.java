package com.minigod.zero.manage.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.minigod.zero.manage.entity.AppVersionEntity;
import com.minigod.zero.manage.vo.request.AppVersionVO;
import com.minigod.zero.core.boot.ctrl.ZeroController;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.mp.support.Condition;
import com.minigod.zero.core.mp.support.Query;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.utils.Func;
import com.minigod.zero.manage.service.IAppVersionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.minigod.zero.core.secure.utils.AuthUtil.getTenantId;

/**
 * APP版本信息表 控制器
 *
 * @author 掌上智珠
 * @since 2023-04-24
 */
@RestController
@AllArgsConstructor
@RequestMapping(AppConstant.BACK_API_PREFIX + "/appVersion")
@Api(value = "APP版本信息表", tags = "APP版本信息表接口")
public class AppVersionController extends ZeroController {

	private final IAppVersionService appVersionService;

	/**
	 * APP版本信息表详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "APP版本信息表详情", notes = "传入appVersion")
	public R<AppVersionEntity> detail(AppVersionEntity appVersion) {
		appVersion.setTenantId(getTenantId());
		AppVersionEntity detail = appVersionService.getOne(Condition.getQueryWrapper(appVersion));
		return R.data(detail);
	}

	/**
	 * APP版本信息表
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "APP版本信息表", notes = "传入appVersion")
	public R<IPage<AppVersionVO>> page(AppVersionVO appVersion, Query query) {
		appVersion.setTenantId(getTenantId());
		IPage<AppVersionVO> pages = appVersionService.selectAppVersionPage(Condition.getPage(query), appVersion);
		return R.data(pages);
	}

	/**
	 * APP版本信息表 新增或修改
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改APP版本信息", notes = "传入appVersion")
	public R<Object> submit(@Valid @RequestBody AppVersionVO appVersion) {
		if (StringUtils.isEmpty(appVersion.getVersionNo())) {
			return R.fail("版本号不能为空");
		}
		if (StringUtils.isEmpty(appVersion.getChannel())) {
			return R.fail("渠道号不能为空");
		}
		Pattern pattern = Pattern.compile("^[0-9]+(\\.[0-9])+[0-9]?$");
		Matcher matcher = pattern.matcher(appVersion.getVersionNo());
		if (!matcher.matches()) {
			return R.fail("版本号格式错误");
		}

		String versionNo = appVersion.getVersionNo();
		String[] split = versionNo.split("\\.");
		if (split.length > 3) {
			return R.fail("版本长度不能超过3位");
		}
		appVersion.setTenantId(getTenantId());
		Integer checkCode = appVersion.getCheckCode();
		appVersion.setUpdateTime(new Date());
		if (null != appVersion.getId()) {
			appVersion.setUpdateDeclare(appVersion.getUpdateDeclare().replaceAll("\r\n", "\n"));
			appVersion.setCheckCode(checkCode);
			appVersionService.saveOrUpdate(appVersion);
			appVersionService.updateBeforeCheckCode(appVersion.getAppCode(),
				appVersion.getDeviceType(),
				appVersion.getOsType(),
				appVersion.getVersionNo(),
				appVersion.getToAll(),
				checkCode,
				getTenantId());
		} else {
			//保存添加版本
			appVersion.setUpdateDeclare(appVersion.getUpdateDeclare().replaceAll("\r\n", "\n"));
			appVersion.setCreateTime(new Date());
			appVersion.setCheckCode(checkCode);
			//相同APP，相同OS，相同版本号是否存在记录
			AppVersionEntity old = appVersionService.findAppVersionByVersion(appVersion.getAppCode(),
				appVersion.getDeviceType(),
				appVersion.getOsType(),
				appVersion.getVersionNo(),
				getTenantId());
			if (old != null) {
				return R.fail("版本已存在");
			}
			appVersionService.saveOrUpdate(appVersion);
			//每次添加新版本后，将之前操作系统类型相同的升级状态和是否最新版本两个字段做相应的修改
			//需要修改历史版本的isNew和checkCode两个字段的值
			//1.将历史版本设置为不是最新版本
			appVersionService.updateBeforeStatus(appVersion.getAppCode(),
				appVersion.getDeviceType(),
				appVersion.getOsType(),
				appVersion.getVersionNo(),
				getTenantId());

			//2.修改历史最新版本或者历史全部版本的升级提示为checkCode
			appVersionService.updateBeforeCheckCode(appVersion.getAppCode(),
				appVersion.getDeviceType(),
				appVersion.getOsType(),
				appVersion.getVersionNo(),
				appVersion.getToAll(),
				checkCode,
				getTenantId());
		}
		return R.success();
	}

	/**
	 * APP版本信息表 删除
	 */
	@DeleteMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "删除", notes = "传入ids")
	public R<Object> remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(appVersionService.removeByIds(Func.toLongList(ids)));
	}
}
