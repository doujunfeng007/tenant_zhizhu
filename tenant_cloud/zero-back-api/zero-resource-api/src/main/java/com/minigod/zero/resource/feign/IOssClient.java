package com.minigod.zero.resource.feign;

import com.minigod.zero.core.cloud.feign.ZeroFeignRequestInterceptor;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.oss.model.ZeroFile;
import com.minigod.zero.core.tool.api.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

/**
 * IOssClient
 *
 * @author Chill
 */
@FeignClient(
	value = AppConstant.APPLICATION_RESOURCE_NAME,
	fallback = IOssClientFallback.class,
	configuration = ZeroFeignRequestInterceptor.class
)
public interface IOssClient {

	String UPLOAD_MINIO_FILE = AppConstant.FEIGN_API_PREFIX  + "/upload-minio-file";

	String UPLOAD_MINIO_FILE_1 = AppConstant.FEIGN_API_PREFIX  + "/upload-file";

	String UPLOAD_QINIU_FILE = AppConstant.FEIGN_API_PREFIX  + "/upload-qiniu-file";

	String DOWNLOAD_MINIO_FILE = AppConstant.FEIGN_API_PREFIX  + "/download-minio-file";

	String DOWNLOAD_QINIU_FILE = AppConstant.FEIGN_API_PREFIX  + "/download-qiniu-file";

	@PostMapping(value = UPLOAD_MINIO_FILE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	R<ZeroFile> uploadMinioFile(@RequestPart(value = "file") MultipartFile file, @RequestParam("fileName") String fileName);

	@PostMapping(value = UPLOAD_MINIO_FILE_1, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	R<ZeroFile> uploadMinioFile(@RequestPart(value = "file") MultipartFile file);

	@PostMapping(value = UPLOAD_QINIU_FILE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	R<ZeroFile> uploadQiniuFile( @RequestPart(value = "file") MultipartFile file, @RequestParam("fileName") String fileName);

    @PostMapping(value = DOWNLOAD_MINIO_FILE)
    R<byte[]> downloadMinioFile(@RequestParam("filePath") String filePath);

	@PostMapping(value = DOWNLOAD_QINIU_FILE)
	R<byte[]> downloadQiniuFile(@RequestParam("filePath") String filePath);

}
