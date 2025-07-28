package com.minigod.zero.manage.controller;

import com.minigod.zero.core.excel.util.ExcelUtil;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.tool.utils.*;
import com.minigod.zero.manage.entity.ReportLogEntity;
import com.minigod.zero.manage.excel.ReportLogExcel;
import com.minigod.zero.manage.vo.ReportLogVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import lombok.AllArgsConstructor;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import com.minigod.zero.core.mp.support.Condition;
import com.minigod.zero.core.mp.support.Query;
import com.minigod.zero.core.tool.api.R;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.manage.wrapper.ReportLogWrapper;
import com.minigod.zero.manage.service.IReportLogService;
import com.minigod.zero.core.boot.ctrl.ZeroController;
import springfox.documentation.annotations.ApiIgnore;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * APP日志下载 控制器
 *
 * @author 掌上智珠
 * @since 2023-03-23
 */
@RestController
@AllArgsConstructor
@RequestMapping(AppConstant.BACK_API_PREFIX + "/reportLog")
@Api(value = "APP日志下载", tags = "APP日志下载接口")
public class ReportLogController extends ZeroController {

	private final IReportLogService reportLogService;

	/**
	 * APP日志下载 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入reportLog")
	public R<ReportLogVO> detail(ReportLogEntity reportLog) {
		ReportLogEntity detail = reportLogService.getOne(Condition.getQueryWrapper(reportLog));
		return R.data(ReportLogWrapper.build().entityVO(detail));
	}

	/**
	 * APP日志下载 自定义分页
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入reportLog")
	public R<IPage<ReportLogVO>> page(ReportLogVO reportLog, Query query) {
		IPage<ReportLogVO> pages = reportLogService.selectReportLogPage(Condition.getPage(query), reportLog);
		return R.data(pages);
	}


	@GetMapping("export-log")
	@ApiOperationSupport(order = 11)
	@ApiOperation(value = "导出上报日志", notes = "传入user")
	public void exportLog(@ApiIgnore @RequestParam String ids, HttpServletResponse response) {
		if (!StringUtil.isBlank(ids)) {
			List<ReportLogExcel> list = new ArrayList();
			List<Long> arrIds = Func.toLongList(ids);
			for (Long id : arrIds) {
				ReportLogEntity entity = reportLogService.getById(id);
				list.add(BeanUtil.copy(entity, ReportLogExcel.class));
			}
			ExcelUtil.export(response, "日志上报" + DateUtil.time(), "日志上报表", list, ReportLogExcel.class);
		}

	}

	@GetMapping("downFlile")
	@ApiOperationSupport(order = 12)
	@ApiOperation(value = "下载", notes = "传入user")
	public void downFlile(@ApiIgnore @RequestParam String id, HttpServletResponse response) {
		if (!StringUtil.isBlank(id)) {
			ReportLogEntity entity = reportLogService.getById(id);
			if (null != entity) {
				ServletOutputStream out = null;
				try {
					URL url = new URL(entity.getLogPath());
					URLConnection conn = url.openConnection();
					InputStream inputStream = conn.getInputStream();
					String fileNameWithExtension = entity.getLogPath().substring(entity.getLogPath().lastIndexOf("/") + 1);
					try {
						// 设置文件ContentType类型，这样设置，会自动判断下载文件类型
						response.setContentType("multipart/form-data");
						// 设置Content-Disposition头部，指定文件名（没有拓展名）
						response.setHeader("Content-Disposition", "attachment; filename=\"" + fileNameWithExtension + "\"");
					} catch (Exception e) {
						e.printStackTrace();
					}
					out = response.getOutputStream();
					// 读取文件流
					int len = 0;
					byte[] buffer = new byte[1024 * 10];
					while ((len = inputStream.read(buffer)) != -1) {
						out.write(buffer, 0, len);
					}
					out.flush();
					out.close();
					inputStream.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

}
