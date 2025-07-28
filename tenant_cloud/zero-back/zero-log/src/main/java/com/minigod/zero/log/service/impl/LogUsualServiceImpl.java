
package com.minigod.zero.log.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minigod.zero.core.log.exception.ServiceException;
import com.minigod.zero.core.mp.support.Condition;
import com.minigod.zero.core.mp.support.Query;
import com.minigod.zero.log.excel.ExcelCellWidthStyleStrategy;
import com.minigod.zero.log.excel.StyleUtils;
import com.minigod.zero.log.mapper.LogUsualMapper;
import com.minigod.zero.log.model.LogApiVO;
import com.minigod.zero.log.model.LogUsualDTO;
import com.minigod.zero.log.model.LogUsualVO;
import com.minigod.zero.log.service.ILogUsualService;
import com.minigod.zero.core.log.model.LogUsual;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.Charsets;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * 服务实现类
 *
 * @author Chill
 */
@Slf4j
@Service
@AllArgsConstructor
public class LogUsualServiceImpl extends ServiceImpl<LogUsualMapper, LogUsual> implements ILogUsualService {
	/**
	 * 导出最大限制
	 */
	public static final long EXPORT_MAX_SIZE = 50000L;
	private final LogUsualMapper logUsualMapper;

	/**
	 * 分页查询Error-Log
	 *
	 * @param query
	 * @param usualDTO
	 * @return
	 */
	@Override
	public IPage<LogUsualVO> selectLogPage(Query query, LogUsualDTO usualDTO) {
		IPage<LogUsualVO> page = Condition.getPage(query);
		List<LogUsual> logUsualList = logUsualMapper.selectLogPage(page, usualDTO);
		List<LogUsualVO> logErrorVOList = new ArrayList<>();
		for (LogUsual logUsual : logUsualList) {
			LogUsualVO logUsualVO = new LogUsualVO();
			logUsualVO.setId(logUsual.getId());
			logUsualVO.setServiceId(logUsual.getServiceId());
			logUsualVO.setLogData(logUsual.getLogData());
			logUsualVO.setLogLevel(logUsual.getLogLevel());
			logUsualVO.setLogId(logUsual.getLogId());
			logUsualVO.setEnv(logUsual.getEnv());
			logUsualVO.setMethod(logUsual.getMethod());
			logUsualVO.setRemoteIp(logUsual.getRemoteIp());
			logUsualVO.setRequestUri(logUsual.getRequestUri());
			logUsualVO.setServerIp(logUsual.getServerIp());
			logUsualVO.setServerHost(logUsual.getServerHost());
			logUsualVO.setUserAgent(logUsual.getUserAgent());
			logUsualVO.setMethodClass(logUsual.getMethodClass());
			logUsualVO.setMethodName(logUsual.getMethodName());
			logUsualVO.setParams(logUsual.getParams());
			logUsualVO.setCreateTime(logUsual.getCreateTime());
			logUsualVO.setCreateBy(logUsual.getCreateBy());
			logErrorVOList.add(logUsualVO);
		}
		return page.setRecords(logErrorVOList);
	}

	/**
	 * 查询Error-Log详情
	 *
	 * @param id
	 * @return
	 */
	@Override
	public LogUsualVO getLogDetail(Long id) {
		LambdaQueryWrapper<LogUsual> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(LogUsual::getId, id);
		LogUsual logUsual = logUsualMapper.selectOne(queryWrapper);
		if (logUsual != null) {
			LogUsualVO logUsualVO = new LogUsualVO();
			logUsualVO.setId(logUsual.getId());
			logUsualVO.setServiceId(logUsual.getServiceId());
			logUsualVO.setLogData(logUsual.getLogData());
			logUsualVO.setLogLevel(logUsual.getLogLevel());
			logUsualVO.setLogId(logUsual.getLogId());
			logUsualVO.setEnv(logUsual.getEnv());
			logUsualVO.setMethod(logUsual.getMethod());
			logUsualVO.setRemoteIp(logUsual.getRemoteIp());
			logUsualVO.setRequestUri(logUsual.getRequestUri());
			logUsualVO.setServerIp(logUsual.getServerIp());
			logUsualVO.setServerHost(logUsual.getServerHost());
			logUsualVO.setUserAgent(logUsual.getUserAgent());
			logUsualVO.setMethodClass(logUsual.getMethodClass());
			logUsualVO.setMethodName(logUsual.getMethodName());
			logUsualVO.setParams(logUsual.getParams());
			logUsualVO.setCreateTime(logUsual.getCreateTime());
			logUsualVO.setCreateBy(logUsual.getCreateBy());
			return logUsualVO;
		}
		return null;
	}

