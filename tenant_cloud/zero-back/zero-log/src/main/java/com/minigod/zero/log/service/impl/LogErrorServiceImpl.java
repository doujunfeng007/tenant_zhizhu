
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
import com.minigod.zero.log.mapper.LogErrorMapper;
import com.minigod.zero.core.log.model.LogError;
import com.minigod.zero.log.model.LogApiVO;
import com.minigod.zero.log.model.LogErrorDTO;
import com.minigod.zero.log.model.LogErrorVO;
import com.minigod.zero.log.service.ILogErrorService;
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
public class LogErrorServiceImpl extends ServiceImpl<LogErrorMapper, LogError> implements ILogErrorService {
	/**
	 * 导出最大限制
	 */
	public static final long EXPORT_MAX_SIZE = 50000L;
	private final LogErrorMapper logErrorMapper;

	/**
	 * 分页查询Error-Log
	 *
	 * @param query
	 * @param errorDTO
	 * @return
	 */
	@Override
	public IPage<LogErrorVO> selectLogPage(Query query, LogErrorDTO errorDTO) {
		IPage<LogErrorVO> page = Condition.getPage(query);
		List<LogError> logErrorList = logErrorMapper.selectLogPage(page, errorDTO);
		List<LogErrorVO> logErrorVOList = new ArrayList<>();
		for (LogError logError : logErrorList) {
			LogErrorVO logErrorVO = new LogErrorVO();
			logErrorVO.setId(logError.getId());
			logErrorVO.setServiceId(logError.getServiceId());
			logErrorVO.setExceptionName(logError.getExceptionName());
			logErrorVO.setStackTrace(logError.getStackTrace());
			logErrorVO.setFileName(logError.getFileName());
			logErrorVO.setLineNumber(logError.getLineNumber());
			logErrorVO.setMessage(logError.getMessage());
			logErrorVO.setEnv(logError.getEnv());
			logErrorVO.setMethod(logError.getMethod());
			logErrorVO.setRemoteIp(logError.getRemoteIp());
			logErrorVO.setRequestUri(logError.getRequestUri());
			logErrorVO.setServerIp(logError.getServerIp());
			logErrorVO.setServerHost(logError.getServerHost());
			logErrorVO.setUserAgent(logError.getUserAgent());
			logErrorVO.setMethodClass(logError.getMethodClass());
			logErrorVO.setMethodName(logError.getMethodName());
			logErrorVO.setParams(logError.getParams());
			logErrorVO.setCreateTime(logError.getCreateTime());
			logErrorVO.setCreateBy(logError.getCreateBy());
			logErrorVOList.add(logErrorVO);
		}
		return page.setRecords(logErrorVOList);
	}

	/**
	 * 查询Error-Log列表
	 *
	 * @param errorDTO
	 * @return
	 */
	@Override
	public List<LogErrorVO> selectLogList(LogErrorDTO errorDTO) {
		List<LogError> logErrorList = logErrorMapper.selectLogList(errorDTO);
		List<LogErrorVO> logErrorVOList = new ArrayList<>();
		for (LogError logError : logErrorList) {
			LogErrorVO logErrorVO = new LogErrorVO();
			logErrorVO.setId(logError.getId());
			logErrorVO.setServiceId(logError.getServiceId());
			logErrorVO.setExceptionName(logError.getExceptionName());
			logErrorVO.setStackTrace(logError.getStackTrace());
			logErrorVO.setFileName(logError.getFileName());
			logErrorVO.setLineNumber(logError.getLineNumber());
			logErrorVO.setMessage(logError.getMessage());
			logErrorVO.setEnv(logError.getEnv());
			logErrorVO.setMethod(logError.getMethod());
			logErrorVO.setRemoteIp(logError.getRemoteIp());
			logErrorVO.setRequestUri(logError.getRequestUri());
			logErrorVO.setServerIp(logError.getServerIp());
			logErrorVO.setServerHost(logError.getServerHost());
			logErrorVO.setUserAgent(logError.getUserAgent());
			logErrorVO.setMethodClass(logError.getMethodClass());
			logErrorVO.setMethodName(logError.getMethodName());
			logErrorVO.setParams(logError.getParams());
			logErrorVO.setCreateTime(logError.getCreateTime());
			logErrorVO.setCreateBy(logError.getCreateBy());
			logErrorVOList.add(logErrorVO);
		}
		return logErrorVOList;
	}

