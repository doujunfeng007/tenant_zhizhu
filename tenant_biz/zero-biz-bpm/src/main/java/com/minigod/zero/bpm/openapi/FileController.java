package com.minigod.zero.bpm.openapi;

import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.resource.feign.IOssClient;
import io.swagger.annotations.Api;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.HandlerMapping;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * 文件接口
 *
 * @author 掌上智珠
 * @since 2023-05-16
 */
@RestController
@RequestMapping(AppConstant.OPEN_API_PREFIX)
@Api(value = "文件接口", tags = "文件接口")
@Slf4j
public class FileController {

	@Resource
	private IOssClient ossClient;


	//private AntPathMatcher antPathMatcher = new AntPathMatcher();

	@GetMapping("/files/**")
	@SneakyThrows
	public void downloadFile(HttpServletRequest request, HttpServletResponse response) {
		InputStream inputStream = null;
		ServletOutputStream outputStream = null;
		try {
			String path = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);

			//String matchPattern = (String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
			//String fileName = antPathMatcher.extractPathWithinPattern(matchPattern, path);

			R<byte[]> resp = ossClient.downloadMinioFile(path);
			byte[] bytes = resp.getData();
			inputStream = new ByteArrayInputStream(bytes);
			outputStream = response.getOutputStream();
			IOUtils.copy(inputStream, outputStream);

		} catch (Exception e) {
			log.error("下载文件错误:{}", e.getMessage(), e);
		} finally {
			if (inputStream != null) {
				inputStream.close();
			}
			if (outputStream != null) {
				outputStream.close();
			}
		}
	}
}
