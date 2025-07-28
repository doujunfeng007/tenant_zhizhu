
package com.minigod.zero.resource.endpoint;

import com.minigod.zero.resource.entity.Attach;
import com.minigod.zero.resource.service.IAttachService;
import com.minigod.zero.core.oss.model.OssFile;
import com.minigod.zero.core.oss.model.ZeroFile;
import com.minigod.zero.core.secure.annotation.PreAuth;
import com.minigod.zero.core.tenant.annotation.NonDS;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.constant.RoleConstant;
import com.minigod.zero.core.tool.utils.FileUtil;
import com.minigod.zero.core.tool.utils.Func;
import com.minigod.zero.core.tool.utils.WebUtil;
import com.minigod.zero.resource.builder.oss.OssBuilder;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.security.MessageDigest;

import static com.minigod.zero.resource.builder.oss.OssBuilder.OSS_PARAM_KEY;

/**
 * 对象存储端点
 *
 * @author Chill
 */
@Slf4j
@NonDS
@RestController
@RequiredArgsConstructor
@RequestMapping("/oss/endpoint")
@Api(value = "对象存储端点", tags = "对象存储端点")
public class OssEndpoint {

	@Value("${oss.sensitive-md5s:650fcf97057e9c009e35a3b09ee681a9}")
	private String sensitiveMd5s;

	/**
	 * 对象存储构建类
	 */
	private final OssBuilder ossBuilder;

	/**
	 * 附件表服务
	 */
	private final IAttachService attachService;

	// 七牛云和MinIO桶名相同，仅区分环境
	@Value("${oss.bucket-name}")
	private String bucketName;

	/**
	 * 创建存储桶
	 *
	 * @param bucketName 存储桶名称
	 * @return Bucket
	 */
	@SneakyThrows
	@PostMapping("/make-bucket")
	@PreAuth(RoleConstant.HAS_ROLE_ADMIN)
	public R makeBucket(@RequestParam String bucketName) {
		ossBuilder.template().makeBucket(bucketName);
		return R.success("创建成功");
	}

	/**
	 * 创建存储桶
	 *
	 * @param bucketName 存储桶名称
	 * @return R
	 */
	@SneakyThrows
	@PostMapping("/remove-bucket")
	@PreAuth(RoleConstant.HAS_ROLE_ADMIN)
	public R removeBucket(@RequestParam String bucketName) {
		ossBuilder.template().removeBucket(bucketName);
		return R.success("删除成功");
	}

	/**
	 * 拷贝文件
	 *
	 * @param fileName       存储桶对象名称
	 * @param destBucketName 目标存储桶名称
	 * @param destFileName   目标存储桶对象名称
	 * @return R
	 */
	@SneakyThrows
	@PostMapping("/copy-file")
	public R copyFile(@RequestParam String fileName, @RequestParam String destBucketName, String destFileName) {
		ossBuilder.template().copyFile(fileName, destBucketName, destFileName);
		return R.success("操作成功");
	}

	/**
	 * 获取文件信息
	 *
	 * @param fileName 存储桶对象名称
	 * @return InputStream
	 */
	@SneakyThrows
	@GetMapping("/stat-file")
	public R<OssFile> statFile(@RequestParam String fileName) {
		return R.data(ossBuilder.template().statFile(fileName));
	}

	/**
	 * 获取文件相对路径
	 *
	 * @param fileName 存储桶对象名称
	 * @return String
	 */
	@SneakyThrows
	@GetMapping("/file-path")
	public R<String> filePath(@RequestParam String fileName) {
		return R.data(ossBuilder.template().filePath(fileName));
	}


	/**
	 * 获取文件外链
	 *
	 * @param fileName 存储桶对象名称
	 * @return String
	 */
	@SneakyThrows
	@GetMapping("/file-link")
	public R<String> fileLink(@RequestParam String fileName) {
		return R.data(ossBuilder.template().fileLink(fileName));
	}