	/**
	 * 查询Error-Log详情
	 *
	 * @param id
	 * @return
	 */
	@Override
	public LogErrorVO getLogDetail(Long id) {
		LambdaQueryWrapper<LogError> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(LogError::getId, id);
		LogError logError = logErrorMapper.selectOne(queryWrapper);
		if (logError != null) {
			LogErrorVO logErrorVO = new LogErrorVO();
			logErrorVO.setId(logError.getId());
			logErrorVO.setServiceId(logError.getServiceId());
			logErrorVO.setLineNumber(logError.getLineNumber());
			logErrorVO.setExceptionName(logError.getExceptionName());
			logErrorVO.setStackTrace(logError.getStackTrace());
			logErrorVO.setMessage(logError.getMessage());
			logErrorVO.setFileName(logError.getFileName());
			logErrorVO.setEnv(logError.getEnv());
			logErrorVO.setMethod(logError.getMethod());
			logErrorVO.setRemoteIp(logError.getRemoteIp());
			logErrorVO.setRequestUri(logError.getRequestUri());
			logErrorVO.setServerIp(logError.getServerIp());
			logErrorVO.setServerHost(logError.getServerHost());
			logErrorVO.setUserAgent(logError.getUserAgent());
			logErrorVO.setMethodClass(logError.getMethodClass());
			logErrorVO.setMethodName(logError.getMethodName());
			logErrorVO.setParams(logError.getParams());
			logErrorVO.setCreateTime(logError.getCreateTime());
			logErrorVO.setCreateBy(logError.getCreateBy());
			return logErrorVO;
		}
		return null;
	}

	/**
	 * 导出Error-Log
	 *
	 * @param response
	 * @param errorDTO
	 */
	@Override
	public void exportLog(HttpServletResponse response, LogErrorDTO errorDTO) {
		Long count = logErrorMapper.selectLogCount(errorDTO);
		if (count > EXPORT_MAX_SIZE) {
			throw new ServiceException("导出数据超过最大限制,请尽量缩短日志的时间区间!");
		}
		List<LogError> logErrors = logErrorMapper.selectLogList(errorDTO);
		List<LogErrorVO> logErrorVOS = new ArrayList<>();
		for (LogError logError : logErrors) {
			LogErrorVO logErrorVO = new LogErrorVO();
			logErrorVO.setId(logError.getId());
			logErrorVO.setServiceId(logError.getServiceId());
			logErrorVO.setLineNumber(logError.getLineNumber());
			logErrorVO.setExceptionName(logError.getExceptionName());
			logErrorVO.setStackTrace(logError.getStackTrace());
			logErrorVO.setMessage(logError.getMessage());
			logErrorVO.setFileName(logError.getFileName());
			logErrorVO.setEnv(logError.getEnv());
			logErrorVO.setMethod(logError.getMethod());
			logErrorVO.setRemoteIp(logError.getRemoteIp());
			logErrorVO.setRequestUri(logError.getRequestUri());
			logErrorVO.setServerIp(logError.getServerIp());
			logErrorVO.setServerHost(logError.getServerHost());
			logErrorVO.setUserAgent(logError.getUserAgent());
			logErrorVO.setMethodClass(logError.getMethodClass());
			logErrorVO.setMethodName(logError.getMethodName());
			logErrorVO.setParams(logError.getParams());
			logErrorVO.setCreateTime(logError.getCreateTime());
			logErrorVO.setCreateBy(logError.getCreateBy());
			logErrorVOS.add(logErrorVO);
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
			String fileName = "Error系统日志";
			fileName = URLEncoder.encode(fileName, Charsets.UTF_8.name()).replaceAll("\\+", "%20");

			response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
			String sheetName = "日志详情";
			ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream())
				.registerWriteHandler(horizontalCellStyleStrategy)
				.registerWriteHandler(widthStyleStrategy)
				.build();
			WriteSheet writeSheet = EasyExcel.writerSheet(0, sheetName).head(LogErrorVO.class).build();
			excelWriter.write(logErrorVOS, writeSheet);
			excelWriter.finish();
			response.flushBuffer();
		} catch (Exception e) {
			log.error("导出失败,失败原因:" + e.getMessage());
			throw new ServiceException("导出失败！");
		}
	}
}
