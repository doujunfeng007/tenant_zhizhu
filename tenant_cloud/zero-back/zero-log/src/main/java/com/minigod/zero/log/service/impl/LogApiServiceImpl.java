
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
import com.minigod.zero.log.model.LogApiDTO;
import com.minigod.zero.log.model.LogApiVO;
import com.minigod.zero.log.service.ILogApiService;
import com.minigod.zero.log.mapper.LogApiMapper;
import com.minigod.zero.core.log.model.LogApi;
import lombok.AllArgsConstructor;
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
@Service
@AllArgsConstructor
public class LogApiServiceImpl extends ServiceImpl<LogApiMapper, LogApi> implements ILogApiService {
	/**
	 * 导出最大限制
	 */
	public static final long EXPORT_MAX_SIZE = 50000L;
	private final LogApiMapper logApiMapper;

	/**
	 * 分页查询API-Log
	 *
	 * @param query
	 * @param apiDTO
	 * @return
	 */
	@Override
	public IPage<LogApiVO> selectLogPage(Query query, LogApiDTO apiDTO) {
		IPage<LogApiVO> page = Condition.getPage(query);
		List<LogApi> logApiList = logApiMapper.selectLogPage(page, apiDTO);
		List<LogApiVO> logApiVOList = new ArrayList<>();
		for (LogApi logApi : logApiList) {
			LogApiVO logApiVO = new LogApiVO();
			logApiVO.setId(logApi.getId());
			logApiVO.setTenantId(logApi.getTenantId());
			logApiVO.setServiceId(logApi.getServiceId());
			logApiVO.setTitle(logApi.getTitle());
			logApiVO.setType(logApi.getType());
			logApiVO.setTime(logApi.getTime());
			logApiVO.setEnv(logApi.getEnv());
			logApiVO.setMethod(logApi.getMethod());
			logApiVO.setRemoteIp(logApi.getRemoteIp());
			logApiVO.setRequestUri(logApi.getRequestUri());
			logApiVO.setServerIp(logApi.getServerIp());
			logApiVO.setServerHost(logApi.getServerHost());
			logApiVO.setUserAgent(logApi.getUserAgent());
			logApiVO.setMethodClass(logApi.getMethodClass());
			logApiVO.setMethodName(logApi.getMethodName());
			logApiVO.setParams(logApi.getParams());
			logApiVO.setCreateTime(logApi.getCreateTime());
			logApiVO.setCreateBy(logApi.getCreateBy());
			logApiVOList.add(logApiVO);
		}
		return page.setRecords(logApiVOList);
	}

	/**
	 * 查询API-Log列表
	 *
	 * @param apiDTO
	 * @return
	 */
	@Override
	public List<LogApiVO> selectLogList(LogApiDTO apiDTO) {
		List<LogApi> logApiList = logApiMapper.selectLogList(apiDTO);
		List<LogApiVO> logApiVOList = new ArrayList<>();
		for (LogApi logApi : logApiList) {
			LogApiVO logApiVO = new LogApiVO();
			logApiVO.setId(logApi.getId());
			logApiVO.setTenantId(logApi.getTenantId());
			logApiVO.setServiceId(logApi.getServiceId());
			logApiVO.setTitle(logApi.getTitle());
			logApiVO.setType(logApi.getType());
			logApiVO.setTime(logApi.getTime());
			logApiVO.setEnv(logApi.getEnv());
			logApiVO.setMethod(logApi.getMethod());
			logApiVO.setRemoteIp(logApi.getRemoteIp());
			logApiVO.setRequestUri(logApi.getRequestUri());
			logApiVO.setServerIp(logApi.getServerIp());
			logApiVO.setServerHost(logApi.getServerHost());
			logApiVO.setUserAgent(logApi.getUserAgent());
			logApiVO.setMethodClass(logApi.getMethodClass());
			logApiVO.setMethodName(logApi.getMethodName());
			logApiVO.setParams(logApi.getParams());
			logApiVO.setCreateTime(logApi.getCreateTime());
			logApiVO.setCreateBy(logApi.getCreateBy());
			logApiVOList.add(logApiVO);
		}
		return logApiVOList;
	}

