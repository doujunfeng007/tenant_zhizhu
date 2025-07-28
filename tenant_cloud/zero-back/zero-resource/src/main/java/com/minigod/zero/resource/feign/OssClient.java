package com.minigod.zero.resource.feign;

import com.minigod.zero.resource.builder.oss.OssBuilder;
import com.minigod.zero.core.oss.enums.OssEnum;
import com.minigod.zero.core.oss.model.ZeroFile;
import com.minigod.zero.core.tenant.annotation.NonDS;
import com.minigod.zero.core.tool.api.R;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.security.MessageDigest;

@Slf4j
@NonDS
@RestController
@RequiredArgsConstructor
public class OssClient implements IOssClient {

	// 七牛云和MinIO桶名相同，仅区分环境
	@Value("${oss.bucket-name}")
	private String bucketName;

	@Value("${oss.sensitive-md5s:650fcf97057e9c009e35a3b09ee681a9}")
	private String sensitiveMd5s;

	/**
	 * 对象存储构建类
	 */
	private final OssBuilder ossBuilder;

	@Override
	@SneakyThrows
	public R<ZeroFile> uploadMinioFile(MultipartFile file, String fileName) {
		ZeroFile zeroFile = ossBuilder.template(OssEnum.MINIO.getName()).putFile(fileName, file.getInputStream());
		return R.data(zeroFile);
	}

	@Override
	@SneakyThrows
	public R<ZeroFile> uploadMinioFile(MultipartFile file) {
		ZeroFile zeroFile = ossBuilder.template(OssEnum.MINIO.getName()).putFile(file);
		return R.data(zeroFile);
	}

	@Override
	@SneakyThrows
	public R<ZeroFile> uploadQiniuFile(MultipartFile file, String fileName) {
		ZeroFile zeroFile = ossBuilder.template(OssEnum.MINIO.getName()).putFile(fileName, file.getInputStream());
		return R.data(zeroFile);
	}

	@Override
	@SneakyThrows
	public R<byte[]> downloadMinioFile(String filePath) {
		String fileName = filePath.substring(filePath.lastIndexOf(bucketName) + bucketName.length() + 1);
		byte[] bytes = ossBuilder.template(OssEnum.MINIO.getName()).downloadFile(bucketName, fileName);
		return R.data(bytes);
	}

	@Override
	@SneakyThrows
	public R<byte[]> downloadQiniuFile(String filePath) {
		String fileName = filePath.substring(filePath.lastIndexOf(bucketName) + bucketName.length() + 1);
		byte[] bytes = ossBuilder.template(OssEnum.QINIU.getName()).downloadFile(bucketName, fileName);
		return R.data(bytes);
	}

	private String calculateMD5(MultipartFile file) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			InputStream inputStream = file.getInputStream();

			byte[] buf = new byte[8192];
			int len;
			while ((len = inputStream.read(buf)) > 0) {
				md.update(buf, 0, len);
			}
			byte[] md5Bytes = md.digest();

			StringBuilder sb = new StringBuilder();
			for (byte b : md5Bytes) {
				sb.append(String.format("%02x", b));
			}
			return sb.toString();
		} catch (Exception e) {
			log.error("计算上传文件MD5值异常", e.getMessage());
		}
		return "---";
	}

}
