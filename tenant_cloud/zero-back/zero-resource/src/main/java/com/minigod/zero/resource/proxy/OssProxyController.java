
package com.minigod.zero.resource.proxy;

import com.qiniu.util.IOUtils;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.oss.enums.OssEnum;
import com.minigod.zero.core.oss.model.ZeroFile;
import com.minigod.zero.core.tenant.annotation.NonDS;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.resource.builder.oss.OssBuilder;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;

/**
 * 对象存储
 *
 * @author Chill
 */
@NonDS
@RestController
@RequestMapping(AppConstant.PROXY_API_PREFIX)
@Slf4j
public class OssProxyController {

	// 七牛云和MinIO桶名相同，仅区分环境
	@Value("${oss.bucket-name}")
	private String bucketName;

	@Value("${oss.sensitive-md5s:650fcf97057e9c009e35a3b09ee681a9}")
	private String sensitiveMd5s;

	/**
	 * 对象存储构建类
	 */
	@Resource
	private OssBuilder ossBuilder;

	@SneakyThrows
	@PostMapping(value = "/upload-minio-file")
	public R<ZeroFile> uploadMinioFile(MultipartFile file, String fileName) {
		ZeroFile zeroFile = ossBuilder.template(OssEnum.MINIO.getName()).putFile(bucketName, fileName, file.getInputStream());
		return R.data(zeroFile);
	}

	@SneakyThrows
	@PostMapping(value = "/upload-qiniu-file")
	public R<ZeroFile> uploadQiniuFile(MultipartFile file, String fileName) {
		String fileMd5 = calculateMD5(file);
		if (sensitiveMd5s.contains(fileMd5)) {
			log.warn("七牛云敏感图片上传拦截");
			return R.fail("七牛云敏感图片上传拦截");
		}
		ZeroFile zeroFile = ossBuilder.template(OssEnum.QINIU.getName()).putFile(bucketName, fileName, file.getInputStream());
		return R.data(zeroFile);
	}

	@SneakyThrows
	@PostMapping("/download-minio-file")
	public R<byte[]> downloadMinioFile(@RequestParam String filePath) {
		String fileName = filePath.substring(filePath.lastIndexOf(bucketName) + bucketName.length() + 1);
		byte[] bytes = ossBuilder.template(OssEnum.MINIO.getName()).downloadFile(bucketName, fileName);
		return R.data(bytes);
	}

	@SneakyThrows
	@PostMapping("/download-qiniu-file")
	public R<byte[]> downloadQiniuFile(@RequestParam String filePath) {
		URL url = new URL(filePath);
		URLConnection conn = url.openConnection();
		InputStream inputStream = conn.getInputStream();
		byte[] bytes = IOUtils.toByteArray(inputStream);
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
