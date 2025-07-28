package com.minigod.zero.manage.controller;

import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.oss.model.ZeroFile;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.resource.feign.IOssClient;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.annotation.Resource;

@RestController
@RequestMapping(AppConstant.BACK_API_PREFIX + "/oms")
@Slf4j
public class OmsController {

	@Resource
	private IOssClient ossClient;

	@ApiOperation("上传图片")
	@PostMapping("/upload_img")
	public R<String> upload(@RequestParam("file") CommonsMultipartFile multipartFile) {
		try {
			String fileName = multipartFile.getName();
			int index = fileName.lastIndexOf(".");
			if (!(index > -1)) {
				String conType = multipartFile.getContentType();
				index = conType.lastIndexOf("/");
				if (index > -1) {
					fileName = fileName + "." + conType.substring(index + 1, conType.length());
				}
			}
			R<ZeroFile> ossResp = ossClient.uploadQiniuFile(multipartFile, fileName);
			if (null == ossResp || null == ossResp.getData() || StringUtils.isBlank(ossResp.getData().getLink())) {
				return R.fail("上传图片失败");
			}
			ZeroFile zeroFile = ossResp.getData();
			return R.data(zeroFile.getLink());
		} catch (Exception e) {
			log.error("上传图片失败", e);
			return R.fail("上传图片失败");
		}
	}

}
