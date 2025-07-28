package com.minigod.zero.resource.feign;

import com.minigod.zero.core.oss.model.ZeroFile;
import com.minigod.zero.core.tool.api.R;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;


/**
 * 流程远程调用失败处理类
 *
 * @author Chill
 */
@Component
public class IOssClientFallback implements IOssClient {


	@Override
	public R<ZeroFile> uploadMinioFile(MultipartFile file, String fileName) {
		return R.fail("文件上传远程调用失败");
	}

	@Override
	public R<ZeroFile> uploadMinioFile(MultipartFile file) {
		return R.fail("文件上传远程调用失败");
	}

	@Override
	public R<ZeroFile> uploadQiniuFile(MultipartFile file, String fileName) {
		return R.fail("文件上传远程调用失败");
	}

	@Override
	public R<byte[]> downloadMinioFile(String filePath) {
		return R.fail("文件下载远程调用失败");
	}

	@Override
	public R<byte[]> downloadQiniuFile(String filePath) {
		return R.fail("文件下载远程调用失败");
	}

}
