package com.minigod.zero.manage.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.minigod.zero.manage.entity.SysDocumentEntity;
import com.minigod.zero.manage.excel.SysDocumentExcel;
import com.minigod.zero.manage.service.ISysDocumentService;
import com.minigod.zero.core.excel.util.ExcelUtil;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.mp.support.Condition;
import com.minigod.zero.core.mp.support.Query;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.utils.BeanUtil;
import com.minigod.zero.core.tool.utils.DateUtil;
import com.minigod.zero.core.tool.utils.StringUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 平台文档Controller
 *
 * @author jim
 * @date 2020-05-27
 */
@RestController
@RequestMapping(AppConstant.BACK_API_PREFIX + "/document")
public class SysDocumentController {
	@Autowired
	private ISysDocumentService sysDocumentService;

	/**
	 * 查询平台文档列表
	 */
	@ApiOperationSupport(order = 1)
	@GetMapping("/page")
	public R<IPage<SysDocumentEntity>> list(SysDocumentEntity sysDocument, Query query) {
		IPage<SysDocumentEntity> pages = sysDocumentService.selectSysDocPage(Condition.getPage(query), sysDocument);
		return R.data(pages);
	}

	/**
	 * 导出平台文档列表
	 */
	@GetMapping("/export")
	public void export(SysDocumentEntity sysDocument, HttpServletResponse response) {
		List<SysDocumentEntity> list = sysDocumentService.selectSysDocumentList(null,sysDocument);
		List<SysDocumentExcel> exportList = new ArrayList();
		if(null != list){
			for (SysDocumentEntity entity: list){
				SysDocumentExcel excel = BeanUtil.copy(entity,SysDocumentExcel.class);
				if(!StringUtil.isBlank(excel.getDocContent()) && excel.getDocContent().length() > 255){
					excel.setDocContent(excel.getDocContent().substring(0,250) +"..");
				}
				excel.setStatusStr(entity.getStatus() == 0?"无效/禁用":"有效/启用");
				exportList.add(excel);
			}
		}
		ExcelUtil.export(response, "文档列表" + DateUtil.time(), "文档列表", exportList, SysDocumentExcel.class);
	}

	/**
	 * 获取平台文档详细信息
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "详情", notes = "传入SysDocumentEntity")
	public R<SysDocumentEntity> getInfo(SysDocumentEntity sysDocument) {
		return R.data(sysDocumentService.selectSysDocumentById(sysDocument.getId()));
	}

	/**
	 * 新增平台文档
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增或修改", notes = "传入sysDocument")
	public R<Object> submit(@RequestBody SysDocumentEntity sysDocument) {
		if(null == sysDocument.getId()){
			sysDocument.setCreateUser(AuthUtil.getUserId());
			sysDocument.setCreateDept(Long.parseLong(AuthUtil.getDeptId()));
			sysDocument.setCreateTime(new Date());
			sysDocument.setUpdateTime(new Date());
			sysDocument.setUpdateUser(AuthUtil.getUserId());
			sysDocument.setStatus(1);
		}else{
			sysDocument.setUpdateTime(new Date());
			sysDocument.setUpdateUser(AuthUtil.getUserId());
		}
		return R.status(sysDocumentService.submit(sysDocument));
	}

	/**
	 * 删除平台文档
	 */
	@DeleteMapping("/remove")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R<Object> remove(@RequestParam Integer[] ids) {
		return R.status(sysDocumentService.deleteSysDocumentByIds(ids));
	}
}
