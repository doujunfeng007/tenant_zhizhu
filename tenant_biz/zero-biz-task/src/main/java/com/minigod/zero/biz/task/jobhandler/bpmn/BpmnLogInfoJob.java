package com.minigod.zero.biz.task.jobhandler.bpmn;

import com.minigod.zero.biz.common.utils.DateUtil;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.search.entity.ApiLogDocument;
import com.minigod.zero.search.entity.ErrorLogDocument;
import com.minigod.zero.search.feign.ILogInfoClient;
import com.minigod.zero.search.feign.ILogInfoDocumentClient;
import com.minigod.zero.search.vo.ApiLogVO;
import com.minigod.zero.search.vo.ErrorLogVO;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 同步日志到ES定时任务
 *
 * @author eric
 * @since 2024-09-11 17:15:01
 */
@Slf4j
@Component
public class BpmnLogInfoJob {
	@Resource
	private ILogInfoClient logInfoClient;
	@Resource
	private ILogInfoDocumentClient iLogInfoDocumentClient;

	@XxlJob("apiLogInfoSyncJob")
	public void apiLogInfoSyncJob() {
		log.info(".....开始同步API-日志到ES搜索服务.....");
		try {
			//当前日期减一天
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			calendar.add(Calendar.HOUR_OF_DAY, -24);
			Date last24HTime = calendar.getTime();
			String startTime = DateUtil.dateTime(last24HTime);
			String endTime = DateUtil.dateTime(new Date());
			log.info("-->开始查询API-日志,时间范围:{}", startTime + "~" + endTime);
			long startQueryTime = System.currentTimeMillis();
			R<List<ApiLogVO>> result = logInfoClient.listLogAll(startTime, endTime);
			long endQueryTime = System.currentTimeMillis();
			log.info("从zero-log服务查询耗时:{}毫秒", (endQueryTime - startQueryTime));
			if (result.isSuccess() && result.getData() != null) {
				log.info("-->获取到的API-日志条数:{}", result.getData().size());
				List<ApiLogVO> list = result.getData();
				List<ApiLogDocument> documentList = new ArrayList<>();
				for (ApiLogVO apiLogVO : list) {
					ApiLogDocument apiLogDocument = new ApiLogDocument();
					apiLogDocument.setCreateBy(apiLogVO.getCreateBy());
					apiLogDocument.setCreateTime(apiLogVO.getCreateTime());
					apiLogDocument.setEnv(apiLogVO.getEnv());
					apiLogDocument.setId(apiLogVO.getId());
					apiLogDocument.setMethod(apiLogVO.getMethod());
					apiLogDocument.setMethodClass(apiLogVO.getMethodClass());
					apiLogDocument.setMethodName(apiLogVO.getMethodName());
					apiLogDocument.setParams(apiLogVO.getParams());
					apiLogDocument.setRemoteIp(apiLogVO.getRemoteIp());
					apiLogDocument.setRequestUri(apiLogVO.getRequestUri());
					apiLogDocument.setServerHost(apiLogVO.getServerHost());
					apiLogDocument.setServerIp(apiLogVO.getServerIp());
					apiLogDocument.setServiceId(apiLogVO.getServiceId());
					apiLogDocument.setTenantId(apiLogVO.getTenantId());
					apiLogDocument.setTime(apiLogVO.getTime());
					apiLogDocument.setTitle(apiLogVO.getTitle());
					apiLogDocument.setType(apiLogVO.getType());
					apiLogDocument.setUserAgent(apiLogVO.getUserAgent());
					documentList.add(apiLogDocument);
				}
				Integer count = iLogInfoDocumentClient.apiLogBatchLoad(documentList);
				log.info("-->同步到ES的API-日志条数:{}", count);
			} else {
				log.error("-->从zero-log服务获取日志数据失败,详情->{}", result);
			}
		} catch (Exception e) {
			log.error("同步API-日志到ES搜索服务异常->详情:", e);
		}
		log.info(".....结束同步API-日志到ES搜索服务.....");
	}

	@XxlJob("apiErrorInfoSyncJob")
	public void apiErrorInfoSyncJob() {
		log.info(".....开始同步Error-日志到ES搜索服务.....");
		try {
			//当前日期减一天
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			calendar.add(Calendar.HOUR_OF_DAY, -24);
			Date last24HTime = calendar.getTime();
			String startTime = DateUtil.dateTime(last24HTime);
			String endTime = DateUtil.dateTime(new Date());
			log.info("-->开始查询Error-日志,时间范围:{}", startTime + "~" + endTime);
			long startQueryTime = System.currentTimeMillis();
			R<List<ErrorLogVO>> result = logInfoClient.listErrorLogAll(startTime, endTime);
			long endQueryTime = System.currentTimeMillis();
			log.info("从zero-log服务查询耗时:{}毫秒", (endQueryTime - startQueryTime));
			if (result.isSuccess() && result.getData() != null) {
				log.info("-->获取到的ERROR-日志条数:{}", result.getData().size());
				List<ErrorLogVO> list = result.getData();
				List<ErrorLogDocument> documentList = new ArrayList<>();
				for (ErrorLogVO errorLogVO : list) {
					ErrorLogDocument errorLogDocument = new ErrorLogDocument();
					errorLogDocument.setCreateBy(errorLogVO.getCreateBy());
					errorLogDocument.setCreateTime(errorLogVO.getCreateTime());
					errorLogDocument.setEnv(errorLogVO.getEnv());
					errorLogDocument.setId(errorLogVO.getId().toString());
					errorLogDocument.setMethod(errorLogVO.getMethod());
					errorLogDocument.setMethodClass(errorLogVO.getMethodClass());
					errorLogDocument.setMethodName(errorLogVO.getMethodName());
					errorLogDocument.setParams(errorLogVO.getParams());
					errorLogDocument.setRemoteIp(errorLogVO.getRemoteIp());
					errorLogDocument.setRequestUri(errorLogVO.getRequestUri());
					errorLogDocument.setServerHost(errorLogVO.getServerHost());
					errorLogDocument.setServerIp(errorLogVO.getServerIp());
					errorLogDocument.setServiceId(errorLogVO.getServiceId());
					errorLogDocument.setUserAgent(errorLogVO.getUserAgent());
					errorLogDocument.setLineNumber(errorLogVO.getLineNumber());
					errorLogDocument.setMessage(errorLogVO.getMessage());
					errorLogDocument.setFileName(errorLogVO.getFileName());
					errorLogDocument.setExceptionName(errorLogVO.getExceptionName());
					errorLogDocument.setStackTrace(errorLogVO.getStackTrace());
					documentList.add(errorLogDocument);
				}
				Integer count = iLogInfoDocumentClient.errorLogBatchLoad(documentList);
				log.info("-->同步到ES的ERROR-日志条数:{}", count);
			} else {
				log.error("-->从zero-log服务获取日志数据失败,详情->{}", result);
			}
		} catch (Exception e) {
			log.error("同步ERROR-日志到ES搜索服务异常->详情:", e);
		}
		log.info(".....结束同步ERROR-日志到ES搜索服务.....");
	}
}