	/**
	 * 查询API-Log详情
	 *
	 * @param id
	 * @return
	 */
	@Override
	public LogApiVO getLogDetail(Long id) {
		LambdaQueryWrapper<LogApi> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(LogApi::getId, id);
		LogApi logApi = logApiMapper.selectOne(queryWrapper);
		if (logApi != null) {
			LogApiVO logApiVO = new LogApiVO();
			logApiVO.setId(logApi.getId());
			logApiVO.setTenantId(logApi.getTenantId());
			logApiVO.setServiceId(logApi.getServiceId());
			logApiVO.setTitle(logApi.getTitle());
			logApiVO.setType(logApi.getType());
			logApiVO.setTime(logApi.getTime());
			logApiVO.setEnv(logApi.getEnv());
			logApiVO.setMethod(logApi.getMethod());
			logApiVO.setRemoteIp(logApi.getRemoteIp());
			logApiVO.setRequestUri(logApi.getRequestUri());
			logApiVO.setServerIp(logApi.getServerIp());
			logApiVO.setServerHost(logApi.getServerHost());
			logApiVO.setUserAgent(logApi.getUserAgent());
			logApiVO.setMethodClass(logApi.getMethodClass());
			logApiVO.setMethodName(logApi.getMethodName());
			logApiVO.setParams(logApi.getParams());
			logApiVO.setCreateTime(logApi.getCreateTime());
			logApiVO.setCreateBy(logApi.getCreateBy());
			return logApiVO;
		}
		return null;
	}

	/**
	 * 导出API-Log
	 *
	 * @param response
	 * @param apiDTO
	 */
	@Override
	public void exportLog(HttpServletResponse response, LogApiDTO apiDTO) {
		Long count = logApiMapper.selectLogCount(apiDTO);
		if (count > EXPORT_MAX_SIZE) {
			throw new ServiceException("导出数据超过最大限制,请尽量缩短日志的时间区间!");
		}
		List<LogApi> logApis = logApiMapper.selectLogList(apiDTO);
		List<LogApiVO> logApiVOs = new ArrayList<>();
		for (LogApi logApi : logApis) {
			LogApiVO logApiVO = new LogApiVO();
			logApiVO.setId(logApi.getId());
			logApiVO.setTenantId(logApi.getTenantId());
			logApiVO.setServiceId(logApi.getServiceId());
			logApiVO.setTitle(logApi.getTitle());
			logApiVO.setType(logApi.getType());
			logApiVO.setTime(logApi.getTime());
			logApiVO.setEnv(logApi.getEnv());
			logApiVO.setMethod(logApi.getMethod());
			logApiVO.setRemoteIp(logApi.getRemoteIp());
			logApiVO.setRequestUri(logApi.getRequestUri());
			logApiVO.setServerIp(logApi.getServerIp());
			logApiVO.setServerHost(logApi.getServerHost());
			logApiVO.setUserAgent(logApi.getUserAgent());
			logApiVO.setMethodClass(logApi.getMethodClass());
			logApiVO.setMethodName(logApi.getMethodName());
			logApiVO.setParams(logApi.getParams());
			logApiVO.setCreateTime(logApi.getCreateTime());
			logApiVO.setCreateBy(logApi.getCreateBy());
			logApiVOs.add(logApiVO);
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
			String fileName = "API调用日志";
			fileName = URLEncoder.encode(fileName, Charsets.UTF_8.name()).replaceAll("\\+", "%20");

			response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");

			String sheetName = "日志详情";
			ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream())
				.registerWriteHandler(horizontalCellStyleStrategy)
				.registerWriteHandler(widthStyleStrategy)
				.build();
			WriteSheet writeSheet = EasyExcel.writerSheet(0, sheetName).head(LogApiVO.class).build();
			excelWriter.write(logApiVOs, writeSheet);
			excelWriter.finish();
			response.flushBuffer();
		} catch (Exception e) {
			log.error("导出失败,失败原因:" + e.getMessage());
			throw new ServiceException("导出失败！");
		}
	}


}