	/**
	 * 导出Error-Log
	 *
	 * @param response
	 * @param usualDTO
	 */
	@Override
	public void exportLog(HttpServletResponse response, LogUsualDTO usualDTO) {
		Long count = logUsualMapper.selectLogCount(usualDTO);
		if (count > EXPORT_MAX_SIZE) {
			throw new ServiceException("导出数据超过最大限制,请尽量缩短日志的时间区间!");
		}
		List<LogUsual> logUsuals = logUsualMapper.selectLogList(usualDTO);
		List<LogUsualVO> logUsualVOS = new ArrayList<>();
		for (LogUsual logUsual : logUsuals) {
			LogUsualVO logUsualVO = new LogUsualVO();
			logUsualVO.setId(logUsual.getId());
			logUsualVO.setServiceId(logUsual.getServiceId());
			logUsualVO.setLogData(logUsual.getLogData());
			logUsualVO.setLogLevel(logUsual.getLogLevel());
			logUsualVO.setLogId(logUsual.getLogId());
			logUsualVO.setEnv(logUsual.getEnv());
			logUsualVO.setMethod(logUsual.getMethod());
			logUsualVO.setRemoteIp(logUsual.getRemoteIp());
			logUsualVO.setRequestUri(logUsual.getRequestUri());
			logUsualVO.setServerIp(logUsual.getServerIp());
			logUsualVO.setServerHost(logUsual.getServerHost());
			logUsualVO.setUserAgent(logUsual.getUserAgent());
			logUsualVO.setMethodClass(logUsual.getMethodClass());
			logUsualVO.setMethodName(logUsual.getMethodName());
			logUsualVO.setParams(logUsual.getParams());
			logUsualVO.setCreateTime(logUsual.getCreateTime());
			logUsualVO.setCreateBy(logUsual.getCreateBy());
			logUsualVOS.add(logUsualVO);
		}
		// 将数据写入sheet页中
		try {
			response.setContentType("application/vnd.ms-excel");
			response.setCharacterEncoding(Charsets.UTF_8.name());
			// 设置单元格样式
			HorizontalCellStyleStrategy horizontalCellStyleStrategy =
				new HorizontalCellStyleStrategy(StyleUtils.getHeadStyle(), StyleUtils.getContentStyle());
			// 列宽策略设置
			ExcelCellWidthStyleStrategy widthStyleStrategy = new ExcelCellWidthStyleStrategy();
			String fileName = "普通日志";
			fileName = URLEncoder.encode(fileName, Charsets.UTF_8.name()).replaceAll("\\+", "%20");

			response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
			String sheetName = "日志详情";
			ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream())
				.registerWriteHandler(horizontalCellStyleStrategy)
				.registerWriteHandler(widthStyleStrategy)
				.build();
			WriteSheet writeSheet = EasyExcel.writerSheet(0, sheetName).head(LogUsualVO.class).build();
			excelWriter.write(logUsualVOS, writeSheet);
			excelWriter.finish();
			response.flushBuffer();
		} catch (Exception e) {
			log.error("导出失败,失败原因:" + e.getMessage());
			throw new ServiceException("导出失败！");
		}
	}
}
