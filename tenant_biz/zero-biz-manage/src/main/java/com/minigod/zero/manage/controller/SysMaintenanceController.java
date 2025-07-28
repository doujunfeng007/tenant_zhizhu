package com.minigod.zero.manage.controller;

import com.minigod.zero.manage.entity.SysMaintenanceEntity;
import com.minigod.zero.manage.vo.SysMaintenanceVO;
import com.minigod.zero.manage.wrapper.SysMaintenanceWrapper;
import com.minigod.zero.manage.service.ISysMaintenanceService;
import com.minigod.zero.core.launch.constant.AppConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import lombok.AllArgsConstructor;
import javax.validation.Valid;

import com.minigod.zero.core.mp.support.Condition;
import com.minigod.zero.core.mp.support.Query;
import com.minigod.zero.core.tool.api.R;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.core.boot.ctrl.ZeroController;

import java.util.Date;

/**
 * 系统维护 控制器
 *
 * @author 掌上智珠
 * @since 2023-03-23
 */
@RestController
@AllArgsConstructor
@RequestMapping(AppConstant.BACK_API_PREFIX + "/sysMaintenance")
@Api(value = "系统维护", tags = "系统维护接口")
public class SysMaintenanceController extends ZeroController {

	private final ISysMaintenanceService sysMaintenanceService;

	/**
	 * 系统维护 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入sysMaintenance")
	public R<SysMaintenanceVO> detail(SysMaintenanceEntity sysMaintenance) {
		SysMaintenanceEntity detail = sysMaintenanceService.getOne(Condition.getQueryWrapper(sysMaintenance));
		return R.data(SysMaintenanceWrapper.build().entityVO(detail));
	}

	/**
	 * 系统维护 自定义分页
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入sysMaintenance")
	public R<IPage<SysMaintenanceVO>> page(SysMaintenanceVO sysMaintenance, Query query) {
		IPage<SysMaintenanceVO> pages = sysMaintenanceService.selectSysMaintenancePage(Condition.getPage(query), sysMaintenance);
		return R.data(pages);
	}

	/**
	 * 系统维护 新增或修改
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入sysMaintenance")
	public R<Object> submit(@Valid @RequestBody SysMaintenanceEntity sysMaintenance) {
		sysMaintenance.setStatus(0); //待生效
		if(null != sysMaintenance.getForceTime() && null != sysMaintenance.getDeadTime()){
			Date now = new Date();
			if(now.after(sysMaintenance.getForceTime())){
				sysMaintenance.setStatus(1); //生效中
			}
			if(now.after(sysMaintenance.getDeadTime())){
				sysMaintenance.setStatus(2); //已失效
			}
		}
		return R.status(sysMaintenanceService.saveOrUpdate(sysMaintenance));
	}

}