	/**
	 * 上传文件
	 *
	 * @param file 文件
	 * @return ObjectStat
	 */
	@SneakyThrows
	@PostMapping("/put-file")
	public R<ZeroFile> putFile(@RequestParam MultipartFile file,@RequestParam(required = false,defaultValue = "false") Boolean isName) {
		ZeroFile zeroFile = null;
		try {
			String fileMd5 = calculateMD5(file);
			if (sensitiveMd5s.contains(fileMd5)) {
				log.warn("七牛云敏感图片上传拦截");
				return R.fail("七牛云敏感图片上传拦截");
			}
			zeroFile = ossBuilder.template(WebUtil.getParameter(OSS_PARAM_KEY)).putFile(bucketName, file.getOriginalFilename(), file.getInputStream(),isName);
		} catch (Exception e) {
			return R.fail("上传失败:" + e.getMessage());
		}
		return R.data(zeroFile);
	}

	/**
	 * 上传文件
	 *
	 * @param fileName 存储桶对象名称
	 * @param file     文件
	 * @return ObjectStat
	 */
	@SneakyThrows
	@PostMapping("/put-file-by-name")
	public R<ZeroFile> putFile(@RequestParam String fileName, @RequestParam MultipartFile file) {
		ZeroFile zeroFile = ossBuilder.template().putFile(fileName, file.getInputStream());
		return R.data(zeroFile);
	}

	/**
	 * 上传文件并保存至附件表
	 *
	 * @param file 文件
	 * @return ObjectStat
	 */
	@SneakyThrows
	@PostMapping("/put-file-attach")
	public R<ZeroFile> putFileAttach(@RequestParam MultipartFile file) {
		ZeroFile zeroFile = null;
		try {
			String fileName = file.getOriginalFilename();
			zeroFile = ossBuilder.template().putFile(fileName, file.getInputStream());
			Long attachId = buildAttach(fileName, file.getSize(), zeroFile);
			zeroFile.setAttachId(attachId);
		} catch (Exception e) {
			return R.fail("上传失败:" + e.getMessage());
		}
		return R.data(zeroFile);
	}

	/**
	 * 上传文件并保存至附件表
	 *
	 * @param fileName 存储桶对象名称
	 * @param file     文件
	 * @return ObjectStat
	 */
	@SneakyThrows
	@PostMapping("/put-file-attach-by-name")
	public R<ZeroFile> putFileAttach(@RequestParam String fileName, @RequestParam MultipartFile file) {
		ZeroFile zeroFile = null;
		try {
			zeroFile = ossBuilder.template().putFile(fileName, file.getInputStream());
			Long attachId = buildAttach(fileName, file.getSize(), zeroFile);
			zeroFile.setAttachId(attachId);
		} catch (Exception e) {
			return R.fail("上传失败:" + e.getMessage());
		}
		return R.data(zeroFile);
	}

	/**
	 * 构建附件表
	 *
	 * @param fileName 文件名
	 * @param fileSize 文件大小
	 * @param zeroFile 对象存储文件
	 * @return attachId
	 */
	private Long buildAttach(String fileName, Long fileSize, ZeroFile zeroFile) {
		String fileExtension = FileUtil.getFileExtension(fileName);
		Attach attach = new Attach();
		attach.setDomainUrl(zeroFile.getDomain());
		attach.setLink(zeroFile.getLink());
		attach.setName(zeroFile.getName());
		attach.setOriginalName(zeroFile.getOriginalName());
		attach.setAttachSize(fileSize);
		attach.setExtension(fileExtension);
		attachService.save(attach);
		return attach.getId();
	}

	/**
	 * 删除文件
	 *
	 * @param fileName 存储桶对象名称
	 * @return R
	 */
	@SneakyThrows
	@PostMapping("/remove-file")
	@PreAuth(RoleConstant.HAS_ROLE_ADMIN)
	public R removeFile(@RequestParam String fileName) {
		ossBuilder.template().removeFile(fileName);
		return R.success("操作成功");
	}

	/**
	 * 批量删除文件
	 *
	 * @param fileNames 存储桶对象名称集合
	 * @return R
	 */
	@SneakyThrows
	@PostMapping("/remove-files")
	@PreAuth(RoleConstant.HAS_ROLE_ADMIN)
	public R removeFiles(@RequestParam String fileNames) {
		ossBuilder.template().removeFiles(Func.toStrList(fileNames));
		return R.success("操作成功");
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
